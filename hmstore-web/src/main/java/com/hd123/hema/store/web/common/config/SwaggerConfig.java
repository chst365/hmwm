/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	SwaggerConfig.java
 * 模块说明：
 * 修改历史：
 * 2016-6-22 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * @author xiepingping
 * 
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {

  private SpringSwaggerConfig springSwaggerConfig;

  /**
   * Required to autowire SpringSwaggerConfig
   */
  @Autowired
  public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
    this.springSwaggerConfig = springSwaggerConfig;
  }

  /**
   * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc framework
   * - allowing for multiple swagger groups i.e. same code base multiple swagger
   * resource listings.
   */
  @Bean
  public SwaggerSpringMvcPlugin customImplementation() {
    return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(
        ".*?");
  }

  private ApiInfo apiInfo() {
    ApiInfo apiInfo = new ApiInfo("盒马门店系统接口", "该接口实时与后端服务一致，方便前端开发者调用，有问题随时联系",
        "My Apps API terms of service", "xiepingping@hd123.com", "My Apps API Licence Type",
        "My Apps API License URL");
    return apiInfo;
  }
}