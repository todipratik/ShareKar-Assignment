package com.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author pratik
 * 
 */
public class SignUpActivity extends ActionBarActivity {

	private EditText mName;
	private EditText mEmail;
	private EditText mMobile;
	private EditText mPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String name = Util.getName(getApplicationContext());
		if (name != null) {
			Intent intent = new Intent(SignUpActivity.this, Home.class);
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.activity_sign_up);
		mName = (EditText) findViewById(R.id.name);
		mEmail = (EditText) findViewById(R.id.email);
		mMobile = (EditText) findViewById(R.id.mobile);
		mPassword = (EditText) findViewById(R.id.password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_signup) {
			return tryLogin();
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean tryLogin() {
		String name = mName.getText().toString().trim();
		String email = mEmail.getText().toString().trim();
		String mobile = mMobile.getText().toString().trim();
		String password = mPassword.getText().toString().trim();

		if (name.matches(Util.REGEX_NAME) && email.matches(Util.REGEX_EMAIL)
				&& mobile.matches(Util.REGEX_PHONE)
				&& password.matches(Util.REGEX_PASSWORD)) {
			// save the details in SharedPreferences
			Util.save(getApplicationContext(), name, email, mobile, password);
			// move to home activity
			Intent intent = new Intent(SignUpActivity.this, Home.class);
			startActivity(intent);
			finish();
			return true;
		} else {
			if (!name.matches(Util.REGEX_NAME)) {
				Util.toastText("Please enter a valid name",
						getApplicationContext(), Toast.LENGTH_LONG);
			} else if (!email.matches(Util.REGEX_EMAIL)) {
				Util.toastText("Please enter a valid email address",
						getApplicationContext(), Toast.LENGTH_LONG);
			} else if (!mobile.matches(Util.REGEX_PHONE)) {
				Util.toastText("Please enter your 10-digit mobile number",
						getApplicationContext(), Toast.LENGTH_LONG);
			} else if (!password.matches(Util.REGEX_PASSWORD)) {
				Util.toastText(
						"Please enter a password with length between 3 and 9",
						getApplicationContext(), Toast.LENGTH_LONG);
			}
			return false;
		}
	}
}
