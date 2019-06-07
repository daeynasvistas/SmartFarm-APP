package pt.ipg.SmartFarmAPP.Service.API;

import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Model.NodeModel;
import pt.ipg.SmartFarmAPP.Model.Value;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonOracleAPI {

    @GET("nodes")
    Call<List<Value>> getValues();

    @GET("nodes")
    Call<NodeModel.MyNodes> getNodesModel();

    // -------  CRUD retrofit  ----------------
    @GET("nodes")
    Call<Node.MyNodes> getNodes();

    @POST("nodes")
    @FormUrlEncoded
    Call<Node> postNode(@Field("IOT_PERSON_ID") int person_id,
                        @Field("MODEL") String model,
                        @Field("FIRM_VERS") String firm_vers,
                        @Field("MAC") String mac,
                        @Field("IP") String ip,
                        @Field("LATITUDE") double latitude,
                        @Field("LONGITUDE") double longitude,
                        @Field("ALTITUDE") int altitude
                        );
}

/*
    private int id;
    private String person;
    private String model;
    private String firm_vers;
    private String mac;
    private float longitude;
    private float latitude;
    private int altitude;
    private int has_api;
    private String ip;
* {
    "IOT_PERSON_ID":1,
    "MODEL":"New ESP32",
    "FIRM_VERS":"0.2",
    "MAC":"1B11111111",
    "IP":"192.168.1.2",
    "LATITUDE":40.1254125,
    "LONGITUDE":-7.21548714,
    "ALTITUDE":955
}
* */