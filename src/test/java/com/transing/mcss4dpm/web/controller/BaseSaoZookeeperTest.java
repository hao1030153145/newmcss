package com.transing.mcss4dpm.web.controller;

import com.jeeframework.logicframework.integration.sao.zookeeper.BaseSaoZookeeper;
import com.jeeframework.testframework.AbstractSpringBaseTestNoTransaction;
import com.jeeframework.util.validate.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 描述
 *
 * @author lance
 * @version 1.0 2018-01-30 18:42
 */
public class BaseSaoZookeeperTest extends AbstractSpringBaseTestNoTransaction {


    @Autowired
    BaseSaoZookeeper baseSaoZookeeper;


    @Test
    public void createPath() throws Exception {
        baseSaoZookeeper.create("/tttt", "vvv");
    }

    @Test
    public void isExisted() throws Exception {
        Assert.isTrue(baseSaoZookeeper.isExisted("/tttt"));
    }

    @Test
    public void get() throws Exception {
        String value = baseSaoZookeeper.get("/tttt");
        Assert.isTrue("vvv".equals(value));
    }

    @Test
    public void update() throws Exception {
        baseSaoZookeeper.update("/tttt", "xxx");
    }

    @Test
    public void createSubPath() throws Exception {
        baseSaoZookeeper.create("/tttt/xxxx", "vvv");
    }

    @Test(expected = Exception.class)
    public void deleteContainsSubPathThrowException() throws Exception {
        baseSaoZookeeper.delete("/tttt");
    }

    @Test
    public void deleteContainsSubPath() throws Exception {
        baseSaoZookeeper.delete("/tttt", true);
    }


    @Test
    public void createChildren() throws Exception {
        baseSaoZookeeper.create("/tttt/1111", "vvv");
        baseSaoZookeeper.create("/tttt/2222", "vvv");
        baseSaoZookeeper.create("/tttt/3333", "vvv");
    }

    @Test
    public void getChildrenKeys() throws Exception {
        List<String> childrenList = baseSaoZookeeper.getChildrenKeys("/tttt");
        Assert.isTrue(childrenList.size() == 3);
        Assert.isTrue(childrenList.get(0).equals("1111"));
        Assert.isTrue(childrenList.get(1).equals("2222"));
        Assert.isTrue(childrenList.get(2).equals("3333"));
    }

    @Test
    public void getChildrenCount() throws Exception {
        Assert.isTrue(baseSaoZookeeper.getChildrenCount("/tttt") == 3);
    }

    @Test
    public void createEphemeral() throws Exception {
        baseSaoZookeeper.createEphemeral("hhhh", "fff");
    }

    @Test
    public void createSequential() throws Exception {
        String seq = baseSaoZookeeper.createSequential("/ffff", "xxxx");
        Assert.hasLength(seq);
    }

    @Test
    public void createEphemeralSequential() throws Exception {
        baseSaoZookeeper.createEphemeralSequential("/aaaa");
        System.out.println("sss");
    }


}