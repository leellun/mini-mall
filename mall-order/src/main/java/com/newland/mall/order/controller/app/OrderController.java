package com.newland.mall.order.controller.app;

import com.github.pagehelper.PageInfo;
import com.newland.mall.model.LoginUser;
import com.newland.mall.model.RestResponse;
import com.newland.mall.order.model.dto.OrderListDTO;
import com.newland.mall.order.model.dto.SaveOrderParamDTO;
import com.newland.mall.order.model.vo.OrderDetailVO;
import com.newland.mall.order.model.vo.OrderListVO;
import com.newland.mall.order.service.OrderService;
import com.newland.security.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@RestController
@RequestMapping("/order")
@Tag(name = "", description = "")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Parameters({
            @Parameter(name = "saveOrderDTO", description = "订单参数")
    })
    @PostMapping("/saveOrder")
    @Operation(method = "生成订单接口", description = "传参为地址id和待结算的购物项id数组")
    public RestResponse<String> saveOrder(@RequestBody SaveOrderParamDTO saveOrderDTO) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        String orderSn = orderService.saveOrder(loginUser.getUserId(), saveOrderDTO);
        return RestResponse.ok(orderSn);
    }

    @Parameters({
            @Parameter(name = "orderNo", description = "订单号")
    })
    @GetMapping("/{orderNo}")
    @Operation(method = "订单详情接口", description = "传参为订单号")
    public RestResponse<OrderDetailVO> orderDetailPage(@PathVariable("orderNo") String orderNo) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        return RestResponse.success(orderService.getOrderDetailByOrderNo(orderNo, loginUser.getUserId()));
    }

    @Parameters({
            @Parameter(name = "pageNo", description = "页码"),
            @Parameter(name = "status", description = "订单状态:0.待支付 1.待确认 2.待发货 3:已发货 4.交易成功"),
    })
    @GetMapping("/list")
    @Operation(method = "订单列表接口", description = "传参为页码")
    public RestResponse<PageInfo<OrderListVO>> orderList(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer status) {
        OrderListDTO dto = new OrderListDTO();
        dto.setOrderStatus(status);
        dto.setPageNo(pageNo);
        dto.setPageSize(5);
        LoginUser loginUser = SecurityUtil.getLoginUser();
        dto.setUserId(loginUser.getUserId());
        return RestResponse.success(orderService.getMyOrders(dto));
    }

    @Parameters({
            @Parameter(name = "orderNo", description = "订单号"),
    })
    @PutMapping("/cancel/{orderNo}")
    @Operation(method = "订单取消接口", description = "传参为订单号")
    public RestResponse cancelOrder(@PathVariable("orderNo") String orderNo) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        orderService.cancelOrder(orderNo, loginUser.getUserId());
        return RestResponse.success("取消成功");
    }

    @Parameters({
            @Parameter(name = "orderNo", description = "订单号"),
    })
    @PutMapping("/finish/{orderNo}")
    @Operation(method = "确认收货接口", description = "传参为订单号")
    public RestResponse finishOrder(@PathVariable("orderNo") String orderNo) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        orderService.finishOrder(orderNo, loginUser.getUserId());
        return RestResponse.success("取消成功");
    }

    @Parameters({
            @Parameter(name = "orderNo", description = "订单号"),
            @Parameter(name = "payType", description = "支付方式"),
    })
    @GetMapping("/paySuccess")
    @Operation(method = "模拟支付成功回调的接口", description = "传参为订单号和支付方式")
    public RestResponse paySuccess(@RequestParam("orderNo") String orderNo, @RequestParam("payType") int payType) {
        orderService.paySuccess(orderNo, payType);
        return RestResponse.success("取消成功");
    }
}