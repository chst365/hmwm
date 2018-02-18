/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	Section.java
 * 模块说明：
 * 修改历史：
 * 2016-6-15 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.jobpoint;

import java.util.ArrayList;
import java.util.List;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.rumba.commons.biz.entity.Entity;
import com.hd123.rumba.commons.lang.Assert;

/**
 * 区段
 * 
 * @author xiepingping
 * 
 */
public class Section extends Entity implements Validator {
  private static final long serialVersionUID = -5036831549212592154L;

  /** 代码 */
  private String code;
  /** 名称 */
  private String name;
  /** 分区UUID */
  private String pickAreaUuid;

  /** 货位标签明细 */
  private List<BinEleTag> binEleTags = new ArrayList<BinEleTag>();
  /** 区段标签明细 */
  private List<SectionEleTag> sectionEleTags = new ArrayList<SectionEleTag>();
  /** 补货标签明细 */
  private List<RplEleTag> rplEleTags = new ArrayList<RplEleTag>();

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_10, "section.code");
    Assert.assertArgumentNotNull(code, "section.code");
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_20, "section.name");
    Assert.assertArgumentNotNull(name, "section.name");
    this.name = name;
  }

  public String getPickAreaUuid() {
    return pickAreaUuid;
  }

  public void setPickAreaUuid(String pickAreaUuid) {
    Assert.assertStringNotTooLong(pickAreaUuid, FieldLength.LENGTH_32, "section.pickAreaUuid");
    Assert.assertArgumentNotNull(pickAreaUuid, "section.pickAreaUuid");
    this.pickAreaUuid = pickAreaUuid;
  }

  public List<BinEleTag> getBinEleTags() {
    return binEleTags;
  }

  public void setBinEleTags(List<BinEleTag> binEleTags) {
    this.binEleTags = binEleTags;
  }

  public List<SectionEleTag> getSectionEleTags() {
    return sectionEleTags;
  }

  public void setSectionEleTags(List<SectionEleTag> sectionEleTags) {
    this.sectionEleTags = sectionEleTags;
  }

  public List<RplEleTag> getRplEleTags() {
    return rplEleTags;
  }

  public void setRplEleTags(List<RplEleTag> rplEleTags) {
    this.rplEleTags = rplEleTags;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(code, FieldLength.LENGTH_10, "section.code");
    Assert.assertStringNotTooLong(name, FieldLength.LENGTH_20, "section.name");
    Assert.assertStringNotTooLong(pickAreaUuid, FieldLength.LENGTH_32, "section.pickAreaUuid");

    Assert.assertArgumentNotNull(code, "section.code");
    Assert.assertArgumentNotNull(name, "section.name");
    Assert.assertArgumentNotNull(pickAreaUuid, "section.pickAreaUuid");

    for (int i = 0; i < rplEleTags.size(); i++) {
      RplEleTag iRplEleTag = rplEleTags.get(i);
      for (int j = i + 1; j < rplEleTags.size(); j++) {
        RplEleTag jRplEleTag = rplEleTags.get(j);
        if (iRplEleTag.getRequestNodeCode().equals(jRplEleTag.getRequestNodeCode()))
          throw new IllegalArgumentException("区段" + code + "补货节点明细第" + (i + 1) + "行与第" + (j + 1)
              + "行请求节点编号重复。");
        if (iRplEleTag.getResponseNodeCode().equals(jRplEleTag.getResponseNodeCode()))
          throw new IllegalArgumentException("区段" + code + "补货节点明细第" + (i + 1) + "行与第" + (j + 1)
              + "行答应节点编号重复。");
      }

      if (iRplEleTag.getRequestNodeCode().equals(iRplEleTag.getResponseNodeCode()))
        throw new IllegalArgumentException("区段" + code + "补货节点明细第" + (i + 1) + "行请求节点编号与答应节点编号重复。");
    }

    for (int i = 0; i < sectionEleTags.size(); i++) {
      SectionEleTag iSectionEleTag = sectionEleTags.get(i);
      for (int j = i + 1; j < sectionEleTags.size(); j++) {
        SectionEleTag jSectionEleTag = sectionEleTags.get(j);
        if (iSectionEleTag.getNodeCode().equals(jSectionEleTag.getNodeCode()))
          throw new IllegalArgumentException("区段" + code + "区段节点明细第" + (i + 1) + "行与第" + (j + 1)
              + "行编号重复。");
      }
    }

    for (int i = 0; i < binEleTags.size(); i++) {
      BinEleTag iBinEleTag = binEleTags.get(i);
      for (int j = i + 1; j < binEleTags.size(); j++) {
        BinEleTag jBinEleTag = binEleTags.get(j);
        if (iBinEleTag.getBinCode().equals(jBinEleTag.getBinCode()))
          throw new IllegalArgumentException("区段" + code + "货位明细第" + (i + 1) + "行与第" + (j + 1)
              + "行代码重复。");
        if (iBinEleTag.getNodeCode().equals(jBinEleTag.getNodeCode()))
          throw new IllegalArgumentException("区段" + code + "货位明细第" + (i + 1) + "行与第" + (j + 1)
              + "行节点编号重复。");
      }
    }
  }

}
