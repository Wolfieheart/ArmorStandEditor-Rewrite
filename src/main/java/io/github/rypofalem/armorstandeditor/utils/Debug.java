package io.github.rypofalem.armorstandeditor.utils;

import io.github.rypofalem.armorstandeditor.ArmorStandEditorMain;

public final class Debug {

    private final ArmorStandEditorMain plugin;

    public Debug(ArmorStandEditorMain plugin){ this.plugin = plugin; }

    public void logger(String msg){
        if(plugin.isDebugEnabled()){
            plugin.getLogger().info("[ArmorStandEditor-Rewrite DEBUG MSG] " + msg);
        }
    }

}
