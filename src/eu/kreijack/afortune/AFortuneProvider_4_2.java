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
