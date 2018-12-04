package com.abhijit.hubbletesting;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.abhijit.hubbletesting.Model.Image;
import com.abhijit.hubbletesting.Model.ImageFormat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageMainActivity extends AppCompatActivity {

    TextView t ;
    ScrollView mScrollView;
    TextView t2;
    ProgressBar mProgressBar;
    ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_main_layout);
        t= findViewById(R.id.txt);
        mScrollView = findViewById(R.id.scrollbar);
        mProgressBar = findViewById(R.id.progressbar);

        t2=findViewById(R.id.name);
        mImageView = findViewById(R.id.imageview);
        Image i =(Image)getIntent().getSerializableExtra("image");
        if(i!=null){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://hubblesite.org/api/v3/")
                .build();

        hubbleImageInterface hubbleInterface = retrofit.create(hubbleImageInterface.class);
        hubbleInterface.GetPage(i.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageFormat>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ImageFormat s) {
                        //System.out.println(s.getDescription());
                       if(s.getDescription() != null){
                        t.setText(s.getDescription());}
                        else{
                           mScrollView.setVisibility(View.INVISIBLE);
                       }
                        t2.setText(s.getName());
                        System.out.println();
                        if(s.getImage_files().get(0).getFile_url().contains("mini_thumb")){

                            System.out.println("contains mini thumb");
                            Glide.with(ImageMainActivity.this).load(s.getImage_files().
                            get(5).getFile_url()).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    return false;
                                }
                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    return false;
                                }
                            }).into(mImageView);
                        }else{

                            System.out.println(s.getImage_files().get(0));
                            Glide.with(ImageMainActivity.this).load(s.getImage_files().
                                    get(0).getFile_url()).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    mProgressBar.setVisibility(View.INVISIBLE);

                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    return false;
                                }
                            }).into(mImageView);

                        } }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        }
    }

}
