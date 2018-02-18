/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-dao
 * 文件名： StoreDaoTest.java
 * 模块说明：
 * 修改历史：
 * 2016-8-9 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.material;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.material.Store;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;

/**
 * @author xiepingping
 * 
 */
@DatabaseSetup({
  "store/InitData.xml" })
public class StoreDaoTest extends AbstractDataAccessTests {

  @Autowired
  private StoreDao storeDao;

  @Test
  public void getByUuid() {
    Store store = storeDao.getByUuid("1001");

    assertNotNull(store);
    Assert.assertEquals("1001", store.getUuid());
    Assert.assertEquals("S001", store.getCode());
    Assert.assertEquals("门店1", store.getName());
    Assert.assertEquals("地址1", store.getAddress());
    Assert.assertEquals("备注1", store.getRemark());
  }

  @Test
  public void getByCode() {
    Store store = storeDao.getByCode("S001");

    assertNotNull(store);
    Assert.assertEquals("1001", store.getUuid());
    Assert.assertEquals("S001", store.getCode());
    Assert.assertEquals("门店1", store.getName());
    Assert.assertEquals("地址1", store.getAddress());
    Assert.assertEquals("备注1", store.getRemark());
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "store/DeleteStore.xml")
  public void delete() {
    storeDao.delete("1001");
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "store/InsertStore.xml")
  public void insert() throws ParseException {
    Store store = new Store();
    store.setUuid("1004");
    store.setCode("S004");
    store.setName("门店4");
    store.setAddress("地址4");
    store.setRemark("备注4");

    storeDao.insert(store);
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "store/UpdateStore.xml")
  public void update() throws ParseException {
    Store store = storeDao.getByCode("S003");
    assertNotNull(store);

    store.setUuid("1003");
    store.setCode("S003改");
    store.setName("门店3改");
    store.setAddress("地址3改");
    store.setRemark("备注3改");
    storeDao.update(store);
  }

  @Test
  public void querypage() {
    PageQueryDefinition filter = new PageQueryDefinition();
    int page = 1;
    int pageSize = 2;
    filter.setPage(page);
    filter.setPageSize(pageSize);

    List<Store> stores = storeDao.queryByPage(filter);
    Assert.assertEquals(2, stores.size());
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "store/InsertBatchStore.xml")
  public void insertBatch() throws ParseException {
    List<Store> stores = new ArrayList<Store>();
    Store store1 = new Store();
    store1.setUuid("1004");
    store1.setCode("S004");
    store1.setName("门店4");
    store1.setAddress("地址4");
    store1.setRemark("备注4");
    stores.add(store1);

    Store store2 = new Store();
    store2.setUuid("1005");
    store2.setCode("S005");
    store2.setName("门店5");
    store2.setAddress("地址5");
    store2.setRemark("备注5");
    stores.add(store2);
    storeDao.insertBatch(stores);
  }

}
