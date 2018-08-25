package com.john.www.e_libraryapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BorowerFragment extends Fragment {


    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<ReviewModel, ShopViewHolder> mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favourites_fragment, container, false);





        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setUpAdapter();
        recyclerView.setAdapter(mAdapter);
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }
    private void setUpAdapter() {

        Query query = FirebaseFirestore.getInstance().collection("review");


        FirestoreRecyclerOptions<ReviewModel> options = new FirestoreRecyclerOptions.Builder<ReviewModel>().setQuery(query, ReviewModel.class).build();


        mAdapter = new FirestoreRecyclerAdapter<ReviewModel, ShopViewHolder>(options) {
            @Override
            protected void onBindViewHolder(ShopViewHolder holder, int position, final ReviewModel model) {


                holder.setTitle(model.getName());
                holder.setBookName(model.getNameofbook());
                holder.setDateofcollection(model.getDateofcollection());













            }

            @NonNull
            @Override
            public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ShopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewsinglelayout, parent, false));

            }
        };



    }

    class ShopViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private TextView nameofbook, nameofborrower, dateofcollection;


        public ShopViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            nameofbook = (TextView) mView.findViewById(R.id.nameofbook);
            nameofborrower = (TextView) mView.findViewById(R.id.nameofborrower);
            dateofcollection = (TextView) mView.findViewById(R.id.dateofcollection);








        }


        public void setTitle(String title) {
            nameofborrower.setText(title);
        }
        public void setBookName(String title) {
            nameofbook.setText(title);
        }
        public void setDateofcollection(String title) {
            dateofcollection.setText( DateUtils.getRelativeTimeSpanString(Long.valueOf(title)));
        }
    }
}
