package com.google.zxing.client.android;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class CosmeticViewer extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cosmetic);
        Button btnAddByQR = (Button)findViewById(R.id.Button02);

        btnAddByQR.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  Intent intent = new Intent(CosmeticViewer.this, CaptureActivity.class);
        	  startActivity(intent);
          }
        });
        
        Button btnAddDirectly = (Button)findViewById(R.id.Button03);
        
        btnAddDirectly.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CosmeticViewer.this, AddCosmeticDirectHandler.class);
				startActivity(intent);
			}
		});
    }

}
