package dev.uit.grablove;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {
    Button btnSignIn;
    EditText edtPhone,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
/*
        edtPhone= (MaterialEditText)findViewById(R.id.edtPhoneNumber);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);

        btnSignIn= (Button)findViewById(R.id.btnSignIn);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/UVFLHLST.ttf");
        edtPhone.setTypeface(face);
        edtPassword.setTypeface(face);
        //Init FireBase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                //region *Check null or empty
                if (edtPhone.getText().toString() == null || edtPhone.getText().toString().length() ==0)
                {
                    Toast.makeText(SignInActivity.this, "Please insert your Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtPassword.getText().toString() == null || edtPassword.getText().toString().length() ==0)
                {
                    Toast.makeText(SignInActivity.this, "Please insert your Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            //Get user info
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Toast.makeText(SignInActivity.this, "Sign In Successfully!!!", Toast.LENGTH_SHORT).show();
                                Intent Birthday = new Intent(SignInActivity.this,BirthdayActivity.class);
                                startActivity(Birthday);
                            } else {
                                Toast.makeText(SignInActivity.this, "Wrong Password!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignInActivity.this, "User not Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        */
    }
}
