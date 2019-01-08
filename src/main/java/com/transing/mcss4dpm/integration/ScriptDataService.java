package com.transing.mcss4dpm.integration;

import com.jeeframework.logicframework.integration.DataService;
import com.transing.mcss4dpm.integration.bo.ContactBO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2019/1/7
 */
@Scope("prototype")
@Repository("scriptDataService")
public interface ScriptDataService extends DataService {

    List<ContactBO> getContactList();

}
