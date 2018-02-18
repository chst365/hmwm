/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	SectionEleTagDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.jobpoint.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.jobpoint.SectionEleTag;
import com.hd123.hema.store.dao.facility.jobpoint.SectionEleTagDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public class SectionEleTagDaoImpl extends SqlSessionDaoSupport implements SectionEleTagDao {

  @Override
  public int insert(SectionEleTag t) {
    return getSqlSession().getMapper(SectionEleTagDao.class).insert(t);
  }

  @Override
  public int update(SectionEleTag t) {
    return getSqlSession().getMapper(SectionEleTagDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(SectionEleTagDao.class).delete(uuid);
  }

  @Override
  public SectionEleTag getByUuid(String uuid) {
    return getSqlSession().getMapper(SectionEleTagDao.class).getByUuid(uuid);
  }

  @Override
  public List<SectionEleTag> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(SectionEleTagDao.class).queryByPage(params);
  }

  @Override
  public List<SectionEleTag> queryByList(QueryParam params) {
    return getSqlSession().getMapper(SectionEleTagDao.class).queryByList(params);
  }

  @Override
  public void insertBatch(List<SectionEleTag> sectionEleTags) {
    getSqlSession().getMapper(SectionEleTagDao.class).insertBatch(sectionEleTags);
  }

  @Override
  public void deleteBySection(String sectionUuid) {
    getSqlSession().getMapper(SectionEleTagDao.class).deleteBySection(sectionUuid);
  }

}
