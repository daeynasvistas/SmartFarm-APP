package pt.ipg.SmartFarmAPP.UI.Fragment;



import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import pt.ipg.SmartFarmAPP.R;
import pt.ipg.SmartFarmAPP.Service.API.API;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;

import static android.view.View.GONE;


/**
 * Created by Daey
 */

public class DashboardFragment extends Fragment {
    private NodeViewModel nodeViewModel;
    private ProgressBar progressbar;
    //    private RecyclerView recyclerView;
    private LineChart mChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);


    /*    nodeViewModel = ViewModelProviders.of(this).get(NodeViewModel.class);
        TextView text = (TextView) view.findViewById(R.id.text_view_result);//Find textview Id


        API.getOracleAPI(text, progressbar);
*/
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);

        mChart = view.findViewById(R.id.chart1);

        setData(100, 30);

        Description des = mChart.getDescription();
        des.setText("Últimas 24 horas");
       // des.setEnabled(false);
        mChart.animate();


        return view;//return view
    }


    private void setData(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i=0; i < count; i++) {
            float val = (float) (Math.random()*range) + 400;
            values1.add(new Entry(i, val));
        }

        ArrayList<Entry> values2 = new ArrayList<>();
        for (int i=0; i < count; i++) {
            float val = (float) (Math.random()*range) + 5;
            values2.add(new Entry(i, val));
        }

        ArrayList<Entry> values3 = new ArrayList<>();
        for (int i=0; i < count; i++) {
            float val = (float) (Math.random()*range) + 350;
            values3.add(new Entry(i, val));
        }

        ArrayList<Entry> values4 = new ArrayList<>();
        for (int i=0; i < count; i++) {
            float val = (float) (Math.random()*range) + 30;
            values4.add(new Entry(i, val));
        }

        ArrayList<Entry> values5 = new ArrayList<>();
        for (int i=0; i < count; i++) {
            float val = (float) (Math.random()*range) + 100;
            values5.add(new Entry(i, val));
        }

        LineDataSet set1, set2, set3,set4, set5;

        set1 = new LineDataSet(values1, "CO2");
        set1.setColor(Color.RED);
        set1.setDrawCircles(false);
        set1.setLineWidth(1f);

        set2 = new LineDataSet(values2, "Temperatura");
        set2.setColor(Color.GREEN);
        set2.setDrawCircles(false);
        set2.setLineWidth(3f);

        set3 = new LineDataSet(values3, "Tvoc");
        set3.setColor(Color.BLUE);
        set3.setDrawCircles(false);
        set3.setLineWidth(1f);

        set4 = new LineDataSet(values4, "Humidade");
        set4.setColor(Color.GRAY);
        set4.setDrawCircles(false);
         set4.setLineWidth(3f);

        set5 = new LineDataSet(values5, "Pressão Atm (x10)");
        set5.setColor(Color.MAGENTA);
        set5.setDrawCircles(false);
        set5.setLineWidth(1f);

        LineData data = new LineData(set1, set2,set3,set4,set5);
        mChart.setData(data);

        progressbar.setVisibility(GONE);
    }


}
