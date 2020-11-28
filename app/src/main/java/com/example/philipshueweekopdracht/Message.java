package com.example.philipshueweekopdracht;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

public class Message {

    public static void createToastMessage(String messageText,int toastLength ) {
        Toast.makeText(Data.getInstance().getContext(), messageText, toastLength).show();
    }

    public static void createDialog(String messageText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Data.getInstance().getContext());
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder.setMessage(messageText);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setNeutralButton("OK", (dialog, id) -> createToastMessage("Loading...", Toast.LENGTH_LONG));

        alertDialogBuilder.create().show();
    }

    public static void createLinkButtonDialog(Client client){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Data.getInstance().getContext());
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder.setMessage(Data.getInstance().getContext().getString(R.string.linkButtonDialog));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setNeutralButton("OK", (dialog, id) -> client.createUsername());

        alertDialogBuilder.create().show();
    }
}
