package com.example.kryptonite.myblog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.Random;

public class Post_Activity extends AppCompatActivity {

    private ImageButton SelectImagebutton;
    private ImageView imageView;
    private TextView textView;
    private Button btn,btnSubmit;
    private TextView ClickHere;
    private EditText etPostTitle, etPostDescription;
    private static final int GALLERY_REQUEST = 1;
    private Bitmap bmap = null;
    public Uri selectedImageUri=null;
    private ProgressDialog mProgerss;


    private StorageReference mStorage,filepath;
    private DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_);
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");


        mProgerss = new ProgressDialog(this);

        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btn = (Button) findViewById(R.id.button);
        //imageView = (ImageView) findViewById(R.id.imageView);
        // textView =(TextView)findViewById(R.id.textView);
        SelectImagebutton =(ImageButton)findViewById(R.id.imageButton);
        // ClickHere =(TextView)findViewById(R.id.ClickHere);
        etPostTitle = (EditText) findViewById(R.id.etPostTitle);
        etPostDescription = (EditText) findViewById(R.id.etPostDescription);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();


            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hhhhh", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent,"Select Picture"), GALLERY_REQUEST);


            }
        });


    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
               selectedImageUri = data.getData();

                SelectImagebutton.setImageURI(selectedImageUri);
            }
        }


    }
    public void startPosting() {
        mProgerss.setMessage("Posting to Blog...");
        mProgerss.show();
        final String PostTitle = etPostTitle.getText().toString();
        final String Description = etPostDescription.getText().toString();

        if(!TextUtils.isEmpty(PostTitle) && !TextUtils.isEmpty(Description) && selectedImageUri!=null)
        {
           filepath= mStorage.child("Blog Image").child(mrandom());
            filepath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost= mDatabase.push();
                    newPost.child("Title").setValue(PostTitle);

                    newPost.child("desc").setValue(Description);
                    newPost.child("image url").setValue(downloadurl.toString());

                    Toast.makeText(getApplicationContext(),"Uploading complete..",Toast.LENGTH_SHORT).show();

                    mProgerss.dismiss();

                    startActivity(new Intent(Post_Activity.this,Main.class));


                }
            });




        }




    }
    public static String mrandom(){
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    
}
