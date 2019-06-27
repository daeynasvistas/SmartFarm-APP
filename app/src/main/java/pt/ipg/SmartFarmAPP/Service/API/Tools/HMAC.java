package pt.ipg.SmartFarmAPP.Service.API.Tools;

import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Formatter;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class HMAC {

   private String secret = "z0nVOnp8HknQxDq5fDgX5rWgClbeNCt+OUMfEi1dd1kb9+uOwYgxVppP/VwQV9JQH6JuPU8q8CTHtO0Oy2Ey6g=="; // GERAR DEPOIS!!!
   private String key = "RTlCRjMzMjBDQjNFNDc0QjNBNTEzNkVCODIyMTQwM0RBMjVFNzAyNQ=="; // GERAR DEPOIS!!!


   public String getNonce(){
     String nonce = String.valueOf(TimeUnit.MILLISECONDS.toMicros(System.currentTimeMillis()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        nonce = String.valueOf(LocalDateTime.now().getLong(ChronoField.MICRO_OF_SECOND));
    }
    return nonce;
   }


    public String getSign(String nonce, String secret){
    // String nonce = "1561142283215331";  // GERAR DEPOIS!!!
    String name = "persons";          // GERAR DEPOIS!!!
    String operation = "";          // GERAR DEPOIS!!!

    //ORACLE///v_post_data := 'nonce=' || TO_CHAR(v_nonce);
    String v_post_data = "nonce=" + nonce;

    //ORACLE//v_binary_hash := dbms_crypto.hash(utl_i18n.string_to_raw(TO_CHAR(v_nonce)|| v_post_data, 'AL32UTF8'), dbms_crypto.hash_sh256);
    String v_binary_hash = HexString (getSHA(nonce + v_post_data, "SHA-256"));
    byte[] v_binary_path_and_hash_byte = getSHA(nonce+v_post_data, "SHA-256");

    //ORACLE// v_path := '/' || ws_name || '/' || ws_operation;
    String v_path = "/" + name + "/" + operation;

    //ORACLE// v_binary_path := utl_i18n.string_to_raw(v_path, 'AL32UTF8');
    String v_binary_path = HexString(v_path.getBytes(StandardCharsets.UTF_8));

    //ORACLE//  v_binary_path_and_hash := v_binary_path || v_binary_hash;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
        outputStream.write(v_path.getBytes(StandardCharsets.UTF_8));
        outputStream.write(v_binary_path_and_hash_byte);
    } catch (
    IOException e) { e.printStackTrace();}

    byte[] v_binary_path_and_hash = outputStream.toByteArray();

    //ORACLE //  v_secret_decoded := utl_encode.base64_decode(utl_raw.cast_to_raw(ws_secret));
    byte[] v_secret_decoded = Base64.decode(secret, Base64.DEFAULT);

    //ORACLE//   v_sign := dbms_crypto.mac(v_binary_path_and_hash, dbms_crypto.hmac_sh512, v_secret_decoded);
    //ORACLE // v_sign_base64 := utl_encode.base64_encode(v_sign);
    String v_sign = HmacSHA512(v_binary_path_and_hash, v_secret_decoded);

    /*
            Log.d(post_data, v_post_data);
            Log.d(binary_hash, HexString(v_binary_path_and_hash_byte));
            Log.d(path, v_post_data);
            Log.d(binary_path, "" + v_binary_path);
            Log.d(binary_path_and_hash, HexString(v_binary_path_and_hash));
            Log.d(api_sign, v_sign);
    */

    return v_sign;
}



//----------------------------- Funções necessárias e privadas --------------------------------------

    private String HexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }


    private static byte[] getSHA(String input, String algo)
    {
        try {
            MessageDigest md = MessageDigest.getInstance(algo);
            byte[] hash = md.digest(input.getBytes());
            return hash;
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
            return null;
        }
    }


    private String HmacSHA512(byte[] data, byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA512");
        Mac mac = null;
        try {
            mac = Mac.getInstance(secretKey.getAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(secretKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        String result = new String(Base64.encode(mac.doFinal(data),Base64.NO_WRAP));
        return result;
    }


    //----------------------------- END --------------------------------------

}