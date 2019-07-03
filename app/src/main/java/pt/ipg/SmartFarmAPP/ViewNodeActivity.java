package pt.ipg.SmartFarmAPP;

import android.arch.lifecycle.LiveData;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.Entity.Repository;
import pt.ipg.SmartFarmAPP.Entity.SensorData;
import pt.ipg.SmartFarmAPP.UI.Fragment.Graph.TempFragment;


public class ViewNodeActivity  extends AppCompatActivity {

    public static final String EXTRA_ID = "pt.ipg.SmartFarmAPP.EXTRA_ID"; // para o SELECT só este node


    public static Node currentNode; // node aqui (todo colocar private ver 0.7)

    // --- todo ---- ENVIAR node em bundle .. ou fazer SELECT aqui do EXTRA_ID VERS. 06
    /* REMOVER  --- passei node directamente ------------------------------
    public static final String EXTRA_MAC ="pt.ipg.SmartFarmAPP.EXTRA_MAC";
    public static final String EXTRA_LATITUDE ="pt.ipg.SmartFarmAPP.EXTRA_LATITUDE";
    public static final String EXTRA_LONGITUDE ="pt.ipg.SmartFarmAPP.EXTRA_LONGITUDE";
    public static final String EXTRA_ALTITUDE ="pt.ipg.SmartFarmAPP.EXTRA_ALTITUDE";
    public static final String EXTRA_FIRM_VERS ="pt.ipg.SmartFarmAPP.EXTRA_FIRM_VERS";
    public static final String EXTRA_HAS_API ="pt.ipg.SmartFarmAPP.EXTRA_HAS_API";
    public static final String EXTRA_ORACLE_ID ="pt.ipg.SmartFarmAPP.EXTRA_ORACLE_ID";
    public static final String EXTRA_IP ="pt.ipg.SmartFarmAPP.EXTRA_IP";
    public static final String EXTRA_MODEL ="pt.ipg.SmartFarmAPP.EXTRA_MODEL";
    //REMOVER  --- passei node directamente ------------------------------
    */


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String  mac_id;

    private Repository repository;
    private LiveData<List<SensorData>> allSensorData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrool_tab);
       // mac_id =      getIntent().getStringExtra(EXTRA_ID); // o id local do node a mostrar!!!

