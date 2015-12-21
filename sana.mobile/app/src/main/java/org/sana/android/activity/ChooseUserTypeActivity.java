package org.sana.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.sana.R;
/**import org.sana.android.PatientAuthentication;**/
import org.sana.android.SanaApplication;

public class ChooseUserTypeActivity extends Activity implements View.OnClickListener {

    private Button mDoctor, mPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_type);

        mDoctor = (Button)findViewById(R.id.btn_doctor);
        mPatient = (Button)findViewById(R.id.btn_patient);
        mPatient.setOnClickListener(this);
        mDoctor.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_doctor:
                SanaApplication.isPatient =false;
                SanaApplication.isDoctor  = true;
                Intent mainIntent = new Intent(this,MainActivity.class);
                startActivity(mainIntent);

                break;

            case R.id.btn_patient:

                SanaApplication.isDoctor =false;
                SanaApplication.isPatient = true;
                Intent mainIntentTwo = new Intent(this,PatientAuthentication.class);
                startActivity(mainIntentTwo);




                break;
        }

    }
}
