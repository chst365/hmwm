package com.hd123.hema.store.dao.facility.jobpoint;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.gateway.NodeType;
import com.hd123.hema.store.bean.facility.gateway.NodeUsage;
import com.hd123.hema.store.bean.facility.jobpoint.RplEleTag;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DatabaseSetup({"./InitRplEleTag.xml" })
public class RplEleTagDaoTest extends AbstractDataAccessTests {
    @Autowired
    private RplEleTagDao rplEleTagDao;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertRplEleTag.xml")
    public void insert() {
        RplEleTag rplEleTag = new RplEleTag();
        rplEleTag.setUuid("70b39f9e84ba4309ab4636da9cd2523e");
        rplEleTag.setRequestNodeCode("xs004");
        rplEleTag.setRequestNodeAddress("xs002");
        rplEleTag.setRequestNodeType(NodeType.DisplayTag);
        rplEleTag.setRequestNodeUsage(NodeUsage.RplRequest);
        rplEleTag.setResponseNodeCode("xs003");
        rplEleTag.setResponseNodeAddress("xs003");
        rplEleTag.setResponseNodeType(NodeType.DisplayTag);
        rplEleTag.setResponseNodeUsage(NodeUsage.RplResponse);
        rplEleTag.setSectionUuid("e6bc692202ac47a58f4b32da9e3a256e");
        rplEleTagDao.insert(rplEleTag);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdateRplEleTag.xml")
    public void update() {
        RplEleTag rplEleTag = rplEleTagDao.getByUuid("536977f724384267bb69b36acceda7c2");
        rplEleTag.setRequestNodeCode("xs004");
        rplEleTag.setRequestNodeAddress("xs002");
        rplEleTag.setRequestNodeType(NodeType.DisplayTag);
        rplEleTag.setRequestNodeUsage(NodeUsage.RplRequest);
        rplEleTag.setResponseNodeCode("xs003");
        rplEleTag.setResponseNodeAddress("xs003");
        rplEleTag.setResponseNodeType(NodeType.DisplayTag);
        rplEleTag.setResponseNodeUsage(NodeUsage.RplResponse);
        rplEleTagDao.update(rplEleTag);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteRplEleTag.xml")
    public void delete() {
        rplEleTagDao.delete("536977f724384267bb69b36acceda7c2");
    }

    @Test
    public void getByUuid() {
        RplEleTag rplEleTag = rplEleTagDao.getByUuid("0b7a702816334f44b6136fb0d2eacfed");
        Assert.assertEquals("0b7a702816334f44b6136fb0d2eacfed", rplEleTag.getUuid());
        Assert.assertEquals("xs006", rplEleTag.getRequestNodeCode());
        Assert.assertEquals("xs006", rplEleTag.getRequestNodeAddress());
        Assert.assertEquals(NodeType.DisplayTag, rplEleTag.getRequestNodeType());
        Assert.assertEquals(NodeUsage.RplRequest, rplEleTag.getRequestNodeUsage());
        Assert.assertEquals("xs007", rplEleTag.getResponseNodeCode());
        Assert.assertEquals("xs007", rplEleTag.getResponseNodeAddress());
        Assert.assertEquals(NodeType.DisplayTag, rplEleTag.getResponseNodeType());
        Assert.assertEquals(NodeUsage.RplResponse, rplEleTag.getResponseNodeUsage());
        Assert.assertEquals("49c3f90163ca47d6824cfd432d196d20", rplEleTag.getSectionUuid());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<RplEleTag> list = rplEleTagDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<RplEleTag> list = rplEleTagDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchRplEleTag.xml")
    public void insertBatch() {
        List<RplEleTag> rplEleTags = new ArrayList<>();

        RplEleTag rplEleTag1 = new RplEleTag();
        rplEleTag1.setUuid("70b39f9e84ba4309ab4636da9cd2523e");
        rplEleTag1.setRequestNodeCode("xs004");
        rplEleTag1.setRequestNodeAddress("xs002");
        rplEleTag1.setRequestNodeType(NodeType.DisplayTag);
        rplEleTag1.setRequestNodeUsage(NodeUsage.RplRequest);
        rplEleTag1.setResponseNodeCode("xs003");
        rplEleTag1.setResponseNodeAddress("xs003");
        rplEleTag1.setResponseNodeType(NodeType.DisplayTag);
        rplEleTag1.setResponseNodeUsage(NodeUsage.RplResponse);
        rplEleTag1.setSectionUuid("e6bc692202ac47a58f4b32da9e3a256e");

        RplEleTag rplEleTag2 = new RplEleTag();
        rplEleTag2.setUuid("cfcac2f3036f4ef78c069e0bf92812f0");
        rplEleTag2.setRequestNodeCode("3004");
        rplEleTag2.setRequestNodeAddress("3004");
        rplEleTag2.setRequestNodeType(NodeType.DisplayTag);
        rplEleTag2.setRequestNodeUsage(NodeUsage.RplRequest);
        rplEleTag2.setResponseNodeCode("4004");
        rplEleTag2.setResponseNodeAddress("4004");
        rplEleTag2.setResponseNodeType(NodeType.DisplayTag);
        rplEleTag2.setResponseNodeUsage(NodeUsage.RplResponse);
        rplEleTag2.setSectionUuid("3c50ad638c354d99aec620694140eec4");

        //hsqldb不支持批量插入
        rplEleTags.add(rplEleTag1);
        rplEleTags.add(rplEleTag2);
        rplEleTagDao.insertBatch(rplEleTags);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteRplEleTag.xml")
    public void deleteBySection() {
        rplEleTagDao.deleteBySection("d3e3263353894831b00faf650cd254f4");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
