package com.franjmartin21.mydailytasks.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.franjmartin21.mydailytasks.R;

public class EditTaskActivity extends BaseActivity implements EditTaskFragment.OnFragmentInteractionListener {

    public enum IntentExtra {
        TASK_OCCURRENCE_ID,
        TASK_OCCURRENCE_DATE,
        TASK_ACTIVITY_TYPE
    }

    private enum FragmentTag{
        EDIT_TASK
    }

    private View mLayout;
    private int occurrenceId;
    private long occurrenceDate;

    private EditTaskFragment editTaskFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        mLayout = findViewById(R.id.layout_edit_activity);

        Intent intent = getIntent();
        if (intent.hasExtra(IntentExtra.TASK_ACTIVITY_TYPE.name()) && intent.hasExtra(IntentExtra.TASK_OCCURRENCE_ID.name()) && intent.hasExtra(IntentExtra.TASK_OCCURRENCE_DATE.name())) {
            occurrenceId = intent.getIntExtra(IntentExtra.TASK_OCCURRENCE_ID.name(), 0);
            occurrenceDate = intent.getLongExtra(IntentExtra.TASK_OCCURRENCE_DATE.name(), 0);
        } else
            finish();

        FragmentManager fragmentManager = getSupportFragmentManager();
        editTaskFragment = (EditTaskFragment) fragmentManager.findFragmentByTag(FragmentTag.EDIT_TASK.name());
        if (editTaskFragment == null) {
            editTaskFragment = EditTaskFragment.newInstance(null, occurrenceId);
        }
        addFragmentToActivity(fragmentManager, editTaskFragment, R.id.edit_task_fragment_container, FragmentTag.EDIT_TASK.name());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            actionSave();
            return true;
        }

        if (id == R.id.action_delete){
            actionDelete();
            return true;
        }

        if (id == android.R.id.home){
            Intent returnIntent = new Intent();
            returnIntent.putExtra(DailyTasksActivity.IntentExtra.GOAL_DATE_RETURNED.name(), occurrenceDate);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void actionSave(){
        editTaskFragment.save();
        finish();
    }

    private void actionDelete(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        editTaskFragment.delete();
                        finish();
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_task_message)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
