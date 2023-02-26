package com.newland.mall.order.mapper;

import com.newland.mall.order.entity.Order;
import com.newland.mall.order.model.dto.OrderListAdminDTO;
import com.newland.mall.order.model.dto.OrderListDTO;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Mapper 接口
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 根据订单编号获取订单信息
     * @param orderNo
     * @return
     */
    Order getByOrderNo(String orderNo);

    /**
     * 获取订单列表
     * @param dto
     * @return
     */
    List<Order> listByUserIdAndOrderStatus(OrderListDTO dto);
    /**
     * 获取订单列表
     * @param dto
     * @return
     */
    List<Order> listByOrderNoAndOrderStatus(OrderListAdminDTO dto);

    /**
     * 批量更新订单状态
     * @param orderIds
     * @param orderStatus
     * @return
     */
    int updateBatchOrderStatus(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    /**
     * 查询订单
     * @param orderIds
     * @return
     */
    List<Order> getByOrderIds(@Param("orderIds") List<Long> orderIds);
}