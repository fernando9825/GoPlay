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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Fernando
 */
public class ExtraerTAGS extends Thread {

    public String artista, titulo, album, anhio, genero;
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
                genero = id3v1Tag.getGenre() + ": " + id3v1Tag.getGenreDescription();

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
                genero = id3v2Tag.getGenre() + ": " + id3v2Tag.getGenreDescription();

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
    

    

    public void Etiquetas(String ubicacion, DefaultTableModel tableModel, int pos) throws IOException, UnsupportedTagException, InvalidDataException {
        //Tomando ID3v2 frame values
        Mp3File mp3file = new Mp3File(ubicacion);

        if (mp3file.hasId3v1Tag()) {
            try {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                System.out.println("-----------------------------------------------\n");
                System.out.println("Pista: " + pos);
                System.out.println("Artista: " + id3v1Tag.getArtist());
                System.out.println("Título: " + id3v1Tag.getTitle());
                System.out.println("Álbum: " + id3v1Tag.getAlbum());
                System.out.println("Año: " + id3v1Tag.getYear());
                System.out.println("Género: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
                System.out.println("-----------------------------------------------\n");
                Object[] fila = {pos, id3v1Tag.getArtist(), id3v1Tag.getTitle(), id3v1Tag.getAlbum(), id3v1Tag.getGenre()};
                LlenarTabla tab = new LlenarTabla();
                tab.Llenar(tableModel, fila);
            } catch (Exception e) {
                System.out.println("Se produjo el siguiente error: " + e);
            }
        } else if (mp3file.hasId3v2Tag()) {
            try {

                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                System.out.println("-----------------------------------------------\n");
                System.out.println("#: " + pos);
                System.out.println("Artista: " + id3v2Tag.getArtist());
                System.out.println("Título: " + id3v2Tag.getTitle());
                System.out.println("Álbum: " + id3v2Tag.getAlbum());
                System.out.println("Año: " + id3v2Tag.getYear());
                System.out.println("Género: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
                System.out.println("-----------------------------------------------\n");
                Object[] fila = {pos, id3v2Tag.getArtist(), id3v2Tag.getTitle(), id3v2Tag.getAlbum(), id3v2Tag.getGenre()};
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

//    int longitud;
//    String[] Titulo = new String[longitud];
//    String[] Artista = new String[longitud];
//    String[] Compositor = new String[longitud];
//    String[] Genero = new String[longitud];
//    String[] Album = new String[longitud];
//    public ExtraerTAGS(int longitud) {
//        this.longitud = longitud;
//        
//    }
//
//    public void ExtraerTAGSMP3(String ubicacion, int posicion) {
//        try {
//            //System.out.println("Esta es la ubicacion: " + ubicacion);
//            //ubicacion = ubicacion.replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\"));
//            //System.out.println("Esta es la ubicacion corregida: " + ubicacion);
//            InputStream input = new FileInputStream(new File(ubicacion));
//            ContentHandler handler = new DefaultHandler();
//            Metadata metadata = new Metadata();
//            Parser parser = new Mp3Parser();
//            ParseContext parseCtx = new ParseContext();
//            parser.parse(input, handler, metadata, parseCtx);
//            input.close();
//
//            // List all metadata
//            String[] metadataNames = metadata.names();
//
////            for (String name : metadataNames) {
////                System.out.println(name + ": " + metadata.get(name));
////            }
//
////            // Retrieve the necessary info from metadata
////            // Names - title, xmpDM:artist etc. - mentioned below may differ based
////            System.out.println("----------------------------------------------");
////            System.out.println("Title: " + metadata.get("title"));
////            System.out.println("Artists: " + metadata.get("xmpDM:artist"));
////            System.out.println("Composer : " + metadata.get("xmpDM:composer"));
////            System.out.println("Genre : " + metadata.get("xmpDM:genre"));
////            System.out.println("Album : " + metadata.get("xmpDM:album"));
//            
//            if(metadata.get("title") == null){
//                Titulo[posicion] = "";
//            }else{
//                Titulo[posicion] = metadata.get("title").toString();
//            }
//            
//            if(metadata.get("xmpDM:artist") == null){
//                Artista[posicion] = "";
//            }else{
//                Artista[posicion] = metadata.get("xmpDM:artist").toString();
//            }
//            
//            if(metadata.get("xmpDM:composer") == null){
//                Compositor[posicion] = "";
//            }else{
//                Compositor[posicion] = metadata.get("xmpDM:composer").toString();
//            }
//            
//            if(metadata.get("xmpDM:genre") == null){
//                Genero[posicion] = "";
//            }else{
//               Genero[posicion] = metadata.get("xmpDM:genre");
//            }
//            
//            if(metadata.get("xmpDM:album") == null){
//                Album[posicion] = "";
//            }else{
//                Album[posicion] = metadata.get("xmpDM:album");
//            }
//            
//            
//            
//            
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            System.out.println(e);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println(e);
//        } catch (SAXException e) {
//            e.printStackTrace();
//            System.out.println(e);
//        } catch (TikaException e) {
//            e.printStackTrace();
//            System.out.println(e);
//        }
//    }
        }
    }
}
