package com.codewithritom.raptorclient.api.gui.component;

import com.codewithritom.raptorclient.api.gui.RcClickGui;
import com.codewithritom.raptorclient.api.gui.component.components.Button;
import com.codewithritom.raptorclient.api.managers.ModuleManager;
import com.codewithritom.raptorclient.api.moduleapi.Category;
import com.codewithritom.raptorclient.api.moduleapi.Module;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class Frame {

	public ArrayList<Component> components;
	public Category category;
	private boolean open;
	private final int width;
	private int y;
	private int x;
	private final int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
	
	public Frame(Category cat) {
		this.components = new ArrayList<Component>();
		this.category = cat;
		this.width = 88;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = false;
		this.isDragging = false;
		int tY = this.barHeight;
		
		for(Module mod : ModuleManager.getModulesinCategory(category)) {
			Button modButton = new Button(mod, this, tY);
			this.components.add(modButton);
			tY += 12;
		}
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public void renderFrame(TextRenderer fontRenderer) {
		DrawableHelper.fill(RcClickGui.matrix,this.x, this.y, this.x + this.width, this.y + this.barHeight,-1);
		MatrixStack modelview= RenderSystem.getModelViewStack();
		modelview.push();
		modelview.scale(0.5f,0.5f,0.5f);
		RenderSystem.applyModelViewMatrix();
		fontRenderer.drawWithShadow(RcClickGui.matrix,this.category.name(), (this.x + 2) * 2 + 5, (this.y + 2.5f) * 2 + 5, 0xe8baff);
		fontRenderer.drawWithShadow(RcClickGui.matrix,this.open ? "-" : "+", (this.x + this.width - 10) * 2 + 5, (this.y + 2.5f) * 2 + 5, -1);
		modelview.pop();
		RenderSystem.applyModelViewMatrix();
		if(this.open) {
			if(!this.components.isEmpty()) {
				for(Component component : components) {
					component.renderComponent();
				}
			}
		}
	}
	
	public void refresh() {
		int off = this.barHeight;
		for(Component comp : components) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}
	
}
