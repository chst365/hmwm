/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-plugins
 * 文件名： SignValidator.java
 * 模块说明：
 * 修改历史：
 * 2016-7-20 - xiepingping - 创建。
 */
package com.hd123.hema.store.plugins.common;

import org.springframework.util.StringUtils;

import com.hd123.wms.mm.common.Validator;
import com.hd123.wms.mm.common.exception.InvalidContextMMException;
import com.hd123.wms.mm.common.exception.ValidateMMException;
import com.hd123.wms.mm.common.util.SignUtils;
import com.hd123.wms.mm.wdk.common.bean.WDKRequestBean;
import com.hd123.wms.mm.wdk.common.util.AppKeyFactory;

/**
 * @author xiepingping
 * 
 */
public class SignValidator<T extends WDKRequestBean> implements Validator<T> {

  @Override
  public T validate(T object) throws InvalidContextMMException, ValidateMMException {
    if (object == null)
      return object;

    if (StringUtils.isEmpty(object.getSign())) {
      throw new ValidateMMException("消息未签名");
    }

    String secet = AppKeyFactory.getSecretByKey(object.getAppId());
    if (StringUtils.isEmpty(secet)) {
      secet = "default";
    }

    String source = object.getAppId() + "&&" + secet + "&&" + object.getBizType() + "&&"
        + object.getRequestTime();
    String sign = SignUtils.getSign(source);

    if (sign.toUpperCase().equals(object.getSign().toUpperCase()) == false) {
      throw new ValidateMMException("签名不正确");
    }

    return object;
  }

}
