package com.parabellum.nftasset;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/***************************************************************************************

                                     BLQStat

                                  Static class.
                              Loading necessary data.
                               Application version...


 ***************************************************************************************/

public class BLQStat
{

    Context context;


    private static volatile BLQStat instance;






    public static class ActivityRequest                        // ENUM
    {
        public static final int POSTAVLJANJE_REQUEST = 1;
        public static final int IMAGE_ZADNJA_STRANA_REQUEST = 2;
        public static final int IMAGE_PREDNJA_STRANA_REQUEST = 3;
        public static final int SKIDANJE_REQUEST = 4;
        public static final int SKIDANJE_PREGLED_REQUEST = 5;
        public static final int POSTAVLJANJE_PREGLED_REQUEST = 6;
        public static final int PREGLED_PODATAKA_REQUEST = 7;
        public static final int PREGLED_PODATAKA_PREGLED_REQUEST = 8;
        public static final int LOGIN_REQUEST = 9;
        public static final int UPGRADE_APK_REQUEST = 10;
        public static final int PODESAVANJE_CARINIKA_REQUEST = 11;
        public static final int PROMENA_LOZINKE_REQUEST = 12;
        public static final int PROMENA_CARINSKE_ISPOSTAVE_REQUEST = 13;
        public static final int POPIS_MENU_REQUEST = 14;
        public static final int POPIS_SA_NFC_REQUEST = 15;
        public static final int POPIS_BEZ_NFC_REQUEST = 16;
        public static final int ODABIR_CARINARNICE_REQUEST = 17;

    }


    public static class CarinskaIspostava
    {
        public int Sifra;
        public String Naziv;

        public CarinskaIspostava()
        {
        }

        public CarinskaIspostava(int Sifraa, String Nazivv)
        {
            Sifra = Sifraa;
            Naziv = Nazivv;
        }
    }

    public static class Carinarnica
    {
        public int Sifra;
        public String Naziv;

        public Carinarnica()
        {
        }

        public Carinarnica(int Sifraa, String Nazivv)
        {
            Sifra = Sifraa;
            Naziv = Nazivv;
        }
    }

    public static class BLQSearch implements Serializable
    {
        public int ID;
        public String Naziv;
    }

    public static class PersonalAsset
    {
        // Basic data
        @SerializedName("firstname")
        public String FirstName;

        @SerializedName("middlename")
        public String MiddleName;

        @SerializedName("lastname")
        public String LastName;

        @SerializedName("dob")
        public String DateOfBirth;

        @SerializedName("email")
        public String Email;

        @SerializedName("mobile")
        public String MobileNumber;

      //  @SerializedName("templatename")
       // @Expose(serialize = false)
        public transient String TemplateName;

        // Address data
        @SerializedName("inclusion1")
        public boolean IncludeAddressData;

        @SerializedName("address1")
        public String Address1;

        @SerializedName("address2")
        public String Address2;

        @SerializedName("city")
        public String City;

        @SerializedName("stateprovince")
        public String StateProvince;

        @SerializedName("country")
        public String Country;

        @SerializedName("zippostal")
        public String ZipPostalCode;


        // Personal data
        @SerializedName("inclusion2")
        public boolean IncludePersonalData;

        @SerializedName("countrydob")
        public String CountryOfBirth;

        @SerializedName("countrycitizenship")
        public String CountryOfCitizenship;

        @SerializedName("height")
        public String Height;

        @SerializedName("weight")
        public String Weight;

        @SerializedName("eyecolor")
        public String EyeColor;

        @SerializedName("haircolor")
        public String HairColor;

        public PersonalAsset()
        {
        }

    }


    public PersonalAsset objPersonalAsset;

    List<Carinarnica> lstCarinarnice = new ArrayList<Carinarnica>();

    public ArrayList<BLQSearch> lstSearch;

