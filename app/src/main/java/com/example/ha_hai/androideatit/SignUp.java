package com.example.ha_hai.androideatit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ha_hai.androideatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUp extends AppCompatActivity {

    @BindView(R.id.edtPhone)
    EditText edtPhone;

    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    FirebaseDatabase database;
    DatabaseReference table_use;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        table_use = database.getReference("User");
    }

    @OnClick(R.id.btnSignUp)
    public void onButtonSignUpClick(View view) {

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();

        table_use.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //check if already
                if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                    mDialog.dismiss();
                    Toast.makeText(SignUp.this, "Phone number already register", Toast.LENGTH_SHORT).show();
                } else {
                    mDialog.dismiss();
                    User user = new User(edtName.getText().toString(), edtPassword.getText().toString());
                    table_use.child(edtPhone.getText().toString()).setValue(user);
                    Toast.makeText(SignUp.this, "Sign up successfully!!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
