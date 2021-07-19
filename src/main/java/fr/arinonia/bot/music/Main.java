package fr.arinonia.bot.music;

import fr.ordinalteam.bot.api.plugin.Plugin;
import fr.arinonia.bot.music.commands.CommandClear;
import fr.arinonia.bot.music.commands.CommandPlay;
import fr.arinonia.bot.music.commands.CommandSkip;
import fr.arinonia.bot.music.music.MusicManager;

public class Main extends Plugin {

    private final MusicManager manager = new MusicManager();


    @Override
    public void onEnable() {
        System.out.println("Register du plugin de musique");
        this.getCommandRegistry().registerCommand(new CommandPlay(this), this);
        this.getCommandRegistry().registerCommand(new CommandSkip(this), this);
        this.getCommandRegistry().registerCommand(new CommandClear(this), this);
    }

    public MusicManager getManager() {
        return this.manager;
    }
}
