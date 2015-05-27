package com.assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Class containing the utility functions and variables used in the application
 * 
 * @author pratik
 * 
 */
public class Util {

	/**
	 * Regex for name supporting Unicode characters For ex: Pratik Todi, Prátik
	 * Todi
	 */
	public static final String REGEX_NAME = "^[\\p{L} .'-]+$";

	/**
	 * Regex for 10-digit mobile number
	 */
	public static final String REGEX_PHONE = "^[0-9]{10}$";

	/**
	 * Regex for standard email-id
	 */
	public static final String REGEX_EMAIL = "\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b";

	/**
	 * Regex for password with length between 3 and 9
	 */
	public static final String REGEX_PASSWORD = "^.{4,8}$";

	/*
	 * keys for storing login data in shared preferences
	 */
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_MOBILE = "mobile";
	public static final String KEY_PASSWORD = "password";

	/*
	 * Shared Preference file name
	 */
	public static final String PREFERENCE_NAME = "Settings";

	/**
	 * Toast text on the screen
	 * 
	 * @param text
	 *            The text to be displayed on the screen
	 * @param context
	 *            Context of the application where text is to be displayed
	 * @param length
	 *            Duration for which the text is to displayed -
	 *            Toast.LENGTH_LONG or Toast.LENGTH_SHORT
	 */
	public static void toastText(String text, Context context, Integer length) {
		Toast.makeText(context, text, length).show();
		return;
	}

	private static SharedPreferences getSharedPrefs(Context context) {
		return (context).getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * Saves the login data to SharedPreferences
	 * 
	 * @param context
	 *            Activity context from which function is called
	 * @param name
	 *            Name of the user
	 * @param email
	 *            Email of the user
	 * @param mobile
	 *            Phone of the user
	 * @param password
	 *            Password of the user
	 */
	public static void save(Context context, String name, String email,
			String mobile, String password) {
		SharedPreferences.Editor editor = getSharedPrefs(context).edit();
		editor.putString(KEY_NAME, name);
		editor.putString(KEY_EMAIL, email);
		editor.putString(KEY_MOBILE, mobile);
		editor.putString(KEY_PASSWORD, password);
		editor.commit();
	}

	/**
	 * Retrieve name of the user
	 * 
	 * @param context
	 *            Activity context from which function is called
	 * @return name of the user
	 */
	public static String getName(Context context) {
		return getSharedPrefs(context).getString(KEY_NAME, null);
	}
	
	/**
	 * Retrieve email-id of the user
	 * 
	 * @param context
	 *            Activity context from which function is called
	 * @return email-id of the user
	 */
	public static String getEmail(Context context) {
		return getSharedPrefs(context).getString(KEY_EMAIL, null);
	}

	/**
	 * Retrieve mobile number of the user
	 * 
	 * @param context
	 *            Activity context from which function is called
	 * @return mobile number of the user
	 */
	public static String getMobile(Context context) {
		return getSharedPrefs(context).getString(KEY_MOBILE, null);
	}
}
