package com.john.www.e_libraryapp.Rgistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.john.www.e_libraryapp.MainActivity;
import com.john.www.e_libraryapp.R;

import javax.annotation.Nullable;


public class SignActivity extends AppCompatActivity implements  View.OnClickListener {

    private static final String TAG = "EmailPassword";


    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private TextInputEditText mEmailField;
    private TextInputEditText mPasswordField;
    private TextInputEditText mnameField;

    private TextInputLayout mEmailFieldla;
    private TextInputLayout mPasswordFieldla;
    private TextInputLayout mnameFieldla;

    String email, password ,name;

    private ProgressDialog progressDialog;
    private InputValidation validation;
    private String userId;

    private Button register, signin;

    // [START declare_auth]

    private FirebaseFirestore mDatabase;
    private FirebaseFirestore profileDatabase;
    private Spinner spinner;
    private String anyUser;
    private EditText editText;
    private Button button;

    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_activity);
        progressDialog = new ProgressDialog(this);
        spinner = (Spinner)findViewById(R.id.spinner);
        final String email = getIntent().getStringExtra("regno");
        String password = getIntent().getStringExtra("password");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.useroradmin, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);



        validation = new InputValidation(getApplicationContext());
        // Views

        mEmailField = (TextInputEditText) findViewById(R.id.register_field_email);
        mEmailField.setText(email);
        mPasswordField = (TextInputEditText) findViewById(R.id.register_field_password);
        mPasswordField.setText(password);

        mEmailFieldla = (TextInputLayout) findViewById(R.id.register_layout_email);
        mPasswordFieldla = (TextInputLayout) findViewById(R.id.register_layout_passsword);
        // Buttons
        register=(Button) findViewById(R.id.create_new_account);
        signin=(Button) findViewById(R.id.signin);
        register.setOnClickListener(this);
        signin.setOnClickListener(this);

         editText = (EditText)findViewById(R.id.adminedt);
         button = (Button) findViewById(R.id.adminbtn) ;

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String edittest = editText.getText().toString();
                 if(edittest.contains("12345678")){
                     Intent intent = new Intent(SignActivity.this, MainActivity.class);
                     intent.putExtra("spinnerdecision", "admin");

                     startActivity(intent);
                     finish();
                 }

             }
         });
        profileDatabase = FirebaseFirestore.getInstance();


        int selectedItem = (int) spinner.getSelectedItemPosition();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        mEmailFieldla.setVisibility(View.VISIBLE);
                        mPasswordFieldla.setVisibility(View.VISIBLE);
                        register.setVisibility(View.VISIBLE);
                        signin.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);
                        button.setVisibility(View.GONE);


                    break;
                    case 1:

                        mEmailFieldla.setVisibility(View.GONE);
                        mPasswordFieldla.setVisibility(View.GONE);
                        register.setVisibility(View.GONE);
                        signin.setVisibility(View.GONE);
                        editText.setVisibility(View.VISIBLE);
                        button.setVisibility(View.VISIBLE);
                        break;
                }
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.create_new_account) {
            startActivity(new Intent(SignActivity.this, RegisterActivity.class));

        } else if (i == R.id.signin) {
            if (!validation.isInputEditTextFilled(mEmailField, mEmailFieldla, getString(R.string.error_message_emailnull))||
                    !validation.isInputEditTextFilled(mPasswordField, mPasswordFieldla, getString(R.string.error_message_emailnull))) {
                return;
            }
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);


        progressDialog.setTitle("Log in");
        progressDialog.setMessage("Processing your account,please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // [START sign_in_with_email]
        FirebaseFirestore.getInstance().collection("users").whereEqualTo("regno" , email).whereEqualTo("password", password).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(DocumentSnapshot documentSnapshot :queryDocumentSnapshots){
                    if(documentSnapshot.exists()){
                        Intent intent = new Intent(SignActivity.this, MainActivity.class);
                        intent.putExtra("spinnerdecision", "student");
                        intent.putExtra("regno", mEmailField.getText().toString());
                        startActivity(intent);
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(SignActivity.this, "Please create a new account", Toast.LENGTH_SHORT).show();
                    }

                }
              //  Toast.makeText(SignActivity.this, "Please try again..", Toast.LENGTH_SHORT).show();

            }
        });


    }






}



