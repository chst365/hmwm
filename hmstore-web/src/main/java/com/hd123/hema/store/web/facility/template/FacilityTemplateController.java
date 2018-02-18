/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	FacilityTemplateController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.facility.template;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

import com.hd123.hema.store.bean.facility.gateway.ElectronicTag;
import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.hema.store.bean.facility.gateway.NodeType;
import com.hd123.hema.store.bean.facility.gateway.NodeUsage;
import com.hd123.hema.store.bean.facility.jobpoint.BinEleTag;
import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.hema.store.bean.facility.jobpoint.PickArea;
import com.hd123.hema.store.bean.facility.jobpoint.RplEleTag;
import com.hd123.hema.store.bean.facility.jobpoint.SGateway;
import com.hd123.hema.store.bean.facility.jobpoint.Section;
import com.hd123.hema.store.bean.facility.jobpoint.SectionEleTag;
import com.hd123.hema.store.bean.facility.template.FacilityTemplate;
import com.hd123.hema.store.service.facility.template.FacilityTemplateService;
import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.hema.store.web.common.utils.ExcelHelper;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.exception.ANException;
import com.hd123.wms.antman.system.bean.Enterprise;
import com.hd123.wms.antman.system.service.EnterpriseService;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/facilitytemplate")
public class FacilityTemplateController {
  private static final Logger logger = LoggerFactory.getLogger(FacilityTemplateController.class);

  @Autowired
  private FacilityTemplateService templateService;
  @Autowired
  private EnterpriseService enterpriseService;

