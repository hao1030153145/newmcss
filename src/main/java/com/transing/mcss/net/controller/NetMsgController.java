package com.transing.mcss.net.controller;

import com.jeeframework.logicframework.util.logging.LoggerUtil;
import com.jeeframework.logicframework.util.server.tcp.BaseNetController;
import com.jeeframework.logicframework.util.server.tcp.annotation.NetProtocol;
import com.transing.mcss.biz.service.BossUserService;
import com.transing.mcss.net.controller.protocol.NetDataReq;
import com.transing.mcss.net.controller.protocol.NetDataResp;
import com.transing.mcss.net.controller.protocol.SingleMsg;

import javax.annotation.Resource;
import java.util.Vector;

/**
 * 
 * @author lance
 * 
 */
public class NetMsgController extends BaseNetController {

	@Resource
	private BossUserService bossUserService;

	@NetProtocol(cmdId = 0x88211803L, desc = "测试网络接口", export = true)
	public NetDataResp testNetData(NetDataReq request) {
	    LoggerUtil.debugTrace("   in GetMsgCntByRole  method ");

		bossUserService.hashCode();
	    if (request.getSInReserve() != null)
			LoggerUtil.debugTrace("request " + new String(request.getSInReserve()));
		else
			LoggerUtil.debugTrace("request.getSInReserve() is null!");


		NetDataResp resp = new NetDataResp();
		resp.setSOutReserve("哈哈哈 数据".getBytes());
		Vector<SingleMsg> msgs = new Vector<SingleMsg>();
		msgs.add(new SingleMsg());
		resp.setMsgs(msgs);

		LoggerUtil.debugTrace("resp   " + resp.getMsgs());
		return resp;
	}

}
