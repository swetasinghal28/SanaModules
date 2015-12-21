package org.sana.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.database.Cursor;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import org.sana.android.LoginDataBaseAdapter;


import org.sana.R;

public class User_profile extends Activity {
    EditText firstname,lastname,password,height,weight,haemoglobin,bloodgroup,new_firstname,
            new_lastname,new_height,new_weight,new_password,new_haemoglobin,new_bloodgroup,fn;
    Button btn_savechanges, btn_delete_account;
    LoginDataBaseAdapter loginDataBaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        firstname = (EditText) findViewById(R.id.edittext_editprofile_firstname);
        lastname = (EditText) findViewById(R.id.edittext_editprofile_lastname);
        password = (EditText) findViewById(R.id.edittext_editprofile_password);
        haemoglobin = (EditText) findViewById(R.id.edittext_editprofile_hb);
        height = (EditText) findViewById(R.id.edittext_editprofile_height);
        weight = (EditText) findViewById(R.id.edittext_editprofile_weight);
        bloodgroup = (EditText) findViewById(R.id.edittext_editprofile_bloodgroup);
        btn_savechanges = (Button) findViewById(R.id.btn_savechanges);
        btn_delete_account = (Button) findViewById(R.id.btn_delete_account);
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter.open();
        Cursor c = loginDataBaseAdapter.getPatientDetail();
        firstname.setText(c.getString(0));
        lastname.setText(c.getString(1));
        password.setText(c.getString(2));
        haemoglobin.setText(c.getString(3));
        weight.setText(c.getString(4));
        height.setText(c.getString(5));
        bloodgroup.setText(c.getString(6));

        new_firstname = (EditText) findViewById(R.id.edittext_editprofile_firstname);
        new_lastname = (EditText) findViewById(R.id.edittext_editprofile_lastname);
        new_height = (EditText) findViewById(R.id.edittext_editprofile_height);
        new_weight = (EditText) findViewById(R.id.edittext_editprofile_weight);
        new_password = (EditText) findViewById(R.id.edittext_editprofile_password);
        new_haemoglobin = (EditText) findViewById(R.id.edittext_editprofile_hb);
        new_bloodgroup = (EditText) findViewById(R.id.edittext_editprofile_bloodgroup);

        btn_savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                        String new_fn = new_firstname.getText().toString();
                        String new_ln = new_lastname.getText().toString();
                        String new_pswd = new_password.getText().toString();
                        String new_wt = new_weight.getText().toString();
                        String new_ht = new_height.getText().toString();
                        String new_hb = new_haemoglobin.getText().toString();
                        String new_bg = new_bloodgroup.getText().toString();


                        if (new_fn.equals("") || new_pswd.equals("") || new_ln.equals("")
                            || new_bg.equals("") || new_ht.equals("") || new_wt.equals("")
                            || new_hb.equals("")) {
                            Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            loginDataBaseAdapter.updateEntry(new_fn,
                                        new_ln, new_pswd, new_bg, new_hb, new_wt, new_ht);
                            Toast.makeText(getApplicationContext(), "Changes Saved", Toast.LENGTH_LONG).show();
                            Intent intentlogin = new Intent(User_profile.this, MainActivityPatient.class);
                            startActivity(intentlogin);}

                    loginDataBaseAdapter.close();


            }
        });
        btn_delete_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                fn=(EditText)findViewById(R.id.edittext_editprofile_firstname);
                //AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setMessage(R.string.deletePatient)
                        //.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            //public void onClick(DialogInterface dialog, int id) {
                                loginDataBaseAdapter.deleteEntry(fn.getText().toString());
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),PatientAuthentication.class);
                                startActivity(intent);
                            }
                        //})
                        //.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            //public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            //}
                        //});
                //AlertDialog d = builder.create();
                //d.setTitle("Are you sure");
                //d.show();



            //}
        });

    }


}



