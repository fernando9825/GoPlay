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
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.apache.commons.io.FileUtils;
import static sun.audio.AudioPlayer.player;

/**
 *
 * @author Fernando
 */
public class Reproductor {

    public Reproductor() {
    }

    public BasicPlayer player = new BasicPlayer();
    public BasicController control = (BasicController) player;
    

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
            ubicaciones[x] = file.getCanonicalPath();
            x++;
        }
        return ubicaciones;
    }

    public String NombreCancion(String ubicacion) throws IOException {
        File file = new File(ubicacion);
        String ubicaciones = "";
        //System.out.println("Esta cancion no tenia etiquetas, en su lugar el nombre de archivo como titulo: " + file.getName());
        ubicaciones = file.getName();
        return ubicaciones;
    }

    //Esta funcion devuelve la duracion de la cancion en segundos
    public int Duracion(String ubicacion) throws UnsupportedAudioFileException, IOException {
        File file = new File(ubicacion);
        AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
        Map properties = baseFileFormat.properties();
        Long duration = (Long) properties.get("duration");
        int segundos = (int) (duration/1000000);
        return segundos;
    }
}
