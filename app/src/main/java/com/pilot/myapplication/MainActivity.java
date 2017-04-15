package com.pilot.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tuacy.logger.DebugLogger;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

	private Button mButtonString;
	private Button mButtonIntent;
	private Button mButtonBundle;
	private Button mButtonMap;
	private Button mButtonReerence;
	private Button mButtonCollection;
	private Button mButtonThrowable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
	}

	private void initView() {
		mButtonString = (Button) findViewById(R.id.button_log_normal);
		mButtonIntent = (Button) findViewById(R.id.button_log_intent);
		mButtonBundle = (Button) findViewById(R.id.button_log_bundle);
		mButtonMap = (Button) findViewById(R.id.button_log_map);
		mButtonReerence = (Button) findViewById(R.id.button_log_reference);
		mButtonCollection = (Button) findViewById(R.id.button_log_collection);
		mButtonThrowable = (Button) findViewById(R.id.button_log_throwable);
	}

	private void initEvent() {
		mButtonString.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logNormal();
			}
		});
		mButtonIntent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logIntent();
			}
		});
		mButtonBundle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logBundle();
			}
		});
		  mButtonMap.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  logMap();
			  }
		  });
		  mButtonReerence.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  logReference();
			  }
		  });
		  mButtonCollection.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  logCollection();
			  }
		  });
		  mButtonThrowable.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  logThrowable();
			  }
		  });
	}

	private void logNormal() {
		DebugLogger.d("tuacy", 12.0f);
	}

	private void logIntent() {
		Intent intent = new Intent();
		intent.putExtra("key1", 123);
		intent.putExtra("key2", "string");
		DebugLogger.d("tuacy", intent);
	}

	private void logBundle() {
		Bundle bundle = new Bundle();
		bundle.putInt("key1", 123);
		bundle.putString("key2", "string");
		DebugLogger.d("tuacy", bundle);
	}

	private void logCollection() {
		Collection<String> collection = new HashSet<String>();
		collection.add("java");
		collection.add("cpp");
		collection.add("php");
		collection.add("c#");
		collection.add("objective-c");
		DebugLogger.d("tuacy", collection);
	}

	private void logMap() {
		Map<String,String> map = new HashMap<>();
		map.put("a", "123");
		map.put("b", "456");
		DebugLogger.d("tuacy", map);
	}

	private void logReference() {

	}

	private void logThrowable() {

	}
}
