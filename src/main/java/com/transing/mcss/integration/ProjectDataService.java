package com.transing.mcss.integration;


import com.jeeframework.logicframework.integration.DataService;
import com.jeeframework.logicframework.integration.DataServiceException;
import com.transing.mcss.integration.bo.ProjectBO;
import com.transing.mcss.integration.bo.TaskBO;
import com.transing.mcss.integration.bo.WeixinBrandBO;
import com.transing.mcss.integration.bo.WeixinCommentBO;

import java.util.List;

public interface ProjectDataService extends DataService {
    long getProjectId(long taskId) throws DataServiceException;

    List<TaskBO> getTaskList() throws DataServiceException;

    Integer addWeixinBrand(WeixinBrandBO weixinBrandBO)throws DataServiceException;

    Integer updateTaskCrawlNum(long taskId)throws DataServiceException;

    Integer updateTaskStatus(long taskId)throws DataServiceException;

    Integer updateTaskStatusBegin(long taskId)throws DataServiceException;

    ProjectBO getProjectById(long projectId)throws DataServiceException;

    Integer updateProjectById(ProjectBO projectBO)throws DataServiceException;

    Integer insertWeixinCommentList(List<WeixinCommentBO> weixinCommentBOList)throws DataServiceException;

    Integer insertWeixinComment(WeixinCommentBO weixinCommentBO)throws DataServiceException;

//    void addWeixinBrand() throws DataServiceException;
}
