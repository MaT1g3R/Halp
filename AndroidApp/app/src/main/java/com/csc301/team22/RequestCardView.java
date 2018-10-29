package com.csc301.team22;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RequestCardView extends ExpandableView {
    private Button buttonTitle;
    private TextView textViewDescription;

    public RequestCardView(Context context, String title, String description) {
        super();
        layout = new LinearLayout(context);

        buttonTitle = new Button(layout.getContext());
        buttonTitle.setText(title);
        setButtonColorDefault();

        textViewDescription = new TextView(layout.getContext());
        textViewDescription.setText(description);
        textViewDescription.setPadding(10,10,10,10);

        titleView = buttonTitle;
        bodyView = textViewDescription;

        layoutParams = defaultLayoutParams();
        setUpViews();
    }

    private void setButtonColorDefault() {
        buttonTitle.setBackgroundColor(layout.getContext().getColor(R.color.base01));
    }

    private void setButtonColorClicked() {
        buttonTitle.setBackgroundColor(layout.getContext().getColor(R.color.base0B));
    }

    @Override
    public void expand() {
        super.expand();
        setButtonColorClicked();
    }

    @Override
    public void collapse() {
        super.collapse();
        setButtonColorDefault();
    }
}
