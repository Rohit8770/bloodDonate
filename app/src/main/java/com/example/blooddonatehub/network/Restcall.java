package com.example.blooddonatehub.network;


import com.example.blooddonatehub.Response.BloodDonateListResponse;
import com.example.blooddonatehub.Response.BloodRequestListResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

public interface Restcall {

    @FormUrlEncoded
    @POST("blood_donorsapi/blood_donation_apis.php")
    Single<BloodRequestListResponse>CallRequestForBlood(
            @Field("tag") String tag,
            @Field("blood_type") String blood_type,
            @Field("blood_group") String blood_group,
            @Field("patient_full_name") String patient_full_name,
            @Field("patient_mobileno") String patient_mobileno,
            @Field("date") String date,
            @Field("select_units") String select_units,
            @Field("critical_situation") String critical_situation,
            @Field("description") String description,
            @Field("location") String location);

    @FormUrlEncoded
    @POST("blood_donorsapi/blood_donation_apis.php")
    Single<BloodDonateListResponse>GetallBloodgroups(
            @Field("tag") String tag);

}


