package com.romulodusi.makeyourapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.romulodusi.makeyourapp.R;
import com.romulodusi.makeyourapp.database.ProjectDbHelper;
import com.romulodusi.makeyourapp.database.ScreenDbHelper;

import java.util.ArrayList;

public class ProjectsActivity extends AppCompatActivity implements View.OnClickListener,
        DialogInterface.OnClickListener {

    private final static int CREATE_PROJECT = 0xFF;
    private final static int OPEN_PROJECT = 0xFE;

    private Button createProject;
    private Button openProject;

    private EditText projectName;

    private AlertDialog createProjectDialog;
    private AlertDialog openProjectDialog;

    private Spinner currentProjects;

    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        loadInstances();
        setListeners();
    }

    private void loadInstances() {
        projectName = new EditText(this);

        createProject = (Button) findViewById(R.id.create_project);
        openProject = (Button) findViewById(R.id.open_project);

        createProjectDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.project_name))
                .setMessage(getString(R.string.tell_project_name))
                .setPositiveButton(R.string.confirm, this)
                .setNegativeButton(R.string.cancel, this)
                .create();

        createProjectDialog.setView(projectName);
        currentProjects = new Spinner(this);

        ArrayList<String> currProjects = MakeYourAppApplication.projectDb.getAllProjects();
        currProjects.add(0, "");

        currentProjects.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                currProjects));
        openProjectDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.project_name))
                .setMessage(getString(R.string.tell_project_name))
                .setView(currentProjects)
                .setPositiveButton(R.string.confirm, this)
                .setNegativeButton(R.string.cancel, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        createProjectDialog.dismiss();
        openProjectDialog.dismiss();

        if (which == AlertDialog.BUTTON_POSITIVE) {
            if (state == CREATE_PROJECT) {
                MakeYourAppApplication.projectDb.insertProject(projectName.getText().toString());

                Intent intent = new Intent(this, ScreenActivity.class);
                intent.putExtra("is_created", true);
                startActivity(intent);
            }
            else if (state == OPEN_PROJECT) {
                String currSelected = currentProjects.getSelectedItem().toString();
                if (!currSelected.equals("")) {
                    Intent intent = new Intent(this, ScreenActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private void setListeners() {
        createProject.setOnClickListener(this);
        openProject.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_project:
                state = CREATE_PROJECT;
                createProjectDialog.show();
                break;
            case R.id.open_project:
                state = OPEN_PROJECT;
                openProjectDialog.show();
                break;
            default:
                break;
        }
    }
}
