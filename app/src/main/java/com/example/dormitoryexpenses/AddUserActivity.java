package com.example.dormitoryexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUserActivity extends AppCompatActivity {

    DataBaseHelper dbh;
    private Button btnAdd;
    private EditText tfUsername, tfName, tfSurname,tfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        tfUsername = findViewById(R.id.tfUsername);
        tfName = findViewById(R.id.tfShowDate);
        tfSurname = findViewById(R.id.tfSurname);
        tfPassword = findViewById(R.id.tfPassword);
        btnAdd = findViewById(R.id.btnAdd);
        dbh = new DataBaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = tfUsername.getText().toString();
                String name = tfName.getText().toString();
                String sname = tfSurname.getText().toString();
                String password = tfPassword.getText().toString();
                if (tfUsername.length() !=0 && tfName.length() !=0 && tfSurname.length() !=0 && tfPassword.length() !=0){
                    LoginHelper.addNewUser(uname, name, sname, password, dbh, AddUserActivity.this);
                    tfUsername.setText("");
                    tfName.setText("");
                    tfSurname.setText("");
                    tfPassword.setText("");
                    Intent intent = new Intent(AddUserActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Text field empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
