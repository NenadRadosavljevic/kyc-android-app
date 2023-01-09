package com.parabellum.nftasset;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Context context = this;

    EditText iSelectDate;
    private int mYear, mMonth, mDay;

  //  TextView btnBack; // not used
    TextView btnNext;

    TextView iFirstName;
    TextView iMiddleName;
    TextView iLastName;
    TextView iEmail;
    TextView iMobileNumber;
    TextView iTemplateName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("   NFT Asset");
        actionBar.setSubtitle("   General data");

  //      BLQStat.Instance().InicializujBLQStat(this);

        btnNext = (TextView) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                goToNextForm();
            }
        });


        iSelectDate=(EditText)findViewById(R.id.date_of_birth);


        iSelectDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (view == iSelectDate)
                {
                    iSelectDate.setError(null);

                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    /* Theme_Holo_Dialog  R.style.MySpinnerDatePickerStyle*/
                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    String dateStringFormat = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    BLQStat.Instance().objPersonalAsset.DateOfBirth = dateStringFormat;
                                    iSelectDate.setText(dateStringFormat);

                                }
                            }, mYear, mMonth, mDay);

                    datePickerDialog.show();
                }

            }
        });


        iFirstName = (TextView) findViewById(R.id.txtFirstName);

        iMiddleName = (TextView) findViewById(R.id.txtMiddleName);

        iLastName = (TextView) findViewById(R.id.txtLastName);

        iEmail = (TextView) findViewById(R.id.txtEmail);

        iMobileNumber = (TextView) findViewById(R.id.txtMobileNumber);

        iTemplateName = (TextView) findViewById(R.id.txtTemplateName);

    }

    private static boolean isValidEmail(CharSequence target)
    {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

/*
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    if(iEmail.getText().toString().isEmpty())
    {
        "enter email address"
    }
    else
    {
        if (iEmail.getText().toString().trim().matches(emailPattern))
        {
            "valid email address"
        }
        else
        {
            "Invalid email address"
        }
    }
*/

    // Check entered data and save.
    private boolean SaveDataSet()
    {

        // Reset errors.
        iFirstName.setError(null);
        iMiddleName.setError(null);
        iLastName.setError(null);
        iSelectDate.setError(null);
        iEmail.setError(null);
        iMobileNumber.setError(null);


        String firstname = iFirstName.getText().toString().trim();
        if(TextUtils.isEmpty(firstname))
        {
            iFirstName.setError(getString(R.string.required_field));
            iFirstName.requestFocus();
            return false;
        }
        String middlename = iMiddleName.getText().toString();
/*
        if(TextUtils.isEmpty(middlename))
        {
            iMiddleName.setError(getString(R.string.required_field));
            iMiddleName.requestFocus();
            return false;
        }
*/
        String lastname = iLastName.getText().toString().trim();
        if(TextUtils.isEmpty(lastname))
        {
            iLastName.setError(getString(R.string.required_field));
            iLastName.requestFocus();
            return false;
        }

        if(TextUtils.isEmpty(iSelectDate.getText().toString()))
        {
            iSelectDate.setError(getString(R.string.required_field));
            iSelectDate.requestFocus();
            return false;
        }

        String email = iEmail.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            iEmail.setError(getString(R.string.required_field));
            iEmail.requestFocus();
            return false;
        }
        else if(!isValidEmail(email))
        {
            iEmail.setError(getString(R.string.not_valid_email));
            iEmail.requestFocus();
            return false;
        }
        String mobilenumber = iMobileNumber.getText().toString();
        if(TextUtils.isEmpty(mobilenumber))
        {
            iMobileNumber.setError(getString(R.string.required_field));
            iMobileNumber.requestFocus();
            return false;
        }
        else if(!mobilenumber.matches(".*[0-9].*"))
        {
            iMobileNumber.setError(getString(R.string.not_valid_phone_number));
            iMobileNumber.requestFocus();
            return false;
        }
        String templatename = iTemplateName.getText().toString();



        BLQStat.Instance().objPersonalAsset.FirstName = firstname;
        BLQStat.Instance().objPersonalAsset.MiddleName = middlename;
        BLQStat.Instance().objPersonalAsset.LastName = lastname;
        BLQStat.Instance().objPersonalAsset.Email = email;
        BLQStat.Instance().objPersonalAsset.MobileNumber = mobilenumber;
        BLQStat.Instance().objPersonalAsset.TemplateName = templatename;

        return true;
    }

    /**
     * go to next form with address data line.
     */
    private void goToNextForm()
    {
        if(!SaveDataSet())
        {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), aAddressData.class);
        startActivity(intent);
      //  finish();
    }

}