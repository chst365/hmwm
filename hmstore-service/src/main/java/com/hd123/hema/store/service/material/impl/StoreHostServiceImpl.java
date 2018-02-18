/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	StoreHostServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-17 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.material.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.material.StoreHost;
import com.hd123.hema.store.dao.material.StoreHostDao;
import com.hd123.hema.store.service.material.StoreHostService;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.UUIDGenerator;

/**
 * @author xiepingping
 * 
 */
@Service
public class StoreHostServiceImpl implements StoreHostService {
  

  @Autowired
  private StoreHostDao storeHostDao;

  @Override
  public String insert(StoreHost storeHost) {
    Assert.assertArgumentNotNull(storeHost, "storeHost");
    storeHost.validate();

    validByUniqueKey(storeHost);

    storeHost.setUuid(UUIDGenerator.genUUID());
    storeHostDao.insert(storeHost);
    //----重置其他主机api
    if(storeHost.isAllowAccess()){
      List<StoreHost> list=queryByList(storeHost.getStoreUuid());
      if (CollectionUtils.isEmpty(list))
        throw new IllegalArgumentException("重置主机api时，查询主机列表为空");
      for(StoreHost sh:list){
        if(sh.isAllowAccess()){
          if(!sh.getUuid().equals(storeHost.getUuid())){
            sh.setAllowAccess(false);
            storeHostDao.update(sh);
          }
        }
      }
    }
    //end----重置主机api
    

    return storeHost.getUuid();
  }

  private void validByUniqueKey(StoreHost storeHost) {
    List<StoreHost> dbDatas = queryByList(storeHost.getStoreUuid());
    for (StoreHost dbsh : dbDatas) {
      if (dbsh.getIp().equals(storeHost.getIp())
          && dbsh.getStoreUuid().equals(storeHost.getStoreUuid())
          && dbsh.getUuid().equals(storeHost.getUuid()) == false)
        throw new IllegalArgumentException("已存在IP为" + storeHost.getIp() + "的主机");
      if (dbsh.getMacAddress().equals(storeHost.getMacAddress())
          && dbsh.getStoreUuid().equals(storeHost.getStoreUuid())
          && dbsh.getUuid().equals(storeHost.getUuid()) == false)
        throw new IllegalArgumentException("已存在MAC地址为" + storeHost.getIp() + "的主机");
    }
  }

  @Override
  public void update(StoreHost storeHost) {
    Assert.assertArgumentNotNull(storeHost, "storeHost");
    storeHost.validate();

    StoreHost dbStoreHost = storeHostDao.getByUuid(storeHost.getUuid());
    if (dbStoreHost == null)
      throw new IllegalArgumentException("商品不存在");

    validByUniqueKey(storeHost);

    storeHostDao.update(storeHost);
    
    //----重置其他主机api
    if(storeHost.isAllowAccess()){
      List<StoreHost> list=queryByList(storeHost.getStoreUuid());
      if (CollectionUtils.isEmpty(list))
        throw new IllegalArgumentException("重置主机api时，查询主机列表为空");
      for(StoreHost sh:list){
        if(sh.isAllowAccess()){
          if(!sh.getUuid().equals(storeHost.getUuid())){
            sh.setAllowAccess(false);
            storeHostDao.update(sh);
          }
        }
      }
    }
    //end----重置主机api
    
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    storeHostDao.delete(uuid);
  }

  @Override
  public StoreHost getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    return storeHostDao.getByUuid(uuid);
  }

  @Override
  public List<StoreHost> queryByList(String orgUuid) {
    if (StringUtils.isEmpty(orgUuid))
      return new ArrayList<StoreHost>();

    QueryParam param = new QueryParam();
    param.put("orgUuid", orgUuid);
    List<StoreHost> list = storeHostDao.queryByList(param);
    return list;
  }

}
