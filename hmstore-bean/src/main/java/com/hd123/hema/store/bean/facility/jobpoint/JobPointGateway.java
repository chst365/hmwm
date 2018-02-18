/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-bean
 * 文件名：	JobPointGateway.java
 * 模块说明：
 * 修改历史：
 * 2016-7-27 - xiepingping - 创建。
 */
package com.hd123.hema.store.bean.facility.jobpoint;

import com.hd123.hema.store.bean.common.FieldLength;
import com.hd123.hema.store.bean.common.Validator;
import com.hd123.rumba.commons.biz.entity.Entity;
import com.hd123.rumba.commons.lang.Assert;

/**
 * @author xiepingping
 * 
 */
public class JobPointGateway extends Entity  implements Validator{
  private static final long serialVersionUID = 644895545733583234L;

  /** 作业点UUID */
  private String jobPointUuid;
  /** 网关UUID */
  private String gatewayUuid;

  public String getJobPointUuid() {
    return jobPointUuid;
  }

  public void setJobPointUuid(String jobPointUuid) {
    Assert.assertStringNotTooLong(jobPointUuid, FieldLength.LENGTH_32, "jobpointgateway.jobPointUuid");
    Assert.assertArgumentNotNull(jobPointUuid, "jobpointgateway.jobPointUuid");
    this.jobPointUuid = jobPointUuid;
  }

  public String getGatewayUuid() {
    return gatewayUuid;
  }

  public void setGatewayUuid(String gatewayUuid) {
    Assert.assertStringNotTooLong(gatewayUuid, FieldLength.LENGTH_32, "jobpointgateway.gatewayUuid");
    Assert.assertArgumentNotNull(gatewayUuid, "jobpointgateway.gatewayUuid");
    this.gatewayUuid = gatewayUuid;
  }

  @Override
  public void validate() {
    Assert.assertStringNotTooLong(jobPointUuid, FieldLength.LENGTH_32, "jobpointgateway.jobPointUuid");
    Assert.assertStringNotTooLong(gatewayUuid, FieldLength.LENGTH_32, "jobpointgateway.gatewayUuid");

    Assert.assertArgumentNotNull(jobPointUuid, "jobpointgateway.jobPointUuid");
    Assert.assertArgumentNotNull(gatewayUuid, "jobpointgateway.gatewayUuid");
    
  }

}
