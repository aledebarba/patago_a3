package com.alemacedo.patago;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Rotinas extends Fragment {

    public static final String BASE_URL = "http://www.mocky.io/v2/";
    private static final String TAG = "ALEMACEDO";
    protected boolean onCreateViewCalled = false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<AndroidVersion> data;
    private ArrayList<DBRotinas> rdata;
    private DataAdapter adapter;
    private Button mbtCreate;


    public Rotinas() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rotinas, container, false);
        rootView.setTag(TAG);

        Button mbtCreate = (Button) rootView.findViewById(R.id.addRoutine);
        mbtCreate.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view1) {
                                             Intent intent = new Intent(view1.getContext(), rotina.class);
                                             intent.putExtra("position", "none");
                                             view1.getContext().startActivity(intent);
                                         }
                                     });

        // inicializa a RecyclerView e o layoutmanager
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (firebaseDatabase == null) {  inicializarFirebase(); }
        eventoDatabase();

        return rootView;

    }

    private void inicializarFirebase() {

        FirebaseApp.initializeApp(Rotinas.this.getContext());

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    private void eventoDatabase () {

        databaseReference.child("DBRotinas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rdata = new ArrayList<DBRotinas>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    DBRotinas dbrot = snapshot.getValue(DBRotinas.class);
                    rdata.add(dbrot);
                }
                adapter = new DataAdapter(rdata);
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Database Error");
            }
        });
    }

    // CODIGO QUE LIDAVA COM O JSON...
    /*
    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();

        // Cria o progress
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setTitle("Carregando rotinas...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        // ^^ mostra o progress


        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
            if (response.isSuccessful()) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<AndroidVersion>();
                for (AndroidVersion av : jsonResponse.android) {
                    data.add(av);
                }
                adapter = new DataAdapter(data);
                mRecyclerView.setAdapter(adapter);
            } else {
                Log.e(TAG, "Erro no JSON");
            }

            progressDoalog.dismiss();

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                progressDoalog.dismiss();
            }
        });
    }*/

}


