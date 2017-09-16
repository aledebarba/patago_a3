package com.alemacedo.patago;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;


public class rotina extends AppCompatActivity {

    EditText mTitle, mSubtitle, mSteps;
    RadioGroup mRadioGroup;
    String mColor;
    String extra;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotina);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        mTitle = (EditText) findViewById(R.id.etTitle);
        mSubtitle = (EditText) findViewById(R.id.etSubtitle);
        mSteps = (EditText) findViewById(R.id.etPassos);
        mRadioGroup = (RadioGroup) findViewById(R.id.rgColors);
        mColor = "#DDDDFF";

        inicializarFirebase();

        Button btnSave = (Button)findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           btnSaveClick();
                                       }
                                   });
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            extra = extras.getString("position");
            if (extra!="none"){ // recebi a referencia do registro
                DBRotinas r = new DBRotinas();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = database.child("DBRotinas");
                Query uidQuery = ref.orderByChild("uid").equalTo(extra); //busquei o registro

                uidQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            DBRotinas r = snapshot.getValue(DBRotinas.class);

                            //setup na tela de edição
                            mTitle.setText(r.getTitle().toString());
                            mSubtitle.setText(r.getSubtitle().toString());
                            mSteps.setText(r.getSteps().toString());
                            mColor = r.getColor().toString();
                            Button mButton = (Button) findViewById(R.id.btSave);
                            mButton.setText("Atualizar essa Rotina");

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }



    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(rotina.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    public void btnSaveClick() {

        // TODO: Validar dados

        DBRotinas r = new DBRotinas();

        r.setTitle(mTitle.getText().toString());
        r.setSubtitle(mSubtitle.getText().toString());
        r.setOnTime(false);
        r.setOnPlace(false);
        r.setAlarm(false);
        r.setAlarmType("default");
        r.setAlarmName("ATENÇÃO");

        int radioButtonID = mRadioGroup.getCheckedRadioButtonId();
        if (radioButtonID != -1){
            View radioButton = mRadioGroup.findViewById(radioButtonID);
            mColor = String.format("#%06X",
                                    0xFFFFFF & ((ColorDrawable)radioButton
                                                .getBackground())
                                                .getColor());
        } else {
            mColor = "#DDDDFF";
        }
        r.setColor(mColor);
        r.setSteps(mSteps.getText().toString());

        String msg = "";
        if ( extra == "none") {
            msg = "Sua rotina foi criada com sucesso!";
            r.setUid(UUID.randomUUID().toString() );
            }  else  {
            msg = "Sua rotina foi alterada com sucesso!";
            r.setUid(extra);
        }

        databaseReference.child("DBRotinas").child(r.getUid()).setValue(r);

        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
        killActivity();

    }

    public void btRunClick (View v) {
        Toast.makeText(this, "CLICOU NO BOTÃO EXECUTAR",Toast.LENGTH_LONG).show();
        finish();
    }

    public void btDeleteClick (View v) {

        if (extra!="none") {
        DBRotinas r = new DBRotinas();
            r.setUid(extra);
            databaseReference.child("DBRotinas").child(r.getUid()).removeValue();
            Toast.makeText(this, "A rotina foi apagada...",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void killActivity() {
        finish();
    }
}
