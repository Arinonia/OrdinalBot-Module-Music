package fr.arinonia.bot.music.commands;

import fr.ordinalteam.bot.api.commands.Command;
import fr.arinonia.bot.music.Main;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandPlay extends Command {

    private final Main main;

    public CommandPlay(Main main) {
        super("play", "");
        this.main = main;
    }

    @Override
    public void run(MessageReceivedEvent e, String[] args) {
        if (e.getGuild() == null) return;

        if (!e.getGuild().getAudioManager().isConnected() && !e.getGuild().getAudioManager().isAttemptingToConnect()) {
            VoiceChannel voiceChannel = e.getMember().getVoiceState().getChannel();
            if (voiceChannel == null) {
                e.getChannel().sendMessage("t kon va en voc fdp").queue();
                return;
            }
            e.getGuild().getAudioManager().openAudioConnection(voiceChannel);
        }
        this.main.getManager().loadTrack(e.getTextChannel(),args[1]);
    }
}
