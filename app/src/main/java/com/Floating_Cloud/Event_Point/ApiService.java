package com.Floating_Cloud.Event_Point;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/sites")
    Call<Void> sendSiteData(@Body SiteData siteData);

    @GET("/sites")
    Call<List<SiteData>> getSites();

    @PUT("/sites/{name}")
    Call<Void> updateSite(@Path("name") String name, @Body SiteData siteData);

    @DELETE("/sites/{name}")
    Call<Void> deleteSite(@Path("name") String name);
}