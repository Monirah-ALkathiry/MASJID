package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.masajid.kacst.monirah.masjid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Monirah on 7/25/2018.
 */

public class MosqueImagAdapter extends RecyclerView.Adapter<MosqueImagAdapter.MosqueViewImag>{

    // private   Intent intent;
    private Context context;
    ArrayList<String> ImageUrls = new ArrayList<String>();


    public MosqueImagAdapter( Context context, ArrayList<String> imageUrls){

        this.context = context;
        this.ImageUrls= imageUrls;
    }



    @Override
    public MosqueViewImag onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mosque_image_adapter, parent, false);

        System.out.println("Image Size: "+ ImageUrls.size());
        return new MosqueViewImag(view);
    }

    @Override
    public void onBindViewHolder(MosqueViewImag holder, int position) {
        //   String imageUrl= null;
        // String MosqueCode = intent.getStringExtra("MOSQUE_CODE");

           /* if(ImageUrls.size()== 1){
                holder.textView.setText("لايوجد مسجد");
            }*/

        //TODO: If No Image Print No Image

        Picasso.with(context)
                .load("http://gis.moia.gov.sa/"+ImageUrls.get(position))
                .placeholder(R.drawable.mosque)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {

        return ImageUrls.size();
    }



public class MosqueViewImag extends RecyclerView.ViewHolder{

    private ImageView imageView;

    private TextView textView;

    public MosqueViewImag(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.Mosqueimage) ;
        itemView = (TextView) itemView.findViewById(R.id.NoImage);
    }
}
}