package com.hd123.hema.store.dao.facility.jobpoint;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.jobpoint.Section;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DatabaseSetup({"./InitSection.xml" })
public class SectionDaoTest extends AbstractDataAccessTests {
    @Autowired
    private SectionDao sectionDao;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertSection.xml")
    public void insert() {
        Section section = new Section();
        section.setUuid("072f088e46554f82a686b250b66e14ec");
        section.setCode("区段001");
        section.setName("区段001");
        section.setPickAreaUuid("b94fb0d1138f4628bb67befab794a461");
        sectionDao.insert(section);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdateSection.xml")
    public void update() {
        Section section = sectionDao.getByUuid("04d5e135f10d4512aee046c2136bbd65");
        section.setCode("区段001");
        section.setName("区段001");
        sectionDao.update(section);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteSection.xml")
    public void delete() {
        sectionDao.delete("04d5e135f10d4512aee046c2136bbd65");
    }

    @Test
    public void getByUuid() {
        Section section = sectionDao.getByUuid("03701d0936ab4188a81f97cf1755c497");
        Assert.assertEquals("03701d0936ab4188a81f97cf1755c497", section.getUuid());
        Assert.assertEquals("区段02", section.getCode());
        Assert.assertEquals("区段02", section.getName());
        Assert.assertEquals("946eee8d69bb40a98a59ab9d991d7430", section.getPickAreaUuid());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<Section> list = sectionDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryByList() {
        List<Section> list = sectionDao.queryByList(null);
        Assert.assertEquals(2, list.size());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
            value = "./InsertBatchSection.xml")
    public void insertBatch() {
        List<Section> sections = new ArrayList<>();

        Section section1 = new Section();
        section1.setUuid("072f088e46554f82a686b250b66e14ec");
        section1.setCode("区段001");
        section1.setName("区段001");
        section1.setPickAreaUuid("b94fb0d1138f4628bb67befab794a461");

        Section section2 = new Section();
        section2.setUuid("085c8ed3ff0442c6ba0f595e885eb2f5");
        section2.setCode("100104");
        section2.setName("1001区段-4");
        section2.setPickAreaUuid("6d6634e10d494748a8184ac95f39935f");

        //hsqldb不支持批量插入
        sections.add(section1);
        sections.add(section2);
        sectionDao.insertBatch(sections);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteSection.xml")
    public void deleteByPickArea() {
        sectionDao.deleteByPickArea("3578f73487ae49308e5e74a30f0c1113");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
