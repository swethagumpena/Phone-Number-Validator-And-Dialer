package edu.uic.swethag.cs478.project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected Button enterPhoneNumberButton;
    protected Button dialButton;
    public String phone_number = "";
    public Boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the main layout (from the res folder)
        setContentView(R.layout.activity_main);

        // Bind the interface elements to the corresponding fields
        enterPhoneNumberButton = findViewById(R.id.enterPhoneNumber);
        dialButton = findViewById(R.id.dial);

        // Set up listeners for the button. Actual listeners are created below as instances anonymous classes
        enterPhoneNumberButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, PhoneNumberActivity.class);
            startActivityForResult(i, 1);
        });
        dialButton.setOnClickListener(v -> {
            if(phone_number.equals("")){
                Toast.makeText(getApplicationContext(), "Please enter a phone number", Toast.LENGTH_LONG).show();
            }
            else if(isValid){
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ phone_number));
                startActivity(intent);
            }
            else if(!isValid){
                Toast.makeText(getApplicationContext(), "The number " + phone_number + " you entered is incorrect", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            isValid = false;
        } else if(resultCode == RESULT_OK){
            isValid = true;
        }
        if(data.hasExtra("phone_number"))
            phone_number = data.getExtras().getString("phone_number");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("phone_num",phone_number);
        outState.putBoolean("isValid",isValid);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        phone_number = savedInstanceState.getString("phone_num","");
        isValid = savedInstanceState.getBoolean("isValid",false);
    }
}