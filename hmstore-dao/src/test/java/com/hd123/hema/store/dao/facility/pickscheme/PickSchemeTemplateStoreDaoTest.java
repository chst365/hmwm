package com.hd123.hema.store.dao.facility.pickscheme;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeTemplateStore;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.rumba.commons.biz.entity.UCN;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DatabaseSetup({"./InitPickSchemeTemplateStore.xml" })
public class PickSchemeTemplateStoreDaoTest extends AbstractDataAccessTests {
    @Autowired
    private PickSchemeTemplateStoreDao pickSchemeTemplateStoreDao;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertPickSchemeTemplateStore.xml")
    public void insert() {
        PickSchemeTemplateStore pickSchemeTemplateStore = new PickSchemeTemplateStore();
        pickSchemeTemplateStore.setUuid("09a0248e81e64c3dbc2afa3d2b1f3f4c");
        UCN store = new UCN();
        store.setUuid("a80031c886f8436c9730e265b2dcfbc8");
        store.setCode("x008");
        store.setName("S001");
        pickSchemeTemplateStore.setStore(store);
        pickSchemeTemplateStore.setPickSchemeTemplateUuid("2cac64edeeae4a31be71a80935faa841");
        pickSchemeTemplateStoreDao.insert(pickSchemeTemplateStore);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdatePickSchemeTemplateStore.xml")
    public void update() {
        PickSchemeTemplateStore pickSchemeTemplateStore = pickSchemeTemplateStoreDao.getByUuid("04dbbb5cbe2d437eae6be41df42650e8");
        UCN store = pickSchemeTemplateStore.getStore();
        store.setUuid("a80031c886f8436c9730e265b2dcfbc8");
        store.setCode("x008");
        store.setName("S001");
        pickSchemeTemplateStore.setStore(store);
        pickSchemeTemplateStoreDao.update(pickSchemeTemplateStore);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeletePickSchemeTemplateStore.xml")
    public void delete() {
        pickSchemeTemplateStoreDao.delete("04dbbb5cbe2d437eae6be41df42650e8");
    }

    @Test
    public void getByUuid() {
        PickSchemeTemplateStore pickSchemeTemplateStore = pickSchemeTemplateStoreDao.getByUuid("003550c083fc4269b389ab69cc0c1e3f");
        Assert.assertEquals("003550c083fc4269b389ab69cc0c1e3f", pickSchemeTemplateStore.getUuid());
        Assert.assertEquals("a80031c886f8436c9730e265b2dcfbc8", pickSchemeTemplateStore.getStore().getUuid());
        Assert.assertEquals("平平陆家嘴的店", pickSchemeTemplateStore.getStore().getCode());
        Assert.assertEquals("x008", pickSchemeTemplateStore.getStore().getName());
        Assert.assertEquals("d483021ca13a4e07be6236431ca90459", pickSchemeTemplateStore.getPickSchemeTemplateUuid());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<PickSchemeTemplateStore> list = pickSchemeTemplateStoreDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<PickSchemeTemplateStore> list = pickSchemeTemplateStoreDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchPickSchemeTemplateStore.xml")
    public void insertBatch() {
        List<PickSchemeTemplateStore> pickSchemeTemplateStores = new ArrayList<>();

        PickSchemeTemplateStore pickSchemeTemplateStore1 = new PickSchemeTemplateStore();
        pickSchemeTemplateStore1.setUuid("09a0248e81e64c3dbc2afa3d2b1f3f4c");
        UCN store1 = new UCN();
        store1.setUuid("a80031c886f8436c9730e265b2dcfbc8");
        store1.setCode("x008");
        store1.setName("S001");
        pickSchemeTemplateStore1.setStore(store1);
        pickSchemeTemplateStore1.setPickSchemeTemplateUuid("2cac64edeeae4a31be71a80935faa841");

        PickSchemeTemplateStore pickSchemeTemplateStore2 = new PickSchemeTemplateStore();
        pickSchemeTemplateStore2.setUuid("db3497840f234179b5d453301d58fec1");
        UCN store2 = new UCN();
        store2.setUuid("33f4ea23d2c149b2b650e1cc9232e77f");
        store2.setCode("徐汇店");
        store2.setName("S002");
        pickSchemeTemplateStore2.setStore(store2);
        pickSchemeTemplateStore2.setPickSchemeTemplateUuid("876d7b96a8fc4320bdb03bc97199d38a");

        //hsqldb不支持批量插入
        pickSchemeTemplateStores.add(pickSchemeTemplateStore1);
        pickSchemeTemplateStores.add(pickSchemeTemplateStore2);
        pickSchemeTemplateStoreDao.insertBatch(pickSchemeTemplateStores);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeletePickSchemeTemplateStore.xml")
    public void deleteByTemplate() {
        pickSchemeTemplateStoreDao.deleteByTemplate("0f5a2dd95d34441c9b48e91725de3843");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
