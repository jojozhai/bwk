/*
 * 项目名称：bwk-core
 * 类名称: ClearingTreeNodeCreatedEventListener
 * 创建时间: 2016年10月6日 上午10:53:30
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.listener;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ymt.bwk.spi.sms.SmsDemo;
import com.ymt.mirage.clearing.domain.ClearingTree;
import com.ymt.mirage.clearing.event.ClearingTreeNodeCreatedEvent;
import com.ymt.mirage.clearing.repository.ClearingTreeRepository;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
@Component
public class ClearingTreeNodeCreatedEventListener implements ApplicationListener<ClearingTreeNodeCreatedEvent> {

    @Autowired
    private ClearingTreeRepository clearingTreeRepository;
    
    /**
     * 关系建立时的短信模板id
     */
    private String cidForClearingNodeAdd = "";
    
    /**
     * 关系建立时的短信模板id2
     */
    private String cidForClearingNodeAdd2 = "";
    
    @Override
    public void onApplicationEvent(ClearingTreeNodeCreatedEvent event) {
        
        if(StringUtils.isNotBlank(cidForClearingNodeAdd) && StringUtils.isNotBlank(cidForClearingNodeAdd2)){
            ClearingTree user = clearingTreeRepository.findOne(event.getUserId());
            ClearingTree parent = clearingTreeRepository.findOne(event.getParentId());
            
            SmsDemo.sms_api2(parent.getUser().getMobile(), cidForClearingNodeAdd, new String[]{user.getUser().getNickname()});
            if(parent.getParent() != null) {
                SmsDemo.sms_api2(parent.getParent().getUser().getMobile(), cidForClearingNodeAdd2, new String[]{parent.getUser().getNickname(), user.getUser().getNickname()});
            }
        }
        
    }

}
