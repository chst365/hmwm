/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	PickAreaDao.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.jobpoint;

import java.util.List;

import com.hd123.hema.store.bean.facility.jobpoint.PickArea;
import com.hd123.wms.antman.common.daosupport.BaseDao;

/**
 * @author xiepingping
 *
 */
public interface PickAreaDao extends BaseDao<PickArea> {

  public void insertBatch(List<PickArea> jobPoints);

  public void deleteByJobPoint(String jobPointUuid);
}