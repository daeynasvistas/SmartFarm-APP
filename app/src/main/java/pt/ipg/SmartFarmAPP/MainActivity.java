package pt.ipg.SmartFarmAPP;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import pt.ipg.SmartFarmAPP.UI.Fragment.DashboardFragment;
import pt.ipg.SmartFarmAPP.UI.Fragment.HomeFragment;
import pt.ipg.SmartFarmAPP.UI.Fragment.ConfigurationsFragment;
import pt.ipg.SmartFarmAPP.UI.Fragment.ProfileFragment;

import pt.ipg.SmartFarmAPP.ViewModel.NodeViewModel;

//implement the interface OnNavigationItemSelectedListener in your activity class
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private NodeViewModel nodeViewModel;

    private TextView textViewResult;
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loading the default fragment
        loadFragment(new HomeFragment());
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        scheduleJob();
    }



    private void scheduleJob(){
        SharedPreferences preferences = PreferenceManager.
                getDefaultSharedPreferences(this);
        nodeViewModel = ViewModelProviders.of(this).get(NodeViewModel.class);

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
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(60 * 1000)
                //.setPeriodic(15 * 60 * 1000)
                .build();

        jobScheduler.schedule(info);
    }
















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

        return loadFragment(fragment);
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





}
