/**
 * @project: mcss4dpm
 * @Title: MySwaggerConfig.java
 * @Package: com.transing.mcss4dpm.web.swagger
 * <p>
 * Copyright (c) 2014-2017 Jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.web.apidoc;

import com.jeeframework.webframework.controller.AbstractSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import org.springframework.context.annotation.Configuration;

/**
 * 文档生成的配置
 * <p>
 *
 * @Description: 配置文档页面显示的内容、工程名、工程URL、作者
 * @author lance
 * @version 1.0 2015-2-25 下午05:36:40
 */
@Configuration
@EnableSwagger
//@EnableWebMvc
//开启会出现两个访问地址
// Loads the spring beans required by the framework
public class MySwaggerConfig extends AbstractSwaggerConfig {
    @Override
    protected ApiInfo apiInfo() {
        return new ApiInfo("mcss4dpm调用接口API", "mcss4dpm applications and beyond!", "", "", "", "");

    }
}
