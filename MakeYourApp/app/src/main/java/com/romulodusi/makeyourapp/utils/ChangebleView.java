package com.romulodusi.makeyourapp.utils;

import android.view.View;


public interface ChangebleView extends View.OnTouchListener {
    void renameElement(String newName);
    void toggleMovement(boolean enabled);
    void setPositions(int left, int top);
}
