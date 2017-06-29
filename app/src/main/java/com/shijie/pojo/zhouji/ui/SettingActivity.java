package com.shijie.pojo.zhouji.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.shijie.pojo.zhouji.DAO.TaskDAO;
import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.database.ImageFactory;
import com.shijie.pojo.zhouji.service.NotificationService;
import com.shijie.pojo.zhouji.utils.DataUtils;
import com.shijie.pojo.zhouji.utils.FileUtils;
import com.shijie.pojo.zhouji.utils.L;
import com.shijie.pojo.zhouji.utils.ShareUtils;
import com.shijie.pojo.zhouji.utils.StaticClass;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/25 15:56
 * 描述:  设置界面
 */

public class SettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {


    @Bind(R.id.sc_open_week)
    SwitchCompat scOpenWeek;
    @Bind(R.id.fl_layout)
    FrameLayout flLayout;
    @Bind(R.id.fl_bg_color)
    FrameLayout flBgColor;
    @Bind(R.id.fl_backup)
    FrameLayout flBackup;
    @Bind(R.id.fl_restore)
    FrameLayout flRestore;
    @Bind(R.id.sc_notification)
    SwitchCompat scNotification;
    @Bind(R.id.layout_container)
    ScrollView layoutContainer;
    @Bind(R.id.cbt)
    CoordinatorLayout cbt;
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.fl_cloud)
    FrameLayout flCloud;
    @Bind(R.id.ll_delete)
    LinearLayout llDelete;

    public int layout = 1;
    public int bgColorPosition;
    private String[] bgColor;
    private LayoutInflater inflater;
    private AlertDialog dialog;
    private FileUtils fileUtils;
    private NotificationService service = new NotificationService();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    dialog.dismiss();
                    Snackbar.make(cbt, "本地备份失败", Snackbar.LENGTH_SHORT).show();
                    break;
                case 0:
                    dialog.dismiss();
                    Snackbar.make(cbt, "本地备份成功", Snackbar.LENGTH_SHORT).show();
                    break;
                case 1:
                    dialog.dismiss();
                    Snackbar.make(cbt, "本地恢复成功", Snackbar.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        scOpenWeek.setOnCheckedChangeListener(this);
        //  scDayOrNight.setOnCheckedChangeListener(this);
        scNotification.setOnCheckedChangeListener(this);

        fileUtils = new FileUtils(this);
        inflater = LayoutInflater.from(this);
        //判断显示本周任务
        boolean openWeek = ShareUtils.getBoolean(this, StaticClass.SWTCH_OPEN_WEEK, false);
        scOpenWeek.setChecked(openWeek);
        //判断显示开启智能提服务
        boolean open_notification = ShareUtils.getBoolean(this, StaticClass.SWTCH_NITIFICATION, true);
        scNotification.setChecked(open_notification);

        bgColor = ImageFactory.createbackgroundStringColor();
        bgColorPosition = ShareUtils.getInt(SettingActivity.this, StaticClass.BGCOLOR, bgColor.length);
        if (bgColorPosition == bgColor.length) {
            layoutContainer.setBackgroundColor(Color.parseColor(bgColor[bgColorPosition - 1]));
        } else {
            layoutContainer.setBackgroundColor(Color.parseColor(bgColor[bgColorPosition]));
        }

    }

    @OnClick({R.id.fl_layout, R.id.fl_bg_color, R.id.fl_backup, R.id.fl_restore, R.id.fl_cloud, R.id.tv_back,R.id.ll_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_layout:
                showLayoutDialog();
                break;
            case R.id.fl_bg_color:
                showBgColorDialog();
                break;
            case R.id.fl_backup:
                new AlertDialog.Builder(this)
                        .setTitle("您要备份数据到本地吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                backUp();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                break;
            case R.id.fl_restore:
                new AlertDialog.Builder(this)
                        .setTitle("您要恢复数据到周记吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                restore();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                break;
            case R.id.fl_cloud:
                new AlertDialog.Builder(this)
                        .setTitle("云端数据备份或恢复")
                        .setPositiveButton("备份", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cloudBackUp();
                            }
                        })
                        .setNegativeButton("恢复", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cloudRestore();
                            }
                        })
                        .setNeutralButton("取消", null).show();
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_delete:
                new AlertDialog.Builder(this)
                        .setTitle("您要删除所有记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteAll();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                break;
        }
    }

    private void deleteAll() {
        TaskDAO taskDAO = new TaskDAO();
        boolean delete = taskDAO.deleteAll();
        if (delete){
            Toast.makeText(this, "数据删除成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "无数据可删除", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 恢复云端文件到本地
     */
    private void cloudRestore() {
        showDialog();
        final String fileUrl = ShareUtils.getString(SettingActivity.this, StaticClass.CLOUD_BACK, null);
        L.i("fileUrl--------" + fileUrl);
        if (fileUrl == null) {
            Toast.makeText(this, "云端无数据备份，请备份", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        dialog.dismiss();
                        downloadFile(fileUrl);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }

    /**
     * 备份文件到Bmob云端
     */
    private void cloudBackUp() {
        showDialog();
        String pathTask = "/data/data/com.shijie.pojo.zhouji/databases/task";
        final BmobFile bmobFile = new BmobFile(new File(pathTask));
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(final BmobException e) {
                if (e == null) {
                    dialog.dismiss();
                    String fileUrl = ShareUtils.getString(SettingActivity.this, StaticClass.CLOUD_BACK, null);
                    if (fileUrl == null) {
                        ShareUtils.putString(SettingActivity.this, StaticClass.CLOUD_BACK, bmobFile.getFileUrl());
                    } else {
                        ShareUtils.deleteOne(SettingActivity.this, StaticClass.CLOUD_BACK);
                        ShareUtils.putString(SettingActivity.this, StaticClass.CLOUD_BACK, bmobFile.getFileUrl());
                    }
                    Toast.makeText(SettingActivity.this, "上传文件成功", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(SettingActivity.this, "上传文件失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 恢复文件
     */
    private void restore() {
        showDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    int flag = fileUtils.dbFileOperation(FileUtils.RESTORE, FileUtils.ISCONTACT);
                    Message message = new Message();
                    message.what = flag;
                    handler.sendMessage(message);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    /**
     * 备份文件到本地
     */
    private void backUp() {
        showDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                    int flag = fileUtils.dbFileOperation(FileUtils.BACKUP, FileUtils.ISCONTACT);
                    Message message = new Message();
                    message.what = flag;
                    handler.sendMessage(message);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    /**
     * 进度条
     */
    private void showDialog() {
        View view = inflater.inflate(R.layout.dialog_loding, null);
        ((TextView) view.findViewById(R.id.progress_msg)).setText("备份中...");
        dialog = new AlertDialog.Builder(this).setView(view).create();
        dialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.sc_open_week:
                if (isChecked) {
                    ShareUtils.putBoolean(this, StaticClass.SWTCH_OPEN_WEEK, true);
                    int weekOfYear_curr = DataUtils.getWeekOfYear();
                    L.i("weekOfYear_curr-----" + weekOfYear_curr);
                    ShareUtils.putInt(this, StaticClass.CURRENT_WEEK, weekOfYear_curr);
                } else if (!isChecked) {
                    ShareUtils.putBoolean(this, StaticClass.SWTCH_OPEN_WEEK, false);
                    ShareUtils.deleteOne(this, StaticClass.CURRENT_WEEK);
                }

                break;
            /*case R.id.sc_dayOrNight:
                if (isChecked) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                    L.i("MODE_NIGHT_YES");
                } else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                    L.i("MODE_NIGHT_NO");
                }
                break;*/
            case R.id.sc_notification:
                if (isChecked) {
                    String isOpenNoti = ShareUtils.getString(this, StaticClass.IS_OPEN_NITIFICATION, "");
                    if (isOpenNoti.equals("OPEN_NITIFICATION")) {
                        ShareUtils.putBoolean(this, StaticClass.SWTCH_NITIFICATION, true);
                        startService(new Intent(this, NotificationService.class));
                    }
                    L.i("startService");
                } else if (!isChecked) {
                    ShareUtils.putString(this,StaticClass.IS_OPEN_NITIFICATION,"OPEN_NITIFICATION");
                    ShareUtils.putBoolean(this, StaticClass.SWTCH_NITIFICATION, false);
                    service.stopSelf();
                    service.cancel();
                    L.i("stopService");
                }
                break;

        }
    }

    private void showLayoutDialog() {
        new AlertDialog.Builder(this).setTitle("布局方式").
                setItems(new String[]{"列表展示", "网格展示"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                if (position == 0) {
                                    layout = StaticClass.LINEARLAYOUT;
                                } else if (position == 1) {
                                    layout = StaticClass.GRIDLAYOUT;
                                }
                                L.i("SettingActivity" + layout);
                                ShareUtils.putInt(SettingActivity.this, StaticClass.LAYOUT, layout);
                            }
                        }).show();

    }

    /**
     * 显示背景颜色对话框
     */
    private void showBgColorDialog() {
        new AlertDialog.Builder(this).setTitle("背景颜色").
                setItems(new String[]{"竹青", "赤金", "铅白", "莹白", "素", "月白",
                                "水红", "苍黄", "丁香色", "艾绿", "玉色", "黄栌", "姜黄"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                switch (position) {
                                    case 0:
                                        bgColorPosition = 0;
                                        break;
                                    case 1:
                                        bgColorPosition = 1;
                                        break;
                                    case 2:
                                        bgColorPosition = 2;
                                        break;
                                    case 3:
                                        bgColorPosition = 3;
                                        break;
                                    case 4:
                                        bgColorPosition = 4;
                                        break;
                                    case 5:
                                        bgColorPosition = 5;
                                        break;
                                    case 6:
                                        bgColorPosition = 6;
                                        break;
                                    case 7:
                                        bgColorPosition = 7;
                                        break;
                                    case 8:
                                        bgColorPosition = 8;
                                        break;
                                    case 9:
                                        bgColorPosition = 9;
                                        break;
                                    case 10:
                                        bgColorPosition = 10;
                                        break;
                                    case 11:
                                        bgColorPosition = 11;
                                        break;
                                    case 12:
                                        bgColorPosition = 12;
                                        break;
                                }
                                ShareUtils.putInt(SettingActivity.this, StaticClass.BGCOLOR, bgColorPosition);
                                layoutContainer.setBackgroundColor(Color.parseColor(bgColor[bgColorPosition]));
                            }
                        }).show();

    }

    /**
     * 用httpUrlConnection下载文件
     *
     * @param urlPath
     */
    private void downloadFile(String urlPath) {
        File backupDir = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.connect();
            int fileLength = httpURLConnection.getContentLength();

            String filePathUrl = "task";

            BufferedInputStream bin = new
                    BufferedInputStream(httpURLConnection.getInputStream());
            backupDir = new File(Environment.getExternalStorageDirectory(),
                    "Backup");
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            L.i("-------" + backupDir.getAbsolutePath());

            OutputStream out = new FileOutputStream(new File(backupDir, filePathUrl));
            int size = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                out.write(buf, 0, size);
            }
            L.i("下载完成");
            out.flush();
            bin.close();
            out.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

