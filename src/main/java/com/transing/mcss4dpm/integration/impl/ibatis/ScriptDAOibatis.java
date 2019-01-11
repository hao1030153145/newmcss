package com.transing.mcss4dpm.integration.impl.ibatis;

import com.jeeframework.logicframework.integration.dao.DAOException;
import com.jeeframework.logicframework.integration.dao.ibatis.BaseDaoiBATIS;
import com.transing.mcss4dpm.biz.service.impl.api.bo.WechatBO;
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
            throw new DAOException("获得数据库的通讯录列表失败", e);
        }
    }

    @Override
    public List<WechatBO> getWeChatData() {
        try {
            return sqlSessionTemplate.selectList("scriptMapper.getWeChatData");
        } catch (DataAccessException e) {
            throw new DAOException("获得数据库的微信数据失败", e);
        }
    }

    @Override
    public Integer getWeChatDataCount() {
        try {
            return sqlSessionTemplate.selectOne("scriptMapper.getWeChatDataCount");
        } catch (DataAccessException e) {
            throw new DAOException("获得数据库的微信数据数量失败", e);
        }
    }

    @Override
    public Integer insertWeChatData(WechatBO wechatBO) {
        try {
            return sqlSessionTemplate.insert("scriptMapper.insertWeChatData",wechatBO);
        } catch (DataAccessException e) {
            throw new DAOException("向数据库插入微信数据失败", e);
        }
    }
}
