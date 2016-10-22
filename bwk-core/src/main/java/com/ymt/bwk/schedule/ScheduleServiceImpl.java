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
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ymt.bwk.domain.Product;
import com.ymt.bwk.repository.ProductRepository;
import com.ymt.mirage.clearing.domain.Clearing;
import com.ymt.mirage.clearing.repository.ClearingRepository;
import com.ymt.mirage.clearing.service.ClearingService;
import com.ymt.mirage.lesson.domain.Teacher;
import com.ymt.mirage.order.domain.Order;
import com.ymt.mirage.order.domain.OrderState;
import com.ymt.mirage.order.repository.OrderRepository;
import com.ymt.mirage.user.domain.User;
import com.ymt.mirage.user.repository.UserRepository;
import com.ymt.pz365.data.jpa.domain.ClearingType;
import com.ymt.pz365.framework.param.service.ParamService;

@Service("scheduleService")
@Transactional
@Profile("weixin")
public class ScheduleServiceImpl implements ScheduleService, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ClearingService clearingService;

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ClearingRepository clearingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ParamService paramService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * (non-Javadoc)
     * 
     * @see com.ymt.bwk.schedule.ScheduleService#clear()
     */
    @Override
    @Scheduled(cron = "0 0 1 * * *")
    public void clear() {
        logger.info("定时任务启动,结算!");
        List<Order> orders = orderRepository.findByStateAndIdGreaterThan(OrderState.COMPLETE, 2728L);
        int index = 0;
        for (Order order : orders) {
            if (new DateTime(order.getCompleteTime()).plusDays(2).isBeforeNow()) {
                order.setState(OrderState.FINISH);
                clear(order.getId());
            }
            System.out.println(index++);
        }
    }

    @Override
    public void clear(Long orderId) {

        Order order = orderRepository.findOne(orderId);

        logger.info("结算订单:" + orderId);

        Product product = productRepository.findOne(order.getProducts().get(0).getGoodsId());
        product.setSaleCount(product.getSaleCount() + 1);

        Teacher teacher = product.getLesson().getTeacher();
        teacher.setSaleCount(teacher.getSaleCount() + 1);

        clearingService.addUser(order.getProducts().get(0).getGoodsId().toString(), order.getUser().getId(),
                order.getSharer().getId(), true);
        clearingService.clearing(order);

        // 城市霸主分成
        if (StringUtils.isNotBlank(order.getUser().getCity())) {
            User cityVip = userRepository.findByVipAndCity(true, order.getUser().getCity());
            if (cityVip != null && !cityVip.getId().equals(order.getUser().getId())) {
                
                BigDecimal percentage = new BigDecimal(paramService.getParam("cityVipRate", "0.02").getValue());
                BigDecimal amount = order.getAmount().multiply(percentage);
                
                Clearing clearing = new Clearing();
                clearing.setActive(true);
                clearing.setAmount(amount);
                clearing.setBefore(cityVip.getMoney());
                clearing.setContributor(order.getUser());
                clearing.setContributorName(order.getUser().getNickname());
                clearing.setDetails("城市会员"+order.getUser().getNickname()+"("+order.getUser().getId()+")购买分成");
                clearing.setLevel(1);
                clearing.setPercentage(percentage);
                clearing.setTargetId(order.getId());
                clearing.setTargetName("order");
                clearing.setTargetValue(order.getAmount());
                clearing.setType(ClearingType.REBATE);
                clearing.setUser(cityVip);
                
                cityVip.setMoney(cityVip.getMoney().add(amount));
                
                clearing.setAfter(cityVip.getMoney());
                
                clearingRepository.save(clearing);
                
                logger.info("城市霸主" + cityVip.getNickname() + "(" + cityVip.getId() + ")分成" + amount);
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        clear();
    }

}
