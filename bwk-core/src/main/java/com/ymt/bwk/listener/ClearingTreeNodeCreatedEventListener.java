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

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ymt.bwk.spi.sms.SmsDemo;
import com.ymt.mirage.clearing.domain.ClearingTree;
import com.ymt.mirage.clearing.event.ClearingTreeNodeCreatedEvent;
import com.ymt.mirage.clearing.repository.ClearingTreeRepository;
import com.ymt.pz365.framework.param.service.ParamService;
import com.ymt.pz365.framework.weixin.service.WeixinService;
import com.ymt.pz365.framework.weixin.support.message.TemplateMessage;

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
    
    @Autowired
    private WeixinService weixinService;
    
    @Autowired
    private ParamService paramService;
    
    /**
     * 关系建立时的短信模板id
     */
    private String cidForClearingNodeAdd = "Hnz6UGjssPer";
    
    /**
     * 关系建立时的短信模板id2
     */
    private String cidForClearingNodeAdd2 = "zQf6l5x77JMc";
    
    private String clearingNodeAddTemplate = "KKi4q1557qQhIRE660gU7kHxG9OjCZ29s25m-wLJEG4";
    
    @Override
    public void onApplicationEvent(ClearingTreeNodeCreatedEvent event) {
        
//        if(StringUtils.isNotBlank(cidForClearingNodeAdd) && StringUtils.isNotBlank(cidForClearingNodeAdd2)){
            ClearingTree user = clearingTreeRepository.findOne(event.getUserId());
            ClearingTree parent = clearingTreeRepository.findOne(event.getParentId());
            
            SmsDemo.sms_api2(parent.getUser().getMobile(), cidForClearingNodeAdd, new String[]{user.getUser().getNickname()});
            if(parent.getParent() != null) {
                SmsDemo.sms_api2(parent.getParent().getUser().getMobile(), cidForClearingNodeAdd2, new String[]{parent.getUser().getNickname(), user.getUser().getNickname()});
            }
            
            TemplateMessage templateMessage = new TemplateMessage(user.getUser().getWeixinOpenId(), clearingNodeAddTemplate);
            templateMessage.addValue("keyword1", user.getUser().getNickname());
            templateMessage.addValue("keyword2", new DateTime().toString("yyyy-MM-dd HH:mm"));
            String content = paramService.getParam("templateContentForClearingNodeAdded", "恭喜您成功加入老师合作股东计划，分享干货知识，您就可以分成老师收费的增值服务收益，与老师一起，共享智慧成果。更多玩法秘籍，请添加微信号：banhua008，暗号“课代表”。霸王热线：010-56029675").getValue();
            templateMessage.addValue("remark", content);
            try {
                weixinService.pushTemplateMessage(templateMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
        
        
        
    }

}
