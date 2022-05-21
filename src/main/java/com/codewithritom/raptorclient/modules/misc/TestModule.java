package com.codewithritom.raptorclient.modules.misc;

import com.codewithritom.raptorclient.api.moduleapi.Category;
import com.codewithritom.raptorclient.api.moduleapi.Module;
import com.codewithritom.raptorclient.api.settingsapi.Setting;
import org.lwjgl.glfw.GLFW;

public class TestModule extends Module {

    Setting<Double> exampleSetting1 = new Setting<>("ExampleSetting1","A example setting", 10d,1d,100d,this);
    Setting<Boolean> exampleSetting2 = new Setting<>("ExampleSetting1","A example setting", true,this);
    Setting<String> exampleSetting3 = new Setting<>("ExampleSetting1","A example setting", "Value1",this,"Value1","Value2","Value3");

    public TestModule() {
        super("Test Module", "A Test Module", Category.Misc);
        this.keybind = GLFW.GLFW_KEY_RIGHT_CONTROL;
    }

    @Override
    public void onEnable() {
        System.out.println("Enabled");
        super.onEnable();
    }
}
