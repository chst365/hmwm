package com.hd123.hema.store.dao.query;

import java.util.List;

import com.hd123.hema.store.bean.query.SStorePickStatus;
import com.hd123.hema.store.bean.query.StorePickStatus;
import com.hd123.wms.antman.common.daosupport.BaseDao;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public interface StorePickStatusDao extends BaseDao<StorePickStatus> {

  public List<StorePickStatus> queryByListStore(QueryParam param);

  public void updatePickStatus(SStorePickStatus sStorePickStatus);
}