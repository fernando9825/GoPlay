/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goplay;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fernando
 */
public class ExtraerTAGS extends Thread {

    public String artista, titulo, album, anhio, genero;
    public BufferedImage image; //para obtener la imagen
    public ImageIcon ImagePeke; //Para obtener la imagen de tamaño pequeño
    public boolean hayImagen = false;

    public ExtraerTAGS() {
    }

    public void Informacion(String ubicacion) throws IOException, UnsupportedTagException, InvalidDataException {
        Mp3File mp3file = new Mp3File(ubicacion);
        System.out.println("------------------------------------------------------------------------\n\n");
        System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
        System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
        System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
        System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
        System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
        System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
        System.out.println("------------------------------------------------------------------------\n\n");
    }

    //Esta funcion devuelve las etiquetas para una sola canción
    public void Etiquetas(String ubicacion) throws IOException, UnsupportedTagException, InvalidDataException {
        //Tomando ID3v2 frame values
        Mp3File mp3file = new Mp3File(ubicacion);

        artista = titulo = album = anhio = genero = "";
        //el numero de pista se devuelve desde la tabla
        if (mp3file.hasId3v1Tag()) {
            try {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();

                artista = id3v1Tag.getArtist();
                titulo = id3v1Tag.getTitle();
                album = id3v1Tag.getAlbum();
                anhio = id3v1Tag.getYear();
                genero = id3v1Tag.getGenreDescription();
                hayImagen = false;
            } catch (Exception e) {
                System.out.println("Se produjo el siguiente error: " + e);
            }
        } else if (mp3file.hasId3v2Tag()) {
            try {

                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                artista = id3v2Tag.getArtist();
                titulo = id3v2Tag.getTitle();
                album = id3v2Tag.getAlbum();
                anhio = id3v2Tag.getYear();
                genero = id3v2Tag.getGenreDescription();

                byte[] albumImageData = id3v2Tag.getAlbumImage();
                if (albumImageData != null) {
                    hayImagen = true;
                    System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
                    System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());

                    image = ImageIO.read(new ByteArrayInputStream(id3v2Tag.getAlbumImage()));
                    ImageIO.write(image, "BMP", new File("filename.bmp"));
                    Image newimg = image.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH); // dandole las dimensiones
                    ImagePeke = new ImageIcon(newimg); // Rehacer la imagen
                }
            } catch (Exception e) {
                System.out.println("Se produjo el siguiente error: " + e);
            }
        }
    }

    public void Etiquetas(String ubicacion, DefaultTableModel tableModel, int pos) throws IOException, UnsupportedTagException, InvalidDataException {
        //Tomando ID3v2 frame values
        Mp3File mp3file = new Mp3File(ubicacion);

        if (mp3file.hasId3v1Tag()) {
            try {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
//                System.out.println("-----------------------------------------------\n");
//                System.out.println("Pista: " + pos);
//                System.out.println("Artista: " + id3v1Tag.getArtist());
//                System.out.println("Título: " + id3v1Tag.getTitle());
//                System.out.println("Álbum: " + id3v1Tag.getAlbum());
//                System.out.println("Año: " + id3v1Tag.getYear());
//                System.out.println("Género: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
//                System.out.println("-----------------------------------------------\n");
                Object[] fila = {pos, id3v1Tag.getArtist(), id3v1Tag.getTitle(), id3v1Tag.getAlbum(), id3v1Tag.getYear(), id3v1Tag.getGenreDescription()};
                LlenarTabla tab = new LlenarTabla();
                tab.Llenar(tableModel, fila);
            } catch (Exception e) {
                System.out.println("Se produjo el siguiente error: " + e);
            }
        } else if (mp3file.hasId3v2Tag()) {
            try {

                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
//                System.out.println("-----------------------------------------------\n");
//                System.out.println("#: " + pos);
//                System.out.println("Artista: " + id3v2Tag.getArtist());
//                System.out.println("Título: " + id3v2Tag.getTitle());
//                System.out.println("Álbum: " + id3v2Tag.getAlbum());
//                System.out.println("Año: " + id3v2Tag.getYear());
//                System.out.println("Género: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
//                System.out.println("-----------------------------------------------\n");
                Object[] fila = {pos, id3v2Tag.getArtist(), id3v2Tag.getTitle(), id3v2Tag.getAlbum(), id3v2Tag.getYear(), id3v2Tag.getGenreDescription()};
                LlenarTabla tab = new LlenarTabla();
                tab.Llenar(tableModel, fila);
                byte[] albumImageData = id3v2Tag.getAlbumImage();
                if (albumImageData != null) {
                    System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
                    System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());

                }
            } catch (Exception e) {
                System.out.println("Se produjo el siguiente error: " + e);
            }

        }
    }
}
