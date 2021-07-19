package fr.arinonia.bot.music.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioListener extends AudioEventAdapter {

    private final BlockingQueue<AudioTrack> tracks = new LinkedBlockingQueue<>();
    private final MusicPlayer player;

    public AudioListener(MusicPlayer player) {
        this.player = player;
    }

    public void nextTrack() {
        if (this.tracks.isEmpty()) {
            if (this.player.getGuild().getAudioManager().getConnectedChannel() != null) {
                this.player.getGuild().getAudioManager().closeAudioConnection();
            }
            return;
        }
        this.player.getAudioPlayer().startTrack(this.tracks.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) this.nextTrack();
    }

    public void queue(AudioTrack track) {
        if (!this.player.getAudioPlayer().startTrack(track, true)) this.tracks.offer(track);
    }

    public BlockingQueue<AudioTrack> getTracks() {
        return tracks;
    }

    public MusicPlayer getPlayer() {
        return this.player;
    }
}
