package com.example.dormitoryexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private DataBaseHelper dbh;

    private Button btnLogin, btnRegister, btnReset;
    private Spinner spnUser;
    private EditText tfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tfPassword = findViewById(R.id.tfPassword);
        spnUser = findViewById(R.id.spnUser);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnReset = findViewById(R.id.btnReset);
        dbh = new DataBaseHelper(this);

        LoggedInUser.setUser(null);

        ArrayList<String> ulist = new ArrayList<>();
        getUserList(ulist);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ulist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUser.setAdapter(dataAdapter);

        spnUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (tfPassword.getText().toString().trim().length() != 0){
                    btnLogin.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btnLogin.setEnabled(false);
            }
        });

        tfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length() == 0 ){
                    btnLogin.setEnabled(false);
                } else if (spnUser.getSelectedItem()!=null){
                    btnLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = spnUser.getSelectedItem().toString();
                String password = tfPassword.getText().toString();
                boolean res = LoginHelper.validatePassword(uname, password, dbh);
                if (res){
                    LoggedInUser.setUser(uname);
                    Intent intent = new Intent(LoginActivity.this, ListDataActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbh.dropDB();
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getUserList(ArrayList<String> ulist) {
        Cursor data = dbh.getUsers();
        while(data.moveToNext()) {
            ulist.add(data.getString(0));
        }
    }
}
