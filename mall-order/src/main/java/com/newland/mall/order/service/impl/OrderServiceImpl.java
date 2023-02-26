package com.newland.mall.order.service.impl;

import com.github.pagehelper.PageInfo;
import com.newland.mall.cart.agent.CartApiAgent;
import com.newland.mall.cart.vo.CartItemVO;
import com.newland.mall.enumeration.ResultCode;
import com.newland.mall.exception.BusinessException;
import com.newland.mall.model.RestResponse;
import com.newland.mall.order.common.OrderStatusEnum;
import com.newland.mall.order.common.PayStatusEnum;
import com.newland.mall.order.common.PayTypeEnum;
import com.newland.mall.order.entity.Order;
import com.newland.mall.order.entity.OrderAddress;
import com.newland.mall.order.entity.OrderItem;
import com.newland.mall.order.entity.UserAddress;
import com.newland.mall.order.mapper.OrderAddressMapper;
import com.newland.mall.order.mapper.OrderItemMapper;
import com.newland.mall.order.mapper.OrderMapper;
import com.newland.mall.order.mapper.UserAddressMapper;
import com.newland.mall.order.model.dto.OrderListAdminDTO;
import com.newland.mall.order.model.dto.OrderListDTO;
import com.newland.mall.order.model.dto.SaveOrderParamDTO;
import com.newland.mall.order.model.vo.OrderDetailVO;
import com.newland.mall.order.model.vo.OrderItemVO;
import com.newland.mall.order.model.vo.OrderListVO;
import com.newland.mall.order.service.OrderItemService;
import com.newland.mall.order.service.OrderService;
import com.newland.mall.order.utils.NumberUtil;
import com.newland.mall.product.ProductApiAgent;
import com.newland.mall.product.dto.ProductVO;
import com.newland.mall.product.dto.StockNumDTO;
import com.newland.mall.product.dto.UpdateStockNumDTO;
import com.newland.mall.utils.AssertUtil;
import com.newland.mybatis.page.PageWrapper;
import com.newland.mybatis.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private OrderAddressMapper orderAddressMapper;
    @Autowired
    private CartApiAgent cartApiAgent;
    @Autowired
    private ProductApiAgent productApiAgent;

    @Override
    public OrderDetailVO getOrderDetailByOrderId(Long orderId) {
        Order order = baseMapper.selectByPrimaryKey(orderId);
        AssertUtil.notNull(order, "数据不存在");
        List<OrderItem> orderItems = orderItemMapper.getByOrderId(order.getOrderId());
        AssertUtil.isNotTrue(orderItems.size() == 0, "无订单项");
        List<OrderItemVO> orderItemVOS = orderItems.stream().map(item -> {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(item, orderItemVO);
            return orderItemVO;
        }).collect(Collectors.toList());
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        BeanUtils.copyProperties(order, orderDetailVO);

        orderDetailVO.setOrderStatusString(OrderStatusEnum.getOrderStatusEnumByStatus(orderDetailVO.getOrderStatus()).getName());
        orderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(orderDetailVO.getPayType()).getName());
        orderDetailVO.setOrderItemVOS(orderItemVOS);
        return orderDetailVO;
    }

    @Override
    public OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        Order order = baseMapper.getByOrderNo(orderNo);
        AssertUtil.notNull(order, "数据不存在");
        AssertUtil.isTrue(userId.equals(order.getUserId()), "无权限访问订单");
        List<OrderItem> orderItems = orderItemMapper.getByOrderId(order.getOrderId());
        AssertUtil.isNotTrue(orderItems.size() == 0, "无订单项");
        List<OrderItemVO> orderItemVOS = orderItems.stream().map(item -> {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(item, orderItemVO);
            return orderItemVO;
        }).collect(Collectors.toList());
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        BeanUtils.copyProperties(order, orderDetailVO);

        orderDetailVO.setOrderStatusString(OrderStatusEnum.getOrderStatusEnumByStatus(orderDetailVO.getOrderStatus()).getName());
        orderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(orderDetailVO.getPayType()).getName());
        orderDetailVO.setOrderItemVOS(orderItemVOS);
        return orderDetailVO;
    }

    @Override
    public PageInfo<OrderListVO> getMyOrders(OrderListDTO dto) {
        PageInfo<Order> pageInfo = PageWrapper.page(dto, () -> baseMapper.listByUserIdAndOrderStatus(dto));
        List<OrderListVO> orderListVOS = new ArrayList<>();
        pageInfo.getList().forEach(item -> {
            OrderListVO orderListVO = new OrderListVO();
            BeanUtils.copyProperties(item, orderListVO);
            orderListVO.setOrderStatusString(OrderStatusEnum.getOrderStatusEnumByStatus(orderListVO.getOrderStatus()).getName());
            List<OrderItem> orderItems = orderItemMapper.getByOrderId(orderListVO.getOrderId());
            List<OrderItemVO> orderItemVOS = new ArrayList<>();
            orderItems.forEach(orderItem -> {
                OrderItemVO orderItemVO = new OrderItemVO();
                BeanUtils.copyProperties(orderItem, orderItemVO);
                orderItemVOS.add(orderItemVO);
            });
            orderListVO.setOrderItemVOS(orderItemVOS);
            orderListVOS.add(orderListVO);
        });
        PageInfo<OrderListVO> result = PageWrapper.newPageInfo(pageInfo, orderListVOS);
        return result;
    }

    @Override
    public void cancelOrder(String orderNo, Long userId) {
        Order order = baseMapper.getByOrderNo(orderNo);
        AssertUtil.notNull(order, "订单不存在");
        AssertUtil.isTrue(userId.equals(order.getUserId()), "无权限访问订单");
        //订单状态判断
        if (order.getOrderStatus().equals(OrderStatusEnum.ORDER_SUCCESS.getOrderStatus())
                || order.getOrderStatus().equals(OrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus())
                || order.getOrderStatus().equals(OrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus())
                || order.getOrderStatus().equals(OrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus())) {
            throw new BusinessException("订单不能取消");
        }
        int count = baseMapper.updateBatchOrderStatus(Collections.singletonList(order.getOrderId()), OrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus());
        AssertUtil.isTrue(count > 0, "更新失败");
    }

    @Override
    public void finishOrder(String orderNo, Long userId) {
        Order order = baseMapper.getByOrderNo(orderNo);
        AssertUtil.notNull(order, "订单不存在");
        AssertUtil.isTrue(userId.equals(order.getUserId()), "无权限访问订单");
        //订单状态判断 非出库状态下不进行修改操作
        if (order.getOrderStatus().equals(OrderStatusEnum.ORDER_EXPRESS.getOrderStatus())) {
            throw new BusinessException("订单不能结束");
        }
        order.setOrderStatus(OrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
        order.setUpdateTime(LocalDateTime.now());
        int count = baseMapper.updateByPrimaryKeySelective(order);
        AssertUtil.isTrue(count > 0, "更新失败");
    }

    @Override
    public void paySuccess(String orderNo, int payType) {
        Order order = baseMapper.getByOrderNo(orderNo);
        AssertUtil.notNull(order, "订单不存在");
        //订单状态判断 非待支付状态下不进行修改操作
        if (order.getOrderStatus().equals(OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus())) {
            throw new BusinessException("订单状态异常");
        }
        order.setOrderStatus(OrderStatusEnum.ORDER_PAID.getOrderStatus());
        order.setPayType(payType);
        order.setPayStatus(PayStatusEnum.PAY_SUCCESS.getPayStatus());
        order.setPayTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        int count = baseMapper.updateByPrimaryKeySelective(order);
        AssertUtil.isTrue(count > 0, "支付失败");
    }

    @Override
    @Transactional
    @GlobalTransactional
    public String saveOrder(Long userId, SaveOrderParamDTO saveOrderDTO) {
        AssertUtil.isTrue(saveOrderDTO.getCartItemIds().length >0,"参数异常");
        UserAddress address = userAddressMapper.selectByPrimaryKey(saveOrderDTO.getAddressId());
        AssertUtil.isTrue(userId.equals(address.getUserId()),"无权限操作此订单");

        //调用购物车服务feign获取数据
        RestResponse<List<CartItemVO>> cartItemDTOListResult = cartApiAgent.listByCartItemIds(Arrays.asList(saveOrderDTO.getCartItemIds()));
        AssertUtil.isTrue(cartItemDTOListResult.getCode().equals(ResultCode.SUCCESS.getCode()) && cartItemDTOListResult.getData().size() > 0, "参数异常");
        List<CartItemVO> itemsForSave = cartItemDTOListResult.getData();
        List<Long> itemIdList = new ArrayList<>();
        List<Long> goodsIds = new ArrayList<>();
        itemsForSave.forEach(item -> {
            itemIdList.add(item.getCartItemId());
            goodsIds.add(item.getGoodsId());
        });
        //调用商品服务feign获取数据
        RestResponse<List<ProductVO>> goodsDTOListResult = productApiAgent.listByGoodsIds(goodsIds);
        AssertUtil.isTrue(goodsDTOListResult.getCode().equals(ResultCode.SUCCESS.getCode()) && goodsDTOListResult.getData().size() > 0, "参数异常");
        List<ProductVO> productVOS = goodsDTOListResult.getData();
        //检查是否包含已下架商品
        List<ProductVO> goodsListNotSelling = productVOS.stream()
                .filter(item -> item.getGoodsSellStatus() != 0)
                .collect(Collectors.toList());
        //goodsListNotSelling 对象非空则表示有下架商品
        AssertUtil.isTrue(goodsListNotSelling.size() > 0, goodsListNotSelling.get(0).getGoodsName() + "已下架，无法生成订单");

        Map<Long, ProductVO> goodsMap = productVOS.stream().collect(Collectors.toMap(ProductVO::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        //判断商品库存
        for (CartItemVO cartItemDTO : itemsForSave) {
            //查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
            if (!goodsMap.containsKey(cartItemDTO.getGoodsId())) {
                throw new BusinessException("购物车数据异常！");
            }
            //存在数量大于库存的情况，直接返回错误提醒
            if (cartItemDTO.getGoodsCount() > goodsMap.get(cartItemDTO.getGoodsId()).getStockNum()) {
                throw new BusinessException("库存不足！");
            }
        }
        boolean check = !CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(productVOS);
        AssertUtil.isTrue(check, "操作异常!");
        //删除购物项
        //调用购物车服务feign删除数据
        RestResponse deleteByCartItemIdsResult = cartApiAgent.deleteByCartItemIds(itemIdList);
        AssertUtil.isTrue(deleteByCartItemIdsResult.getCode().equals(ResultCode.SUCCESS.getCode()), "操作异常");

        List<StockNumDTO> stockNumDTOS = itemsForSave.stream().map(item -> {
            StockNumDTO dto = new StockNumDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
        UpdateStockNumDTO updateStockNumDTO = new UpdateStockNumDTO();
        updateStockNumDTO.setStockNumDTOS(stockNumDTOS);

        //调用商品服务feign修改库存数据
        RestResponse updateStockResult = productApiAgent.updateStock(updateStockNumDTO);
        AssertUtil.isTrue(updateStockResult.getCode().equals(ResultCode.SUCCESS.getCode()), "操作异常");
        //生成订单号
        String orderNo = NumberUtil.genOrderNo();


        int priceTotal = 0;
        //保存订单
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        //总价
        for (CartItemVO cartItemVO : itemsForSave) {
            priceTotal += cartItemVO.getGoodsCount() * goodsMap.get(cartItemVO.getGoodsId()).getSellingPrice();
        }
        if (priceTotal < 1) {
            throw new BusinessException("价格错误");
        }
        order.setTotalPrice(priceTotal);
        String extraInfo = "";
        order.setExtraInfo(extraInfo);
        int r = baseMapper.insertSelective(order);
        AssertUtil.isTrue(r > 0, "操作失败");

        //生成订单项并保存订单项纪录
        //生成订单收货地址快照，并保存至数据库
        OrderAddress orderAddress = new OrderAddress();
        BeanUtils.copyProperties(address, orderAddress);
        orderAddress.setOrderId(order.getOrderId());
        //生成所有的订单项快照，并保存至数据库
        List<OrderItem> newBeeMallOrderItems = new ArrayList<>();
        for (CartItemVO cartItemVO : itemsForSave) {
            ProductVO productVO = goodsMap.get(cartItemVO.getGoodsId());
            OrderItem orderItem = new OrderItem();
            //使用BeanUtil工具类将cartItemDTO中的属性复制到newBeeMallOrderItem对象中
            BeanUtils.copyProperties(cartItemVO, orderItem);
            orderItem.setGoodsCoverImg(productVO.getGoodsCoverImg());
            orderItem.setGoodsName(productVO.getGoodsName());
            orderItem.setSellingPrice(productVO.getSellingPrice());
            //NewBeeMallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
            orderItem.setOrderId(order.getOrderId());
            newBeeMallOrderItems.add(orderItem);
        }
        //所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
        //保存至数据库
        boolean result = orderItemService.saveBatch(newBeeMallOrderItems) && orderAddressMapper.insertSelective(orderAddress) > 0;
        AssertUtil.isTrue(result, "订单提交失败");
        return orderNo;
    }


    @Override
    public PageInfo<Order> getOrdersPage(OrderListAdminDTO dto) {
        PageInfo<Order> pageInfo = PageWrapper.page(dto, () -> baseMapper.listByOrderNoAndOrderStatus(dto));
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderInfo(Order order) {
        Order temp = baseMapper.selectByPrimaryKey(order.getOrderId());
        AssertUtil.notNull(temp, "订单不存在");
        //不为空且orderStatus>=0且状态为出库之前可以修改部分信息
        AssertUtil.isTrue(temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3, "订单不能更新");
        temp.setTotalPrice(order.getTotalPrice());
        temp.setUpdateTime(LocalDateTime.now());
        int count = baseMapper.updateByPrimaryKeySelective(temp);
        AssertUtil.isTrue(count > 0, "操作失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkDone(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<Order> orders = baseMapper.getByOrderIds(Arrays.asList(ids));
        AssertUtil.isTrue(orders.size() > 0, "订单不存在");
        String errorOrderNos = "";
        for (Order order : orders) {
            if (order.getIsDeleted() == 1) {
                errorOrderNos += order.getOrderNo() + " ";
                continue;
            }
            if (order.getOrderStatus() != OrderStatusEnum.ORDER_PAID.getOrderStatus() && order.getOrderStatus() != OrderStatusEnum.ORDER_PACKAGED.getOrderStatus()) {
                errorOrderNos += order.getOrderNo() + " ";
            }
        }
        if (StringUtils.isEmpty(errorOrderNos)) {
            //订单状态正常 可以执行出库操作 修改订单状态和更新时间
            int count = baseMapper.updateBatchOrderStatus(Arrays.asList(ids), OrderStatusEnum.ORDER_PACKAGED.getOrderStatus());
            AssertUtil.isTrue(count > 0, "出库失败");
        } else {
            //订单此时不可执行出库操作
            if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                throw new BusinessException(errorOrderNos + "订单的状态不是支付成功无法执行出库操作");
            } else {
                throw new BusinessException("你选择了太多状态不是支付成功的订单，无法执行配货完成操作");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkOut(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<Order> orders = baseMapper.getByOrderIds(Arrays.asList(ids));
        AssertUtil.isTrue(orders.size() > 0, "订单不存在");
        String errorOrderNos = "";
        for (Order order : orders) {
            if (order.getIsDeleted() == 1) {
                errorOrderNos += order.getOrderNo() + " ";
                continue;
            }
            if (order.getOrderStatus() != OrderStatusEnum.ORDER_PAID.getOrderStatus() && order.getOrderStatus() != OrderStatusEnum.ORDER_PACKAGED.getOrderStatus()) {
                errorOrderNos += order.getOrderNo() + " ";
            }
        }
        if (StringUtils.isEmpty(errorOrderNos)) {
            //订单状态正常 可以执行出库操作 修改订单状态和更新时间
            int count = baseMapper.updateBatchOrderStatus(Arrays.asList(ids), OrderStatusEnum.ORDER_EXPRESS.getOrderStatus());
            AssertUtil.isTrue(count > 0, "出库失败");
        } else {
            //订单此时不可执行出库操作
            if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                throw new BusinessException(errorOrderNos + "订单的状态不是支付成功或配货完成无法执行出库操作");
            } else {
                throw new BusinessException("你选择了太多状态不是支付成功或配货完成的订单，无法执行出库操作");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeOrder(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<Order> orders = baseMapper.getByOrderIds(Arrays.asList(ids));
        AssertUtil.isTrue(orders.size() > 0, "订单不存在");

        String errorOrderNos = "";
        for (Order order : orders) {
            // isDeleted=1 一定为已关闭订单
            if (order.getIsDeleted() == 1) {
                errorOrderNos += order.getOrderNo() + " ";
                continue;
            }
            //已关闭或者已完成无法关闭订单
            if (order.getOrderStatus().equals(OrderStatusEnum.ORDER_SUCCESS.getOrderStatus()) || order.getOrderStatus() < 0) {
                errorOrderNos += order.getOrderNo() + " ";
            }
        }
        if (StringUtils.isEmpty(errorOrderNos)) {
            //订单状态正常 可以执行关闭操作 修改订单状态和更新时间
            int count = baseMapper.updateBatchOrderStatus(Arrays.asList(ids), OrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus());
            AssertUtil.isNotTrue(count > 0, "操作失败");
        } else {
            //订单此时不可执行关闭操作
            if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                throw new BusinessException(errorOrderNos + "订单不能执行关闭操作");
            } else {
                throw new BusinessException("你选择的订单不能执行关闭操作");
            }
        }
    }

    @Override
    public List<OrderItemVO> getOrderItems(Long orderId) {
        Order order = baseMapper.selectByPrimaryKey(orderId);
        AssertUtil.notNull(order, "订单不存在");
        List<OrderItem> orderItems = orderItemMapper.getByOrderId(orderId);
        AssertUtil.isTrue(orderItems.size() > 0, "订单异常");
        return orderItems.stream().map(item -> {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(item, orderItemVO);
            return orderItemVO;
        }).collect(Collectors.toList());
    }
}