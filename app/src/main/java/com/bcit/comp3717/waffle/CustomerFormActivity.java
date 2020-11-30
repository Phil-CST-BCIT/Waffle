package com.bcit.comp3717.waffle;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CustomerFormActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    String arriveTime;
    EditText editTextName;
    EditText editTextPhoneNumber;
    TextView tvRestaurant;
    String restaurantName;
    Button buttonAddCustomer;
    DatabaseReference databaseCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_form);

        Button btnTimePicker = findViewById(R.id.btnTimePicker);

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time_picker");
            }
        });

        tvRestaurant = findViewById(R.id.textViewRestaurant);

        Intent intent = getIntent();
        restaurantName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        tvRestaurant.setText(restaurantName);


        databaseCustomer = FirebaseDatabase.getInstance().getReference("customers");

        editTextName = findViewById(R.id.editTextTextCusName);
        editTextPhoneNumber = findViewById(R.id.editTextPhone);
        buttonAddCustomer = findViewById(R.id.btnSubForm);

        buttonAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomer();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + " : " + minute;
        Log.d("=============time===========", time);
        arriveTime = time;
    }

    private void addCustomer() {
        String customerName = editTextName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(customerName)) {
            Toast.makeText(this, "You must enter a name.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(this, "You must enter a phone number.", Toast.LENGTH_LONG).show();
            return;
        }

        // ask firebase to return a key
        String id = databaseCustomer.push().getKey();
        Customer cst = new Customer(id, customerName, phoneNumber, arriveTime, restaurantName);

        Task setValueTask = databaseCustomer.child(id).setValue(cst);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {

            @Override
            public void onSuccess(Object o) {
                Toast.makeText(CustomerFormActivity.this,"customer added.",Toast.LENGTH_LONG).show();

                editTextName.setText("");
                editTextPhoneNumber.setText("");

            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CustomerFormActivity.this,
                        "something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}


