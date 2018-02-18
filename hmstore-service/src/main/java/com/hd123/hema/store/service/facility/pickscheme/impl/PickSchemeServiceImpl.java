/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-service
 * 文件名：	PickSchemeServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2016-6-29 - xiepingping - 创建。
 */
package com.hd123.hema.store.service.facility.pickscheme.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.hd123.hema.store.bean.facility.pickscheme.BinArticle;
import com.hd123.hema.store.bean.facility.pickscheme.PickScheme;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeState;
import com.hd123.hema.store.dao.facility.pickscheme.BinArticleDao;
import com.hd123.hema.store.dao.facility.pickscheme.PickSchemeDao;
import com.hd123.hema.store.service.facility.pickscheme.PickSchemeService;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.ListUtils;
import com.hd123.wms.antman.common.utils.UUIDGenerator;

/**
 * @author xiepingping
 * 
 */
public class PickSchemeServiceImpl implements PickSchemeService {

  @Autowired
  private PickSchemeDao pickSchemeDao;
  @Autowired
  private BinArticleDao binArticleDao;

  @Override
  public String insert(PickScheme pickScheme) {
    Assert.assertArgumentNotNull(pickScheme, "pickScheme");
    pickScheme.validate();

    validByUniqueKey(pickScheme.getCode(), pickScheme.getOrgUuid(), null, pickScheme.getJobPoint()
        .getUuid());

    pickScheme.setUuid(UUIDGenerator.genUUID());
    pickScheme.setVersionTime(new Date());
    pickSchemeDao.insert(pickScheme);
    insertItems(pickScheme);

    return pickScheme.getUuid();
  }

  private void insertItems(PickScheme pickScheme) {
    if (CollectionUtils.isEmpty(pickScheme.getBinArticle()))
      return;

    for (BinArticle binArticle : pickScheme.getBinArticle()) {
      binArticle.setPickSchemeUuid(pickScheme.getUuid());
      binArticle.setUuid(UUIDGenerator.genUUID());
    }

    binArticleDao.insertBatch(pickScheme.getBinArticle());
  }

  @Override
  public void insertBatch(List<PickScheme> pickSchemes) {
    Assert.assertArgumentNotNull(pickSchemes, "pickSchemes");

    for (int i = 0; i < pickSchemes.size(); i++) {
      PickScheme iPickScheme = pickSchemes.get(i);
      for (int j = i + 1; j < pickSchemes.size(); j++) {
        PickScheme jPickScheme = pickSchemes.get(j);
        if (iPickScheme.getCode().equals(jPickScheme.getCode())
            && iPickScheme.getOrgUuid().equals(jPickScheme.getOrgUuid())
            && iPickScheme.getJobPoint().getUuid().equals(jPickScheme.getJobPoint().getUuid()))
          throw new IllegalArgumentException("分拣方案第" + (i + 1) + "行与第" + (j + 1) + "行作业点相同，代码重复。");
      }
    }

    for (PickScheme pickScheme : pickSchemes) {
      Assert.assertArgumentNotNull(pickScheme, "PickSchemeTemplate");
      pickScheme.validate();

      validByUniqueKey(pickScheme.getCode(), pickScheme.getOrgUuid(), null, pickScheme
          .getJobPoint().getUuid());

      pickScheme.setUuid(UUIDGenerator.genUUID());
      pickScheme.setVersionTime(new Date());

      insertItems(pickScheme);
    }

    for (List<PickScheme> list : ListUtils.splitList(pickSchemes, 1000))
      pickSchemeDao.insertBatch(list);
  }

  @Override
  public void update(PickScheme pickScheme) {
    Assert.assertArgumentNotNull(pickScheme, "pickScheme");
    pickScheme.validate();

    PickScheme dbPickScheme = pickSchemeDao.getByUuid(pickScheme.getUuid());
    if (dbPickScheme == null)
      throw new IllegalArgumentException("分拣方案不存在");
    if (dbPickScheme.getVersion() != pickScheme.getVersion())
      throw new IllegalArgumentException("分拣方案已被其他人修改");

    validByUniqueKey(pickScheme.getCode(), pickScheme.getOrgUuid(), pickScheme.getUuid(),
        pickScheme.getJobPoint().getUuid());

    pickScheme.setVersionTime(new Date());
    pickSchemeDao.update(pickScheme);

    deleteItems(pickScheme.getUuid());
    insertItems(pickScheme);
  }

  private void deleteItems(String uuid) {
    assert uuid != null;

    binArticleDao.deleteByPickScheme(uuid);
  }

  private void validByUniqueKey(String code, String orgUuid, String uuid, String jobPointUuid) {
    assert code != null;

    PickScheme dbPickScheme = pickSchemeDao.getByCode(code, orgUuid, jobPointUuid);
    if (dbPickScheme != null && dbPickScheme.getUuid().equals(uuid) == false)
      throw new IllegalArgumentException("已存在相同门店相同作业点下代码为" + code + "的分拣方案");
  }

  @Override
  public void delete(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return;

    PickScheme pickScheme = getByUuid(uuid);
    if (pickScheme == null)
      return;

    pickSchemeDao.delete(uuid);
    deleteItems(uuid);
  }

  @Override
  public PickScheme getByUuid(String uuid) {
    if (StringUtils.isEmpty(uuid))
      return null;

    PickScheme pickScheme = pickSchemeDao.getByUuid(uuid);
    setItems(pickScheme);

    return pickScheme;
  }

  private void setItems(PickScheme pickScheme) {
    if (pickScheme == null)
      return;

    QueryParam param = new QueryParam();
    param.put("pickSchemeUuid", pickScheme.getUuid());
    pickScheme.setBinArticle(binArticleDao.queryByList(param));
  }

  @Override
  public List<PickScheme> queryByList(QueryParam param) {
    List<PickScheme> pickSchemes = pickSchemeDao.queryByList(param);
    for (PickScheme scheme : pickSchemes)
      setItems(scheme);

    return pickSchemes;
  }

  @Override
  public void effective(String uuid) {
    Assert.assertArgumentNotNull(uuid, "uuid");

    PickScheme pickScheme = getByUuid(uuid);

    QueryParam param = new QueryParam();
    param.put("orgUuid", pickScheme.getOrgUuid());
    param.put("state", PickSchemeState.Using.name());
    if (pickScheme.getJobPoint() != null)
      param.put("jobPointUuid", pickScheme.getJobPoint().getUuid());
    List<PickScheme> usingPickSchemes = queryByList(param);
    for (PickScheme usingScheme : usingPickSchemes) {
      usingScheme.setState(PickSchemeState.UnEffective);
      pickSchemeDao.update(usingScheme);
    }

    pickScheme.setState(PickSchemeState.Using);
    pickSchemeDao.update(pickScheme);

  }

  @Override
  public void deleteByOrg(String orgUuid) {
    Assert.assertArgumentNotNull(orgUuid, "orgUuid");

    pickSchemeDao.deleteByOrg(orgUuid);
  }

}
