package com.example.simpleappwithdatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simpleappwithdatabase.databinding.ActivityMainBinding;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bi;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);

        myDb = new DatabaseHelper(this);
        AddData();
        viewAll();
        updateData();
        deleteData();
    }

    /*Add Data*/
    public void AddData() {
        bi.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertData(
                        bi.name.getText().toString(),
                        bi.contact.getText().toString(),
                        bi.email.getText().toString());

                if (bi.name.getText().toString().isEmpty()) {
                    bi.name.setError("Field can't be Empty");
                    bi.name.requestFocus();
                } else {
                    bi.name.setError(null);
                }

                if (isInserted == true) {
                    bi.id.setText(null);
                    bi.contact.setText(null);
                    bi.email.setText(null);
                    bi.name.setText(null);
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*View Data*/
    public void viewAll() {
        bi.viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = myDb.getAllData();
                if (result.getCount() == 0) {
                    // Show Message
                    showMessage("Error", "No Data Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    buffer.append("Id : " + result.getString(0) + "\n");
                    buffer.append("Name : " + result.getString(1) + "\n");
                    buffer.append("Contact : " + result.getString(2) + "\n");
                    buffer.append("Email : " + result.getString(3) + "\n\n\n");
                }
                /*Show All Data*/
                showMessage("Data", buffer.toString());
            }
        });
    }

    /*Update Data*/
    public void updateData() {
        bi.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = myDb.updateData(bi.id.getText().toString(),
                        bi.name.getText().toString(),
                        bi.contact.getText().toString(),
                        bi.email.getText().toString());
                if (isUpdated == true) {
                    bi.id.setText(null);
                    bi.contact.setText(null);
                    bi.email.setText(null);
                    bi.name.setText(null);
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*Delete Data*/
    public void deleteData() {
        bi.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRows = myDb.deleteData(bi.id.getText().toString());
                if (deleteRows > 0) {
                    bi.id.setText(null);
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}