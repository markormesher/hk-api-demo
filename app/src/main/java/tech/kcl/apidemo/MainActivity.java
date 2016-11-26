package tech.kcl.apidemo;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private PermissionHelper perms = new PermissionHelper(this);
	private static String[] PERMISSIONS = {
			Manifest.permission.INTERNET
	};

	private ScrollView outputWrapper;
	private TextView output;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (perms.checkPermissionList(PERMISSIONS)) {
			init();
		} else {
			perms.requestPermissionList(PERMISSIONS);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (perms.checkPermissionRequestResult(requestCode, grantResults)) {
			init();
		} else {
			perms.requestPermissionList(PERMISSIONS);
		}
	}

	private void init() {
		setContentView(R.layout.main_activity);

		// find views
		Button btn1 = (Button) findViewById(R.id.btn_1);
		Button btn2 = (Button) findViewById(R.id.btn_2);
		Button btn3 = (Button) findViewById(R.id.btn_3);
		outputWrapper = (ScrollView) findViewById(R.id.output_wrapper);
		output = (TextView) findViewById(R.id.output);

		// set up click listeners
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				runRequest1();
			}
		});
		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				runRequest2();
			}
		});
		btn3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				runRequest3();
			}
		});

		// let's go!
		printLn("This is the API demo app!");
	}

	private void printLn(final String msg) {
		MainActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				output.setText(output.getText() + "\n" + msg);
				outputWrapper.post(new Runnable() {
					@Override
					public void run() {
						outputWrapper.fullScroll(View.FOCUS_DOWN);
					}
				});
			}
		});
	}

	private void runRequest1() {
		// You'll need this link:
		// http://apidemo.markormesher.co.uk/example-1

		Toast.makeText(MainActivity.this, "Request 1: coming soon!", Toast.LENGTH_SHORT).show();
	}

	private void runRequest2() {
		// You'll need this link:
		// http://api.worldbank.org/countries/?format=json&per_page=300

		Toast.makeText(MainActivity.this, "Request 2: coming soon!", Toast.LENGTH_SHORT).show();
	}

	private void runRequest3() {
		// You'll need this link:
		// http://apidemo.markormesher.co.uk/example-2

		Toast.makeText(MainActivity.this, "Request 3: coming soon!", Toast.LENGTH_SHORT).show();
	}
}
