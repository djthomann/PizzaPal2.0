package pizzapal.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationManager {

    private static final Logger logger = LoggerFactory.getLogger(NotificationManager.class);

    private static NotificationManager instance;

    private List<String> notifications = new ArrayList<>();

    private final List<NotificationListener> listeners = new ArrayList<>();

    private NotificationManager() {
    }

    public void addListener(NotificationListener l) {
        listeners.add(l);
    }

    public void removeListener(NotificationListener l) {
        listeners.remove(l);
    }

    public void notifyListeners() {
        for (NotificationListener l : listeners) {
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
        notifyListeners();
    }

    public void addNotification(String text) {
        logger.info("Adding Notification: " + text);
        notifications.add(text);
        notifyListeners();
        SoundPlayer.playNotificationSounds();
    }

    public void removeNotification(String text) {
        logger.info("Removing Notification: " + text);
        notifications.remove(text);
        notifyListeners();
    }

    public interface NotificationListener {

        void onNotificationsChanged(List<String> newNotifications);

    }

}
