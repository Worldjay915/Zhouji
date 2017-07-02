# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\zhangshijie\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 压缩质量  0~7
-optimizationpasses 5
#不采用大小写命名的混淆
-dontusemixedcaseclassnames
#不跳过类库例的非公共类
-dontskipnonpubliclibraryclasses
#混淆时是否记录日志（混淆后生产映射文件 map 类名 -> 转化后类名的映射
-verbose
#不跳过类库中的非公共方法
-dontskipnonpubliclibraryclassmembers
# 不做预校验
-dontpreverify
# 不混淆注解和内部类
-keepattributes *Annotation*,InnerClasses
#保持泛型
-keepattributes Signature
#保持源文件以及行号，友盟统计需要加入此行
-keepattributes SourceFile,LineNumberTable
# 混淆算法
-optimizations !code/simplification/cast,!field/*,!class/merging/*
#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

 ## 注册等需要类名，所以不能被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

## v4,v7,v13包
-keep class android.support.** {*;}

# 默认proguard-android文件已混淆，此处重复
-keep class **.R$* {*;}

# 默认proguard-android文件已混淆，此处重复
-keepclasseswithmembernames class * {
    native <methods>;
}
# 默认proguard-android文件已混淆，此处重复
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
# 默认proguard-android文件已混淆，此处重复
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#默认proguard-android文件已混淆，此处重复  添加View的构造方法不能被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 序列化
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements android.os.Parcelable {
 public <fields>;
 private <fields>;
}
# 同上
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#触摸事件
-keepclassmembers class * {
    void *(**On*Event);
}

#webView处理，项目中没有使用到webView忽略即可
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#-keepclassmembers class * extends android.webkit.webViewClient {
#    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
#    public boolean *(android.webkit.WebView, java.lang.String);
#}
#-keepclassmembers class * extends android.webkit.webViewClient {
#    public void *(android.webkit.webView, jav.lang.String);
#}

#############################################
#
# 项目中特殊处理部分
#
#############################################

#-----------处理反射类---------------

#实体类不能被混淆，用到反射的类不能被混淆

#-----------处理js交互---------------

# JS端调用的接口不能被混淆

#-----------处理第三方依赖库---------

# 一半查找三方库的开发文档，或者使用下面方法
#-libraryjars libs/aaa.jar
#-dontwarn com.xx.yy.**
#-keep class com.xx.yy.** { *;}

#Picasso
-dontwarn com.squareup.okhttp.**
#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#bmob混淆
#-ignorewarnings
# keep BmobSDK
-dontwarn cn.bmob.v3.**
-keep class cn.bmob.v3.** {*;}

# 确保JavaBean不被混淆-否则gson将无法将数据解析成具体对象
-keep class * extends cn.bmob.v3.BmobObject {
    *;
}
-keep public class com.shijie.pojo.zhouji.entity.** {
    public void set*(***);
    public *** get*();
    public *** is*();
}

# keep BmobPush
-dontwarn  cn.bmob.push.**
-keep class cn.bmob.push.** {*;}

# keep okhttp3、okio
-dontwarn okhttp3.**
-keep class okhttp3.** { *;}
-keep interface okhttp3.** { *; }
-dontwarn okio.**

# keep rx
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}