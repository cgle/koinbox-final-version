package gosuninjas.koinbox.Core;


import gosuninjas.koinbox.Home;
import gosuninjas.koinbox.MainMenu;
import gosuninjas.koinbox.R;
import gosuninjas.koinbox.OtherUser.OtherUserProfile;
import gosuninjas.koinbox.R.id;
import gosuninjas.koinbox.R.layout;
import gosuninjas.koinbox.R.string;
import gosuninjas.koinbox.User.UserProfile;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Shader.TileMode;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * 
  * This is the class that returns the friends.
  * There will be a progressDialog running at the start of the screen till every information is loaded
  * Typeface() method will implement our customized UI to corresponding textviews
	 
 * @author shivangi
 *
 */
public class MyFriends extends Activity implements OnClickListener {
	final Context context = this;
	Button frnProf, frnMKB, frnFriend, frnLogout;
	View fback;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.myfriends);
	frnProf = (Button) findViewById(R.id.fProf);
	frnProf.setOnClickListener(this);
	frnMKB = (Button) findViewById(R.id.fMKB);
	frnMKB.setOnClickListener(this);
	frnFriend = (Button) findViewById(R.id.fFriends);
    frnFriend.setOnClickListener(this);
	fback = (View) findViewById(R.id.fBack);
	fback.setOnClickListener(this);
	Typeface fbac = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView fba = (TextView) findViewById(R.id.fBack);  
	fba.setTypeface(fbac);
	Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView txt = (TextView) findViewById(R.id.fTitle);  
	txt.setTypeface(font);
	Shader textShader=new LinearGradient(2, 0, 4, 60,
            new int[]{Color.parseColor("#ffffff"),Color.parseColor("#ffffff"),Color.parseColor("#ffffff")},
            new float[]{0, 3,1}, TileMode.MIRROR);
    txt.getPaint().setShader(textShader);
	Typeface fPro = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView fP = (TextView) findViewById(R.id.fProf);  
	fP.setTypeface(fPro);
	Typeface FMKB = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView fK = (TextView) findViewById(R.id.fMKB);  
	fK.setTypeface(FMKB);
	Typeface fFR = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView fF = (TextView) findViewById(R.id.fFriends);  
	fF.setTypeface(fFR);
	Typeface fLO = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
	TextView fL = (TextView) findViewById(R.id.fLogOut);  
	fL.setTypeface(fLO);
	frnLogout = (Button) findViewById(R.id.fLogOut);
	frnLogout.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View arg) {
			new AlertDialog.Builder(context)
				.setMessage(R.string.LOtext)
					.setPositiveButton(R.string.bye, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Intent mfLO = new Intent(context, MainMenu.class);
							mfLO.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

	



	
	
	
	final TableLayout layout = (TableLayout) findViewById(R.id.myfriends_layout);
	if (Home.myfriends.size()==0){
		TextView tx1 = new TextView(MyFriends.this);
		tx1.setText("You currently don't have any friend.");
		layout.addView(tx1);
		Typeface k = Typeface.createFromAsset(getAssets(), "font/Simple tfb.ttf");
		tx1.setTypeface(k);
	}
	else{
	TextView tx1 = new TextView(MyFriends.this);
	tx1.setText("*Tap the username to see the profile");
	layout.addView(tx1);
	Typeface kp = Typeface.createFromAsset(getAssets(), "font/Simple tfb.ttf");
	tx1.setTypeface(kp);
	TextView[] tx = new TextView[Home.myfriends.size()];
	
	
	for (int i=0;i<tx.length;i++){
		
		tx[i]=new TextView(MyFriends.this);
		tx[i].setText(Home.myfriends.get(i));
		tx[i].setTextSize(16);
		tx[i].setBackgroundColor(Color.WHITE);
		final String k = Home.myfriends.get(i);
		tx[i].setOnClickListener(new TextView.OnClickListener() {  
			   public void onClick(View v) {
				   OtherUserProfile.other_username=k;
				   
				   Intent i = new Intent(MyFriends.this, OtherUserProfile.class);
				   startActivity(i);
				   
					   
			   }
			   });
		tx[i].setTypeface(kp);
		layout.addView(tx[i]);
	}}
}
	
public void onClick(View v) {
	switch (v.getId()){
	case R.id.fProf:
		Intent fP = new Intent(this, UserProfile.class);
		startActivity(fP);
		break;
	case R.id.fMKB:
		Intent fMK = new Intent(this, MyKoinbox.class);
		startActivity(fMK);
		break;
	case R.id.fFriends:
		Intent fFRN = new Intent(this, MyFriends.class);
		startActivity(fFRN);
		break;
	case R.id.fBack:
		Intent fBK = new Intent(this, Home.class);
		startActivity(fBK);
		break;
		
	
	}}
	public void ShowAlertDialog(String strErrorMessage)
    {
        AlertDialog adMessage = new AlertDialog.Builder(this).create();
        adMessage.setMessage(strErrorMessage);
        adMessage.setButton("Ok", new DialogInterface.OnClickListener() {
            
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adMessage.show();
    }

}
