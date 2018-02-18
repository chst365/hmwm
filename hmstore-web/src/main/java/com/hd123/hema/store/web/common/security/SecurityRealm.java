/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	SecurityRealm.java
 * 模块说明：
 * 修改历史：
 * 2016-6-21 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.common.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hd123.wms.antman.system.bean.User;
import com.hd123.wms.antman.system.service.UserService;

/**
 * @author xiepingping
 * 
 */
@Component(value = "securityRealm")
public class SecurityRealm extends AuthorizingRealm {

  @Autowired
  private UserService userService;

  /**
   * 权限检查
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    SimpleAuthorizationInfo authoriationInfo = new SimpleAuthorizationInfo();
    String code = String.valueOf(principals.getPrimaryPrincipal());

    @SuppressWarnings("unused")
    final User user = userService.getByLoginId(code);

    return authoriationInfo;
  }

  /**
   * 登录验证
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    String code = String.valueOf(token.getPrincipal());
    String password = new String((char[]) token.getCredentials());

    // 通过数据库进行验证
    final User authentication = userService.authentication(code, password);
    if (authentication == null) {
      throw new AuthenticationException("用户名或密码错误.");
    }
    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(code, password,
        getName());
    return authenticationInfo;
  }

}
