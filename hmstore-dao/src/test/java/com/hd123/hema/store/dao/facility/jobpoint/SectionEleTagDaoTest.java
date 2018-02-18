package com.hd123.hema.store.dao.facility.jobpoint;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.gateway.NodeType;
import com.hd123.hema.store.bean.facility.gateway.NodeUsage;
import com.hd123.hema.store.bean.facility.jobpoint.SectionEleTag;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DatabaseSetup({"./InitSectionEleTag.xml" })
public class SectionEleTagDaoTest extends AbstractDataAccessTests {
    @Autowired
    private SectionEleTagDao sectionEleTagDao;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertSectionEleTag.xml")
    public void insert() {
        SectionEleTag sectionEleTag = new SectionEleTag();
        sectionEleTag.setUuid("ec3d8e9e79e6442fa9cb4a0b609ff44a");
        sectionEleTag.setNodeCode("string");
        sectionEleTag.setNodeAddress("string");
        sectionEleTag.setNodeType(NodeType.PickTag);
        sectionEleTag.setNodeUsage(NodeUsage.PickDisplayQty);
        sectionEleTag.setSectionUuid("d3e3263353894831b00faf650cd254f4");
        sectionEleTagDao.insert(sectionEleTag);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdateSectionEleTag.xml")
    public void update() {
        SectionEleTag sectionEleTag = sectionEleTagDao.getByUuid("b106e75cbfbc4889b16b52b737a36298");
        sectionEleTag.setNodeCode("string");
        sectionEleTag.setNodeAddress("string");
        sectionEleTag.setNodeType(NodeType.PickTag);
        sectionEleTag.setNodeUsage(NodeUsage.PickDisplayQty);
        sectionEleTagDao.update(sectionEleTag);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteSectionEleTag.xml")
    public void delete() {
        sectionEleTagDao.delete("b106e75cbfbc4889b16b52b737a36298");
    }

    @Test
    public void getByUuid() {
        SectionEleTag sectionEleTag = sectionEleTagDao.getByUuid("a358a210c9c04e7b8294b1a742e43cfe");
        Assert.assertEquals("a358a210c9c04e7b8294b1a742e43cfe", sectionEleTag.getUuid());
        Assert.assertEquals("string", sectionEleTag.getNodeCode());
        Assert.assertEquals("string", sectionEleTag.getNodeAddress());
        Assert.assertEquals(NodeType.PickTag, sectionEleTag.getNodeType());
        Assert.assertEquals(NodeUsage.PickDisplayQty, sectionEleTag.getNodeUsage());
        Assert.assertEquals("875576c819524f45a8cd00f5b0c36ffe", sectionEleTag.getSectionUuid());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<SectionEleTag> list = sectionEleTagDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<SectionEleTag> list = sectionEleTagDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchSectionEleTag.xml")
    public void insertBatch() {
        List<SectionEleTag> sectionEleTags = new ArrayList<>();

        SectionEleTag sectionEleTag1 = new SectionEleTag();
        sectionEleTag1.setUuid("ec3d8e9e79e6442fa9cb4a0b609ff44a");
        sectionEleTag1.setNodeCode("string");
        sectionEleTag1.setNodeAddress("string");
        sectionEleTag1.setNodeType(NodeType.PickTag);
        sectionEleTag1.setNodeUsage(NodeUsage.PickDisplayQty);
        sectionEleTag1.setSectionUuid("d3e3263353894831b00faf650cd254f4");

        SectionEleTag sectionEleTag2 = new SectionEleTag();
        sectionEleTag2.setUuid("65503cd8fe5a434084b92ce715bab843");
        sectionEleTag2.setNodeCode("2005");
        sectionEleTag2.setNodeAddress("2005");
        sectionEleTag2.setNodeType(NodeType.DisplayTag);
        sectionEleTag2.setNodeUsage(NodeUsage.Section);
        sectionEleTag2.setSectionUuid("7d5cc694773f478bbda64b9802cd0212");

        //hsqldb不支持批量插入
        sectionEleTags.add(sectionEleTag1);
        sectionEleTags.add(sectionEleTag2);
        sectionEleTagDao.insertBatch(sectionEleTags);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteSectionEleTag.xml")
    public void deleteBySection() {
        sectionEleTagDao.deleteBySection("e6bc692202ac47a58f4b32da9e3a256e");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
