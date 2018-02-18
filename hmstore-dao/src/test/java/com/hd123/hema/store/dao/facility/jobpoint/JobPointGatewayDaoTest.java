package com.hd123.hema.store.dao.facility.jobpoint;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.jobpoint.JobPointGateway;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DatabaseSetup({"./InitJobPointGateway.xml" })
public class JobPointGatewayDaoTest extends AbstractDataAccessTests {
    @Autowired
    private JobPointGatewayDao jobPointGatewayDao;

    @Test
    public void queryByList() {
        List<JobPointGateway> list = jobPointGatewayDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertBatchJobPointGateway.xml")
    public void insertBatch() {
        List<JobPointGateway> jobPointGateways = new ArrayList<>();

        JobPointGateway jobPointGateway1 = new JobPointGateway();
        jobPointGateway1.setUuid("01dd2b7068a645c7826ecd84a99b4c28");
        jobPointGateway1.setJobPointUuid("624419e659ab42beb73fd05540408a81");
        jobPointGateway1.setGatewayUuid("3ea794de5fda4373ad34c57f230b2172");

        JobPointGateway jobPointGateway2 = new JobPointGateway();
        jobPointGateway2.setUuid("25b4f13b53b846e5a13889002d373673");
        jobPointGateway2.setJobPointUuid("f8c0eeb68ea746e0b49cd0e1810e1500");
        jobPointGateway2.setGatewayUuid("a3d0de33248c4ab39f6e24e8f922a0c1");

        //hsqldb不支持批量插入
        jobPointGateways.add(jobPointGateway1);
        jobPointGateways.add(jobPointGateway2);
        jobPointGatewayDao.insertBatch(jobPointGateways);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./DeleteJobPointGateway.xml")
    public void deleteByJobPoint() {
        jobPointGatewayDao.deleteByJobPoint("7d78c3c5d4eb4fd1bfc8a921fa4067b3");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
