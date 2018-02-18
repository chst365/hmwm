package com.hd123.hema.store.dao.facility.gateway;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.hd123.hema.store.bean.facility.gateway.Gateway;
import com.hd123.hema.store.dao.AbstractDataAccessTests;
import com.hd123.wms.antman.common.query.PageQueryDefinition;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DatabaseSetup({"./InitGateway.xml" })
public class GatewayDaoTest extends AbstractDataAccessTests {
    @Autowired
    private GatewayDao gatewayDao;

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertGateway.xml")
    public void insert() {
        Gateway gateway = new Gateway();
        gateway.setUuid("bfc42e1c52a84e45a447c893935a700e");
        gateway.setCode("Code004");
        gateway.setIp("187.1.233.14");
        gateway.setPort("8888");
        gateway.setTemplateUuid("3c3494bcab0b4b17aaf4a3798d23ac9c");
        gateway.setOrgUuid("2a375856ba7d4493a0253221605abd0e");
        gatewayDao.insert(gateway);
    }

    @Test
    public void getByUuid() {
        Gateway gateway = gatewayDao.getByUuid("587f0bc087cb4defa48ae7f5da0506ce");
        Assert.assertEquals("587f0bc087cb4defa48ae7f5da0506ce", gateway.getUuid());
        Assert.assertEquals("Code002", gateway.getCode());
        Assert.assertEquals("192.168.23.15", gateway.getIp());
        Assert.assertEquals("21", gateway.getPort());
        Assert.assertEquals("null", gateway.getTemplateUuid());
        Assert.assertEquals("2a375856ba7d4493a0253221605abd0e", gateway.getOrgUuid());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./UpdateGateway.xml")
    public void update() {
        Gateway gateway = gatewayDao.getByUuid("b7f776582b634a2697ab186163251b63");
        gateway.setCode("Code004");
        gateway.setIp("187.1.233.14");
        gateway.setPort("8888");
        gatewayDao.update(gateway);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteGateway.xml")
    public void delete() {
        gatewayDao.delete("b7f776582b634a2697ab186163251b63");
    }

    @Test
    public void queryByList() {
        List<Gateway> list = gatewayDao.queryByList(null);
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void queryByPage() {
        PageQueryDefinition page = new PageQueryDefinition();
        page.setPage(0);
        page.setPageSize(2);
        page.setPageCount(0);
        page.setRecordCount(1);
        List<Gateway> list = gatewayDao.queryByPage(page);
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void getByCode() {
        Gateway gateway = gatewayDao.getByCode("Code002",null,"2a375856ba7d4493a0253221605abd0e");
        Assert.assertEquals("587f0bc087cb4defa48ae7f5da0506ce", gateway.getUuid());
        Assert.assertEquals("Code002", gateway.getCode());
        Assert.assertEquals("192.168.23.15", gateway.getIp());
        Assert.assertEquals("21", gateway.getPort());
        Assert.assertEquals("null", gateway.getTemplateUuid());
        Assert.assertEquals("2a375856ba7d4493a0253221605abd0e", gateway.getOrgUuid());
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./InsertBatchGateway.xml")
    public void insertBatch() {
        List<Gateway> gateways = new ArrayList<>();

        Gateway gateway1 = new Gateway();
        gateway1.setUuid("bfc42e1c52a84e45a447c893935a700e");
        gateway1.setCode("Code004");
        gateway1.setIp("187.1.233.14");
        gateway1.setPort("8888");
        gateway1.setTemplateUuid("3c3494bcab0b4b17aaf4a3798d23ac9c");
        gateway1.setOrgUuid("2a375856ba7d4493a0253221605abd0e");

        Gateway gateway2 = new Gateway();
        gateway2.setUuid("c0c1bfa9c2d04f528e45e614f7bc6a6c");
        gateway2.setCode("Code005");
        gateway2.setIp("192.168.23.126");
        gateway2.setPort("12345");
        gateway2.setTemplateUuid("null");
        gateway2.setOrgUuid("85a2f360ca2a427e84284b107e6ccf63");

        //hsqldb不支持批量插入
        gateways.add(gateway1);
        gateways.add(gateway2);
        gatewayDao.insertBatch(gateways);
    }

    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT,
            value = "./DeleteGateway.xml")
    public void deleteByOrg() {
        gatewayDao.deleteByOrg("1a375856ba7d5493a0253221605abd0e");
    }

    @AfterClass
    public static void finishAll() {
        System.out.println("已全部完成。");
    }

}
