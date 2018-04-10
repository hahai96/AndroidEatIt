package com.example.ha_hai.androideatit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ha_hai.androideatit.Common.Common;
import com.example.ha_hai.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignIn extends AppCompatActivity {

    @BindView(R.id.edtPhone)
    EditText edtPhone;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.btnSignInActivity)
    Button btnSignInActivity;

    FirebaseDatabase database;
    DatabaseReference table_use;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        //init firebase
        database = FirebaseDatabase.getInstance();
        table_use = database.getReference("User");
    }

    @OnClick(R.id.btnSignInActivity)
    public void onButtonSignInClick(View view) {

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();

        table_use.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //check if user not exist in database
                if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                    //get user information
                    mDialog.dismiss();
                    User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                    user.setPhone(edtPhone.getText().toString());
                    if (user.getPassword().equals(edtPassword.getText().toString())) {
                        Intent homeIntent = new Intent(SignIn.this, Home.class);
                        Common.currentUser = user;
                        startActivity(homeIntent);
                        finish();
                    } else {
                        Toast.makeText(SignIn.this, "Wrong password !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this, "User not exits in Database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
