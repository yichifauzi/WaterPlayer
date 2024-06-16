package ru.kelcuprum.waterplayer.frontend.localization;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.minecraft.resources.ResourceLocation;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.waterplayer.WaterPlayer;
import ru.kelcuprum.waterplayer.frontend.gui.TexturesHelper;

import java.io.File;

public class Music {
    //
    public static boolean trackIsNull(){
        return trackIsNull(WaterPlayer.player.getAudioPlayer().getPlayingTrack());
    }
    public static boolean trackIsNull(AudioTrack track){
        return track == null;
    }
    //
    public static boolean isAuthorNull(AudioTrack info){
        return trackIsNull(info) || info.getInfo().author.equals("Unknown artist");
    }
    public static boolean isAuthorNull() {return isAuthorNull(WaterPlayer.player.getAudioPlayer().getPlayingTrack());}
    //
    public static String getAuthor(AudioTrack info){
        String author = isAuthorNull(info) ? "" : info.getInfo().author;
        if(author.endsWith(" - Topic")) author = author.replace(" - Topic", "");
        return author;
    }
    public static String getAuthor() {return getAuthor(WaterPlayer.player.getAudioPlayer().getPlayingTrack());}
    //
    public static boolean isTitleNull(AudioTrack info){
        return trackIsNull(info) || info.getInfo().title.equals("Unknown title");
    }
    //
    public static String getTitle(AudioTrack info){
        String[] fileArgs = info.getInfo().uri.split("/");
        if(fileArgs.length == 1) fileArgs = info.getInfo().uri.split("\\\\");
        String file = fileArgs[fileArgs.length-1];
        return isTitleNull(info) ? file : info.getInfo().title;
    }
    public static String getTitle() {return getTitle(WaterPlayer.player.getAudioPlayer().getPlayingTrack());}
    //
    public static int getVolume(){
        return WaterPlayer.player.getVolume();
    }
    public static String getSpeakerVolume(){
        return (getVolume() <= 0) ? "🔇" : (getVolume() <= 1) ? "🔈" : (getVolume() <= 70) ? "🔉" :  "🔊";
    }
    public static String getRepeatState(){
        return WaterPlayer.player.getTrackScheduler().getRepeatStatus() == 0 ? "" : WaterPlayer.player.getTrackScheduler().getRepeatStatus() == 1 ? " 🔁" : " 🔂";
    }
    public static String getPauseState(){
        return WaterPlayer.player.getAudioPlayer().isPaused() ? "⏸" : "▶";
    }
    public static boolean isFile(AudioTrack info){
        if(trackIsNull(info)) return false;
        File track = new File(info.getInfo().uri);
        return track.exists() && track.isFile();
    }

    public static ResourceLocation getThumbnail(){
        return trackIsNull() ? InterfaceUtils.getResourceLocation("waterplayer", "textures/no_icon.png") : getThumbnail(WaterPlayer.player.getAudioPlayer().getPlayingTrack());
    }
    public static ResourceLocation getThumbnail(AudioTrack info){
        return info.getInfo().artworkUrl != null ? TexturesHelper.getTexture(info.getInfo().artworkUrl, (info.getSourceManager().getSourceName() + "_" + info.getInfo().identifier)) : Music.isFile(info) ? InterfaceUtils.getResourceLocation("waterplayer", "textures/file_icon.png") : InterfaceUtils.getResourceLocation("waterplayer", "textures/no_icon.png");
    }

    public static boolean isFile(){
        return trackIsNull() || isFile(WaterPlayer.player.getAudioPlayer().getPlayingTrack());
    }
    //
    public static long getPosition(AudioTrack track){
        return trackIsNull() ? 0 : track.getPosition();
    }
    public static long getPosition() {return getPosition(WaterPlayer.player.getAudioPlayer().getPlayingTrack());}
    //
    public static long getDuration(AudioTrack track){
        return trackIsNull() ? 0 : track.getDuration();
    }
    public static long getDuration() {return getDuration(WaterPlayer.player.getAudioPlayer().getPlayingTrack());}
    //
    public static boolean getIsLive(){return getIsLive(WaterPlayer.player.getAudioPlayer().getPlayingTrack());}
    public static boolean getIsLive(AudioTrack track){return !trackIsNull(track) && WaterPlayer.player.getAudioPlayer().getPlayingTrack().getInfo().isStream;}

}
