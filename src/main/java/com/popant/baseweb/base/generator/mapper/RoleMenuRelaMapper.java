package com.popant.baseweb.base.generator.mapper;

import com.popant.baseweb.base.generator.po.RoleMenuRela;
import com.popant.baseweb.base.generator.po.RoleMenuRelaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMenuRelaMapper {
    int countByExample(RoleMenuRelaExample example);

    int deleteByExample(RoleMenuRelaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoleMenuRela record);

    int insertSelective(RoleMenuRela record);

    List<RoleMenuRela> selectByExample(RoleMenuRelaExample example);

    RoleMenuRela selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RoleMenuRela record, @Param("example") RoleMenuRelaExample example);

    int updateByExample(@Param("record") RoleMenuRela record, @Param("example") RoleMenuRelaExample example);

    int updateByPrimaryKeySelective(RoleMenuRela record);

    int updateByPrimaryKey(RoleMenuRela record);
}