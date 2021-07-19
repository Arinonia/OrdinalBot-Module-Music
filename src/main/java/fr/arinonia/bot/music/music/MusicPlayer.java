package fr.arinonia.bot.music.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;

public class MusicPlayer {

    private final AudioPlayer audioPlayer;
    private final AudioListener listener;
    private final Guild guild;

    public MusicPlayer(AudioPlayer audioPlayer, Guild guild) {
        this.audioPlayer = audioPlayer;
        this.guild = guild;
        this.listener = new AudioListener(this);
        this.audioPlayer.addListener(this.listener);
    }

    public synchronized void playTrack(AudioTrack track) {
        this.listener.queue(track);
    }
    public synchronized void skipTrack() {
        this.listener.nextTrack();
    }

    public AudioPlayer getAudioPlayer() {
        return this.audioPlayer;
    }

    public AudioListener getListener() {
        return this.listener;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public AudioHandler getAudioHandler() {
        return new AudioHandler(this.audioPlayer);
    }
}
