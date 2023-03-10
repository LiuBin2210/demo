package com.abin.demo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.abin.demo.common.util.PageUtils;
import com.abin.demo.common.util.R;
import com.abin.demo.controller.form.DeleteRoleByIdsForm;
import com.abin.demo.controller.form.InsertRoleForm;
import com.abin.demo.controller.form.SearchRoleByPageForm;
import com.abin.demo.controller.form.UpdateRoleForm;
import com.abin.demo.db.pojo.Role;
import com.abin.demo.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

/**
* @Author: 啊斌
* @Data:2023/2/21 15:31
*/
@RestController
@Tag(name = "RoleController",description = "用户权限web接口")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/searchRoleByPage")
    @Operation(summary = "查询用户角色")
    @SaCheckPermission(value = {"ROOT","USER:SELECT"}, mode = SaMode.OR)
    public R searchRoleByPage(@Valid @RequestBody SearchRoleByPageForm form){
        int page = form.getPage();
        int length = form.getLength();
        int start = (page -1) *length;
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        param.put("start",start);
        PageUtils pageUtils = roleService.searchRoleByPage(param);
        return R.ok().put("page",pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加角色")
    @SaCheckPermission(value = {"ROOT","USER:INSERT"},mode = SaMode.OR)
    public R insert(@Valid @RequestBody InsertRoleForm form){
        Role role = new Role();
        role.setRoleName(form.getRoleName());
        role.setPermissions(JSONUtil.parseArray(form.getPermissions()).toString());
        role.setDesc(form.getDesc());
        int rows = roleService.insert(role);
        return R.ok().put("rows",rows);
    }

    @PostMapping("/update")
    @Operation(summary = "更新角色")
    public R update(@Valid @RequestBody UpdateRoleForm form){
        Role role = new Role();
        role.setId(form.getId());
        role.setRoleName(form.getRoleName());
        role.setPermissions(JSONUtil.parseArray(form.getPermissions()).toString());
        role.setDesc(form.getDesc());
        int rows = roleService.update(role);
        if (rows ==1 && form.getChanged()){
            ArrayList<Integer> list = roleService.searchUserIdRoleId(form.getId());
            for(Integer userId : list){
                StpUtil.logoutByLoginId(userId);
            }
        }
        return R.ok().put("rows",rows);
    }

    @PostMapping("/deleteRoleByIds")
    @Operation(summary = "删除角色记录")
    @SaCheckPermission(value = {"ROOT","USER:DELETE"},mode = SaMode.OR)
    public R deleteRoleByIds(@Valid @RequestBody DeleteRoleByIdsForm form){
        int rows = roleService.deleteRoleByIds(form.getIds());
        return R.ok().put("rows",rows);
    }
}
