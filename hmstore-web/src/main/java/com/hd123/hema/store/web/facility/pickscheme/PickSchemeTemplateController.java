/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	PickSchemeTemplateController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.facility.pickscheme;

import java.text.ParseException;
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

import com.hd123.hema.store.bean.facility.pickscheme.BinArticle;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeState;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeTemplate;
import com.hd123.hema.store.bean.facility.pickscheme.PickSchemeTemplateStore;
import com.hd123.hema.store.bean.facility.template.FacilityTemplate;
import com.hd123.hema.store.bean.material.Article;
import com.hd123.hema.store.bean.material.Store;
import com.hd123.hema.store.service.facility.pickscheme.PickSchemeTemplateService;
import com.hd123.hema.store.service.facility.template.FacilityTemplateService;
import com.hd123.hema.store.service.material.ArticleService;
import com.hd123.hema.store.service.material.StoreService;
import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.hema.store.web.common.utils.ExcelHelper;
import com.hd123.rumba.commons.biz.entity.UCN;
import com.hd123.wms.antman.common.exception.ANException;
import com.hd123.wms.antman.common.query.QueryParam;
import com.hd123.wms.antman.system.bean.Enterprise;
import com.hd123.wms.antman.system.service.EnterpriseService;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/pickschemetemplate")
public class PickSchemeTemplateController {
  private static final Logger logger = LoggerFactory.getLogger(PickSchemeTemplateController.class);

  @Autowired
  private PickSchemeTemplateService templateService;
  @Autowired
  private StoreService storeService;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private EnterpriseService enterpriseService;
  @Autowired
  private FacilityTemplateService facilitytempService;
  
  @ApiOperation(value = "查询分拣方案模板列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getPickSchemeTemplates(@RequestParam("orgUuid")
  String orgUuid) {
    RespBean resp = new RespBean();

    QueryParam param = new QueryParam();
    try {
      param.put("orgUuid", orgUuid);
      List<PickSchemeTemplate> templates = templateService.queryByList(param);
      resp.setObj(templates);

    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "新增分拣方案模板")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody
  RespBean addPickSchemeTemplate(@RequestBody
  PickSchemeTemplate template) {
    RespBean resp = new RespBean();

    try {
      String uuid = templateService.insert(template);
      resp.setObj(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "修改分拣方案模板")
  @RequestMapping(method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editPickSchemeTemplate(@RequestBody
  PickSchemeTemplate template) {
    RespBean resp = new RespBean();

    try {
      templateService.update(template);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "根据UUID获取分拣方案模板")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getPickSchemeTemplate(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();

    try {
      PickSchemeTemplate template = templateService.getByUuid(uuid);
      resp.setObj(template);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "删除分拣方案模板")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deletePickSchemeTemplate(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();

    try {
      templateService.delete(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "分发分拣方案模板")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
  public @ResponseBody
  RespBean distributePickSchemeTemplate(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();

    try {
      templateService.distribute(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "上传分拣方案模板")
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public @ResponseBody
  RespBean upload(MultipartFile file, HttpServletRequest request) {
    RespBean resp = new RespBean();

    try {
      List<String> templatesStr = ExcelHelper.exportListFromExcel(file, 0);
      List<String> templateStoresStr = ExcelHelper.exportListFromExcel(file, 1);
      List<String> binArticlesStr = ExcelHelper.exportListFromExcel(file, 2);
      List<PickSchemeTemplateStore> templateStores = convertTemplateStoresStr(templateStoresStr);
      List<BinArticle> binArticles = convertBinArticlesStr(binArticlesStr);
      List<PickSchemeTemplate> templates = convertTemplatesStr(templatesStr, templateStores,
          binArticles);
      templateService.insertBatch(templates);
      resp.setObj(templates.size());
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
      
      target.setBinCode(parts[0]);
      //根据articleCode查找article
      String part1Uuid=null;String part1Code=null;String part1Name=null;
      Article article=articleService.getByCode(parts[1]);
      if(article!=null)
      {
        part1Uuid= article.getUuid();
        part1Code=article.getCode();
        part1Name=article.getName();
      }
      if (StringUtils.isEmpty(part1Uuid))
        throw new IllegalArgumentException("商品：" + parts[0] + "不存在");
      //
      target.setArticle(new UCN(part1Uuid, part1Code, part1Name));
      target.setPickSchemeUuid(parts[2]);
      result.add(target);
    }
    return result;
  }

  private List<PickSchemeTemplateStore> convertTemplateStoresStr(List<String> templateStoresStr) {
    List<PickSchemeTemplateStore> result = new ArrayList<PickSchemeTemplateStore>();
    for (String source : templateStoresStr) {
      String[] parts = ExcelHelper.substring(source);
      PickSchemeTemplateStore target = new PickSchemeTemplateStore();
      //根据storecode查门店
      String part0Uuid=null;String part0Code=null;String part0Name=null;
      Store store = storeService.getByCode(parts[0]);
      if(store!=null)
      {
        part0Uuid= store.getUuid();
        part0Code=store.getCode();
        part0Name=store.getName();
      }
      if (StringUtils.isEmpty(part0Uuid))
        throw new IllegalArgumentException("门店：" + parts[0] + "不存在");
      //
      target.setStore(new UCN(part0Uuid, part0Code, part0Name));
      target.setPickSchemeTemplateUuid(parts[1]);
      result.add(target);
    }
    return result;
  }

  private List<PickSchemeTemplate> convertTemplatesStr(List<String> templatesStr,
      List<PickSchemeTemplateStore> templateStores, List<BinArticle> binArticles)
      throws UnsupportedOperationException, ParseException, ANException {
    List<PickSchemeTemplate> result = new ArrayList<PickSchemeTemplate>();
    for (String source : templatesStr) {
      String[] parts = ExcelHelper.substring(source);
      PickSchemeTemplate target = new PickSchemeTemplate();
      target.setUuid(parts[0]);
      target.setCode(parts[1]);
      target.setName(parts[2]);
      target.setState(PickSchemeState.UnEffective);
      target.setVersion(0);
      target.setVersionTime(new Date());
      
      String parts3 = null;
      Enterprise enterprise = enterpriseService.getByCode(parts[3]);
      if (enterprise != null) {
        parts3 = enterprise.getUuid();
      } 
      if (StringUtils.isEmpty(parts3))
        throw new IllegalArgumentException("组织：" + parts[3] + "不存在");
      target.setOrgUuid(parts3);
      //根据code查Facility
      String parts4Uuid = null;String parts4Code=null;String parts4Name=null;
      FacilityTemplate facility=facilitytempService.getByCode(parts[4]);
      if(facility!=null){
        parts4Uuid=facility.getUuid();
        parts4Code=facility.getCode();
        parts4Name=facility.getName();
      }
      if (StringUtils.isEmpty(parts4Uuid))
        throw new IllegalArgumentException("设施facilty模板：" + parts[4] + "不存在");
      //
      target.setFacilityTemplate(new UCN(parts4Uuid, parts4Code, parts4Name));
      target.setPickSchemeStores(new ArrayList<PickSchemeTemplateStore>());
      target.setBinArticle(new ArrayList<BinArticle>());

      for (PickSchemeTemplateStore templateStore : templateStores)
        if (templateStore.getPickSchemeTemplateUuid().equals(target.getUuid()))
          target.getPickSchemeStores().add(templateStore);
      for (BinArticle binArticle : binArticles)
        if (binArticle.getPickSchemeUuid().equals(target.getUuid()))
          target.getBinArticle().add(binArticle);

      result.add(target);
    }
    return result;
  }

}