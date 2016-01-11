package com.dhb.qqhook.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dhb.qqhook.Common;
import com.dhb.qqhook.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EditActivity extends Activity {

    private EditText editText;
    private FileReader reader;
    private Button button;
    private static final String TAG = "EditActivity";

    @SuppressLint("HandlerLeak")
    class MyHandle extends Handler {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    setText();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = (EditText) findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setGravity(Gravity.TOP);
        editText.setSingleLine(false);
        editText.setHorizontallyScrolling(false);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyHandle myHandle = new MyHandle();
        myHandle.sendEmptyMessage(0);
//        if (editText.getText().toString().equals("")){
//            button.setVisibility(View.GONE);
//            Log.d(TAG, "button.setVisibility(View.GONE)");
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                showHelpDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String setText() {
        File file = new File(Common.path);
        file.setReadable(true);
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        if (file.exists()) {
            try {
                reader = new FileReader(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader bufferedReader = new BufferedReader(reader);
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuilder.append(s + "\n");
                    editText.setText(stringBuilder);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
        if (editText.getText().toString().equals("")) {
            editText.setHint(R.string.no_login);
        }
        return "";
    }

        private void showDeleteDialog() {
        new AlertDialog.Builder(EditActivity.this).setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_message)
                .setNegativeButton(R.string.dialog_delete_negative_button, null)
                .setPositiveButton(R.string.dialog_delete_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File file = new File(Common.path);
                        boolean b = file.delete();
                        if (b) {
                            Toast.makeText(EditActivity.this, R.string.toast_clean_success,
                                    Toast.LENGTH_SHORT).show();
                            editText.setText("");
                        } else {
                            Toast.makeText(EditActivity.this, R.string.toast_clean_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_help_title)
                .setMessage(getString(R.string.text))
                .setPositiveButton("问题反馈", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse("http://weibo.com/u/2483019873");
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                })
                .show();
    }
}
