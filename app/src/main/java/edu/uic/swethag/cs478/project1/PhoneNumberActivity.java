package edu.uic.swethag.cs478.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.util.regex.*;

public class PhoneNumberActivity extends AppCompatActivity {
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        EditText editText = findViewById(R.id.phoneNumber);

        editText.setOnEditorActionListener(new OnEditorActionListener() {
            public  boolean isValidMobileNo(String str)
            {
                Pattern pattern = Pattern.compile("^(\\([0-9]{3}\\)\\s?[0-9]{3}-|[0-9]{6})[0-9]{4}$");
                // regex to check if phoneNumber is in the format 3125551212 or (312) 555-1212 or (312)555-1212
                Matcher match = pattern.matcher(str);
                return (match.find() && match.group().equals(str));
            }
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean isValid;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    phoneNumber = editText.getText().toString().trim();
                    isValid = isValidMobileNo(phoneNumber);

                    Intent data = new Intent();
                    if(!isValid){
                        data.putExtra("phone_number",phoneNumber);
                        setResult(RESULT_CANCELED,data);
                        finish();
                    }
                    data.putExtra("phone_number",phoneNumber);
                    setResult(RESULT_OK,data);
                    finish();
                }
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("phone_num",phoneNumber);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        phoneNumber = savedInstanceState.getString("phone_num","");
    }
}