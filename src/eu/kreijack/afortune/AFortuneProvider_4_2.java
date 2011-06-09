package eu.kreijack.afortune;

import android.content.ComponentName;
import android.content.Context;

public class AFortuneProvider_4_2 extends AFortuneProvider {

	public AFortuneProvider_4_2() {
		super(4, 2);
		TAG = "AFortuneProvider_4_2";
	}
    protected ComponentName getComponentName(Context context){
    	return new ComponentName(context, AFortuneProvider_4_2.class);
    }
}
