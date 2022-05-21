package com.codewithritom.raptorclient.api.gui.component.components.sub;

import com.codewithritom.raptorclient.api.gui.RcClickGui;
import com.codewithritom.raptorclient.api.gui.component.Component;
import com.codewithritom.raptorclient.api.gui.component.components.Button;
import com.codewithritom.raptorclient.api.settingsapi.Setting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Checkbox extends Component {

	private boolean hovered;
	private Setting<Boolean> op;
	private Button parent;
	private int offset;
	private int x;
	private int y;
	
	public Checkbox(Setting<Boolean> option, Button button, int offset) {
		this.op = option;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}

	@Override
	public void renderComponent() {
		DrawableHelper.fill(RcClickGui.matrix,parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth()), parent.parent.getY() + offset + 12, this.hovered ? 0xFF222222 : 0xFF111111);
		DrawableHelper.fill(RcClickGui.matrix,parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
		MatrixStack modelview= RenderSystem.getModelViewStack();
		modelview.push();
		modelview.scale(0.5f,0.5f,0.5f);
		RenderSystem.applyModelViewMatrix();
		MinecraftClient.getInstance().textRenderer.drawWithShadow(RcClickGui.matrix,this.op.getName(), (parent.parent.getX() + 14) * 2 + 5, (parent.parent.getY() + offset + 2) * 2 + 4, -1);
		MinecraftClient.getInstance().textRenderer.drawWithShadow(RcClickGui.matrix,this.op.getDescription(), (parent.parent.getX() + 14) * 2 + 30, (parent.parent.getY() + offset + 2) * 2 + 30, Color.RED.getRGB());
		modelview.pop();
		RenderSystem.applyModelViewMatrix();
		DrawableHelper.fill(RcClickGui.matrix,parent.parent.getX() + 3 + 4, parent.parent.getY() + offset + 3, parent.parent.getX() + 9 + 4, parent.parent.getY() + offset + 9, 0xFF999999);
		if(this.op.getValue())
			DrawableHelper.fill(RcClickGui.matrix,parent.parent.getX() + 4 + 4, parent.parent.getY() + offset + 4, parent.parent.getX() + 8 + 4, parent.parent.getY() + offset + 8, 0xFF666666);
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
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
			this.op.setValue(!op.getValue());;
		}
	}

	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
