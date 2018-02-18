/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	ElectronicTagDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.gateway.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.gateway.ElectronicTag;
import com.hd123.hema.store.dao.facility.gateway.ElectronicTagDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public class ElectronicTagDaoImpl extends SqlSessionDaoSupport implements ElectronicTagDao {

  @Override
  public int insert(ElectronicTag t) {
    return getSqlSession().getMapper(ElectronicTagDao.class).insert(t);
  }

  @Override
  public int update(ElectronicTag t) {
    return getSqlSession().getMapper(ElectronicTagDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(ElectronicTagDao.class).delete(uuid);
  }

  @Override
  public ElectronicTag getByUuid(String uuid) {
    return getSqlSession().getMapper(ElectronicTagDao.class).getByUuid(uuid);
  }

  @Override
  public List<ElectronicTag> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(ElectronicTagDao.class).queryByPage(params);
  }

  @Override
  public List<ElectronicTag> queryByList(QueryParam params) {
    return getSqlSession().getMapper(ElectronicTagDao.class).queryByList(params);
  }

  @Override
  public void insertBatch(List<ElectronicTag> eleTags) {
    getSqlSession().getMapper(ElectronicTagDao.class).insertBatch(eleTags);
  }

  @Override
  public void deleteByGateway(String gatewayUuid) {
    getSqlSession().getMapper(ElectronicTagDao.class).deleteByGateway(gatewayUuid);
  }

}
