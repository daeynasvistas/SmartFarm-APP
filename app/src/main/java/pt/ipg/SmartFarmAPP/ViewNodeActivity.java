package pt.ipg.SmartFarmAPP;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.UI.Fragment.OneFragment;


public class ViewNodeActivity  extends AppCompatActivity {
    public static final String EXTRA_ID ="pt.ipg.SmartFarmAPP.EXTRA_ID";
    public static Node currentNode;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrool_tab);

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

        setContentView(R.layout.activity_scrool_tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TextView textNode = findViewById(R.id.text_view_node);
        TextView textModel = findViewById(R.id.text_view_model);
        TextView textModelMore = findViewById(R.id.text_view_model_more);

        textNode.setText(currentNode.getModel());
        textModel.setText("MAC: "+currentNode.getMac()+" | Firmware: "+currentNode.getFirm_vers());
        textModelMore.setText(" Altitude: "+currentNode.getAltitude()+" | Lat: "+currentNode.getLatitude()+" | Lng: "+currentNode.getLongitude());



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "Temperatura");
        adapter.addFrag(new OneFragment(), "Humidade Ar");
        adapter.addFrag(new OneFragment(), "Pressão Atm");
        adapter.addFrag(new OneFragment(), "Luminosidade");
        adapter.addFrag(new OneFragment(), "CO2");
        adapter.addFrag(new OneFragment(), "Qualidade Ar");
        adapter.addFrag(new OneFragment(), "Humidade solo");
        adapter.addFrag(new OneFragment(), "Som");
        adapter.addFrag(new OneFragment(), "Fogo");


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