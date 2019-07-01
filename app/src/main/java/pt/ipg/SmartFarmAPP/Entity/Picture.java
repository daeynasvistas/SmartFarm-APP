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
    byte[] image;


    public Picture(int local_ID, int id, String doenca, String description, byte[] image) {
        this.local_ID = local_ID;
        this.id = id;
        this.doenca = doenca;
        this.description = description;
        this.image = image;
    }

    public void setLocal_ID(int local_ID) {
        this.local_ID = local_ID;
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
}


