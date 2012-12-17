package gosuninjas.koinbox;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * this class displays the main menu screen of the application. 
 * Contains two buttons sign in and register. 
 * Clicking on either or the buttons will take you to the respective screens. 
 * It also creates a dialog for when the phone is not connected to the internet.
 * Uses Typeface graphics to change the fonts to make the UI look better.
 * @author shivangi
 *
 */
public class MainMenu extends Activity implements OnClickListener {
	Button registr, signn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        registr = (Button) findViewById(R.id.register);
        registr.setOnClickListener(this);
        signn = (Button) findViewById(R.id.login);
        signn.setOnClickListener(this);
        if (!isNetworkAvailable()){
        	new AlertDialog.Builder(MainMenu.this)
			.setMessage("Please enable internet connection to user this program!")
			.show();
        }
        Typeface cPhrase = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
        TextView cP = (TextView) findViewById(R.id.catch_phrase);  
        cP.setTypeface(cPhrase);
        Typeface rSter = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
        TextView rS = (TextView) findViewById(R.id.register);  
        rS.setTypeface(rSter);
        Typeface sIn = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
        TextView sI = (TextView) findViewById(R.id.login);  
        sI.setTypeface(sIn);
    }
    /**
     * This function checks whether the phone is connected to the Internet. The function returns a boolean.
     * 
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    
    public void onClick(View v){
    	switch (v.getId()){
    	/**
    	 * main class will start either the login or the register activity and take you to the respective page.
    	 * 
    	 * 
    	 */
    	case R.id.register:
    		Intent Rster = new Intent(this, Register.class);
    		startActivity(Rster);
    		break;
    	
    	case R.id.login:
    		Intent sIN =new Intent(this, Koinbox.class);
    		
    			startActivity(sIN);
    			break;
    		
    	}
    }
}
