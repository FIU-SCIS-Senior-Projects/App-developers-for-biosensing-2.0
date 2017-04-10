package com.example.biosensing;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GraphsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        getActionBar().setDisplayHomeAsUpEnabled(true);




    }


        public void targetTemp(View view) {
        Intent intent = new Intent(this, AllTargetTemp.class);
        intent.putExtra("target", "target");
        startActivity(intent);
    }

    public void ambientTemp(View view) {
        Intent intent = new Intent(this, AllTargetTemp.class);
        intent.putExtra("target", "ambient");
        startActivity(intent);
    }

}
