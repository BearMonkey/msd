package org.monkey.msd.security02.controller;


import org.monkey.msd.security02.pojo.Role;
import org.monkey.msd.security02.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cc
 * @since 2025-02-07
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @PostMapping("/create")
    public void createRole(@RequestBody Role role){
        roleService.saveRole(role);
    }
}
