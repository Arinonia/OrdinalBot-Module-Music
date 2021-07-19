package fr.arinonia.bot.music.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private final AudioPlayerManager manager = new DefaultAudioPlayerManager();
    private final Map<String, MusicPlayer> players = new HashMap<>();

    public MusicManager() {
        AudioSourceManagers.registerRemoteSources(manager);
        AudioSourceManagers.registerLocalSource(manager);
    }

    public synchronized MusicPlayer getPlayer(Guild guild) {
        if (!this.players.containsKey(guild.getId())) {
            this.players.put(guild.getId(), new MusicPlayer(manager.createPlayer(), guild));
        }
        return this.players.get(guild.getId());
    }

    public void loadTrack(final TextChannel channel, final String source) {
        MusicPlayer player = this.getPlayer(channel.getGuild());
        channel.getGuild().getAudioManager().setSendingHandler(player.getAudioHandler());
        this.manager.loadItemOrdered(player, source, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Ajout de la piste " + track.getInfo().title + ".").queue();
                player.playTrack(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                StringBuilder builder = new StringBuilder();
                builder.append("Ajout de la playlist **").append(playlist.getName()).append("**\n");
                for (AudioTrack track : playlist.getTracks()) {
                    builder.append("\n **->** ").append(track.getInfo().title);
                    player.playTrack(track);
                }
                channel.sendMessage(builder.toString()).queue();
            }

            @Override
            public void noMatches() {
                channel.sendMessage("g pa trouvé frr").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage(exception.getMessage()).queue();
            }
        });
    }
}
