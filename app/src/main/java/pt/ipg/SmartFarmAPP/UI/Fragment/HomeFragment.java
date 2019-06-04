package pt.ipg.SmartFarmAPP.UI.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.Service.API.Tools.API;
import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.NodeAdapter;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;


/**
 * Created by Daey
 */
import static android.view.View.GONE;
public class HomeFragment extends Fragment {
    private ProgressBar progressbar;
 //   private NodeViewModel nodeViewModel;
   // private RecyclerView recyclerView;

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new  HomeFragment();
        Bundle args = new Bundle();
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, null);
        FloatingActionButton fab = view.findViewById(R.id.fab);//Find fab Id
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // faz cenas
                Toast.makeText(getActivity(), "Montes de cenas!",
                        Toast.LENGTH_SHORT).show();
            }
        });


        // Content in view
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final NodeAdapter nodeAdapter = new NodeAdapter();
        recyclerView.setAdapter(nodeAdapter);
        ProgressBar progressbar = (ProgressBar) view.findViewById(R.id.progressbar);

        NodeViewModel nodeViewModel = ViewModelProviders.of(getActivity()).get(NodeViewModel.class);
        nodeViewModel.getAllNodes().observe(getActivity(), new Observer<List<Node>>() {
            @Override
            public void onChanged(@Nullable List<Node> nodes) {
                nodeAdapter.setNodes(nodes);
            }
        });


        // FIM --- Content in view



        // Jobintent <-- mover para sync database
 //       API.syncOracleAPI(nodeViewModel, progressbar);
        progressbar.setVisibility(GONE);
        return view;
    }
}
