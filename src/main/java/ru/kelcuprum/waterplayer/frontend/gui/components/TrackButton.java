package ru.kelcuprum.waterplayer.frontend.gui.components;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.buttons.base.Button;
import ru.kelcuprum.waterplayer.WaterPlayer;
import ru.kelcuprum.waterplayer.frontend.localization.Music;
import ru.kelcuprum.waterplayer.frontend.localization.StarScript;

public class TrackButton extends Button {
    protected AudioTrack track;
    private final boolean isShort;
    public TrackButton(int x, int y, int width, AudioTrack track, Screen screen, boolean isShort) {
        super(x, y, width, isShort ? 20 : 40, InterfaceUtils.DesignType.FLAT, Component.empty(), (s) -> confirmLinkNow(screen, track.getInfo().uri));
        StringBuilder builder = new StringBuilder();
        if (!Music.isAuthorNull(track)) builder.append("«").append(Music.getAuthor(track)).append("» ");
        builder.append(Music.getTitle(track)).append(" ").append(StarScript.getTimestamp(Music.getDuration(track)));
        setMessage(Component.literal(builder.toString()));
        this.isShort = isShort;
        this.track = track;
    }
    @Override
    public void renderText(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (getY() < guiGraphics.guiHeight() && !(getY() <=-getHeight()) ) {
            StringBuilder builder = new StringBuilder();
            if (!Music.isAuthorNull(track)) builder.append("«").append(Music.getAuthor(track)).append("» ");
            builder.append(Music.getTitle(track));
            String time = track.getInfo().isStream ? WaterPlayer.localization.getLocalization("format.live") : StarScript.getTimestamp(Music.getDuration(track));
            if (isShort) {
                if(InterfaceUtils.isDoesNotFit(getMessage(), getWidth(), getHeight())){
                    this.renderScrollingString(guiGraphics, AlinLib.MINECRAFT.font, 2, 0xFFFFFF);
                } else {
                    guiGraphics.drawString(AlinLib.MINECRAFT.font, builder.toString(), getX() + (getHeight() - 8) / 2, getY() + (getHeight() - 8) / 2, 0xffffff);
                    guiGraphics.drawString(AlinLib.MINECRAFT.font, time, getX() + getWidth()-AlinLib.MINECRAFT.font.width(time)-((getHeight() - 8) / 2), getY() + (getHeight() - 8) / 2, 0xffffff);
                }
//                super.renderText(guiGraphics, mouseX, mouseY, partialTicks);
            } else {
                ResourceLocation icon = Music.getThumbnail(track);
                guiGraphics.blit(icon, getX() + 2, getY() + 2, 0.0F, 0.0F, 36, 36, 36, 36);
                if (getWidth() - 50 < AlinLib.MINECRAFT.font.width(builder.toString())) {
                    guiGraphics.drawString(AlinLib.MINECRAFT.font, AlinLib.MINECRAFT.font.substrByWidth(FormattedText.of(builder.toString()), getWidth() - 50 - AlinLib.MINECRAFT.font.width("...")).getString() + "...", getX() + 45, getY() + 8, -1);
                } else {
                    guiGraphics.drawString(AlinLib.MINECRAFT.font, builder.toString(), getX() + 45, getY() + 8, -1);
                }
                guiGraphics.drawString(AlinLib.MINECRAFT.font, time, getX() + 45, getY() + 30 - AlinLib.MINECRAFT.font.lineHeight, -1);
            }
        }
    }
    public static void confirmLinkNow(Screen screen, String string) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.setScreen(new ConfirmLinkScreen((bl) -> {
            if (bl) {
                Util.getPlatform().openUri(string);
            }

            minecraft.setScreen(screen);
        }, string, true));
    }
}
