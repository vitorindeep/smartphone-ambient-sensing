package services.boredomapp;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;

import com.islab.boredomappfase1.MainActivity;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends android.service.notification.NotificationListenerService {

    /*
        These are the package names of the apps. for which we want to
        listen the notifications
     */
    private static final class ApplicationPackageNames {
        public static final String FACEBOOK_PACK_NAME = "com.facebook.katana";
        public static final String FACEBOOK_MESSENGER_PACK_NAME = "com.facebook.orca";
        public static final String WHATSAPP_PACK_NAME = "com.whatsapp";
        public static final String INSTAGRAM_PACK_NAME = "com.instagram.android";
    }

    /*
        These are the return codes we use in the method which intercepts
        the notifications, to decide whether we should do something or not
     */
    public static final class InterceptedNotificationCode {
        public static final int SOCIAL_CODE = 1;
        public static final int CHATTING_CODE = 2;
        public static final int OTHER_NOTIFICATIONS_CODE = 4; // We ignore all notification with code == 4
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }


    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        // match apps to their type (social, chatting, others)
        int notificationCode = matchNotificationCode(sbn);

        switch(notificationCode) {
            // social notification
            case 1:
                MainActivity.socialNotifications++;
                MainActivity.counterNotifications++;
                System.out.println("$$$INFO NOTIFICATIONS: (SOCIAL++) ||| " +
                        "#social: " + MainActivity.socialNotifications + "; " +
                        "#chatting: " + MainActivity.chattingNotifications + "; " +
                        "#others: " +  MainActivity.othersNotifications + "; " +
                        "#counter: " +  MainActivity.othersNotifications + ";");
                break;
            // chatting notification
            case 2:
                MainActivity.chattingNotifications++;
                MainActivity.counterNotifications++;
                System.out.println("$$$INFO NOTIFICATIONS: (CHATTING++) ||| " +
                        "#social: " + MainActivity.socialNotifications + "; " +
                        "#chatting: " + MainActivity.chattingNotifications + "; " +
                        "#others: " +  MainActivity.othersNotifications + "; " +
                        "#counter: " +  MainActivity.othersNotifications + ";");
                break;
            // others notification
            case 4:
                MainActivity.othersNotifications++;
                MainActivity.counterNotifications++;
                System.out.println("$$$INFO NOTIFICATIONS: (OTHERS++) ||| " +
                        "#social: " + MainActivity.socialNotifications + "; " +
                        "#chatting: " + MainActivity.chattingNotifications + "; " +
                        "#others: " +  MainActivity.othersNotifications + "; " +
                        "#counter: " +  MainActivity.othersNotifications + ";");
                break;
            // something wrong
            default:
                System.out.println("Something wrong on notification counting.");
                break;
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){

        MainActivity.counterNotifications--;
        System.out.println("$$$INFO REMOVED NOTIFICATION ||| " +
                "#social: " + MainActivity.socialNotifications + "; " +
                "#chatting: " + MainActivity.chattingNotifications + "; " +
                "#others: " +  MainActivity.othersNotifications + "; " +
                "#counter: " +  MainActivity.othersNotifications + ";");
    }

    private int matchNotificationCode(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();

        // if common social app (facebook, instagram)
        if(packageName.equals(ApplicationPackageNames.FACEBOOK_PACK_NAME)
                || packageName.equals(ApplicationPackageNames.INSTAGRAM_PACK_NAME)){
            return(InterceptedNotificationCode.SOCIAL_CODE);
        }
        // if common communication app (fbmessenger, whatsapp)
        else if(packageName.equals(ApplicationPackageNames.FACEBOOK_MESSENGER_PACK_NAME)
                || packageName.equals(ApplicationPackageNames.WHATSAPP_PACK_NAME)){
            return(InterceptedNotificationCode.CHATTING_CODE);
        }
        // if none of the above
        else{
            return(InterceptedNotificationCode.OTHER_NOTIFICATIONS_CODE);
        }
    }

}