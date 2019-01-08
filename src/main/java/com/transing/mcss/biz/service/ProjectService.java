package com.transing.mcss.biz.service;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.biz.service.BizService;
import com.transing.mcss.integration.bo.ProjectBO;
import com.transing.mcss.integration.bo.TaskBO;
import com.transing.mcss.integration.bo.WeixinBrandBO;
import com.transing.mcss.integration.bo.WeixinCommentBO;

import java.util.List;


public interface ProjectService extends BizService {
    long getProjectId(long taskId) throws BizException;

    List<TaskBO> getTaskList() throws BizException;

    Integer addWeixinBrand(WeixinBrandBO weixinBrandBO)throws BizException;

    Integer updateTaskCrawlNum(long taskId)throws BizException;

    Integer updateTaskStatus(long taskId)throws BizException;

    Integer updateTaskStatusBegin(long taskId)throws BizException;

    ProjectBO getProjectById(long projectId)throws BizException;

    Integer updateProjectById(ProjectBO projectBO)throws BizException;

    Integer insertWeixinCommentList(List<WeixinCommentBO> weixinCommentBOList)throws BizException;

    Integer insertWeixinComment(WeixinCommentBO weixinCommentBO)throws BizException;

//    void addWeixinBrand() throws BizException;
}
