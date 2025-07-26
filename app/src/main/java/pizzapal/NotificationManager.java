package pizzapal;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {

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

    public void addNotification(String text) {
        notifications.add(text);
        notifyListeners();
    }

    public void removeNotification(String text) {
        if (notifications.remove(text)) {
            notifyListeners();
        }
    }

    public interface NotificationListener {

        void onNotificationsChanged(List<String> newNotifications);

    }

}
