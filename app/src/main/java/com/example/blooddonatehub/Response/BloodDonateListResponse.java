package com.example.blooddonatehub.Response;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class BloodDonateListResponse implements Serializable
{

    @SerializedName("getBlood_groupList")
    @Expose
    private List<GetBloodGroup> getBloodGroupList;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    private final static long serialVersionUID = -2375248582043013250L;

    /**
     * No args constructor for use in serialization
     *
     */
    public BloodDonateListResponse() {
    }

    /**
     *
     * @param getBloodGroupList
     * @param message
     * @param status
     */
    public BloodDonateListResponse(List<GetBloodGroup> getBloodGroupList, String message, String status) {
        super();
        this.getBloodGroupList = getBloodGroupList;
        this.message = message;
        this.status = status;
    }

    public List<GetBloodGroup> getGetBloodGroupList() {
        return getBloodGroupList;
    }

    public void setGetBloodGroupList(List<GetBloodGroup> getBloodGroupList) {
        this.getBloodGroupList = getBloodGroupList;
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
public class GetBloodGroup implements Serializable {

    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("blood_type")
    @Expose
    private String bloodType;
    @SerializedName("blood_group")
    @Expose
    private String bloodGroup;
    @SerializedName("patient_full_name")
    @Expose
    private String patientFullName;
    @SerializedName("patient_mobileno")
    @Expose
    private String patientMobileno;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("select_units")
    @Expose
    private String selectUnits;
    @SerializedName("critical_situation")
    @Expose
    private String criticalSituation;
    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = 5041276041828306238L;

    /**
     * No args constructor for use in serialization
     */
    public GetBloodGroup() {
    }

    /**
     * @param date
     * @param bloodGroup
     * @param patientFullName
     * @param selectUnits
     * @param criticalSituation
     * @param requestId
     * @param description
     * @param patientMobileno
     * @param location
     * @param bloodType
     * @param userId
     */
    public GetBloodGroup(String requestId, String userId, String bloodType, String bloodGroup, String patientFullName, String patientMobileno, String date, String location, String selectUnits, String criticalSituation, String description) {
        super();
        this.requestId = requestId;
        this.userId = userId;
        this.bloodType = bloodType;
        this.bloodGroup = bloodGroup;
        this.patientFullName = patientFullName;
        this.patientMobileno = patientMobileno;
        this.date = date;
        this.location = location;
        this.selectUnits = selectUnits;
        this.criticalSituation = criticalSituation;
        this.description = description;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPatientFullName() {
        return patientFullName;
    }

    public void setPatientFullName(String patientFullName) {
        this.patientFullName = patientFullName;
    }

    public String getPatientMobileno() {
        return patientMobileno;
    }

    public void setPatientMobileno(String patientMobileno) {
        this.patientMobileno = patientMobileno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSelectUnits() {
        return selectUnits;
    }

    public void setSelectUnits(String selectUnits) {
        this.selectUnits = selectUnits;
    }

    public String getCriticalSituation() {
        return criticalSituation;
    }

    public void setCriticalSituation(String criticalSituation) {
        this.criticalSituation = criticalSituation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

}