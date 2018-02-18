/**
 * 业务公用实现。
 * 
 * 项目名： hmstore-web
 * 文件名： UserController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hd123.hema.store.bean.material.Store;
import com.hd123.hema.store.service.material.StoreService;
import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.hema.store.web.common.utils.ExcelHelper;
import com.hd123.hema.store.web.system.dto.SUser;
import com.hd123.wms.antman.common.bean.UCN;
import com.hd123.wms.antman.common.exception.ANException;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.PageQueryResult;
import com.hd123.wms.antman.system.bean.Enterprise;
import com.hd123.wms.antman.system.bean.Role;
import com.hd123.wms.antman.system.bean.User;
import com.hd123.wms.antman.system.service.EnterpriseService;
import com.hd123.wms.antman.system.service.RoleService;
import com.hd123.wms.antman.system.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;
  @Autowired
  private StoreService storeService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private EnterpriseService enterpriseService;

  @ApiOperation(value = "分页查询员工")
  @RequestMapping(value = "/list/{page}/{pageSize}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getUsers(@ApiParam(required = true, name = "page", value = "页码")
  @PathVariable("page")
  Integer page, @ApiParam(required = true, name = "pageSize", value = "页大小")
  @PathVariable("pageSize")
  Integer pageSize, @RequestParam(required = false)
  String code) {
    RespBean resp = new RespBean();
    PageQueryDefinition filter = new PageQueryDefinition();
    filter.setPage(page);
    filter.setPageSize(pageSize);
    filter.put("loginId", code);
    try {
      PageQueryResult<User> pqr = userService.queryByPage(filter);
      for (User user : pqr.getRecords())
        installOrg(user);

      PageQueryResult<SUser> result = convertPqr(pqr);
      resp.setObj(result);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private PageQueryResult<SUser> convertPqr(PageQueryResult<User> pqr) {
    PageQueryResult<SUser> result = new PageQueryResult<SUser>();
    List<SUser> susers = new ArrayList<SUser>();
    for (User source : pqr.getRecords()) {
      SUser target = convertUser(source);
      susers.add(target);
    }

    result.setPage(pqr.getPage());
    result.setPageCount(pqr.getPageCount());
    result.setPageSize(pqr.getPageSize());
    result.setRecordCount(pqr.getRecordCount());
    result.setRecords(susers);
    return result;
  }

  private void installOrg(User user) {
    if (user == null)
      return;

    if (StringUtils.isEmpty(user.getEnterpriseCode())) {
      Store store = storeService.getByUuid(user.getTenantUuid());
      if (store != null) {
        user.setEnterpriseCode(store.getCode());
        user.setEnterpriseName(store.getName());
      }
    }
  }

  @ApiOperation(value = "新增员工")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody
  RespBean addUser(@RequestBody
  SUser suser) {
    RespBean resp = new RespBean();
    try {
      User user = convertSUser(suser);

      String uuid = userService.insert(user);
      resp.setObj(uuid);

    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private User convertSUser(SUser source) {
    if (source == null)
      return null;

    User user = new User();
    user.setCreator(source.getOperator());
    user.setEnable(1);
    user.setEnterpriseCode(source.getOrg().getCode());
    user.setEnterpriseName(source.getOrg().getName());
    user.setLoginId(source.getCode());
    user.setMobile(source.getMobile());
    user.setModifier(source.getOperator());
    user.setName(source.getName());
    user.setPassword(source.getPassword());
    user.setRoles(source.getRoles());
    user.setTenantUuid(source.getOrg().getUuid());
    user.setUuid(source.getUuid());
    return user;
  }

  @ApiOperation(value = "修改员工")
  @RequestMapping(method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editUser(@RequestBody
  SUser suser) {
    RespBean resp = new RespBean();
    try {
      User user = convertSUser(suser);
      userService.update(user);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "根据UUID获取员工")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getUser(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();
    try {
      User user = userService.getByUuid(uuid);
      installOrg(user);
      SUser result = convertUser(user);
      resp.setObj(result);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }
  
  
  @ApiOperation(value = "根据loginid获取员工")
  @RequestMapping(value = "/loginid", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getLoginid(@RequestParam("loginid")
  String loginid) {
    RespBean resp = new RespBean();
    try {
      User user = userService.getByLoginId(loginid);
      installOrg(user);
      SUser result = convertUser(user);
      resp.setObj(result);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private SUser convertUser(User user) {
    if (user == null)
      return null;

    SUser target = new SUser();
    target.setUuid(user.getUuid());
    target.setCode(user.getLoginId());
    target.setMobile(user.getMobile());
    target.setName(user.getName());
    target
        .setOrg(new UCN(user.getTenantUuid(), user.getEnterpriseCode(), user.getEnterpriseName()));
    target.setPassword(user.getPassword());
    target.setRoles(user.getRoles());
    return target;
  }

  @ApiOperation(value = "删除员工")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deleteUser(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();
    try {
      userService.delete(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "上传员工")
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public @ResponseBody
  RespBean upload(MultipartFile file, HttpServletRequest request) {
    RespBean resp = new RespBean();
    try {
      List<String> usersStr = ExcelHelper.exportListFromExcel(file, 0);
      List<String> userroleStr = ExcelHelper.exportListFromExcel(file, 1);
      List<User> users = convertUsers(usersStr, userroleStr);

      userService.insertBatch(users);
      resp.setObj(users.size());
    } catch (Exception e) {
      logger.error(e.getMessage());
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private List<User> convertUsers(List<String> usersStr, List<String> userroleStr)
      throws ANException {
    List<User> result = new ArrayList<User>();
    for (String source : usersStr) {
      String[] parts = ExcelHelper.substring(source);
      User target = new User();
      target.setUuid(parts[0]);
      target.setLoginId(parts[1]);
      target.setName(parts[2]);
      target.setPassword(parts[3]);
      target.setMobile(parts[4]);
      target.setEmail(parts[5]);

      String parts6 = null;
      Enterprise enterprise = enterpriseService.getByCode(parts[6]);
      if (enterprise != null) {
        parts6 = enterprise.getUuid();
      } else {
        Store store = storeService.getByCode(parts[6]);
        if (store != null)
          parts6 = store.getUuid();
      }
      
      if (StringUtils.isEmpty(parts6))
        throw new IllegalArgumentException("组织：" + parts[6] + "不存在");

      target.setTenantUuid(parts6);
      target.setCreator(parts[7]);
      target.setModifier(parts[7]);
      target.setEnable(1);
      target.setCreateTime(new Date());
      target.setModifyTime(new Date());
      List<UCN> roles = getroleStr(parts[0], userroleStr);
      target.setRoles(roles);
      result.add(target);
    }
    return result;
  }

  private List<UCN> getroleStr(String partid, List<String> userroleStr) {
    // --------------------------------
    List<UCN> roles = new ArrayList<UCN>();
    for (String str : userroleStr) {
      String[] roleparts = ExcelHelper.substring(str);
      if (roleparts[0].equals(partid)) {
        UCN roleucn = new UCN();
        // 根据code查role
        Role role = new Role();
        role = roleService.getByCode(roleparts[1]);
        if (role == null)
          throw new IllegalArgumentException("没有编码为：" + roleparts[1] + "的角色.");
        roleucn.setUuid(role.getUuid());
        roleucn.setCode(role.getCode());
        roleucn.setName(role.getName());
        roles.add(roleucn);
      }
    }
    // ------------------------------------
    return roles;
  }

}// -----end--class!