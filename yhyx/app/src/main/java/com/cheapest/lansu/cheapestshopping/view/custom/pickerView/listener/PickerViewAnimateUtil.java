package com.cheapest.lansu.cheapestshopping.view.custom.pickerView.listener;

import android.view.Gravity;

import com.cheapest.lansu.cheapestshopping.R;


/**
 * Created by Sai on 15/8/9.
 */
public class PickerViewAnimateUtil {
	private static final int INVALID = -1;

	/**
	 * Get default animation resource when not defined by the user
	 *
	 * @param gravity       the gravity of the dialog
	 * @param isInAnimation determine if is in or out animation. true when is is
	 * @return the id of the animation resource
	 */
	public static int getAnimationResource(int gravity, boolean isInAnimation) {
		switch (gravity) {
			case Gravity.BOTTOM:
				return isInAnimation ? R.anim.photo_dialog_in_anim : R.anim.photo_dialog_out_anim;
		}
		return INVALID;
	}
}
