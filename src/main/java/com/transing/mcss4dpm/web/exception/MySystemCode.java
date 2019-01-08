package com.transing.mcss4dpm.web.exception;

import com.jeeframework.webframework.exception.SystemCode;

/**
 * SystemCode
 * 系统错误编码枚举
 *
 * @author lance
 * @date 2016/3/21 0021
 */
public class MySystemCode extends SystemCode {

    public static final  int CUSTOM_EXCEPTION = 1_10_14;
    public static final  String CUSTOM_EXCEPTION_MESSAGE = "自定义错误!";

    static {
        errorMessageMap.put(CUSTOM_EXCEPTION, CUSTOM_EXCEPTION_MESSAGE);
    }

    public static final  int JSONFORMAT_EXCEPTION = 1_10_15;
    public static final  String JSONFORMAT_EXCEPTION_MESSAGE = "不是合法的JSON格式!";

    static {
        errorMessageMap.put(JSONFORMAT_EXCEPTION, JSONFORMAT_EXCEPTION_MESSAGE);
    }

    public static final  int BIZ_APPVERSION_EXCEPTION = 1_11_1;
    public static final  String BIZ_APPVERSION_EXCEPTION_MESSAGE = "查询程序版本号不能为空!";

    static {
        errorMessageMap.put(BIZ_APPVERSION_EXCEPTION, BIZ_APPVERSION_EXCEPTION_MESSAGE);
    }

    public static final int QUERY_TABLE_NULL = 1_12_1;
    public static final String QUERY_TABLE_NULL_MESSAGE = "查询存贮表为空!";

    static {
        errorMessageMap.put(QUERY_TABLE_NULL, QUERY_TABLE_NULL_MESSAGE);
    }

    public static final int GET_APPLICATION_INFO_NULL = 1_12_2;
    public static final String GET_APPLICATION_INFO_MESSAGE = "查询应用失败!";

    static {
        errorMessageMap.put(GET_APPLICATION_INFO_NULL, GET_APPLICATION_INFO_MESSAGE);
    }

    public static final int GET_SCRIPT_DETAIL_NULL = 1_12_3;
    public static final String GET_SCRIPT_DETAIL_MESSAGE = "查询脚本详情!";

    static {
        errorMessageMap.put(GET_SCRIPT_DETAIL_NULL, GET_SCRIPT_DETAIL_MESSAGE);
    }

    public static final int GET_SCRTPT_INFO_NULL = 1_12_3;
    public static final String GET_SCRTPT_INFO_MESSAGE = "查询脚本信息为空!";

    static {
        errorMessageMap.put(GET_SCRTPT_INFO_NULL, GET_SCRTPT_INFO_MESSAGE);
    }

    public static final int GET_ENABLE_DEVICE_NULL = 1_12_4;
    public static final String GET_ENABLE_DEVICE_MESSAGE = "没有可用设备!";

    static {
        errorMessageMap.put(GET_ENABLE_DEVICE_NULL, GET_ENABLE_DEVICE_MESSAGE);
    }

    public static final int GET_DELETE_DEVICE_NULL = 1_12_5;
    public static final String GET_DELETE_DEVICE_MESSAGE = "删除设备失败!";

    static {
        errorMessageMap.put(GET_DELETE_DEVICE_NULL, GET_DELETE_DEVICE_MESSAGE);
    }

    public static final int GET_SCRIPT_INFO_NULL = 1_12_6;
    public static final String GET_SCRIPT_INFO_MESSAGE = "没有查询到相关脚本";

    static {
        errorMessageMap.put(GET_SCRIPT_INFO_NULL, GET_SCRIPT_INFO_MESSAGE);
    }
}
