package com.hd123.hema.store.dao.facility.jobpoint;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DatabaseSetup({"./InitJobpoint.xml", "../gateway/InitGateway.xml", "../gateway/InitElectronicTag.xml"  })
public class JobpointDaoTest extends AbstractDataAccessTests {
    @Autowired
    private JobPointDao jobpointDao;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertJobpoint.xml")
    public void insert() {
        JobPoint jobpoint = new JobPoint();
        jobpoint.setUuid("0ced946c0984459390d301eb26dbbab0");
        jobpoint.setCode("平平总部作?");
        jobpoint.setName("平平总部作业点002");
        jobpoint.setTemplateUuid("3c3494bcab0b4b17aaf4a3798d23ac9c");
        jobpoint.setOrgUuid("a80031c886f8436c9730e265b2dcfbc8");
        jobpointDao.insert(jobpoint);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdateJobpoint.xml")
    public void update() {
        JobPoint jobpoint = jobpointDao.getByUuid("0cea3d2be9004fbcbf8459213f47d50d");
        jobpoint.setCode("平平总部作?");
        jobpoint.setName("平平总部作业点002");
        jobpointDao.update(jobpoint);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteJobpoint.xml")
    public void delete() {
        jobpointDao.delete("0cea3d2be9004fbcbf8459213f47d50d");
    }

    @Test
    public void getByUuid() {
        JobPoint jobpoint = jobpointDao.getByUuid("0cea3d2be9004fbcbf8459213f47d50d");
        Assert.assertEquals("0cea3d2be9004fbcbf8459213f47d50d", jobpoint.getUuid());
        Assert.assertEquals("ckj总部作业", jobpoint.getCode());
        Assert.assertEquals("ckj总部作业点", jobpoint.getName());
        Assert.assertEquals("0c7aeae6de3941b38fbf4293c3ece002", jobpoint.getTemplateUuid());
        Assert.assertEquals("b89940ecec8b44caa4bc588d08a1ab23", jobpoint.getOrgUuid());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<JobPoint> list = jobpointDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<JobPoint> list = jobpointDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void getByCode() {
        JobPoint jobpoint = jobpointDao.getByCode("ckj总部作业","0c7aeae6de3941b38fbf4293c3ece002","b89940ecec8b44caa4bc588d08a1ab23");
        Assert.assertEquals("0cea3d2be9004fbcbf8459213f47d50d", jobpoint.getUuid());
        Assert.assertEquals("ckj总部作业", jobpoint.getCode());
        Assert.assertEquals("ckj总部作业点", jobpoint.getName());
        Assert.assertEquals("0c7aeae6de3941b38fbf4293c3ece002", jobpoint.getTemplateUuid());
        Assert.assertEquals("b89940ecec8b44caa4bc588d08a1ab23", jobpoint.getOrgUuid());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchJobpoint.xml")
    public void insertBatch() {
        List<JobPoint> jobpoints = new ArrayList<>();

        JobPoint jobpoint1 = new JobPoint();
        jobpoint1.setUuid("0ced946c0984459390d301eb26dbbab0");
        jobpoint1.setCode("平平总部作?");
        jobpoint1.setName("平平总部作业点002");
        jobpoint1.setTemplateUuid("3c3494bcab0b4b17aaf4a3798d23ac9c");
        jobpoint1.setOrgUuid("a80031c886f8436c9730e265b2dcfbc8");

        JobPoint jobpoint2 = new JobPoint();
        jobpoint2.setUuid("d88da5de337549169b54f584aa9830d7");
        jobpoint2.setCode("fdh");
        jobpoint2.setName("hdfhd");
        jobpoint2.setTemplateUuid("a6c48b0514234bf19614ef19cfc76c36");
        jobpoint2.setOrgUuid("85a2f360ca2a427e84284b107e6ccf63");

        //hsqldb不支持批量插入
        jobpoints.add(jobpoint1);
        jobpoints.add(jobpoint2);
        jobpointDao.insertBatch(jobpoints);
    }

    @Ignore
    public void queryAvaliableEleTags() {
        List<String> list = jobpointDao.queryAvaliableEleTags(null);
        Assert.assertEquals(1, list.size());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteJobpoint.xml")
    public void deleteByOrg() {
        jobpointDao.deleteByOrg("b89940ecec8b44caa4bc588d08a1ab23");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
