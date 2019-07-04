package pt.ipg.SmartFarmAPP.Service.API.Tools;

import android.os.Build;

/*
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;


import java.util.ArrayList;
import java.util.List;
*/
import pt.ipg.SmartFarmAPP.Entity.Picture;

public class GoogleAI {

    /*

    public static String detect(Picture picture) throws Exception {
        // Instantiates a client
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            // Reads the image file into memory
            byte[] data = picture.getImage();
            ByteString imgBytes = ByteString.copyFrom(data);

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();
            requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                   // System.out.printf("Error: %s\n", res.getError().getMessage());
                    return "Error: %s\n"+ res.getError().getMessage();
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                     return "Ok";

                    }
                }
            }
        }
        return "nada de novo";
    }
    */



}