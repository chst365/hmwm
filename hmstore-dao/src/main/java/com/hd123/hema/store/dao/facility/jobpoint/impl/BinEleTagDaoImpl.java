/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	BinEleTagDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.jobpoint.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.jobpoint.BinEleTag;
import com.hd123.hema.store.dao.facility.jobpoint.BinEleTagDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public class BinEleTagDaoImpl extends SqlSessionDaoSupport implements BinEleTagDao {

  @Override
  public int insert(BinEleTag t) {
    return getSqlSession().getMapper(BinEleTagDao.class).insert(t);
  }

  @Override
  public int update(BinEleTag t) {
    return getSqlSession().getMapper(BinEleTagDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(BinEleTagDao.class).delete(uuid);
  }

  @Override
  public BinEleTag getByUuid(String uuid) {
    return getSqlSession().getMapper(BinEleTagDao.class).getByUuid(uuid);
  }

  @Override
  public List<BinEleTag> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(BinEleTagDao.class).queryByPage(params);
  }

  @Override
  public List<BinEleTag> queryByList(QueryParam params) {
    return getSqlSession().getMapper(BinEleTagDao.class).queryByList(params);
  }

  @Override
  public void insertBatch(List<BinEleTag> binEleTags) {
    getSqlSession().getMapper(BinEleTagDao.class).insertBatch(binEleTags);
  }

  @Override
  public void deleteBySection(String sectionUuid) {
    getSqlSession().getMapper(BinEleTagDao.class).deleteBySection(sectionUuid);
  }

}
