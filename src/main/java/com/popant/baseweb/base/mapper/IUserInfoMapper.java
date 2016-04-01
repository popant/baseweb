package com.popant.baseweb.base.mapper;

import com.popant.baseweb.base.generator.mapper.UserInfoMapper;
import com.popant.baseweb.base.generator.po.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <dl>
 * <dd>Description:</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年03月30日</dd>
 * </dl>
 *
 * @author 安宁
 */
public interface IUserInfoMapper extends UserInfoMapper,IGenericMapper<UserInfo,Integer>{
    /**
     * 用户登录验证查询
     *
     * @param record
     * @return
     */
    UserInfo authentication(@Param("record") UserInfo record);
}
