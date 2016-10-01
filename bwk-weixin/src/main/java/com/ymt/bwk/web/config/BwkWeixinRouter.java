/*
 * 项目名称：bwk-weixin
 * 类名称: BwkWeixinRouter
 * 创建时间: 2016年10月1日 下午2:29:59
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.web.config;

import java.net.URLDecoder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ymt.pz365.framework.weixin.spi.router.WeixinRouter;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
@Component("weixinRouter")
public class BwkWeixinRouter implements WeixinRouter {

    @Override
    public String route(String state, UserDetails user) throws Exception {
        return URLDecoder.decode(state, "UTF-8");
    }

}
