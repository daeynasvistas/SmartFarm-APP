package pt.ipg.SmartFarmAPP.UI.Fragment.Graph;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.SensorData;
import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.ViewModel.SensorDataViewModel;

import static android.view.View.GONE;

public class TempFragment extends Fragment{
    private String mac_ID;
    private int sensor_ID;
    private int limit_ROW;

    public void setMac_ID(String mac_ID) {
        this.mac_ID = mac_ID;
    }

    public void setSensor_ID(int sensor_ID) {
        this.sensor_ID = sensor_ID;
    }

    public void setLimit_ROW(int limit_ROW) {
        this.limit_ROW = limit_ROW;
    }





    private ProgressBar progressbar;
    private LineChart mChart;

    public TempFragment() {
        // construtor vazio
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);
       // String mac_ID = getArguments().getString(EXTRA_ID);

        progressbar = view.findViewById(R.id.progressbar);

        mChart = view.findViewById(R.id.chart1);
        mChart.getXAxis().setTextSize(0);

        setData(limit_ROW, mac_ID);  ///-----------------------------------------------------------------TodO alterar ver 0.6


        Description des = mChart.getDescription();
        des.setText("Últimas 24 horas");
        // des.setEnabled(false);
        mChart.animate();

        return view;
    }


    private void setData(final int count, String mac_ID) {

        final ArrayList<Entry> values1 = new ArrayList<>();
        final LineDataSet set1;

        set1 = new LineDataSet(values1, "Temperatura");
        set1.setColor(Color.GREEN);
        set1.setDrawCircles(false);
        set1.setLineWidth(2f);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // set1.setDrawFilled(true);
        //  set1.setDrawValues(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);

        final LineData data = new LineData(set1);


        final SensorDataViewModel sensorDataViewModel = ViewModelProviders.of(getActivity()).get(SensorDataViewModel.class);
        sensorDataViewModel.getAllSelectedSensorData(sensor_ID,limit_ROW, mac_ID).observe(getActivity(), new Observer<List<SensorData>>() {
            @Override
            public void onChanged(@Nullable List<SensorData> sensorData) {
                // tenho aqui todos os valores sensor

                  for(SensorData value:sensorData){
                    data.addEntry(new Entry(set1.getEntryCount(), (float) value.getValue()), 0);
                }

                data.notifyDataChanged();
                mChart.notifyDataSetChanged(); // let the chart know it's data changed
                mChart.invalidate(); // refresh
            }
        });

        mChart.setData(data);
        progressbar.setVisibility(GONE);
    }
}