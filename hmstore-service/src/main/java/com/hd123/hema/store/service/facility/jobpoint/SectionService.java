/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	SectionService.java
 * 模块说明：
 * 修改历史：
 * 2016-6-27 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.jobpoint;

import java.util.List;

import com.hd123.hema.store.bean.facility.jobpoint.Section;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 *
 */
public interface SectionService {
  String insert(Section section);

  void insertBatch(List<Section> sections);

  void update(Section section);

  void delete(String uuid);

  void deleteByPickArea(String pickAreaUuid);

  Section getByUuid(String uuid);

  List<Section> queryByList(QueryParam param);
}
