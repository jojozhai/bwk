/**
 * 
 */
package com.ymt.bwk.spi.sms;

import org.springframework.stereotype.Component;

import com.ymt.mirage.sms.spi.AbstractSmsProcessor;

/**
 * @author zhailiang
 * @since 2016年6月8日
 */
@Component("bwkSmsProcessor")
public class BwkSmsProcessor extends AbstractSmsProcessor {
    
    @Override
    public void send(String phone, String message) {
        if(SmsDemo.isMobileNO(phone)){
            //do nothing;
        }
    }

    @Override
    public void send(String phone, String cid, String[] params) {
//        if(SmsDemo.isMobileNO(phone)){
            SmsDemo.sms_api2(phone, cid, params);
//        }
    }

}
