/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	PickSchemeController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.facility.pickscheme;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.hd123.hema.store.bean.facility.pickscheme.BinArticle;
import com.hd123.hema.store.bean.facility.pickscheme.PickScheme;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeState;
import com.hd123.hema.store.service.facility.pickscheme.PickSchemeService;
import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.hema.store.web.common.utils.ExcelHelper;
import com.hd123.rumba.commons.biz.entity.UCN;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.common.utils.DateUtils;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/pickscheme")
public class PickSchemeController {
  private static final Logger logger = LoggerFactory.getLogger(PickSchemeController.class);

  @Autowired
  private PickSchemeService pickSchemeService;

  @ApiOperation(value = "查询分拣方案列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getPickSchemes(@RequestParam("orgUuid")
  String orgUuid) {
    RespBean resp = new RespBean();

    QueryParam param = new QueryParam();
    param.put("orgUuid", orgUuid);
    try {
      List<PickScheme> pickSchemes = pickSchemeService.queryByList(param);
      resp.setObj(pickSchemes);

    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "新增分拣方案")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody
  RespBean addPickScheme(@RequestBody
  PickScheme template) {
    Assert.assertArgumentNotNull(template.getJobPoint(), "作业点");

    RespBean resp = new RespBean();

    try {
      String uuid = pickSchemeService.insert(template);
      resp.setObj(uuid);

    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "修改分拣方案")
  @RequestMapping(method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editPickScheme(@RequestBody
  PickScheme template) {
    RespBean resp = new RespBean();

    try {
      pickSchemeService.update(template);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "根据UUID获取分拣方案")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getPickScheme(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();

    try {
      PickScheme pickScheme = pickSchemeService.getByUuid(uuid);
      resp.setObj(pickScheme);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "删除分拣方案")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deletePickScheme(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();

    try {
      pickSchemeService.delete(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "生效分拣方案")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
  public @ResponseBody
  RespBean effectivePickScheme(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();

    try {
      pickSchemeService.effective(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "上传分拣方案")
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public @ResponseBody
  RespBean upload(MultipartFile file, HttpServletRequest request) {
    RespBean resp = new RespBean();

    try {
      List<String> pickSchemesStr = ExcelHelper.exportListFromExcel(file, 0);
      List<String> binArticlesStr = ExcelHelper.exportListFromExcel(file, 1);
      List<BinArticle> binArticles = convertBinArticlesStr(binArticlesStr);
      List<PickScheme> pickSchemes = convertPickSchemesStr(pickSchemesStr, binArticles);
      pickSchemeService.insertBatch(pickSchemes);
      resp.setObj(pickSchemes.size());
    } catch (Exception e) {
      logger.error(e.getMessage());
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private List<BinArticle> convertBinArticlesStr(List<String> binArticlesStr) {
    List<BinArticle> result = new ArrayList<BinArticle>();
    for (String source : binArticlesStr) {
      String[] parts = ExcelHelper.substring(source);
      BinArticle target = new BinArticle();
      target.setUuid(parts[0]);
      target.setBinCode(parts[1]);
      target.setArticle(new UCN(parts[2], parts[3], parts[4]));
      target.setPickSchemeUuid(parts[5]);
      result.add(target);
    }
    return result;
  }

  private List<PickScheme> convertPickSchemesStr(List<String> pickSchemesStr,
      List<BinArticle> binArticles) throws UnsupportedOperationException, ParseException {
    List<PickScheme> result = new ArrayList<PickScheme>();
    for (String source : pickSchemesStr) {
      String[] parts = ExcelHelper.substring(source);
      PickScheme target = new PickScheme();
      target.setUuid(parts[0]);
      target.setCode(parts[1]);
      target.setName(parts[2]);
      target.setState(PickSchemeState.UnEffective);
      target.setVersion(Long.valueOf(parts[4]));
      target.setVersionTime(DateUtils.FMT_MDYHMS.parse(parts[5]));
      target.setOrgUuid(parts[6]);
      target.setBinArticle(new ArrayList<BinArticle>());

      for (BinArticle binArticle : binArticles)
        if (binArticle.getPickSchemeUuid().equals(target.getUuid()))
          target.getBinArticle().add(binArticle);

      result.add(target);
    }
    return result;
  }

}