/**
 * 
 */
package com.popant.baseweb.base.controller;


import com.popant.baseweb.base.generator.po.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;

/**
 * <dl>
 * <dt>BaseController</dt>
 * <dd>Description:Controller基类</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年1月14日</dd>
 * </dl>
 * 
 * @author cy
 */
public class BaseController {
	
	protected Log logger = LogFactory.getLog(getClass());
	
	protected UserInfo getUserInfo(){
		UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getSession().getAttribute("userInfo");
		return userInfo;
	}

}
