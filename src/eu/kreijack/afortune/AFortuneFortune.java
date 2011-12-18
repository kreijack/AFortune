/*
    AFortune: show a fortune cookie

    Copyright (C) 2011  Goffredo Baroncelli <kreijack@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package eu.kreijack.afortune;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AFortuneFortune extends Activity {

	private static final String TAG = "AFortuneFortune";
	private Button				btnNext;
	private Button 				btnCopyAll;
	private Button				btnShare;
	private Button				btnSendSMS;
	private TextView			tvFortune;
	private String				fortune;
	private BroadcastReceiver 	eventReceiver;
	private IntentFilter 		intentFilter;
	private AFortuneDB			aFortuneDB;
	private ClipboardManager	clipboardManager;
	private SharedPreferences 	settings;
	private static final String	replaceForSMS[] = {
		"\t", "    "
	};
	
	private void onNext(){
		Intent i = new Intent(Const.UPDATE_TIMEOUT);
		sendBroadcast(i); 		
	}
	private void onShare(){
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		//emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Your mail Subject");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, fortune);
		startActivity(Intent.createChooser(emailIntent, this.getString(R.string.txtSendTo)));		
	}

	private void onSendSMS(){
		
		String f = fortune;
		
		for(int i=0; i < replaceForSMS.length; i+= 2){
			f = f.replaceAll(replaceForSMS[i], replaceForSMS[i+1]);
		}
		
		Intent sendIntent = new Intent(Intent.ACTION_VIEW);
		sendIntent.putExtra("sms_body", f); 
		sendIntent.setType("vnd.android-dir/mms-sms");
		startActivity(sendIntent);  
	}
	
	private void onCopyAll(){
		clipboardManager.setText(fortune);
	}
	private void handleIntent(Intent intent){
		Log.d(TAG, "handleIntent()");
		if (intent == null){
			Log.d(TAG,"Intent == null");
			return;
		}else if(Const.UPDATE_FORTUNE.equals(intent.getAction())){
			refresh();
		}		
	}
	
	private void refresh(){
		fortune = aFortuneDB.getLastFortune();
		float textSize = settings.getFloat("TextSize", -1);
        if(textSize != -1)
        	tvFortune.setTextSize(textSize );
		tvFortune.setText(fortune);
	}
	
	private void onLongClick_(){
		startActivity(new Intent(this, AFortuneConfigure.class)); 
	}
	
    private void initWidget(){
    	btnNext = (Button)findViewById(R.id.btnNext);
    	btnNext.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick (View v){ onNext(); }
    	});
    	btnCopyAll = (Button)findViewById(R.id.btnCopyAll);
    	btnCopyAll.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick (View v){ onCopyAll(); }
    	});
    	btnShare = (Button)findViewById(R.id.btnShare);
    	btnShare.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick (View v){ onShare(); }
    	});
    	btnSendSMS = (Button)findViewById(R.id.btnSendSMS);
    	btnSendSMS.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick (View v){ onSendSMS(); }
    	});
    	tvFortune = (TextView)findViewById(R.id.tvFortuneText);
    	tvFortune.setOnLongClickListener(new TextView.OnLongClickListener() { 
			@Override
			public boolean onLongClick(View arg0) {
				onLongClick_();
				return true;
			}
    	});    	
    }	

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fortune);
        initWidget();
        
		eventReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				handleIntent(intent);
			}
		};

		intentFilter = new IntentFilter();
		intentFilter.addAction(Const.UPDATE_FORTUNE);
		
		//registerReceiver(eventReceiver, intentFilter);
		
		aFortuneDB = AFortuneDB.getAFortuneDB(this);
		
		clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		settings = getSharedPreferences(Const.SHARED_PREFERENCES, 0);
    }
    protected void onResume(){
    	super.onResume();
		// do nothing
		Log.d(TAG, "onResume");
		registerReceiver(eventReceiver, intentFilter);
		refresh();
    }

    protected void onPause(){
    	super.onPause();
		Log.d(TAG, "onResume");
    	unregisterReceiver(eventReceiver);    
    }
    
}
