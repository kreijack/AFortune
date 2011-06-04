package eu.kreijack.afortune;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class AFortuneConfigure extends Activity {
	protected static final String TAG = "eu.kreijack.afortune.AFortuneConfigure";
	private Spinner		spTimeout;
	private Button		btnUpdate;
	private Button		btnAbout;
	final private int timeouts[] = {
			10*1000,
			30*1000,
			10*60*1000,
			30*60*1000,
			1*60*60*1000,
			6*60*60*1000,
			12*60*60*1000,
			24*60*60*1000
	};
	private SharedPreferences settings;
	private Button btnListConfig;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    settings = getSharedPreferences(Const.SHARED_PREFERENCES, 0);
	    spTimeout = (Spinner) findViewById(R.id.spTimeout);

	    OnItemSelectedListener myOnItemSelectedListener = new OnItemSelectedListener() {
	    	@Override
	        public void onItemSelected(AdapterView<?> parent,
	            View view, int pos, long id) {
		    		if(pos>=timeouts.length)
		    			pos = timeouts.length-1;
		    		if(pos<0)
		    			pos = 0;
		    		Log.d(TAG,"Pos = "+String.valueOf(pos));
		    		saveWidgetState();
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub1
			}
	    };	    
	    spTimeout.setOnItemSelectedListener(myOnItemSelectedListener);
	    
    	btnAbout = (Button)findViewById(R.id.btnAbout);
    	btnAbout.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick (View v){ onAbout(); }
    	}); 
    	btnUpdate = (Button)findViewById(R.id.btnUpdate);
    	btnUpdate.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick (View v){ 		
    			Intent i = new Intent(Const.UPDATE_TIMEOUT);
    			sendBroadcast(i);  
    		}
    	});
    	btnListConfig = (Button)findViewById(R.id.btEnableFortunes);
    	btnListConfig.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick (View v){ onListConfig(); }
    	});     	
	    loadWidgetState();
	}
	
    private void onAbout(){
    	startActivity(new Intent(this, AboutActivity.class));   	
    }
    private void onListConfig(){
    	startActivity(new Intent(this, AFortuneSelectList.class));   	
    }
    private void loadWidgetState(){
        int t = settings.getInt("Timeout",timeouts[5]);
        
        int i;
        for( i = 0 ; i < timeouts.length; i++)
        	if(t >= timeouts[i]){
        		spTimeout.setSelection(i);
        		break;
        	}
        if(i>= timeouts.length)
        	spTimeout.setSelection(5);
    }
	private void saveWidgetState(){

        SharedPreferences.Editor editor = settings.edit();
        
    	editor.putInt("Timeout", timeouts[spTimeout.getSelectedItemPosition()]); 

        // Commit the edits!
        editor.commit();    
        
		Intent i = new Intent(Const.UPDATE_TIMEOUT);
		sendBroadcast(i);        
    }    
    
}
