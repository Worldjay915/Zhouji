package com.shijie.pojo.zhouji.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 项目名: Zhouji
 * 包名: com.shijie.pojo.zhouji.utils
 * 创建者:  zsj
 * 创建事件: 2017/5/3 9:05
 * 描述:   文件备份与还原
 */

public class FileUtils {

    public static final String BACKUP = "backup";   //请求备份
    public static final String RESTORE = "restore";  //请求备份
    public static final String ISCONTACT = "contact";   //是否拿到数据库
    private Context mContext;

    public FileUtils(Context mContext) {
        this.mContext = mContext;
    }

    /**
     *
     * @param params
     * @return
     */
    public int dbFileOperation(String... params) {
        // 默认路径是 /data/data/(包名)/databases/*.db
        File dbFile = null;      //数据库
        File backupDir = null;   //备份文件夹
        String command1 = params[1];   //表示第二个参数
        if (command1.equals(ISCONTACT)) {
            dbFile = mContext
                    .getDatabasePath("/data/data/com.shijie.pojo.zhouji/databases/task");

            L.i("-------"+dbFile.getAbsolutePath());

            backupDir = new File(Environment.getExternalStorageDirectory(),
                    "Backup");
            L.i("FileUtils----"+Environment.getExternalStorageDirectory()+"----Backup");
        }else{
            System.out.println("没有找到数据库文件");
            return -1;
        }

        if (!backupDir.exists()) {
            backupDir.mkdirs();   //创建备份文件
        }

        L.i("备份文件的名字是："+dbFile.getName());
        L.i("备份文件夹是=====："+backupDir.getName());

        File backup = new File(backupDir, dbFile.getName());
        String command2 = params[0];//传递第一个参数
        //判断是否为调用备份数据的方法
        if (command2.equals(BACKUP)) {
            try {
                if (command1.equals(ISCONTACT)) {
                    backup.createNewFile();
                    fileCopy(dbFile, backup);
                    return 0;
                } else {
                    backup.createNewFile();// 最后再把数据库备份到SD卡上
                    fileCopy(dbFile, backup);
                    return 3;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("备份抛出异常");
                return -1;
            }
        } else if (command2.equals(RESTORE)) {
            try {
                if (command1.equals(ISCONTACT)) {
                    fileCopy(backup, dbFile);
                    return 1;
                } else {
                    fileCopy(backup, dbFile);// 先把数据库从SD卡上恢复到我的数据库
                    return 4;
                }
            } catch (Exception e) {
                System.out.println("还原抛出异常");
                e.printStackTrace();
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     *
     * @param dbFile  读取数据库输入流
     * @param backup  copy数据库到backup
     * @throws IOException
     */
    private void fileCopy(File dbFile, File backup) throws IOException {
        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(backup).getChannel(); // copy数据库到backup
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }


}
