package com.alemacedo.patago;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LayerRasterizer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

import com.alemacedo.patago.fragments.TabFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    // Não sei onde isso é usado, daí comentei //private InterstitialAd interstitial;
    // Não sei onde isso é usado, daí comentei //public Timer AdTimer;

    // Retrofit: RecyclerView, ArrayList, BASE_URL, DataAdapter

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());





        // LIDA COM NAVDRAWER ==================================================================================

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.main_drawer);

        // CARREGA OS FRAGMENTS DE ACORDO COM A OPÇÃO DO USUÁRIO

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.drawer_home) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_panic){
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, new Panic()).commit();
                }

              if (menuItem.getItemId() == R.id.drawer_exit){
                    finish();
                }

                if (menuItem.getItemId() == R.id.drawer_share) {
                    try {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, "PATAGO");
                        String sAux = "\nLet me recommend you this application\n\n";
                        sAux = sAux + "https://play.google.com/store/apps/details?id=com.ted.android\n\n";
                        i.putExtra(Intent.EXTRA_TEXT, sAux);
                        startActivity(Intent.createChooser(i, "Share Patago!"));
                    } catch(Exception e) {
                        //e.toString();
                    }
                }

                if (menuItem.getItemId() == R.id.nav_trusted) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_container, new TrustedFriendFragment()).commit();
                }

               if (menuItem.getItemId() == R.id.nav_rotinas) {
                   FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                   fragmentTransaction.replace(R.id.frame_container, new About()).commit();
                }

                 if (menuItem.getItemId() == R.id.nav_myinfo) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.frame_container, new MyData()).commit();
                }
                /* if (menuItem.getItemId() == R.id.drawer_youtube) {
                    Toast.makeText(MainActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }

                if (menuItem.getItemId() == R.id.drawer_exit) {
                    Toast.makeText(MainActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                } */

                return false;
            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();



    }


    // Lida com o botão FAZER CHAMADA do fragment Amigo de Confiança ================================

    public void btCallClick (View v) {
        Uri number = Uri.parse("tel:11985527281");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(callIntent);
    }


    // Trata o botão de voltar do Android para voltar pra tela inicial e não sair do app ===================
    @Override
    public void onBackPressed() {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, new TabFragment()).commit();

    }
}

