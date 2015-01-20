package com.timvisee.dungeonmaze.command;

import com.timvisee.dungeonmaze.command.executable.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class CommandManager {

    /** The list of commandDescriptions. */
    // TODO: Make this list sorted!
    private List<CommandDescription> commandDescriptions = new ArrayList<CommandDescription>();

    /**
     * Constructor.
     *
     * @param registerCommands True to register the commands, false otherwise.
     */
    public CommandManager(boolean registerCommands) {
        // Register the commands
        if(registerCommands)
            registerCommands();
    }

    /**
     * Register all commands.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void registerCommands() {
        // Register the base Dungeon Maze command
        CommandDescription dungeonMazeCommand = new CommandDescription(
                new DungeonMazeCommand(),
                new ArrayList<String>() {{
                    add("dungeonmaze");
                    add("dm");
                }},
                "Main command",
                "The main Dungeon Maze command. The root for all the other commands.", null);

        // Register the help command
        CommandDescription helpCommand = new CommandDescription(
                new HelpCommand(),
                new ArrayList<String>() {{
                    add("help");
                    add("hlp");
                    add("h");
                    add("sos");
                    add("?");
                }},
                "View help",
                "View detailed help pages about Dungeon Maze commands.",
                dungeonMazeCommand);
        helpCommand.addArgument(new CommandArgumentDescription("query", "The command or query to view help for.", true));
        helpCommand.setMaximumArguments(false);

        // Register the version command
        CommandDescription versionCommand = new CommandDescription(
                new VersionCommand(),
                new ArrayList<String>() {{
                    add("version");
                    add("ver");
                    add("v");
                    add("about");
                    add("info");
                }},
                "Version info",
                "Show detailed information about the installed Dungeon Maze version, and shows the developers, contributors, license and other information.",
                dungeonMazeCommand);
        versionCommand.setMaximumArguments(false);

        // Register the create command
        CommandDescription createWorldCommand = new CommandDescription(
                new CreateWorldCommand(),
                new ArrayList<String>() {{
                    add("createworld");
                    add("cw");
                }},
                "Create world",
                "Create a new Dungeon Maze world, the name of the world must be unique.",
                dungeonMazeCommand);
        createWorldCommand.addArgument(new CommandArgumentDescription("world", "The name of the world to create.", false));
        createWorldCommand.setCommandPermissions("dungeonmaze.command.createworld", CommandPermissions.DefaultPermission.OP_ONLY);

        // Register the teleport command
        CommandDescription teleportCommand = new CommandDescription(
                new TeleportCommand(),
                new ArrayList<String>() {{
                    add("teleport");
                    add("tp");
                    add("warp");
                    add("goto");
                    add("move");
                }},
                "Teleport to world",
                "Teleports to any another world, such as a Dungeon Maze world." ,
                dungeonMazeCommand);
        teleportCommand.addArgument(new CommandArgumentDescription("world", "The name of the world to teleport to.", false));
        teleportCommand.setCommandPermissions("dungeonmaze.command.teleport", CommandPermissions.DefaultPermission.OP_ONLY);

        // Register the list world command
        CommandDescription listWorldCommand = new CommandDescription(
                new ListWorldCommand(),
                new ArrayList<String>() {{
                    add("listworlds");
                    add("listworld");
                    add("list");
                    add("worlds");
                    add("lw");
                }},
                "List Dungeon Mazes",
                "Lists the available Dungeon Maze worlds and shows some additional information.",
                dungeonMazeCommand);
        listWorldCommand.setCommandPermissions("dungeonmaze.command.listworlds", CommandPermissions.DefaultPermission.OP_ONLY);

        // Register the reload command
        CommandDescription reloadCommand = new CommandDescription(
                new ReloadCommand(),
                new ArrayList<String>() {{
                    add("reload");
                    add("rld");
                    add("r");
                }},
                "Reload plugin",
                "Reload the Dungeon maze plugin.",
                dungeonMazeCommand);
        reloadCommand.setCommandPermissions("dungeonmaze.command.reload", CommandPermissions.DefaultPermission.OP_ONLY);

        // Register the reload permissions command
        CommandDescription reloadPermissionsCommand = new CommandDescription(
                new ReloadPermissionsCommand(),
                new ArrayList<String>() {{
                    add("reloadpermissions");
                    add("reloadpermission");
                    add("reloadperms");
                    add("rp");
                }},
                "Reload permissions",
                "Reload the permissions system and rehook the installed permissions system.",
                dungeonMazeCommand);
        reloadPermissionsCommand.setCommandPermissions("dungeonmaze.command.reloadpermissions", CommandPermissions.DefaultPermission.OP_ONLY);

        // Register the check updates command
        CommandDescription checkUpdatesCommand = new CommandDescription(
                new CheckUpdatesCommand(),
                new ArrayList<String>() {{
                    add("checkupdates");
                    add("checkupdate");
                    add("check");
                    add("updates");
                    add("update");
                    add("cu");
                }},
                "Check updates",
                "Check for available updates to install.",
                dungeonMazeCommand);
        checkUpdatesCommand.setCommandPermissions("dungeonmaze.command.checkupdates", CommandPermissions.DefaultPermission.OP_ONLY);

        // Register the install update command
        CommandDescription installUpdateCommand = new CommandDescription(
                new InstallUpdateCommand(),
                new ArrayList<String>() {{
                    add("installupdates");
                    add("installupdate");
                    add("install");
                    add("iu");
                }},
                "Install update",
                "Try to install any availble update.",
                dungeonMazeCommand);
        installUpdateCommand.setCommandPermissions("dungeonmaze.command.installupdate", CommandPermissions.DefaultPermission.OP_ONLY);

        // Add the base command to the commands array
        this.commandDescriptions.add(dungeonMazeCommand);
    }

    /**
     * Get the list of command descriptions
     *
     * @return List of command descriptions.
     */
    public List<CommandDescription> getCommandDescriptions() {
        return this.commandDescriptions;
    }

    /**
     * Get the number of command description count.
     *
     * @return Command description count.
     */
    public int getCommandDescriptionCount() {
        return this.getCommandDescriptions().size();
    }

    /**
     * Find the best suitable command for the specified reference.
     *
     * @param queryReference The query reference to find a command for.
     *
     * @return The command found, or null.
     */
    public FoundCommandResult findCommand(CommandParts queryReference) {
        // Make sure the command reference is valid
        if(queryReference.getCount() <= 0)
            return null;

        // Get the base command description
        for(CommandDescription commandDescription : this.commandDescriptions) {
            // Check whether there's a command description available for the current command
            if(!commandDescription.isSuitableLabel(queryReference))
                return null;

            // TODO: Handle unknown/similar labels.
            // TODO: Handle references without the base command.

            // Find the command reference, return the result
            return commandDescription.findCommand(queryReference);
        }

        // No applicable command description found, return false
        return null;
    }
}