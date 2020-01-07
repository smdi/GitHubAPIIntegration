package aidev.com.github;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserData extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RelativeLayout prof, repocode;
    private String username;
    private String REQUEST_URL;
    private ImageView profilePic;
    private TextView summary;
    private TextView repoNums;
    private ProgressBar spinner;
    private TextView nametv,comopanytv, loctv, followerstv, followingtv;
    private String name,company, loc, followers, followins;
    private EditText reposearch;

    public  int  start1 =0,start2 = 0 ;

    String avatar, bio, repos;

    ArrayList<RepoInitialiser> listView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.profile:

                        spinner.setVisibility(View.VISIBLE);
                        repocode.setVisibility(View.GONE);
                        prof.setVisibility(View.GONE);
                        getProfile(username);

                    return true;
                case R.id.repository:

                        spinner.setVisibility(View.VISIBLE);
                        prof.setVisibility(View.GONE);
                        repocode.setVisibility(View.GONE);
                        getRepository(username);
                        return true;

            }
            return false;
        }
    };

    private void getToast(String no_internet) {
        TastyToast.makeText(getApplicationContext(),no_internet,TastyToast.LENGTH_SHORT, TastyToast.CONFUSING).show();
    }

    private void getRepository(String username) {


        if(start2 == 0 && checkInternet() == 1){
            REQUEST_URL ="https://api.github.com/users/"+username+"/repos";
            Repo rp= new Repo();
            rp.execute(REQUEST_URL);
        }
        else{

            adapter = new RepoAdapter(getApplicationContext(),listView);
            recyclerView.setAdapter(adapter);

            spinner.setVisibility(View.GONE);
            repocode.setVisibility(View.VISIBLE);

            if(checkInternet() == 0){getToast("Internet not available loading offline data");}

        }

    }

    private void getProfile(String username) {


        if(start1 == 0 && checkInternet() == 1){
            REQUEST_URL ="https://api.github.com/users/"+username ;
            Prof pf= new Prof();
            pf.execute(REQUEST_URL);
        }
        else{

            profilePic.setImageResource(R.color.transparent);
            Glide.with(getApplicationContext()).load(avatar).into(profilePic);
            summary.setText(bio);
            repoNums.setText(repos);

            spinner.setVisibility(View.GONE);
            prof.setVisibility(View.VISIBLE);

            if(checkInternet() == 0){getToast("Internet not available loading offline data");}
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        initialse();


    }

    private void initialse() {

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        prof = (RelativeLayout) findViewById(R.id.profileTab);
        repocode = (RelativeLayout) findViewById(R.id.repocode);




        profilePic = (ImageView) findViewById(R.id.userimage);
        summary = (TextView) findViewById(R.id.summary);
        repoNums = (TextView) findViewById(R.id.reponum);
        nametv = (TextView) findViewById(R.id.username);
        loctv = (TextView) findViewById(R.id.userloc);
        comopanytv = (TextView) findViewById(R.id.usercompany);
        followerstv = (TextView) findViewById(R.id.followersno);
        followingtv = (TextView) findViewById(R.id.followingno);



        reposearch = (EditText) findViewById(R.id.reposearch);
        recyclerView = (RecyclerView) findViewById(R.id.repoTab);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        listView = new ArrayList();

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        repocode.setVisibility(View.GONE);
        prof.setVisibility(View.GONE);

        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        username = (shared.getString("username", "github"));
//        getProfile(username);

        navigation.setSelectedItemId(R.id.profile);

        //add text watcher
        reposearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    filter(editable.toString());
            }
        });

    }

    private class Repo extends AsyncTask<String, Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }


        @Override
        protected void onPostExecute(Void androidAdapter) {

            spinner.setVisibility(View.GONE);
            repocode.setVisibility(View.VISIBLE);
            prof.setVisibility(View.GONE);

        }

        @Override
        protected Void doInBackground(String... strings) {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, REQUEST_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {


                        JSONArray baseJsonResponse = new JSONArray(response);

                        for(int i = 0;i<baseJsonResponse.length();i++){

                            JSONObject data = baseJsonResponse.getJSONObject(i);
                            JSONObject owner = data.getJSONObject("owner");
                            RepoInitialiser ri = new RepoInitialiser(data.getString("name"),owner.getString("html_url")
                            ,data.getString("description"),data.getString("pushed_at"),data.getString("updated_at"),data.getString("created_at"));
                            listView.add(ri);

                        }

                        start2 = start2+1;

                    } catch (JSONException e) {


                    }


                    adapter = new RepoAdapter(getApplicationContext(),listView);
                    recyclerView.setAdapter(adapter);

                }
            }

                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    TastyToast.makeText(getApplicationContext(),"Information not available",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                    finish();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);



            return null;
        }



    }


    private class Prof extends AsyncTask<String, Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }


        @Override
        protected void onPostExecute(Void androidAdapter) {

            spinner.setVisibility(View.GONE);
            repocode.setVisibility(View.GONE);
            prof.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(final String... strings) {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, REQUEST_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {



                    try {


                        JSONObject baseJsonResponse = new JSONObject(response);
                        avatar = baseJsonResponse.getString("avatar_url");
                        bio = baseJsonResponse.getString("bio");
                        repos = baseJsonResponse.getString("public_repos");
                        name = baseJsonResponse.getString("name");
                        company = baseJsonResponse.getString("company");
                        loc = baseJsonResponse.getString("location");
                        followers = baseJsonResponse.getString("followers");
                        followins = baseJsonResponse.getString("following");

                        if(bio.equals("null")){
                            summary.setText("Not Available");
                        }
                        if(company.equals("null")){
                            comopanytv.setText("Organization not available");
                        }
                        if(loc.equals("null")){
                            comopanytv.setText("User Location not available");
                        }


                        profilePic.setImageResource(R.color.transparent);
                        Glide.with(getApplicationContext()).load(avatar).into(profilePic);
                        summary.setText(bio);
                        repoNums.setText(repos);
                        start1 = start1+1;
                        nametv.setText(name);
                        loctv.setText(loc);
                        comopanytv.setText(company);
                        followerstv.setText(followers.toString());
                        followingtv.setText(followins.toString());


                    } catch (JSONException e) {

                        Log.e("profile tab", "Json exception", e);
                    }


                }
            }

                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    TastyToast.makeText(getApplicationContext(),"Information not available",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                    finish();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);



            return null;
        }



    }

    public void filter(String text){
        List<RepoInitialiser> temp = new ArrayList();
        for(RepoInitialiser d: listView){

            if(d.getLink().toLowerCase().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        adapter = new RepoAdapter(getApplicationContext(),temp);
        recyclerView.setAdapter(adapter);
    }

    private int checkInternet(){

        int connectivity = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connectivity = 1;

        }

        return connectivity;
    }

}
