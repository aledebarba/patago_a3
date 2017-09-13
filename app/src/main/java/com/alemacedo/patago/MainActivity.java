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

import java.util.Timer;

import com.alemacedo.patago.fragments.TabFragment;
import com.alemacedo.patago.models.PatagoRotinas;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final Object TAG = "ALEMACEDO";
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    // private InterstitialAd interstitial;
    public Timer AdTimer;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //recupera dados ===========================================================================================

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PatagoRoutineService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PatagoRoutineService service = retrofit.create(PatagoRoutineService.class);
        Call<PatagoRotinas> requestRotinas = service.ListRoutines();

        requestRotinas.enqueue(new Callback<PatagoRotinas>() {
            @Override
            public void onResponse(Call<PatagoRotinas> call, Response<PatagoRotinas> response) {
                if(!response.isSuccessful()) {
                    Log.i("TAG","deu erro:" + response.code());
                } else {
                    // Deu certo!
                    PatagoRotinas rotinas = response.body();
                }
            }

            @Override
            public void onFailure(Call<PatagoRotinas> call, Throwable t) {
                Log.e("TAG","Erro: " + t.getMessage());
            }
        });


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

     /*             if (menuItem.getItemId() == R.id.drawer_share) {
                    Toast.makeText(MainActivity.this,
                            "Replace with your own function", Toast.LENGTH_LONG).show();
                }
                if (menuItem.getItemId() == R.id.drawer_youtube) {
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


    // Lida com o botão Chamar do fragment Amigo de Confiança ================================

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





/*
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {







    private ViewPager viewPager;
    private DrawerLayout drawer;
    private TabLayout tabLayout;
    private String[] pageTitle = {"Buscar Rotinas", "Minhas Rotinas", "Meu Dia"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // viewPager = (ViewPager)findViewById(R.id.view_pager);


        // tablayout titles
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_panic) {
            fragment = new Panic();
        } else if (id == R.id.nav_trusted) {
            fragment = new TrustedFriendFragment();
        } else if (id == R.id.nav_myinfo) {
            fragment = new BlankFragment(); // TODO
        } else if (id == R.id.nav_rotinas) {
            fragment = new Rotinas();
        } else if (id == R.id.nav_about) {
            fragment = new BlankFragment(); // TODO
        }
        else if (id == R.id.nav_send) {
            fragment = new BlankFragment(); // TODO
        }
        else if (id == R.id.nav_share) {
            fragment = new BlankFragment(); // TODO
        }
        else if (id == R.id.nav_settings) {
            fragment = new BlankFragment(); // TODO
        };
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, fragment).commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
*/
