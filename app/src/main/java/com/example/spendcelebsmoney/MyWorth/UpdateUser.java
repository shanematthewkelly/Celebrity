package com.example.spendcelebsmoney.MyWorth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.spendcelebsmoney.Core.MainActivity;
import com.example.spendcelebsmoney.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Objects;

public class UpdateUser extends AppCompatActivity {

    private CircularImageView userPhoto;
    private EditText userNameInput, userDescInput, userWorth;
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
//    public static final String PHOTO = "PHOTO";
    public static final String WORTH = "WORTH";
    static int PReqCode = 100;
    static int REQUESTCODE = 200;
    Context context = this;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create);

        //init views
        ImageView backarrow = findViewById(R.id.backarrow2);
        userNameInput = findViewById(R.id.userNameInput);
        userDescInput = findViewById(R.id.userDescInput);
        userWorth = findViewById(R.id.userWorth);
        Button userCreate = findViewById(R.id.confirmCreate);
        userPhoto = findViewById(R.id.userPhoto);


        Intent fetchingIntent = getIntent();

        //passing in the old data
        if (fetchingIntent.hasExtra(ID)) {
            userNameInput.setText(fetchingIntent.getStringExtra(NAME));
            userDescInput.setText(fetchingIntent.getStringExtra(DESCRIPTION));
            userWorth.setText(fetchingIntent.getStringExtra(WORTH));
//            userWorth.setText(fetchingIntent.getIntExtra(WORTH, 1000));
        }

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        userCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Accessing the device's camera, depending on OS version
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermissions();

                } else {

                    openGallery();
                }
            }
        });



        //hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    private void updateUser() {

        String name = userNameInput.getText().toString();
        String description = userDescInput.getText().toString();
        int worth = Integer.parseInt(userWorth.getText().toString());

        if (name.trim().isEmpty() || description.trim().isEmpty()) {

            Toast.makeText(this, "Please Correct The Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        //sending the updated info back to the activity
        Intent backToActivity = new Intent();
        backToActivity.putExtra(NAME , name);
        backToActivity.putExtra(DESCRIPTION , description);
        backToActivity.putExtra(WORTH, worth);

        int itemId = getIntent().getIntExtra(ID, -1);

        if (itemId != -1) {
            backToActivity.putExtra(ID, itemId);
        }

        setResult(RESULT_OK, backToActivity);
        finish();


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    //For API 22 and above this method is ran for accessing the user's camera
    private void checkAndRequestForPermissions() {


        if (ContextCompat.checkSelfPermission(UpdateUser.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateUser.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(UpdateUser.this, "Please Accept For Required Permissions", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.requestPermissions(UpdateUser.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PReqCode);
                }
            }

        }
        else
            openGallery();

    }

    //For API 21 and below this method is ran for accessing the user's camera
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null ) {

            // the user has successfully picked an image - load it into place
            Uri pickedImgUri = data.getData();
            userPhoto.setImageURI(pickedImgUri);


        }


    }

}
