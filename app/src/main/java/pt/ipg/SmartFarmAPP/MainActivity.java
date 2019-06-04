package pt.ipg.SmartFarmAPP;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.List;

import pt.ipg.SmartFarmAPP.Entity.Node;
import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.NodeAdapter;
import pt.ipg.SmartFarmAPP.UI.Fragment.DashboardFragment;
import pt.ipg.SmartFarmAPP.UI.Fragment.HomeFragment;
import pt.ipg.SmartFarmAPP.UI.Fragment.ConfigurationsFragment;
import pt.ipg.SmartFarmAPP.UI.Fragment.ProfileFragment;
import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;


public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";

    private ProgressBar progressbar;
    private RecyclerView recyclerView;
    // viewModel persistentes!!!!!
    private NodeViewModel nodeViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Factory Cenas .... tentativa em Vers0.3
        // Content in view

    //    nodeViewModel = ViewModelProviders.of(this).get(NodeViewModel.class);


        //Rotação do dispositivo .. FINALMENTE !!!
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }




        // job for the biys
        scheduleJob();
    }












    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = HomeFragment.newInstance();
                            break;
                        case R.id.navigation_dashboard:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new ConfigurationsFragment();
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };























    private void scheduleJob(){
        SharedPreferences preferences = PreferenceManager.
                getDefaultSharedPreferences(this);


        if(!preferences.getBoolean("firstRunComplete", false)){
            //schedule the job only once.
            scheduleJobOracleToRoomDataUpdate();

            //update shared preference
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstRunComplete", true);
            editor.commit();
        }
    }


    private void scheduleJobOracleToRoomDataUpdate(){
        JobScheduler jobScheduler = (JobScheduler)getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(this, SyncJobService.class);


        JobInfo info = new JobInfo.Builder(1000, componentName)
                .setRequiresCharging(true) // DEBUG!!!!!! <-------------------------------- DEBUG CHARGING
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                //.setPeriodic(15 * 60 * 1000)
                .setPeriodic(60 * 1000)
                .build();

        jobScheduler.schedule(info);
    }















/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                Bundle data = new Bundle();//Use bundle to pass data
                //data.putString("data", "Montes de cenas");
                fragment.setArguments(data);
                break;

            case R.id.navigation_notifications:
                fragment = new ConfigurationsFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
        }

       // return loadFragment(fragment);
    }




    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
*/




}
