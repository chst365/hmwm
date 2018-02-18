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
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.material.StoreHost;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
@DatabaseSetup({
  "storehost/InitData.xml" })
public class StoreHostDaoTest extends AbstractDataAccessTests {

  @Autowired
  private StoreHostDao storeHostDao;

  @Test
  public void getByUuid() {
    StoreHost storeHost = storeHostDao.getByUuid("1001");

    assertNotNull(storeHost);
    Assert.assertEquals("1001", storeHost.getUuid());
    Assert.assertEquals("门店主机1", storeHost.getName());
    Assert.assertEquals("ip地址1", storeHost.getIp());
    Assert.assertEquals("mac地址1", storeHost.getMacAddress());
    Assert.assertEquals(true, storeHost.isAllowAccess());
    Assert.assertEquals("uuid门店1", storeHost.getStoreUuid());
  }

  @Test
  public void trans() {
    System.out.println(Boolean.valueOf(false));
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "storehost/DeleteStoreHost.xml")
  public void delete() {
    storeHostDao.delete("1001");
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "storehost/InsertStoreHost.xml")
  public void insert() throws ParseException {
    StoreHost storeHost = new StoreHost();
    storeHost.setUuid("1004");
    storeHost.setName("门店主机4");
    storeHost.setIp("ip地址4");
    storeHost.setMacAddress("mac地址4");
    storeHost.setAllowAccess(false);
    storeHost.setStoreUuid("uuid门店4");

    storeHostDao.insert(storeHost);
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "storehost/UpdateStoreHost.xml")
  public void update() throws ParseException {
    StoreHost storeHost = storeHostDao.getByUuid("1003");
    assertNotNull(storeHost);

    storeHost.setUuid("1003");
    storeHost.setName("门店主机3改");
    storeHost.setIp("ip改");
    storeHost.setMacAddress("mac地址3改");
    storeHost.setAllowAccess(false);
    storeHostDao.update(storeHost);
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "storehost/QueryListStoreHost.xml")
  public void querylist() {
    QueryParam param = new QueryParam();
    param.put("orgUuid", "uuid门店1");
    storeHostDao.queryByList(param);
  }

  @Test
  public void querypage() {
    PageQueryDefinition filter = new PageQueryDefinition();
    int page = 1;
    int pageSize = 2;
    filter.setPage(page);
    filter.setPageSize(pageSize);

    List<StoreHost> storehosts = storeHostDao.queryByPage(filter);
    Assert.assertEquals(2, storehosts.size());
  }

}
