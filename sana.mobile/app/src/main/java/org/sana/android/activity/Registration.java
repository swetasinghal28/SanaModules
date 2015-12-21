package org.sana.android.activity;

import android.os.Bundle;
import android.app.Activity;
import org.sana.R;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.sana.android.LoginDataBaseAdapter;
import android.content.Intent;

public class Registration extends Activity {

    EditText edittext_firstname,edittext_lastname,edittext_password, edittext_bloodgroup, edittext_height, edittext_weight, edittext_haemoglobin;
    Button btn_save_reg;

    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        edittext_firstname = (EditText) findViewById(R.id.edittext_login_firstname);
        edittext_lastname = (EditText) findViewById(R.id.edittext_login_lastname);
        edittext_password = (EditText) findViewById(R.id.edittext_login_password);
        edittext_bloodgroup = (EditText) findViewById(R.id.edittext_login_bloodgroup);
        edittext_height = (EditText) findViewById(R.id.edittext_login_height);
        edittext_weight = (EditText) findViewById(R.id.edittext_login_weight);
        edittext_haemoglobin = (EditText) findViewById(R.id.edittext_login_haemoglobin);

        btn_save_reg = (Button) findViewById(R.id.btn_savechanges);
        btn_save_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = edittext_firstname.getText().toString();
                String lastname = edittext_lastname.getText().toString();
                String password = edittext_password.getText().toString();
                String bloodgroup = edittext_bloodgroup.getText().toString();
                String height = edittext_height.getText().toString();
                String weight = edittext_weight.getText().toString();
                String haemoglobin = edittext_haemoglobin.getText().toString();

                if (firstname.equals("") || password.equals("") || lastname.equals("") || bloodgroup.equals("") || height.equals("") || weight.equals("") || haemoglobin.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    loginDataBaseAdapter.insertEntry(firstname, lastname, password, bloodgroup, haemoglobin, weight, height);
                    Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_LONG).show();
                    Intent intentlogin = new Intent(Registration.this,PatientAuthentication.class);
                    startActivity(intentlogin);
                }
            }

        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}

