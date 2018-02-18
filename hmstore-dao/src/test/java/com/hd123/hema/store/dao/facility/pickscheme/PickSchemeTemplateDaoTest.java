package com.hd123.hema.store.dao.facility.pickscheme;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeState;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeTemplate;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.rumba.commons.biz.entity.UCN;
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

@DatabaseSetup({"./InitPickSchemeTemplate.xml" })
public class PickSchemeTemplateDaoTest extends AbstractDataAccessTests {
    @Autowired
    private PickSchemeTemplateDao pickSchemeTemplateDao;
    private DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertPickSchemeTemplate.xml")
    public void insert() throws ParseException {
        PickSchemeTemplate pickSchemeTemplate = new PickSchemeTemplate();
        pickSchemeTemplate.setUuid("fd8de22f3b554d6fac8309ffad4ddb14");
        pickSchemeTemplate.setCode("aa1234");
        pickSchemeTemplate.setName("平平的拣货方案模板");
        pickSchemeTemplate.setState(PickSchemeState.Using);
        pickSchemeTemplate.setVersion(4);
        pickSchemeTemplate.setVersionTime(df.parse("2016-08-04 15:44:22"));
        pickSchemeTemplate.setOrgUuid("2a375856ba7d4493a0253221605abd0e");
        UCN facilityTemplate = new UCN();
        facilityTemplate.setUuid("2a375856ba7d4493a0253221605abd0e");
        facilityTemplate.setCode("ss1002");
        facilityTemplate.setName("测试设施模板");
        pickSchemeTemplate.setFacilityTemplate(facilityTemplate);
        pickSchemeTemplateDao.insert(pickSchemeTemplate);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdatePickSchemeTemplate.xml")
    public void update() throws ParseException {
        PickSchemeTemplate pickSchemeTemplate = pickSchemeTemplateDao.getByUuid("e9b578e1704646fa9c56ae2c77168a57");
        pickSchemeTemplate.setCode("aa1234");
        pickSchemeTemplate.setName("平平的拣货方案模板");
        pickSchemeTemplate.setState(PickSchemeState.Using);
        pickSchemeTemplate.setVersion(4);
        pickSchemeTemplate.setVersionTime(df.parse("2016-08-04 15:44:22"));
        UCN facilityTemplate = new UCN();
        facilityTemplate.setUuid("2a375856ba7d4493a0253221605abd0e");
        facilityTemplate.setCode("ss1002");
        facilityTemplate.setName("测试设施模板");
        pickSchemeTemplate.setFacilityTemplate(facilityTemplate);
        pickSchemeTemplateDao.update(pickSchemeTemplate);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeletePickSchemeTemplate.xml")
    public void delete() {
        pickSchemeTemplateDao.delete("e9b578e1704646fa9c56ae2c77168a57");
    }

    @Test
    public void getByUuid() throws ParseException {
        PickSchemeTemplate pickSchemeTemplate = pickSchemeTemplateDao.getByUuid("0f5a2dd95d34441c9b48e91725de3843");
        Assert.assertEquals("0f5a2dd95d34441c9b48e91725de3843", pickSchemeTemplate.getUuid());
        Assert.assertEquals("Z001", pickSchemeTemplate.getCode());
        Assert.assertEquals("张问问的分拣方案模板", pickSchemeTemplate.getName());
        Assert.assertEquals(PickSchemeState.UnEffective, pickSchemeTemplate.getState());
        Assert.assertEquals(14, pickSchemeTemplate.getVersion());
        Assert.assertEquals(df.parse("2016-08-08 10:42:57"), pickSchemeTemplate.getVersionTime());
        Assert.assertEquals("hemaUuid", pickSchemeTemplate.getOrgUuid());
        Assert.assertEquals("729c23e63c9f457f8ab3b71ce5c35d54", pickSchemeTemplate.getFacilityTemplate().getUuid());
        Assert.assertEquals("永杰设施模板7", pickSchemeTemplate.getFacilityTemplate().getCode());
        Assert.assertEquals("永杰设施模板7", pickSchemeTemplate.getFacilityTemplate().getName());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<PickSchemeTemplate> list = pickSchemeTemplateDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<PickSchemeTemplate> list = pickSchemeTemplateDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void getByCode() throws ParseException {
        PickSchemeTemplate pickSchemeTemplate = pickSchemeTemplateDao.getByCode("Z001","hemaUuid");
        Assert.assertEquals("0f5a2dd95d34441c9b48e91725de3843", pickSchemeTemplate.getUuid());
        Assert.assertEquals("Z001", pickSchemeTemplate.getCode());
        Assert.assertEquals("张问问的分拣方案模板", pickSchemeTemplate.getName());
        Assert.assertEquals(PickSchemeState.UnEffective, pickSchemeTemplate.getState());
        Assert.assertEquals(14, pickSchemeTemplate.getVersion());
        Assert.assertEquals(df.parse("2016-08-08 10:42:57"), pickSchemeTemplate.getVersionTime());
        Assert.assertEquals("hemaUuid", pickSchemeTemplate.getOrgUuid());
        Assert.assertEquals("729c23e63c9f457f8ab3b71ce5c35d54", pickSchemeTemplate.getFacilityTemplate().getUuid());
        Assert.assertEquals("永杰设施模板7", pickSchemeTemplate.getFacilityTemplate().getCode());
        Assert.assertEquals("永杰设施模板7", pickSchemeTemplate.getFacilityTemplate().getName());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchPickSchemeTemplate.xml")
    public void insertBatch() throws ParseException {
        List<PickSchemeTemplate> pickSchemeTemplates = new ArrayList<>();

        PickSchemeTemplate pickSchemeTemplate1 = new PickSchemeTemplate();
        pickSchemeTemplate1.setUuid("fd8de22f3b554d6fac8309ffad4ddb14");
        pickSchemeTemplate1.setCode("aa1234");
        pickSchemeTemplate1.setName("平平的拣货方案模板");
        pickSchemeTemplate1.setState(PickSchemeState.Using);
        pickSchemeTemplate1.setVersion(4);
        pickSchemeTemplate1.setVersionTime(df.parse("2016-08-04 15:44:22"));
        pickSchemeTemplate1.setOrgUuid("2a375856ba7d4493a0253221605abd0e");
        UCN facilityTemplate1 = new UCN();
        facilityTemplate1.setUuid("2a375856ba7d4493a0253221605abd0e");
        facilityTemplate1.setCode("ss1002");
        facilityTemplate1.setName("测试设施模板");
        pickSchemeTemplate1.setFacilityTemplate(facilityTemplate1);

        PickSchemeTemplate pickSchemeTemplate2 = new PickSchemeTemplate();
        pickSchemeTemplate2.setUuid("7f11b2936717406685386915658032de");
        pickSchemeTemplate2.setCode("Z001");
        pickSchemeTemplate2.setName("ckj门店1");
        pickSchemeTemplate2.setState(PickSchemeState.UnEffective);
        pickSchemeTemplate2.setVersion(6);
        pickSchemeTemplate2.setVersionTime(df.parse("2016-08-09 09:22:25"));
        pickSchemeTemplate2.setOrgUuid("fe83284d7329420e867d6ac1c2ed2ac8");
        UCN facilityTemplate2 = new UCN();
        facilityTemplate2.setUuid("d813407c06f547b3b9541a4fc83f6d64");
        facilityTemplate2.setCode("总部作业点");
        facilityTemplate2.setName("总部作业点");
        pickSchemeTemplate2.setFacilityTemplate(facilityTemplate2);

        //hsqldb不支持批量插入
        pickSchemeTemplates.add(pickSchemeTemplate1);
        pickSchemeTemplates.add(pickSchemeTemplate2);
        pickSchemeTemplateDao.insertBatch(pickSchemeTemplates);
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
