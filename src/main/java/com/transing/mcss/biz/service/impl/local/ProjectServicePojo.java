package com.transing.mcss.biz.service.impl.local;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.biz.service.BaseService;
import com.jeeframework.logicframework.integration.dao.DAOException;
import com.jeeframework.logicframework.util.logging.LoggerUtil;
import com.transing.mcss.biz.service.ProjectService;
import com.transing.mcss.integration.ProjectDataService;
import com.transing.mcss.integration.UserDataService;
import com.transing.mcss.integration.bo.ProjectBO;
import com.transing.mcss.integration.bo.TaskBO;
import com.transing.mcss.integration.bo.WeixinBrandBO;
import com.transing.mcss.integration.bo.WeixinCommentBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("projectService")
public class ProjectServicePojo extends BaseService implements ProjectService{
    @Resource
    private ProjectDataService projectDataService;

    @Override
    public long getProjectId(long taskId) throws BizException {
        try {
            return projectDataService.getProjectId(taskId);
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public List<TaskBO> getTaskList() throws BizException {
        try {
            return projectDataService.getTaskList();
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public Integer addWeixinBrand(WeixinBrandBO weixinBrandBO) throws BizException {
        try {
            return projectDataService.addWeixinBrand(weixinBrandBO);
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public Integer updateTaskCrawlNum(long taskId) throws BizException {
        try {
            return projectDataService.updateTaskCrawlNum(taskId);
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public Integer updateTaskStatus(long taskId) throws BizException {
        try {
            return projectDataService.updateTaskStatus(taskId);
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public Integer updateTaskStatusBegin(long taskId) throws BizException {
        try {
            return projectDataService.updateTaskStatusBegin(taskId);
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public ProjectBO getProjectById(long projectId) throws BizException {
        try {
            return projectDataService.getProjectById(projectId);
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public Integer updateProjectById(ProjectBO projectBO) throws BizException {
        try {
            return projectDataService.updateProjectById(projectBO);
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public Integer insertWeixinCommentList(List<WeixinCommentBO> weixinCommentBOList) throws BizException {
        try {
            return projectDataService.insertWeixinCommentList(weixinCommentBOList);
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }

    @Override
    public Integer insertWeixinComment(WeixinCommentBO weixinCommentBO) throws BizException {
        try {
            return projectDataService.insertWeixinComment(weixinCommentBO);
        } catch (DAOException e) {
            throw new BizException(e);
        }
    }

//    @Override
//    public void addWeixinBrand() throws BizException {
//        try {
//            projectDataService.addWeixinBrand();
//        } catch (DAOException e) {
//            LoggerUtil.errorTrace("addUser", "添加用户出错", e);
//            throw new BizException("添加用户数据库出错" + e, e);
//        }
//    }
}
