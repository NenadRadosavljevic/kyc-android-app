package com.parabellum.nftasset;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.util.Log;

/**
 * Created by Ljuba on 19.3.2018..
 */

/***************************************************************************************

                                    BLQWaitForm

                    Showing a wait window during a long running process.


 ***************************************************************************************/


public class BLQWaitForm {

    private static volatile BLQWaitForm instance;

    public String DispText;
    public boolean Greska = false;

    Context context;

    Activity activity;

    ProgressDialog dialog;

    /**
     * Creating BLQWaitForm instance
     */
    public static BLQWaitForm Instance()
    {
        if (instance == null)
        {
            instance = new BLQWaitForm();
        }
        return instance;
    }

    /**
     * Set wait form display text
     */
    public void BLQDispText(String str)
    {
        DispText = str;
        SetNewText();
    }

    /**
     * Set wait form display error text
     */
    public void BLQDispTextError(String str)
    {
        DispText = str;
        Greska = true;
        SetNewText();
    }

    /**
     * Set wait form context
     */
    public void SetContext(Context cnt, Activity act)
    {
        context = cnt;
        activity = act;
    }

    /**
     * Show wait form info
     */
    public void ShowBLQInfo(String str)
    {
        DispText = str;

        if(dialog == null)
        {
            dialog = ProgressDialog.show(context, "",
                    DispText, true);
        }
        else
        {
            activity.runOnUiThread(changeText);
            //dialog.setMessage(DispText);
        }
    }

    /**
     * Set wait form new display text
     */
    public void SetNewText()
    {
        if(dialog == null)
        {
            dialog = ProgressDialog.show(context, "",
                    DispText, true);
        }
        else
        {
            activity.runOnUiThread(changeText);
            //dialog.setMessage(DispText);
        }



    }

    /**
     * Change wait form display text
     */
    public Runnable changeText = new Runnable() {
        @Override
        public void run() {

            if(Greska)
            {
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_red_light);
            }

            dialog.setMessage(DispText);

        }
    };

    /**
     * Hide wait form
     */
    public Runnable KrajDialog = new Runnable() {
        @Override
        public void run()
        {
            Greska = false;
            dialog.dismiss();
            dialog = null;
        }
    };

    /**
     * Hide wait form
     */
    public void Kraj()
    {
        //Log.e(DEBUG_TAG,"BLQ WAIT kraj");
        activity.runOnUiThread(KrajDialog);
    }

    interface BLQCallback
    {
        void callbackCall(int RESULT);
    }

    BLQCallback callback = null;

    /**
     * Show wait form dialog
     */
    public void ShowBLQDialog(String str)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
        dlgAlert.setMessage(str);
        dlgAlert.setTitle("Notification");
        dlgAlert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(callback != null)
                        {
                            callback.callbackCall(1);
                            callback = null;
                        }
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(false);
        //dlgAlert.setCanceledOnTouchOutside(false);
        dlgAlert.create().show();
    }

    /**
     * Show wait form confirmation
     */
    public void ShowBLQDialogConfirmation(String title, String str, String confirmBtnName, boolean cancelBtn, String cancelBtnName, boolean ErrorStyle)
    {
        AlertDialog.Builder dlgAlert;

        if(ErrorStyle)
        {
            dlgAlert  = new AlertDialog.Builder(context, R.style.MyDialogErrorTheme);
        }
        else
        {
            dlgAlert  = new AlertDialog.Builder(context);
        }



        dlgAlert.setMessage(str);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton(confirmBtnName,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(callback != null)
                        {
                            callback.callbackCall(1);
                            callback = null;
                        }
                        //dismiss the dialog
                    }
                });

        if(cancelBtn)
        {
            dlgAlert.setNegativeButton(cancelBtnName,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(callback != null)
                            {
                                callback.callbackCall(0);
                                callback = null;
                            }
                            //dismiss the dialog
                        }
                    });
        }

        dlgAlert.setCancelable(false);
        //dlgAlert.setCanceledOnTouchOutside(false);
        dlgAlert.create().show();
    }



}