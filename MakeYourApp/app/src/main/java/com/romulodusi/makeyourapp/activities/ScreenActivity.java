package com.romulodusi.makeyourapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.romulodusi.makeyourapp.R;
import com.romulodusi.makeyourapp.utils.ChangebleView;
import com.romulodusi.makeyourapp.views.EditableButton;
import com.romulodusi.makeyourapp.views.NewNameAlert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ScreenActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    public enum ViewState {
        none,
        reposition,
        rename,
        remove,
    };

    private RelativeLayout parentElements;
    private AlertDialog addItemDialog;
    private Spinner viewTypeSpinner;

    private ViewState currState;
    private ArrayList<ChangebleView> elements;
    private NewNameAlert newNameAlert;
    private HashMap<String, Class<?>> mapClasses;

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
        this.mapClasses = new HashMap<>();
        this.mapClasses.put(getString(R.string.button), EditableButton.class);

        viewTypeSpinner = new Spinner(this);
        ArrayList<String> keysList = new ArrayList<>();
        keysList.addAll(this.mapClasses.keySet());

        viewTypeSpinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                keysList));

        addItemDialog = new AlertDialog.Builder(this).setPositiveButton(R.string.confirm, this)
                .setNegativeButton(R.string.cancel, this).
                        create();

        addItemDialog.setTitle(R.string.select_type);
        addItemDialog.setMessage(getString(R.string.select_type_view));
        addItemDialog.setView(viewTypeSpinner);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        addItemDialog.dismiss();
        String currSelected = viewTypeSpinner.getSelectedItem().toString();

        if (which == AlertDialog.BUTTON_POSITIVE) {
            Class<?> selectedClass = mapClasses.get(currSelected);
            try {
                Constructor<?> constructor = selectedClass.getConstructor(Context.class);
                ChangebleView element = (ChangebleView) constructor.newInstance(this);
                parentElements.addView(
                        (View) element,
                        parentElements.getChildCount()
                );
                elements.add(element);
                currState = ViewState.none;
            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                catch(NoSuchMethodException err){
                err.printStackTrace();
            }
            catch(IllegalAccessException err2){
                err2.printStackTrace();
            }
            catch(InvocationTargetException err3){
                err3.printStackTrace();
            }
            catch(InstantiationException err4){
                err4.printStackTrace();
            }
        }
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
                addItemDialog.show();
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
                newNameAlert = new NewNameAlert(this, view);
                break;
            default:
                break;
        }
    }
}
