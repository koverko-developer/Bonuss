package com.example.x.bonus.retrofit;

import com.example.x.bonus.retrofit.fixwed.InfoF;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by x on 13.06.2017.
 */
public interface Api {

    @FormUrlEncoded
    @POST("bonus/reg.php")
    Call<Otvet> regisnrations(@Field("login") String login,
                                    @Field("password") String password,
                                    @Field("fio") String fio,
                                    @Field("phone") String phone,
                                    @Field("invait") String invait
                              )
                              ;
    @GET("bonus/login.php")
    Call<Otvet> login(@Query("login") String login,
                      @Query("password") String password);

    @GET("bonus/get_all_company.php")
    Call<List<Company>> getCompany(@Query("id") String id);

    @GET("bonus/my_company.php")
    Call<List<Company>> getMyCompany(@Query("id") String id);

    @GET("bonus/user_company.php")
    Call<Otvet> getBonus(@Query("id_user") String id, @Query("id_company") String id_company);

    @GET("bonus/user_company_info.php")
    Call<List<Info>> getInfoUser(@Query("id_user") String id_user, @Query("id_company") String id_company);

    @GET("bonus/user_company_info.php")
    Call<List<InfoF>> getInfoUserFixed(@Query("id_user") String id_user, @Query("id_company") String id_company);

    @GET("bonus/activate_bonus.php")
    Call<Otvet> activateBonus(@Query("id_user") String id_user, @Query("id_company") String id_company);

    @FormUrlEncoded
    @POST("bonus/forgot_password_user.php")
    Call<Otvet> sendEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("bonus/notice_user_company.php")
    Call<List<Notification>> getNotification(@Field("id_user") String id_user);


    @FormUrlEncoded
    @POST("bonus/add_view_notice.php")
    Call<Otvet> deleteNotification(@Field("id_user") String id_user,@Field("id_news") String id_news);

}