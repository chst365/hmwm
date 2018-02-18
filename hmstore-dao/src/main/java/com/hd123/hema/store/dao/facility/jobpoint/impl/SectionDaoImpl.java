/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	SectionDaoImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.jobpoint.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hd123.hema.store.bean.facility.jobpoint.Section;
import com.hd123.hema.store.dao.facility.jobpoint.SectionDao;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
public class SectionDaoImpl extends SqlSessionDaoSupport implements SectionDao {

  @Override
  public int insert(Section t) {
    return getSqlSession().getMapper(SectionDao.class).insert(t);
  }

  @Override
  public int update(Section t) {
    return getSqlSession().getMapper(SectionDao.class).update(t);
  }

  @Override
  public int delete(String uuid) {
    return getSqlSession().getMapper(SectionDao.class).delete(uuid);
  }

  @Override
  public Section getByUuid(String uuid) {
    return getSqlSession().getMapper(SectionDao.class).getByUuid(uuid);
  }

  @Override
  public List<Section> queryByPage(PageQueryDefinition params) {
    return getSqlSession().getMapper(SectionDao.class).queryByPage(params);
  }

  @Override
  public List<Section> queryByList(QueryParam params) {
    return getSqlSession().getMapper(SectionDao.class).queryByList(params);
  }

  @Override
  public void insertBatch(List<Section> sections) {
    getSqlSession().getMapper(SectionDao.class).insertBatch(sections);
  }

  @Override
  public void deleteByPickArea(String pickAreaUuid) {
    getSqlSession().getMapper(SectionDao.class).deleteByPickArea(pickAreaUuid);
  }

}
