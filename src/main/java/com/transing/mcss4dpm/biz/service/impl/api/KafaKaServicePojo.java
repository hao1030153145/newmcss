package com.transing.mcss4dpm.biz.service.impl.api;

import com.jeeframework.logicframework.biz.service.BaseService;
import com.transing.mcss4dpm.job.BaseKafkaConsumerImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/3/7
 */
@Service("kafaKaServicePojo")
public class KafaKaServicePojo extends BaseService {
    @Resource
    private BaseKafkaConsumerImpl kafkaConsumer;
    @Resource
    private BaseKafkaConsumerImpl kafkaConsumeDevice;
}
