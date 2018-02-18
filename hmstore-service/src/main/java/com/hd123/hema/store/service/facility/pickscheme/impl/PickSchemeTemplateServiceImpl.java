/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	PickSchemeTemplateServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-29 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.pickscheme.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.hema.store.bean.facility.pickscheme.BinArticle;
import com.hd123.hema.store.bean.facility.pickscheme.PickScheme;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeState;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeTemplate;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeTemplateStore;
import com.hd123.hema.store.dao.facility.pickscheme.BinArticleDao;
import com.hd123.hema.store.dao.facility.pickscheme.PickSchemeTemplateDao;
import com.hd123.hema.store.dao.facility.pickscheme.PickSchemeTemplateStoreDao;
import com.hd123.hema.store.service.facility.jobpoint.JobPointService;
import com.hd123.hema.store.service.facility.pickscheme.PickSchemeService;
import com.hd123.hema.store.service.facility.pickscheme.PickSchemeTemplateService;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.bean.UCN;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.ListUtils;
import com.hd123.wms.antman.common.utils.UUIDGenerator;

/**
 * @author xiepingping
 * 
 */
public class PickSchemeTemplateServiceImpl implements PickSchemeTemplateService {

  @Autowired
  private PickSchemeTemplateDao templateDao;
  @Autowired
  private BinArticleDao binArticleDao;
  @Autowired
  private PickSchemeTemplateStoreDao templateStoreDao;
  @Autowired
  private PickSchemeService pickSchemeService;
  @Autowired
  private JobPointService jobPointService;

  @Override
  public String insert(PickSchemeTemplate template) {
    Assert.assertArgumentNotNull(template, "pickSchemeTemplate");

    template.validate();
    validByUniqueKey(template.getCode(), template.getOrgUuid(), null);

    template.setUuid(UUIDGenerator.genUUID());
    template.setVersionTime(new Date());
    templateDao.insert(template);
    insertItems(template);

    return template.getUuid();
  }

  private void insertItems(PickSchemeTemplate template) {
    for (BinArticle binArticle : template.getBinArticle()) {
      binArticle.setPickSchemeUuid(template.getUuid());
      binArticle.setUuid(UUIDGenerator.genUUID());
    }
    for (PickSchemeTemplateStore templateStore : template.getPickSchemeStores()) {
      templateStore.setPickSchemeTemplateUuid(template.getUuid());
      templateStore.setUuid(UUIDGenerator.genUUID());
    }

    if (CollectionUtils.isEmpty(template.getBinArticle()) == false)
      binArticleDao.insertBatch(template.getBinArticle());
    if (CollectionUtils.isEmpty(template.getPickSchemeStores()) == false)
      templateStoreDao.insertBatch(template.getPickSchemeStores());
  }

  @Override
  public void insertBatch(List<PickSchemeTemplate> templates) {
    Assert.assertArgumentNotNull(templates, "templates");

    for (int i = 0; i < templates.size(); i++) {
      PickScheme iPickScheme = templates.get(i);
      for (int j = i + 1; j < templates.size(); j++) {
        PickScheme jPickScheme = templates.get(j);
        if (iPickScheme.getCode().equals(jPickScheme.getCode())
            && iPickScheme.getOrgUuid().equals(jPickScheme.getOrgUuid()))
          throw new IllegalArgumentException("分拣方案模板第" + (i + 1) + "行与第" + (j + 1) + "行代码重复。");
      }
    }

    for (PickSchemeTemplate template : templates) {
      Assert.assertArgumentNotNull(template, "PickSchemeTemplate");
      template.validate();

      validByUniqueKey(template.getCode(), template.getOrgUuid(), null);

      template.setUuid(UUIDGenerator.genUUID());
      template.setVersionTime(new Date());

      insertItems(template);
    }

    for (List<PickSchemeTemplate> list : ListUtils.splitList(templates, 1000))
      templateDao.insertBatch(list);
  }

  @Override
  public void update(PickSchemeTemplate template) {
    Assert.assertArgumentNotNull(template, "pickSchemeTemplate");

    template.validate();
    PickSchemeTemplate dbPickSchemeTemplate = templateDao.getByUuid(template.getUuid());
    if (dbPickSchemeTemplate == null)
      throw new IllegalArgumentException("分拣方案模板不存在");
    if (dbPickSchemeTemplate.getVersion() != template.getVersion())
      throw new IllegalArgumentException("分拣方案已被其他人修改");

    validByUniqueKey(template.getCode(), template.getOrgUuid(), template.getUuid());

    template.setVersionTime(new Date());
    templateDao.update(template);

    deleteItems(template.getUuid());
    insertItems(template);
  }

  private void deleteItems(String uuid) {
    assert uuid != null;

    binArticleDao.deleteByPickScheme(uuid);
    templateStoreDao.deleteByTemplate(uuid);
  }

  private void validByUniqueKey(String code, String orgUuid, String uuid) {
    assert code != null;

    PickSchemeTemplate dbPickSchemeTemplate = templateDao.getByCode(code, orgUuid);
    if (dbPickSchemeTemplate != null && dbPickSchemeTemplate.getUuid().equals(uuid) == false)
      throw new IllegalArgumentException("已存在代码为" + code + "的分拣方案模板");
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    PickSchemeTemplate template = getByUuid(uuid);
    if (template == null)
      return;

    templateDao.delete(uuid);
    deleteItems(uuid);
  }

  @Override
  public PickSchemeTemplate getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    PickSchemeTemplate template = templateDao.getByUuid(uuid);
    setItems(template);

    return template;
  }

  private void setItems(PickSchemeTemplate template) {
    QueryParam param = new QueryParam();
    param.put("pickSchemeUuid", template.getUuid());
    template.setBinArticle(binArticleDao.queryByList(param));
    template.setPickSchemeStores(templateStoreDao.queryByList(param));
  }

  @Override
  public List<PickSchemeTemplate> queryByList(QueryParam param) {
    return templateDao.queryByList(param);
  }

  @Override
  public void distribute(String uuid) {
    Assert.assertArgumentNotNull(uuid, "uuid");

    PickSchemeTemplate template = getByUuid(uuid);
    if (template == null)
      throw new IllegalArgumentException("分拣方案模板不存在");

    List<PickScheme> pickSchemes = convertTemplate(template);
    pickSchemeService.insertBatch(pickSchemes);
  }

  private List<PickScheme> convertTemplate(PickSchemeTemplate template) {
    assert template != null;

    List<PickScheme> result = new ArrayList<PickScheme>();

    for (PickSchemeTemplateStore templateStore : template.getPickSchemeStores()) {
      QueryParam param = new QueryParam();
      param.put("orgUuid", templateStore.getStore().getUuid());
      List<JobPoint> jobPoints = jobPointService.queryByList(param);
      for (JobPoint jobPoint : jobPoints) {
        PickScheme target = new PickScheme();
        target.setCode(template.getCode());
        target.setName(template.getName());
        target.setOrgUuid(templateStore.getStore().getUuid());
        target.setState(PickSchemeState.UnEffective);
        target.setVersion(template.getVersion());
        target.setVersionTime(template.getVersionTime());
        target.setBinArticle(template.getBinArticle());
        target.setJobPoint(new UCN(jobPoint.getUuid(), jobPoint.getCode(), jobPoint.getName()));

        result.add(target);
      }
    }

    return result;
  }

}
