package pt.ipg.SmartFarmAPP.Service.API.Tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Locale;

public class DataConverter {

    public static byte[] convertImage2ByteArray (Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArray2Bitmap (byte[] array){
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }



    /// retornar timestamp
    public static Long getNowTimestamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString(); // <<----------------- (( debug))
        return tsLong;
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }


}
