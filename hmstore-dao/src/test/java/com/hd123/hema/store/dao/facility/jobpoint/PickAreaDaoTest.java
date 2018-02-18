package com.hd123.hema.store.dao.facility.jobpoint;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.jobpoint.PickArea;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DatabaseSetup({"./InitPickArea.xml" })
public class PickAreaDaoTest extends AbstractDataAccessTests {
    @Autowired
    private PickAreaDao pickAreaDao;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertPickArea.xml")
    public void insert() {
        PickArea pickArea = new PickArea();
        pickArea.setUuid("2287e2a2ada741cbbbaeeb7d960b8574");
        pickArea.setCode("ckj分区");
        pickArea.setName("ckj分区");
        pickArea.setJobPointUuid("d387f80e17c24626b22b89f802cb2eb8");
        pickAreaDao.insert(pickArea);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdatePickArea.xml")
    public void update() {
        PickArea pickArea = pickAreaDao.getByUuid("0ac9fd3b7bca4927a7bb3bc319e37893");
        pickArea.setCode("ckj分区");
        pickArea.setName("ckj分区");
        pickAreaDao.update(pickArea);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeletePickArea.xml")
    public void delete() {
        pickAreaDao.delete("0ac9fd3b7bca4927a7bb3bc319e37893");
    }

    @Test
    public void getByUuid() {
        PickArea pickArea = pickAreaDao.getByUuid("04df1abfc07b4153aa2398fb2d42ee88");
        Assert.assertEquals("04df1abfc07b4153aa2398fb2d42ee88", pickArea.getUuid());
        Assert.assertEquals("ckj总部分区", pickArea.getCode());
        Assert.assertEquals("ckj总部分区", pickArea.getName());
        Assert.assertEquals("6331567bc428443e8c5dc2e7945268db", pickArea.getJobPointUuid());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<PickArea> list = pickAreaDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<PickArea> list = pickAreaDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchPickArea.xml")
    public void insertBatch() {
        List<PickArea> pickAreas = new ArrayList<>();

        PickArea pickArea1 = new PickArea();
        pickArea1.setUuid("2287e2a2ada741cbbbaeeb7d960b8574");
        pickArea1.setCode("ckj分区");
        pickArea1.setName("ckj分区");
        pickArea1.setJobPointUuid("d387f80e17c24626b22b89f802cb2eb8");

        PickArea pickArea2 = new PickArea();
        pickArea2.setUuid("0d5d1dabe1ab412ab283e9edcc7be24c");
        pickArea2.setCode("dedd");
        pickArea2.setName("额");
        pickArea2.setJobPointUuid("015c297d92844da095904a91ff12031b");

        //hsqldb不支持批量插入
        pickAreas.add(pickArea1);
        pickAreas.add(pickArea2);
        pickAreaDao.insertBatch(pickAreas);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeletePickArea.xml")
    public void deleteByJobPoint() {
        pickAreaDao.deleteByJobPoint("ef1d22411fa543d099fd87f3791c8714");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
