package org.sana.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import org.sana.R;
import org.sana.android.activity.MainActivity;
import org.sana.android.content.Intents;

public class User_profile1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile1);
    }
    public void press(View view) {
        Intent intent = new Intent(User_profile1.this, MainActivityPatient.class);
        startActivity(intent);



    }
}
