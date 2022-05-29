package me.aroze.parkourthingy;

import jdk.incubator.vector.VectorOperators;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (!(e.getPlayer().hasPermission("parkourthingy.admin"))) return;

        ArrayList<Block> blocksBelow = getNonAirBlocksBelow(e.getPlayer().getLocation());

        if (TestGenerate.parkourNextJump.get(e.getPlayer()).getY() - e.getPlayer().getLocation().getY() >= 0 && TestGenerate.parkourNextJump.get(e.getPlayer()).getY() - e.getPlayer().getLocation().getY() < 0.4) {

            Block block = TestGenerate.parkourNextJump.get(e.getPlayer());
            Location blockMiddle = new Location(e.getPlayer().getWorld(), block.getX() + 0.5, block.getY(), block.getZ() + 0.5);

            if (blockMiddle.distance(e.getPlayer().getLocation()) > 1.52) return;

            block.setType(Material.DIRT);
            TestGenerate.parkourLastJump.put(e.getPlayer(), block);

            Block nextJump = block.getLocation().add(2,0,0).getBlock();
            TestGenerate.parkourNextJump.put(e.getPlayer(), nextJump);
            nextJump.setType(Material.WHITE_CONCRETE);
            Bukkit.broadcastMessage("Next jump: " + nextJump.getX() + " " + nextJump.getY() + " " + nextJump.getZ());

        }

    }

    public ArrayList<Block> getBlocksBelow(Location loc) {

        ArrayList<Block> blocksBelow = new ArrayList<>();

        Location blockBelow = loc.add(0, -1, 0);

        blocksBelow.add(blockBelow.getBlock());
        blocksBelow.add(blockBelow.clone().add(0,0,1).getBlock());
        blocksBelow.add(blockBelow.clone().add(0,0,-1).getBlock());

        blocksBelow.add(blockBelow.clone().add(1,0,1).getBlock());
        blocksBelow.add(blockBelow.clone().add(1,0,-1).getBlock());
        blocksBelow.add(blockBelow.clone().add(1,0,0).getBlock());

        blocksBelow.add(blockBelow.clone().add(-1,0,1).getBlock());
        blocksBelow.add(blockBelow.clone().add(-1,0,-1).getBlock());
        blocksBelow.add(blockBelow.clone().add(-1,0,0).getBlock());

        return blocksBelow;
    }

    public ArrayList<Block> getNonAirBlocksBelow(Location loc) {

        ArrayList<Block> blocksBelow = getBlocksBelow(loc);
        ArrayList<Block> valuesToRemove = new ArrayList<>();

        for (Block block : blocksBelow) {
            if (block.getType() == org.bukkit.Material.AIR) {
                valuesToRemove.add(block);
            }
        }

        blocksBelow.removeAll(valuesToRemove);
        return blocksBelow;
    }


}
