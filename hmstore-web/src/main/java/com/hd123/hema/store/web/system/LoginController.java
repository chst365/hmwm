/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	LoginController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd123.hema.store.bean.material.Store;
import com.hd123.hema.store.service.material.StoreService;
import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.wms.antman.system.bean.User;
import com.hd123.wms.antman.system.service.UserService;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/auth")
public class LoginController {
  private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

  public final static String CURRENT_USER = "SessionUser";

  @Autowired
  private UserService userService;
  @Autowired
  private StoreService storeService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public @ResponseBody
  RespBean login(@RequestParam("userCode")
  String userCode, @RequestParam("password")
  String password, HttpServletRequest request) {
    RespBean res = new RespBean();
    try {
      Subject subject = SecurityUtils.getSubject();
      // 已登录
      if (subject.isAuthenticated()) {
        return res;
      }

      // 身份验证
      subject.login(new UsernamePasswordToken(userCode, password));
      // 验证成功在Session中保存用户信息
      final User authUserInfo = userService.getByLoginId(userCode);
      SUser sUser = new SUser();
      sUser.setCode(authUserInfo.getLoginId());
      sUser.setName(authUserInfo.getName());
      sUser.setOrgUuid(authUserInfo.getTenantUuid());

      if (StringUtils.isEmpty(authUserInfo.getEnterpriseCode())) {
        Store store = storeService.getByUuid(authUserInfo.getTenantUuid());
        authUserInfo.setEnterpriseCode(store.getCode());
        authUserInfo.setEnterpriseName(store.getName());
      }
      sUser.setOrgCode(authUserInfo.getEnterpriseCode());
      sUser.setOrgName(authUserInfo.getEnterpriseName());

      request.getSession().setAttribute(CURRENT_USER, sUser);
      res.setObj(sUser);
      return res;
    } catch (AuthenticationException e) {
      logger.error("登录出错", e);
      res.setMsg("用户名或密码错误");
      res.setSuccess(false);
      return res;
    }
  }

  @RequestMapping(value = "/isLoggedIn", method = RequestMethod.GET)
  public @ResponseBody
  RespBean isLoggedIn(HttpSession session) {
    RespBean res = new RespBean();

    try {
      Subject subject = SecurityUtils.getSubject();
      if (subject.isAuthenticated() && session.getAttribute(CURRENT_USER) != null) {
        res.setSuccess(true);
      } else {
        session.removeAttribute(CURRENT_USER);
        res.setSuccess(false);
      }

      return res;
    } catch (Exception e) {
      res.setSuccess(false);
      return res;
    }
  }

  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public @ResponseBody
  RespBean logout(HttpSession session) {
    session.removeAttribute(CURRENT_USER);
    // 登出操作
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return new RespBean();
  }
}
