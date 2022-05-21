package com.codewithritom.raptorclient.api.moduleapi;

import com.codewithritom.raptorclient.api.settingsapi.Setting;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Module {

    protected final String name,description;
    protected final Category category;
    protected boolean enabled = false;

    protected int keybind = GLFW.GLFW_KEY_UNKNOWN;

    private final ArrayList<Setting<?>> settings;

    public Module(String name, String description,Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        settings = new ArrayList<>();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onUpdate() {}

    public void enable() {
        onEnable();
        this.enabled = true;
    }

    public void disable() {
        onDisable();
        this.enabled = false;
    }

    public void toggle() {
        if (enabled) {
            disable();
        }
        else {
            enable();
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public ArrayList<Setting<?>> getSettings() {
        ArrayList<Setting<?>> list = new ArrayList<>(settings.stream().filter(setting -> setting.getModule() == this).collect(Collectors.toList()));
        return list;
    }

    public void addSetting(Setting<?> setting) {
        if (!settings.contains(setting)) settings.add(setting);
    }

}
