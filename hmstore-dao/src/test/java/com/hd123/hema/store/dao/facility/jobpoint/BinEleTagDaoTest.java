package com.hd123.hema.store.dao.facility.jobpoint;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.gateway.NodeType;
import com.hd123.hema.store.bean.facility.gateway.NodeUsage;
import com.hd123.hema.store.bean.facility.jobpoint.BinEleTag;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DatabaseSetup({"./InitBinEleTag.xml" })
public class BinEleTagDaoTest extends AbstractDataAccessTests {
    @Autowired
    private BinEleTagDao binEleTagDao;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertBinEleTag.xml")
    public void insert() {
        BinEleTag binEleTag = new BinEleTag();
        binEleTag.setUuid("f53bf91bdf484375892087bf57dc1277");
        binEleTag.setBinCode("2A");
        binEleTag.setNodeCode("1002");
        binEleTag.setNodeAddress("zbdzbq");
        binEleTag.setNodeType(NodeType.PickTag);
        binEleTag.setNodeUsage(NodeUsage.PickDisplayQty);
        binEleTag.setSectionUuid("9b9fa17cec3c43a6a782afa4949cc1a0");
        binEleTagDao.insert(binEleTag);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdateBinEleTag.xml")
    public void update() {
        BinEleTag binEleTag = binEleTagDao.getByUuid("5663c3182c524b02b84216e592cd86fe");
        binEleTag.setBinCode("2A");
        binEleTag.setNodeCode("1002");
        binEleTag.setNodeAddress("zbdzbq");
        binEleTag.setNodeType(NodeType.PickTag);
        binEleTag.setNodeUsage(NodeUsage.PickDisplayQty);
        binEleTagDao.update(binEleTag);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteBinEleTag.xml")
    public void delete() {
        binEleTagDao.delete("5663c3182c524b02b84216e592cd86fe");
    }

    @Test
    public void getByUuid() {
        BinEleTag binEleTag = binEleTagDao.getByUuid("002ac15631874ac8894f7061a70fd6cb");
        Assert.assertEquals("002ac15631874ac8894f7061a70fd6cb", binEleTag.getUuid());
        Assert.assertEquals("ckjAA1", binEleTag.getBinCode());
        Assert.assertEquals("zbdzbq", binEleTag.getNodeCode());
        Assert.assertEquals("zbdzbq", binEleTag.getNodeAddress());
        Assert.assertEquals(NodeType.PickTag, binEleTag.getNodeType());
        Assert.assertEquals(NodeUsage.PickDisplayQty, binEleTag.getNodeUsage());
        Assert.assertEquals("c4cf12ecbc3d4e189f64d5c91314ca6a", binEleTag.getSectionUuid());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<BinEleTag> list = binEleTagDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<BinEleTag> list = binEleTagDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchBinEleTag.xml")
    public void insertBatch() {
        List<BinEleTag> binEleTags = new ArrayList<>();

        BinEleTag binEleTag1 = new BinEleTag();
        binEleTag1.setUuid("f53bf91bdf484375892087bf57dc1277");
        binEleTag1.setBinCode("2A");
        binEleTag1.setNodeCode("1002");
        binEleTag1.setNodeAddress("zbdzbq");
        binEleTag1.setNodeType(NodeType.PickTag);
        binEleTag1.setNodeUsage(NodeUsage.PickDisplayQty);
        binEleTag1.setSectionUuid("9b9fa17cec3c43a6a782afa4949cc1a0");

        BinEleTag binEleTag2 = new BinEleTag();
        binEleTag2.setUuid("13bf001042a1459abe69075b523eebea");
        binEleTag2.setBinCode("z2货位01");
        binEleTag2.setNodeCode("标签dz001");
        binEleTag2.setNodeAddress("标签dz001");
        binEleTag2.setNodeType(NodeType.PickTag);
        binEleTag2.setNodeUsage(NodeUsage.PickDisplayQty);
        binEleTag2.setSectionUuid("e4ee8f7696b745bdbfd31d6992126fd2");

        //hsqldb不支持批量插入
        binEleTags.add(binEleTag1);
        binEleTags.add(binEleTag2);
        binEleTagDao.insertBatch(binEleTags);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteBinEleTag.xml")
    public void deleteBySection() {
        binEleTagDao.deleteBySection("d01556c6b45944d0bd2b542e7d6d6400");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
