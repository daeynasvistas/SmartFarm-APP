package pt.ipg.SmartFarmAPP.UI.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;

import static android.view.View.GONE;

public class OneFragment extends Fragment{

    private NodeViewModel nodeViewModel;
    private ProgressBar progressbar;
    private LineChart mChart;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        // Inflate the layout for this fragment

        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);

        mChart = view.findViewById(R.id.chart1);
        mChart.getXAxis().setTextSize(0);


        setData(20, 3);

        Description des = mChart.getDescription();
        des.setText("Últimas 24 horas");
        // des.setEnabled(false);
        mChart.animate();

        return view;
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i=0; i < count; i++) {
            float val = (float) (Math.random()*range) + 10;
            values1.add(new Entry(i, val));
        }



        LineDataSet set1;


        set1 = new LineDataSet(values1, "Temperatura");
        set1.setColor(Color.LTGRAY);
        set1.setDrawCircles(false);
        set1.setLineWidth(3f);
       // set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

       // set1.setDrawFilled(true);
      //  set1.setDrawValues(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);

        LineData data = new LineData(set1);

        mChart.setData(data);


        progressbar.setVisibility(GONE);
    }
}