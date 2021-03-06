package com.codewithritom.raptorclient.mixin;

import com.codewithritom.raptorclient.api.managers.ModuleManager;
import com.codewithritom.raptorclient.api.moduleapi.Module;
import net.minecraft.client.Keyboard;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"))
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {
        if (key != GLFW.GLFW_KEY_UNKNOWN) {
            for (Module m: ModuleManager.getModules()) {
                if (key == m.getKeybind()) m.toggle();
            }
        }
    }

}
