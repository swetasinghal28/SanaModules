package org.sana.android.activity;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sana.R;
import org.sana.android.LoginDataBaseAdapter;

public class PatientAuthentication extends Activity   {

    Button btn_patient_login,btn_patient_exit,btn_reg_new_patient;
    EditText edittext_login_firstname , edittext_login_password;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_authentication);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        btn_patient_login = (Button)findViewById(R.id.btn_patient_login);
        btn_patient_exit = (Button)findViewById(R.id.btn_patient_exit);
        btn_reg_new_patient = (Button)findViewById(R.id.btn_reg_new_patient);
        edittext_login_firstname = (EditText)findViewById(R.id.edittext_login_firstname);
        edittext_login_password= (EditText)findViewById(R.id.edittext_login_password);

        btn_reg_new_patient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentRegister = new Intent(PatientAuthentication.this, Registration.class);
                startActivity(intentRegister);
            }
        });
        btn_patient_login.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                String firstname = edittext_login_firstname.getText().toString();
                String password = edittext_login_password.getText().toString();
                String storedpassword = loginDataBaseAdapter.getSingleEntry(firstname);

                if (password.equals(storedpassword))
                {
                    Toast.makeText(PatientAuthentication.this , "LOGIN SUCCESSFUL", Toast.LENGTH_LONG).show();
                    Intent intentlogin = new Intent(PatientAuthentication.this, MainActivityPatient.class);
                    startActivity(intentlogin);
                }
                else{
                    Toast.makeText(PatientAuthentication.this , "USERNAME OR PASSWORD DOES NOT MATCH" , Toast.LENGTH_LONG).show();;
                    
                }
            }
        });

    }

    protected  void onDestroy(){
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
