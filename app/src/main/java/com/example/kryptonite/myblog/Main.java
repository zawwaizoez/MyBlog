package com.example.kryptonite.myblog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Main extends AppCompatActivity {

    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mBlogList =(RecyclerView)findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();

            FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                    Blog.class,
                    R.layout.blog_row,
                   BlogViewHolder.class,
                    mDatabase

            ) {
                @Override
                protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                    viewHolder.setTitle(model.getTitle());
                    viewHolder.setDesc(model.getDesc());
                }
            };


        mBlogList.setAdapter(firebaseRecyclerAdapter);


    }

    public static class BlogViewHolder extends  RecyclerView.ViewHolder{
        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setTitle(String title){
            TextView post_title =(TextView)mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }
        public void setDesc(String desc){
            TextView post_desc=(TextView)mView.findViewById(R.id.post_text);
            post_desc.setText(desc);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_add){
            startActivity(new Intent(Main.this,Post_Activity.class));
        }


        return  super.onOptionsItemSelected(item);
    }

}