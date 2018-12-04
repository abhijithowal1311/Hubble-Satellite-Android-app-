package com.abhijit.hubbletesting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HubbleCurrentFeed extends AppCompatActivity {
    ImageView mImageView;
    TextView mTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_hubble);
        mTextView = findViewById(R.id.getFeed);

        mImageView = findViewById(R.id.imgLive);
        getImageData data= new getImageData();
        data.execute();
    }

    private class getImageData extends AsyncTask<Void, Void, String[]> {


        @Override
        protected String[] doInBackground(Void... voids) {


           String[] contents= getLiveFeed();
            return contents;
        }

        @Override
        protected void onPostExecute(String[] url) {
            super.onPostExecute(url);
            if(url[0] != null){
                Glide.with(getApplicationContext()).load(url[0]).into(mImageView);
            }
            if(url[1]!=null){
                mTextView.setText(url[1]);
            }

        }
    }


    public String[] getLiveFeed(){
        String[] contents=new String[2];
        String data=null;
        String url=null;
        try {
            Document doc = Jsoup.connect("http://spacetelescopelive.org/").get();
         Elements links  = doc.select("img");

        Elements linksNew= doc.getElementsByClass("message_content");
        for(Element link :linksNew){

            System.out.println(linksNew.first());
           // System.out.println(link.children());
              String finals=link.toString().replaceFirst(link.children().toString()," ");
              //System.out.println(finals);
              String[] split= finals.split("\">");
               String[] split2= split[1].split("</d");
                data= split2[0];
                //System.out.println(link);
        }
        for(Element link: links){

               // System.out.println(link.absUrl("src"));
                if(link.absUrl("src").contains("/images")){
                     url = link.absUrl("src");

                    // System.out.println(url);
                    break;
                }
            }
        } catch (IOException e) {

        }catch (NullPointerException e){

        }
        contents[0]=url;
        contents[1]=data;
        return contents;
    }
}
