package com.codewithritom.raptorclient.api.gui.component.components.sub;

import com.codewithritom.raptorclient.api.gui.RcClickGui;
import com.codewithritom.raptorclient.api.gui.component.Component;
import com.codewithritom.raptorclient.api.gui.component.components.Button;
import com.codewithritom.raptorclient.api.settingsapi.Setting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class ModeButton extends Component {

	private boolean hovered;
	private final Button parent;
	private final Setting<String> set;
	private int offset;
	private int x;
	private int y;

	private int modeIndex;
	
	public ModeButton(Setting<String> set, Button button, int offset) {
		this.set = set;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		this.modeIndex = 0;
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void renderComponent() {
		DrawableHelper.fill(RcClickGui.matrix,parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth()), parent.parent.getY() + offset + 12, this.hovered ? 0xFF222222 : 0xFF111111);
		DrawableHelper.fill(RcClickGui.matrix,parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
		MatrixStack modelview= RenderSystem.getModelViewStack();
		modelview.push();
		modelview.scale(0.5f,0.5f,0.5f);
		RenderSystem.applyModelViewMatrix();
		MinecraftClient.getInstance().textRenderer.drawWithShadow(RcClickGui.matrix,set.getName() +": " + set.getValue(), (parent.parent.getX() + 7) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, -1);
		MinecraftClient.getInstance().textRenderer.drawWithShadow(RcClickGui.matrix,set.getDescription(), (parent.parent.getX() + 7) * 2 + 25, (parent.parent.getY() + offset + 2) * 2 + 30, Color.RED.getRGB());
		modelview.pop();
		RenderSystem.applyModelViewMatrix();
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			int maxIndex = set.getModes().size();

			if(modeIndex + 1 >= maxIndex)
				modeIndex = 0;
			else
				modeIndex++;

			set.setValue(set.getModes().get(modeIndex));
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
