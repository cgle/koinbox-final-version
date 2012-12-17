package gosuninjas.koinbox.Core;

import gosuninjas.koinbox.Home;
import gosuninjas.koinbox.MainMenu;
import gosuninjas.koinbox.R;
import gosuninjas.koinbox.R.id;
import gosuninjas.koinbox.R.layout;
import gosuninjas.koinbox.R.string;
import gosuninjas.koinbox.User.UserProfile;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * class for a description about the developers. 
 * This screen has four buttons at the bottoms.
 * @author shivangi
 *
 */

public class AboutUs extends Activity implements OnClickListener {
	final Context context = this;
	Button abProf, abMKB, abFriend, abLogout;
	View aback;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		abProf = (Button) findViewById(R.id.aProf);
		abProf.setOnClickListener(this);
		abMKB = (Button) findViewById(R.id.aMKB);
		abMKB.setOnClickListener(this);
		abFriend = (Button) findViewById(R.id.aFriends);
		abFriend.setOnClickListener(this);
		aback = (View) findViewById(R.id.aBack);
		aback.setOnClickListener(this);
		Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView txt = (TextView) findViewById(R.id.aTitle);  
		txt.setTypeface(font);
		Shader textShader=new LinearGradient(2, 0, 4, 60,
                new int[]{Color.parseColor("#000000"),Color.parseColor("#000000"),Color.parseColor("#000000")},
                new float[]{0, 3,1}, TileMode.MIRROR);
        txt.getPaint().setShader(textShader);
		Typeface aPro = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView aP = (TextView) findViewById(R.id.aProf);  
		aP.setTypeface(aPro);
		Typeface AMKB = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView aK = (TextView) findViewById(R.id.aMKB);  
		aK.setTypeface(AMKB);
		Typeface aFR = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView aF = (TextView) findViewById(R.id.aFriends);  
		aF.setTypeface(aFR);
		Typeface aLO = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView aL = (TextView) findViewById(R.id.aLogOut);  
		aL.setTypeface(aLO);
		abLogout = (Button) findViewById(R.id.aLogOut);
		abLogout.setOnClickListener(new View.OnClickListener() 
		{
			
			public void onClick(View v) {
				new AlertDialog.Builder(context)
	        	.setMessage(R.string.LOtext)
	               .setPositiveButton(R.string.bye, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   Intent mfLO = new Intent(context, MainMenu.class);
	                	   startActivity(mfLO);
	                	   
	                   }
	               })
	               .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   dialog.cancel();
	                   }
	               })
	               .show();
				
			}
		});

	}
	public void onClick(View v) {
		
		switch (v.getId()){
		case R.id.aProf:
			Intent pP = new Intent(this, UserProfile.class);
			startActivity(pP);
			break;
		case R.id.aMKB:
			Intent pMK = new Intent(this, MyKoinbox.class);
			startActivity(pMK);
			break;
		case R.id.aFriends:
			Intent pFRN = new Intent(this, MyFriends.class);
			startActivity(pFRN);
			break;
		case R.id.aBack:
			Intent pBK = new Intent(this, Home.class);
			startActivity(pBK);
			break;
			
		
		}
	

	}
}
