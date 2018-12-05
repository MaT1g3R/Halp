package com.csc301.team22.views;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csc301.team22.EButtonState;
import com.csc301.team22.R;
import com.csc301.team22.api.JobRequest;

public class RequestCardView extends ExpandableView {
    private Button buttonTitle;
    private TextView textViewDescription;
    private RequestCardObservable observable;
    private JobRequest job;

    public RequestCardView(Context context, JobRequest job) {
        super();
        layout = new LinearLayout(context);
        this.job = job;

        buttonTitle = new Button(layout.getContext());
        buttonTitle.setText(job.getTitle());
        setButtonColorDefault();

        textViewDescription = new TextView(layout.getContext());
        textViewDescription.setText(job.getDescription());
        textViewDescription.setPadding(10, 10, 10, 10);

        titleView = buttonTitle;
        bodyView = textViewDescription;

        layoutParams = defaultLayoutParams();
        setUpViews();

        this.observable = new RequestCardObservable(this);
    }

    public JobRequest getJob() {
        return job;
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
