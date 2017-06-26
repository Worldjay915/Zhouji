package com.shijie.pojo.zhouji.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.shijie.pojo.zhouji.DAO.TaskDAO;
import com.shijie.pojo.zhouji.R;
import com.shijie.pojo.zhouji.entity.Task;
import com.shijie.pojo.zhouji.ui.MainActivity;
import com.shijie.pojo.zhouji.utils.DataUtils;
import com.shijie.pojo.zhouji.utils.L;
import com.shijie.pojo.zhouji.utils.ShareUtils;
import com.shijie.pojo.zhouji.utils.StaticClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 智能提醒
 */

public class NotificationService extends Service {


    private String name;
    private int taskCount;
    private Timer timer = null;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw null;
    }

    /**
     * @param intent
     * @param flags  示启动服务的方式
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        TaskDAO taskDAO= new TaskDAO();
        List<Task>  taskList = new ArrayList<>();
        int dayOfWeek = DataUtils.getDayOfWeek();
        L.i("------------"+dayOfWeek);
        int weekOfYear = DataUtils.getWeekOfYear();
        L.i("------------"+weekOfYear);
        name = ShareUtils.getString(this, StaticClass.USERNAME, "");
        int currentWeek = ShareUtils.getInt(this, StaticClass.CURRENT_WEEK, 0);
        if (currentWeek==0){
            taskList = taskDAO.query(dayOfWeek);
        }else if (currentWeek == weekOfYear){
            taskList = taskDAO.query(dayOfWeek, weekOfYear);
        }
        //未完成任务
        taskCount = 0;

        if (taskList.size()>0){
            for (int i=0;i<taskList.size();i++){
                int state = taskList.get(i).getState();
                if (state == StaticClass.STATE_NOT_FINISH) {
                     taskCount ++ ;
                }
            }
        }

        if (taskCount>0){
        //通知用户
            if (timer == null){
                timer = new Timer();
            }
            long period = 1000*60*60;
            long delay = 1000*20;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Intent mainIntent = new Intent(NotificationService.this, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(NotificationService.this,0,mainIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this)
                            .setSmallIcon(R.mipmap.ic_zhou)
                            .setAutoCancel(true)
                            .setContentTitle("你好"+name)
                            .setContentText("您今天还有"+taskCount+"项任务没有完成")
                            .setContentIntent(pendingIntent)
                            .setDefaults(Notification.DEFAULT_ALL);

                    manager.notify(1,builder.build());
                }
            },delay,period);
        }

        return START_STICKY;
    }

    public void cancel(){
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /*
    *onStartComand参数flags含义
    *flags表示启动服务的方式：
    * Additional data about this start request. Currently either 0, START_FLAG_REDELIVERY, or START_FLAG_RETRY.
    * START_FLAG_REDELIVERY：如果你实现onStartCommand()来安排异步工作或者在另一个线程中工作, 那么你可能需要使用START_FLAG_REDELIVERY来让系统重新发送一个intent。这样如果你的服务在处理它的时候被Kill掉, Intent不会丢失.
    *START_FLAG_RETRY：表示服务之前被设为START_STICKY，则会被传入这个标记。
    * onStartComand使用时，返回的是一个(int)整形。
    这个整形可以有四个返回值：start_sticky、start_no_sticky、START_REDELIVER_INTENT、START_STICKY_COMPATIBILITY。
    它们的含义分别是：
    1):START_STICKY： 如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建service，由 于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传 递到service，那么参数Intent将为null。
    2):START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务
    3):START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
    4):START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。  */

}
