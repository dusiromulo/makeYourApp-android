package com.romulodusi.makeyourapp.views;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.romulodusi.makeyourapp.R;
import com.romulodusi.makeyourapp.utils.ChangebleView;

public class NewNameAlert implements DialogInterface.OnClickListener {

    private AlertDialog thisDialog;
    private EditText newNameEdit;
    private ChangebleView viewTo;

    public NewNameAlert(Context context, ChangebleView viewTo) {
        this.viewTo = viewTo;
        thisDialog = new AlertDialog.Builder(context).setPositiveButton(R.string.confirm, this)
                    .setNegativeButton(R.string.cancel, this).
                    create();
        newNameEdit = new EditText(context);
        thisDialog.setTitle(R.string.new_text);
        thisDialog.setMessage(context.getString(R.string.tell_new_text));
        thisDialog.setView(newNameEdit);
        thisDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        thisDialog.dismiss();
        String newName = newNameEdit.getText().toString();

        if (which == AlertDialog.BUTTON_POSITIVE) {
            viewTo.renameElement(newName);
        }
    }
}
