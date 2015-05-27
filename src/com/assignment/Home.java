package com.assignment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends ActionBarActivity {

	private TextView mWelcomeText;
	private EditText mSearchQuery;
	private Button mSearch;
	private LinearLayout mLinearLayout;
	private TextView mMessage;
	private ProgressBar mSearchProgress;

	/*
	 * Google custom search API
	 */
	private static final String API_KEY_FOR_ANDROID = "AIzaSyDiKeXNupNwm9AqZwFkreo_7JEqQG0ge8M";
	private static final String KEY_CX = "016360823908975823092:8n9m_3wnv84";
	private static final String URL = "https://www.googleapis.com/customsearch/v1?key="
			+ API_KEY_FOR_ANDROID + "&cx=" + KEY_CX + "&q=";

	private static String finalURL;

	/*
	 * JSON keys
	 */
	private static final String TAG_KIND = "kind";
	private static final String TAG_ERROR = "error";
	private static final String TAG_SEARCH_INFORMATION = "searchInformation";
	private static final String TAG_TOTAL_RESULTS = "totalResults";
	private static final String TAG_ERROR_MESSAGE = "message";
	private static final String TAG_ITEMS = "items";
	private static final String TAG_SNIPPET = "snippet";
	private static final String TAG_LINK = "link";
	private static final String TAG_TITLE = "title";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mWelcomeText = (TextView) findViewById(R.id.welcome_text);
		mSearchQuery = (EditText) findViewById(R.id.search_query);
		mSearch = (Button) findViewById(R.id.search);
		mLinearLayout = (LinearLayout) findViewById(R.id.cards);
		mMessage = (TextView) findViewById(R.id.message);
		mSearchProgress = (ProgressBar) findViewById(R.id.search_progress);
		mWelcomeText.setText("Welcome, "
				+ Util.getName(getApplicationContext()) + "!");
		mSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// hide the keyboard
				hideSoftKeyboard();
				mMessage.setVisibility(View.GONE);
				String query = mSearchQuery.getText().toString().trim();
				if (query.equals("")) {
					Util.toastText("Please enter your search query",
							getApplicationContext(), Toast.LENGTH_LONG);
				} else {
					// check for Internet connection
					if (isNetworkAvailable()) {
						// encode the query
						try {
							query = URLEncoder.encode(query, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						// remove views of linear layout
						mLinearLayout.removeAllViews();
						// search for the query
						finalURL = URL + query;
						new SearchParseAndDisplay().execute();
					} else {
						// ask user to turn on internet connection
						Util.toastText(
								"Unable to connect. Please check the Internet connection",
								getApplicationContext(), Toast.LENGTH_LONG);
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	class SearchParseAndDisplay extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mSearchProgress.setVisibility(View.VISIBLE);
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = jsonParser.getJSONFromUrl(finalURL);
			return jsonObject;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			mSearchProgress.setVisibility(View.INVISIBLE);
			if (jsonObject != null) {
				JSONArray jsonArray = null;
				if (jsonObject.has(TAG_KIND)) {
					// search is successful
					try {
						String count = jsonObject.getJSONObject(
								TAG_SEARCH_INFORMATION).getString(
								TAG_TOTAL_RESULTS);
						// Util.toastText("Total count is " + count,
						// getApplicationContext(), Toast.LENGTH_LONG);
						Integer results_count = Integer.parseInt(count);
						if (results_count == 0) {
							// no search results
							mMessage.setText("Oops...No results found :(");
							mMessage.setVisibility(View.VISIBLE);
						} else if (results_count <= 3) {
							jsonArray = jsonObject.getJSONArray(TAG_ITEMS);
							// show all the search results
							for (int i = 0; i < results_count; i++) {
								View view = getLayoutInflater().inflate(
										R.layout.card_layout, null);
								TextView snippet = (TextView) view
										.findViewById(R.id.snippet);
								TextView link = (TextView) view
										.findViewById(R.id.link);
								TextView title = (TextView) view
										.findViewById(R.id.title);
								JSONObject object = jsonArray.getJSONObject(i);
								snippet.setText(object.getString(TAG_SNIPPET));
								link.setText(object.getString(TAG_LINK));
								title.setText(object.getString(TAG_TITLE));
								mLinearLayout.addView(view);
							}
						} else {
							jsonArray = jsonObject.getJSONArray(TAG_ITEMS);
							// show first three search results
							for (int i = 0; i < 3; i++) {
								View view = getLayoutInflater().inflate(
										R.layout.card_layout, null);
								TextView snippet = (TextView) view
										.findViewById(R.id.snippet);
								TextView link = (TextView) view
										.findViewById(R.id.link);
								TextView title = (TextView) view
										.findViewById(R.id.title);
								JSONObject object = jsonArray.getJSONObject(i);
								snippet.setText(object.getString(TAG_SNIPPET));
								link.setText(object.getString(TAG_LINK));
								title.setText(object.getString(TAG_TITLE));
								mLinearLayout.addView(view);
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					// search failed
					// show the error message
					try {
						String message = jsonObject.getJSONObject(TAG_ERROR)
								.getString(TAG_ERROR_MESSAGE);
						// Util.toastText(message, getApplicationContext(),
						// Toast.LENGTH_LONG);
						mMessage.setText(message);
						mMessage.setVisibility(View.VISIBLE);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} else {
				mMessage.setText("Unable to connect. Please check the Internet connection");
				mMessage.setVisibility(View.VISIBLE);
			}
		}
	}

	private void hideSoftKeyboard() {
		InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
		mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
				.getWindowToken(), 0);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}