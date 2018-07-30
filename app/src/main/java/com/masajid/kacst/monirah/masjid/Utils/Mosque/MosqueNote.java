package com.masajid.kacst.monirah.masjid.Utils.Mosque;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.masajid.kacst.monirah.masjid.R;
import com.masajid.kacst.monirah.masjid.Utils.Utils.SharedPreferencesConfig;

/**
 * Created by Monirah on 7/25/2018.
 */

public class MosqueNote  extends Fragment {

    private Button button;
    private TextView checkLogin;


    //sharedpreferences
    private SharedPreferencesConfig sharedConfig;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_mosque_note, container, false);

        button = (Button) view.findViewById(R.id.AddNewNote);
        checkLogin = (TextView) view.findViewById(R.id.NoData);

        //Check If User Login
        sharedConfig = new SharedPreferencesConfig(getActivity().getApplicationContext());
        if (sharedConfig.readLoginStatus()) {

            checkLogin.setVisibility(View.GONE);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO : Chang Dialog Style

                    android.support.v7.app.AlertDialog.Builder dlgAlert = new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
                    dlgAlert.setIcon(R.drawable.warning);

                    dlgAlert.setTitle("تنبيه");
                    dlgAlert.setMessage("لم تتاح الخدمة مؤقتا");
                    dlgAlert.setPositiveButton("موافق", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    dlgAlert.setPositiveButton("موافق",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                }
            });


        } else {

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder Dialog = new AlertDialog.Builder(getContext());
                    View DialogView = getLayoutInflater().inflate(R.layout.dialog_note_check_login, null);
                    Button OkButoon = (Button) DialogView.findViewById(R.id.Ok);
                    Dialog.setView(DialogView);
                    final AlertDialog dialog = Dialog.create();
                    dialog.show();
                    OkButoon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                }
            });
        }


        return view;
    }

}