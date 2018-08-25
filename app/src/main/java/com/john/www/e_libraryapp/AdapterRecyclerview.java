package com.john.www.e_libraryapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.Constants;

import java.io.File;
import java.util.List;

public class AdapterRecyclerview extends RecyclerView.Adapter<AdapterRecyclerview.ShopViewHolder> {

    private Context context;
    private List<BookModel> lists;

    public AdapterRecyclerview(Context context, List<BookModel> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public ShopViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new ShopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {

        final BookModel bookModel = lists.get(position);
        holder.nameofbook.setText(bookModel.getImagename());

        Glide.with(context).load(bookModel.getImageUrl())
                .into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              Intent intent = new Intent(context, PdfActivity.class);
              intent.putExtra("pdf",bookModel.getPdfUrl());
              context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (lists != null)? lists.size(): 0;
    }

    class ShopViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private ImageView imageView;
        private TextView nameofbook;
        private LinearLayout linearLayout;


        public ShopViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            imageView =  (ImageView) mView.findViewById(R.id.pdfimage);
            nameofbook = (TextView) mView.findViewById(R.id.pdfname);
            linearLayout =(LinearLayout) mView.findViewById(R.id.click_library);







        }



    }
}
