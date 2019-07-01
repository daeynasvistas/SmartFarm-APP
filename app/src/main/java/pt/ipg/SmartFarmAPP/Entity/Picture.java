package pt.ipg.SmartFarmAPP.Entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "picture_table")
public class Picture {
    @PrimaryKey(autoGenerate = true)
    private int local_ID;

    private int id;
    private String doenca;
    private String description;
    private int date;
    byte[] image;
    private float longitude;
    private float latitude;
    private int altitude;

    public Picture(int local_ID, int id, String doenca, String description, int date, byte[] image, float longitude, float latitude, int altitude) {
        this.local_ID = local_ID;
        this.id = id;
        this.doenca = doenca;
        this.description = description;
        this.date = date;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public void setLocal_ID(int local_ID) {
        this.local_ID = local_ID;
    }


    public int getLocal_ID() {
        return local_ID;
    }

    public int getId() {
        return id;
    }

    public String getDoenca() {
        return doenca;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public int getDate() {
        return date;
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
}


