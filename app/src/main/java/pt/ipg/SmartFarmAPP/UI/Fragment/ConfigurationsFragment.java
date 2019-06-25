package pt.ipg.SmartFarmAPP.UI.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.Service.API.API;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;
import pt.ipg.SmartFarmAPP.ViewModel.SharedViewModel;


/**
 * Created by Daey
 */

public class ConfigurationsFragment extends Fragment {
    private SharedViewModel viewModel;
    private EditText editText;
    private NodeViewModel nodeViewModel;

    private ProgressBar progressbar;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configurations, container, false);

        /*
        editText = v.findViewById(R.id.edit_text);
        Button button = v.findViewById(R.id.button_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setText(editText.getText());
            }
        });
        */

        nodeViewModel = ViewModelProviders.of(this).get(NodeViewModel.class);
        TextView text = view.findViewById(R.id.text_view_result_API);//Find textview Id
        progressbar = view.findViewById(R.id.progressbar);

        API.getOracleHMAC_API(text, progressbar);

        return view;//return view

    }
/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {
                editText.setText(charSequence);
            }
        });
    }
    */
}
