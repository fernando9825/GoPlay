/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goplay;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Fernando
 */
public class Reproductor {

    public Reproductor() {
    }

    BasicPlayer player = new BasicPlayer();
   

    public void reproducir(String nombreCancion) {
        //String songName = "HungryKidsofHungary-ScatteredDiamonds.mp3";
        //String direccionCorregida = System.getProperty("user.dir") + "/" + songName;
        
        try {
                player.open(new URL("file:///" + nombreCancion));
                player.play();
            
        } catch (BasicPlayerException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public File[] finder(String dirName) {
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".mp3");
            }
        });

    }

    public Collection getAllFolderFiles(File folder, String[] extensions) {
        Set results = new HashSet();
        Stack subFolders = new Stack();
        File currentFolder = folder;
        while (currentFolder != null && currentFolder.isDirectory() && currentFolder.canRead()) {
            File[] fs = null;
            try {
                fs = currentFolder.listFiles();
            } catch (SecurityException e) {
            }

            if (fs != null && fs.length > 0) {
                for (File f : fs) {
                    if (!f.isDirectory()) {
                        if (extensions == null || FilenameUtils.isExtension(f.getName(), extensions)) {
                            results.add(f);
                        }
                    } else {
                        subFolders.push(f);
                    }
                }
            }

            if (!subFolders.isEmpty()) {
                currentFolder = (File) subFolders.pop();
            } else {
                currentFolder = null;
            }
        }
        return results;
    }
}
