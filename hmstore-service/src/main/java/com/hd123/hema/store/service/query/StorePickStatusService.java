package com.hd123.hema.store.service.query;

import java.util.List;

import com.hd123.hema.store.bean.query.SStorePickStatus;
import com.hd123.hema.store.bean.query.StorePickStatus;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public interface StorePickStatusService {

  List<StorePickStatus> queryByList(QueryParam param);

  void receivePickStatus(List<SStorePickStatus> status);
}