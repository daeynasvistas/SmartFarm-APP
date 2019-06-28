package pt.ipg.SmartFarmAPP.Service.API;


import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.SensorData;
import pt.ipg.SmartFarmAPP.Model.NodeModel;
import pt.ipg.SmartFarmAPP.Model.Value;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonOracleAPI {

    // ------------ HMAC aqui vai assinado todas as REQ -----------------------
    @Headers({
            "API_key: "+ "RTlCRjMzMjBDQjNFNDc0QjNBNTEzNkVCODIyMTQwM0RBMjVFNzAyNQ==",
            "API_sign: "+ "z0nVOnp8HknQxDq5fDgX5rWgClbeNCt+OUMfEi1dd1kb9+uOwYgxVppP/VwQV9JQH6JuPU8q8CTHtO0Oy2Ey6g==",
            "API_nonce: "+ "1561289582855000"
    })
    //--------------------------------------------------------------------------
    @GET("nodes/")
    Call<List<Value>> getValues(
    );


    @Headers({
            "API_key: "+ "RTlCRjMzMjBDQjNFNDc0QjNBNTEzNkVCODIyMTQwM0RBMjVFNzAyNQ==",
            "API_sign: "+ "z0nVOnp8HknQxDq5fDgX5rWgClbeNCt+OUMfEi1dd1kb9+uOwYgxVppP/VwQV9JQH6JuPU8q8CTHtO0Oy2Ey6g==",
            "API_nonce: "+ "1561289582855000"
    })
    @GET("nodes/")
    Call<NodeModel.MyNodes> getNodesModel();








    // -------  CRUD retrofit  ------------------------------------------------------------------------
    // ------------------------------------------------ READ NODES ------------------------------------
    @Headers({
            "API_key:RTlCRjMzMjBDQjNFNDc0QjNBNTEzNkVCODIyMTQwM0RBMjVFNzAyNQ==",
            "API_sign:z0nVOnp8HknQxDq5fDgX5rWgClbeNCt+OUMfEi1dd1kb9+uOwYgxVppP/VwQV9JQH6JuPU8q8CTHtO0Oy2Ey6g==",
            "API_nonce:1561289582855000"
    })
    @GET("nodes/")
    Call<Node.MyNodes> getNodes();







    // ------------------------------------------------ CREATE NODE ------------------------------------
    @POST("nodes/")
    @FormUrlEncoded
    Call<Node> postNode(//@Field("IOT_PERSON_ID") int person_id,
                        @Field("MODEL") String model,
                        @Field("FIRM_VERS") String firm_vers,
                        @Field("MAC") String mac,
                        @Field("IP") String ip,
                        @Field("LATITUDE") double latitude,
                        @Field("LONGITUDE") double longitude,
                        @Field("ALTITUDE") int altitude,
                        @Field("HAS_API") String has_api,

                        @Header("API_key") String api_key,
                        @Header("API_sign") String api_sign,
                        @Header("API_nonce") String api_nonce
                        );


    // ------------------------------------------------ DELETE NODE ------------------------------------
    @DELETE("nodes/{nodeID}")
    Call<Void> deleteNode(@Path("nodeID") int nodeID,

                          @Header("API_key") String api_key,
                          @Header("API_sign") String api_sign,
                          @Header("API_nonce") String api_nonce
    );





    //-----------------------  VALORES dos SENSORES ---------------------------------
    // -------  CRUD retrofit  ------------------------------------------------------------------------
    // ------------------------------------------------ READ NODES ------------------------------------
   // @Headers({
   //         "API_key:RTlCRjMzMjBDQjNFNDc0QjNBNTEzNkVCODIyMTQwM0RBMjVFNzAyNQ==",
   //         "API_sign:z0nVOnp8HknQxDq5fDgX5rWgClbeNCt+OUMfEi1dd1kb9+uOwYgxVppP/VwQV9JQH6JuPU8q8CTHtO0Oy2Ey6g==",
   //         "API_nonce:1561289582855000"
   // })
    @GET("data/{timestamp}")
    Call<SensorData.MySensorDataValues> getSensorData(@Path("timestamp") int timeStamp,

                                                      @Header("API_key") String api_key,
                                                      @Header("API_sign") String api_sign,
                                                      @Header("API_nonce") String api_nonce
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