package com.transing.mcss4dpm.integration;

import com.jeeframework.logicframework.integration.DataService;
import com.transing.mcss4dpm.biz.service.impl.api.bo.WechatBO;
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

    // 查询联系人数据
    List<ContactBO> getContactList();

    // 查询微信数据
    List<WechatBO> getWeChatData();

    // 查询微信数据的数量
    Integer getWeChatDataCount();

    // 插入微信数据
    Integer insertWeChatData(WechatBO wechatBO);

}
