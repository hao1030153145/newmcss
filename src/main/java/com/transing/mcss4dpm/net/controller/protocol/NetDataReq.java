
package com.transing.mcss4dpm.net.controller.protocol;

import com.jeeframework.logicframework.util.server.tcp.protocol.NetData;
import com.jeeframework.util.io.ByteStream;

public class NetDataReq extends NetData
{
	private long		dwUserUin;
	private byte[]	sInReserve;

	public long getDwUserUin() {
		return dwUserUin;
	}

	public void setDwUserUin(long dwUserUin) {
		this.dwUserUin = dwUserUin;
	}

	public byte[] getSInReserve() {
		return sInReserve;
	}

	public void setSInReserve(byte[] inReserve) {
		sInReserve = inReserve;
	}
	@Override
	public int unSerialize(ByteStream bs) throws Exception
	{
		this.dwUserUin = bs.popUInt();
		int len = bs.popInt();
		this.sInReserve = bs.popBytes(len);
		return bs.getReadLength();
	}
	@Override
	public int serialize(ByteStream bs) throws Exception
	{
		bs.pushUInt(dwUserUin);
		bs.pushBytes(sInReserve);
		return bs.getWrittenLength();
	}
	@Override
	public long getCmdId()
	{
		return 0x88211803L;
	}


	@Override
	public int getSize() {
		return 4 + 4 + ((sInReserve != null) ? sInReserve.length : 0);
	}


}
