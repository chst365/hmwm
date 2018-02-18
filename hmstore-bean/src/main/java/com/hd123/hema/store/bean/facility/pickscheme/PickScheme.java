/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	PickScheme.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.pickscheme;

import java.util.ArrayList;
import java.util.List;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.rumba.commons.biz.entity.VersionedEntity;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.bean.UCN;

/**
 * 拣货方案
 * 
 * @author xiepingping
 * 
 */
public class PickScheme extends VersionedEntity implements Validator {
  private static final long serialVersionUID = -5333564323018665361L;

  /** 代码 */
  private String code;
  /** 名称 */
  private String name;
  /** 状态 */
  private PickSchemeState state = PickSchemeState.UnEffective;
  /** 组织UUID */
  private String orgUuid;

  /** 作业点 */
  private UCN jobPoint;
  /** 货位商品明细 */
  private List<BinArticle> binArticle = new ArrayList<BinArticle>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_30, "pickScheme.code");
    Assert.assertArgumentNotNull(code, "article.code");
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_30, "pickScheme.name");
    Assert.assertArgumentNotNull(name, "article.name");
    this.name = name;
  }

  public PickSchemeState getState() {
    return state;
  }

  public void setState(PickSchemeState state) {
    this.state = state;
  }

  public UCN getJobPoint() {
    return jobPoint;
  }

  public void setJobPoint(UCN jobPoint) {
    this.jobPoint = jobPoint;
  }

  public String getOrgUuid() {
    return orgUuid;
  }

  public void setOrgUuid(String orgUuid) {
    Assert.assertStringNotTooLong(orgUuid, FieldLength.LENGTH_32, "pickScheme.orgUuid");
    this.orgUuid = orgUuid;
  }

  public List<BinArticle> getBinArticle() {
    return binArticle;
  }

  public void setBinArticle(List<BinArticle> binArticle) {
    this.binArticle = binArticle;
  }

  @Override
  public void validate() {
    
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_30, "pickScheme.code");
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_30, "pickScheme.name");
    Assert.assertStringNotTooLong(orgUuid, FieldLength.LENGTH_32, "pickScheme.orgUuid");

    Assert.assertArgumentNotNull(code, "article.code");
    Assert.assertArgumentNotNull(name, "article.name");
    
    
    for (int i = 0; i < binArticle.size(); i++) {
      BinArticle iBinArticle = binArticle.get(i);
      for (int j = i + 1; j < binArticle.size(); j++) {
        BinArticle jBinArticle = binArticle.get(j);
        if (iBinArticle.getBinCode().equals(jBinArticle.getBinCode()))
          throw new IllegalArgumentException("分拣方案" + code + "货位商品明细第" + (i + 1) + "行与第" + (j + 1)
              + "行重复。");
      }
    }
  }

}
