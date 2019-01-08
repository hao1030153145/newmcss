package com.transing.mcss4dpm.biz.service.impl.local;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.biz.service.BaseService;
import com.jeeframework.logicframework.integration.dao.DAOException;
import com.transing.mcss4dpm.biz.service.UserService;
import com.transing.mcss4dpm.integration.UserDataService;
import com.transing.mcss4dpm.integration.bo.User;
import com.transing.mcss4dpm.web.filter.GetUsersFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lanceyan
 * @version 1.0
 */
@Service("userService")
public class UserServicePojo extends BaseService implements UserService {
    @Resource
    private UserDataService userDataService;

    @Override
    public List<User> getUsers(GetUsersFilter getUsersFilter) {
        try {
            return userDataService.getUsers(getUsersFilter);
        } catch (DAOException e) {
            throw new BizException(e);

        }
    }

    @Override
    public long getUsersCount(GetUsersFilter getUsersFilter) {
        try {
            return userDataService.getUsersCount(getUsersFilter);
        } catch (DAOException e) {
            throw new BizException(e);

        }
    }

}