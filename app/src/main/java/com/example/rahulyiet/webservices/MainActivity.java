package com.example.rahulyiet.webservices;

import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.networkutil.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView imageview;
    TextView githubName,githubBio;
    Button go;
    EditText githubUsername;

    String BASE_URL = "https://api.github.com/users/";
    String data=null;

    String Name,Bio,Image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageview=findViewById(R.id.githubImage);
        githubName=findViewById(R.id.githubName);
        githubBio=findViewById(R.id.githubBio);
        go=findViewById(R.id.goButton);
        githubUsername=findViewById(R.id.githubUsername);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadData().execute();
            }
        });
    }
         class LoadData extends AsyncTask<Void,Void,Void>{

             @Override
             protected Void doInBackground(Void... voids) {
                 String gitName = githubUsername.getText().toString();
                 if(gitName==null){
                     return null;
                 }
                 String url=  BASE_URL+gitName;
                 data= NetworkUtil.makeServiceCall(url);
                 try {
                     JSONObject jsonObject = new JSONObject(data);
                     Name=jsonObject.getString("name");
                     Bio=jsonObject.getString("bio");
                     Image=jsonObject.getString("avatar_url");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

                 return null;
             }

             @Override
             protected void onPreExecute() {
                 super.onPreExecute();
             }

             @Override
             protected void onPostExecute(Void aVoid) {
                 super.onPostExecute(aVoid);

                 if(Name.equals("null")){
                     githubName.setText("null");
                 }else {
                     githubName.setText(Name);
                 }
                 if(Bio.equals("null")){
                     githubBio.setText("null");
                 }else {
                     githubBio.setText(Bio);
                 }
                 Glide.with(MainActivity.this).load(Image).into(imageview);
             }
         }
}
