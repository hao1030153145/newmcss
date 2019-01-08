
package com.transing.mcss4dpm.net.controller.protocol;

import com.jeeframework.logicframework.util.server.tcp.protocol.NetData;
import com.jeeframework.util.io.ByteStream;

import java.util.ArrayList;
import java.util.List;

public class NetDataResp extends NetData {
    private long dwMsgCnt = 1;
    private byte[] sOutReserve;
    private List<SingleMsg> msgs;

    @Override
    public long getCmdId() {
        return 0x88211803L;
    }

    @Override
    public int serialize(ByteStream bs) throws Exception {
        bs.pushUInt(this.dwMsgCnt);
        bs.pushBytes(sOutReserve);
        bs.pushList(msgs);
        return bs.getWrittenLength();
    }


    @Override
    public int unSerialize(ByteStream bs) throws Exception {
        dwMsgCnt = bs.popUInt();
        sOutReserve = bs.popBytes();
        msgs = (ArrayList<SingleMsg>) bs.popList(ArrayList.class, SingleMsg.class);

        return bs.getReadLength();
    }

    public long getDwMsgCnt() {
        return dwMsgCnt;
    }

    public void setDwMsgCnt(long dwMsgCnt) {
        this.dwMsgCnt = dwMsgCnt;
    }

    public byte[] getSOutReserve() {
        return sOutReserve;
    }

    public void setSOutReserve(byte[] outReserve) {
        sOutReserve = outReserve;
    }

    public List<SingleMsg> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<SingleMsg> msgs) {
        this.msgs = msgs;
    }

    @Override
    public int getSize() {
        int l = 4 + 4 + ((sOutReserve != null) ? sOutReserve.length : 0) + 4;
        if (msgs != null) {
            for (int i = 0; i < msgs.size(); i++) {
                l += msgs.get(i).getSize();
            }
        }

        return l;
    }
}
