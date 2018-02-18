package com.hd123.hema.store.plugins.common;

/**
 * @author xiepingping
 * 
 */
public class BizType {

  /** 从平台拉取商品资料 */
  public static final String BIZTYPE_PULL_ARTICLE = "pullArticle";

  /** 从平台拉取货位资料 */
  public static final String BIZTYPE_PULL_BIN = "pullBin";

  /** 从平台拉取电子标签 */
  public static final String BIZTYPE_PULL_ELETAG = "pullEleTag";

  /** 从平台拉取分拣方案资料 */
  public static final String BIZTYPE_PULL_PICKSCHEME = "pullPickScheme";

  /** 往平台推送标签状态 */
  public static final String BIZTYPE_PULL_ELETAGSTATUS = "pullELeTagStatus";

  /** 往平台推送拣货情况 */
  public static final String BIZTYPE_PULL_PICKSTATUS = "pullPickStatus";
}
