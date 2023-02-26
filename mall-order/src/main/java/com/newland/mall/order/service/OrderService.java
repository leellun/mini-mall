package com.newland.mall.order.service;

import com.github.pagehelper.PageInfo;
import com.newland.mall.order.entity.Order;
import com.newland.mall.order.entity.UserAddress;
import com.newland.mall.order.model.dto.OrderListAdminDTO;
import com.newland.mall.order.model.dto.OrderListDTO;
import com.newland.mall.order.model.dto.SaveOrderParamDTO;
import com.newland.mall.order.model.vo.OrderDetailVO;
import com.newland.mall.order.model.vo.OrderItemVO;
import com.newland.mall.order.model.vo.OrderListVO;
import com.newland.mybatis.service.IService;

import java.util.List;

/**
 *  订单服务类
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
public interface OrderService extends IService<Order> {

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    OrderDetailVO getOrderDetailByOrderId(Long orderId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    /**
     * 我的订单列表
     *
     * @param dto
     * @return
     */
    PageInfo<OrderListVO> getMyOrders(OrderListDTO dto);

    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    void cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    void finishOrder(String orderNo, Long userId);

    void paySuccess(String orderNo, int payType);

    /**
     * 生成订单  UserAddress address, List<Long> cartItemIds
     * @param mallUserId
     * @param saveOrderDTO
     * @return
     */
    String saveOrder(Long mallUserId, SaveOrderParamDTO saveOrderDTO);

    /**
     * 后台分页
     *
     * @param dto
     */
    PageInfo<Order> getOrdersPage(OrderListAdminDTO dto);

    /**
     * 订单信息修改
     *
     * @param order
     * @return
     */
    void updateOrderInfo(Order order);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    void checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    void checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    void closeOrder(Long[] ids);

    List<OrderItemVO> getOrderItems(Long orderId);
}