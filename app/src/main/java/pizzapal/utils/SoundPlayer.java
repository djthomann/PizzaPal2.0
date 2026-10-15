package pizzapal.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public final class SoundPlayer {

    private static final String SOUND_DIR = "/sounds/";
    private static final String[] EXTENSIONS = { ".wav", ".mp3" }; // WAV hat Vorrang

    private static final String THEME = "jazz-background-music";
    private static final String NOTIFICATION = "new-notification";
    private static final String SWEEP = "broom-sweep";
    private static final String DELETE = "breaking-wood";
    private static final String EDIT = "repair-metal";
    private static final String ADD = "pop-up";
    private static final String MOVE = "sliding-lock";

    private static float volume = Config.INITIAL_VOLUME;

    private static final Map<String, AudioClip> clips = new HashMap<>();
    private static final Set<String> failedClips = new HashSet<>();

    private static MediaPlayer themePlayer;
    private static boolean themeFailed = false;
    private static Timeline fadeTimeline;

    private SoundPlayer() {
    }

    // ---------------------------------------------------------------
    // Ressourcen finden
    // ---------------------------------------------------------------

    private static URL findResource(String name) {
        for (String ext : EXTENSIONS) {
            URL url = SoundPlayer.class.getResource(SOUND_DIR + name + ext);
            if (url != null) {
                return url;
            }
        }
        System.err.println("Sounddatei nicht gefunden: " + SOUND_DIR + name);
        return null;
    }

    // ---------------------------------------------------------------
    // Soundeffekte (AudioClip, lazy geladen)
    // ---------------------------------------------------------------

    private static AudioClip getClip(String name) {
        if (failedClips.contains(name)) {
            return null;
        }
        AudioClip clip = clips.get(name);
        if (clip != null) {
            return clip;
        }
        try {
            URL url = findResource(name);
            if (url == null) {
                failedClips.add(name);
                return null;
            }
            clip = new AudioClip(url.toExternalForm());
            clips.put(name, clip);
            return clip;
        } catch (Throwable t) {
            failedClips.add(name);
            System.err.println("Sound '" + name + "' nicht verfügbar: " + t);
            return null;
        }
    }

    private static void playEffect(String name) {
        if (!soundActive()) {
            return;
        }
        AudioClip clip = getClip(name);
        if (clip == null) {
            return;
        }
        try {
            clip.setVolume(volume);
            clip.play();
        } catch (Throwable t) {
            failedClips.add(name);
            clips.remove(name);
            System.err.println("Sound '" + name + "' konnte nicht abgespielt werden: " + t);
        }
    }

    public static void playSound(AudioClip clip) {
        if (clip == null) {
            return;
        }
        try {
            clip.setVolume(volume);
            clip.play();
        } catch (Throwable t) {
            System.err.println("Sound konnte nicht abgespielt werden: " + t);
        }
    }

    public static void stopSound(AudioClip clip) {
        if (clip != null && clip.isPlaying()) {
            clip.stop();
        }
    }

    public static void playSweepSound() {
        playEffect(SWEEP);
    }

    public static void playNotificationSounds() {
        playEffect(NOTIFICATION);
    }

    public static void playGarbageSound() {
        playEffect(DELETE);
    }

    public static void playEditSound() {
        playEffect(EDIT);
    }

    public static void playAddSound() {
        playEffect(ADD);
    }

    public static void playMoveSound() {
        playEffect(MOVE);
    }

    // ---------------------------------------------------------------
    // Hintergrundmusik (MediaPlayer, lazy erzeugt)
    // ---------------------------------------------------------------

    private static MediaPlayer getThemePlayer() {
        if (themeFailed) {
            return null;
        }
        if (themePlayer != null) {
            return themePlayer;
        }
        try {
            URL url = findResource(THEME);
            if (url == null) {
                themeFailed = true;
                return null;
            }
            Media media = new Media(url.toExternalForm());
            media.setOnError(() -> {
                System.err.println("Media-Fehler (Musik): " + media.getError());
                themeFailed = true;
                themePlayer = null;
            });

            MediaPlayer player = new MediaPlayer(media);
            player.setOnError(() -> {
                System.err.println("Player-Fehler (Musik): " + player.getError());
                themeFailed = true;
                themePlayer = null;
            });
            player.setCycleCount(MediaPlayer.INDEFINITE);
            themePlayer = player;
            return player;
        } catch (Throwable t) {
            themeFailed = true;
            System.err.println("Hintergrundmusik nicht verfügbar: " + t);
            return null;
        }
    }

    public static void playMainMenuTheme() {
    if (!soundActive()) {
        return;
    }
    try {
        cancelFade();

        if (themePlayer == null) {
            URL url = SoundPlayer.class.getResource("/sounds/jazz-background-music.wav");
            if (url == null) {
                System.err.println("jazz-background-music.wav nicht im Classpath");
                return;
            }
            Media media = new Media(url.toExternalForm());
            media.setOnError(() -> System.err.println("Media-Fehler: " + media.getError()));

            MediaPlayer player = new MediaPlayer(media);
            player.setOnError(() -> System.err.println("Player-Fehler: " + player.getError()));
            player.setOnReady(() -> System.out.println("Jazz-Sound OK, Dauer: " + media.getDuration()));
            player.setCycleCount(MediaPlayer.INDEFINITE);
            themePlayer = player;
        }

        themePlayer.setVolume(volume);
        themePlayer.play();
    } catch (Throwable t) {
        themePlayer = null;
        System.err.println("Hintergrundmusik konnte nicht gestartet werden: " + t);
    }
}

    public static void stopMainMenuTheme() {
        if (themePlayer != null) {
            fadeOut(themePlayer, 2500);
        }
    }

    // ---------------------------------------------------------------
    // Fade-Out
    // ---------------------------------------------------------------

    private static void cancelFade() {
        if (fadeTimeline != null) {
            fadeTimeline.stop();
            fadeTimeline = null;
        }
    }

    public static void fadeOut(MediaPlayer player, int durationMillis) {
        if (player == null) {
            return;
        }
        cancelFade();

        final int steps = 30;
        final double startVolume = player.getVolume();
        final double volumeStep = startVolume / steps;
        final double interval = (double) durationMillis / steps;

        Timeline timeline = new Timeline();
        for (int i = 1; i <= steps; i++) {
            final double vol = Math.max(startVolume - volumeStep * i, 0);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * interval), e -> {
                player.setVolume(vol);
                if (vol == 0) {
                    player.stop();
                }
            }));
        }
        fadeTimeline = timeline;
        timeline.play();
    }

    // ---------------------------------------------------------------
    // Lautstärke
    // ---------------------------------------------------------------

    public static float getVolume() {
        return volume;
    }

    public static void setVolume(float newVolume) {
        if (newVolume >= 0 && newVolume <= 1) {
            volume = newVolume;
            if (themePlayer != null) {
                try {
                    themePlayer.setVolume(newVolume);
                } catch (Throwable t) {
                    System.err.println("Lautstärke konnte nicht gesetzt werden: " + t);
                }
            }
        }
    }

    public static boolean soundActive() {
        return volume > 0;
    }
}