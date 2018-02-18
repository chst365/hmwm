/**
 * 业务公用实现。
 * 
 * 项目名：	hmstore-xi
 * 文件名：	PickSchemePullController.java
 * 模块说明：
 * 修改历史：
 * 2016-8-1 - xiepingping - 创建。
 */
package com.hd123.hema.store.xi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hd123.wms.mm.control.rest.RestReceiveController;

/**
 * @author xiepingping
 *
 */
@RestController
@RequestMapping(value = "/pullpickscheme")
public class PickSchemePullController extends RestReceiveController {

}
