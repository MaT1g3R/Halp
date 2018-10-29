package com.csc301.team22;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class ExpandableView implements IExpandableView {
    View titleView;
    View bodyView;
    LinearLayout layout;
    LinearLayout.LayoutParams layoutParams;
    boolean expanded = false;

    public static LinearLayout.LayoutParams defaultLayoutParams() {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public LinearLayout getLayout() {
        return layout;
    }

    void setUpViews() {
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(titleView, layoutParams);
        titleView.setOnClickListener(this);
    }

    public void expand() {
        layout.addView(bodyView, layoutParams);
        expanded = true;
    }

    public void collapse() {
        layout.removeView(bodyView);
        expanded = false;
    }

    @Override
    public void onClick(View v) {
        if (expanded) {
            collapse();
        } else {
            expand();
        }
    }
}
