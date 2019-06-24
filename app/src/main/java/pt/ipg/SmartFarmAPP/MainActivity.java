package pt.ipg.SmartFarmAPP;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.support.v7.widget.SearchView;



import pt.ipg.SmartFarmAPP.UI.Fragment.Adapter.NodeAdapter;
import pt.ipg.SmartFarmAPP.UI.Fragment.DashboardFragment;
import pt.ipg.SmartFarmAPP.UI.Fragment.HomeFragment;
import pt.ipg.SmartFarmAPP.UI.Fragment.ConfigurationsFragment;
import pt.ipg.SmartFarmAPP.UI.Fragment.ProfileFragment;



public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        //Rotação do dispositivo .. FINALMENTE !!!
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
        scheduleJob();

    }

    // --- SEarch cenas no recycleview dos nodes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_nodemenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
           //     adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
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

        ComponentName componentName = new ComponentName(this, SyncJobService.class);
        JobInfo info = new JobInfo.Builder(1000, componentName)
                .setRequiresCharging(true) // DEBUG!!!!!! <-------------------------------- DEBUG CHARGING
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                //.setPeriodic(15 * 60 * 1000)
                .setPeriodic(5 * 1000)
                .build();

        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(info);
        if (resultCode==JobScheduler.RESULT_SUCCESS){
            Log.d(TAG, "Job schedule");
        }else{
            Log.d(TAG, "Job com montes Problemas");
        }

    }

    public void cancelJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(1000);
        Log.d(TAG, "Job cancelled");
    }














//-------------------------------- REMOVER ----------------------------------
/*
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
