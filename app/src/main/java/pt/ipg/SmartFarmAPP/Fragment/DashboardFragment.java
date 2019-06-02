package pt.ipg.SmartFarmAPP.Fragment;



import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import pt.ipg.SmartFarmAPP.API.JsonOracleAPI;
import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.MainActivity;
import pt.ipg.SmartFarmAPP.Model.Value;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.Tools.API;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by Daey
 */

public class DashboardFragment extends Fragment {
    private NodeViewModel nodeViewModel;

    private ProgressBar progressbar;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);


        nodeViewModel = ViewModelProviders.of(this).get(NodeViewModel.class);
        TextView text = (TextView) view.findViewById(R.id.text_view_result);//Find textview Id
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);

        API.getOracleAPI(text, progressbar);

        return view;//return view
   }






}
