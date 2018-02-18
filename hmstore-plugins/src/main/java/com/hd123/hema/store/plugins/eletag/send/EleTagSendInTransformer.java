/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-plugins
 * 文件名： GoodsSendInTransformer.java
 * 模块说明：
 * 修改历史：
 * 2016-7-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.eletag.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hd123.hema.store.plugins.common.BizType;
import com.hd123.hema.store.plugins.eletag.send.request.PullEleTagRequestBean;
import com.hd123.wms.mm.common.entity.BaseStandardBean;
import com.hd123.wms.mm.common.util.JsonUtils;
import com.hd123.wms.mm.transformer.exception.TransformMMException;
import com.hd123.wms.mm.transformer.receive.ReceiveInTransformer;

/**
 * @author xiepingping
 * 
 */
public class EleTagSendInTransformer extends ReceiveInTransformer<PullEleTagRequestBean, String> {
  private final static Logger logger = LoggerFactory.getLogger(EleTagSendInTransformer.class);

  @Override
  protected PullEleTagRequestBean convert2Objecct(String s) throws TransformMMException {
    PullEleTagRequestBean bean = JsonUtils.stringToObject(s, PullEleTagRequestBean.class);
    if (bean == null) {
      logger.error("转换为类型[" + this.getClass().getName() + "]时失败");
      logger.error(s);
      throw new TransformMMException("转换对象时未成功");
    }
    bean.setBizType(BizType.BIZTYPE_PULL_ELETAG);
    return bean;
  }

  @Override
  protected BaseStandardBean<String> doTransform(PullEleTagRequestBean s)
      throws TransformMMException {
    BaseStandardBean<String> bean = new BaseStandardBean<String>();

    bean.setData(s.getStoreCode());

    return bean;
  }

}
