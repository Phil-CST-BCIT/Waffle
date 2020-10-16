package com.bcit.comp3717.waffle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextUserName;
    EditText editTextPhoneNumber;
    Button buttonAddCustomer;

    DatabaseReference databaseCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseCustomer = FirebaseDatabase.getInstance().getReference("customers");

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonAddCustomer = findViewById(R.id.buttonAddCustomer);

        buttonAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomer();
            }
        });
    }

    private void addCustomer() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String userName = editTextUserName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();


        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "You must enter a first name.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "You must enter a last name.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "You must enter a user name.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "You must enter a phone number.", Toast.LENGTH_LONG).show();
            return;
        }

        // ask firebase to return a key
        String id = databaseCustomer.push().getKey();
        Customer cst = new Customer(id, firstName, lastName, userName, phoneNumber);

        Task setValueTask = databaseCustomer.child(id).setValue(cst);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,"customer added.",Toast.LENGTH_LONG).show();

                editTextFirstName.setText("");
                editTextLastName.setText("");
                editTextUserName.setText("");
                editTextPhoneNumber.setText("");

            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}