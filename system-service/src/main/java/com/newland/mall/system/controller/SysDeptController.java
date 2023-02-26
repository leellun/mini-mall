package com.newland.mall.system.controller;

import com.newland.mall.model.RestResponse;
import com.newland.mall.system.entity.SysDept;
import com.newland.mall.system.model.dto.DeptQueryDTO;
import com.newland.mall.system.service.SysDeptService;
import com.newland.mall.validator.Insert;
import com.newland.mall.validator.IntOptions;
import com.newland.mall.validator.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 部门 控制器
 *
 * @author leellun
 * @since 2023-02-21 15:38:48
 */
@RestController
@RequestMapping("/dept")
@Tag(name = "部门", description = "部门")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDepartmentService;

    @Operation(description = "查询部门列表")
    @Parameters({@Parameter(name = "deptQueryDTO", description = "查询部门", required = true)})
    @PostMapping("/list")
    @PreAuthorize("hasAnyAuthority('user:select','dept:select')")
    public RestResponse query(@RequestBody DeptQueryDTO deptQueryDTO) {
        return RestResponse.ok(sysDepartmentService.getDepartments(deptQueryDTO));
    }

    @Operation(description = "查询部门")
    @Parameters({@Parameter(name = "id", description = "查询部门", required = true)})
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('dept:select')")
    public RestResponse get(@PathVariable Long id) {
        return RestResponse.ok(sysDepartmentService.getDepartment(id));
    }

    @Operation(description = "查询部门:获取同级与上级数据")
    @Parameters({@Parameter(name = "ids", description = "获取同级与上级数据", required = true)})
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('user:select','dept:select')")
    public RestResponse getDepartSearch(@RequestParam("name") String name) {
        return RestResponse.ok(sysDepartmentService.getDepartSearch(name));
    }

    @Operation(description = "查询部门:获取子部门")
    @Parameters({@Parameter(name = "pid", description = "上级数据id", required = true)})
    @GetMapping("/sub/{pid}")
    @PreAuthorize("hasAnyAuthority('user:select','dept:select')")
    public RestResponse<List<SysDept>> getSubDepts(@PathVariable("pid") Long pid) {
        return RestResponse.ok(sysDepartmentService.getSubDepts(pid));
    }

    @Operation(description = "新增部门")
    @Parameters({@Parameter(name = "department", description = "新增部门", required = true)})
    @PostMapping
    @PreAuthorize("hasAuthority('dept:add')")
    public RestResponse add(@Validated(Insert.class) @RequestBody SysDept department) {
        sysDepartmentService.addDepartment(department);
        return RestResponse.success("添加成功");
    }

    @Operation(description = "修改部门")
    @Parameters({@Parameter(name = "department", description = "修改部门", required = true)})
    @PutMapping
    @PreAuthorize("hasAuthority('dept:update')")
    public RestResponse update(@Validated(Update.class) @RequestBody SysDept department) {
        sysDepartmentService.updateDepartment(department);
        return RestResponse.success("修改成功");
    }

    @Operation(description = "修改部门状态")
    @Parameters({@Parameter(name = "id", description = "部门id", required = true), @Parameter(name = "enable", description = "状态", required = true)})
    @PutMapping("/enable/{id}")
    @PreAuthorize("hasAuthority('dept:update')")
    public RestResponse enable(@PathVariable("id") Long id, @RequestParam("enable") @Validated @IntOptions(options = {0, 1}, message = "状态不正确") Integer enable) {
        sysDepartmentService.enableDepartment(id, enable);
        return RestResponse.success("更新成功");
    }

    @Operation(description = "修改部门排序")
    @Parameters({@Parameter(name = "id", description = "用户id", required = true), @Parameter(name = "deptSort", description = "排序", required = true)})
    @PutMapping("/sort/{id}")
    @PreAuthorize("hasAuthority('dept:update')")
    public RestResponse updateDeptSort(@PathVariable("id") Long id, @RequestParam("deptSort") @Validated @Min(value = 1, message = "不能小于1") @Max(value = 1000, message = "不能大于1000") Integer deptSort) {
        sysDepartmentService.updateDeptSort(id, deptSort);
        return RestResponse.success("更新成功");
    }

    @Operation(description = "删除部门")
    @Parameters({@Parameter(name = "ids", description = "删除部门id", required = true)})
    @DeleteMapping
    @PreAuthorize("hasAuthority('dept:delete')")
    public RestResponse delete(@RequestBody Set<Long> ids) {
        sysDepartmentService.deleteDepartment(ids);
        return RestResponse.success("删除成功");
    }
}