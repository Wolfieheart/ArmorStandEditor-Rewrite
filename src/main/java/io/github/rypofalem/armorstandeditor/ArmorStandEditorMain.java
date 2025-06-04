package io.github.rypofalem.armorstandeditor;

import io.github.rypofalem.armorstandeditor.utils.Debug;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class ArmorStandEditorMain extends JavaPlugin {

    public Debug debugLogging;
    public Logger logger;

    private static final String ASE_HEADER_FIELD = "==== ArmorStandEditor ====";
    private static final String SEPERATOR_FIELD  = "==========================";


    // Configuration file values
    boolean compatabilityCheck;
    boolean debugFlag;


    @Override
    public void onEnable() {
        // Plugin startup logic
        setupLogger();
        compatabilityCheck = checkCompatability();
        if(!compatabilityCheck) return;

        //... Do Load STuff later
        debugFlag = getConfig().getBoolean("debugFlag");
        debugLogging = new Debug(this); //Always Initialize this. Reduces NullPointerExceptions
        if(debugFlag){ enableDebugLogging(); }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public boolean isDebugEnabled() {
        return debugFlag;
    }

    public void enableDebugLogging(){
            debugLogging.logger("ArmorStandEditor Debug Mode is now ENABLED! \n" +
                    "Use this ONLY for testing Purposes. If you can see this and you have debug disabled, " +
                    "please report it as a bug!");
    }

    public void setupLogger(){
        logger = getLogger();
        logger.info(ASE_HEADER_FIELD);
        logger.info("Plugin Version: v" + getVersionString());
        logger.info("Server Platform: " + getServerPlatform() + ", running MC" + getMinecraftVersion());
        logger.info(SEPERATOR_FIELD);
    }
    public boolean checkCompatability(){
        if(getServerPlatform().equals("Spigot")){
            logger.severe("Spigot is no longer supported.");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }

        if (isMinecraftVersionLowerThan("1.17")) {
            logger.severe("ArmorStandEditor is not compatible with this version of Minecraft. Please update to at least version 1.17. Loading failed.");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        } else if (isMinecraftVersionLowerThan("1.21")) {
            logger.warning("ArmorStandEditor is compatible with this version of Minecraft, but it is not the latest supported version.");
            logger.warning("Loading will continue, but please consider updating to the latest version.");
            return true;
        } else {
            logger.info("ArmorStandEditor is compatible with this version of Minecraft. Loading will continue.");
            return true;
        }
    }

    public String getVersionString() {
        return getPluginMeta().getVersion();
    }
    public String getServerPlatform(){
        return getServer().getName();
    }
    public String getMinecraftVersion() { return getServer().getMinecraftVersion();}
    private boolean isMinecraftVersionLowerThan(String requiredVersion) { return compareVersions(getMinecraftVersion(), requiredVersion) < 0;}



    private int compareVersions(String v1, String v2) {
        String[] parts1 = v1.split("\\.");
        String[] parts2 = v2.split("\\.");

        int length = Math.max(parts1.length, parts2.length);
        for (int i = 0; i < length; i++) {
            int p1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            int p2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
            if (p1 != p2) return Integer.compare(p1, p2);
        }
        return 0;
    }

}
