package com.csc301.team22;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public final class Util {

    private Util() {
    }

    public static String getEditTextString(EditText editText, boolean reset) {
        String r = editText.getText().toString();
        if (reset) {
            editText.setText(null);
        }
        return r;
    }

    static <T extends AppCompatActivity> void
    openActivity(AppCompatActivity source, Class<T> target) {
        Intent intent = new Intent(source.getApplicationContext(), target);
        source.startActivity(intent);
    }

    public static <T extends AppCompatActivity> void
    openActivity(AppCompatActivity source, Class<T> target, Bundle options) {
        Intent intent = new Intent(source.getApplicationContext(), target);
        source.startActivity(intent, options);
    }
}
