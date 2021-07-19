package fr.arinonia.bot.music.commands;

import fr.ordinalteam.bot.api.commands.Command;
import fr.arinonia.bot.music.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandSkip extends Command {

    private final Main main;

    public CommandSkip(Main main) {
        super("skip", "description");
        this.main = main;
    }

    @Override
    public void run(MessageReceivedEvent e, String[] args) {
        Guild guild = e.getGuild();
        if (!guild.getAudioManager().isConnected() && !guild.getAudioManager().isAttemptingToConnect()) {
            e.getChannel().sendMessage("nop").queue();
            return;
        }
        this.main.getManager().getPlayer(guild).skipTrack();
        e.getChannel().sendMessage("Allez hop, suivant !").queue();
    }
}
