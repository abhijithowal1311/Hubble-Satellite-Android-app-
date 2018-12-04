package com.abhijit.hubbletesting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhijit.hubbletesting.Model.Image;
import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    SharedPreferences viewType;
    Button mButton;
    private static int page;
    private TextView mTextView;
    private  Button next;
    Button previous;
    int mView;
    private List<Image> mImageList;
    private class viewhold extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img;
        Image image;
        public viewhold(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image1);
            itemView.setOnClickListener(this);
        }


        public void bind(Image i)
        {
            image = i;
            Glide.with(getActivity()).load(i.getThumbnailURL()).into(img);
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(getActivity(),ImageMainActivity.class);
            i.putExtra("image",image);
            startActivity(i);

        }
    }

    private class adapter extends RecyclerView.Adapter<viewhold>{

        int v1;
        public adapter(int viw){
            v1=viw;
        }

        @NonNull
        @Override
        public viewhold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(getActivity()).inflate(v1,
                    viewGroup,false);
            viewhold viewhld = new viewhold(v);
            return viewhld;
        }
        @Override
        public void onBindViewHolder(@NonNull viewhold viewhold, int i) {
                viewhold.bind(mImageList.get(i));
        }

        @Override
        public int getItemCount() {
            return mImageList.size();
        }
    }
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageList = new ArrayList<>();
        page = 1;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment,container,false);
        mRecyclerView = v.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity().getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
       // mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        mView = R.layout.griditem;
        next =v.findViewById(R.id.next);
        previous = v.findViewById(R.id.previous);
        mTextView = v.findViewById(R.id.textView);
        taskGet tk = new taskGet();
        tk.execute();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    page++;
                try {
                    //mTextView.setText(Integer.toString(page));
                    taskGet tk = new taskGet();
                    tk.execute();
                }catch (NullPointerException e){

                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                page--;
                try {
                    //mTextView.setText(Integer.toString(page));
                    taskGet tk = new taskGet();
                    tk.execute();
                }catch (NullPointerException e){

                }
            }
        });


        return v;

    }

    private class taskGet extends AsyncTask<Void, Void, List<Image>> {



        @Override
        protected List<Image> doInBackground(Void... voids) {
            List<Image> result=null;
            if(page>0){
                try {
                    result = getWebsite(page);
                }catch (NullPointerException e){

                }catch (IndexOutOfBoundsException e){

                }
          }
           //System.out.println(result);
           return result;
        }

        @Override
        protected void onPostExecute(List<Image> s) {
            super.onPostExecute(s);
            mImageList=s;
            if(mImageList !=null){

                mTextView.setText(Integer.toString(page));
                updateUI();

            }
    try {
        System.out.println(mImageList.get(0));
    }catch (NullPointerException e){

    }catch (IndexOutOfBoundsException e){

    }
        }
    }



  public List<Image>  getWebsite(int page){
           List<Image> imageList =new ArrayList<>();
            Document doc=null;
        try{
            /*For letter development
            String page = null;
            Uri builtUri = Uri.parse("http://hubblesite.org")
                    .buildUpon()
                    .path("images/gallery/page/"+page)
                    .build();
            System.out.println(builtUri.toString());
            */
            String url= "http://hubblesite.org/images/gallery/page/"+Integer.toString(page);


                    System.out.println("Page Number"+page);
                    if(page!=0) {
                        doc = Jsoup.connect(url).get();
                    }else
                    {
                        Toast.makeText(getActivity(),"huhh!!",Toast.LENGTH_SHORT);
                    }


            Elements links = doc.select("img");

            for(Element i : links){
                //System.out.println(i.absUrl("src"));
                String[] split = i.absUrl("src").split(".png");
                if(split[0].contains("thumbnail")){

                    String id = getIds(split[0]);
                    Image image =new Image(id,i.absUrl("src"));
                    imageList.add(image);
                }
                System.out.println(i.absUrl("src"));
            }
        }catch (IOException e){
            System.out.println("Error Fetching Images");
        }
        return imageList;
  }

  public String getIds(String url){

        String result=null;
      if (url != null) {

          String part1[] = url.split("/thumbnail/");
          String part2[] = part1[1].split("/");
          result =part2[0];
      }else{
          return null;
      }
        return result;
  }


  public void updateUI(){

        adapter mAdapter=new adapter(mView);
        
        mRecyclerView.setAdapter(mAdapter);
  }


}
