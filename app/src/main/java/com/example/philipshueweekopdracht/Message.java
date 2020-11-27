package com.example.philipshueweekopdracht;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

public class Message {

    public static void createToastMessage(String messageText) {
        Toast.makeText(Data.getInstance().getContext(), messageText, Toast.LENGTH_SHORT).show();
    }

    public static void createDialog(String messageText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Data.getInstance().getContext());
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder.setMessage(messageText);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                createToastMessage("Loading...");
            }
        });

        alertDialogBuilder.create().show();
    }
}
