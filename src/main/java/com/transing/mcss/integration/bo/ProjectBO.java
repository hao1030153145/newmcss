package com.transing.mcss.integration.bo;

import com.jeeframework.logicframework.integration.bo.AbstractBO;

import java.util.Date;

public class ProjectBO extends AbstractBO {
    private long id;
    private long uid;
    private String name;
    private long status;
    private Date createTime;
    private Date completeTime;
    private String fileZipAddress;
    private long allTaskcnt;
    private long completeTaskcnt;
    private String lastModifyTime;
    private long isDelete;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getFileZipAddress() {
        return fileZipAddress;
    }

    public void setFileZipAddress(String fileZipAddress) {
        this.fileZipAddress = fileZipAddress;
    }

    public long getAllTaskcnt() {
        return allTaskcnt;
    }

    public void setAllTaskcnt(long allTaskcnt) {
        this.allTaskcnt = allTaskcnt;
    }

    public long getCompleteTaskcnt() {
        return completeTaskcnt;
    }

    public void setCompleteTaskcnt(long completeTaskcnt) {
        this.completeTaskcnt = completeTaskcnt;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(long isDelete) {
        this.isDelete = isDelete;
    }
}
