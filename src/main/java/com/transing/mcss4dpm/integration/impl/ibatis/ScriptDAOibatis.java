package com.transing.mcss4dpm.integration.impl.ibatis;

import com.jeeframework.logicframework.integration.dao.DAOException;
import com.jeeframework.logicframework.integration.dao.ibatis.BaseDaoiBATIS;
import com.transing.mcss4dpm.integration.ScriptDataService;
import com.transing.mcss4dpm.integration.bo.ContactBO;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
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
public class ScriptDAOibatis extends BaseDaoiBATIS implements ScriptDataService {

    @Override
    public List<ContactBO> getContactList() {
        try {
            return sqlSessionTemplate.selectList("scriptMapper.getContactList");
        } catch (DataAccessException e) {
            throw new DAOException("获得数据库的通讯录列表", e);
        }
    }
}
