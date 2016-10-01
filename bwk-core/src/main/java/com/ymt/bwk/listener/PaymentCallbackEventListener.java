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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ymt.mirage.order.domain.Order;
import com.ymt.mirage.order.domain.OrderState;
import com.ymt.mirage.order.repository.OrderRepository;
import com.ymt.pz365.framework.weixin.event.PaymentCallbackEvent;

/**
 *
 *
 * @author zhailiang@pz365.com
 * @version 1.0.0
 */
@Component
public class PaymentCallbackEventListener implements ApplicationListener<PaymentCallbackEvent> {
    
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void onApplicationEvent(PaymentCallbackEvent event) {
        Order order = orderRepository.findOne(new Long(event.getInfo().getOut_trade_no()));
        order.setState(OrderState.PAYED);
    }

}
