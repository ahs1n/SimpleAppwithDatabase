package com.example.simpleappwithdatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText name, contact, email, id;
    Button addData, update, viewData, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        name = (EditText) findViewById(R.id.name);
        contact = (EditText) findViewById(R.id.contact);
        email = (EditText) findViewById(R.id.email);
        id = (EditText) findViewById(R.id.id);
        addData = (Button) findViewById(R.id.addData);
        update = (Button) findViewById(R.id.update);
        viewData = (Button) findViewById(R.id.viewData);
        delete = (Button) findViewById(R.id.delete);
        AddData();
        viewAll();
        updateData();
        deleteData();
    }

    /*Add Data*/
    public void AddData() {
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertData(name.getText().toString(),
                        contact.getText().toString(),
                        email.getText().toString());
                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*View Data*/
    public void viewAll() {
        viewData.setOnClickListener(new View.OnClickListener() {
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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = myDb.updateData(id.getText().toString(),
                        name.getText().toString(),
                        contact.getText().toString(),
                        email.getText().toString());
                if (isUpdated == true)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
            }
        });
    }

    /*Delete Data*/
    public void deleteData() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleteRows = myDb.deleteData(id.getText().toString());
                if (deleteRows > 0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                else
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