package dev.uit.grablove;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import dev.uit.grablove.Model.User;

public class SignUp extends AppCompatActivity {
    MaterialEditText edtPhone,edtName,edtPassword;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtPhone = (MaterialEditText)findViewById(R.id.edtPhoneNumber);
        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtPassword= (MaterialEditText) findViewById(R.id.edtPassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region *Check Null or empty
                if (edtPhone.getText().toString() == null  || edtPhone.getText().toString().length() == 0 )
                {
                    Toast.makeText(SignUp.this, "Please insert your number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtPassword.getText().toString() == null|| edtPassword.getText().toString().length() == 0)
                {
                    Toast.makeText(SignUp.this, "Please insert your Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtName.getText().toString() == null|| edtName.getText().toString().length() == 0)
                {
                    Toast.makeText(SignUp.this, "Please insert your Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if(dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone Number already register", Toast.LENGTH_SHORT).show();

                        }
                        else    {
                            mDialog.show();
                            User user = new User(edtName.getText().toString(),edtPassword.getText().toString());

                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign Up Successfully!!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
