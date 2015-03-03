package com.cmu.as.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cmu.apointmentscedulerpatient.app.R;


public class app_DocumentActivity extends ActionBarActivity {

    private Button ret = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_document);
        this.ret = (Button) super.findViewById(R.id.doc_back);
        this.ret.setOnClickListener(new OnClickListenerImpl());
    }

    private class OnClickListenerImpl implements OnClickListener {
        public void onClick(View v){
            Intent it = new Intent(app_DocumentActivity.this, app_PastDetialActivity.class);
            app_DocumentActivity.this.startActivity(it);
            app_DocumentActivity.this.finish();
        }
    }

    public void newTab(View view) {
        Intent intent = new Intent(this, app_SelectHospital.class);
        startActivity(intent);
    }

    public void past(View view){
        Intent intent = new Intent(this, app_PastActivity.class);
        startActivity(intent);

    }

    public void future(View view){
        Intent intent = new Intent(this, app_FutureActivity.class);
        startActivity(intent);

    }

    public void info(View view){
        Intent intent = new Intent(this, app_InfoActivity.class);
        startActivity(intent);

    }

}
