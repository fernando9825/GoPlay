/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goplay;

import java.util.List;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Fernando
 */
public class Reproductor {

    public Reproductor() {
    }

    BasicPlayer player = new BasicPlayer();

    public boolean reproducir(String nombreCancion) {
        //String songName = "HungryKidsofHungary-ScatteredDiamonds.mp3";
        //String direccionCorregida = System.getProperty("user.dir") + "/" + songName;
        boolean reproduciendo = false;
        try {
            player.open(new URL("file:///" + nombreCancion));
            player.play();
            reproduciendo = true;

        } catch (BasicPlayerException | MalformedURLException e) {
            e.printStackTrace();
        }
        return reproduciendo;
    }

  

    public File dir; //Se declara fuera para que sea global
    public String[] buscarCanciones(String ubicacion) throws IOException {
        dir = new File(ubicacion);
        
        String[] extensions = new String[]{"mp3", ".flac"};
        System.out.println("Obteniendo todos los .mp3 y .flac de " + dir.getAbsolutePath()
                + " y los subfolders");
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        String[] ubicaciones = new String[files.size()];
        int x = 0;
        for (File file : files) {
            ubicaciones[x] = file.getAbsolutePath();
            x++;
        }
        return ubicaciones;
    }
    
    
}
