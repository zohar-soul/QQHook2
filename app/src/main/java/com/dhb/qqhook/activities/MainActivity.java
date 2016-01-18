package com.dhb.qqhook.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dhb.qqhook.Adapter.QqListViewAdapter;
import com.dhb.qqhook.Common;
import com.dhb.qqhook.R;
import com.dhb.qqhook.util.FieldHelper;
import com.dhb.qqhook.util.QqInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    private ListView listView;
    private static final String TAG = MainActivity.class.getName();
    private static final String SHARED_PREFERENCE_NAME = "qq_hook";
    private SharedPreferences preferences;
    private static final String IS_AGREE = "isAgree";
    private List<QqInfo> qqInfos;

//    @SuppressLint("HandlerLeak")
//    class MyHandle extends Handler {
//        /**
//         * Subclasses must implement this to receive messages.
//         *
//         * @param msg
//         */
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
////                    setText();
//                    break;
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.qqList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int finalPosition = position;
                new AlertDialog.Builder(MainActivity.this)
                        .setItems(R.array.copy, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        Common.copy(qqInfos.get(finalPosition).getQq().substring(3),MainActivity.this);
                                        break;
                                    case 1:
                                        Common.copy(qqInfos.get(finalPosition).getKey().substring(3),MainActivity.this);
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });
        preferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);

        if (!isMod()) {
            showIsModDialog();
        }

        File file = new File(Common.path);
        Log.d(TAG, file.canWrite()+""+file.canRead());
        if (!file.exists()){
            Toast.makeText(this, R.string.toast_no,Toast.LENGTH_LONG).show();
        }
        if (!Common.checkSdCardIsCanWrite()) {
            new AlertDialog.Builder(this).setMessage(R.string.dialog_sd_message).show();
        }
    }

    private List<Map<String, Object>> getItems() {
        List<Map<String, Object>> listItems = new ArrayList<>();
        qqInfos = new FieldHelper().getQqInfoList();
        for (int i = 0; i < qqInfos.size(); i++) {
            Log.d(TAG, qqInfos.get(i).getQq());
            Log.d(TAG, qqInfos.get(i).getKey());
            Map<String, Object> map = new HashMap<>();
            map.put("QqText", qqInfos.get(i).getQq());
            map.put("QqKeyText", qqInfos.get(i).getKey());
            listItems.add(map);
        }
        return listItems;
    }

    @Override
    protected void onResume() {
        super.onResume();
        QqListViewAdapter adapter = new QqListViewAdapter(this, getItems());
        listView.setAdapter(adapter);
        if (!preferences.getBoolean(IS_AGREE, false)) {
            showWarmingDialog();
        }
//        if(listView.getCount()== 0){
//            Toast.makeText(this, R.string.toast_no,Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent startActivityIntent = new Intent(this, AboutActivity.class);
                startActivity(startActivityIntent);
                break;
            case R.id.warming:
                showWarmingDialog();
                break;
            case R.id.text:
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showWarmingDialog() {
        AlertDialog warmingDialog = new AlertDialog.Builder(this).setTitle(R.string.dialog_warming_title)
                .setMessage(R.string.dialog_warming_message)
                .setPositiveButton(R.string.dialog_warming_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putBoolean(IS_AGREE, true);
                        edit.apply();
                    }
                })
                .setNegativeButton(R.string.dialog_warming_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putBoolean(IS_AGREE, false);
                        edit.apply();
                        MainActivity.this.finish();
                    }
                }).create();
        warmingDialog.setCanceledOnTouchOutside(false);
        warmingDialog.setCancelable(false);
        warmingDialog.show();
    }

    private void showIsModDialog() {
        AlertDialog  modDialog = new AlertDialog.Builder(this).setTitle(R.string.dialog_is_mod_title)
                .setMessage(R.string.dialog_is_mod_message)
                .setPositiveButton(R.string.dialog_is_mod_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                }).create();
        modDialog.setCanceledOnTouchOutside(false);
        modDialog.setCancelable(false);
        modDialog.show();
    }

    /**
     * 只能通过Xposed更改，验证是否生效
     */
    private static boolean isMod() {
        return false;
    }

}
