package eu.kreijack.afortune;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class AFortuneConfigure extends Activity {
	protected static final String TAG = "eu.kreijack.afortune.AFortuneConfigure";
	private Spinner		spTimeout;
	private Spinner		spTransparent;
	private Spinner		spTextSize;
	private Spinner		spFGColor;
	private Spinner		spBGColor;
	private Button		btnUpdate;
	private Button		btnAbout;
	private TextView	tvHello;
	final private static int timeouts[] = {
			10*1000,
			30*1000,
			10*60*1000,
			30*60*1000,
			1*60*60*1000,
			6*60*60*1000,
			12*60*60*1000,
			24*60*60*1000
	};
	
	final private static int colors[] ={
		Color.BLACK,
		Color.BLUE,
		Color.CYAN,
		Color.DKGRAY,
		Color.GRAY,
		Color.GREEN,
		Color.LTGRAY,
		Color.MAGENTA,
		Color.RED,
		Color.WHITE,
		Color.YELLOW
	};
	final private int colorDefault = 0;
	
	
	final private static float fontSizes[] = { 70, 85, 100, 115, 130 };
	final private static int fontSizesDefaultId = 2;
	final private static int transparent[] = { 0x00, 0x40, 0x80, 0xc0, 0xff };
	final private static int transparentDefaultId = 0;
	
	private SharedPreferences settings;
	private Button btnListConfig;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
	    settings = getSharedPreferences(Const.SHARED_PREFERENCES, 0);

	    OnItemSelectedListener myOnItemSelectedListener = new OnItemSelectedListener() {
	    	@Override
	        public void onItemSelected(AdapterView<?> parent,
	            View view, int pos, long id) { saveWidgetState(); }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub1
			}
	    };	    
	    spTimeout = (Spinner) findViewById(R.id.spTimeout);
	    spTimeout.setOnItemSelectedListener(myOnItemSelectedListener);

	    spTextSize = (Spinner) findViewById(R.id.spTextSize);
	    spTextSize.setOnItemSelectedListener(myOnItemSelectedListener);

	    spBGColor = (Spinner) findViewById(R.id.spBGColor);
	    spBGColor.setOnItemSelectedListener(myOnItemSelectedListener);
	    
	    spFGColor = (Spinner) findViewById(R.id.spFGColor);
	    spFGColor.setOnItemSelectedListener(myOnItemSelectedListener);
	    
	    spTransparent = (Spinner) findViewById(R.id.spTransparent);
	    spTransparent.setOnItemSelectedListener(myOnItemSelectedListener);
	    
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
    	
    	tvHello = (TextView)findViewById(R.id.tvHello);
    	
	    loadWidgetState();
	}
	
    private void onAbout(){
    	startActivity(new Intent(this, AboutActivity.class));   	
    }
    private void onListConfig(){
    	startActivity(new Intent(this, AFortuneSelectList.class));   	
    }
    boolean near(float value, float target, float errperc){
    	return Math.abs((value-target)/target)<Math.abs(errperc/100.0);
    }
    int fontSize2Spinner(float fontSize){
        float referenceFontSize = tvHello.getTextSize();
        for(int i = 0 ; i < fontSizes.length; i++)
        	if(near(fontSize/referenceFontSize*100, fontSizes[i], 5))
        		return i;

        return fontSizesDefaultId;
    }
    float spinner2FontSize(int spId){
    	float referenceFontSize = tvHello.getTextSize();
    	if(spId<0 || spId >= fontSizes.length)
    		spId = fontSizesDefaultId;
    		
    	return fontSizes[spId]/100*referenceFontSize;
    }
    int spinner2Transparent(int spId){
    	if(spId<0 || spId >= transparent.length)
    		spId = transparentDefaultId;
    	return transparent[spId];
    }
    int transparent2Spinner(int t){
        for(int i = 0 ; i < transparent.length; i++)
        	if(t == transparent[i])
        		return i;
        return transparentDefaultId;   	
    }
    int spinner2Color(int spId){
    	if(spId<0 || spId >= colors.length)
    		spId = colorDefault;
    	return colors[spId];
    }
    int color2Spinner(int t){
        for(int i = 0 ; i < colors.length; i++)
        	if(t == colors[i])
        		return i;
        return colorDefault;   	
    }    
    private void loadWidgetState(){
        int t = settings.getInt("Timeout",timeouts[5]);
        
        int i;
        for( i = 0 ; i < timeouts.length; i++)
        	if(t <= timeouts[i]){
        		spTimeout.setSelection(i);
        		break;
        	}
        if(i>= timeouts.length)
        	spTimeout.setSelection(timeouts.length-1);
        
        spTextSize.setSelection(fontSize2Spinner(settings.getFloat("TextSize", -1)));
        spTransparent.setSelection(transparent2Spinner(settings.getInt("Transparent", -1)));
        spFGColor.setSelection(color2Spinner(settings.getInt("FGColor", -1)));
        spBGColor.setSelection(color2Spinner(settings.getInt("BGColor", -1)));

    }
	private void saveWidgetState(){

        SharedPreferences.Editor editor = settings.edit();
        
    	editor.putInt("Timeout", timeouts[spTimeout.getSelectedItemPosition()]); 
    	editor.putInt("Transparent", spinner2Transparent(spTransparent.getSelectedItemPosition()));
    	editor.putInt("FGColor", spinner2Color(spFGColor.getSelectedItemPosition()));
    	editor.putInt("BGColor", spinner2Color(spBGColor.getSelectedItemPosition()));
    	
    	Log.d(TAG, String.format("FGColor=%d -> 0x%08x",
    			spFGColor.getSelectedItemPosition(),
    			spinner2Color(spFGColor.getSelectedItemPosition()))
    	);
    	
        // Commit the edits!
        editor.commit();    
        
		Intent i = new Intent(Const.UPDATE_TIMEOUT);
		sendBroadcast(i);        
    }    
    
}
