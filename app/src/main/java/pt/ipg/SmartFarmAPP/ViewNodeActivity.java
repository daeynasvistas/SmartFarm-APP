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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pt.ipg.SmartFarmAPP.UI.Fragment.OneFragment;


public class ViewNodeActivity  extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrool_tab);

        // REVER tool bar custom!!! ----------------------------------------- todo rever toolbar
    //    toolbar = (Toolbar) findViewById(R.id.toolbar);
    //    setSupportActionBar(toolbar);
    //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "Temperatura");
        adapter.addFrag(new OneFragment(), "Humidade Ar");
        adapter.addFrag(new OneFragment(), "Pressão Atm");
        adapter.addFrag(new OneFragment(), "Pressão Atm");
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