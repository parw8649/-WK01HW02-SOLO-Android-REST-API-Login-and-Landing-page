package com.example.wk01hw02solo.external;

import com.example.wk01hw02solo.model.UserPostData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IExternalAPI {

    @GET("/posts")
    Call<List<UserPostData>> getAllUserPostData();
}
