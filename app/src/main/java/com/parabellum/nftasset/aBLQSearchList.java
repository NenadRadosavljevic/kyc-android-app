package com.parabellum.nftasset;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class aBLQSearchList extends AppCompatActivity {


    ListView listView;
    TextView btnNazad;
    TextView btnPotvrdi;
    TextView lblNatpis;

    //ArrayList<BLQStat.BLQSearch> lstSearch;
    String Natpis;
    String PredefinisaniTekst="";

    boolean ImaPotvrdi = false;

    String NovoIme = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_blqsearch_list);

        BLQWaitForm.Instance().SetContext(this, this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setTitle(Naslov);

        Intent intent = getIntent();
        //lstSearch = (ArrayList<BLQStat.BLQSearch>)intent.getSerializableExtra("lstSearch");
        Natpis = intent.getStringExtra("Natpiss");
        ImaPotvrdi = intent.getBooleanExtra("ImaPotvrdi", false);
        PredefinisaniTekst = intent.getStringExtra("PredefinisaniTekst");


        listView = (ListView) findViewById(R.id.listView);
        btnNazad = (TextView) findViewById(R.id.btnNazad);
        lblNatpis = (TextView) findViewById(R.id.lblNatpis);
        btnPotvrdi = (TextView) findViewById(R.id.btnPotvrdi);

        btnNazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                closeForm(RESULT_CANCELED, -1);
            }
        });

        lblNatpis.setText(Natpis);

        if(!ImaPotvrdi)
        {
            btnPotvrdi.setVisibility(View.GONE);
            LinearLayout.LayoutParams loparams = (LinearLayout.LayoutParams) btnNazad.getLayoutParams();

            loparams.weight = (float) 1.0;
            btnNazad.setLayoutParams(loparams);
        }
        else
        {
            btnPotvrdi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0)
                {
                    closeForm(RESULT_OK, -1);
                }
            });
        }

        BLQAdapterViewCustom adapter = new BLQAdapterViewCustom(this, R.layout.list_item, BLQStat.Instance().lstSearch);              //android.R.layout.simple_list_item_1

        listView.setAdapter(adapter);





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                int ID = ((BLQStat.BLQSearch)listView.getItemAtPosition(i)).ID;
                closeForm(RESULT_OK, ID);
                //String text = ((BLQStat.BLQSearch)listView.getItemAtPosition(i)).Naziv;
                //Toast.makeText(aBLQSearchList.this, " " + text, Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setIconified(false);
        searchView.setQueryHint("Enter the text");

        if(PredefinisaniTekst.length() > 0)
        {
            searchView.setQuery(PredefinisaniTekst, false);
        }
        //
        //searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextChange(String newText)
            {
                NovoIme = newText;

                ArrayList<BLQStat.BLQSearch> tempList = new ArrayList<BLQStat.BLQSearch>();

                for(BLQStat.BLQSearch temp : BLQStat.Instance().lstSearch)
                {
                    if(temp.Naziv.toLowerCase().contains(newText.toLowerCase()))
                    {
                        tempList.add(temp);
                    }
                }

                BLQAdapterViewCustom adapter = new BLQAdapterViewCustom(aBLQSearchList.this, R.layout.list_item, tempList);

                listView.setAdapter(adapter);

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    public void closeForm(int RESULT, int ID)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("IzabranID", ID);
        bundle.putString("NovoIme", NovoIme);
        intent.putExtras(bundle);
        setResult(RESULT, intent);
        finish();
    }
}