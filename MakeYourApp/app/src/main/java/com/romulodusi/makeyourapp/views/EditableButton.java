package com.romulodusi.makeyourapp.views;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;

import com.romulodusi.makeyourapp.activities.ScreenActivity;
import com.romulodusi.makeyourapp.utils.ChangebleView;

public class EditableButton extends Button implements ChangebleView {
    private ScreenActivity screenActivity;
    private LayoutParams lp;
    private boolean movEnabled = false;

    public EditableButton(Context context) {
        super(context);
        this.screenActivity = (ScreenActivity) context;
        this.setOnTouchListener(this);
    }

    public void toggleMovement(boolean enabled) {
        movEnabled = enabled;
    }

    public EditableButton(Context context, String label) {
        super(context);
        this.screenActivity = (ScreenActivity) context;
        this.setOnTouchListener(this);
        this.setText(label);

        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

    }

    public void renameElement(String newName) {
        this.setText(newName);

    }

    public void setPositions(int left, int top) {
        lp.setMargins(left, top, 0, 0);
        setLayoutParams(lp);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() != this.getId()) return false;

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            screenActivity.notifyElementActionDown(this);
        }
        else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            Log.i("BUTTON", "ACTION UP");
            screenActivity.notifyElementActionUp(this);
        }
        else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            if (movEnabled) {
                setPositions((int)motionEvent.getRawX() - view.getWidth()/2,
                        (int)(motionEvent.getRawY() - view.getHeight()*1.5f));
            }
        }
        return true;
    }
}
