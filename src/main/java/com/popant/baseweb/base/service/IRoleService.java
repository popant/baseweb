package com.popant.baseweb.base.service;


import com.popant.baseweb.base.generator.po.Role;

import java.util.List;

public interface IRoleService extends IGenericService<Role, Integer> {
    /**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(Integer userId);
}
