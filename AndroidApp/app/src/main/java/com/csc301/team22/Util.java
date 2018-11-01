package com.csc301.team22;

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
}
