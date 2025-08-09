package pizzapal.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundPlayer {

    private static Double volume = 0.33;

    private static Media mainMenuTheme = new Media(
            SoundPlayer.class.getResource("/sounds/jazz-background-music.mp3").toString());

    private static MediaPlayer mainMenuThemePlayer = new MediaPlayer(mainMenuTheme);

    private static AudioClip startupSound = new AudioClip(
            SoundPlayer.class.getResource("/sounds/startup.wav").toString());

    private static AudioClip notificationSound = new AudioClip(
            SoundPlayer.class.getResource("/sounds/notification.mp3").toString());

    private static AudioClip sweepSound = new AudioClip(
            SoundPlayer.class.getResource("/sounds/broom-sweep.mp3").toString());

    private static AudioClip garbageSound = new AudioClip(
            SoundPlayer.class.getResource("/sounds/garbage-truck.mp3").toString());

    private static AudioClip deleteSound = new AudioClip(
            SoundPlayer.class.getResource("/sounds/breaking-wood.mp3").toString());

    private static AudioClip editSound = new AudioClip(
            SoundPlayer.class.getResource("/sounds/repair-metal.mp3").toString());

    private static AudioClip addSound = new AudioClip(
            SoundPlayer.class.getResource("/sounds/pop-up.mp3").toString());

    public static void stopSound(AudioClip clip) {
        if (clip.isPlaying()) {
            clip.stop();
        }
    }

    public static void playSound(AudioClip clip) {
        if (Config.SOUND_ACTIVE) {
            clip.setVolume(volume);
            clip.play();
        }
    }

    public static void fadeOut(MediaPlayer player, int durationMillis) {
        int steps = 30;
        double volumeStep = player.getVolume() / steps;
        int interval = durationMillis / steps;

        Timeline timeline = new Timeline();
        for (int i = 1; i <= steps; i++) {
            final double vol = player.getVolume() - volumeStep * i;
            KeyFrame kf = new KeyFrame(Duration.millis(i * interval), e -> {
                double newVol = Math.max(vol, 0);
                player.setVolume(newVol);
                if (newVol == 0) {
                    player.stop();
                }
            });
            timeline.getKeyFrames().add(kf);
        }
        timeline.play();
    }

    public static void playMainMenuTheme() {
        if (Config.SOUND_ACTIVE) {
            mainMenuThemePlayer.setVolume(volume);
            mainMenuThemePlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mainMenuThemePlayer.play();
        }

    }

    public static void stopMainMenuTheme() {
        fadeOut(mainMenuThemePlayer, 2500);
    }

    public static void playSweepSound() {
        playSound(sweepSound);
    }

    public static void playStartupSound() {
        playSound(startupSound);
    }

    public static void playNotificationSounds() {
        playSound(notificationSound);
    }

    public static void playGarbageSound() {
        playSound(deleteSound);
    }

    public static void playEditSound() {
        playSound(editSound);
    }

    public static void playAddSound() {
        playSound(addSound);
    }

}
