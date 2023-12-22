package com.example.blooddonatehub.Response;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class LocationListResponse implements Serializable
{

    @SerializedName("pincodes")
    @Expose
    private List<AreaResponse> areaResponseList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    private final static long serialVersionUID = 6928888042252091796L;

    public LocationListResponse() {
    }


    public LocationListResponse(List<AreaResponse> areaResponseList, String message, String status) {
        this.areaResponseList = areaResponseList;
        this.message = message;
        this.status = status;
    }

    public List<AreaResponse> getAreaResponseList() {
        return areaResponseList;
    }

    public void setAreaResponseList(List<AreaResponse> areaResponseList) {
        this.areaResponseList = areaResponseList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


@Generated("jsonschema2pojo")
public class AreaResponse implements Serializable {

    @SerializedName("area_id")
    @Expose
    private String areaId;
    @SerializedName("area_name")
    @Expose
    private String areaName;
    @SerializedName("pincodes")
    @Expose
    private String pincodes;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    private final static long serialVersionUID = -1378811111924816030L;

    public AreaResponse() {
    }


    public AreaResponse(String areaId, String areaName, String pincodes, String city, String state) {
        super();
        this.areaId = areaId;
        this.areaName = areaName;
        this.pincodes = pincodes;
        this.city = city;
        this.state = state;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPincode() {
        return pincodes;
    }

    public void setPincode(String pincode) {
        this.pincodes = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

}