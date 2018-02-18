/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	StoreDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.material.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.material.Store;
import com.hd123.hema.store.dao.material.StoreDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class StoreDaoImpl extends SqlSessionDaoSupport implements StoreDao {

  @Override
  public int insert(Store t) {
    return getSqlSession().getMapper(StoreDao.class).insert(t);
  }

  @Override
  public int update(Store t) {
    return getSqlSession().getMapper(StoreDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(StoreDao.class).delete(uuid);
  }

  @Override
  public Store getByUuid(String uuid) {
    return getSqlSession().getMapper(StoreDao.class).getByUuid(uuid);
  }

  @Override
  public List<Store> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(StoreDao.class).queryByPage(params);
  }

  @Override
  public List<Store> queryByList(QueryParam params) {
    return getSqlSession().getMapper(StoreDao.class).queryByList(params);
  }

  @Override
  public Store getByCode(String code) {
    return getSqlSession().getMapper(StoreDao.class).getByCode(code);
  }

  @Override
  public void insertBatch(List<Store> stores) {
    getSqlSession().getMapper(StoreDao.class).insertBatch(stores);
  }

}
