package com.example.blooddonatehub.Model;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PersonRelationDataModel {

    private  String name;
    private  String unit;
    private  String location;
    private  String time;
    private  String critical;
    private  String bloodgroup;

    public PersonRelationDataModel(String name, String unit, String location, String time, String critical, String bloodgroup) {
        this.name = name;
        this.unit = unit;
        this.location = location;
        this.time = time;
        this.critical = critical;
        this.bloodgroup = bloodgroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }
}
