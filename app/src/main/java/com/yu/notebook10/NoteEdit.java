package com.yu.notebook10;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteEdit extends Activity {
	private EditText et_content;
	private SQLiteDatabase dbread;
	public static int ENTER_STATE = 0;
	public static String last_content;
	public static int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit);

		TextView tv_date = findViewById(R.id.tv_date);
		Date date = new Date();
		@SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = sdf.format(date);
		tv_date.setText(dateString);

		et_content = findViewById(R.id.et_content);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		NotesDB DB = new NotesDB(this);
		dbread = DB.getReadableDatabase();

		Bundle myBundle = this.getIntent().getExtras();
		assert myBundle != null;
		last_content = myBundle.getString("info");
		Log.d("LAST_CONTENT", last_content);
		et_content.setText(last_content);
		Button btn_ok = findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String content = et_content.getText().toString();
				Log.d("LOG1", content);
				Date date = new Date();
				@SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dateNum = sdf.format(date);
				String sql;
				String sql_count = "SELECT COUNT(*) FROM note";
				SQLiteStatement statement = dbread.compileStatement(sql_count);
				long count = statement.simpleQueryForLong();
				Log.d("COUNT", count + "");
				Log.d("ENTER_STATE", ENTER_STATE + "");
				if (ENTER_STATE == 0) {
					if (!content.equals("")) {
						sql = "insert into " + NotesDB.TABLE_NAME_NOTES
								+ " values(" + count + "," + "'" + content
								+ "'" + "," + "'" + dateNum + "')";
						Log.d("LOG", sql);
						dbread.execSQL(sql);
					}
				}
				else {
						String updatesql = "update note set content='"
								+ content + "' where _id=" + id;
						dbread.execSQL(updatesql);
						// et_content.setText(last_content);
				}
				Intent data = new Intent();
				setResult(2, data);
				finish();
			}
		});
		Button btn_cancel = findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}
