package dev.uit.grablove.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import dev.uit.grablove.Constants;
import dev.uit.grablove.MainActivity;
import dev.uit.grablove.Model.User;
import dev.uit.grablove.R;

public class SignInActivity extends AppCompatActivity {
    private MaterialEditText etUserName;
    private MaterialEditText etPassword;

    private String strUserName;
    private String strPassword;

    private Button btnSignIn;

    private FirebaseFirestore db;

    private SharedPreferences pre;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        pre=getSharedPreferences (Constants.REF_NAME,MODE_PRIVATE);

        map();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIsEmpty())
                    SignIn();
            }
        });

    }

    private boolean checkIsEmpty() {
        strUserName = etUserName.getText().toString();
        strPassword = etPassword.getText().toString();
        boolean isEmpty = false;
        if(TextUtils.isEmpty(strUserName)) {
            etUserName.setError("You must enter user name");
            isEmpty = true;
        }
        if(TextUtils.isEmpty(strPassword)) {
            etPassword.setError("You must enter password");
            isEmpty = true;
        }
        return isEmpty;
    }

    private void SignIn() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo(Constants.DB_USER_NAME, strUserName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "UserName khong ton tai!!", Toast.LENGTH_LONG).show();
                            } else {
                                for (DocumentSnapshot document : task.getResult()) {
                                    String password = document.getString(Constants.DB_USER_PASSWORD);
                                    if (strPassword.equals(password)){
                                        progressDialog.dismiss();
                                        if (document.getBoolean(Constants.DB_USER_IS_NEW)){
                                            saveUserNew(document);
                                            Intent main = new Intent(SignInActivity.this, SexActivity.class);
                                            startActivity(main);
                                            finish();
                                            WelcomeActivity.getInstance().finish();
                                        }
                                        else {
                                            saveUser(document);
                                            Intent main = new Intent(SignInActivity.this, MainActivity.class);
                                            startActivity(main);
                                            finish();
                                            WelcomeActivity.getInstance().finish();
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Password khong dung!!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    }
                });
    }

    private void saveUserNew(DocumentSnapshot documentSnapshot) {
        SharedPreferences.Editor edit= pre.edit();
        edit.putString(Constants.USER_KEY, documentSnapshot.getId());
        edit.putString(Constants.USER_NAME, documentSnapshot.getString(Constants.DB_USER_FULL_NAME));
        edit.putBoolean(Constants.IS_LOG_IN, true);
        edit.commit();
    }

    private void saveUser(DocumentSnapshot documentSnapshot) {
        SharedPreferences.Editor edit= pre.edit();
        edit.putString(Constants.USER_KEY, documentSnapshot.getId());
        edit.putString(Constants.USER_NAME, documentSnapshot.getString(Constants.DB_USER_FULL_NAME));
        edit.putString(Constants.USER_SEX, documentSnapshot.getString(Constants.DB_USER_SEX));
        edit.putString(Constants.USER_DOB, documentSnapshot.getString(Constants.DB_USER_DOB));
        edit.putString(Constants.USER_AVATAR, documentSnapshot.getString(Constants.DB_USER_AVATAR));
        edit.putBoolean(Constants.IS_LOG_IN, true);
        edit.commit();
    }

    private void map() {
        etUserName = (MaterialEditText) findViewById(R.id.edtUserNameSignIn);
        etPassword = (MaterialEditText) findViewById(R.id.edtPasswordSignIn);

        btnSignIn = (Button) findViewById(R.id.btnSignInSignIn);
    }
}
