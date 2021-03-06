package xyz.apollo30.skyblockremastered.events.dragonHandler;

import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.managers.MobManager;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.MobObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CustomEnderDragon extends EntityEnderDragon {

    // Constant Variable
    private Location currentLoc = new Location(getBukkitEntity().getWorld(), 1, 63, -4);
    private double currentLocX = currentLoc.getX();
    private double currentLocY = currentLoc.getY();
    private double currentLocZ = currentLoc.getZ();
    public static boolean targetingPlayer = false;

    // Variables to keep the dragon's path randomized
    private boolean onRandomPath = false;
    private Location destinationLoc = null;
    private long cooldown = 0;

    public static int endCrystals = 0; // Ender Crystals

    public boolean dragonStop = false; // Dragon Stopping
    private final boolean finalBattle = false; // Final Battle
    public boolean isFireball = false; // Fireball
    public boolean isLightningStrike = false; // Lightning Strike

    private final SkyblockRemastered plugin = JavaPlugin.getPlugin(SkyblockRemastered.class);

    /**
     * Calculates for another location
     * @param start lowest x, lowest y, lowest z
     * @param end highest x, highest y, highest z
     * @return new Location inside that cuboid
     */
    private Location getRandomLocation(Location start, Location end) {

        int maxX = Math.max(start.getBlockX(), end.getBlockX());
        int maxY = Math.max(start.getBlockY(), end.getBlockY());
        int maxZ = Math.max(start.getBlockZ(), end.getBlockZ());

        int minX = Math.min(start.getBlockX(), end.getBlockX());
        int minY = Math.min(start.getBlockY(), end.getBlockY());
        int minZ = Math.min(start.getBlockZ(), end.getBlockZ());

        return new Location(start.getWorld(), nextInt(minX, maxX), nextInt(minY, maxY), nextInt(minZ, maxZ));
    }

    private int nextInt(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt(max - min) + max;
    }

    /**
     * Updates the current location of the dragon
     */
    private void updateCurrentLocation() {
        currentLoc = getBukkitEntity().getLocation();
        currentLocX = currentLoc.getX();
        currentLocY = currentLoc.getY();
        currentLocZ = currentLoc.getZ();
    }

    /**
     * Updates the destination of the dragon.
     */
    private void updateLocation() {
        destinationLoc = getRandomLocation(new Location(getBukkitEntity().getWorld(), -24, 9, -33), new Location(getBukkitEntity().getWorld(), 32, 50, 29));
    }

    public CustomEnderDragon(World world) {
        super(world);

        /*
         * This handles the dragon's custom pathfinder,
         * so the dragon won't fly out of the dragons nest.
         * Cooldown of 2-5 Seconds.
         *
         * If the dragon gets out of the dragons nest, it is forced to go back inside to try again.
         */
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getBukkitEntity().isDead()) this.cancel();

                /*
                 * Executes if the dragon has reached its stopping position.
                 * Keeps it in place for 3-7 seconds using triangular distribution as usual.
                 *
                 * Executes if the dragon wants to do a lightning strike.
                 * Keeps it in place for 5 seconds?
                 *
                 * Executes if the dragon wants to face to a random player and fireball them.
                 * 1/2 second interval of each fireball for 8-10 seconds.
                 */
                if (dragonStop && destinationLoc != null || isLightningStrike && destinationLoc != null || isFireball && destinationLoc != null) {
                    setPositionRotation(destinationLoc.getX(), destinationLoc.getY(), destinationLoc.getZ(), destinationLoc.getYaw(), destinationLoc.getPitch());
                }

                /*
                 * After the dragon has reached its path and the cooldown is done
                 * It chooses a random path inside the dragon's nest.
                 */
                if (destinationLoc == null && !onRandomPath && cooldown < new Date().getTime()) {
                    updateLocation();
                    onRandomPath = true;
                } else {

                    /*
                     * Checking if the dragon is out of the dragon's nest
                     * Then it chooses a new location.
                     */
                    if (!Utils.isInZone(getBukkitEntity().getLocation(), new Location(getBukkitEntity().getWorld(), -46, 9, -48), new Location(getBukkitEntity().getWorld(), 54, 50, 38))) {
                        updateLocation();
                        targetingPlayer = false;
                    }
                    /*
                     * If all arguments haven't passed, we shall give them
                     * a random location to force the dragon to fly to against its will.
                     */
                    else if (!targetingPlayer && destinationLoc != null) {

                        double destinationLocX = destinationLoc.getX();
                        double destinationLocY = destinationLoc.getY();
                        double destinationLocZ = destinationLoc.getZ();

                        double dragonSpeed = 0.6;
                        String name = getBukkitEntity().getCustomName();
                        if (!name.isEmpty()) {
                            if (name.toLowerCase().contains("young")) dragonSpeed = .7;
                            else if (name.toLowerCase().contains("old")) dragonSpeed = .55;
                        }

                        currentLocX += (destinationLocX - currentLocX) > 0 ? dragonSpeed : -dragonSpeed;
                        currentLocY += (destinationLocY - currentLocY) > 0 ? dragonSpeed : -dragonSpeed;
                        currentLocZ += (destinationLocZ - currentLocZ) > 0 ? dragonSpeed : -dragonSpeed;

                        /*
                         * Checking if the dragon has already passed its destination
                         */
                        if (currentLocX + 3 >= destinationLocX && currentLocY + 3 >= destinationLocY && currentLocZ + 3 >= destinationLocZ) {
                            destinationLoc = null;
                            currentLoc = getBukkitEntity().getLocation();
                            onRandomPath = false;
                            cooldown = new Date().getTime() + 1000;

                            // A simple variable switch to see if the dragon can do an ability or not.
                        }
                        /*
                         * Else we shall force the dragon against its will
                         * To move to the desired location.
                         */
                        else {
                            Vector vec = getBukkitEntity().getLocation().toVector().subtract(destinationLoc.toVector());
                            Location loc = ((LivingEntity) getBukkitEntity()).getEyeLocation().setDirection(vec);

                            setPositionRotation(currentLocX, currentLocY, currentLocZ, loc.getYaw(), loc.getPitch());
                        }
                    }
                }
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), 0L, 1L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (getBukkitEntity().isDead()) this.cancel();

                /*
                 * Spawning ender crystals around the dragons nest
                 * The little there are, the higher chance
                 * it spawns in the map!
                 */
                if (Math.random() > (endCrystals / 5F)) respawnCrystal();

                /*
                 * Determines whether if the dragon
                 * should stop and do one of the 4 abilities.
                 */

                /*
                 * BUT! We check if the dragon is already dead.
                 * Because the code above doesn't fucking work.
                 */
                LivingEntity entity = (LivingEntity) getBukkitEntity();
                MobObject mo = MobManager.mobObjects.get(entity);
                if (mo.getHealth() <= 0) return;

                if (Math.random() > .5) {
                    if (Math.random() > .75) {
                        /*
                         * Randomly Stopping the Dragon
                         */
                        dragonStop = true;
                        destinationLoc = getBukkitEntity().getLocation();
                        JavaPlugin.getProvidingPlugin(SkyblockRemastered.class).getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), () -> {
                            dragonStop = false;
                            destinationLoc = null;
                        }, (long) (20 * Helper.triangularDistribution(3, 5, 7)));
                    } else if (Math.random() > .75) {
                        /*
                         * Stopping the Dragon to execute lightnings
                         */
                        isLightningStrike = true;
                        destinationLoc = getBukkitEntity().getLocation();
                        JavaPlugin.getProvidingPlugin(SkyblockRemastered.class).getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), () -> {
                            isLightningStrike = false;
                            destinationLoc = null;
                        }, 20 * 5);

                        // Lightning Mechanism
                        for (int i = 0; i < 6; i++) {
                            JavaPlugin.getProvidingPlugin(SkyblockRemastered.class).getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), () -> {
                                if (!getBukkitEntity().isDead()) {
                                    getBukkitEntity().getWorld().strikeLightningEffect(getBukkitEntity().getLocation());
                                }
                            }, 15 * i);
                        }

                        JavaPlugin.getProvidingPlugin(SkyblockRemastered.class).getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), () -> {
                            for (Player plr : getBukkitEntity().getWorld().getPlayers().stream().filter(p -> Utils.isInZone(p.getLocation(), new Location(p.getWorld(), -64, 101, -68), new Location(p.getWorld(), 72, 1, 67))).collect(Collectors.toList())) {
                                int damage = (int) Helper.triangularDistribution(700, 1100, 1500);
                                plr.getWorld().strikeLightningEffect(plr.getLocation());
                                PlayerObject po = PlayerManager.playerObjects.get(plr);
                                po.subtractHealth(damage);
                                if (po.getHealth() <= 0 && !plr.isDead())
                                    Helper.deathHandler(JavaPlugin.getPlugin(SkyblockRemastered.class), plr, "mob", (LivingEntity) getBukkitEntity());
                                if (!getBukkitEntity().getCustomName().isEmpty())
                                    plr.sendMessage(Utils.chat("&5❂ &c" + getBukkitEntity().getCustomName().replaceAll("&[0-9a-zA-z]", "") + " &dused &eLightning Strike &don you for &c" + damage + " damage"));
                            }
                        }, 60L);
                    }
                    // Fireball
                    else if (Math.random() > .75) {
                        List<Fireball> fireballs = new ArrayList<>();
                        Player plr = getBukkitEntity().getWorld().getPlayers().get((int) Math.floor(Math.random() * getBukkitEntity().getWorld().getPlayers().size()));
                        Location plrLoc = plr.getLocation();
                        Vector vec = getBukkitEntity().getLocation().toVector().subtract(plr.getLocation().toVector());
                        BukkitTask task;
                        destinationLoc = ((LivingEntity) getBukkitEntity()).getEyeLocation().setDirection(vec);
                        isFireball = true;

                        int fireballTotal = (int) Helper.triangularDistribution(10, 15, 20);

                        for (int i = 0; i < fireballTotal; i++) {
                            LivingEntity ent = (LivingEntity) getBukkitEntity();

                            Fireball fireball = plr.getWorld().spawn(ent.getLocation().add(0, 10, 0), Fireball.class);
                            fireball.setBounce(false);
                            fireball.setIsIncendiary(false);
                            fireball.setYield(4F);
                            fireballs.add(fireball);
                        }

                        task = new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (Fireball fireball : fireballs) {
                                    Location from = getBukkitEntity().getLocation();
                                    Location to = plrLoc.add(0, 2, 0);
                                    Vector vFrom = from.toVector();
                                    Vector vTo = to.toVector();
                                    Vector direction = vTo.subtract(vFrom).normalize();
                                    fireball.setVelocity(direction);
                                }
                            }
                        }.runTaskTimer(JavaPlugin.getPlugin(SkyblockRemastered.class), 0, 1L);

                        JavaPlugin.getProvidingPlugin(SkyblockRemastered.class).getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), () -> {
                            isFireball = false;
                            task.cancel();
                            fireballs.clear();
                        }, 20 * fireballTotal);

                        // int damage = (int) Helper.triangularDistribution(300, 600, 900);
                    }
                }
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), 0L, 80L);

    }

    public static CustomEnderDragon spawn(Location loc, String name) {
        World mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        final CustomEnderDragon enderDragon = new CustomEnderDragon(mcWorld);

        enderDragon.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) enderDragon.getBukkitEntity()).setRemoveWhenFarAway(true);
        mcWorld.addEntity(enderDragon, CreatureSpawnEvent.SpawnReason.CUSTOM);
        enderDragon.setCustomName(Utils.chat(name));
        enderDragon.setCustomNameVisible(false);
        return enderDragon;
    }

    public Location getRandomLocation(Location entity, Location loc1, Location loc2) {

        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        double curX = entity.getX();
        double curY = entity.getY();
        double curZ = entity.getZ();

        double targetX = randomDouble(minX, maxX);
        double targetY = randomDouble(minY, maxY);
        double targetZ = randomDouble(minZ, maxZ);

        int threshold = 25; // Change this number to change how for away the dragon has to be to let the location be returned.

        while (Math.sqrt(Math.pow(curX - targetX, 2) + Math.pow(curY - targetY, 2) + Math.pow(curZ - targetZ, 2)) < threshold) {
            targetX = randomDouble(minX, maxX);
            targetY = randomDouble(minY, maxY);
            targetZ = randomDouble(minZ, maxZ);
        }

        return new Location(loc1.getWorld(), targetX, targetY, targetZ);
    }

    private void respawnCrystal() {
        if (getBukkitEntity().isDead()) return;
        List<Location> crystalSpawns = new ArrayList<>();
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), -10.5, 44.5, 48.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), -39.5, 69.5, 35.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), -43.5, 44.5, -27.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), 36.5, 51.5, 38.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), 29.5, 36.5, 25.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), -25.5, 35.5, 22.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), 1.5, 58.5, 42));

        Location loc = crystalSpawns.get((int) Math.floor(Math.random() * crystalSpawns.size()));
        for (Entity entity : loc.getChunk().getEntities()) {
            if (entity.getType() == EntityType.ENDER_CRYSTAL) return;
        }
        Entity endCrystal = loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
        DragonEvent.endCrystals.add(endCrystal.getLocation());
        for (Player player : getBukkitEntity().getWorld().getPlayers()) {
            player.sendMessage(Utils.chat("&5❂ &dAn Ender Crystal has respawned!"));
        }
    }

    private static double randomDouble(double min, double max) {
        return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }
}
