package dev.uit.grablove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import dev.uit.grablove.MainActivity;
import dev.uit.grablove.Model.User;
import dev.uit.grablove.R;

public class SignInActivity extends AppCompatActivity {
    private MaterialEditText etUserName;
    private MaterialEditText etPassword;

    private String strUserName;
    private String strPassword;

    private Button btnSignIn;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        map();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });

    }

    private void SignIn() {
        strUserName = etUserName.getText().toString();
        strPassword = etPassword.getText().toString();

        db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("username", strUserName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                Toast.makeText(getApplicationContext(), "UserName khong ton tai!!", Toast.LENGTH_LONG).show();
                            } else {
                                for (DocumentSnapshot document : task.getResult()) {
                                    String password = document.getString("password");
                                    if (strPassword.equals(password)){
                                        Intent main = new Intent(SignInActivity.this,MainActivity.class);
                                        startActivity(main);
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

    private void map() {
        etUserName = (MaterialEditText) findViewById(R.id.edtUserNameSignIn);
        etPassword = (MaterialEditText) findViewById(R.id.edtPasswordSignIn);

        btnSignIn = (Button) findViewById(R.id.btnSignInSignIn);
    }
}
