package com.franjmartin21.mydailytasks.activity;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.franjmartin21.mydailytasks.R;

public class EditTaskActivity extends AppCompatActivity implements EditTaskFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        EditTaskFragment editTaskFragment = EditTaskFragment.newInstance(1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.edit_task_fragment_container, editTaskFragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
