package com.codewithritom.raptorclient.api.gui;

import com.codewithritom.raptorclient.api.gui.component.Component;
import com.codewithritom.raptorclient.api.gui.component.Frame;
import com.codewithritom.raptorclient.api.moduleapi.Category;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;

public class RcClickGui extends Screen {

    public static MatrixStack matrix;
    public static ArrayList<Frame> frames;

    public RcClickGui() {
        super(new LiteralText("RaptorClient gui"));
        frames = new ArrayList<Frame>();
        int frameX = 5;
        for(Category category : Category.values()) {
            Frame frame = new Frame(category);
            frame.setX(frameX);
            frames.add(frame);
            frameX += frame.getWidth() + 1;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrix = matrices;
        for(Frame frame : frames) {
            frame.renderFrame(this.textRenderer);
            frame.updatePosition(mouseX, mouseY);
            for(Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(Frame frame : frames) {
            if(frame.isWithinHeader((int) mouseX, (int) mouseY) && button == 0) {
                frame.setDrag(true);
                frame.dragX = (int) (mouseX - frame.getX());
                frame.dragY = (int) (mouseY - frame.getY());
            }
            if(frame.isWithinHeader((int) mouseX, (int) mouseY) && button == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if(frame.isOpen()) {
                if(!frame.getComponents().isEmpty()) {
                    for(Component component : frame.getComponents()) {
                        component.mouseClicked((int) mouseX, (int) mouseY, button);
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Frame frame : frames) {
            if (frame.isOpen() && keyCode != 1) {
                if (!frame.getComponents().isEmpty()) {
                    for (Component component : frame.getComponents()) {
                        component.keyTyped((char) keyCode, keyCode);
                    }
                }
            }
        }
        if (keyCode == 1) {
            assert this.client != null;
            this.client.setScreen(null);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for(Frame frame : frames) {
            frame.setDrag(false);
        }
        for(Frame frame : frames) {
            if(frame.isOpen()) {
                if(!frame.getComponents().isEmpty()) {
                    for(Component component : frame.getComponents()) {
                        component.mouseReleased((int) mouseX, (int) mouseY, button);
                    }
                }
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

}
