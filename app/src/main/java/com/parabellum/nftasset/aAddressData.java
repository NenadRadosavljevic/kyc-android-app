package com.parabellum.nftasset;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class aAddressData extends AppCompatActivity {

    TextView btnBack;
    TextView btnNext;

    TextView btnCountry;


    LinearLayout checkIncludeDataFlag;
    ImageView infoCheckIncludeData;
    boolean flagIncludeData = false;

    int SifraCarinarnice = -1;

    enum DataCountryType {
        Country,
        CountryOfBirth,
        CountryOfCitizenship
    }

    DataCountryType selectedSearchType = DataCountryType.Country;

    TextView iAddress1;
    TextView iAddress2;
    TextView iCity;
    TextView iStateProvince;
    TextView iZipPostalCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_address_data);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("   NFT Asset");
        actionBar.setSubtitle("   Address data");

   //     BLQStat.Instance().InicializujBLQStat(this);

        checkIncludeDataFlag= (LinearLayout) findViewById(R.id.checkIncludeDataFlag);
        infoCheckIncludeData= (ImageView) findViewById(R.id.infoCheckIncludeData);
        checkIncludeDataFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            { CheckIncludeDataIsSelected(); }
        });
        btnBack = (TextView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                aAddressData.super.onBackPressed();

            }
        });

        btnNext = (TextView) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                goToNextForm();
            }
        });

        btnCountry = (TextView) findViewById(R.id.btnCountry);

        btnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                btnCountry.setError(null);
                selectedSearchType = DataCountryType.Country;
                CountrySelection();
            }
        });


        iAddress1 = (TextView) findViewById(R.id.txtAddress1);

        iAddress2 = (TextView) findViewById(R.id.txtAddress2);

        iCity = (TextView) findViewById(R.id.txtCity);

        iStateProvince = (TextView) findViewById(R.id.txtStateProvince);

        iZipPostalCode = (TextView) findViewById(R.id.txtZipPostalCode);

    }

    private void CheckIncludeDataIsSelected()
    {
        if(!flagIncludeData)
        {
            infoCheckIncludeData.setImageResource(R.drawable.checkedicon);
            flagIncludeData = true;
        }
        else
        {
            infoCheckIncludeData.setImageResource(R.drawable.uncheckedicon);
            flagIncludeData = false;

            // Reset errors.
            UI_Element_ResetErrors();
        }

    }
    private void CountrySelection()
    {
        BLQStat.Instance().lstSearch = new ArrayList<BLQStat.BLQSearch>();

        //Log.e(BLQStat.Instance().DEBUG_TAG, "BR carinskaIspostava: " + BLQStat.Instance().lstCarinskeIspostave.size());

        for (int i = 0; i < BLQStat.Instance().lstCarinarnice.size(); i++)
        {
            BLQStat.Carinarnica carinarnica = BLQStat.Instance().lstCarinarnice.get(i);
            BLQStat.BLQSearch sr = new BLQStat.BLQSearch();
            sr.ID = carinarnica.Sifra;
            sr.Naziv = "  " + carinarnica.Sifra + "   " + carinarnica.Naziv;
            BLQStat.Instance().lstSearch.add((sr));
        }

        BLQStat.Carinarnica odabranaCarinarnica = BLQStat.Instance().GetCarinarnicaByID(SifraCarinarnice);

        String predefinisanText = "";

        if(odabranaCarinarnica.Sifra > 0)
        {
            predefinisanText = odabranaCarinarnica.Naziv;
        }

        Intent intent = new Intent(getApplicationContext(), aBLQSearchList.class);

        intent.putExtra("Natpiss", "Select a country");
        intent.putExtra("ImaPotvrdi", false);
        intent.putExtra("PredefinisaniTekst", predefinisanText);


        startActivityForResult(intent, BLQStat.ActivityRequest.ODABIR_CARINARNICE_REQUEST);
    }

    private void proveriIspravnostCarinarnice(final int Sifra)
    {
        SifraCarinarnice = -1;

        if(Sifra < 0)
        {

        }
        else
        {
            BLQStat.Carinarnica carinarnica = BLQStat.Instance().GetCarinarnicaByID(Sifra);
            SifraCarinarnice = carinarnica.Sifra;
            if(selectedSearchType == DataCountryType.Country)
            {
            //    BLQStat.Instance().objPersonalAsset.Country = carinarnica.Naziv;
                btnCountry.setText(carinarnica.Naziv);
            }

        }

    }

    private void UI_Element_ResetErrors()
    {
        // Reset errors.
        iAddress1.setError(null);
        iAddress2.setError(null);
        iCity.setError(null);
        iStateProvince.setError(null);
        iZipPostalCode.setError(null);
        btnCountry.setError(null);
    }

    // Check entered data and save.
    private boolean SaveDataSet()
    {

        // Reset errors.
        UI_Element_ResetErrors();

        if(flagIncludeData)
        {
            String address1 = iAddress1.getText().toString().trim();
            if (TextUtils.isEmpty(address1))
            {
                iAddress1.setError(getString(R.string.required_field));
                iAddress1.requestFocus();
                return false;
            }

            String address2 = iAddress2.getText().toString().trim();
/*
        if(TextUtils.isEmpty(address2))
        {
            iAddress2.setError(getString(R.string.required_field));
            iAddress2.requestFocus();
            return false;
        }
*/
            String city = iCity.getText().toString().trim();
            if (TextUtils.isEmpty(city))
            {
                iCity.setError(getString(R.string.required_field));
                iCity.requestFocus();
                return false;
            }

            String stateprovince = iStateProvince.getText().toString().trim();
            if (TextUtils.isEmpty(stateprovince))
            {
                iStateProvince.setError(getString(R.string.required_field));
                iStateProvince.requestFocus();
                return false;
            }

            String zippostalcode = iZipPostalCode.getText().toString().trim();
            if (TextUtils.isEmpty(zippostalcode))
            {
                iZipPostalCode.setError(getString(R.string.required_field));
                iZipPostalCode.requestFocus();
                return false;
            }

            String country = btnCountry.getText().toString();
            if (TextUtils.isEmpty(country) || btnCountry.getHint().equals(country))
            {
                btnCountry.setError(getString(R.string.required_field));
                btnCountry.requestFocus();
                return false;
            }

            BLQStat.Instance().objPersonalAsset.Address1 = address1;
            BLQStat.Instance().objPersonalAsset.Address2 = address2;
            BLQStat.Instance().objPersonalAsset.City = city;
            BLQStat.Instance().objPersonalAsset.StateProvince = stateprovince;
            BLQStat.Instance().objPersonalAsset.ZipPostalCode = zippostalcode;
            BLQStat.Instance().objPersonalAsset.Country = country;
        }


        BLQStat.Instance().objPersonalAsset.IncludeAddressData = flagIncludeData;

        return true;
    }


    /**
     * go to next form with Personal data line.
     */
    private void goToNextForm()
    {
        if(!SaveDataSet())
        {
            return;
        }

        Intent intent = new Intent(getApplicationContext(), aPersonalData.class);

        startActivity(intent);
        //  finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        BLQWaitForm.Instance().SetContext(this, this);

        if (requestCode == BLQStat.ActivityRequest.ODABIR_CARINARNICE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = intent.getExtras();
                int ID = bundle.getInt("IzabranID");

                proveriIspravnostCarinarnice(ID);
            } else {
                return;
            }
        }
    }
}