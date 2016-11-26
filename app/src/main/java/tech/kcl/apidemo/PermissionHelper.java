package tech.kcl.apidemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionHelper {

	private Activity activity;
	private static int PERMISSION_REQUEST_CODE = 1415;

	public PermissionHelper(Activity activity) {
		this.activity = activity;
	}

	private boolean shouldUseRuntimePermissions() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
	}

	private boolean hasPermission(Context context, String permission) {
		return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
	}

	public boolean checkPermissionList(String[] permissions) {
		if (!shouldUseRuntimePermissions()) return true;
		for (String perm : permissions) {
			if (!hasPermission(activity, perm)) return false;
		}
		return true;
	}

	public void requestPermissionList(final String[] permissions) {
		requestPermissionList(permissions, false);
	}

	public void requestPermissionList(final String[] permissions, boolean reRequest) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
		alertBuilder.setTitle(R.string.permissions_request_title);
		if (reRequest) {
			alertBuilder.setMessage(R.string.permissions_request_failure);
			alertBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							doRequestPermissionList(permissions);
						}
					}
			);
			alertBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							activity.finish();
						}
					}
			);
		} else {
			alertBuilder.setMessage(R.string.permissions_request_primer);
			alertBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							doRequestPermissionList(permissions);
						}
					}
			);
		}
		alertBuilder.create().show();
	}

	private void doRequestPermissionList(String[] permissions) {
		ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
	}

	public boolean checkPermissionRequestResult(int requestCode, int[] grantResults) {
		if (requestCode != PERMISSION_REQUEST_CODE) return false;
		if (grantResults.length == 0) return false;
		for (int result : grantResults) {
			if (result != PackageManager.PERMISSION_GRANTED) return false;
		}
		return true;
	}

}
