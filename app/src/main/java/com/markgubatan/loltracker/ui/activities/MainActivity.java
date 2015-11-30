package com.markgubatan.loltracker.ui.activities;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.markgubatan.loltracker.R;
import com.markgubatan.loltracker.ui.fragments.LeaguesFragment;
import com.markgubatan.loltracker.ui.fragments.MainActivityFragment;


public class MainActivity extends ActionBarActivity{
    private final static String TAG = "MainActivity";



    private String titles[] = new String[] {"Home", "Challenger Tier", "Teams", "Players"};

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private Toolbar toolbar;
    //private DrawerAdapter drawerAdapter;
    private int currentDrawerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.postponeEnterTransition(this);

        setContentView(R.layout.activity_main);
        String apiKey = getString(R.string.riot_api_key);
        Log.e(TAG, "the api key is " + apiKey);

        findViews();
        setSupportActionBar(toolbar);

        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, LeaguesFragment.newInstance())
                .commit();


        setDrawerToggle();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.temp_simple_list_item, titles);
        drawerList.setAdapter(adapter);

        //TODO: Switch this to 1 when header view is implemented
        currentDrawerItem = 0;
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, position + " was pressed, switching to " + titles[position]);
                if(position != currentDrawerItem) {
                    currentDrawerItem = position;
                    switch (position) {
                        case 0: switchToFragment(MainActivityFragment.newInstance());
                            break;
                        case 2: switchToFragment(LeaguesFragment.newInstance());
                            break;
                    }
                    drawerLayout.closeDrawer(Gravity.START);
                }
            }

        });
    }

    private void findViews() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView)findViewById(R.id.drawer_list);
    }

    private void setDrawerToggle() {
        drawerToggle =  new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void switchToFragment(Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
        Log.d(TAG, "Fragment switched");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
