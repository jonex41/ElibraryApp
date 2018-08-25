package com.john.www.e_libraryapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public abstract class GroupActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<BookModel, LibraryViewHolder> mAdapter;
    private AlertDialog.Builder builder1;
    private ProgressDialog progressDialog;
    private TextView webView;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity);
         builder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        Toolbar toolbar = findViewById(R.id.toolbar);
          progressDialog = new ProgressDialog(GroupActivity.this);
        setSupportActionBar(toolbar);
        webView = findViewById(R.id.webview);
       // webView.getSettings().setJavaScriptEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(getString().toUpperCase());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        setUpAdapter();
        recyclerView.setAdapter(mAdapter);
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

        Query query = FirebaseFirestore.getInstance().collection(getString());


        FirestoreRecyclerOptions<BookModel> options = new FirestoreRecyclerOptions.Builder<BookModel>().setQuery(query, BookModel.class).build();


        mAdapter = new FirestoreRecyclerAdapter<BookModel, LibraryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(final LibraryViewHolder holder, int position, final BookModel model) {


                holder.setTitle(model.getImagename());







                Glide.with(getApplicationContext()).load(model.getImageUrl())
                        .into(holder.imageView);
                final String spinner = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("spinner", null);

                holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {


                        builder1.setMessage("Are you sure you want to delete this pdf...");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(spinner.contains("admin")){
                                            DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                                            String id2 = snapshot.getId();

                                            FirebaseFirestore.getInstance().collection(model.getTypeofbook()).document(id2).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(GroupActivity.this, "Pdf has been deleted", Toast.LENGTH_SHORT).show();
                                                    }else {

                                                        Toast.makeText(GroupActivity.this, "Please try again later", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }else {
                                            Toast.makeText(GroupActivity.this, "Please you can not delete this data...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                        builder1.setNegativeButton(
                                "Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                        return true;
                    }
                });

               holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        builder1.setMessage("Are you sure you want to download this pdf.");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        progressDialog.setIndeterminate(true);
                                        progressDialog.setMessage("Loading...");
                                        progressDialog.show();
                                        String regno = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("regno", "Registration number");

                                        ReviewModel reviewModel = new ReviewModel();
                                        reviewModel.setDateofcollection(System.currentTimeMillis()+"");
                                        reviewModel.setName(regno);
                                        reviewModel.setNameofbook(model.getImagename());

                                        FirebaseFirestore.getInstance().collection("review").add(reviewModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull final Task<DocumentReference> task) {
                                                if(task.isSuccessful()){



                                                    final DatabaseFavourites databaseFavourites = new DatabaseFavourites(getApplicationContext());

                                                  //  FirebaseStorage storage = FirebaseStorage.getInstance();
                                                    StorageReference storageRef = storage.getReferenceFromUrl(model.getPdfUrl());
                                                 //   StorageReference  islandRef = storageRef.child("file.txt");

                                                    File rootPath = new File(Environment.getExternalStorageDirectory(), "ElibraryPdfs");
                                                    if(!rootPath.exists()) {
                                                        rootPath.mkdirs();
                                                    }

                                                    final File localFile = new File(rootPath,model.getImagename()+System.currentTimeMillis()+".pdf");

                                                    storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                            // Log.e("firebase ",";local tem file created  created " +localFile.toString());
                                                            //  updateDb(timestamp,localFile.toString(),position);
                                                            if(task.isSuccessful()) {
                                                                BookModel bookModel = new BookModel();
                                                                bookModel.setPdfUrl(localFile.toString());
                                                                bookModel.setImagename(model.getImagename());
                                                                bookModel.setImageUrl(model.getImageUrl());
                                                                if (!databaseFavourites.checkUser(bookModel.getImagename())) {
                                                                    databaseFavourites.addtoCart(bookModel);
                                                                }
                                                                  webView.setText(localFile.getAbsolutePath().toString());
                                                                progressDialog.dismiss();
                                                            }else {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(GroupActivity.this, "Please try again later...", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                            //  Log.e("firebase ",";local tem file not created  created " +exception.toString());
                                                        }
                                                    });


                                                  //  downloadFile(model);

                                                }else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(GroupActivity.this, "Please try again later...", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();


                         

                    }
                });
            }

            @NonNull
            @Override
            public LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new LibraryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false));

            }
        };



    }
    private void downloadFile(final BookModel model) {
        final DatabaseFavourites databaseFavourites = new DatabaseFavourites(getApplicationContext());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(model.getPdfUrl());
        StorageReference  islandRef = storageRef.child("file.txt");

        File rootPath = new File(Environment.getExternalStorageDirectory(), "ElibraryPdfs");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath,model.getImagename()+System.currentTimeMillis()+".pdf");

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
               // Log.e("firebase ",";local tem file created  created " +localFile.toString());
                //  updateDb(timestamp,localFile.toString(),position);
                BookModel bookModel = new BookModel();
                bookModel.setPdfUrl(localFile.toString());
                bookModel.setImagename(model.getImagename());
                bookModel.setImageUrl(model.getImageUrl());
                if (!databaseFavourites.checkUser(bookModel.getImagename())) {
                    databaseFavourites.addtoCart(bookModel);
                }
                //  webView.setText(finalLocalFile.getAbsolutePath().toString());
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
              //  Log.e("firebase ",";local tem file not created  created " +exception.toString());
            }
        });
    }
    private void SaveImage() {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_pdfs");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "pdf-"+ n +".pdf";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();

    }
    public abstract String getString();
    class LibraryViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private ImageView imageView;
        private TextView nameofbook;
        private LinearLayout linearLayout;


        public LibraryViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            imageView =  (ImageView) mView.findViewById(R.id.pdfimage);
            nameofbook = (TextView) mView.findViewById(R.id.pdfname);
            linearLayout =(LinearLayout) mView.findViewById(R.id.click_library);







        }


        public void setTitle(String title) {
            nameofbook.setText(title);
        }
    }
}
