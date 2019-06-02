package pt.ipg.SmartFarmAPP.Model;

import java.util.List;

public class Node {

    private int id;
    private int iot_person_id;
    private String model;
    private String firm_vers;
    private String mac;
    private float longitude;
    private float latitude;
    private int altitude;
    private int has_api;
    private String ip;

    public class MyNodes {
        public List<Node> items; // Lista de values
    }

    public int getId() {
        return id;
    }

    public int getIot_person_id() {
        return iot_person_id;
    }

    public String getModel() {
        return model;
    }

    public String getFirm_vers() {
        return firm_vers;
    }

    public String getMac() {
        return mac;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public int getHas_api() {
        return has_api;
    }

    public String getIp() {
        return ip;
    }

    /*
    *
    * {"id":1000,
    * "iot_person_id":1,
    * "model":"ESP32 Lora",
    * "firm_vers":"0.1",
    * "mac":"1A0000000",
    * "longitude":-7.354986,
    * "latitude":40.777083,
    * "altitude":1090,
    * "has_api":null,
    * "ip":"192.000.000.000"}
    * */
}
