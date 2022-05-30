package me.aroze.parkourthingy;

import me.aroze.parkourthingy.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static me.aroze.parkourthingy.MoveListener.randInt;

public class TestGenerate implements CommandExecutor {

    public static HashMap<Player, Block> parkourLastJump = new HashMap<>();
    public static HashMap<Player, Block> parkourNextJump = new HashMap<>();
    public static HashMap<Player, Block> parkourNextNext = new HashMap<>();
    public static HashMap<Player, Block> parkourNextNextNext = new HashMap<>();

    public static ArrayList<Player> playingParkour = new ArrayList<>();
    public static HashMap<Player, Integer> parkourJumps = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("parkourthingy.startcommand")) {
            sender.sendMessage(ChatUtils.color("&#ff6e6e⚠ &#ff7f6eYou aren't allowed to do this! smh!"));
            return true;
        }

        Player player = (Player) sender;

        if (playingParkour.contains(player)) {
            sender.sendMessage(ChatUtils.color("&#ff6e6e⚠ &#ff7f6eYou're already playing! smh!"));
            return true;
        }

        playingParkour.add(player);

        Block startingBlock = player.getLocation().add(0, -1, 0).getBlock();
        startingBlock.setType(Material.WHITE_CONCRETE);
        player.sendMessage("Starting block: " + startingBlock.getX() + " " + startingBlock.getY() + " " + startingBlock.getZ());

        parkourNextJump.put(player, startingBlock);
        parkourNextNext.put(player, startingBlock);
        parkourNextNextNext.put(player, startingBlock);

        Block nextJump = startingBlock.getLocation().clone().add(randInt(2,3),randInt(-1,1),randInt(2,3)).getBlock();
        Block nextNextJump = nextJump.getLocation().clone().add(randInt(2,3),randInt(-1,1),randInt(2,3)).getBlock();
        Block nextNextNextJump = nextNextJump.getLocation().clone().add(randInt(2,3),randInt(-1,1),randInt(2,3)).getBlock();

        parkourLastJump.put(player, startingBlock);
        parkourNextJump.put(player, nextJump);
        parkourNextNext.put(player, nextNextJump);
        parkourNextNextNext.put(player, nextNextNextJump);

        nextJump.setType(Material.PINK_CONCRETE);
        nextNextJump.setType(Material.PINK_CONCRETE);
        nextNextNextJump.setType(Material.PINK_CONCRETE);

        return true;
    }
}
