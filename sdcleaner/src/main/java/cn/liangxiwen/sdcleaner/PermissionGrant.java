package com.scandemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;


public class PermissionGrant {

    public static final int REQ_CODE = 894162;

    private Activity activity;
    private String[] permissions;

    public PermissionGrant(Activity activity) {
        this.activity = activity;
        this.permissions = getSelfDaguersPermission();
    }

    public void requestAllPermission() {
        if (activity == null) {
            return;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            ArrayList<String> notGranted = new ArrayList<>();
            for (String permission : permissions) {
                boolean granted = activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
                if (!granted) {
                    notGranted.add(permission);
                }
            }
            if (activity != null && notGranted.size() > 0) {
                String[] notGrantArr = notGranted.toArray(new String[notGranted.size()]);
                activity.requestPermissions(notGrantArr, REQ_CODE);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (!allGranted()) {
//            LoadingView.showToast("有权限未开通！！！");
        } else {
//            LoadingView.showToast("所有权限都开通");
        }
    }

    /**
     * 是否所有权限都启用
     *
     * @return
     */
    public boolean allGranted() {
        boolean allGranted = true;
        for (String permission : permissions) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                boolean granted = activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
                allGranted = allGranted && granted;
            }
            if (!allGranted) {
                break;
            }
        }
        return allGranted;
    }

    private String[] getSelfDaguersPermission() {
        ArrayList<String> pList = new ArrayList<>();
        String[] permissions = getSelfPermissions();
        for (String dpm : DANGUER) {
            for (String pm : permissions) {
                if (dpm.equals(pm)) {
                    pList.add(dpm);
                }
            }
        }
        String[] dArr = pList.toArray(new String[pList.size()]);
        return dArr;
    }

    private String[] getSelfPermissions() {
        if (activity == null) {
            return new String[]{};
        }
        PackageManager pm = activity.getPackageManager();
        PackageInfo pack = null;
        try {
            pack = pm.getPackageInfo(activity.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
        }
        String[] permissionStrings = pack.requestedPermissions;
        return permissionStrings;
    }

    private static String[] DANGUER = new String[]{
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CALENDAR
//            Manifest.permission.READ_CELL_BROADCASTS
    };
}
