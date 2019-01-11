/**
 * @project: com.hyfaycrawler
 * @Title: RuleAction.java
 * @Package: com.hyfaycrawler.web.action
 * @author tag
 * Copyright (c) 2014-2017 Jeeframework.com Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.util;

import com.transing.mcss4dpm.integration.bo.ContactBO;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @project: com.hyfaycrawler
 * @Title: RuleAction.java
 * @Package: com.hyfaycrawler.web.action
 * @author tag Copyright (c) 2014-2017 Jeeframework.com Limited, Inc. All rights reserved.
 *
 */
public class FileUtil {


    public static String readFileByPath(String path)
            throws IOException {
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String urls = "";
        String contentLine = "";
        while ((contentLine = bufferedReader.readLine()) != null) {
            urls = urls + contentLine;
        }
        bufferedReader.close();
        return urls;

    }

    public static List<ContactBO> readFileByFile(MultipartFile file)
            throws IOException {

        List<ContactBO> contactBOList = new ArrayList<>();
        // String path = file.getPath() + file.getName();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String contentLine ;
        while ((contentLine = bufferedReader.readLine()) != null ) {
            String name = contentLine.split(":")[0];
            String phone = contentLine.split(":")[1];
            ContactBO contactBO = new ContactBO();
            contactBO.setName(name);
            contactBO.setPhone(phone);
            contactBOList.add(contactBO);
        }
        bufferedReader.close();
        return contactBOList;

    }
}
