package com.google.zxing.client.android;

import android.app.Activity;
import android.content.Intent;
import com.google.zxing.client.android.CosmeticViewer;
import com.google.zxing.client.android.FileIO;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddCosmeticDirectHandler extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcosmeticui);
        
        Button btnConfirm = (Button)findViewById(R.id.Button04);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  
        	  EditText etBrand = (EditText)findViewById(R.id.add_cosmetic_brand);
        	  EditText etName = (EditText)findViewById(R.id.add_cosmetic_name);
        	  EditText etExpired = (EditText)findViewById(R.id.add_cosmetic_expire);
        	  
        	  String strInput = "" + etBrand.getText() + ":" + etName.getText() + ":" + etExpired.getText();
        	  FileIO fileHandler = new FileIO();
        	  fileHandler.addCosmetic(strInput);
        	  
        	  Intent intent = new Intent(AddCosmeticDirectHandler.this, SETermActivity.class);
        	  startActivity(intent);
          }
        });
        
        Button btnInit = (Button)findViewById(R.id.Button05);

        btnInit.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  // Get Text by ID and initialize

        	  EditText etBrand = (EditText)findViewById(R.id.add_cosmetic_brand);
        	  EditText etName = (EditText)findViewById(R.id.add_cosmetic_name);
        	  EditText etExpired = (EditText)findViewById(R.id.add_cosmetic_expire);

        	  etBrand.setText("");
        	  etName.setText("");
        	  etExpired.setText("");
          }
        });
    }

}
