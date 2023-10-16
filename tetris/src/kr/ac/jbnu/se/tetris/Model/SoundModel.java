package kr.ac.jbnu.se.tetris.Model;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundModel {
    /***************************************************************/
    // 객체 초기화
    AudioInputStream ais;
    Clip clip;
    /***************************************************************/
    // intro bgm
    public void introBgmPlay() {
        try {
            ais = AudioSystem.getAudioInputStream(new File("tetris/src/sound/bgm.wav"));
            //"src/sound/bgm.wav"
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /***************************************************************/
    // intro bgm stop
    public void intoBgmStop() {
        clip.stop();
    }
    /***************************************************************/
    // playBgm
    public void playBgm() {
        try {
            ais = AudioSystem.getAudioInputStream(new File("tetris/src/sound/playBGM.wav"));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /***************************************************************/

    // victory
    public void victoryPlay() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("tetris/sound/victory.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /***************************************************************/
    // lose
    public void losePlay() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("tetris/sound/lose.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /***************************************************************/
    // menu click
    public void menuClickPlay() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("tetris/sound/menu_click.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /***************************************************************/
    // move block
    public void dropBlockPlay() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("tetris/src/sound/drop.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /***************************************************************/
    // clear block
    public void clearBlockPlay() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("tetris/src/sound/break.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}