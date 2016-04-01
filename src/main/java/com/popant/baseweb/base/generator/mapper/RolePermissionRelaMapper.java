package com.popant.baseweb.base.generator.mapper;

import com.popant.baseweb.base.generator.po.RolePermissionRela;
import com.popant.baseweb.base.generator.po.RolePermissionRelaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionRelaMapper {
    int countByExample(RolePermissionRelaExample example);

    int deleteByExample(RolePermissionRelaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermissionRela record);

    int insertSelective(RolePermissionRela record);

    List<RolePermissionRela> selectByExample(RolePermissionRelaExample example);

    RolePermissionRela selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePermissionRela record, @Param("example") RolePermissionRelaExample example);

    int updateByExample(@Param("record") RolePermissionRela record, @Param("example") RolePermissionRelaExample example);

    int updateByPrimaryKeySelective(RolePermissionRela record);

    int updateByPrimaryKey(RolePermissionRela record);
}