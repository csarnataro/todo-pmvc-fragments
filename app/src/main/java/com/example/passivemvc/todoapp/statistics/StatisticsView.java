package com.example.passivemvc.todoapp.statistics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.passivemvc.todoapp.R;

/**
 * @author Christian Sarnataro
 *         Created on 18/04/16.
 */
public class StatisticsView extends FrameLayout {

    private TextView statistics;

    public StatisticsView(Context context) {
        super(context);
    }

    public StatisticsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatisticsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initComponents() {
        statistics = (TextView) findViewById(R.id.statistics);
    }

    public void setStatisticsText(String displayString) {
        statistics.setText(displayString);
    }
}
