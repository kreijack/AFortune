package eu.kreijack.afortune;

import android.content.ComponentName;
import android.content.Context;

public class AFortuneProvider_4_3 extends AFortuneProvider {

	public AFortuneProvider_4_3() {
		super(4, 3);
		TAG = "AFortuneProvider_4_3";
	}
    protected ComponentName getComponentName(Context context){
    	return new ComponentName(context, AFortuneProvider_4_3.class);
    }	
}
