package pt.ipg.SmartFarmAPP.Service.API.Tools;

import android.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMAC {

    public static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec(keyString.getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            // Base64.NO_WRAP   Encoder flag bit to omit all line terminators (i.e., the output will be on one long line).
            // If we do not use Base64.NO_WRAP the string will not be correct
            digest = Base64.encodeToString(mac.doFinal(msg.getBytes("UTF-8")), Base64.NO_WRAP);

        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return digest;
    }

    public static String hmacDigest(byte[] msg, String keyString, String algo) {
        String digest = "";
        try {
            SecretKeySpec key = new SecretKeySpec(keyString.getBytes("UTF-8"), algo);
            Mac sha256_HMAC = Mac.getInstance(algo);
            sha256_HMAC.init(key);

            // Base64.NO_WRAP   Encoder flag bit to omit all line terminators (i.e., the output will be on one long line).
            // If we do not use Base64.NO_WRAP the string will not be correct
            digest = Base64.encodeToString(sha256_HMAC.doFinal(msg), Base64.NO_WRAP);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return digest;
    }
}