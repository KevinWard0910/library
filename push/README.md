        **1，在build文件里添加 **
            compile project(':push')
            
        **2，在Manifest添加**
                <!--JPUSH-->
                <meta-data
                    android:name="JPUSH_CHANNEL"
                    android:value="${JPUSH_CHANNEL}" />
                <meta-data
                    android:name="JPUSH_APPKEY"
                    android:value="${JPUSH_APPKEY}" /> <!--  </>值来自开发者平台取得的AppKey-->
        
                <!--flyme-->
                <meta-data
                    android:name="FLYME_APPID"
                    android:value="${FLYME_APPID}" />
                <meta-data
                    android:name="FLYME_APPKEY"
                    android:value="${FLYME_APPKEY}" />
        
        
                <!--miui-->
                <meta-data
                    android:name="MIUI_APPID"
                    android:value="${MIUI_APPID}" />
                <meta-data
                    android:name="MIUI_APPKEY"
                    android:value="${MIUI_APPKEY}" />
         
                    
                    
        **3，在app的build文件里添加如下配置，并替换为各平台申请的值。**
         defaultConfig {
                manifestPlaceholders = [
                      JPUSH_APPKEY : "极光appkey",
                      JPUSH_CHANNEL: "统计渠道",
                      FLYME_APPID  : "魅族推送appid",
                      FLYME_APPKEY : "魅族推送appkey",
                      MIUI_APPID   : "小米推送appid",
                      MIUI_APPKEY  : "小米推送appkey",
                 ]
         }
         
         
         **4，在application的oncreate方法里添加下列代码，push库将根据当前手机的room自动判断注册的平台**
                HxPush.addPushProvider(new JPushProvider());
                HxPush.addPushProvider(new MiuiPushProvider(this));
                HxPush.addPushProvider(new FlymePushProvider(this));
                HxPush.addPushProvider(new EmuiPushProvider());
                HxPush.register(this);
                
               **注：如果不分平台，只使用极光推送，则只在application里添加一行代码：HxPush.register(this);**
                
          **5，创建自己的receiver并继承HxPushReceiver**
          public class MyReceiver extends HxPushReceiver {
              @Override
              public void onReceivePassThroughMessage(Context context, HxPushMessage message) {
                  System.out.println("MyReceiver onReceivePassThroughMessage:" + JsonUtil.toJsonString(message));
              }
          
              @Override
              public void onNotificationMessageClicked(Context context, HxPushMessage message) {
                  System.out.println("MyReceiver onNotificationMessageClicked:" + JsonUtil.toJsonString(message));
              }
          
              @Override
              public void onNotificationMessageArrived(Context context, HxPushMessage message) {
                  System.out.println("MyReceiver onNotificationMessageArrived:" + JsonUtil.toJsonString(message));
              }
          }
          
          **6，在在Manifest添加注册自己的receiver**
                  <receiver
                      android:name=".MyReceiver"
                      android:exported="true">
                      <intent-filter>
                          <action android:name="com.chinaredstar.RECEIVE_THROUGH_MESSAGE" />
                      </intent-filter>
                      <intent-filter>
                          <action android:name="com.chinaredstar.NOTIFICATION_ARRIVED" />
                      </intent-filter>
                      <intent-filter>
                          <action android:name="com.chinaredstar.NOTIFICATION_CLICKED" />
                      </intent-filter>
                  </receiver>
                  
                  
           **注：透传消息，通知，通知栏被点击这3类action不是所有平台都全部支持。**       