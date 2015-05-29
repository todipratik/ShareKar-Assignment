package com.assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Home extends ActionBarActivity {

	private TextView mWelcomeText;
	private TextView mEmail;
	private TextView mMobile;
	private Button mSearch;
	private Button mCustomEntry;
	private LinearLayout mLinearLayout;
	private TextView mMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mWelcomeText = (TextView) findViewById(R.id.welcome_text);
		mEmail = (TextView) findViewById(R.id.email);
		mMobile = (TextView) findViewById(R.id.mobile);
		mCustomEntry = (Button) findViewById(R.id.add_manually);
		mSearch = (Button) findViewById(R.id.google_search);
		mLinearLayout = (LinearLayout) findViewById(R.id.cards);
		mMessage = (TextView) findViewById(R.id.message);

		mWelcomeText.setText("Welcome, "
				+ Util.getName(getApplicationContext()) + "!");
		mEmail.setText(Util.getEmail(getApplicationContext()));
		mMobile.setText(Util.getMobile(getApplicationContext()));

		mSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Home.this, SearchGoogle.class);
				startActivity(intent);
			}
		});

		mCustomEntry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Home.this, ManualEntry.class);
				startActivity(intent);
			}
		});
	}

	private void populateFromDatabase() {
		DataEntry entry = new DataEntry(getApplicationContext());
		entry.open();
		Cursor cursor = entry.readAll();

		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				String item = cursor.getString(cursor
						.getColumnIndex(DataEntry.KEY_ITEM));
				String description = cursor.getString(cursor
						.getColumnIndex(DataEntry.KEY_DESCRIPTION));
				cursor.moveToNext();

				View view = getLayoutInflater().inflate(R.layout.card_layout,
						null);
				TextView itemTextView = (TextView) view.findViewById(R.id.item);
				TextView descriptionTextView = (TextView) view
						.findViewById(R.id.description);
				descriptionTextView.setText(description);
				itemTextView.setText(item);
				mLinearLayout.addView(view);
			}
			cursor.close();
			return;
		} else {
			mMessage.setText("No entry");
			mMessage.setVisibility(View.VISIBLE);
			cursor.close();
			return;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// remove all views in linear layout
		mLinearLayout.removeAllViews();
		populateFromDatabase();
	}

}