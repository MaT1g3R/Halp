package com.csc301.team22;

import android.view.View;

public interface IExpandableView extends View.OnClickListener {
    void expand();

    void collapse();

    @Override
    void onClick(View v);
}
