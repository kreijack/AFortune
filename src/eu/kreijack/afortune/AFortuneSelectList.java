package eu.kreijack.afortune;

import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class AFortuneSelectList extends Activity {
	
	private AFortuneDB aFortuneDB;
	private LinearLayout linearLayout;
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_selection);
        aFortuneDB = AFortuneDB.getAFortuneDB(this);
        linearLayout = (LinearLayout)findViewById(R.id.llFortunesList);
    }
    public void onStart (){
    	super.onStart();
    	initWidget();
    }
    
    private void initWidget(){    	
    	linearLayout.removeAllViews();
    	aFortuneDB.refresh();
    	String	fortunes[] = aFortuneDB.getDBs(); 
    	Set<String> enabled = AFortuneDB.array2set(aFortuneDB.getEnabledDBs());
    	
    	for(int i = 0 ; i < fortunes.length ; i++){
    		CheckBox	cb = new CheckBox(this);

    	    cb.setText(fortunes[i]);
    	    cb.setId(100+i);
    	    cb.setLayoutParams(new LayoutParams(
    	            LayoutParams.FILL_PARENT,
    	            LayoutParams.WRAP_CONTENT));
    	    
    	    class CallOnClickCB implements CheckBox.OnClickListener {
    	    	private String		fortune;

    	    	CallOnClickCB(String fortune_){
    	    		fortune = fortune_;
    	    	}
				@Override
				public void onClick(View v) {
					aFortuneDB.enabledDBs(((CheckBox)v).isChecked(), fortune);
				}
    	    	
    	    }
        	cb.setOnClickListener(new CallOnClickCB(fortunes[i]));
       		cb.setChecked(enabled.contains(fortunes[i]));
       		
    	    linearLayout.addView(cb);   		
    	}
    }

}
