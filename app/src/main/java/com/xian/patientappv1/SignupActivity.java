package com.xian.patientappv1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name) EditText etName;
    @Bind(R.id.input_email) EditText etEmail;
    @Bind(R.id.input_password) EditText etPassword;
    @Bind(R.id.input_idno) EditText etMedicalNo;
    @Bind(R.id.btn_signup) Button btnSignin;
    @Bind(R.id.link_login) TextView linkLoginin;

    Context context;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        context = this;

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        linkLoginin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnSignin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = etName.getText().toString();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String medicalNo = etMedicalNo.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        registerUser(email, password, name, medicalNo);
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void registerUser(final String email, final String password, final String name, final String medicalNo)
    {
        final Firebase ref = new Firebase("https://patient-app.firebaseio.com");

        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println(email + " " + password);

                ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                        Firebase userref = ref.child("users");
                        userref.child(authData.getUid()).child("profile").child("name").setValue(name);
                        if(!medicalNo.isEmpty())
                            userref.child(authData.getUid()).child("profile").child("medicalno").setValue(medicalNo);

                        SaveSharedPreference.setUserUid(context, authData.getUid());
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Log.i("test", "error");
                    }
                });

            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
            }
        });
    }



    public void onSignupSuccess() {
        btnSignin.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();

        btnSignin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            etName.setError("at least 3 characters");
            valid = false;
        } else {
            etName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("enter a valid email address");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etPassword.setError("between 4 and 10 alphanumeric characters");

            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }
}