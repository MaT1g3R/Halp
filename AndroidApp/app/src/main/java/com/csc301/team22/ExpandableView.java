package com.csc301.team22;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableView extends LinearLayout implements View.OnClickListener {

    private String title;
    private String description;
    private Button buttonTitle;
    private TextView textViewDescription;
    private LinearLayout.LayoutParams layoutParams;
    private boolean expanded = false;

    public ExpandableView(Context context, String title, String description) {
        super(context);
        this.setOrientation(VERTICAL);
        this.title = title;
        this.description = description;

        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        buttonTitle = new Button(getContext());
        buttonTitle.setText(this.title);

        textViewDescription = new TextView(getContext());
        textViewDescription.setText(this.description);

        addView(buttonTitle, layoutParams);

        this.buttonTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (expanded) {
            removeView(textViewDescription);
            expanded = false;
        } else {
            addView(textViewDescription, layoutParams);
            expanded = true;
        }
    }
}
