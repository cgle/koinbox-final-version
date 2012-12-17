package gosuninjas.koinbox.Message;

import gosuninjas.koinbox.Koinbox;
import gosuninjas.koinbox.MainMenu;
import gosuninjas.koinbox.R;
import gosuninjas.koinbox.Core.MyFriends;
import gosuninjas.koinbox.Core.MyKoinbox;
import gosuninjas.koinbox.R.id;
import gosuninjas.koinbox.R.layout;
import gosuninjas.koinbox.R.string;
import gosuninjas.koinbox.User.UserProfile;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * This class allows user to view message in details, including sender, recipient, subject and body.
 * The class is accessed when the user clicks on the subject line of any message he/she sees on his/her inbox or outbox
 * The user has the option to return to home screen or reply to the message.
 * 
 * If reply button is clicked, SendMessage class is called
 * 
 * The class uses 4 static variables view_sender,view_recipient,view_subject,view_body so that every time one wants to 
 * see a message, these 4 variables needed to be updated/assigned accordingly
 * 
 * @author Cuong
 */
public class ViewMessage extends Activity implements OnClickListener {
	public static String view_sender,view_recipient,view_subject,view_body;
	Button reply;
	final Context context = this;
	Button frnProf, frnMKB, frnFriend, frnLogout;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewmessage);
		frnProf = (Button) findViewById(R.id.fProf);
		frnProf.setOnClickListener(this);
		frnMKB = (Button) findViewById(R.id.fMKB);
		frnMKB.setOnClickListener(this);
		frnFriend = (Button) findViewById(R.id.fFriends);
	    frnFriend.setOnClickListener(this);
	    Typeface vmf = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
		TextView vf = (TextView) findViewById(R.id.vmFrom);  
		vf.setTypeface(vmf);
		Typeface vmt = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
		TextView vt = (TextView) findViewById(R.id.vmTo);  
		vt.setTypeface(vmt);
		Typeface vms = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
		TextView vs = (TextView) findViewById(R.id.vmSub);  
		vs.setTypeface(vms);
		Typeface vmc = Typeface.createFromAsset(getAssets(), "font/Impact Label.ttf");
		TextView vc = (TextView) findViewById(R.id.vmCon);  
		vc.setTypeface(vmc);
		Typeface vmr = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView vr = (TextView) findViewById(R.id.reply_message);  
		vr.setTypeface(vmr);
	    Typeface fPro = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView fP = (TextView) findViewById(R.id.fProf);  
		fP.setTypeface(fPro);
		Typeface font = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView txt = (TextView) findViewById(R.id.vmTitle);  
		txt.setTypeface(font);
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
		/**
		 * Since this class is a direct call from Inbox or Outbox class. Recall that Inbox and Outbox class
		 * keeps track of all messages with three array lists. When viewing for a specific message, the value
		 * of recipient, sender, subject and body are taken from the array lists and assigned to the TextView
		 * in this class
		 * 
		 */
		TextView from = (TextView) findViewById(R.id.from_view);
		TextView to = (TextView) findViewById(R.id.to_view);
		TextView subject = (TextView) findViewById(R.id.subject_view);
		TextView body = (TextView) findViewById(R.id.body_view);
		from.setText(view_sender);
		to.setText(view_recipient);
		subject.setText(view_subject);
		body.setText(view_body);
		body.setMaxWidth(10);
		Typeface pAG = Typeface.createFromAsset(getAssets(), "font/Simple tfb.ttf");
		from.setTypeface(pAG);
		to.setTypeface(pAG);
		subject.setTypeface(pAG);
		body.setTypeface(pAG);
		reply = (Button) findViewById(R.id.reply_message);
		reply.setOnClickListener(this);
		TextView back = (TextView) findViewById(R.id.vmBack);
		back.setOnClickListener(this);
		Typeface pbac = Typeface.createFromAsset(getAssets(), "font/deftone_stylus.ttf");
		TextView pba = (TextView) findViewById(R.id.vmBack);  
		pba.setTypeface(pbac);
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.reply_message:
			/**
			 * When reply_message is clicked, Sendmessage static variable recipient and subject is updated
			 * After that, SendMessage class will be called
			 */
			SendMessage.subject="Re: "+view_subject;
			if (view_recipient!=Koinbox.username){
			SendMessage.recipient=view_recipient;
			}
			else{SendMessage.recipient=view_sender;}
			Intent i =new Intent(this, SendMessage.class); 
			startActivity(i);
			break;
		case R.id.vmBack:
			finish();
			break;
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
		}
	}
}
