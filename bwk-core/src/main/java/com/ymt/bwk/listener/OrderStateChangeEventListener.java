/*
 * 项目名称：bwk-core
 * 类名称: OrderStateChangeEventListener
 * 创建时间: 2016年10月6日 下午4:00:52
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ymt.bwk.domain.Product;
import com.ymt.bwk.repository.ProductRepository;
import com.ymt.bwk.schedule.ScheduleService;
import com.ymt.mirage.order.domain.Order;
import com.ymt.mirage.order.domain.OrderState;
import com.ymt.mirage.order.event.OrderStateChangeEvent;
import com.ymt.mirage.order.repository.OrderRepository;
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
public class OrderStateChangeEventListener implements ApplicationListener<OrderStateChangeEvent> {

    @Autowired
    private ScheduleService scheduleService;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private WeixinService weixinService;
    
    @Autowired
    private ParamService paramService;
    
    private String orderCancelTemplateId = "DK0WU48dJzEjorrbiZGes2YDniU5telOuaWc10R8Ns8";
    
    private String orderCompleteTemplateId = "dom90ksI4K4-nAHJF9VPqh6FyFbJpH9bL3V1x4ZEc7s";
    
    @Override
    public void onApplicationEvent(OrderStateChangeEvent event) {
        if(event.getToState().equals(OrderState.FINISH)){
            
            scheduleService.clear(event.getOrderId());
            
        }else if(event.getToState().equals(OrderState.CANCEL)){
            
            Order order = orderRepository.findOne(event.getOrderId());
            Product product = productRepository.findOne(order.getProducts().get(0).getGoodsId());
            
            TemplateMessage templateMessage = new TemplateMessage(order.getUser().getWeixinOpenId(), orderCancelTemplateId);
            templateMessage.addValue("keyword1", order.getId().toString());
            templateMessage.addValue("keyword2", product.getName());
            String content = paramService.getParam("templateContentForCancelOrder", "%s您好，您的%s订单已取消，感谢您的支持和理解，如有疑问，请拨打：010-56029675联系。").getValue();
            templateMessage.addValue("remark", String.format(content, order.getUser().getNickname(), product.getName()));
            try {
                weixinService.pushTemplateMessage(templateMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(event.getToState().equals(OrderState.COMPLETE)){
            
            Order order = orderRepository.findOne(event.getOrderId());
            Product product = productRepository.findOne(order.getProducts().get(0).getGoodsId());
            
            TemplateMessage templateMessage = new TemplateMessage(order.getUser().getWeixinOpenId(), orderCompleteTemplateId);
            templateMessage.addValue("keyword1", product.getName());
            templateMessage.addValue("keyword2", order.getAmount().toString());
            String content = paramService.getParam("templateContentForCompleteOrder", "%s您好，您的%s订单已经完成，请到个人中心对老师进行评价，如有异议请于48小时内联系客服，48小时后系统将自动将您的付款支付给老师。").getValue();
            templateMessage.addValue("remark", String.format(content, order.getUser().getNickname(), product.getName()));
            try {
                weixinService.pushTemplateMessage(templateMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }

}