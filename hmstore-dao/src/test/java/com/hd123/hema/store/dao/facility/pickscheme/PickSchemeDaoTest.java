package com.hd123.hema.store.dao.facility.pickscheme;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.pickscheme.PickScheme;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeState;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.bean.UCN;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@DatabaseSetup({"./InitPickScheme.xml" })
public class PickSchemeDaoTest extends AbstractDataAccessTests {
    @Autowired
    private PickSchemeDao pickschemeDao;
    private DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertPickScheme.xml")
    public void insert() throws ParseException {
        PickScheme pickscheme = new PickScheme();
        pickscheme.setUuid("fd8de22f3b554d6fac8309ffad4ddb14");
        pickscheme.setCode("aa1234");
        pickscheme.setName("平平的拣货方案模板");
        pickscheme.setState(PickSchemeState.Using);
        pickscheme.setVersion(4);
        pickscheme.setVersionTime(df.parse("2016-08-04 15:44:22"));
        pickscheme.setOrgUuid("2a375856ba7d4493a0253221605abd0e");
        UCN jobPoint = new UCN();
        jobPoint.setUuid("36d6c1fc9f0e463da527b75e4c3a6e8c");
        jobPoint.setCode("7-永杰zyd02");
        jobPoint.setName("7-永杰zyd02");
        pickscheme.setJobPoint(jobPoint);
        pickschemeDao.insert(pickscheme);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdatePickScheme.xml")
    public void update() throws ParseException {
        PickScheme pickscheme = pickschemeDao.getByUuid("e9b578e1704646fa9c56ae2c77168a57");
        pickscheme.setCode("aa1234");
        pickscheme.setName("平平的拣货方案模板");
        pickscheme.setState(PickSchemeState.Using);
        pickscheme.setVersion(4);
        pickscheme.setVersionTime(df.parse("2016-08-04 15:44:22"));
        UCN jobPoint = new UCN();
        jobPoint.setUuid("36d6c1fc9f0e463da527b75e4c3a6e8c");
        jobPoint.setCode("7-永杰zyd02");
        jobPoint.setName("7-永杰zyd02");
        pickscheme.setJobPoint(jobPoint);
        pickschemeDao.update(pickscheme);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeletePickScheme.xml")
    public void delete() {
        pickschemeDao.delete("e9b578e1704646fa9c56ae2c77168a57");
    }

    @Test
    public void getByUuid() throws ParseException {
        PickScheme pickscheme = pickschemeDao.getByUuid("0f5a2dd95d34441c9b48e91725de3843");
        Assert.assertEquals("0f5a2dd95d34441c9b48e91725de3843", pickscheme.getUuid());
        Assert.assertEquals("Z001", pickscheme.getCode());
        Assert.assertEquals("张问问的分拣方案模板", pickscheme.getName());
        Assert.assertEquals(PickSchemeState.UnEffective, pickscheme.getState());
        Assert.assertEquals(14, pickscheme.getVersion());
        Assert.assertEquals(df.parse("2016-08-08 10:42:57"), pickscheme.getVersionTime());
        Assert.assertEquals("hemaUuid", pickscheme.getOrgUuid());
        Assert.assertEquals("7827e2c3c2ec409fadd9c3ca38fa7c82", pickscheme.getJobPoint().getUuid());
        Assert.assertEquals("总部作业点", pickscheme.getJobPoint().getCode());
        Assert.assertEquals("总部作业点", pickscheme.getJobPoint().getName());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<PickScheme> list = pickschemeDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<PickScheme> list = pickschemeDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void getByCode() throws ParseException {
        PickScheme pickscheme = pickschemeDao.getByCode("Z001","hemaUuid","7827e2c3c2ec409fadd9c3ca38fa7c82");
        Assert.assertEquals("0f5a2dd95d34441c9b48e91725de3843", pickscheme.getUuid());
        Assert.assertEquals("Z001", pickscheme.getCode());
        Assert.assertEquals("张问问的分拣方案模板", pickscheme.getName());
        Assert.assertEquals(PickSchemeState.UnEffective, pickscheme.getState());
        Assert.assertEquals(14, pickscheme.getVersion());
        Assert.assertEquals(df.parse("2016-08-08 10:42:57"), pickscheme.getVersionTime());
        Assert.assertEquals("hemaUuid", pickscheme.getOrgUuid());
        Assert.assertEquals("7827e2c3c2ec409fadd9c3ca38fa7c82", pickscheme.getJobPoint().getUuid());
        Assert.assertEquals("总部作业点", pickscheme.getJobPoint().getCode());
        Assert.assertEquals("总部作业点", pickscheme.getJobPoint().getName());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchPickScheme.xml")
    public void insertBatch() throws ParseException {
        List<PickScheme> pickschemes = new ArrayList<>();

        PickScheme pickscheme1 = new PickScheme();
        pickscheme1.setUuid("fd8de22f3b554d6fac8309ffad4ddb14");
        pickscheme1.setCode("aa1234");
        pickscheme1.setName("平平的拣货方案模板");
        pickscheme1.setState(PickSchemeState.Using);
        pickscheme1.setVersion(4);
        pickscheme1.setVersionTime(df.parse("2016-08-04 15:44:22"));
        pickscheme1.setOrgUuid("2a375856ba7d4493a0253221605abd0e");
        UCN jobPoint1 = new UCN();
        jobPoint1.setUuid("36d6c1fc9f0e463da527b75e4c3a6e8c");
        jobPoint1.setCode("7-永杰zyd02");
        jobPoint1.setName("7-永杰zyd02");
        pickscheme1.setJobPoint(jobPoint1);

        PickScheme pickscheme2 = new PickScheme();
        pickscheme2.setUuid("7f11b2936717406685386915658032de");
        pickscheme2.setCode("Z001");
        pickscheme2.setName("ckj门店1");
        pickscheme2.setState(PickSchemeState.UnEffective);
        pickscheme2.setVersion(6);
        pickscheme2.setVersionTime(df.parse("2016-08-09 09:22:25"));
        pickscheme2.setOrgUuid("fe83284d7329420e867d6ac1c2ed2ac8");
        UCN jobPoint2 = new UCN();
        jobPoint2.setUuid("d813407c06f547b3b9541a4fc83f6d64");
        jobPoint2.setCode("总部作业点");
        jobPoint2.setName("总部作业点");
        pickscheme2.setJobPoint(jobPoint2);

        //hsqldb不支持批量插入
        pickschemes.add(pickscheme1);
        pickschemes.add(pickscheme2);
        pickschemeDao.insertBatch(pickschemes);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeletePickScheme.xml")
    public void deleteByOrg() {
        pickschemeDao.deleteByOrg("hemaUuid2");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
