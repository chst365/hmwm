/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-plugins
 * 文件名： GoodsSendInTransformer.java
 * 模块说明：
 * 修改历史：
 * 2016-7-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.article.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hd123.hema.store.plugins.article.send.request.PullArticleRequestBean;
import com.hd123.hema.store.plugins.common.BizType;
import com.hd123.wms.mm.common.entity.BaseStandardBean;
import com.hd123.wms.mm.common.util.JsonUtils;
import com.hd123.wms.mm.transformer.exception.TransformMMException;
import com.hd123.wms.mm.transformer.receive.ReceiveInTransformer;

/**
 * @author xiepingping
 * 
 */
public class ArticleSendInTransformer extends ReceiveInTransformer<PullArticleRequestBean, String> {
  private final static Logger logger = LoggerFactory.getLogger(ArticleSendInTransformer.class);

  @Override
  protected PullArticleRequestBean convert2Objecct(String s) throws TransformMMException {
    PullArticleRequestBean bean = JsonUtils.stringToObject(s, PullArticleRequestBean.class);
    if (bean == null) {
      logger.error("转换为类型[" + this.getClass().getName() + "]时失败");
      logger.error(s);
      throw new TransformMMException("转换对象时未成功");
    }
    bean.setBizType(BizType.BIZTYPE_PULL_ARTICLE);
    return bean;
  }

  @Override
  protected BaseStandardBean<String> doTransform(PullArticleRequestBean s)
      throws TransformMMException {
    BaseStandardBean<String> bean = new BaseStandardBean<String>();

    return bean;
  }

}
