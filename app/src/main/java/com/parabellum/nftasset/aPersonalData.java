package com.parabellum.nftasset;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Element;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class aPersonalData extends AppCompatActivity {

    TextView btnBack;
    TextView btnNext;

    LinearLayout checkIncludeDataFlag;
    ImageView infoCheckIncludeData;
    boolean flagIncludeData = false;

    TextView btnCountryOfBirth;
    TextView btnCountryOfCitizenship;

    TextView iHeight;
    TextView iWeight;

    TextView btnEyeColor;
    TextView btnHairColor;

    enum SearchDataType {
        CountryOfBirth,
        CountryOfCitizenship,
        EyeColor,
        HairColor
    }

    int SifraCarinarnice = -1;

    SearchDataType selectedSearchType = SearchDataType.CountryOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_personal_data);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("   NFT Asset");
        actionBar.setSubtitle("   Personal data");

        BLQWaitForm.Instance().SetContext(this, this);

        btnBack = (TextView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                aPersonalData.super.onBackPressed();

            }
        });

        btnNext = (TextView) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                try {
                    goToNextForm();
                }catch (InterruptedException ee)
                {
                    int x;
                }

            }
        });
        checkIncludeDataFlag= (LinearLayout) findViewById(R.id.checkIncludePersonalDataFlag);
        infoCheckIncludeData= (ImageView) findViewById(R.id.infoCheckIncludePersonalData);
        checkIncludeDataFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            { CheckIncludeDataIsSelected(); }
        });

        btnEyeColor = (TextView) findViewById(R.id.btnEyeColor);

        btnEyeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {btnEyeColor.setError(null); selectedSearchType = SearchDataType.EyeColor; SearchTypeSelection();}
        });

        btnHairColor = (TextView) findViewById(R.id.btnHairColor);

        btnHairColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {btnHairColor.setError(null); selectedSearchType = SearchDataType.HairColor; SearchTypeSelection();}
        });

        btnCountryOfBirth = (TextView) findViewById(R.id.btnCountryOfBirth);

        btnCountryOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {btnCountryOfBirth.setError(null); selectedSearchType = SearchDataType.CountryOfBirth; CountrySelection();}
        });

        btnCountryOfCitizenship = (TextView) findViewById(R.id.btnCountryOfCitizenship);

        btnCountryOfCitizenship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {btnCountryOfCitizenship.setError(null); selectedSearchType = SearchDataType.CountryOfCitizenship; CountrySelection();}
        });


        iHeight = (TextView) findViewById(R.id.txtHeight);

        iWeight = (TextView) findViewById(R.id.txtWeight);

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
    private void SearchTypeSelection()
    {
        BLQStat.Instance().lstSearch = new ArrayList<BLQStat.BLQSearch>();

        //Log.e(BLQStat.Instance().DEBUG_TAG, "BR carinskaIspostava: " + BLQStat.Instance().lstCarinskeIspostave.size());

        int size = BLQStat.Instance().lstEyeColor.size();
        if(selectedSearchType == SearchDataType.HairColor)
        {
            size = BLQStat.Instance().lstHairColor.size();
        }
        for (int i = 0; i < size; i++)
        {
            BLQStat.Carinarnica carinarnica;
            if(selectedSearchType == SearchDataType.HairColor)
            {
                carinarnica = BLQStat.Instance().lstHairColor.get(i);
            }
            else
            {
                carinarnica = BLQStat.Instance().lstEyeColor.get(i);
            }
            BLQStat.BLQSearch sr = new BLQStat.BLQSearch();
            sr.ID = carinarnica.Sifra;
            sr.Naziv = "  " + carinarnica.Sifra + "   " + carinarnica.Naziv;
            BLQStat.Instance().lstSearch.add((sr));
        }

       // BLQStat.Carinarnica odabranaCarinarnica = BLQStat.Instance().GetCarinarnicaByID(SifraCarinarnice);

        String predefinisanText = "";
/*
        if(odabranaCarinarnica.Sifra > 0)
        {
            predefinisanText =  odabranaCarinarnica.Naziv;
        }
*/
        Intent intent = new Intent(getApplicationContext(), aBLQSearchList.class);

        intent.putExtra("Natpiss", selectedSearchType == SearchDataType.EyeColor ? "Select eye color" : "Select hair color");
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

            if(selectedSearchType == SearchDataType.EyeColor)
            {
                BLQStat.Carinarnica carinarnica = BLQStat.Instance().GetEyeColorByID(Sifra);
             //   SifraCarinarnice = carinarnica.Sifra;
                btnEyeColor.setText(carinarnica.Naziv);
              //  BLQStat.Instance().objPersonalAsset.EyeColor = carinarnica.Naziv;
            }
            else if(selectedSearchType == SearchDataType.HairColor)
            {
                BLQStat.Carinarnica carinarnica = BLQStat.Instance().GetHairColorByID(Sifra);
             //   SifraCarinarnice = carinarnica.Sifra;
                btnHairColor.setText(carinarnica.Naziv);
              //  BLQStat.Instance().objPersonalAsset.HairColor = carinarnica.Naziv;
            }
            else if(selectedSearchType == SearchDataType.CountryOfBirth || selectedSearchType == SearchDataType.CountryOfCitizenship)
            {
                BLQStat.Carinarnica carinarnica = BLQStat.Instance().GetCarinarnicaByID(Sifra);
                SifraCarinarnice = carinarnica.Sifra;
                if(selectedSearchType == SearchDataType.CountryOfBirth)
                {
                  //  BLQStat.Instance().objPersonalAsset.CountryOfBirth = carinarnica.Naziv;
                    btnCountryOfBirth.setText(carinarnica.Naziv);
                }
                else
                {
                  //  BLQStat.Instance().objPersonalAsset.CountryOfCitizenship = carinarnica.Naziv;
                    btnCountryOfCitizenship.setText(carinarnica.Naziv);
                }
            }

        }

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

    private void UI_Element_ResetErrors()
    {
        // Reset errors.
        btnCountryOfBirth.setError(null);
        btnCountryOfCitizenship.setError(null);
        iHeight.setError(null);
        iWeight.setError(null);
        btnEyeColor.setError(null);
        btnHairColor.setError(null);
    }

    // Check entered data and save.
    private boolean SaveDataSet()
    {

        // Reset errors.
        UI_Element_ResetErrors();

        if(flagIncludeData)
        {
            String countryofbirth = btnCountryOfBirth.getText().toString();
            if (TextUtils.isEmpty(countryofbirth) || btnCountryOfBirth.getHint().equals(countryofbirth))
            {
                btnCountryOfBirth.setError(getString(R.string.required_field));
                btnCountryOfBirth.requestFocus();
                return false;
            }

            String countryofcitizenship = btnCountryOfCitizenship.getText().toString();
            if (TextUtils.isEmpty(countryofcitizenship) || btnCountryOfCitizenship.getHint().equals(countryofcitizenship))
            {
                btnCountryOfCitizenship.setError(getString(R.string.required_field));
                btnCountryOfCitizenship.requestFocus();
                return false;
            }

            String height = iHeight.getText().toString().trim();
            if (TextUtils.isEmpty(height))
            {
                iHeight.setError(getString(R.string.required_field));
                iHeight.requestFocus();
                return false;
            }
            else if(!height.matches(".*[0-9].*"))
            {
                iHeight.setError(getString(R.string.not_valid_height));
                iHeight.requestFocus();
                return false;
            }

            String weight = iWeight.getText().toString().trim();
            if (TextUtils.isEmpty(weight))
            {
                iWeight.setError(getString(R.string.required_field));
                iWeight.requestFocus();
                return false;
            }
            else if(!weight.matches(".*[0-9].*"))
            {
                iWeight.setError(getString(R.string.not_valid_weight));
                iWeight.requestFocus();
                return false;
            }

            String eyecolor = btnEyeColor.getText().toString();
            if (TextUtils.isEmpty(eyecolor) || btnEyeColor.getHint().equals(eyecolor))
            {
                btnEyeColor.setError(getString(R.string.required_field));
                btnEyeColor.requestFocus();
                return false;
            }

            String haircolor = btnHairColor.getText().toString();
            if (TextUtils.isEmpty(eyecolor) || btnHairColor.getHint().equals(haircolor))
            {
                btnHairColor.setError(getString(R.string.required_field));
                btnHairColor.requestFocus();
                return false;
            }

            BLQStat.Instance().objPersonalAsset.CountryOfBirth = countryofbirth;

            BLQStat.Instance().objPersonalAsset.CountryOfCitizenship = countryofcitizenship;

            BLQStat.Instance().objPersonalAsset.Height = height;

            BLQStat.Instance().objPersonalAsset.Weight = weight;

            BLQStat.Instance().objPersonalAsset.EyeColor = eyecolor;

            BLQStat.Instance().objPersonalAsset.HairColor = haircolor;
        }


        BLQStat.Instance().objPersonalAsset.IncludePersonalData = flagIncludeData;

        return true;
    }


    /**
     * go to next form with Document Upload.
     */
    private void goToNextForm() throws InterruptedException
    {

        if(!SaveDataSet())
        {
            return;
        }


        Intent intent = new Intent(getApplicationContext(), aDocumentData.class);
        startActivity(intent);
    }


}