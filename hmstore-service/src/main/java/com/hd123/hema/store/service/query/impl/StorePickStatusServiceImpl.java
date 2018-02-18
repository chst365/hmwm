package com.hd123.hema.store.service.query.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.query.SStorePickStatus;
import com.hd123.hema.store.bean.query.StorePickStatus;
import com.hd123.hema.store.dao.query.StorePickStatusDao;
import com.hd123.hema.store.service.query.StorePickStatusService;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class StorePickStatusServiceImpl implements StorePickStatusService {

  @Autowired
  private StorePickStatusDao statusDao;

  @Override
  public List<StorePickStatus> queryByList(QueryParam param) {
    if (StringUtils.isEmpty(param))
      return null;

    return statusDao.queryByList(param);
  }

  @Override
  public void receivePickStatus(List<SStorePickStatus> statuss) {
    Assert.assertArgumentNotNull(statuss, "statuss");

    for (SStorePickStatus status : statuss)
      statusDao.updatePickStatus(status);

  }
}