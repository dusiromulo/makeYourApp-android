package com.romulodusi.makeyourapp.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.romulodusi.makeyourapp.R;
import com.romulodusi.makeyourapp.utils.ChangebleView;
import com.romulodusi.makeyourapp.views.EditableButton;
import com.romulodusi.makeyourapp.views.NewNameAlert;

import java.util.ArrayList;

public class ScreenActivity extends AppCompatActivity {
    public enum ViewState {
        none,
        reposition,
        rename,
        remove,
    };

    private RelativeLayout parentElements;

    private ViewState currState;
    private ArrayList<ChangebleView> elements;
    private NewNameAlert newNameAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        Intent lastIntent = getIntent();
        boolean isCreated = lastIntent.getBooleanExtra("is_created", false);

        elements = new ArrayList<>();

        loadInstances();
    }

    private void loadInstances() {
        this.parentElements = (RelativeLayout) findViewById(R.id.parent_elements);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.screen_menu, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (elements.size() == 0) {
            menu.removeItem(R.id.rename_item);
        }
        else if (menu.findItem(R.id.rename_item) == null) {
            MenuInflater menuInflater = new MenuInflater(this);
            menu.clear();
            menuInflater.inflate(R.menu.screen_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        for (ChangebleView curr : elements) {
            curr.toggleMovement(false);
        }
        switch (item.getItemId()) {
            case R.id.add_item:
                EditableButton button = new EditableButton(this, "Example");
                parentElements.addView(button, parentElements.getChildCount());
                elements.add(button);

                currState = ViewState.none;
                return true;
            case R.id.reposition:
                currState = ViewState.reposition;
                for (ChangebleView curr : elements) {
                    curr.toggleMovement(true);
                }

                return true;
            case R.id.rename_item:
                currState = ViewState.rename;

                return true;
            case R.id.remove_item:
                currState = ViewState.remove;

                return true;
            case R.id.remove_all_items:
                parentElements.removeAllViews();
                elements.clear();
                currState = ViewState.none;

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void notifyElementActionDown(ChangebleView view) {

    }

    public void notifyElementActionUp(ChangebleView view) {
        switch (currState) {
            case remove:
                parentElements.removeView((View) view);
                elements.remove(view);
                break;
            case rename:
                Log.i("RENAME", "OPEN ALERT");
                newNameAlert = new NewNameAlert(this, view);
                break;
            default:
                break;
        }
    }
}
