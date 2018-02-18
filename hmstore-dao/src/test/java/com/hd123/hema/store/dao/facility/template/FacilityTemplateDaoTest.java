package com.hd123.hema.store.dao.facility.template;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.template.FacilityTemplate;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import com.hd123.wms.antman.common.query.QueryParam;

/**
 * @author xiepingping
 * 
 */
@DatabaseSetup({
  "InitData.xml" })
public class FacilityTemplateDaoTest extends AbstractDataAccessTests {

  @Autowired
  private FacilityTemplateDao templateDao;

  @Test
  public void getByUuid() {
    FacilityTemplate template = templateDao.getByUuid("1001");

    assertNotNull(template);
    Assert.assertEquals("1001", template.getUuid());
    Assert.assertEquals("ST001", template.getCode());
    Assert.assertEquals("设施模板1", template.getName());

  }

  @Test
  public void getByCode() {
    FacilityTemplate template = templateDao.getByCode("ST001");

    assertNotNull(template);
    Assert.assertEquals("1001", template.getUuid());
    Assert.assertEquals("ST001", template.getCode());
    Assert.assertEquals("设施模板1", template.getName());

  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "DeleteTemplate.xml")
  public void delete() {
    templateDao.delete("1001");
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "InsertTemplate.xml")
  public void insert() throws ParseException {
    FacilityTemplate template = new FacilityTemplate();
    template.setUuid("1004");
    template.setCode("ST004");
    template.setName("设施模板4");
    templateDao.insert(template);
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "InsertBatchTemplate.xml")
  public void insertBatch() throws ParseException {
    List<FacilityTemplate> templates = new ArrayList<FacilityTemplate>();
    FacilityTemplate template1 = new FacilityTemplate();
    template1.setUuid("1004");
    template1.setCode("ST004");
    template1.setName("设施模板4");
    templates.add(template1);

    FacilityTemplate template2 = new FacilityTemplate();
    template2.setUuid("1005");
    template2.setCode("ST005");
    template2.setName("设施模板5");
    templates.add(template2);
    templateDao.insertBatch(templates);
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "UpdateTemplate.xml")
  public void update() throws ParseException {
    FacilityTemplate template = templateDao.getByUuid("1003");
    assertNotNull(template);
    template.setCode("ST003改");
    template.setName("设施模板3改");
    templateDao.update(template);
  }

  @Test
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
      value = "QueryListTemplate.xml")
  public void querylist() {
    QueryParam param = new QueryParam();
    // 在FacilityTemplateMapper.xml 文件中没有用到传进去的QueryParam参数，但是由于方法格式要传参
    templateDao.queryByList(param);
  }

  @Test
  public void querypage() {
    PageQueryDefinition filter = new PageQueryDefinition();
    int page = 1;
    int pageSize = 2;
    filter.setPage(page);
    filter.setPageSize(pageSize);

    List<FacilityTemplate> templates = templateDao.queryByPage(filter);
    Assert.assertEquals(2, templates.size());
  }

}
