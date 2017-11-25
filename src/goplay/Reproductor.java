/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goplay;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 *
 * @author Fernando
 */
public class Reproductor {

    public Reproductor() {
    }

    public void reproducir(String nombreCancion) {
        //String songName = "HungryKidsofHungary-ScatteredDiamonds.mp3";
        //String direccionCorregida = System.getProperty("user.dir") + "/" + songName;
        BasicPlayer player = new BasicPlayer();
        try {
            player.open(new URL("file:///" + nombreCancion));
            player.play();
        } catch (BasicPlayerException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