  @ApiOperation(value = "查询设施模板列表")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getFacilityTemplates() {
    RespBean resp = new RespBean();

    try {
      List<FacilityTemplate> templates = templateService.queryByList();
      resp.setObj(templates);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "新增设施模板")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody
  RespBean addFacilityTemplate(@RequestBody
  FacilityTemplate template) {
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

  @ApiOperation(value = "修改设施模板")
  @RequestMapping(method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editFacilityTemplate(@RequestBody
  FacilityTemplate template) {
    RespBean resp = new RespBean();

    try {
      templateService.update(template);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "根据UUID获取设施模板")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getFacilityTemplate(@PathVariable("uuid")
  String uuid) {
    RespBean resp = new RespBean();

    try {
      FacilityTemplate template = templateService.getByUuid(uuid);
      resp.setObj(template);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "删除设施模板")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deleteFacilityTemplate(@PathVariable("uuid")
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

  @ApiOperation(value = "上传设施模板")
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public @ResponseBody
  RespBean upload(MultipartFile file) {
    RespBean resp = new RespBean();

    try {
      List<String> templatesStr = ExcelHelper.exportListFromExcel(file, 0);
      List<String> gatewaysStr = ExcelHelper.exportListFromExcel(file, 1);
      List<String> electronicsStr = ExcelHelper.exportListFromExcel(file, 2);

      List<String> jobPointsStr = ExcelHelper.exportListFromExcel(file, 3);
      List<String> pickareasStr = ExcelHelper.exportListFromExcel(file, 4);
      List<String> sectionsStr = ExcelHelper.exportListFromExcel(file, 5);
      List<String> binEleTagsStr = ExcelHelper.exportListFromExcel(file, 6);
      List<String> sectionEleTagsStr = ExcelHelper.exportListFromExcel(file, 7);
      List<String> rplEleTagsStr = ExcelHelper.exportListFromExcel(file, 8);

      List<ElectronicTag> eleTags = convertEleTags(electronicsStr);
      List<Gateway> gateways = convertGateways(gatewaysStr, eleTags);

      List<BinEleTag> binEleTags = convertBinEleTags(binEleTagsStr);
      List<SectionEleTag> sectionEleTags = convertSectionEleTags(sectionEleTagsStr);
      List<RplEleTag> rplEleTags = convertRplEleTags(rplEleTagsStr);
      List<Section> sections = convertSection(sectionsStr, binEleTags, sectionEleTags, rplEleTags);
      List<PickArea> pickareas = convertPickAreas(pickareasStr, sections);
      List<JobPoint> jobPoints = convertJobPoints(jobPointsStr, pickareas,gateways);
      List<FacilityTemplate> templates = convertTemplates(templatesStr, gateways, jobPoints);

      templateService.insertBatch(templates);
      resp.setObj(templates.size());
    } catch (Exception e) {
      logger.error(e.getMessage());
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private List<Gateway> convertGateways(List<String> gatewaysStr, List<ElectronicTag> eleTags)
      throws ANException {
    List<Gateway> result = new ArrayList<Gateway>();
    for (String source : gatewaysStr) {
      String[] parts = ExcelHelper.substring(source);
      Gateway target = new Gateway();
      target.setUuid(parts[0]);
      target.setCode(parts[1]);
      target.setIp(parts[2]);
      target.setPort(parts[3]);
      target.setTemplateUuid(parts[4]);
      target.setOrgUuid(fetchOrgUuid(parts[5]));
      target.setTags(new ArrayList<ElectronicTag>());
      for (ElectronicTag eleTag : eleTags)
        if (eleTag.getGatewayUuid().equals(target.getUuid()))
          target.getTags().add(eleTag);

      result.add(target);
    }
    return result;
  }

  private String fetchOrgUuid(String orgCode) throws ANException {
    Assert.assertArgumentNotNull(orgCode, "组织代码");

    Enterprise enterprise = enterpriseService.getByCode(orgCode);
    if (enterprise == null)
      throw new IllegalArgumentException("企业不存在");

    return enterprise.getUuid();
  }

  private List<JobPoint> convertJobPoints(List<String> jobPointsStr, List<PickArea> pickareas,List<Gateway> gateways)
      throws ANException {
    List<JobPoint> result = new ArrayList<JobPoint>();
    for (String source : jobPointsStr) {
      String[] parts = ExcelHelper.substring(source);
      JobPoint jobPoint = new JobPoint();
      jobPoint.setUuid(parts[0]);
      jobPoint.setCode(parts[1]);
      jobPoint.setName(parts[2]);
      jobPoint.setTemplateUuid(parts[3]);
      jobPoint.setOrgUuid(fetchOrgUuid(parts[4]));
      //新增网关字段
      String gatewayCode=parts[5];
      SGateway sgateway=new SGateway();
      Boolean flag=false;
      for(Gateway gateway:gateways){
        if(gateway.getCode().equals(gatewayCode)){
          if(parts[3].equals(gateway.getTemplateUuid())){
            flag=true;
            sgateway.setUuid(gateway.getUuid());
            sgateway.setCode(gateway.getCode());
            sgateway.setIp(gateway.getIp());
            sgateway.setPort(gateway.getPort());
            
          }
        }
      }
      if(flag){
        jobPoint.setsGateway(sgateway);
      }else{
        throw new IllegalArgumentException("不存在同一设施模板下的网关");
      }
      
      //
      jobPoint.setPickAreas(new ArrayList<PickArea>());
      for (PickArea pickArea : pickareas)
        if (pickArea.getJobPointUuid().equals(jobPoint.getUuid()))
          jobPoint.getPickAreas().add(pickArea);

      result.add(jobPoint);
    }
    return result;
  }

  private List<ElectronicTag> convertEleTags(List<String> electronicsStr) throws ParseException {
    List<ElectronicTag> result = new ArrayList<ElectronicTag>();
    for (String source : electronicsStr) {
      String[] parts = ExcelHelper.substring(source);
      ElectronicTag eleTag = new ElectronicTag();
      eleTag.setUuid(parts[0]);
      eleTag.setNodeCode(parts[1]);
      eleTag.setNodeAddress(parts[2]);
      eleTag.setNodeType(NodeType.valueOf(parts[3]));
      eleTag.setNodeUsage(NodeUsage.valueOf(parts[4]));
      eleTag.setGatewayUuid(parts[5]);

      result.add(eleTag);
    }
    return result;
  }

  private List<PickArea> convertPickAreas(List<String> pickareasStr, List<Section> sections) {
    List<PickArea> result = new ArrayList<PickArea>();
    for (String source : pickareasStr) {
      String[] parts = ExcelHelper.substring(source);
      PickArea pickArea = new PickArea();
      pickArea.setUuid(parts[0]);
      pickArea.setCode(parts[1]);
      pickArea.setName(parts[2]);
      pickArea.setJobPointUuid(parts[3]);
      pickArea.setSections(new ArrayList<Section>());
      for (Section section : sections)
        if (section.getPickAreaUuid().equals(pickArea.getUuid()))
          pickArea.getSections().add(section);

      result.add(pickArea);
    }
    return result;
  }

  private List<Section> convertSection(List<String> sectionsStr, List<BinEleTag> binEleTags,
      List<SectionEleTag> sectionEleTags, List<RplEleTag> rplEleTags) {
    List<Section> result = new ArrayList<Section>();
    for (String source : sectionsStr) {
      String[] parts = ExcelHelper.substring(source);
      Section section = new Section();
      section.setUuid(parts[0]);
      section.setCode(parts[1]);
      section.setName(parts[2]);
      section.setPickAreaUuid(parts[3]);
      section.setBinEleTags(new ArrayList<BinEleTag>());
      section.setSectionEleTags(new ArrayList<SectionEleTag>());
      section.setRplEleTags(new ArrayList<RplEleTag>());
      for (BinEleTag binEleTag : binEleTags)
        if (binEleTag.getSectionUuid().equals(section.getUuid()))
          section.getBinEleTags().add(binEleTag);
      for (SectionEleTag sectionEleTag : sectionEleTags)
        if (sectionEleTag.getSectionUuid().equals(section.getUuid()))
          section.getSectionEleTags().add(sectionEleTag);
      for (RplEleTag rplEleTag : rplEleTags)
        if (rplEleTag.getSectionUuid().equals(section.getUuid()))
          section.getRplEleTags().add(rplEleTag);

      result.add(section);
    }
    return result;
  }

  private List<BinEleTag> convertBinEleTags(List<String> binEleTagsStr) {
    List<BinEleTag> result = new ArrayList<BinEleTag>();
    for (String source : binEleTagsStr) {
      String[] parts = ExcelHelper.substring(source);
      BinEleTag target = new BinEleTag();
      target.setUuid(parts[0]);
      target.setBinCode(parts[1]);
      target.setNodeCode(parts[2]);
      target.setNodeAddress(target.getNodeCode());
      target.setNodeType(NodeType.valueOf(parts[3]));
      target.setNodeUsage(NodeUsage.valueOf(parts[4]));
      target.setSectionUuid(parts[5]);

      result.add(target);
    }
    return result;
  }

  private List<SectionEleTag> convertSectionEleTags(List<String> sectionEleTagsStr) {
    List<SectionEleTag> result = new ArrayList<SectionEleTag>();
    for (String source : sectionEleTagsStr) {
      String[] parts = ExcelHelper.substring(source);
      SectionEleTag target = new SectionEleTag();
      target.setUuid(parts[0]);
      target.setNodeCode(parts[1]);
      target.setNodeAddress(target.getNodeCode());
      target.setNodeType(NodeType.valueOf(parts[2]));
      target.setNodeUsage(NodeUsage.valueOf(parts[3]));
      target.setSectionUuid(parts[4]);

      result.add(target);
    }
    return result;
  }

  private List<RplEleTag> convertRplEleTags(List<String> rplEleTagsStr) {
    List<RplEleTag> result = new ArrayList<RplEleTag>();
    for (String source : rplEleTagsStr) {
      String[] parts = ExcelHelper.substring(source);
      RplEleTag target = new RplEleTag();
      target.setUuid(parts[0]);
      target.setRequestNodeCode(parts[1]);
      target.setRequestNodeAddress(target.getRequestNodeCode());
      target.setRequestNodeType(NodeType.valueOf(parts[2]));
      target.setRequestNodeUsage(NodeUsage.valueOf(parts[3]));
      target.setResponseNodeCode(parts[4]);
      target.setResponseNodeAddress(target.getResponseNodeCode());
      target.setResponseNodeType(NodeType.valueOf(parts[5]));
      target.setResponseNodeUsage(NodeUsage.valueOf(parts[6]));
      target.setSectionUuid(parts[7]);

      result.add(target);
    }
    return result;
  }

  private List<FacilityTemplate> convertTemplates(List<String> templatesStr,
      List<Gateway> gateways, List<JobPoint> jobPoints) {
    List<FacilityTemplate> result = new ArrayList<FacilityTemplate>();
    for (String source : templatesStr) {
      String[] parts = ExcelHelper.substring(source);
      FacilityTemplate target = new FacilityTemplate();
      target.setUuid(parts[0]);
      target.setCode(parts[1]);
      target.setName(parts[2]);
      target.setGateways(new ArrayList<Gateway>());
      target.setJobPoints(new ArrayList<JobPoint>());
      for (Gateway gateway : gateways)
        if (gateway.getTemplateUuid().equals(target.getUuid()))
          target.getGateways().add(gateway);
      for (JobPoint jobPoint : jobPoints)
        if (jobPoint.getTemplateUuid().equals(target.getUuid()))
          target.getJobPoints().add(jobPoint);

      result.add(target);
    }
    return result;
  }

  @ApiOperation(value = "从模板导入设施")
  @RequestMapping(value = "/generator", method = RequestMethod.POST)
  public @ResponseBody
  RespBean generateFromTemplate(@RequestParam("templateUuid")
  String templateUuid, @RequestParam("orgUuid")
  String orgUuid) {
    RespBean resp = new RespBean();

    try {
      templateService.generateFacility(templateUuid, orgUuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

}