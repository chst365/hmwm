/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-web
 * 文件名：	JobPointController.java
 * 模块说明：
 * 修改历史：
 * 2016-6-23 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.facility.jobpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd123.hema.store.bean.facility.jobpoint.JobPoint;
import com.hd123.hema.store.bean.facility.jobpoint.PickArea;
import com.hd123.hema.store.bean.facility.jobpoint.SGateway;
import com.hd123.hema.store.bean.facility.jobpoint.Section;
import com.hd123.hema.store.service.facility.jobpoint.JobPointService;
import com.hd123.hema.store.service.facility.jobpoint.PickAreaService;
import com.hd123.hema.store.service.facility.jobpoint.SectionService;
import com.hd123.hema.store.web.common.response.RespBean;
import com.hd123.hema.store.web.facility.jobpoint.dto.SJobPoint;
import com.hd123.hema.store.web.facility.jobpoint.dto.SPickArea;
import com.hd123.rumba.commons.lang.Assert;
import com.hd123.wms.antman.common.query.QueryParam;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author xiepingping
 * 
 */
@Controller
@RequestMapping("/jobpoint")
public class JobPointController {

  @Autowired
  private JobPointService jobPointService;
  @Autowired
  private PickAreaService pickAreaService;
  @Autowired
  private SectionService sectionService;

