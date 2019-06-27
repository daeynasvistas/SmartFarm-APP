package pt.ipg.SmartFarmAPP.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "sensorData_table")
public class SensorData {

    @PrimaryKey(autoGenerate = true)
    private int local_id;

    private int id;
    private int date_of_value;
    private double value;
    private String type;
    private String name;
    private String code;
    private String unit;
    private String description;
    private float longitude;
    private float latitude;
    private String mac;
    private int nodeid;



    public class MySensorDataValues {
        public List<SensorData> items;
    }


    public SensorData(int id, int date_of_value, double value, String type, String name, String code, String unit, String description, float longitude, float latitude, String mac, int nodeid) {
        this.id = id;
        this.date_of_value = date_of_value;
        this.value = value;
        this.type = type;
        this.name = name;
        this.code = code;
        this.unit = unit;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.mac = mac;
        this.nodeid = nodeid;
    }

    // porque não temos no construtor
    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public int getLocal_id() {
        return local_id;
    }

    public int getId() {
        return id;
    }

    public int getDate_of_value() {
        return date_of_value;
    }

    public double getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getMac() {
        return mac;
    }

    public int getNodeid() {
        return nodeid;
    }
}


/*
*         {
             "id": 1,
              "date_of_value": 1561545997,
             "type": "air_temperature",
             "value": 23.1,
             "name": "Digital Sensor",
             "code": "BME280",
             "unit": "C",
             "longitude": null,
             "latitude": null,
             "description": "Temperatura ambiente"
        }
*
* */