package com.abhijit.hubbletesting;

import com.abhijit.hubbletesting.Model.ImageFormat;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface hubbleImageInterface {
    @GET("image/{id}")
    Observable<ImageFormat> GetPage(@Path("id") String id);

}
