package com.example.passage.shelf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.passage.R;
public class FavoriteActivity extends AppCompatActivity {
    private TextView title;
    private TextView author;
    private TextView main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initView();
        getView();
    }
    public void initView()
    {
        title=findViewById(R.id.title_favorite);
        author=findViewById(R.id.author_favorite);
        main=findViewById(R.id.main_favorite);
    }
    public void getView()
    {
        Intent intent=getIntent();
        String artileTiitle=intent.getStringExtra("TITLE");
        String artitleAuthor=intent.getStringExtra("AUTHOR");
        String articleMain=intent.getStringExtra("MAIN");
        title.setText(artileTiitle);
        author.setText(artitleAuthor);
        main.setText(articleMain);
    }

}
