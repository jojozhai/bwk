/**
 * 
 */
package com.ymt;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhailiang
 * @since 2016年3月30日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BwkAppApplication.class)
@ActiveProfiles({"junit", "weixin"})
//@Transactional 
public abstract class BaseTest  {  
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void flush() {
		entityManager.flush();
	}

}
