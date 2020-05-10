package com.example.dormitoryexpenses;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseActivity extends AppCompatActivity {

    DataBaseHelper dbh;

    private TextView tfShowExpName, tfShowExpDesc, tfShowExpUname, tfShowExpFname, tfShowExpSname, tfShowExpDate, tfShowExpMoney;
    private Button btnExpDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        tfShowExpName = findViewById(R.id.tfShowExpName);
        tfShowExpDesc = findViewById(R.id.tfShowExpDesc);
        tfShowExpUname = findViewById(R.id.tfShowExpUname);
        tfShowExpFname = findViewById(R.id.tfShowExpFname);
        tfShowExpSname = findViewById(R.id.tfShowExpSname);
        tfShowExpDate = findViewById(R.id.tfShowExpDate);
        tfShowExpMoney = findViewById(R.id.tfShowExpMoney);
        btnExpDel = findViewById(R.id.btnExpDel);
        dbh = new DataBaseHelper(this);

        final int id = Integer.parseInt(getIntent().getStringExtra("id"));

        Cursor data = dbh.getExpense(id);
        data.moveToFirst();
        String expName = data.getString(1);
        String expDesc = data.getString(2);
        String expDate = data.getString(3);
        final String expUname = data.getString(4);
        String expMoney = data.getString(5);
        data = dbh.getUser(expUname);
        data.moveToFirst();
        String expFname = data.getString(1);
        String expSname = data.getString(2);

        String fExpMoney = expMoney.substring(0,expMoney.length()-2)+","+expMoney.substring(expMoney.length()-2, expMoney.length()) + "zł";

        tfShowExpName.setText(expName);
        tfShowExpDesc.setText(expDesc);
        tfShowExpUname.setText(expUname);
        tfShowExpFname.setText(expFname);
        tfShowExpSname.setText(expSname);
        tfShowExpDate.setText(expDate);
        tfShowExpMoney.setText(fExpMoney);

        if(expUname.compareTo(LoggedInUser.getUser()) == 0){
            btnExpDel.setEnabled(true);
        }

        btnExpDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                deleteExpense(id);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(ExpenseActivity.this);
                builder.setMessage("Delete this expense?").setPositiveButton("Confirm", dialogClickListener).setNegativeButton("Cancel", dialogClickListener).show();
            }
        });
    }

    private void deleteExpense(int id) {
        boolean res = dbh.deleteExpense(id);
        if (res) {
            Toast.makeText(ExpenseActivity.this, "Deleted expense", Toast.LENGTH_SHORT).show();
            ExpenseActivity.this.finish();
        }
        else
            Toast.makeText(ExpenseActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
}
