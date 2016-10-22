/*
 * 项目名称：bwk-core
 * 类名称: ClearingEventListener
 * 创建时间: 2016年10月10日 下午3:12:26
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.listener;

import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ymt.bwk.spi.sms.SmsDemo;
import com.ymt.mirage.clearing.event.ClearingEvent;
import com.ymt.mirage.order.domain.Order;
import com.ymt.mirage.order.repository.OrderRepository;
import com.ymt.mirage.user.domain.User;
import com.ymt.mirage.user.repository.UserRepository;
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
public class ClearingEventListener implements ApplicationListener<ClearingEvent> {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ParamService paramService;
    
    @Autowired
    private WeixinService weixinService;
    
    /**
     * 结算推送
     */
    private String clearingTemplate = "NK9j06jfIVhyts_kzC6BBju3AMjGpSNlW0dzgjUaDlU";
    /**
     * 结算短信
     */
    private String clearingSmsCid = "gMV08kzdhV9N";
    
    @Override
    public void onApplicationEvent(ClearingEvent event) {
        
        User contributor = userRepository.findOne(event.getContributorId());
        User beneficiary = userRepository.findOne(event.getBeneficiaryId());
        Order order = orderRepository.findOne(event.getOrderId());
        
        if(StringUtils.isNotBlank(beneficiary.getWeixinOpenId())){
            TemplateMessage templateMessage = new TemplateMessage(beneficiary.getWeixinOpenId(), clearingTemplate);
            templateMessage.addValue("keyword1", order.getId().toString());
            templateMessage.addValue("keyword2", order.getAmount().toString());
            templateMessage.addValue("keyword3", event.getProfit().toString());
            templateMessage.addValue("keyword4", new DateTime().toString("yyyy-MM-dd HH:mm"));
            String content = paramService.getParam("templateContentForClearing", "%s购买了%s元商品，系统已将奖学金%s元自动打入您的个人账户，继续努力，每月躺赚万元不是梦~~").getValue();
            templateMessage.addValue("remark", String.format(content, contributor.getNickname(), order.getAmount().setScale(2, RoundingMode.DOWN), event.getProfit()));
            try {
                weixinService.pushTemplateMessage(templateMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SmsDemo.sms_api2(beneficiary.getMobile(), clearingSmsCid, new String[]{contributor.getNickname(), order.getAmount().setScale(2, RoundingMode.DOWN).toString(), event.getProfit().toString()});
    }

}
