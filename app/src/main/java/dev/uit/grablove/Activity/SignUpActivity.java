package dev.uit.grablove.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import dev.uit.grablove.R;

public class SignUpActivity extends AppCompatActivity {
    private MaterialEditText etUserName;
    private MaterialEditText etPassword;
    private MaterialEditText etFullName;
    private Button btnSignUp;

    private String strUsername;
    private String strPassword;
    private String strFullName;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        map();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retriveData();
            }
        });

    }

    private void map() {
        etUserName = (MaterialEditText) findViewById(R.id.edtUserNameSignUp);
        etPassword = (MaterialEditText) findViewById(R.id.edtPasswordSignUp);
        etFullName = (MaterialEditText) findViewById(R.id.edtNameSignUp);

        btnSignUp = (Button) findViewById(R.id.btnSignUpSignUp);
    }

    private void retriveData() {
        strUsername = etUserName.getText().toString();
        strPassword = etPassword.getText().toString();
        strFullName = etFullName.getText().toString();

        checkUserName(strUsername);
    }

    private void checkUserName(String strUserName) {
        db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereEqualTo("username", strUserName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                addDataToFirestore();
                            } else {
                                Toast.makeText(getApplicationContext(), "UserName has been used!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void addDataToFirestore() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", strUsername);
        user.put("password", strPassword);
        user.put("fullname", strFullName);
        user.put("isnew", true);

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("wdadwd", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "SignUp thanh cong", Toast.LENGTH_LONG).show();
                        Intent Avatar = new Intent(SignUpActivity.this,AvatarActivity.class);
                        startActivity(Avatar);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("dasddw", "Error adding document", e);
                        Toast.makeText(getApplicationContext(), "SignUp that bai", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
