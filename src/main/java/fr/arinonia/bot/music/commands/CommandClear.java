package fr.arinonia.bot.music.commands;

import fr.ordinalteam.bot.api.commands.Command;
import fr.arinonia.bot.music.Main;
import fr.arinonia.bot.music.music.MusicPlayer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandClear extends Command {

    private final Main main;

    public CommandClear(Main main) {
        super("clear", "description");
        this.main = main;
    }

    @Override
    public void run(MessageReceivedEvent e, String[] args) {
        MusicPlayer player = this.main.getManager().getPlayer(e.getGuild());

        if (player.getListener().getTracks().isEmpty()) {
            e.getChannel().sendMessage("Il n'y a aucune musique en attente").queue();
            return;
        }
        player.getListener().getTracks().clear();
        e.getChannel().sendMessage("La liste d'attente a été supprimée").queue();
    }
}
