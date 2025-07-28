package pizzapal.utils;

import javafx.scene.media.AudioClip;

public class SoundPlayer {

    private static Double volume = 0.33;

    private static AudioClip startupSound = new AudioClip(
            SoundPlayer.class.getResource("/sounds/startup.wav").toString());

    private static AudioClip notificationSound = new AudioClip(
            SoundPlayer.class.getResource("/sounds/notification.mp3").toString());

    public static void playStartupSound() {
        startupSound.setVolume(volume);
        startupSound.play();
    }

    public static void playNotificationSounds() {
        notificationSound.setVolume(volume);
        notificationSound.play();
    }
}
