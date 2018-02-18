/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-plugins
 * 文件名： WDKOutTransformer.java
 * 模块说明：
 * 修改历史：
 * 2016-7-20 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.common;

import com.hd123.wms.mm.common.entity.BaseResponseBean;
import com.hd123.wms.mm.common.entity.ResponseBean;
import com.hd123.wms.mm.common.entity.ResponseCode;
import com.hd123.wms.mm.common.util.JsonUtils;
import com.hd123.wms.mm.transformer.Transformer;
import com.hd123.wms.mm.transformer.exception.TransformMMException;
import com.hd123.wms.mm.wdk.common.bean.WDKResponseBean;

/**
 * @author xiepingping
 * 
 */
public class WDKOutTransformer implements Transformer<ResponseBean, String> {

  @Override
  public String transform(ResponseBean s) throws TransformMMException {
    if (s instanceof BaseResponseBean) {
      WDKResponseBean bean = new WDKResponseBean();
      bean.setCode(getStateCode(((BaseResponseBean) s).getRespCode()));
      bean.setMsg(((BaseResponseBean) s).getRespMessage());
      return JsonUtils.objectToString(bean);
    } else {
      return JsonUtils.objectToString(s);
    }
  }

  private int getStateCode(ResponseCode code) {
    if (ResponseCode.success.equals(code)) {
      return WDKResponseBean.STATE_CODE_SUCCESS;
    } else {
      return WDKResponseBean.STATE_CODE_FAILURE;
    }
  }
}
