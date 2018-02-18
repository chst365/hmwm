/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	FacilityTemplateDao.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.template;

import java.util.List;

import com.hd123.hema.store.bean.facility.template.FacilityTemplate;
import com.hd123.wms.antman.common.daosupport.BaseDao;

/**
 * @author xiepingping
 *
 */
public interface FacilityTemplateDao extends BaseDao<FacilityTemplate> {

  public FacilityTemplate getByCode(String code);

  public void insertBatch(List<FacilityTemplate> templates);

}
