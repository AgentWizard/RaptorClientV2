package com.codewithritom.raptorclient.modules.render;

import com.codewithritom.raptorclient.RaptorClient;
import com.codewithritom.raptorclient.api.moduleapi.Category;
import com.codewithritom.raptorclient.api.moduleapi.Module;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class ClickGui extends Module {
    public ClickGui() {
        super("ClickGui", "Open the ClickGui", Category.Render);
        this.keybind = GLFW.GLFW_KEY_RIGHT_SHIFT;
    }

    @Override
    public void onEnable() {
        MinecraftClient.getInstance().setScreen(RaptorClient.clickGui);
        disable();
        super.onEnable();
    }
}
