package com.csc301.team22;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public final class Util {

    public static String getEditTextString(EditText editText, boolean reset) {
        String r = editText.getText().toString();
        if (reset) {
            editText.setText(null);
        }
        return r;
    }

    public static <T extends AppCompatActivity> void
    openActivity(AppCompatActivity source, Class<T> target) {
        Intent intent = new Intent(source.getApplicationContext(), target);
        source.startActivity(intent);
    }

    public static <T extends AppCompatActivity> void
    openActivity(AppCompatActivity source, Class<T> target, Bundle extras) {
        Intent intent = new Intent(source.getApplicationContext(), target);
        intent.putExtras(extras);
        source.startActivity(intent);
    }

    public static void showError(Context context, String title, String message, boolean cancelable) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(cancelable)
                .show();
    }

    public static void showError(Context context, String title, String message) {
        showError(context, title, message, true);
    }
}
