package com.dfzt.recyclerviewmodule.bean;

public class DataBean {

    private String name;
    private String groupName;

    private int groupIndex;

    public DataBean(String name,String groupName,int groupIndex){
        this.name = name;
        this.groupName = groupName;
        this.groupIndex = groupIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }
}
