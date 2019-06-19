package pt.ipg.SmartFarmAPP.Service.API.Tools;


import java.math.BigInteger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class hmac_generate {


    private String MyHashHmac(String secret, String data){
        String returnString = "";

        try {

            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(),"HmacSHA256");
            Mac mac =  Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
            byte[] dataByteArray = mac.doFinal(data.getBytes());
            BigInteger hash = new BigInteger(1,dataByteArray);
            returnString = hash.toString();



        }catch (Exception e){
            e.printStackTrace();
        }

        return returnString;


    }



    /*CALL this stuff
    String MySecret = "MySecret"; //Replace with your own Secret
    String MyData = "MyData"; // Replace with your data

    String Hash_hmac_result = MyHashHmac(MySecret,MyData);
    Log.d("TAG", "result : " +Hash_hmac_result);
    *
    * */
}
