package com.saolei.minesweeper.entity;


public class GridEntity {
//    定义雷的数量
    private int boomsCount;
//    判断是否为雷
    private boolean isBoom;
//    格子是否被展示
    private boolean isShow;
//    格子是否自动展示
    private boolean isAutoShow;
//    判断是否为边界格子
    private boolean isSide;
    public int getBoomsCount() {
        return boomsCount;
    }

    public void setBoomsCount(int boomsCount) {
        this.boomsCount = boomsCount;
    }

    public boolean isBoom() {
        return isBoom;
    }

    public void setIsBoom(boolean isBoom) {
        this.isBoom = isBoom;
    }


    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isAutoShow() {
        return isAutoShow;
    }

    public void setIsAutoShow(boolean isAutoShow) {
        this.isAutoShow = isAutoShow;
    }

    public boolean isSide() {
        return isSide;
    }

    public void setIsSide(boolean isSide) {
        this.isSide = isSide;
    }
//    无参构造方法
    public GridEntity(){

    }
//    含参构造方法
    public GridEntity(boolean isShow,boolean isSide,int boomsCount){
        this.isShow=isShow;
        this.isSide=isSide;
        this.boomsCount=boomsCount;
    }
}
