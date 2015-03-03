package com.cmu.as.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.entities.patient.Patient;
import com.cmu.as.socket.PatSocket;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class app_InfoActivity extends ActionBarActivity {
    PatSocket patSocket;

    private EditText name = null;
    private EditText age = null;
    private EditText sex = null;
    private EditText insurance = null;
    private Button cancel = null;
    private Button confirm = null;
    private RelativeLayout switchAvatar;
    private ImageView faceImage;
    private String items[] = new String[] { "Take photo" };
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_info);
        patSocket = (PatSocket) getApplicationContext();
        switchAvatar = (RelativeLayout) findViewById(R.id.switch_face_rl);
        faceImage = (ImageView) findViewById(R.id.face);
        switchAvatar.setOnClickListener(listener);

        this.name = (EditText)super.findViewById(R.id.name);
        this.age = (EditText)super.findViewById(R.id.age);
        this.sex = (EditText)super.findViewById(R.id.sex);
        this.insurance = (EditText)super.findViewById(R.id.insurance);
        this.cancel = (Button)super.findViewById(R.id.info_cancel);
        this.confirm = (Button)super.findViewById(R.id.info_confirm);

        this.name.setText(patSocket.getPatInerface().getPatName());
        this.age.setText(patSocket.getPatInerface().getPatAge());
        this.sex.setText(patSocket.getPatInerface().getPatGender());
        this.insurance.setText(patSocket.getPatInerface().getPatInsurance());

        this.confirm.setOnClickListener(new conOnClickListener());
        this.cancel.setOnClickListener(new canOnClickListener());
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            showDialog();
        }
    };

    private void showDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Set photo")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intentFromCapture = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);

                        if (app_Tool.hasSdcard()) {
                            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                            File file = new File(path,IMAGE_FILE_NAME);
                            intentFromCapture.putExtra(
                                    MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(file));
                        }

                        startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (app_Tool.hasSdcard()) {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path,IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(app_InfoActivity.this, "Can not find SD card, unable to save image",Toast.LENGTH_LONG).show();
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * photo zoom
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");
        // aspectX aspectY
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(this.getResources(),photo);
            faceImage.setImageDrawable(drawable);
        }
    }



    private class conOnClickListener implements View.OnClickListener {
        public void onClick(View v){
            if  (!(isNumeric(age.getText().toString()))){
                //show a toast
                Context context = getApplicationContext();
                CharSequence text = "Invalid Age Format!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else if (!(sex.getText().toString().equals("F")||sex.getText().toString().equals("M")||sex.getText().toString().equals("N"))){
                //show a toast
                Context context = getApplicationContext();
                CharSequence text = "Invalid Sex Format!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                patSocket.sendOutput(patSocket.packSocket5(9, name.getText().toString(), age.getText().toString(),
                        sex.getText().toString(), insurance.getText().toString(), patSocket.getPatInerface().getPatID()));

                //updated successfully in database
                if ((patSocket.getInput()).equals("1")) {
                    patSocket.getPatInerface().setPat((Patient) patSocket.getInput());

                    //show a toast
                    Context context = getApplicationContext();
                    CharSequence text = "Updated Successfully!";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
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
            name.setText(patSocket.getPatInerface().getPatName());
            age.setText(patSocket.getPatInerface().getPatAge());
            sex.setText(patSocket.getPatInerface().getPatGender());
            insurance.setText(patSocket.getPatInerface().getPatInsurance());
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
