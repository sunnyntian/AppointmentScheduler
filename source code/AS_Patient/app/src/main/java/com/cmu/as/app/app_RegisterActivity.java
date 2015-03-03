package com.cmu.as.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.entities.patient.Patient;
import com.cmu.as.socket.PatSocket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class app_RegisterActivity extends ActionBarActivity {
    PatSocket patSocket;

    private EditText id = null;
    private EditText name = null;
    private EditText age = null;
    private EditText sex = null;
    private EditText insurance = null;
    private EditText pw = null;
    private EditText pw_again = null;
    private Button cancel = null;
    private Button confirm = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_register);
        patSocket = (PatSocket) getApplicationContext();
        this.name = (EditText)super.findViewById(R.id.name);
        this.age = (EditText)super.findViewById(R.id.age);
        this.sex = (EditText)super.findViewById(R.id.sex);
        this.insurance = (EditText)super.findViewById(R.id.insurance);
        this.pw = (EditText)super.findViewById(R.id.reg_password);
        this.pw_again = (EditText)super.findViewById(R.id.reg_password_again);
        this.id = (EditText)super.findViewById(R.id.reg_id);
        this.cancel = (Button)super.findViewById(R.id.reg_cancel);
        this.confirm = (Button)super.findViewById(R.id.reg_confirm);

        this.confirm.setOnClickListener(new conOnClickListener());
        this.cancel.setOnClickListener(new canOnClickListener());
    }

    private class conOnClickListener implements View.OnClickListener {
        public void onClick(View v){
            if (!(pw.getText().toString().equals(pw_again.getText().toString()))) {
                //show a toast
                Context context = getApplicationContext();
                CharSequence text = "Password doesn't match!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else if (!(isEmail(id.getText().toString()))) {
                //show a toast
                Context context = getApplicationContext();
                CharSequence text = "Invalid Email!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else if (!(isNumeric(age.getText().toString()))){
                //show a toast
                Context context = getApplicationContext();
                CharSequence text = "Invalid Age Format!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else if (!(sex.getText().toString().equals("F")||
                    sex.getText().toString().equals("M")||sex.getText().toString().equals("N"))){
                //show a toast
                Context context = getApplicationContext();
                CharSequence text = "Invalid Sex Format!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                    patSocket.sendOutput(patSocket.packSocket6(52, id.getText().toString(),
                            name.getText().toString(), age.getText().toString(),
                            sex.getText().toString(), insurance.getText().toString(), pw.getText().toString()));

                    //updated successfully in database
                    if ((patSocket.getInput()).equals("1")) {

                        //show a toast
                        Context context = getApplicationContext();
                        CharSequence text = "Registered Successfully!\nPlease log in!";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        Intent intent = new Intent(app_RegisterActivity.this, app_LogIn.class);
                        startActivity(intent);
                    } else {
                        //show a toast
                        Context context = getApplicationContext();
                        CharSequence text = "Email has already been registered!";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        Intent intent = new Intent(app_RegisterActivity.this, app_LogIn.class);
                        startActivity(intent);
                    }
                }
        }
        public boolean isEmail(String email) {
            String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
                    "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(email);

            return m.matches();
        }

        public boolean isNumeric(String str) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return false;
            }
            return true;
        }
    }

    private class canOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(app_RegisterActivity.this, app_LogIn.class);
            startActivity(intent);
        }
    }


}
