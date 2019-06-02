package pt.ipg.SmartFarmAPP.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;


/**
 * Created by Daey
 */

public class HomeFragment extends Fragment {
    private NodeViewModel nodeViewModel;

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

        nodeViewModel = ViewModelProviders.of(this).get(NodeViewModel.class);
        nodeViewModel.getAllNodes().observe(this, new Observer<List<Node>>() {
            @Override
            public void onChanged(@Nullable List<Node> nodes) {
                //update RecyclerView
                Toast.makeText(getContext(), "onChanged", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
