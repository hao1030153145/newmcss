package com.transing.mcss.integration;

import com.jeeframework.logicframework.integration.DataService;
import com.jeeframework.logicframework.integration.DataServiceException;
import com.transing.mcss.integration.bo.User;
import com.transing.mcss.web.filter.UserFilter;

import java.util.List;

/**
 * �û�db���ʲ����Ľӿ���
 * 
 * @author lanceyan�������޸��ߣ�
 * @version 1.0���°汾�ţ�
 * @see �ο���JavaDoc
 */
public interface UserDataService extends DataService {

    /**
     * 简单描述：根据userFilter返回用户对象列表
     * <p>
     * 
     * @param userFilter
     * @throws DataServiceException
     */
    public List<User> getUsers(UserFilter userFilter) throws DataServiceException;

    public long getUserCount(UserFilter userFilter) throws DataServiceException;


}