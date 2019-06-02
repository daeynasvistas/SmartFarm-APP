package pt.ipg.SmartFarmAPP.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "node_table")
public class Node {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    private String person;
    private String model;
    private String firm_vers;
    private String mac;
    private float longitude;
    private float latitude;
    private int altitude;
    private int has_api;
    private String ip;


    public Node(String person, String model, String firm_vers, String mac, float longitude, float latitude, int altitude, int has_api, String ip) {
        this.person = person;
        this.model = model;
        this.firm_vers = firm_vers;
        this.mac = mac;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.has_api = has_api;
        this.ip = ip;
    }
// ------ SETTER -----------
    public void set_id(int _id) {
        this._id = _id;
    }
// ----------- GET --

    public int get_id() {
        return _id;
    }

    public String getPerson() {
        return person;
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


}