package com.Floating_Cloud.Event_Point;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class ApiClient {

    private static final String BASE_URL = "http://3.37.191.230:19991";
    private ApiService apiService;

    public ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    // POST 요청
    public void sendSiteData(SiteData siteData) {
        Call<Void> call = apiService.sendSiteData(siteData);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("POST Request successful");
                } else {
                    System.out.println("POST Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // GET 요청
    public void fetchSites(ApiCallback<List<SiteData>> callback) {
        Call<List<SiteData>> call = apiService.getSites();
        call.enqueue(new Callback<List<SiteData>>() {
            @Override
            public void onResponse(Call<List<SiteData>> call, Response<List<SiteData>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure(new Exception("GET Request failed with code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<SiteData>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    // PUT 요청 (수정)
    public void updateSite(String name, SiteData siteData) {
        Call<Void> call = apiService.updateSite(name, siteData);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("PUT Request successful");
                } else {
                    System.out.println("PUT Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // DELETE 요청 (삭제)
    public void deleteSite(String name) {
        Call<Void> call = apiService.deleteSite(name);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("DELETE Request successful");
                } else {
                    System.out.println("DELETE Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}