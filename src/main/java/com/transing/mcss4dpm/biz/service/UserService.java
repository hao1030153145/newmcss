package com.transing.mcss4dpm.biz.service;

import com.jeeframework.logicframework.biz.service.BizService;
import com.transing.mcss4dpm.integration.bo.User;
import com.transing.mcss4dpm.web.filter.GetUsersFilter;

import java.util.List;

/**
 * @author lanceyan
 * @version 1.0
 */
public interface UserService extends BizService {

    /**
     * 简单描述：根据userFilter返回用户对象列表
     * <p/>
     *
     * @param getUsersFilter
     * @
     */
    List<User> getUsers(GetUsersFilter getUsersFilter);
    /**
     * 简单描述：根据userFilter返回用户列表数量
     * <p/>
     *
     * @param getUsersFilter
     * @
     */
    long getUsersCount(GetUsersFilter getUsersFilter);

}