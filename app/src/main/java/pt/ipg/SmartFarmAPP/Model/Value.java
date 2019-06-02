package pt.ipg.SmartFarmAPP.Model;

import java.util.List;

public class Value {

    private String id;
    private int date_of_value;
    private float air_temp;
    private float air_humidity;
    private int air_CO2;
    private int air_TVOC;
    private int flame;
    private int soil_humidity;
    private int sound;

     public class MyValues {
        public List<Value> items; // Lista de values
    }


    public String getId() {
        return id;
    }
    public int getDate_of_value() {
        return date_of_value;
    }
    public float getAir_temp() {
        return air_temp;
    }
    public float getAir_humidity() {
        return air_humidity;
    }
    public int getAir_CO2() {
        return air_CO2;
    }
    public int getAir_TVOC() {
        return air_TVOC;
    }
    public int getFlame() {
        return flame;
    }
    public int getSoil_humidity() {
        return soil_humidity;
    }
    public int getSound() {
        return sound;
    }
}

    /*
    {
    "id": "1A0000000",
    "date_of_value": 1556969160,
    "air_temp": 23.1,
    "air_humidity": 28.7,
    "air_pressure": 1540,
    "air_CO2": 982,
    "air_TVOC": 468,
    "lux": 540,
    "flame": 10,
    "soil_humidity": 230,
    "sound": 40
  }
  */


