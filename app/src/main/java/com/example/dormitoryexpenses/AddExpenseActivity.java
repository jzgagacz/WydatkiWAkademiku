package com.example.dormitoryexpenses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {


    private DataBaseHelper dbh;
    private EditText tfExpense, tfDescription, tfZL, tfGR;
    private Button btnExpAdd;
    private Button tfDate;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        tfExpense = findViewById(R.id.tfExpense);
        tfDescription = findViewById(R.id.tfDescription);
        tfDate = findViewById(R.id.tfDate);
        tfZL = findViewById(R.id.tfZL);
        tfGR = findViewById(R.id.tfGR);
        btnExpAdd = findViewById(R.id.btnExpAdd);

        dbh = new DataBaseHelper(this);

        tfDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddExpenseActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth, mDateSetListener, year, month,day);
                dialog.show();
                tfZL.requestFocus();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "-" + month + "-" + year;
                tfDate.setText(date);
            }
        };

        btnExpAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String expanse = tfExpense.getText().toString();
                    String description = tfDescription.getText().toString();
                    String date = tfDate.getText().toString();
                    int zl = Integer.parseInt(tfZL.getText().toString());
                    int gr = Integer.parseInt(tfGR.getText().toString());
                    if (expanse.length() == 0 || description.length()==0||date.length()==0||date.equals("Date")){
                        throw new NumberFormatException();
                    }
                    boolean res = dbh.addExpense(expanse,description,date,LoggedInUser.getUser(),zl,gr);
                    if (res) {
                        Toast.makeText(AddExpenseActivity.this, "Added expense", Toast.LENGTH_SHORT).show();
                        AddExpenseActivity.this.finish();
                    }
                    else
                        Toast.makeText(AddExpenseActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e){
                    Toast.makeText(AddExpenseActivity.this, "Empty field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
