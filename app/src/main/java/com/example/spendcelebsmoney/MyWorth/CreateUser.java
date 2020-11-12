package com.example.spendcelebsmoney.MyWorth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

public class CreateUser extends AppCompatActivity {

    private CircularImageView userPhoto;
    private EditText userNameInput, userDescInput, userWorth;
    Context context = this;

    public static final String NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PHOTO = "PHOTO";
    public static final String WORTH = "WORTH";

    static int PReqCode = 300;
    static int REQUESTCODE = 400;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set Content view for Activity
        setContentView(R.layout.activity_create);

        //init views
        ImageView backarrow = findViewById(R.id.backarrow2);
        Button userCreate = findViewById(R.id.confirmCreate);
        userNameInput = findViewById(R.id.userNameInput);
        userDescInput = findViewById(R.id.userDescInput);
        userWorth = findViewById(R.id.userWorth);
        userPhoto = findViewById(R.id.userPhoto);

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Accessing the device's camera, depending on API level
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermissions();

                } else {

                    openGallery();
                }
            }
        });

        //if the user clicks the back arrow image - go back to MainActivity
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //On click handler for the user create button - makes a call to the repository
        userCreate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                createUser();

                //hide action bar
                Objects.requireNonNull(getSupportActionBar()).hide();
            }
        });

    }



    private void createUser() {

        String name = userNameInput.getText().toString();
        String description = userDescInput.getText().toString();
       String photo = userPhoto.toString();
//        Uri photo = Uri.parse(userPhoto.toString());
        int worth = Integer.parseInt(userWorth.getText().toString());

        if (name.trim().isEmpty() || description.trim().isEmpty()) {

            Toast.makeText(this, "Please Correct The Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        //Sending the data back to the Activity
        Intent backToActivity = new Intent();
        backToActivity.putExtra(NAME , name);
        backToActivity.putExtra(DESCRIPTION , description);
        backToActivity.putExtra(PHOTO , photo);
        backToActivity.putExtra(WORTH, worth);

        setResult(RESULT_OK, backToActivity);
        finish();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    //For API 22 and above this method is ran for accessing the user's camera
    private void checkAndRequestForPermissions() {


        if (ContextCompat.checkSelfPermission(CreateUser.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(CreateUser.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(CreateUser.this, "Please Accept For Required Permissions", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.requestPermissions(CreateUser.this,
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


}