/* REMOVER  --- passei node directamente ------------------------------
        int local_id =      getIntent().getIntExtra(EXTRA_ID, 0);

        String mac =        getIntent().getStringExtra(EXTRA_MAC);
        float latitude =   getIntent().getFloatExtra(EXTRA_LATITUDE, 0);
        float longitude =  getIntent().getFloatExtra(EXTRA_LONGITUDE, 0);
        int altitude =   getIntent().getIntExtra(EXTRA_ALTITUDE, 0);
        String firmVers =   getIntent().getStringExtra(EXTRA_FIRM_VERS);
        int has_API =       getIntent().getIntExtra(EXTRA_HAS_API, 0);
        String ip =         getIntent().getStringExtra(EXTRA_IP);
        String model =      getIntent().getStringExtra(EXTRA_MODEL);

//REMOVER  --- passei node directamente ------------------------------
*/
 //  private Toolbar toolbar;
    // REVER tool bar custom!!! ----------------------------------------- todo rever toolbar ver 0.6
    //    toolbar = (Toolbar) findViewById(R.id.toolbar);
    //    setSupportActionBar(toolbar);
    //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // setContentView(R.layout.activity_scrool_tab);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView textNode = findViewById(R.id.text_view_node);
        TextView textModel = findViewById(R.id.text_view_picture_description);
        TextView textModelMore = findViewById(R.id.text_view_picturel_more);
        ImageView imageViewSink = findViewById(R.id.imageViewSink);

        textNode.setText(currentNode.getModel());
        textModel.setText("MAC: "+currentNode.getMac()+" | Firmware: "+currentNode.getFirm_vers());
        textModelMore.setText(" Altitude: "+currentNode.getAltitude()+" | Lat: "+currentNode.getLatitude()+" | Lng: "+currentNode.getLongitude());

        // verificar se é SINK e mostrar icon
        if (currentNode.getHas_api().equals("1")){
            imageViewSink.setVisibility(View.VISIBLE);
        }else  imageViewSink.setVisibility(View.GONE);



    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

          Fragment fragment_temp = new TempFragment();
        ((TempFragment) fragment_temp).setLimit_ROW(100);
        ((TempFragment) fragment_temp).setMac_ID(currentNode.getMac());
        ((TempFragment) fragment_temp).setSensor_ID(1);

        Fragment fragment_hum = new TempFragment();
        ((TempFragment) fragment_hum).setLimit_ROW(100);
        ((TempFragment) fragment_hum).setMac_ID(currentNode.getMac());
        ((TempFragment) fragment_hum).setSensor_ID(2);

        Fragment fragment_atm = new TempFragment();
        ((TempFragment) fragment_atm).setLimit_ROW(100);
        ((TempFragment) fragment_atm).setMac_ID(currentNode.getMac());
        ((TempFragment) fragment_atm).setSensor_ID(3);

        Fragment fragment_co2 = new TempFragment();
        ((TempFragment) fragment_co2).setLimit_ROW(100);
        ((TempFragment) fragment_co2).setMac_ID(currentNode.getMac());
        ((TempFragment) fragment_co2).setSensor_ID(4);

        Fragment fragment_tvoc = new TempFragment();
        ((TempFragment) fragment_tvoc).setLimit_ROW(100);
        ((TempFragment) fragment_tvoc).setMac_ID(currentNode.getMac());
        ((TempFragment) fragment_tvoc).setSensor_ID(5);


        Fragment fragment_soil = new TempFragment();
        ((TempFragment) fragment_soil).setLimit_ROW(100);
        ((TempFragment) fragment_soil).setMac_ID(currentNode.getMac());
        ((TempFragment) fragment_soil).setSensor_ID(6);

        Fragment fragment_lux = new TempFragment();
        ((TempFragment) fragment_lux).setLimit_ROW(100);
        ((TempFragment) fragment_lux).setMac_ID(currentNode.getMac());
        ((TempFragment) fragment_lux).setSensor_ID(7);

        Fragment fragment_fogo = new TempFragment();
        ((TempFragment) fragment_fogo).setLimit_ROW(100);
        ((TempFragment) fragment_fogo).setMac_ID(currentNode.getMac());
        ((TempFragment) fragment_fogo).setSensor_ID(8);

        Fragment fragment_som = new TempFragment();
        ((TempFragment) fragment_som).setLimit_ROW(100);
        ((TempFragment) fragment_som).setMac_ID(currentNode.getMac());
        ((TempFragment) fragment_som).setSensor_ID(9);





        adapter.addFrag(fragment_temp, "Temperatura");
        adapter.addFrag(fragment_hum, "Humidade Ar");
        adapter.addFrag(fragment_atm, "Pressão Atm");
        adapter.addFrag(fragment_lux, "Luminosidade");
        adapter.addFrag(fragment_co2, "CO2");
        adapter.addFrag(fragment_tvoc, "Qualidade Ar");
        adapter.addFrag(fragment_soil, "Humidade solo");
        adapter.addFrag(fragment_som, "Som");
        adapter.addFrag(fragment_fogo, "Fogo");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}






/*

public class ViewNodeActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "pt.ipg.SmartFarmAPP.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "pt.ipg.SmartFarmAPP.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "pt.ipg.SmartFarmAPP.EXTRA_DESCRIPTION";


    private EditText editTextTitle;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_node);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        Intent intent = getIntent();

        setTitle("ESP32 Lora v2");
        editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
        editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));

    }

    private void saveNode() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }


    ///------------------------------ REVER MENU ver 0.6 ---------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  MenuInflater menuInflater = getMenuInflater();
        //  menuInflater.inflate(R.menu.add_node_menu, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
        */