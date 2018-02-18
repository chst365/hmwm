/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	AbstractDataAccessTests.java
 * 模块说明：
 * 修改历史：
 * 2016-8-8 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;

/**
 * @author xiepingping
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:dao.xml", "classpath:applicationContext-ds-test-hsql.xml" })
@Rollback
@Transactional
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class })
public class AbstractDataAccessTests {

}
