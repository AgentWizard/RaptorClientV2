package com.codewithritom.raptorclient.api.gui.component.components;

import java.awt.Color;
import java.util.ArrayList;

import com.codewithritom.raptorclient.api.gui.RcClickGui;
import com.codewithritom.raptorclient.api.gui.component.Component;
import com.codewithritom.raptorclient.api.gui.component.Frame;
import com.codewithritom.raptorclient.api.moduleapi.Module;
import com.codewithritom.raptorclient.api.gui.component.components.sub.*;
import com.codewithritom.raptorclient.api.settingsapi.Setting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class Button extends Component {
//TODO setting descriptions
	public Module mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	private final ArrayList<Component> subcomponents;
	public boolean open;

	public Button(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<>();
		this.open = false;
		int height = 12;
		int opY = offset + 12;
		if(!mod.getSettings().isEmpty()) {
			for(Setting<?> s : mod.getSettings()){
				if(s.isCombo && s.getValue() instanceof String){
					this.subcomponents.add(new ModeButton((Setting<String>) s, this, opY));
					opY += 12;
				}
				if(s.isSlider && s.getValue() instanceof Double){
					this.subcomponents.add(new Slider((Setting<Double>) s, this, opY));
					opY += 12;
				}
				if(s.isCheckBox && s.getValue() instanceof Boolean){
					this.subcomponents.add(new Checkbox((Setting<Boolean>) s, this, opY));
					opY += 12;
				}
			}
		}
		this.subcomponents.add(new Keybind(this, opY));
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 12;
		}
	}
	
	@Override
	public void renderComponent() {
		DrawableHelper.fill(RcClickGui.matrix,parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isEnabled() ? new Color(0xFF9700FF).darker().getRGB() : 0xFF9700FF) : (this.mod.isEnabled() ? new Color(14,14,14).getRGB() : 0xFF111111));
		MatrixStack modelview= RenderSystem.getModelViewStack();
		modelview.push();
		modelview.scale(0.5f,0.5f,1f);
		RenderSystem.applyModelViewMatrix();
		MinecraftClient.getInstance().textRenderer.drawWithShadow(RcClickGui.matrix,this.mod.getName(), (parent.getX() + 2) * 2, (parent.getY() + offset + 2) * 2 + 4, this.mod.isEnabled() ? 0xBD62FF : -1);
		if(this.subcomponents.size() > 2) {
			MinecraftClient.getInstance().textRenderer.drawWithShadow(RcClickGui.matrix, this.open ? "-" : "+", (parent.getX() + parent.getWidth() - 10) * 2, (parent.getY() + offset + 2) * 2 + 4, -1);
		}
		if (this.isHovered) {
			MinecraftClient.getInstance().textRenderer.drawWithShadow(RcClickGui.matrix, mod.getDescription(), ((parent.getX() + parent.getWidth() - 10) * 2)+25, ((parent.getY() + offset + 2) * 2) + 25, 0xFF0000);
		}
		modelview.pop();
		RenderSystem.applyModelViewMatrix();
		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}
				DrawableHelper.fill(RcClickGui.matrix,parent.getX() + 2, parent.getY() + this.offset + 12, parent.getX() + 3, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12), -1);
			}
		}
	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (12 * (this.subcomponents.size() + 1));
		}
		return 12;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		return x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
	}
}
