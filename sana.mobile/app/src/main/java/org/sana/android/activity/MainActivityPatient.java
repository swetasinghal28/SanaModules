package org.sana.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.pm.PackageManager;
import android.content.Context;
import android.widget.Toast;

import org.sana.R;
import org.sana.android.SanaApplication;

public class MainActivityPatient extends Activity implements View.OnClickListener {

    private Button mPatientProfile;
    private Button mSCAPP;
    private Button mContactDoctor;
    private boolean isAppAvailable;
    private Button mNotification;
    private Button mHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_patient);
        isAppAvailable = isPackageInstalled("org.cnmc.painClinic", MainActivityPatient.this);

        mSCAPP = (Button)findViewById(R.id.btn_open_SCD_app);
        mSCAPP.setOnClickListener(this);

        mPatientProfile = (Button)findViewById(R.id.btn_patient_profile);
        mPatientProfile.setOnClickListener(this);

        mContactDoctor = (Button)findViewById(R.id.btn_contact_doctor);
        mContactDoctor.setOnClickListener(this);

        mNotification = (Button)findViewById(R.id.button_patient_notifications);
        mNotification.setOnClickListener(this);

        mHistory = (Button)findViewById(R.id.btn_History_patient);
        mHistory.setOnClickListener(this);




    }

    private boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
                    case R.id.btn_open_SCD_app:
                        if (isAppAvailable) {
                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                            sendIntent.setType("plain/text");
                            sendIntent.setClassName("org.cnmc.painClinic", "org.cnmc.painClinic.painReport.MainActivity");
                            startActivity(sendIntent);
                        }
                        else{
                            Toast.makeText(this,"App not installed on this phone",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.btn_patient_profile:
                        Intent intent = new Intent(this, User_profile.class);
                        startActivity(intent);
                        break;
                    case R.id.btn_contact_doctor:
                         Intent intentContact = new Intent(this, Email.class);
                        startActivity(intentContact);
                        break;
                    case R.id.button_patient_notifications:
                        Intent intentNotification = new Intent(this, EncounterActivity.class);
                        startActivity(intentNotification);
                break;
                    case R.id.btn_History_patient:
                          Intent intentHistory = new Intent(this, HistoryActivity.class);
                        startActivity(intentHistory);
                    break;

        }
            }
        }
