/**
 * @project: mcss
 * @Title: UserController.java
 * @Package: com.transing.mcss.web.controller
 * <p>
 * Copyright (c) 2014-2017 Jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss.web.controller;

import com.jeeframework.util.format.DateFormat;
import com.jeeframework.util.validate.Validate;
import com.jeeframework.webframework.exception.WebException;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.transing.mcss.biz.service.BossUserService;
import com.transing.mcss.biz.service.ProjectService;
import com.transing.mcss.biz.service.UserService;
import com.transing.mcss.integration.bo.User;
import com.transing.mcss.util.WebUtil;
import com.transing.mcss.web.exception.MySystemCode;
import com.transing.mcss.web.filter.UserFilter;
import com.transing.mcss.web.form.UserQueryRequest;
import com.transing.mcss.web.po.AppVersionPO;
import com.transing.mcss.web.po.UserPO;
import com.transing.mcss.web.po.UserPOJQGridResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Controller("userController")
@Api(value = "系统用户管理", description = "系统用户管理相关的访问接口", position = 2)
public class UserController {

    private final static String COLON = ":";
    private final static String API_DOC = "/api-docs";

    @Resource
    private BossUserService bossUserService;
    @Resource
    private UserService userService;
    @Resource
    private ProjectService projectService;

    @RequestMapping(value = "/userList.html", method = RequestMethod.GET)
    @ResponseBody
    //@ApiOperation(value = "用户列表查询界面", position = 0)
	@ApiIgnore //忽略这个api生成文档
    public ModelAndView userList(HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> retMap = new HashMap<String, Object>();
        req.setAttribute("result", retMap);

        return new ModelAndView("user/userList");
    }

	@RequestMapping(value = "/getAppVersion.json", method = {RequestMethod.GET })
    @ResponseBody
    @ApiOperation(value = "得到程序的版本号", notes = "", response = AppVersionPO.class, position = 0)
    public AppVersionPO getAppVersionJSON(@RequestParam("appVersion") @ApiParam(value = "查询服务器程序的版本号") String appVersion)
            throws Exception {


        AppVersionPO appVersionPORet = new AppVersionPO();

        if (Validate.isEmpty(appVersion)) {
            throw new WebException(MySystemCode.BIZ_APPVERSION_EXCEPTION);
        }

        appVersionPORet.setVersion("1.0.0");
        appVersionPORet.setDescription("当前程序版本为1.0.0");

        return appVersionPORet;
    }

    @RequestMapping(value = "/userList.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户列表查询界面", position = 1)
	/**
     * 注意这个类使用了 JQGrid ，所以构造参数使用 RequestBody UserQueryRequest，但是并不建议这样使用
     */
    public UserPOJQGridResponse userListJSON(@RequestBody UserQueryRequest userQueryRequest, HttpServletRequest req, HttpServletResponse res) {

        UserPOJQGridResponse response = new UserPOJQGridResponse();
//            page ： 当前页
//            rows ： 每页记录数
//            sidx ： sort index，排序字段名
//            sord ： sort direction，排序方式：asc， desc

        int currentPage = userQueryRequest.getPage();
        int rows = userQueryRequest.getRows();
        String sortColumnName = userQueryRequest.getSidx();
        String sortDirect = userQueryRequest.getSord();

        UserFilter userFilter = new UserFilter();

        String nickName = userQueryRequest.getNickName();

        if (!Validate.isEmpty(nickName)) {
            userFilter.setNickName(nickName.trim());
        }

        String createTimeTmp = userQueryRequest.getCreateTime();

        if (!Validate.isEmpty(createTimeTmp)) {

            Date createTimeDate = null;
            try {
                createTimeDate = DateFormat.parseDate(createTimeTmp, DateFormat.DT_YYYY_MM_DD);
            } catch (Exception e) {
                e.printStackTrace();
            }
            userFilter.setGreatCreateTime(DateFormat.formatDate(createTimeDate, DateFormat.DT_YYYY_MM_DD_HHMMSS));

            Calendar cal = Calendar.getInstance();
            cal.setTime(createTimeDate);
            cal.add(Calendar.DATE, 1);

            userFilter.setLessCreateTime(DateFormat.formatDate(cal.getTime(), DateFormat.DT_YYYY_MM_DD_HHMMSS));
        }


        if (!Validate.isEmpty(sortColumnName)) {
            userFilter.setOrderBy(sortColumnName);

            if (!Validate.isEmpty(sortDirect)) {
                userFilter.setOrderDirect(sortDirect);
            }
        }

        long rowCount = userService.getUserCount(userFilter);

        //设置分页参数
        int startRow = (currentPage - 1) * rows;
        userFilter.setStartRow(startRow);
        userFilter.setPageSize(rows);
        List<User> userList = userService.getUsers(userFilter);

        List<UserPO> userPOList = new ArrayList<UserPO>();
        for (User user : userList) {
            UserPO userPO = new UserPO();

            try {
                BeanUtils.copyProperties(userPO, user);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            String sexTmp = "未知";
            int sexInt = user.getSex();
            if (sexInt == User.SEX_MALE) {
                sexTmp = "男";
            } else if (sexInt == User.SEX_FEMALE) {
                sexTmp = "女";
            }

            userPO.setAvatar(WebUtil.getUserAvatar(user.getAvatar(), 120));
            userPO.setCreateTime(DateFormat.formatDate(user.getCreateTime(), DateFormat.DT_YYYY_MM_DD));
            userPO.setSex(sexTmp);
            userPOList.add(userPO);
        }

        int totalPage = (int) (rowCount + rows - 1) / rows; //total page
        response.setTotal(totalPage);
        int curPage = Math.min(totalPage, currentPage); //current page
        response.setPage(curPage);

        ArrayList<UserPO> objects = new ArrayList<UserPO>();
        objects.addAll(userPOList);
        response.setRecords(objects.size());
        response.setRows(objects);

        return response;

    }

}
