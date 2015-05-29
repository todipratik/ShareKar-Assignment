package com.assignment;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ManualEntry extends ActionBarActivity {

	private EditText mItem;
	private EditText mDescription;
	private Button mSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_entry);
		mItem = (EditText) findViewById(R.id.item);
		mDescription = (EditText) findViewById(R.id.description);
		mSubmit = (Button) findViewById(R.id.submit);
		mSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String item = mItem.getText().toString().trim();
				String description = mDescription.getText().toString().trim();
				if (!item.equals("") && !description.equals("")) {
					// save in database
					DataEntry entry = new DataEntry(getApplicationContext());
					entry.open();
					entry.createEntry(item, description);
					entry.close();
					finish();
				} else {
					Util.toastText("Please enter both item and description",
							getApplicationContext(), Toast.LENGTH_LONG);
				}
			}
		});
	}

}
