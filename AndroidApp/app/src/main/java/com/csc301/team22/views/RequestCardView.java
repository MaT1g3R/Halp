package com.csc301.team22.views;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csc301.team22.EButtonState;
import com.csc301.team22.R;

public class RequestCardView extends ExpandableView {
    private Button buttonTitle;
    private TextView textViewDescription;
    private RequestCardObservable observable;

    public RequestCardView(Context context, String title, String description) {
        super();
        layout = new LinearLayout(context);

        buttonTitle = new Button(layout.getContext());
        buttonTitle.setText(title);
        setButtonColorDefault();

        textViewDescription = new TextView(layout.getContext());
        textViewDescription.setText(description);
        textViewDescription.setPadding(10, 10, 10, 10);

        titleView = buttonTitle;
        bodyView = textViewDescription;

        layoutParams = defaultLayoutParams();
        setUpViews();

        this.observable = new RequestCardObservable(this);
    }

    public RequestCardObservable getObservable() {
        return observable;
    }

    private void setButtonColorDefault() {
        buttonTitle.setBackgroundColor(layout.getContext().getColor(R.color.base01));
    }

    private void setButtonColorClicked() {
        buttonTitle.setBackgroundColor(layout.getContext().getColor(R.color.base0B));
    }

    public void setExpanded() {
        super.expand();
        setButtonColorClicked();
    }

    public void setCollapsed() {
        super.collapse();
        setButtonColorDefault();
    }

    @Override
    public void expand() {
        setExpanded();
        observable.notifyObservers(EButtonState.EXPANDED);
    }

    @Override
    public void collapse() {
        setCollapsed();
        observable.notifyObservers(EButtonState.COLLAPSED);
    }
}