    List<Carinarnica> lstEyeColor = new ArrayList<Carinarnica>();
    List<Carinarnica> lstHairColor = new ArrayList<Carinarnica>();
    List<Carinarnica> lstDocumentType = new ArrayList<Carinarnica>();

    /**
     * Creating instance of BLQStat class
     */
    public static BLQStat Instance()
    {
        if (instance == null)
        {
            instance = new BLQStat();
        }
        return instance;
    }

    public BLQStat()
    {
        objPersonalAsset = new PersonalAsset();

        LoadEyeColor();
        LoadHairColor();
        LoadDocumentType();
        UcitajCarinarnice();
    }



    private void UcitajCarinarnice()
    {
        lstCarinarnice = new ArrayList<Carinarnica>();

        lstCarinarnice.add(new Carinarnica(1,"Afghanistan"));
        lstCarinarnice.add(new Carinarnica(2,"Albania"));
        lstCarinarnice.add(new Carinarnica(3,"Algeria"));
        lstCarinarnice.add(new Carinarnica(4,"Andorra"));
        lstCarinarnice.add(new Carinarnica(5,"Angola"));
        lstCarinarnice.add(new Carinarnica(6,"Antigua and Barbuda"));
        lstCarinarnice.add(new Carinarnica(7,"Argentina"));
        lstCarinarnice.add(new Carinarnica(8,"Armenia"));
        lstCarinarnice.add(new Carinarnica(9,"Australia"));
        lstCarinarnice.add(new Carinarnica(10,"Austria"));
        lstCarinarnice.add(new Carinarnica(11,"Azerbaijan"));
        lstCarinarnice.add(new Carinarnica(12,"Bahamas (the)"));
        lstCarinarnice.add(new Carinarnica(13,"Bahrain"));
        lstCarinarnice.add(new Carinarnica(14,"Bangladesh"));
        lstCarinarnice.add(new Carinarnica(15,"Barbados"));
        lstCarinarnice.add(new Carinarnica(16,"Belarus"));
        lstCarinarnice.add(new Carinarnica(17,"Belgium"));
        lstCarinarnice.add(new Carinarnica(18,"Belize"));
        lstCarinarnice.add(new Carinarnica(19,"Benin"));
        lstCarinarnice.add(new Carinarnica(20,"Bhutan"));
        lstCarinarnice.add(new Carinarnica(21,"Bolivia (Plurinational State of)"));
        lstCarinarnice.add(new Carinarnica(22,"Bosnia and Herzegovina"));
        lstCarinarnice.add(new Carinarnica(23,"Botswana"));
        lstCarinarnice.add(new Carinarnica(24,"Brazil"));
        lstCarinarnice.add(new Carinarnica(25,"Brunei Darussalam"));
        lstCarinarnice.add(new Carinarnica(26,"Bulgaria"));
        lstCarinarnice.add(new Carinarnica(27,"Burkina Faso"));
        lstCarinarnice.add(new Carinarnica(28,"Burundi"));
        lstCarinarnice.add(new Carinarnica(29,"Cabo Verde"));
        lstCarinarnice.add(new Carinarnica(30,"Cambodia"));
        lstCarinarnice.add(new Carinarnica(31,"Cameroon"));
        lstCarinarnice.add(new Carinarnica(32,"Canada"));
        lstCarinarnice.add(new Carinarnica(33,"Central African Republic (the)"));
        lstCarinarnice.add(new Carinarnica(34,"Chad"));
        lstCarinarnice.add(new Carinarnica(35,"Chile"));
        lstCarinarnice.add(new Carinarnica(36,"China"));
        lstCarinarnice.add(new Carinarnica(37,"Colombia"));
        lstCarinarnice.add(new Carinarnica(38,"Comoros (the)"));
        lstCarinarnice.add(new Carinarnica(39,"Congo (the)"));
        lstCarinarnice.add(new Carinarnica(40,"Costa Rica"));
        lstCarinarnice.add(new Carinarnica(41,"Côte d'Ivoire"));
        lstCarinarnice.add(new Carinarnica(42,"Croatia"));
        lstCarinarnice.add(new Carinarnica(43,"Cuba"));
        lstCarinarnice.add(new Carinarnica(44,"Cyprus"));
        lstCarinarnice.add(new Carinarnica(45,"Czechia"));
        lstCarinarnice.add(new Carinarnica(46,"Democratic People's Republic of Korea (the)"));
        lstCarinarnice.add(new Carinarnica(47,"Democratic Republic of the Congo (the)"));
        lstCarinarnice.add(new Carinarnica(48,"Denmark"));
        lstCarinarnice.add(new Carinarnica(49,"Djibouti"));
        lstCarinarnice.add(new Carinarnica(50,"Dominica"));
        lstCarinarnice.add(new Carinarnica(51,"Dominican Republic (the)"));
        lstCarinarnice.add(new Carinarnica(52,"Ecuador"));
        lstCarinarnice.add(new Carinarnica(53,"Egypt"));
        lstCarinarnice.add(new Carinarnica(54,"El Salvador"));
        lstCarinarnice.add(new Carinarnica(55,"Equatorial Guinea"));
        lstCarinarnice.add(new Carinarnica(56,"Eritrea"));
        lstCarinarnice.add(new Carinarnica(57,"Estonia"));
        lstCarinarnice.add(new Carinarnica(58,"Eswatini"));
        lstCarinarnice.add(new Carinarnica(59,"Ethiopia"));
        lstCarinarnice.add(new Carinarnica(60,"Fiji"));
        lstCarinarnice.add(new Carinarnica(61,"Finland"));
        lstCarinarnice.add(new Carinarnica(62,"France"));
        lstCarinarnice.add(new Carinarnica(63,"Gabon"));
        lstCarinarnice.add(new Carinarnica(64,"Gambia (the)"));
        lstCarinarnice.add(new Carinarnica(65,"Georgia"));
        lstCarinarnice.add(new Carinarnica(66,"Germany"));
        lstCarinarnice.add(new Carinarnica(67,"Ghana"));
        lstCarinarnice.add(new Carinarnica(68,"Greece"));
        lstCarinarnice.add(new Carinarnica(69,"Grenada"));
        lstCarinarnice.add(new Carinarnica(70,"Guatemala"));
        lstCarinarnice.add(new Carinarnica(71,"Guinea"));
        lstCarinarnice.add(new Carinarnica(72,"Guinea-Bissau"));
        lstCarinarnice.add(new Carinarnica(73,"Guyana"));
        lstCarinarnice.add(new Carinarnica(74,"Haiti"));
        lstCarinarnice.add(new Carinarnica(75,"Honduras"));
        lstCarinarnice.add(new Carinarnica(76,"Hungary"));
        lstCarinarnice.add(new Carinarnica(77,"Iceland"));
        lstCarinarnice.add(new Carinarnica(78,"India"));
        lstCarinarnice.add(new Carinarnica(79,"Indonesia"));
        lstCarinarnice.add(new Carinarnica(80,"Iran (Islamic Republic of)"));
        lstCarinarnice.add(new Carinarnica(81,"Iraq"));
        lstCarinarnice.add(new Carinarnica(82,"Ireland"));
        lstCarinarnice.add(new Carinarnica(83,"Israel"));
        lstCarinarnice.add(new Carinarnica(84,"Italy"));
        lstCarinarnice.add(new Carinarnica(85,"Jamaica"));
        lstCarinarnice.add(new Carinarnica(86,"Japan"));
        lstCarinarnice.add(new Carinarnica(87,"Jordan"));
        lstCarinarnice.add(new Carinarnica(88,"Kazakhstan"));
        lstCarinarnice.add(new Carinarnica(89,"Kenya"));
        lstCarinarnice.add(new Carinarnica(90,"Kiribati"));
        lstCarinarnice.add(new Carinarnica(91,"Kuwait"));
        lstCarinarnice.add(new Carinarnica(92,"Kyrgyzstan"));
        lstCarinarnice.add(new Carinarnica(93,"Lao People's Democratic Republic (the)"));
        lstCarinarnice.add(new Carinarnica(94,"Latvia"));
        lstCarinarnice.add(new Carinarnica(95,"Lebanon"));
        lstCarinarnice.add(new Carinarnica(96,"Lesotho"));
        lstCarinarnice.add(new Carinarnica(97,"Liberia"));
        lstCarinarnice.add(new Carinarnica(98,"Libya"));
        lstCarinarnice.add(new Carinarnica(99,"Liechtenstein"));
        lstCarinarnice.add(new Carinarnica(100,"Lithuania"));
        lstCarinarnice.add(new Carinarnica(101,"Luxembourg"));
        lstCarinarnice.add(new Carinarnica(102,"Madagascar"));
        lstCarinarnice.add(new Carinarnica(103,"Malawi"));
        lstCarinarnice.add(new Carinarnica(104,"Malaysia"));
        lstCarinarnice.add(new Carinarnica(105,"Maldives"));
        lstCarinarnice.add(new Carinarnica(106,"Mali"));
        lstCarinarnice.add(new Carinarnica(107,"Malta"));
        lstCarinarnice.add(new Carinarnica(108,"Marshall Islands (the)"));
        lstCarinarnice.add(new Carinarnica(109,"Mauritania"));
        lstCarinarnice.add(new Carinarnica(110,"Mauritius"));
        lstCarinarnice.add(new Carinarnica(111,"Mexico"));
        lstCarinarnice.add(new Carinarnica(112,"Micronesia (Federated States of)"));
        lstCarinarnice.add(new Carinarnica(113,"Monaco"));
        lstCarinarnice.add(new Carinarnica(114,"Mongolia"));
        lstCarinarnice.add(new Carinarnica(115,"Montenegro"));
        lstCarinarnice.add(new Carinarnica(116,"Morocco"));
        lstCarinarnice.add(new Carinarnica(117,"Mozambique"));
        lstCarinarnice.add(new Carinarnica(118,"Myanmar"));
        lstCarinarnice.add(new Carinarnica(119,"Namibia"));
        lstCarinarnice.add(new Carinarnica(120,"Nauru"));
        lstCarinarnice.add(new Carinarnica(121,"Nepal"));
        lstCarinarnice.add(new Carinarnica(122,"Netherlands (the)"));
        lstCarinarnice.add(new Carinarnica(123,"New Zealand"));
        lstCarinarnice.add(new Carinarnica(124,"Nicaragua"));
        lstCarinarnice.add(new Carinarnica(125,"Niger (the)"));
        lstCarinarnice.add(new Carinarnica(126,"Nigeria"));
        lstCarinarnice.add(new Carinarnica(127,"North Macedonia"));
        lstCarinarnice.add(new Carinarnica(128,"Norway"));
        lstCarinarnice.add(new Carinarnica(129,"Oman"));
        lstCarinarnice.add(new Carinarnica(130,"Pakistan"));
        lstCarinarnice.add(new Carinarnica(131,"Palau"));
        lstCarinarnice.add(new Carinarnica(132,"Panama"));
        lstCarinarnice.add(new Carinarnica(133,"Papua New Guinea"));
        lstCarinarnice.add(new Carinarnica(134,"Paraguay"));
        lstCarinarnice.add(new Carinarnica(135,"Peru"));
        lstCarinarnice.add(new Carinarnica(136,"Philippines (the)"));
        lstCarinarnice.add(new Carinarnica(137,"Poland"));
        lstCarinarnice.add(new Carinarnica(138,"Portugal"));
        lstCarinarnice.add(new Carinarnica(139,"Qatar"));
        lstCarinarnice.add(new Carinarnica(140,"Republic of Korea (the)"));
        lstCarinarnice.add(new Carinarnica(141,"Republic of Moldova (the)"));
        lstCarinarnice.add(new Carinarnica(142,"Romania"));
        lstCarinarnice.add(new Carinarnica(143,"Russian Federation (the)"));
        lstCarinarnice.add(new Carinarnica(144,"Rwanda"));
        lstCarinarnice.add(new Carinarnica(145,"Saint Kitts and Nevis"));
        lstCarinarnice.add(new Carinarnica(146,"Saint Lucia"));
        lstCarinarnice.add(new Carinarnica(147,"Saint Vincent and the Grenadines"));
        lstCarinarnice.add(new Carinarnica(148,"Samoa"));
        lstCarinarnice.add(new Carinarnica(149,"San Marino"));
        lstCarinarnice.add(new Carinarnica(150,"Sao Tome and Principe"));
        lstCarinarnice.add(new Carinarnica(151,"Saudi Arabia"));
        lstCarinarnice.add(new Carinarnica(152,"Senegal"));
        lstCarinarnice.add(new Carinarnica(153,"Serbia"));
        lstCarinarnice.add(new Carinarnica(154,"Seychelles"));
        lstCarinarnice.add(new Carinarnica(155,"Sierra Leone"));
        lstCarinarnice.add(new Carinarnica(156,"Singapore"));
        lstCarinarnice.add(new Carinarnica(157,"Slovakia"));
        lstCarinarnice.add(new Carinarnica(158,"Slovenia"));
        lstCarinarnice.add(new Carinarnica(159,"Solomon Islands"));
        lstCarinarnice.add(new Carinarnica(160,"Somalia"));
        lstCarinarnice.add(new Carinarnica(161,"South Africa"));
        lstCarinarnice.add(new Carinarnica(162,"South Sudan"));
        lstCarinarnice.add(new Carinarnica(163,"Spain"));
        lstCarinarnice.add(new Carinarnica(164,"Sri Lanka"));
        lstCarinarnice.add(new Carinarnica(165,"Sudan (the)"));
        lstCarinarnice.add(new Carinarnica(166,"Suriname"));
        lstCarinarnice.add(new Carinarnica(167,"Sweden"));
        lstCarinarnice.add(new Carinarnica(168,"Switzerland"));
        lstCarinarnice.add(new Carinarnica(169,"Syrian Arab Republic (the)"));
        lstCarinarnice.add(new Carinarnica(170,"Tajikistan"));
        lstCarinarnice.add(new Carinarnica(171,"Thailand"));
        lstCarinarnice.add(new Carinarnica(172,"Timor-Leste"));
        lstCarinarnice.add(new Carinarnica(173,"Togo"));
        lstCarinarnice.add(new Carinarnica(174,"Tonga"));
        lstCarinarnice.add(new Carinarnica(175,"Trinidad and Tobago"));
        lstCarinarnice.add(new Carinarnica(176,"Tunisia"));
        lstCarinarnice.add(new Carinarnica(177,"Turkey"));
        lstCarinarnice.add(new Carinarnica(178,"Turkmenistan"));
        lstCarinarnice.add(new Carinarnica(179,"Tuvalu"));
        lstCarinarnice.add(new Carinarnica(180,"Uganda"));
        lstCarinarnice.add(new Carinarnica(181,"Ukraine"));
        lstCarinarnice.add(new Carinarnica(182,"United Arab Emirates (the)"));
        lstCarinarnice.add(new Carinarnica(183,"United Kingdom of Great Britain and Northern Ireland (the)"));
        lstCarinarnice.add(new Carinarnica(184,"United Republic of Tanzania (the)"));
        lstCarinarnice.add(new Carinarnica(185,"United States of America (the)"));
        lstCarinarnice.add(new Carinarnica(186,"Uruguay"));
        lstCarinarnice.add(new Carinarnica(187,"Uzbekistan"));
        lstCarinarnice.add(new Carinarnica(188,"Vanuatu"));
        lstCarinarnice.add(new Carinarnica(189,"Venezuela (Bolivarian Republic of)"));
        lstCarinarnice.add(new Carinarnica(190,"Viet Nam"));
        lstCarinarnice.add(new Carinarnica(191,"Yemen"));
        lstCarinarnice.add(new Carinarnica(192,"Zambia"));
        lstCarinarnice.add(new Carinarnica(193,"Zimbabwe"));
        lstCarinarnice.add(new Carinarnica(194,"Holy See (the) *"));
        lstCarinarnice.add(new Carinarnica(195,"State of Palestine (the) *"));
        lstCarinarnice.add(new Carinarnica(196,"Cook Islands (the) **"));
        lstCarinarnice.add(new Carinarnica(197,"Niue **"));

    }


