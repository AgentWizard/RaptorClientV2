package com.codewithritom.raptorclient.api.gui.component.components.sub;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.codewithritom.raptorclient.api.gui.RcClickGui;
import com.codewithritom.raptorclient.api.gui.component.Component;
import com.codewithritom.raptorclient.api.gui.component.components.Button;
import com.codewithritom.raptorclient.api.settingsapi.Setting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public class Slider extends Component {

	private boolean hovered;

	private Setting<Double> set;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	private boolean dragging = false;

	private double renderWidth;
	
	public Slider(Setting<Double> value, Button button, int offset) {
		this.set = value;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}
	
	@Override
	public void renderComponent() {
		DrawableHelper.fill(RcClickGui.matrix,parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 12, this.hovered ? 0xFF222222 : 0xFF111111);
		DrawableHelper.fill(RcClickGui.matrix,parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (int) renderWidth, parent.parent.getY() + offset + 12,hovered ? 0xFF555555 : 0xFF444444);
		DrawableHelper.fill(RcClickGui.matrix,parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
		MatrixStack modelview= RenderSystem.getModelViewStack();
		modelview.push();
		modelview.scale(0.5f,0.5f,0.5f);
		RenderSystem.applyModelViewMatrix();
		MinecraftClient.getInstance().textRenderer.drawWithShadow(RcClickGui.matrix,this.set.getName() + ": " + this.set.getValue() , (parent.parent.getX()* 2 + 15), (parent.parent.getY() + offset + 2) * 2 + 5, -1);
		MinecraftClient.getInstance().textRenderer.drawWithShadow(RcClickGui.matrix,set.getDescription() , (parent.parent.getX()* 2 + 15) +25, (parent.parent.getY() + offset + 2) * 2 + 30, Color.RED.getRGB());
		modelview.pop();
		RenderSystem.applyModelViewMatrix();
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButtonD(mouseX, mouseY) || isMouseOnButtonI(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
		
		double diff = Math.min(88, Math.max(0, mouseX - this.x));

		double min = set.getMin();
		double max = set.getMax();
		
		renderWidth = (88) * (set.getValue() - min) / (max - min);
		
		if (dragging) {
			if (diff == 0) {
				set.setValue(set.getMin());
			}
			else {
				double newValue = roundToPlace(((diff / 88) * (max - min) + min), 2);
				set.setValue(newValue);
			}
		}
	}
	
	private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
			dragging = true;
		}
		if(isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
			dragging = true;
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		dragging = false;
	}
	
	public boolean isMouseOnButtonD(int x, int y) {
		if(x > this.x && x < this.x + (parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
	
	public boolean isMouseOnButtonI(int x, int y) {
		if(x > this.x + parent.parent.getWidth() / 2 && x < this.x + parent.parent.getWidth() && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
