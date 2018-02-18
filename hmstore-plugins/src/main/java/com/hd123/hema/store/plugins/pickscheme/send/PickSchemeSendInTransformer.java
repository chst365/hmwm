/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-plugins
 * 文件名：	PickSchemeSendInTransformer.java
 * 模块说明：
 * 修改历史：
 * 2016-8-1 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.pickscheme.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hd123.hema.store.plugins.common.BizType;
import com.hd123.hema.store.plugins.pickscheme.send.request.PullPickSchemeRequestBean;
import com.hd123.wms.mm.common.entity.BaseStandardBean;
import com.hd123.wms.mm.common.util.JsonUtils;
import com.hd123.wms.mm.transformer.exception.TransformMMException;
import com.hd123.wms.mm.transformer.receive.ReceiveInTransformer;

/**
 * @author xiepingping
 *
 */
public class PickSchemeSendInTransformer extends ReceiveInTransformer<PullPickSchemeRequestBean, String> {
  private final static Logger logger = LoggerFactory.getLogger(PickSchemeSendInTransformer.class);

  @Override
  protected PullPickSchemeRequestBean convert2Objecct(String s) throws TransformMMException {
    PullPickSchemeRequestBean bean = JsonUtils.stringToObject(s, PullPickSchemeRequestBean.class);
    if (bean == null) {
      logger.error("转换为类型[" + this.getClass().getName() + "]时失败");
      logger.error(s);
      throw new TransformMMException("转换对象时未成功");
    }
    bean.setBizType(BizType.BIZTYPE_PULL_PICKSCHEME);
    return bean;
  }

  @Override
  protected BaseStandardBean<String> doTransform(PullPickSchemeRequestBean s)
      throws TransformMMException {
    BaseStandardBean<String> bean = new BaseStandardBean<String>();

    bean.setData(s.getStoreCode());

    return bean;
  }

}
