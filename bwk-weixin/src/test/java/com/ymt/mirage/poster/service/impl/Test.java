/*
 * 项目名称：bwk-weixin
 * 类名称: Test
 * 创建时间: 2016年8月19日 上午10:23:44
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.mirage.poster.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ymt.BaseTest;
import com.ymt.mirage.poster.service.UserPosterService;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
public class Test extends BaseTest{
    
    @Autowired
    private UserPosterService userPosterService;
    
    @org.junit.Test
    public void test() throws Exception {
        userPosterService.create(1676L, 148868L);
    }

}
