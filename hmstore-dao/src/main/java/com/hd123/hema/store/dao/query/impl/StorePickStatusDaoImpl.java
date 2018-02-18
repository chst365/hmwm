package com.hd123.hema.store.dao.query.impl;


import java.util.List;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.query.SStorePickStatus;
import com.hd123.hema.store.bean.query.StorePickStatus;
import com.hd123.hema.store.dao.query.StorePickStatusDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class StorePickStatusDaoImpl extends SqlSessionDaoSupport implements StorePickStatusDao {

  @Override
  public int delete(String arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public StorePickStatus getByUuid(String uuid) {
    // TODO Auto-generated method stub
    return getSqlSession().getMapper(StorePickStatusDao.class).getByUuid(uuid);
  }

  @Override
  public int insert(StorePickStatus arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public List<StorePickStatus> queryByList(QueryParam params) {
    // TODO Auto-generated method stub
    return getSqlSession().getMapper(StorePickStatusDao.class).queryByList(params);
  }

  @Override
  public List<StorePickStatus> queryByPage(PageQueryDefinition params) {
    // TODO Auto-generated method stub
    return getSqlSession().getMapper(StorePickStatusDao.class).queryByPage(params);
  }

  @Override
  public int update(StorePickStatus arg0) {
    // TODO Auto-generated method stub
    return 0;
  }


  @Override
  public List<StorePickStatus> queryByListStore(QueryParam params) {
    // TODO Auto-generated method stub
    return getSqlSession().getMapper(StorePickStatusDao.class).queryByListStore(params);
  }

  @Override
  public void updatePickStatus(SStorePickStatus sStorePickStatus) {
    getSqlSession().getMapper(StorePickStatusDao.class).updatePickStatus(sStorePickStatus);
  }

}