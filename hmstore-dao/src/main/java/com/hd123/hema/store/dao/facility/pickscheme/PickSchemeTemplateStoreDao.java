/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-dao
 * 文件名：	PickSchemeTemplateStoreDao.java
 * 模块说明：
 * 修改历史：
 * 2016-6-16 - xiepingping - 创建。
 */
package com.hd123.hema.store.dao.facility.pickscheme;

import java.util.List;

import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeTemplateStore;
import com.hd123.wms.antman.common.daosupport.BaseDao;

/**
 * @author xiepingping
 *
 */
public interface PickSchemeTemplateStoreDao extends BaseDao<PickSchemeTemplateStore> {

  public void insertBatch(List<PickSchemeTemplateStore> templateStores);

  public void deleteByTemplate(String pickSchemeTemplateUuid);
}
