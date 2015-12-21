package org.sana.android.activity;

/**
 * Created by Arpit Jaiswal on 10/31/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.sana.R;
import android.content.Intent;

public class Email extends Activity implements View.OnClickListener {

    EditText personsEmail,
            patientID_subject,
            doctor_name,
            send_message;
    String emailAdd,
            Subject_patientID,
            Dname,
            messagestring;
    Button sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        initializeVars();
        sendEmail.setOnClickListener(this);
    }

    private void initializeVars() {
        personsEmail = (EditText) findViewById(R.id.emailadress);
        patientID_subject = (EditText) findViewById(R.id.patient_number);
        doctor_name = (EditText) findViewById(R.id.DoctorName);
        send_message = (EditText) findViewById(R.id.outmessage);
        sendEmail = (Button) findViewById(R.id.btn_SentEmail);
    }

    public void onClick(View v) {

        Text_to_String();
        String emailaddress[] = { emailAdd };
        String Subject_Desc = Subject_patientID;
        String message = "Hello Dr "
                + Dname+","+'\n'
                + messagestring;

        Intent emailIntent  = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,emailaddress);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,Subject_Desc);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_TEXT,message);

        startActivity(emailIntent);

    }

    private void Text_to_String() {
        emailAdd = personsEmail.getText().toString();
        Subject_patientID = patientID_subject.getText().toString();
        Dname = doctor_name.getText().toString();
        messagestring = send_message.getText().toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}