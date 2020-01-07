package aidev.com.github;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    EditText username;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        initialise();
        buttonresults();

    }
    private void buttonresults() {


        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String x = username.getText().toString().trim();

                if(x.length() > 0){
                    SharedPreferences.Editor editor = getApplication().getSharedPreferences("user", getApplication().MODE_PRIVATE).edit();
                    editor.putString("username", x);
                    editor.apply();
                    checkInternet();
                }
                else{
                    TastyToast.makeText(getApplicationContext(),"Enter Valid Information",TastyToast.LENGTH_SHORT,TastyToast.CONFUSING).show();
                }

            }


        });

    }


    private void checkInternet(){


        ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            Intent i = new Intent(getApplicationContext(), UserData.class);
            startActivity(i);
        }
        else {
            TastyToast.makeText(getApplication(), "No Internet", Toast.LENGTH_LONG,TastyToast.CONFUSING).show();
        }



    }




    private void initialise() {

        search = (Button) findViewById(R.id.search);
        username = (EditText)findViewById(R.id.username);
    }

}

