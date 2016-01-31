package hr.foi.fbrd.sensei.network;

import com.dmacan.input.ChannelDetails;
import com.dmacan.input.ChannelJoinResponse;
import com.dmacan.input.HeartrateResponse;
import com.dmacan.input.LightResponse;

import hr.foi.fbrd.sensei.models.BaseResponse;
import hr.foi.fbrd.sensei.models.DataModel;
import hr.foi.fbrd.sensei.models.DeviceModel;
import hr.foi.fbrd.sensei.models.DeviceTypes;
import hr.foi.fbrd.sensei.models.EventModel;
import hr.foi.fbrd.sensei.models.EventsResponse;
import hr.foi.fbrd.sensei.models.LocationResponse;
import hr.foi.fbrd.sensei.models.SocketResponse;
import hr.foi.fbrd.sensei.models.Tag;
import hr.foi.fbrd.sensei.models.TemplateTypes;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ApiService {

    @POST("/api/1/devices/pair")
    @FormUrlEncoded
    Call<BaseResponse<DeviceModel>> sendDeviceData(@Header("Authorization") String token,
                                                   @Field("uniqueId") String id,
                                                   @Field("name") String name,
                                                   @Field("typeId") int type);

    @POST("/api/1/events")
    Call<BaseResponse<EventModel>> sendEventData(@Header("Authorization") String token,
                                                 @Body EventModel model);

    @POST("/api/1/data/store")
        // ovdje se dobije 400, moram pitati denisa kako ide ovaj call
    Call<BaseResponse<DataModel>> storeData(@Header("Authorization") String token, @Body DataModel model);

    @GET("/api/1/data/fetch/{id}")
    Call<BaseResponse<String>> fetchData(@Header("Authorization") String token, @Path("id") long id);

    @GET("/api/1/devices/me")
        // 400, wtf? -> denis
    Call<BaseResponse<String>> getMyDevice(@Header("Authorization") String token);

    @GET("/api/1/types")
    Call<BaseResponse<DeviceTypes>> getDeviceTypes(@Header("Authorization") String token);

    @GET("/api/1/events")
    Call<BaseResponse<EventsResponse>> getAllEvents(@Header("Authorization") String token);

    @POST("/api/1/events")
    Call<BaseCallback<EventsResponse>> storeNewEvent(@Header("Authorization") String token, @Body EventsResponse model);

    @GET("/api/1/events/mine")
        //400 - unauthorized
    Call<BaseResponse<EventsResponse>> getMyEvents(@Header("Authorization") String token);

    @DELETE("/api/1/events/{id}")
    Call<BaseResponse<EventsResponse>> deleteAnEvent(@Header("Authorization") String token, @Path("id") long id);

    @GET("/api/1/events/unlock/{id}")
    Call<BaseResponse<EventsResponse>> unlockAnEvent(@Header("Authorization") String token, @Path("id") long id);

//    @GET("/api/1/roles")
//    Call<BaseResponse<Something>> getRoles(@Header("Authorization") String token);

//    @GET("/api/1/roles/{id}")
//    Call<BaseResponse<Something>> getRolesById(@Header("Authorization") String token, @Path("id") long id);

    @GET("/api/1/tags")
    Call<BaseResponse<Tag>> getAllTags(@Header("Authorization") String token);

    @POST("/api/1/tags")
    @FormUrlEncoded
    Call<BaseResponse<Tag>> storeTag(@Header("Authorization") String token, @Field("hashTag") String tag);

    @GET("/api/1/templates")
    Call<BaseResponse<TemplateTypes>> getAllTemplates(@Header("Authorization") String token);

    @POST("/api/1/templates")
        //TODO ovdje se moram vratiti i pitati denisa wtf, pun klinac podataka ocekuje
    Call<BaseResponse<TemplateTypes>> storeTemplate(@Header("Authorization") String token);

    @GET("/api/1/templates/socket")
    Call<BaseResponse<SocketResponse>> getSockets(@Header("Authorization") String token);

    @POST("api/1/channels/join")
    @FormUrlEncoded
    Call<BaseResponse<ChannelJoinResponse>> joinChannel(@Header("Authorization") String token,
                                                        @Field("channelId") long id,
                                                        @Field("maxPayedRows") long paidNum, @Field("price") long num);

    @POST("/api/1/channels/location")
    @FormUrlEncoded
    Call<BaseResponse<LocationResponse>> sendLocation(@Field("channelId") int id, @Field("lat") double lat,
                                                      @Field("lng") double longitude,
                                                      @Field("deviceId") String deviceId);

    @POST("/api/1/channels/heartrate")
    @FormUrlEncoded
    Call<BaseResponse<HeartrateResponse>> sendHeartrate(@Field("channelId") int id, @Field("rate") int rate,
                                                        @Field("lat") double lat, @Field("lng") double lng,
                                                        @Field("city") String city, @Field("deviceId") String deviceId);

    @POST("/api/1/channels/light")
    @FormUrlEncoded
    Call<BaseResponse<LightResponse>> sendLight(@Field("lightLevel") float rate, @Field("channelId") int id,
                                                @Field("lat") double lat, @Field("lng") double lng,
                                                @Field("city") String city,
                                                @Field("deviceId") String deviceId);

    @GET("/api/1/channels/me")
    Call<BaseResponse<ChannelDetails>> getMyChannels(@Header("Authorization") String token);
    //TODO usere nisam jos stavljao, nisam siguran hoce li nam uopce i trebati


}
