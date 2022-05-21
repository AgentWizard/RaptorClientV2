package com.codewithritom.raptorclient.api.settingsapi;

import java.util.List;
import com.codewithritom.raptorclient.api.moduleapi.Module;
public class Setting<T> {

    //TODO make string setting in gui

    private  T value,min,max;
    private final String name,description;
    private List<String> modes;
    private final T defaultValue;

    private final Module module;
    public boolean isCombo = false,isSlider=false,isCheckBox = false,isString = false;


    public Setting(String name, String description, T value, Module module) {
        this.value = value;
        this.name = name;
        this.description = description;
        this.defaultValue = value;
        this.module = module;
        if (value instanceof String) isString = true;
        else if (value instanceof Boolean) isCheckBox = true;
        module.addSetting(this);
    }

    public Setting(String name, String description, T value, T min, T max, Module module) {
        this.value = value;
        this.name = name;
        this.description = description;
        this.defaultValue = value;
        this.min = min;
        this.max = max;
        this.module = module;
        isSlider = true;
        module.addSetting(this);
    }

    public Setting(String name, String description, T value, Module module, String... modes) {
        this.value = value;
        this.name = name;
        this.description = description;
        this.defaultValue = value;
        this.module = module;
        this.modes = List.of(modes);
        isCombo = true;
        module.addSetting(this);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getMin() {
        return min;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public List<String> getModes() {
        return modes;
    }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    public Module getModule() {
        return module;
    }
}
