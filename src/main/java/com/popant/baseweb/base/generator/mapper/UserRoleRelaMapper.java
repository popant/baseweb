package com.popant.baseweb.base.generator.mapper;

import com.popant.baseweb.base.generator.po.UserRoleRela;
import com.popant.baseweb.base.generator.po.UserRoleRelaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleRelaMapper {
    int countByExample(UserRoleRelaExample example);

    int deleteByExample(UserRoleRelaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleRela record);

    int insertSelective(UserRoleRela record);

    List<UserRoleRela> selectByExample(UserRoleRelaExample example);

    UserRoleRela selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRoleRela record, @Param("example") UserRoleRelaExample example);

    int updateByExample(@Param("record") UserRoleRela record, @Param("example") UserRoleRelaExample example);

    int updateByPrimaryKeySelective(UserRoleRela record);

    int updateByPrimaryKey(UserRoleRela record);
}