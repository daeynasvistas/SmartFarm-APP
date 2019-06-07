package pt.ipg.SmartFarmAPP.UI.Fragment;



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

import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.Service.API.API;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;


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
