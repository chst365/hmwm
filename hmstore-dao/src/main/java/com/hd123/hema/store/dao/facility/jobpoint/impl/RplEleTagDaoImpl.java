/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	RplEleTagDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.jobpoint.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.jobpoint.RplEleTag;
import com.hd123.hema.store.dao.facility.jobpoint.RplEleTagDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public class RplEleTagDaoImpl extends SqlSessionDaoSupport implements RplEleTagDao {

  @Override
  public int insert(RplEleTag t) {
    return getSqlSession().getMapper(RplEleTagDao.class).insert(t);
  }

  @Override
  public int update(RplEleTag t) {
    return getSqlSession().getMapper(RplEleTagDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(RplEleTagDao.class).delete(uuid);
  }

  @Override
  public RplEleTag getByUuid(String uuid) {
    return getSqlSession().getMapper(RplEleTagDao.class).getByUuid(uuid);
  }

  @Override
  public List<RplEleTag> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(RplEleTagDao.class).queryByPage(params);
  }

  @Override
  public List<RplEleTag> queryByList(QueryParam params) {
    return getSqlSession().getMapper(RplEleTagDao.class).queryByList(params);
  }

  @Override
  public void insertBatch(List<RplEleTag> rplEleTags) {
    getSqlSession().getMapper(RplEleTagDao.class).insertBatch(rplEleTags);
  }

  @Override
  public void deleteBySection(String sectionUuid) {
    getSqlSession().getMapper(RplEleTagDao.class).deleteBySection(sectionUuid);
  }

}
