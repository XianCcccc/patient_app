package com.xian.patientappv1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.ButterKnife;
import butterknife.Bind;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    Context context = this;

    @Bind(R.id.input_email) EditText etEmail;
    @Bind(R.id.input_password) EditText etPassword;
    @Bind(R.id.btn_login) Button btLogin;
    @Bind(R.id.link_signup) TextView linkSignup;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if(savedInstanceState == null)
            Firebase.setAndroidContext(this);

        Firebase myFirebaseRef = new Firebase("https://patient-app.firebaseapp.com/");

        btLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        linkSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLogin(email, password);
                        progressDialog.dismiss();

                    }
                }, 3000);
    }


    public void onLogin(String email, String password)
    {
        final Firebase ref = new Firebase("https://patient-app.firebaseio.com");
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.print(authData.getUid());
                SaveSharedPreference.setUserUid(context, authData.getUid());
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                Log.i("test", "error");
                btLogin.setEnabled(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Drawable icon = (Drawable)getResources().getDrawable(R.mipmap.ic_error_outline_white_18dp);
            etEmail.setError("enter a valid email address",icon);
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 30) {
            etPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }
}
