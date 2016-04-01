package com.popant.baseweb.base.service.impl;

import com.popant.baseweb.base.generator.mapper.UserRoleRelaMapper;
import com.popant.baseweb.base.generator.po.UserInfo;
import com.popant.baseweb.base.generator.po.UserInfoExample;
import com.popant.baseweb.base.generator.po.UserRoleRela;
import com.popant.baseweb.base.mapper.IUserInfoMapper;
import com.popant.baseweb.base.service.IUserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <dl>
 * <dd>Description:</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年01月10日</dd>
 * </dl>
 *
 * @author 安宁
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    private Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private IUserInfoMapper userInfoMapper;
    @Autowired
    private UserRoleRelaMapper userRoleRelaMapper;
    @Override
    public UserInfo authentication(UserInfo user) {
        return userInfoMapper.authentication(user);
    }

    @Override
    public UserInfo selectByUsername(String userName) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserNameEqualTo(userName);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        if(userInfos != null && !userInfos.isEmpty()){
            return userInfos.get(0);
        }
        return null;
    }
    @Transactional
    @Override
    public int insertRegisterUserInfo(UserInfo userInfo){
        try {
            userInfo.setEnterpriseId(0);
            int insert = userInfoMapper.insert(userInfo);
            UserInfoExample userInfoExample = new UserInfoExample();
            userInfoExample.createCriteria().andUserNameEqualTo(userInfo.getUserName());
            List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
            if(userInfos != null && !userInfos.isEmpty()){
                UserRoleRela userRoleRela = new UserRoleRela();
                userRoleRela.setRoleId(1);
                userRoleRela.setUserId(userInfos.get(0).getUserId());
                userRoleRelaMapper.insert(userRoleRela);
            }
            return insert;
        } catch (Exception e) {
            logger.error("注册用户失败" ,e);
        }
        return 0;
    }

    @Override
    public int updateUserPassword(UserInfo userInfo) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserNameEqualTo(userInfo.getUserName());
        return userInfoMapper.updateByExampleSelective(userInfo,example);
    }
}
