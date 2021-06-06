package com.example.feb8.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Habits")
public class Habits implements Serializable {

    @PrimaryKey(autoGenerate = false)
    private int ID;

    @ColumnInfo(name = "lightInfo")
    private int lightInfo;

    @ColumnInfo(name = "postureInfo")
    private int postureInfo;

    @ColumnInfo(name = "drivingInfo")
    private int drivingInfo;

    @ColumnInfo(name = "handsFreeCallInfo")
    private int handsFreeCallInfo;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getLightInfo() {
        return lightInfo;
    }

    public void setLightInfo(int lightInfo) {
        this.lightInfo = lightInfo;
    }

    public int getPostureInfo() {
        return postureInfo;
    }

    public void setPostureInfo(int postureInfo) {
        this.postureInfo = postureInfo;
    }

    public int getDrivingInfo() {
        return drivingInfo;
    }

    public void setDrivingInfo(int drivingInfo) {
        this.drivingInfo = drivingInfo;
    }

    public int getHandsFreeCallInfo() {
        return handsFreeCallInfo;
    }

    public void setHandsFreeCallInfo(int handsFreeCallInfo) {
        this.handsFreeCallInfo = handsFreeCallInfo;
    }
}