    public Carinarnica GetCarinarnicaByID(int Sifra)
    {
        Carinarnica carinarnica = new Carinarnica();
        carinarnica.Sifra = -1;

        for(int i = 0; i< lstCarinarnice.size(); i++)
        {
            Carinarnica cur = lstCarinarnice.get(i);

            if(cur.Sifra == Sifra)
            {
                return cur;
            }
        }

        return carinarnica;
    }

    private void LoadEyeColor()
    {
        lstEyeColor = new ArrayList<Carinarnica>();

        lstEyeColor.add(new Carinarnica(1,"Brown"));
        lstEyeColor.add(new Carinarnica(2,"Blue"));
        lstEyeColor.add(new Carinarnica(3,"Hazel"));
        lstEyeColor.add(new Carinarnica(4,"Green"));
    }


    public Carinarnica GetEyeColorByID(int Sifra)
    {
        Carinarnica carinarnica = new Carinarnica();
        carinarnica.Sifra = -1;

        for(int i = 0; i< lstEyeColor.size(); i++)
        {
            Carinarnica cur = lstEyeColor.get(i);

            if(cur.Sifra == Sifra)
            {
                return cur;
            }
        }

        return carinarnica;
    }

    private void LoadHairColor()
    {
        lstHairColor = new ArrayList<Carinarnica>();

        lstHairColor.add(new Carinarnica(1,"Black"));
        lstHairColor.add(new Carinarnica(2,"Brown"));
        lstHairColor.add(new Carinarnica(3,"Red"));
        lstHairColor.add(new Carinarnica(4,"Blond"));
    }