  @ApiOperation(value = "查询作业点列表，查询结果集包括分区与区段")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getJobPoints(@RequestParam("orgUuid")
  String orgUuid) {
    Assert.assertArgumentNotNull(orgUuid, "orgUuid");

    RespBean resp = new RespBean();

    QueryParam param = new QueryParam();
    param.put("orgUuid", orgUuid);
    try {
      List<JobPoint> jobPoints = jobPointService.queryByList(param);
      resp.setObj(jobPoints);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "新增作业点")
  @RequestMapping(method = RequestMethod.POST)
  public @ResponseBody
  RespBean addJobPoint(@RequestBody
  SJobPoint jobPoint) {
    Assert.assertArgumentNotNull(jobPoint, "jobPoint");

    RespBean resp = new RespBean();

    try {
      String uuid = jobPointService.insert(convertSJobPoint(jobPoint));
      resp.setObj(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "修改作业点")
  @RequestMapping(method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editJobPoint(@RequestBody
  SJobPoint jobPoint) {
    Assert.assertArgumentNotNull(jobPoint, "jobPoint");

    RespBean resp = new RespBean();

    try {
      jobPointService.update(convertSJobPoint(jobPoint));
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private JobPoint convertSJobPoint(SJobPoint source) {
    JobPoint target = new JobPoint();
    target.setCode(source.getCode());
    target.setName(source.getName());
    target.setOrgUuid(source.getOrgUuid());
    target.setTemplateUuid(source.getTemplateUuid());
    target.setUuid(source.getUuid());
    if (source.getsGateway() != null)
      target.setsGateway(new SGateway(source.getsGateway().getUuid(), source.getsGateway()
          .getCode(), source.getsGateway().getIp(), source.getsGateway().getPort()));
    return target;
  }

  @ApiOperation(value = "根据UUID获取作业点")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getJobPoint(@PathVariable("uuid")
  String uuid) {
    Assert.assertArgumentNotNull(uuid, "uuid");

    RespBean resp = new RespBean();

    try {
      JobPoint jobPoint = jobPointService.getByUuid(uuid);
      resp.setObj(jobPoint);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "删除作业点")
  @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deleteJobPoint(@PathVariable("uuid")
  String uuid) {
    Assert.assertArgumentNotNull(uuid, "uuid");

    RespBean resp = new RespBean();

    try {
      jobPointService.delete(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  // ----------------------------------------------------------------------------
  @ApiOperation(value = "查询分区列表，查询结果集包括区段")
  @RequestMapping(value = "/pickarea/list", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getPickAreas(@RequestParam("jobPointUuid")
  String jobPointUuid) {
    Assert.assertArgumentNotNull(jobPointUuid, "jobPointUuid");

    RespBean resp = new RespBean();

    QueryParam param = new QueryParam();
    param.put("jobPointUuid", jobPointUuid);
    try {
      List<PickArea> pickAreas = pickAreaService.queryByList(param);
      resp.setObj(pickAreas);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "新增分区")
  @RequestMapping(value = "/pickarea", method = RequestMethod.POST)
  public @ResponseBody
  RespBean addPickArea(@RequestBody
  SPickArea pickArea) {
    Assert.assertArgumentNotNull(pickArea, "pickArea");

    RespBean resp = new RespBean();

    try {
      String uuid = pickAreaService.insert(convertSPickArea(pickArea));
      resp.setObj(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "修改分区")
  @RequestMapping(value = "/pickarea", method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editPickArea(@RequestBody
  SPickArea pickArea) {
    Assert.assertArgumentNotNull(pickArea, "pickArea");

    RespBean resp = new RespBean();

    try {
      pickAreaService.update(convertSPickArea(pickArea));
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  private PickArea convertSPickArea(SPickArea source) {
    PickArea target = new PickArea();
    target.setCode(source.getCode());
    target.setJobPointUuid(source.getJobPointUuid());
    target.setName(source.getName());
    target.setUuid(source.getUuid());
    return target;
  }

  @ApiOperation(value = "根据UUID获取分区")
  @RequestMapping(value = "/pickarea/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getPickArea(@PathVariable("uuid")
  String uuid) {
    Assert.assertArgumentNotNull(uuid, "uuid");

    RespBean resp = new RespBean();

    try {
      PickArea pickArea = pickAreaService.getByUuid(uuid);
      resp.setObj(pickArea);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "删除分区")
  @RequestMapping(value = "/pickarea/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deletePickArea(@PathVariable("uuid")
  String uuid) {
    Assert.assertArgumentNotNull(uuid, "uuid");

    RespBean resp = new RespBean();

    try {
      pickAreaService.delete(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  // ----------------------------------------------------------------------------
  @ApiOperation(value = "查询区段列表")
  @RequestMapping(value = "/section/list", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getSections(@RequestParam("pickAreaUuid")
  String pickAreaUuid) {
    Assert.assertArgumentNotNull(pickAreaUuid, "pickAreaUuid");

    RespBean resp = new RespBean();

    QueryParam param = new QueryParam();
    param.put("pickAreaUuid", pickAreaUuid);
    try {
      List<Section> sections = sectionService.queryByList(param);
      resp.setObj(sections);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "新增区段")
  @RequestMapping(value = "/section", method = RequestMethod.POST)
  public @ResponseBody
  RespBean addSection(@RequestBody
  Section section) {
    Assert.assertArgumentNotNull(section, "section");

    RespBean resp = new RespBean();

    try {
      String uuid = sectionService.insert(section);
      resp.setObj(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "修改区段")
  @RequestMapping(value = "/section", method = RequestMethod.PUT)
  public @ResponseBody
  RespBean editSection(@RequestBody
  Section section) {
    Assert.assertArgumentNotNull(section, "section");

    RespBean resp = new RespBean();

    try {
      sectionService.update(section);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "根据UUID获取区段")
  @RequestMapping(value = "/section/{uuid}", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getSection(@PathVariable("uuid")
  String uuid) {
    Assert.assertArgumentNotNull(uuid, "uuid");

    RespBean resp = new RespBean();

    try {
      Section section = sectionService.getByUuid(uuid);
      resp.setObj(section);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "删除区段")
  @RequestMapping(value = "/section/{uuid}", method = RequestMethod.DELETE)
  public @ResponseBody
  RespBean deleteSection(@PathVariable("uuid")
  String uuid) {
    Assert.assertArgumentNotNull(uuid, "uuid");

    RespBean resp = new RespBean();

    try {
      sectionService.delete(uuid);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

  @ApiOperation(value = "根据标签类型查询可用的电子标签")
  @RequestMapping(value = "/unuse_eletags", method = RequestMethod.GET)
  public @ResponseBody
  RespBean getAvailableELeTags(@RequestParam("orgUuid")
  String orgUuid, @RequestParam("type")
  String type, @RequestParam("gatewayUuid")
  String gatewayUuid, @RequestParam("facilityTemplateUuid")
  String facilityTemplateUuid) {
    RespBean resp = new RespBean();

    try {
      Assert.assertArgumentNotNull(orgUuid, "orgUuid");
      Assert.assertArgumentNotNull(type, "type");
      Assert.assertArgumentNotNull(gatewayUuid, "gatewayUuid");
      Assert.assertArgumentNotNull(facilityTemplateUuid, "facilityTemplateUuid");

      QueryParam param = new QueryParam();
      param.put("type", type);
      param.put("orgUuid", orgUuid);
      param.put("gatewayUuid", gatewayUuid);
      param.put("facilityTemplateUuid", facilityTemplateUuid);

      List<String> eleTags = jobPointService.queryAvaliableEleTags(param);
      resp.setObj(eleTags);
    } catch (Exception e) {
      resp.setSuccess(false);
      resp.setMsg(e.getMessage());
    }

    return resp;
  }

}