package com.alekseiivhsin.taskmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alekseiivhsin.taskmanager.fragments.TaskListFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TASK_LIST_TAG = "taskmanager.fragments.TASK_LIST_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new TaskListFragment(), TASK_LIST_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
