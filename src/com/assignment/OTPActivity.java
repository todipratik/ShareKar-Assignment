package com.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class OTPActivity extends ActionBarActivity {

	private EditText mOtp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String name = Util.getName(getApplicationContext());
		if (name != null) {
			Intent intent = new Intent(OTPActivity.this, Home.class);
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.activity_otp);
		mOtp = (EditText) findViewById(R.id.otp);
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
			String otp = mOtp.getText().toString().trim();
			if (otp.matches(Util.REGEX_OTP)) {
				if (otp.equals("1234")) {
					Intent intent = new Intent(OTPActivity.this,
							SignUpActivity.class);
					startActivity(intent);
					finish();
					return true;
				} else {
					Util.toastText("Please enter correct OTP - 1234",
							getApplicationContext(), Toast.LENGTH_LONG);
					return false;
				}
			} else {
				Util.toastText("Please enter the 4-digit OTP",
						getApplicationContext(), Toast.LENGTH_LONG);
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
