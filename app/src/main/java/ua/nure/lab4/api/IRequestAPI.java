package ua.nure.lab4.api;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import ua.nure.lab4.api.model.Group;
import ua.nure.lab4.api.model.User;
import ua.nure.lab4.api.response.GroupList;
import ua.nure.lab4.api.response.LecturerList;

public interface IRequestAPI {
    @FormUrlEncoded
    @POST("token")
    Call<User>  authenticate(@Field("username") String username,
                             @Field("password") String password);

    @POST("change-provider")
    Call<Object> changeProvider(@Header("Authorization") String authHeader,
                              @Body String new_provider);

    @GET("students")
    Call<GroupList> getGroups(@Header("Authorization") String authHeader);

    @GET("courses")
    Call<LecturerList> getLecturers(@Header("Authorization") String authHeader);
}
