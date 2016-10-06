/*
 * 项目名称：bwk-core
 * 类名称: PaymentCallbackEventListener
 * 创建时间: 2016年9月27日 下午1:52:42
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.listener;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ymt.bwk.domain.Product;
import com.ymt.bwk.repository.ProductRepository;
import com.ymt.mirage.order.domain.Order;
import com.ymt.mirage.order.domain.OrderState;
import com.ymt.mirage.order.repository.OrderRepository;
import com.ymt.pz365.framework.param.service.ParamService;
import com.ymt.pz365.framework.weixin.event.PaymentCallbackEvent;
import com.ymt.pz365.framework.weixin.service.WeixinService;
import com.ymt.pz365.framework.weixin.support.message.TemplateMessage;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
@Component
@Transactional
public class PaymentCallbackEventListener implements ApplicationListener<PaymentCallbackEvent> {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private WeixinService weixinService;
    
    private String paySuccessTemplateId = "cOZilMf5wWCNR45g11JdIcaJIh4DI7W6dLgPvD7W4rY";
    
    private String paySuccessWaiterTemplateId = "U15RiKPh-epCbwTuU3YAAXnubJe-A7dvz0Nh5eAoFx0";
    
    @Autowired
    private ParamService paramService;
    
    @Override
    public void onApplicationEvent(PaymentCallbackEvent event) {
        Order order = orderRepository.findOne(new Long(event.getInfo().getOut_trade_no()));
        order.setState(OrderState.PAYED);
        
        Product product = productRepository.findOne(order.getProducts().get(0).getGoodsId());
        
        TemplateMessage templateMessage = new TemplateMessage(order.getUser().getWeixinOpenId(), paySuccessTemplateId);
        templateMessage.addValue("name", product.getName());
        String content = paramService.getParam("templateContentForPaySuccess", "%s您好，您已成功购买%s，请保持联系畅通，客服妹子将在24小时内跟您沟通反馈。如有疑问，请拨打：010-56029675联系。").getValue();
        templateMessage.addValue("remark", String.format(content, order.getUser().getNickname(), product.getName()));
                
        try {
            weixinService.pushTemplateMessage(templateMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String waiterIds = paramService.getParam("bwkWaiterOpenId", "").getValue();
        if(StringUtils.isNotBlank(waiterIds)){
            String[] ids = StringUtils.splitByWholeSeparatorPreserveAllTokens(waiterIds, ",");
            for (String openId : ids) {
                TemplateMessage waiterMessage = new TemplateMessage(openId, paySuccessWaiterTemplateId);
                waiterMessage.addValue("keyword1", product.getName());
                waiterMessage.addValue("keyword2", order.getUser().getNickname());
                String content2 = paramService.getParam("templateContentForPaySuccess", "%s购买了%s商品，请及时到后台查看，并于24小时内给客户反馈。").getValue();
                waiterMessage.addValue("remark", String.format(content2, order.getUser().getNickname(), product.getName()));
                try {
                    weixinService.pushTemplateMessage(waiterMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
}
