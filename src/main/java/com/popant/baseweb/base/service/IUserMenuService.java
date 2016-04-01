package com.popant.baseweb.base.service;


import com.popant.baseweb.base.generator.po.UserMenu;

/**
 * <dl>
 * <dd>Description:</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年01月12日</dd>
 * </dl>
 *
 * @author 安宁
 */
public interface IUserMenuService extends IGenericService<UserMenu,Integer> {
    String getJsonMenuList(Integer userId, Integer enterpriseId);
}
