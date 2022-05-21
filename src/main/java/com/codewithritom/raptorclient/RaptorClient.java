package com.codewithritom.raptorclient;

import com.codewithritom.raptorclient.api.gui.RcClickGui;
import com.codewithritom.raptorclient.api.managers.ModuleManager;
import net.fabricmc.api.ModInitializer;

public class RaptorClient implements ModInitializer {

    public static RcClickGui clickGui;
    @Override
    public void onInitialize() {
        ModuleManager.addModules();
        clickGui = new RcClickGui();
    }
}
