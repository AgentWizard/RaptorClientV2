package com.codewithritom.raptorclient.api.managers;

import com.codewithritom.raptorclient.api.moduleapi.Category;
import com.codewithritom.raptorclient.api.moduleapi.Module;
import com.codewithritom.raptorclient.modules.misc.TestModule;
import com.codewithritom.raptorclient.modules.render.ClickGui;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private static final ArrayList<Module> modules = new ArrayList<>();

    public static void addModules() {
        addNewModule(new TestModule());
        addNewModule(new ClickGui());
    }

    public static List<Module> getModulesinCategory(Category c) {
        List<Module> module = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory().equals(c)) {
                module.add(m);
            }
        }
        return module;
    }

    public static void addNewModule(Module m) {
        if (!modules.contains(m))
            modules.add(m);
    }

    public static Module getModule(String name) {
        for (Module m : ModuleManager.getModules()) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Module> T getModule(Class<T> clazz) {
        for (Module m : modules) {
            if (m.getClass().equals(clazz)) {
                return (T) m;
            }
        }
        return null;
    }

    public static List<Module> getModules() {
        return modules;
    }

}
