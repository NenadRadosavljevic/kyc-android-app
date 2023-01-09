package com.parabellum.nftasset;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.Nullable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.passbase.passbase_sdk.PassbaseButton;
import com.passbase.passbase_sdk.PassbaseSDK;
import com.passbase.passbase_sdk.PassbaseSDKListener;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;

import javax.crypto.Cipher;


public class aDocumentData extends AppCompatActivity {

    TextView btnBack;
    TextView btnExit;

    TextView btnDocumentType;


    enum SearchDataType {
        DocumentType
    }

    int SifraCarinarnice = -1;

    SearchDataType selectedSearchType = SearchDataType.DocumentType;


    PassbaseSDK passbaseRef;
    PassbaseButton verificationButton;

    // hardcoded private key in PEM format. Base64 encoded.
    String privateKeyPEM = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "Put here your Passbase private key!\n" +
            "-----END RSA PRIVATE KEY-----\n";

    final static String LogcatTAG = "NenadR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_document_data);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("   NFT Asset");
        actionBar.setSubtitle("   Document supporting form");

        btnBack = (TextView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                aDocumentData.super.onBackPressed();

            }
        });

        btnExit = (TextView) findViewById(R.id.btnNext);

        btnExit.setOnClickListener(new View.OnClickListener() {
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

        btnDocumentType = (TextView) findViewById(R.id.btnDocumentType);

        btnDocumentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            { SearchTypeSelection();}
        });


        // 2. Initialize object here with own API key
        passbaseRef = new PassbaseSDK(this);
        passbaseRef.initialize("Put here your Passbase API key!");
        if(!TextUtils.isEmpty(BLQStat.Instance().objPersonalAsset.Email))
        {
            passbaseRef.setPrefillUserEmail(BLQStat.Instance().objPersonalAsset.Email);
        }
        String metadata = doMetadataEncrypt();
        if(!TextUtils.isEmpty(metadata))
        {
            passbaseRef.setMetaData(metadata);
        }

        // Handling verifications via callbacks
        passbaseRef.callback(new PassbaseSDKListener() {
            @Override
            public void onStart() {
                String resp = "ID verification onStart";
                System.out.println(resp);
                showMessage(resp);
            }

            @Override
            public void onFinish(@Nullable String identityAccessKey) {
                String resp = "ID verification onFinish: " + identityAccessKey;
                System.out.println(resp);
                showMessage(resp);

            }

            @Override
            public void onSubmitted(@Nullable String identityAccessKey) {

                String resp = "ID verification onSubmitted: " + identityAccessKey;
                System.out.println(resp);
                showMessage(resp);
            }

            @Override
            public void onError(@NotNull String errorCode) {
                String resp = "ID verification onError: " + errorCode;
                System.out.println(resp);
                showMessage(resp);
            }
        });

        verificationButton = this.findViewById(R.id.passbaseVerificationButton);

        verificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passbaseRef.startVerification();
            }
        });

    }

    private String doMetadataEncrypt()
    {
        String metadataBase64 = "";

        try
        {

            privateKeyPEM = privateKeyPEM.replace("-----BEGIN RSA PRIVATE KEY-----\n", "");
            privateKeyPEM = privateKeyPEM.replace("-----END RSA PRIVATE KEY-----\n", "");

            // base 64 enkodiranje MIME (RFC 2045) !!!
            // Mora ova decoding scheme !!!
            byte[] decoded = Base64.getMimeDecoder().decode(privateKeyPEM);

            // decoded is the private key in a byte array
            PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(decoded);
            Log.i(LogcatTAG, "Priv key loaded");

            KeyFactory kf = KeyFactory.getInstance("RSA"); //, "AndroidOpenSSL");

            PrivateKey priv = kf.generatePrivate(pkcs8);
            Log.i(LogcatTAG, "Priv key format: " + priv.getFormat());
            Log.i(LogcatTAG, "Priv key algorithm: " + priv.getAlgorithm());
            Log.i(LogcatTAG, "Priv key encoded len: " + priv.getEncoded().length);
            Log.i(LogcatTAG, "decoded len: " + decoded.length);
            Log.i(LogcatTAG, "pkcs8 len: " + pkcs8.getEncoded().length);

            Gson gson = new Gson();
            String json = gson.toJson(BLQStat.Instance().objPersonalAsset);
            Log.i(LogcatTAG, "metadata len: " + json.length());
            Log.i(LogcatTAG, "metadata content: " + json);

            // RSA encryption with PKCS1 padding! Complete spec is "RSA/ECB/PKCS1Padding".
            byte[] enc_data = RSA_encrypt(json, priv);
            // encrypted byte stream is base64 encoded, with MIME encoding scheme (RFC2045).
            metadataBase64 =  Base64.getMimeEncoder().encodeToString(enc_data);
            //  metadataBase64 =  Base64.getEncoder().encodeToString(enc_data);
            Log.i(LogcatTAG, "metadata encrypted len: " + metadataBase64.length());
            Log.i(LogcatTAG, "metadata encrypted content base64: " + metadataBase64);
        }
        catch(Exception ee)
        {
            Log.d(LogcatTAG, "Exception Metadata: " + ee. getMessage());
        }
        return metadataBase64;
    }
    private static byte[] RSA_encrypt(String Buffer, PrivateKey priv) {
        try {

            Cipher rsa;
            // RSA/ECB/PKCS1Padding
            rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");  // RSA   RSA/ECB/OAEPWithSHA-1AndMGF1Padding  RSA/ECB/OAEPWithSHA-256AndMGF1Padding
            rsa.init(Cipher.ENCRYPT_MODE, priv);
            return rsa.doFinal(Buffer.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LogcatTAG, "Exception RSA encrypt: " + e. getMessage());
        }
        return null;
    }

    private void showMessage(String p) {

        Toast.makeText(this, p, Toast.LENGTH_SHORT).show();
    }

    private void SearchTypeSelection()
    {
        BLQStat.Instance().lstSearch = new ArrayList<BLQStat.BLQSearch>();


        int size = BLQStat.Instance().lstDocumentType.size();

        for (int i = 0; i < size; i++)
        {
            BLQStat.Carinarnica carinarnica = BLQStat.Instance().lstDocumentType.get(i);

            BLQStat.BLQSearch sr = new BLQStat.BLQSearch();
            sr.ID = carinarnica.Sifra;
            sr.Naziv = "  " + carinarnica.Sifra + "   " + carinarnica.Naziv;
            BLQStat.Instance().lstSearch.add((sr));
        }

        BLQStat.Carinarnica odabranaCarinarnica = BLQStat.Instance().GetDocumentTypeByID(SifraCarinarnice);

        String predefinisanText = "";
/*
        if(odabranaCarinarnica.Sifra > 0)
        {
            predefinisanText = odabranaCarinarnica.Naziv;
        }
*/
        Intent intent = new Intent(getApplicationContext(), aBLQSearchList.class);

        intent.putExtra("Natpiss", selectedSearchType == SearchDataType.DocumentType ? "Required document types" : "");
        intent.putExtra("ImaPotvrdi", false);
        intent.putExtra("PredefinisaniTekst", predefinisanText);


    //    startActivityForResult(intent, BLQStat.ActivityRequest.ODABIR_CARINARNICE_REQUEST);
        startActivity(intent);
    }

    private void proveriIspravnostCarinarnice(final int Sifra)
    {
        SifraCarinarnice = -1;

        if(Sifra < 0)
        {

        }
        else
        {

                BLQStat.Carinarnica carinarnica = BLQStat.Instance().GetDocumentTypeByID(Sifra);

                SifraCarinarnice = carinarnica.Sifra;
                btnDocumentType.setText(carinarnica.Naziv);
             //   BLQStat.Instance().objPersonalAsset.DocumentType = carinarnica.Naziv;

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

    /**
     * go to pop up with JSON object.
     */
    private void goToNextForm() throws InterruptedException {

        TextView txtV = (TextView) findViewById(R.id.btnDocumentType);
     //   BLQStat.Instance().objPersonalAsset.DocumentType = txtV.getText().toString();


        Gson gson = new Gson();
        String json = gson.toJson(BLQStat.Instance().objPersonalAsset);
/*
        JsonObject obj  = gson.toJsonTree(BLQStat.Instance().objPersonalAsset).getAsJsonObject();
        obj.addProperty("MyCustomElement", false);
        String json = obj.toString();
*/
        Log.i(LogcatTAG, json);

        //     Toast.makeText(aPersonalData.this,
        //           json, Toast.LENGTH_LONG).show();
/*
        BLQWaitForm.Instance().ShowBLQInfo("Uploadujem!");
        Thread.sleep(3000);
        BLQWaitForm.Instance().Kraj();
  */
        popupMessage(json);
    }

    public void popupMessage(String json){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(json);
        alertDialogBuilder.setIcon(R.drawable.checkedicon);
        alertDialogBuilder.setTitle("json data");
        alertDialogBuilder.setNegativeButton("ok", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(LogcatTAG,"Ok btn pressed");
                // add these two lines, if you wish to close the app:
                finishAffinity();
                System.exit(0);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
