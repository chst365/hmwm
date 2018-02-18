/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	StoreServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.material.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeTemplateStore;
import com.hd123.hema.store.bean.material.Store;
import com.hd123.hema.store.bean.material.StoreHost;
import com.hd123.hema.store.dao.facility.gateway.GatewayDao;
import com.hd123.hema.store.dao.facility.jobpoint.JobPointDao;
import com.hd123.hema.store.dao.facility.pickscheme.PickSchemeTemplateStoreDao;
import com.hd123.hema.store.dao.material.StoreDao;
import com.hd123.hema.store.dao.material.StoreHostDao;
import com.hd123.hema.store.service.material.StoreService;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.PageQueryResult;
import com.hd123.wms.antman.common.query.PageQueryUtil;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.ListUtils;
import com.hd123.wms.antman.common.utils.UUIDGenerator;
import com.hd123.wms.antman.system.bean.User;
import com.hd123.wms.antman.system.dao.UserDao;

/**
 * @author xiepingping
 * 
 */
public class StoreServiceImpl implements StoreService {

  @Autowired
  private StoreDao storeDao;
  @Autowired
  private StoreHostDao storeHostDao;
  @Autowired
  private PickSchemeTemplateStoreDao schemeTemplateStoreDao;
  @Autowired
  private GatewayDao gatewayDao;
  @Autowired
  private JobPointDao jobPointDao;
  @Autowired
  private UserDao userDao;

  @Override
  public String insert(Store store) {
    Assert.assertArgumentNotNull(store, "store");
    store.validate();

    validByUniqueKey(store.getCode(), null);

    store.setUuid(UUIDGenerator.genUUID());
    storeDao.insert(store);

    return store.getUuid();
  }

  @Override
  public void insertBatch(List<Store> stores) {
    Assert.assertArgumentNotNull(stores, "stores");

    for (int i = 0; i < stores.size(); i++) {
      Store iStore = stores.get(i);
      for (int j = i + 1; j < stores.size(); j++) {
        Store jStore = stores.get(j);
        if (iStore.getCode().equals(jStore.getCode()))
          throw new IllegalArgumentException("商品第" + (i + 1) + "行与第" + (j + 1) + "行代码重复。");
      }
    }

    for (Store store : stores) {
      Assert.assertArgumentNotNull(store, "store");
      store.validate();

      validByUniqueKey(store.getCode(), null);

      store.setUuid(UUIDGenerator.genUUID());
    }

    for (List<Store> list : ListUtils.splitList(stores, 1000))
      storeDao.insertBatch(list);
  }

  @Override
  public void update(Store store) {
    Assert.assertArgumentNotNull(store, "store");
    store.validate();

    Store dbStore = storeDao.getByUuid(store.getUuid());
    if (dbStore == null)
      throw new IllegalArgumentException("门店不存在");

    validByUniqueKey(store.getCode(), store.getUuid());

    storeDao.update(store);
  }

  private void validByUniqueKey(String code, String uuid) {
    assert code != null;

    Store dbStore = storeDao.getByCode(code);
    if (dbStore != null && dbStore.getUuid().equals(uuid) == false)
      throw new IllegalArgumentException("已存在代码为" + code + "的门店");
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    validByForeignKey(uuid);

    storeDao.delete(uuid);
  }

  private void validByForeignKey(String storeUuid) {
    QueryParam params = new QueryParam();
    params.put("orgUuid", storeUuid);
    List<StoreHost> dbsh = storeHostDao.queryByList(params);
    if (CollectionUtils.isEmpty(dbsh) == false)
      throw new IllegalArgumentException("当前门店已经维护门店主机，不能删除");

    QueryParam pTSFilter = new QueryParam();
    pTSFilter.put("storeUuid", storeUuid);
    List<PickSchemeTemplateStore> templateStores = schemeTemplateStoreDao.queryByList(pTSFilter);
    if (CollectionUtils.isEmpty(templateStores) == false)
      throw new IllegalArgumentException("当前门店正在被分拣方案模板使用，不能删除");

    QueryParam gatewayFilter = new QueryParam();
    gatewayFilter.put("orgUuid", storeUuid);
    List<Gateway> gateways = gatewayDao.queryByList(params);
    if (CollectionUtils.isEmpty(gateways) == false)
      throw new IllegalArgumentException("当前门店已经维护网关，不能删除");

    QueryParam jobPointFilter = new QueryParam();
    jobPointFilter.put("orgUuid", storeUuid);
    List<JobPoint> jobPoints = jobPointDao.queryByList(jobPointFilter);
    if (CollectionUtils.isEmpty(jobPoints) == false)
      throw new IllegalArgumentException("当前门店已经维护作业点，不能删除");

    QueryParam userFilter = new QueryParam();
    userFilter.put("tenantUuid", storeUuid);
    List<User> users = userDao.queryByList(userFilter);
    if (CollectionUtils.isEmpty(users) == false)
      throw new IllegalArgumentException("当前门店已经维护员工，不能删除");
  }

  @Override
  public Store getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    return storeDao.getByUuid(uuid);
  }

  @Override
  public PageQueryResult<Store> queryByPage(PageQueryDefinition filter) {
    Assert.assertArgumentNotNull(filter, "filter");

    PageQueryResult<Store> pgr = new PageQueryResult<Store>();
    List<Store> list = storeDao.queryByPage(filter);
    PageQueryUtil.assignPageInfo(pgr, filter);
    pgr.setRecords(list);
    return pgr;
  }

  @Override
  public Store getByCode(String code) {
    if (StringUtils.isEmpty(code))
      return null;

    return storeDao.getByCode(code);
  }

}
