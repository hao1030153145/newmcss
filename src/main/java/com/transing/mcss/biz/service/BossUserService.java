package com.transing.mcss.biz.service;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.biz.service.BizService;
import com.transing.mcss.integration.bo.BossUser;
import com.transing.mcss.web.filter.BossUserFilter;

import java.util.List;

/**
 * 
 * 
 * @author lanceyan
 */
public interface BossUserService extends BizService {

    public BossUser getBossUser(BossUserFilter bossUserFilter) throws BizException;

    public int addBossUser(BossUser bossUser) throws BizException;

    public int updateBossUser(BossUserFilter bossUserFilter) throws BizException;


    public void deleteBossUser(String mobile) throws BizException;

    /**
     * 简单描述：根据用户名、密码返回用户对象
     * <p>
     * 
     * @param bossUser
     * @throws BizException
     */
    public BossUser getBossUserByPasswd(BossUser bossUser) throws BizException;

    /**
     * 简单描述：根据userFilter返回用户对象列表
     * <p>
     * 
     * @param bossUserFilter
     * @throws BizException
     */
    public List<BossUser> getBossUsers(BossUserFilter bossUserFilter) throws BizException;


}