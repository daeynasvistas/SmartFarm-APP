package pt.ipg.SmartFarmAPP.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "picture_table")
public class Picture {
    @PrimaryKey(autoGenerate = true)
    private int local_ID;

    private String doenca;
    private String description;
    private Long date;
    private float longitude;
    private float latitude;
    private int altitude;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[] image;

    public Picture(String doenca, String description, Long date, byte[] image, float longitude, float latitude, int altitude) {

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

    public String getDoenca() {
        return doenca;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public Long getDate() {
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


