package com.transing.mcss4dpm.biz.service;

import com.jeeframework.logicframework.biz.service.BizService;
import com.transing.mcss4dpm.integration.bo.ContactBO;

import java.util.List;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2019/1/7
 */
public interface ScriptService extends BizService {

    List<ContactBO> getContactList();

}
