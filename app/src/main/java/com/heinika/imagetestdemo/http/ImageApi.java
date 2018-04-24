package com.heinika.imagetestdemo.http;

import com.heinika.imagetestdemo.entity.ImageEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageApi {

    @GET("j?pn=50")
    Call<ImageEntity> getImage(
            @Query("q") String name,
            @Query("sn") int sn);

}
