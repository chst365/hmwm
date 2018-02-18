/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	BinEleTagDao.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.jobpoint;

import java.util.List;

import com.hd123.hema.store.bean.facility.jobpoint.BinEleTag;
import com.hd123.wms.antman.common.daosupport.BaseDao;

/**
 * @author xiepingping
 *
 */
public interface BinEleTagDao extends BaseDao<BinEleTag> {

  public void insertBatch(List<BinEleTag> binEleTags);

  public void deleteBySection(String sectionUuid);
}
