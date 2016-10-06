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

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ymt.bwk.domain.Product;
import com.ymt.bwk.repository.ProductRepository;
import com.ymt.mirage.clearing.service.ClearingService;
import com.ymt.mirage.lesson.domain.Teacher;
import com.ymt.mirage.order.domain.Order;
import com.ymt.mirage.order.domain.OrderState;
import com.ymt.mirage.order.repository.OrderRepository;
import com.ymt.mirage.user.domain.User;
import com.ymt.mirage.user.repository.UserRepository;
import com.ymt.pz365.framework.param.service.ParamService;
import com.ymt.pz365.framework.weixin.event.PaymentCallbackEvent;

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
    private ClearingService clearingService;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ParamService paramService;

    @Override
    public void onApplicationEvent(PaymentCallbackEvent event) {
        Order order = orderRepository.findOne(new Long(event.getInfo().getOut_trade_no()));
        order.setState(OrderState.PAYED);
        
        Product product = productRepository.findOne(order.getProducts().get(0).getGoodsId());
        product.setSaleCount(product.getSaleCount() + 1);
        
        Teacher teacher = product.getLesson().getTeacher();
        teacher.setSaleCount(teacher.getSaleCount() + 1);
        
        clearingService.addUser(order.getProducts().get(0).getGoodsId().toString(), order.getUser().getId(), order.getSharer().getId());
        clearingService.clearing(order);
        
        if(StringUtils.isNotBlank(order.getUser().getCity())) {
            User cityVip = userRepository.findByVipAndCity(true, order.getUser().getCity());
            if(cityVip != null && !cityVip.getId().equals(order.getUser().getId())) {
                BigDecimal amount = order.getAmount().multiply(new BigDecimal(paramService.getParam("cityVipRate", "0.02").getValue()));
                cityVip.setMoney(cityVip.getMoney().add(amount));
            }
        }
        
    }

}
