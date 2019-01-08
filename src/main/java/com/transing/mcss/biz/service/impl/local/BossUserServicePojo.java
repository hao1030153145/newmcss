package com.transing.mcss.biz.service.impl.local;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.biz.service.BaseService;
import com.jeeframework.logicframework.util.logging.LoggerUtil;
import com.jeeframework.logicframework.integration.dao.DAOException;
import com.transing.mcss.biz.service.BossUserService;
import com.transing.mcss.integration.BossUserDataService;
import com.transing.mcss.integration.bo.BossUser;
import com.transing.mcss.web.filter.BossUserFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 *
 * @author lanceyan
 */
@Service("bossUserService")
public class BossUserServicePojo extends BaseService implements BossUserService {
    public static int refCount = 0; 
    public BossUserServicePojo() { 
        refCount++;
    }

    protected void finalize() throws Throwable {
        super.finalize();
 
        refCount--;
    }

    @Resource
    private BossUserDataService bossUserDataService;


    public int addBossUser(BossUser bossUser) throws BizException {
        try {
            return bossUserDataService.addBossUser(bossUser);
        } catch (DAOException e) {
            LoggerUtil.errorTrace("addBossUser", "添加用户出错", e);
            throw new BizException("添加用户数据库出错" + e, e);
        } finally {
        }
    }


    public void deleteBossUser(String mobile) throws BizException {
        try {
            bossUserDataService.deleteBossUser(mobile);
        } catch (DAOException e) {
            LoggerUtil.errorTrace("deleteBossUser", "删除用户出错" + e, e);
            throw new BizException(e);
        } finally {
        }
    }


    public int updateBossUser(BossUserFilter bossUserFilter) throws BizException {
        try {
            return bossUserDataService.updateBossUser(bossUserFilter);
        } catch (DAOException e) {
            LoggerUtil.errorTrace("updateBossUser", "修改用户出错", e);
            throw new BizException("修改用户数据库出错" + e, e);
        } finally {
        }
    }

    public BossUserDataService getBossUserDataService() {
        return bossUserDataService;
    }

    public void setBossUserDataService(BossUserDataService bossUserDataService) {
        this.bossUserDataService = bossUserDataService;
    }


    public int getObjCount() {
        return refCount;
    }

    /* 
     * 简单描述：TODO
     * <p>
     * 详细描述：TODO
     *
     * @see BossUserService#getBossUserByPasswd(BossUser)
     */
    public BossUser getBossUserByPasswd(BossUser bossUser) throws BizException {
        try {
            return bossUserDataService.getBossUserByPasswd(bossUser);
        } catch (DAOException e) {
            LoggerUtil.errorTrace("getBossUserByPasswd", "根据用户名密码查询用户出错", e);
            throw new BizException("根据用户名密码查询用户数据库出错" + e, e);
        } finally {
        }
    }


    public BossUser getBossUser(BossUserFilter bossUserFilter) throws BizException {
        try {
            return bossUserDataService.getBossUser(bossUserFilter);
        } catch (DAOException e) {
            LoggerUtil.errorTrace("getBossUser", "根据用户查询用户出错", e);
            throw new BizException("根据用户查询用户数据库出错" + e, e);
        } finally {
        }
    }

    public List<BossUser> getBossUsers(BossUserFilter bossUserFilter) throws BizException {
        try {
            return bossUserDataService.getBossUsers(bossUserFilter);
        } catch (DAOException e) {
            LoggerUtil.errorTrace("addBossUser", "添加用户出错", e);
            throw new BizException("添加用户数据库出错" + e, e);
        } finally {
        }
    }


}