    public Carinarnica GetHairColorByID(int Sifra)
    {
        Carinarnica carinarnica = new Carinarnica();
        carinarnica.Sifra = -1;

        for(int i = 0; i< lstHairColor.size(); i++)
        {
            Carinarnica cur = lstHairColor.get(i);

            if(cur.Sifra == Sifra)
            {
                return cur;
            }
        }

        return carinarnica;
    }
    private void LoadDocumentType()
    {
        lstDocumentType = new ArrayList<Carinarnica>();

        lstDocumentType.add(new Carinarnica(1,"Passport"));
        lstDocumentType.add(new Carinarnica(2,"Citizenship Card"));
        lstDocumentType.add(new Carinarnica(3,"Driver Licence"));
        lstDocumentType.add(new Carinarnica(4,"Social Security/Insurance"));
    }


    public Carinarnica GetDocumentTypeByID(int Sifra)
    {
        Carinarnica carinarnica = new Carinarnica();
        carinarnica.Sifra = -1;

        for(int i = 0; i< lstDocumentType.size(); i++)
        {
            Carinarnica cur = lstDocumentType.get(i);

            if(cur.Sifra == Sifra)
            {
                return cur;
            }
        }

        return carinarnica;
    }

    /**
     * BLQStat class initialization
     */
    public void InicializujBLQStat(Context cnt)
    {


    }










    interface BLQCallback
    {
        void callbackCall(int RESULT, String TXT);
    }

    BLQCallback callback = null;



}
