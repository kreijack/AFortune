package eu.kreijack.afortune;

import android.content.ComponentName;
import android.content.Context;

public class AFortuneProvider_4_4 extends AFortuneProvider {

	public AFortuneProvider_4_4() {
		super(4, 4);
		TAG = "AFortuneProvider_4_4";
	}
    protected ComponentName getComponentName(Context context){
    	return new ComponentName(context, AFortuneProvider_4_4.class);
    }
}