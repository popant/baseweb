package com.popant.baseweb.base.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.popant.baseweb.base.generator.mapper.RoleMenuRelaMapper;
import com.popant.baseweb.base.generator.mapper.UserMenuMapper;
import com.popant.baseweb.base.generator.mapper.UserRoleRelaMapper;
import com.popant.baseweb.base.generator.po.*;
import com.popant.baseweb.base.mapper.IGenericMapper;
import com.popant.baseweb.base.mapper.IUserMenuMapper;
import com.popant.baseweb.base.service.IUserMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
@Service
public class UserMenuServiceImpl extends GenericServiceImpl<UserMenu,Integer> implements IUserMenuService {
    @Autowired
    private IUserMenuMapper mapper;
    @Autowired
    private UserRoleRelaMapper userRoleRelaMapper;
    @Autowired
    private RoleMenuRelaMapper roleMenuRelaMapper;
//    private static Map<Short, Integer> productMenuRelaMap;
    @Override
    public IGenericMapper<UserMenu, Integer> getMapper() {
        return mapper;
    }
//    static{
//        short a = 1;
//        short b = 2;
//        short c = 3;
//        productMenuRelaMap = new HashMap<Short, Integer>();
//        productMenuRelaMap.put(a,5);
//        productMenuRelaMap.put(b,24);
//        productMenuRelaMap.put(c,23);
//    }

    @Override
    public String getJsonMenuList(Integer userId, Integer enterpriseId) {
        UserRoleRelaExample userRoleRelaExample = new UserRoleRelaExample();
        userRoleRelaExample.createCriteria().andUserIdEqualTo(userId);
        List<UserRoleRela> userRoleRelas = userRoleRelaMapper.selectByExample(userRoleRelaExample);
        List<Integer> roleIdList = new ArrayList<Integer>();
        //获取roleId的列表
        if(userRoleRelas != null && !userRoleRelas.isEmpty()){
            for (UserRoleRela userRoleRela : userRoleRelas) {
                Integer roleId = userRoleRela.getRoleId();
                roleIdList.add(roleId);
            }
        }
        //获取menuId列表
        List<Integer> menuIdList = new ArrayList<Integer>();
        if(roleIdList != null && !roleIdList.isEmpty()){
            RoleMenuRelaExample roleMenuRelaExample = new RoleMenuRelaExample();
            roleMenuRelaExample.createCriteria().andRoleIdIn(roleIdList);
            List<RoleMenuRela> roleMenuRelas = roleMenuRelaMapper.selectByExample(roleMenuRelaExample);
            for (RoleMenuRela roleMenuRela : roleMenuRelas) {
                menuIdList.add(roleMenuRela.getMenuId());
            }
        }
        //获取额外menuId列表
//        List<Integer> otherMenuIdList = getOtherMenuId(enterpriseId);
//        if(otherMenuIdList != null ){
//            menuIdList.addAll(otherMenuIdList);
//        }
        //根据menuId列表得到菜单的json数据
        if(menuIdList != null && !menuIdList.isEmpty()){
            UserMenuExample example = new UserMenuExample();
            example.createCriteria().andMenuIdIn(menuIdList);
            List<UserMenu> UserMenus = mapper.selectByExample(example);
            return handleMenuToJson(UserMenus);
        }
        return "[]";
    }

    /**
     * @param enterpriseId
     * 获取一些额外的菜单。开通后才会有这些菜单。
     */
//    private List<Integer> getOtherMenuId(Integer enterpriseId) {
//        EnterpriseProductRelaExample example = new EnterpriseProductRelaExample();
//        example.createCriteria().andEnterpriseIdEqualTo(String.valueOf(enterpriseId));
//        List<EnterpriseProductRela> enterpriseProductRelas = enterpriseProductRelaMapper.selectByExample(example);
//        List<Integer> otherMenuId = new ArrayList<Integer>();
//        if(enterpriseProductRelas != null && !enterpriseProductRelas.isEmpty()){
//            for (EnterpriseProductRela enterpriseProductRela : enterpriseProductRelas) {
//                Short productId = enterpriseProductRela.getProductId();
//                otherMenuId.add(productMenuRelaMap.get(productId));
//            }
//        }
//        return otherMenuId;
//    }

    /**
     * 将一批无序列的menu转化成菜单Json,并排序
     * @param menuList
     * @return
     */
    private String handleMenuToJson(List<UserMenu> menuList){
        JSONArray resultArray = new JSONArray();
        if(menuList != null && !menuList.isEmpty()){
            Map<Integer,UserMenu> idMap = new HashMap<Integer,UserMenu>();
            Map<Integer,List<UserMenu>> menuMap = new HashMap<Integer,List<UserMenu>>();
            for (UserMenu em : menuList) {
                Integer menuId = em.getMenuId();
                Integer parentId = em.getParentId();
                if(parentId == 0){
                    List<UserMenu> UserMenus = menuMap.get(menuId);
                    if(UserMenus == null ){
                        UserMenus = new ArrayList<UserMenu>();
                        menuMap.put(menuId,UserMenus);
                    }
                    idMap.put(menuId , em);
                }else{
                    List<UserMenu> UserMenus = menuMap.get(parentId);
                    if(UserMenus == null ){
                        UserMenus = new ArrayList<UserMenu>();
                        menuMap.put(parentId,UserMenus);
                    }
                    UserMenus.add(em);
                }
            }
            for (Integer pid : menuMap.keySet()) {
                UserMenu pem = idMap.get(pid);
                if(pem == null){
                    continue;
                }
                JSONObject pjson = (JSONObject)JSONObject.toJSON(pem);
                List<UserMenu> subMenuList = menuMap.get(pid);
                Collections.sort(subMenuList, new Comparator<UserMenu>() {
                    @Override
                    public int compare(UserMenu o1, UserMenu o2) {
                        return o1.getMenuOrder() - o2.getMenuOrder();
                    }
                });
                pjson.put("subMenuList",JSONArray.toJSON(subMenuList));
                resultArray.add(pjson);
            }
        }
        return resultArray.toJSONString();
    }
}
