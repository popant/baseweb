package com.popant.baseweb.base.po.typeHandler;

import com.channelsoft.usercenter.account.po.status.UserType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <dl>
 * <dd>Description:</dd>
 * <dd>Copyright: Copyright (C) 2006</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年01月11日</dd>
 * </dl>
 *
 * @author 安宁
 */
public class UserTypeHandler implements TypeHandler{
    private Log logger = LogFactory.getLog(this.getClass());
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if(parameter != null){
            UserType userType = (UserType)parameter;
            ps.setInt(i, userType.getValue());
        }
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        try {
            Integer result = rs.getInt(columnName);
            return UserType.getItem(result);
        } catch (SQLException e) {
            logger.error("自定义转换获取值的时候出错。",e);
        }
        return null;
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            Integer result = rs.getInt(columnIndex);
            return UserType.getItem(result);
        } catch (SQLException e) {
            logger.error("自定义转换获取值的时候出错。",e);
        }
        return null;
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            Integer result = cs.getInt(columnIndex);
            return UserType.getItem(result);
        } catch (SQLException e) {
            logger.error("自定义转换获取值的时候出错。",e);
        }
        return null;
    }
}
