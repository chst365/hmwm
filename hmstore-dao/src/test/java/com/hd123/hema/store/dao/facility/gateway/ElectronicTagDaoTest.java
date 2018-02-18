package com.hd123.hema.store.dao.facility.gateway;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.gateway.ElectronicTag;
import com.hd123.hema.store.bean.facility.gateway.NodeState;
import com.hd123.hema.store.bean.facility.gateway.NodeType;
import com.hd123.hema.store.bean.facility.gateway.NodeUsage;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
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

@DatabaseSetup({"./InitElectronicTag.xml" })
public class ElectronicTagDaoTest extends AbstractDataAccessTests {
    @Autowired
    private ElectronicTagDao electronicTagDao;

    private DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertElectronicTag.xml")
    public void insert() {
        ElectronicTag electronicTag = new ElectronicTag();
        electronicTag.setUuid("bfc42e1c52a84e45a447c893935a700e");
        electronicTag.setNodeCode("Code004");
        electronicTag.setNodeAddress("xs003");
        electronicTag.setNodeType(NodeType.PickTag);
        electronicTag.setNodeUsage(NodeUsage.CompleteInstruction);
        electronicTag.setNodeState(NodeState.Warning);
        electronicTag.setGatewayUuid("2a375856ba7d4493a0253221605abd0e");
        try {
            electronicTag.setCheckTime(df.parse("2016-07-25 14:57:10"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        electronicTag.setRemark("None");
        electronicTagDao.insert(electronicTag);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdateElectronicTag.xml")
    public void update() {
        ElectronicTag electronicTag = electronicTagDao.getByUuid("a7bca9f3e5e345b9be444b87fc464121");
        electronicTag.setNodeCode("Code004");
        electronicTag.setNodeAddress("xs004");
        electronicTag.setNodeType(NodeType.PickTag);
        electronicTag.setNodeUsage(NodeUsage.CompleteInstruction);
        electronicTag.setNodeState(NodeState.Warning);
        try {
            electronicTag.setCheckTime(df.parse("2016-07-25 14:57:10"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        electronicTag.setRemark("None");
        electronicTagDao.update(electronicTag);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteElectronicTag.xml")
    public void delete() {
        electronicTagDao.delete("a7bca9f3e5e345b9be444b87fc464121");
    }

    @Test
    public void getByUuid() throws ParseException {
        ElectronicTag electronicTag = electronicTagDao.getByUuid("02125606ea4245c78455f62701eea072");
        Assert.assertEquals("02125606ea4245c78455f62701eea072", electronicTag.getUuid());
        Assert.assertEquals("zbdzbq", electronicTag.getNodeCode());
        Assert.assertEquals("zbdzbq", electronicTag.getNodeAddress());
        Assert.assertEquals(NodeType.PickTag, electronicTag.getNodeType());
        Assert.assertEquals(NodeUsage.PickDisplayQty, electronicTag.getNodeUsage());
        Assert.assertEquals(NodeState.Normal, electronicTag.getNodeState());
        Assert.assertEquals("22b2a451a58e4a658fe6a728dbc98ba1", electronicTag.getGatewayUuid());
        Assert.assertEquals(df.parse("2016-7-25 14:57:10"), electronicTag.getCheckTime());
        Assert.assertEquals("pp", electronicTag.getRemark());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<ElectronicTag> list = electronicTagDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<ElectronicTag> list = electronicTagDao.queryByList(null);
        Assert.assertEquals(3, list.size());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchElectronicTag.xml")
    public void insertBatch() {
        List<ElectronicTag> electronicTags = new ArrayList<>();

        ElectronicTag electronicTag1 = new ElectronicTag();
        electronicTag1.setUuid("bfc42e1c52a84e45a447c893935a700e");
        electronicTag1.setNodeCode("Code004");
        electronicTag1.setNodeAddress("xs003");
        electronicTag1.setNodeType(NodeType.PickTag);
        electronicTag1.setNodeUsage(NodeUsage.CompleteInstruction);
        electronicTag1.setNodeState(NodeState.Warning);
        electronicTag1.setGatewayUuid("2a375856ba7d4493a0253221605abd0e");
        try {
            electronicTag1.setCheckTime(df.parse("2016-07-30 11:57:11"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        electronicTag1.setRemark("None");

        ElectronicTag electronicTag2 = new ElectronicTag();
        electronicTag2.setUuid("14bb941a11a949d0aea664086fbeba4c");
        electronicTag2.setNodeCode("Code005");
        electronicTag2.setNodeAddress("E000000004");
        electronicTag2.setNodeType(NodeType.DisplayTag);
        electronicTag2.setNodeUsage(NodeUsage.PickDisplayQty);
        electronicTag2.setNodeState(NodeState.Normal);
        electronicTag2.setGatewayUuid("4841692a3feb430eb032d1c5505be2f6");
        try {
            electronicTag2.setCheckTime(df.parse("2016-08-01 11:11:11"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        electronicTag2.setRemark("xx");

        //hsqldb不支持批量插入
        electronicTags.add(electronicTag1);
        electronicTags.add(electronicTag2);
        electronicTagDao.insertBatch(electronicTags);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteElectronicTag.xml")
    public void deleteByGateway() {
        electronicTagDao.deleteByGateway("bfcc0e82ea5d43718934bacd88991f75");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
