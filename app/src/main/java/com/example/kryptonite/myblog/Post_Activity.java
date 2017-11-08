package com.example.kryptonite.myblog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Post_Activity extends AppCompatActivity {

    private ImageButton SelectImagebutton;
    private ImageView imageView;
    private TextView textView;
    private Button btn;
    private TextView ClickHere;
    private EditText etPostTitle, etPostDescription;
    private static final int GALLERY_REQUEST = 1;
    private Bitmap bmap = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_);


        btn = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);
        // textView =(TextView)findViewById(R.id.textView);
        //SelectImagebutton =(ImageButton)findViewById(R.id.imageButton_one);
        // ClickHere =(TextView)findViewById(R.id.ClickHere);
        etPostTitle = (EditText) findViewById(R.id.etPostTitle);
        etPostDescription = (EditText) findViewById(R.id.etPostDescription);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hhhhh", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_REQUEST);


            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                Uri selectedImageUri = data.getData();

                imageView.setImageURI(selectedImageUri);
            }
        }

    /*public void uploadImage(View v){
      Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
          Bundle bd = data.getExtras();
            bmap =(Bitmap)bd.get("data");
      imageView.setImageBitmap(bmap);




           // Uri uri = data.getData();
           //SelectImagebutton.setImageURI(uri);
            Toast.makeText(getApplicationContext(),"hhhhh",Toast.LENGTH_SHORT).show();
        }
    }
    */
    }
}
