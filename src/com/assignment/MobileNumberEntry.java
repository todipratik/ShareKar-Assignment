package com.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MobileNumberEntry extends ActionBarActivity {

	private EditText mMobile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String name = Util.getName(getApplicationContext());
		if (name != null) {
			Intent intent = new Intent(MobileNumberEntry.this, Home.class);
			startActivity(intent);
			finish();
		}
		String mobile = Util.getMobile(getApplicationContext());
		if (mobile != null) {
			Intent intent = new Intent(MobileNumberEntry.this,
					OTPActivity.class);
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.activity_mobile_number_entry);
		mMobile = (EditText) findViewById(R.id.mobile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_signup) {
			String mobile = mMobile.getText().toString().trim();
			if (mobile.matches(Util.REGEX_PHONE)) {
				Util.saveMobile(getApplicationContext(), mobile);
				Intent intent = new Intent(MobileNumberEntry.this,
						OTPActivity.class);
				startActivity(intent);
				finish();
				return true;
			} else {
				Util.toastText("Please enter your 10-digit mobile number",
						getApplicationContext(), Toast.LENGTH_LONG);
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
