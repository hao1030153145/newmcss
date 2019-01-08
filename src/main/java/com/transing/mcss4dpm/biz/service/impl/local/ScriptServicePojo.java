package com.transing.mcss4dpm.biz.service.impl.local;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.biz.service.BaseService;
import com.jeeframework.logicframework.integration.dao.DAOException;
import com.transing.mcss4dpm.biz.service.ScriptService;
import com.transing.mcss4dpm.integration.ScriptDataService;
import com.transing.mcss4dpm.integration.bo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2019/1/7
 */
@Service("scriptService")
public class ScriptServicePojo extends BaseService implements ScriptService {

    @Resource
    private ScriptDataService scriptDataService;

    @Override
    public List<ContactBO> getContactList() {
        try {
            return scriptDataService.getContactList();
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }
}
