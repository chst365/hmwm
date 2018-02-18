/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	PickAreaDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.jobpoint.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.jobpoint.PickArea;
import com.hd123.hema.store.dao.facility.jobpoint.PickAreaDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public class PickAreaDaoImpl extends SqlSessionDaoSupport implements PickAreaDao {

  @Override
  public int insert(PickArea t) {
    return getSqlSession().getMapper(PickAreaDao.class).insert(t);
  }

  @Override
  public int update(PickArea t) {
    return getSqlSession().getMapper(PickAreaDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(PickAreaDao.class).delete(uuid);
  }

  @Override
  public PickArea getByUuid(String uuid) {
    return getSqlSession().getMapper(PickAreaDao.class).getByUuid(uuid);
  }

  @Override
  public List<PickArea> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(PickAreaDao.class).queryByPage(params);
  }

  @Override
  public List<PickArea> queryByList(QueryParam params) {
    return getSqlSession().getMapper(PickAreaDao.class).queryByList(params);
  }

  @Override
  public void insertBatch(List<PickArea> jobPoints) {
    getSqlSession().getMapper(PickAreaDao.class).insertBatch(jobPoints);
  }

  @Override
  public void deleteByJobPoint(String jobPointUuid) {
    getSqlSession().getMapper(PickAreaDao.class).deleteByJobPoint(jobPointUuid);
  }

}
