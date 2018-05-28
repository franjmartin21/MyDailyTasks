package com.franjmartin21.mydailytasks.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.franjmartin21.mydailytasks.R;
import com.franjmartin21.mydailytasks.activity.adapter.DailyTaskAdapter;
import com.franjmartin21.mydailytasks.activity.util.UIUtil;
import com.franjmartin21.mydailytasks.data.db.MyDailyTasksDatabase;
import com.franjmartin21.mydailytasks.util.UtilDate;

import java.util.Date;

public class DailyTasksActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DailyTaskListFragment.OnItemClickedListener {

    public enum SaveState{
        CURRENT_DATE
    }

    public enum IntentExtra {
        GOAL_DATE_RETURNED
    }

    public enum RequestCode{
        EDIT_TASK(1122);

        int code;
        RequestCode(int code){
            this.code = code;
        }
    }

    public enum FragmentTag{
        DAILY_TASK_LIST
    }


    private DailyTaskListFragment dailyTaskListFragment;

    private Date currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.daily_task_title);
        setContentView(R.layout.activity_daily_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentDate = getCurrentDateForActivity(savedInstanceState);

        /**
         * todo: to uncomment when activate navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
         */

        //Adding the fragment to the Activity
        loadFragment();
    }

    private Date getCurrentDateForActivity(Bundle savedInstanceState){
        if(currentDate != null) return currentDate;

        if (savedInstanceState != null && savedInstanceState.getLong(SaveState.CURRENT_DATE.name()) > 0) {
            // Restore value of members from saved state
            currentDate = new Date(savedInstanceState.getLong(SaveState.CURRENT_DATE.name()));
        } else{
            currentDate = new Date();
        }
        return currentDate;
    }

    private void loadFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        dailyTaskListFragment = DailyTaskListFragment.newInstance(currentDate.getTime());

        addFragmentToActivity(fragmentManager,dailyTaskListFragment,R.id.daily_task_list_fragment_container,FragmentTag.DAILY_TASK_LIST.name()
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_today){
            currentDate = new Date();
            loadFragment();
        }
        /* todo: to enable when using settings
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClicked(int itemId) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra(EditTaskActivity.IntentExtra.TASK_OCCURRENCE_ID.name(), itemId);
        intent.putExtra(EditTaskActivity.IntentExtra.TASK_OCCURRENCE_DATE.name(), currentDate.getTime());
        startActivityForResult(intent, RequestCode.EDIT_TASK.code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == RequestCode.EDIT_TASK.code) {
            if(resultCode == Activity.RESULT_OK){
                currentDate = new Date(data.getLongExtra(IntentExtra.GOAL_DATE_RETURNED.name(), 0L));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onPreviousBtnClicked() {
        currentDate = UtilDate.addDays(currentDate, -1);
        loadFragment();
    }

    @Override
    public void onNextBtnClicked() {
        currentDate = UtilDate.addDays(currentDate, 1);
        loadFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putLong(SaveState.CURRENT_DATE.name(), currentDate.getTime());
    }
}
