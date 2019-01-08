package com.transing.mcss.integration.impl.ibatis;

import com.jeeframework.logicframework.integration.DataServiceException;
import com.jeeframework.logicframework.integration.dao.DAOException;
import com.jeeframework.logicframework.integration.dao.ibatis.BaseDaoiBATIS;
import com.transing.mcss.integration.ProjectDataService;
import com.transing.mcss.integration.bo.ProjectBO;
import com.transing.mcss.integration.bo.TaskBO;
import com.transing.mcss.integration.bo.WeixinBrandBO;
import com.transing.mcss.integration.bo.WeixinCommentBO;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Scope("prototype")
@Repository("ProjectService")
public class ProjectDAOIbatis extends BaseDaoiBATIS implements ProjectDataService {
    @Override
    public long getProjectId(long taskId) throws DataServiceException {
        try {
            return sqlSessionTemplate.selectOne("projectMapper.getProject",taskId);
        } catch (DataAccessException e) {
            throw new DAOException("根据token查询用户信息失败", e);
        }
    }

    @Override
    public List<TaskBO> getTaskList() throws DataServiceException {
        try {
            return sqlSessionTemplate.selectList("projectMapper.getTaskList");
        } catch (DataAccessException e) {
            throw new DAOException("查询任务列表", e);
        }
    }

    @Override
    public Integer addWeixinBrand(WeixinBrandBO weixinBrandBO) throws DataServiceException {
        try {
            return sqlSessionTemplate.insert("projectMapper.addWeixinBrand", weixinBrandBO);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer updateTaskCrawlNum(long taskId) throws DataServiceException {
        try {
            return sqlSessionTemplate.update("projectMapper.updateTaskCrawlNum", taskId);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer updateTaskStatus(long taskId) throws DataServiceException {
        try {
            return sqlSessionTemplate.update("projectMapper.updateTaskStatus", taskId);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer updateTaskStatusBegin(long taskId) throws DataServiceException {
        try {
            return sqlSessionTemplate.update("projectMapper.updateTaskStatusBegin", taskId);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ProjectBO getProjectById(long projectId) throws DataServiceException {
        try {
            return sqlSessionTemplate.selectOne("projectMapper.getProjectById", projectId);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer updateProjectById(ProjectBO projectBO) throws DataServiceException {
        try {
            return sqlSessionTemplate.update("projectMapper.updateProjectById", projectBO);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer insertWeixinCommentList(List<WeixinCommentBO> weixinCommentBOList) throws DataServiceException {
        try {
            return sqlSessionTemplate.insert("projectMapper.insertWeixinCommentList", weixinCommentBOList);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Integer insertWeixinComment(WeixinCommentBO weixinCommentBO) throws DataServiceException {
        try {
            return sqlSessionTemplate.insert("projectMapper.insertWeixinComment", weixinCommentBO);
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

//    @Override
//    public void addWeixinBrand() throws DataServiceException {
//        try {
//            sqlSessionTemplate.insert("projectMapper.addWeixinBrand");
//        } catch (DataAccessException e) {
//            throw new DAOException("根据token查询用户信息失败", e);
//        }
//    }
}
