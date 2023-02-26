package com.newland.mall.order.controller.admin;

import com.github.pagehelper.PageInfo;
import com.newland.mall.model.RestResponse;
import com.newland.mall.order.entity.Order;
import com.newland.mall.order.model.dto.BatchIdParamDTO;
import com.newland.mall.order.model.dto.OrderListAdminDTO;
import com.newland.mall.order.model.vo.OrderDetailVO;
import com.newland.mall.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器
 *
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@RestController
@RequestMapping("/admin/order")
@Tag(name = "订单管理", description = "")
public class OrderAdminController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @Parameters({
            @Parameter(name = "pageNo", description = "页码"),
            @Parameter(name = "pageSize", description = "每页条数"),
            @Parameter(name = "orderNo", description = "订单号"),
            @Parameter(name = "orderStatus", description = "订单状态"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Operation(method = "订单列表", description = "可根据订单号和订单状态筛选")
    public RestResponse<PageInfo<Order>> list(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam(required = false) String orderNo, @RequestParam(required = false) Integer orderStatus) {
        OrderListAdminDTO dto = new OrderListAdminDTO();
        dto.setOrderNo(orderNo);
        dto.setOrderStatus(orderStatus);
        dto.setPageNo(pageNo);
        dto.setPageSize(pageSize);
        return RestResponse.success(orderService.getOrdersPage(dto));
    }

    @Parameter(name = "orderId", description = "订单号")
    @GetMapping("/detail/{orderId}")
    @Operation(method = "订单详情接口", description = "传参为订单号")
    public RestResponse<OrderDetailVO> orderDetailPage(@PathVariable("orderId") Long orderId) {
        return RestResponse.success(orderService.getOrderDetailByOrderId(orderId));
    }

    /**
     * 配货
     */
    @RequestMapping(value = "/checkDone", method = RequestMethod.PUT)
    @Operation(method = "修改订单状态为配货成功", description = "批量修改")
    public RestResponse checkDone(@RequestBody BatchIdParamDTO dto) {
        orderService.checkDone(dto.getIds());
        return RestResponse.success("操作成功");
    }

    /**
     * 出库
     */
    @RequestMapping(value = "/checkOut", method = RequestMethod.PUT)
    @Operation(method = "修改订单状态为已出库", description = "批量修改")
    public RestResponse checkOut(@RequestBody BatchIdParamDTO dto) {
        orderService.checkOut(dto.getIds());
        return RestResponse.success("操作成功");
    }

    /**
     * 关闭订单
     */
    @RequestMapping(value = "/close", method = RequestMethod.PUT)
    @Operation(method = "修改订单状态为商家关闭", description = "批量修改")
    public RestResponse closeOrder(@RequestBody BatchIdParamDTO dto) {
        orderService.closeOrder(dto.getIds());
        return RestResponse.success("操作成功");
    }
}