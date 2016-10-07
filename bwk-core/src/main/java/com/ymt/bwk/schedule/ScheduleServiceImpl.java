/*
 * 项目名称：bwk-core
 * 类名称: ScheduleServiceImpl
 * 创建时间: 2016年10月6日 下午3:41:31
 * 创建人: zhailiang@pz365.com
 *
 * 修改历史:
 * 
 * Copyright: 2016 www.pz365.com Inc. All rights reserved.
 * 
 */
package com.ymt.bwk.schedule;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

@Service("scheduleService")
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    
    @Autowired
    private ClearingService clearingService;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ParamService paramService;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    /* (non-Javadoc)
     * @see com.ymt.bwk.schedule.ScheduleService#clear()
     */
    @Override
    @Scheduled(cron = "0 0 1 * * *")
    public void clear() {
        List<Order> orders = orderRepository.findByState(OrderState.COMPLETE);
        for (Order order : orders) {
            if(new DateTime(order.getCompleteTime()).plusDays(2).isBeforeNow()){
                order.setState(OrderState.FINISH);
                clear(order.getId());
            }
        }
    }
    
    
    @Override
    public void clear(Long orderId) {
        
        Order order = orderRepository.findOne(orderId);
        
        logger.info("结算订单:"+orderId);
        
        Product product = productRepository.findOne(order.getProducts().get(0).getGoodsId());
        product.setSaleCount(product.getSaleCount() + 1);
        
        Teacher teacher = product.getLesson().getTeacher();
        teacher.setSaleCount(teacher.getSaleCount() + 1);
        
        clearingService.addUser(order.getProducts().get(0).getGoodsId().toString(), order.getUser().getId(), order.getSharer().getId(), true);
        clearingService.clearing(order);
        
        //城市霸主分成
        if(StringUtils.isNotBlank(order.getUser().getCity())) {
            User cityVip = userRepository.findByVipAndCity(true, order.getUser().getCity());
            if(cityVip != null && !cityVip.getId().equals(order.getUser().getId())) {
                BigDecimal amount = order.getAmount().multiply(new BigDecimal(paramService.getParam("cityVipRate", "0.02").getValue()));
                cityVip.setMoney(cityVip.getMoney().add(amount));
                logger.info("城市霸主"+cityVip.getNickname()+"("+cityVip.getId()+")分成"+amount);
            }
        }        
    }

}
