package com.google.zxing.client.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;


public class SETermActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final TabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("sch_tab")
        		.setIndicator("Schedule")
        		.setContent(new Intent(this, ScheduleViewer.class)));

        tabHost.addTab(tabHost.newTabSpec("cosmetic_tab")
        		.setIndicator("Cosmetics")
        		.setContent(new Intent(this, CosmeticViewer.class)));

    }
}