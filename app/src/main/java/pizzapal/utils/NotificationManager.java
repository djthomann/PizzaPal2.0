package pizzapal.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationManager {

    private static final Logger logger = LoggerFactory.getLogger(NotificationManager.class);

    private static NotificationManager instance;

    private List<String> notifications = new ArrayList<>();

    private final List<NewNotificationListener> newListeners = new ArrayList<>();

    private final List<AllNewNotificationListener> listeners = new ArrayList<>();

    private NotificationManager() {
    }

    public void addNewListener(NewNotificationListener l) {
        newListeners.add(l);
    }

    public void removeNewListener(NewNotificationListener l) {
        newListeners.remove(l);
    }

    public void notifyListenersNew(String newNotification) {
        for (NewNotificationListener l : newListeners) {
            l.onNewNotification(newNotification);
        }
    }

    public void addAllNewListener(AllNewNotificationListener l) {
        listeners.add(l);
    }

    public void removeAllNewListener(AllNewNotificationListener l) {
        listeners.remove(l);
    }

    public void notifyListenersAllNew() {
        for (AllNewNotificationListener l : listeners) {
            l.onNotificationsChanged(notifications);
        }
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public void clearNotifications() {
        logger.info("Clearing Notifications");
        notifications.clear();
        notifyListenersAllNew();
    }

    public void addNotification(String text) {
        logger.info("Adding Notification: " + text);
        notifications.add(text);
        notifyListenersNew(text);
        notifyListenersAllNew();
        SoundPlayer.playNotificationSounds();
    }

    public void removeNotification(String text) {
        logger.info("Removing Notification: " + text);
        notifications.remove(text);
        notifyListenersAllNew();
    }

    public interface AllNewNotificationListener {

        void onNotificationsChanged(List<String> newNotifications);

    }

    public interface NewNotificationListener {
        void onNewNotification(String newNotification);
    }

}
