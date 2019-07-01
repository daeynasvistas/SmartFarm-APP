package pt.ipg.SmartFarmAPP.UI.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.Picture;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.Service.API.API;
import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.NodeAdapter;
import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.PictureAdapter;
import pt.ipg.SmartFarmAPP.UI.Fragment.Dialog.AddNodeDialog;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;
import pt.ipg.SmartFarmAPP.ViewModel.PictureViewModel;
import pt.ipg.SmartFarmAPP.ViewModel.SharedViewModel;

import static android.content.ContentValues.TAG;
import static android.view.View.GONE;


/**
 *
 */

public class ProfileFragment extends Fragment {
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
