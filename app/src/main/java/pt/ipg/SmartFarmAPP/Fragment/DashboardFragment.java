package pt.ipg.SmartFarmAPP.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import pt.ipg.SmartFarmAPP.API.JsonOracleAPI;
import pt.ipg.SmartFarmAPP.Model.Node;
import pt.ipg.SmartFarmAPP.Model.Value;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.Tools.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;


/**
 * Created by Daey
 */

public class DashboardFragment extends Fragment {
    private ProgressBar progressbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, null);
        TextView text = (TextView) view.findViewById(R.id.text_view_result);//Find textview Id
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);

        API.getOracleAPI(text, progressbar);


        //Get Argument that passed from activity in "data" key value
       /* String getArgument = getArguments().getString("data");
        text.setText(getArgument);//set string over textview
*/
        return view;//return view
   }






}
