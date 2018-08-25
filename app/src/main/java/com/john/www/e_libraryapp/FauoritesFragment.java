package com.john.www.e_libraryapp;

import android.content.pm.FeatureGroupInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FauoritesFragment extends Fragment {

    DatabaseFavourites databaseFavourites;
    private List<BookModel> models = new ArrayList<>();
    private AdapterRecyclerview adapterRecyclerview;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favourites_fragment, container, false);
        databaseFavourites = new DatabaseFavourites(getContext());

        adapterRecyclerview = new AdapterRecyclerview(getContext(), models);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }




        new FecthString().execute();
        recyclerView.setAdapter(adapterRecyclerview);
        adapterRecyclerview.notifyDataSetChanged();
        return v;



    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            new FecthString().execute();
        }
    }

    class FecthString extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {

                if(models.size() >0){
                    models.clear();
                }
                models.addAll(databaseFavourites.getindivMessages());


                return null;
            }

            @Override
            public void onPostExecute(Void count) {
                adapterRecyclerview.notifyDataSetChanged();
            }
        }

}
