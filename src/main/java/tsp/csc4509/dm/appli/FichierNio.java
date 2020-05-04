package tsp.csc4509.dm.appli;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class FichierNio {
    /**
     * Écrit les entiers de 0 à max-1 dans le fichier fout.
     * @param fout
     *			fichier ouvert en écriture où doivent être écrits les entiers.
     * @param max
     * 			limite (non incluse) des valeurs à écrire dans le fichier.
     * @return
     * 			le nombre d'octets écrits dans le fichier.
     * @throws IOException
     * 			toutes les exceptions provoquées par les erreurs d'entrées/sorties.
     */
    public static int writeValues(final FileOutputStream fout, final int max) throws IOException {
        FileChannel fcout = fout.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(max * Integer.SIZE / Byte.SIZE);
        int res = 0;
        for(int i = 0; i < max; i++){
            buffer.putInt(i);

        }
        buffer.flip();
        res = fcout.write(buffer);
        return res;
    }

    /**
     * Charge dans une ArrayList tous les entiers contenus dans le fichier fin.
     * @param fin
     * 			fichier ouvert en lecture où sont les entiers à lire.
     * @return
     * 			Une ArrayList qui contiendra tous les entiers du fichier dans le même ordre que le fichier.
     * @throws IOException
     * 			toutes les exceptions provoquées par les erreurs d'entrées/sorties.
     */
    public static ArrayList<Integer> readValues(final FileInputStream fin) throws IOException {
        FileChannel fcin = fin.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int res;
        ArrayList<Integer> array = new ArrayList<>();
        do{
            buffer.clear();
            res = fcin.read(buffer);
            buffer.flip();
            while ( buffer.hasRemaining()) {
                array.add(buffer.getInt());
            }
        }while(res == 1024);//if res==1024,indicates that the number of bytes in fin is larger than the length of the buffer, so we need to reread the rest
        fin.close();
        return array;

    }

    public static void main(String[] args) throws IOException {
        FileOutputStream fileNameOut = new FileOutputStream(args[0]);
        FileInputStream fileNameIn = new FileInputStream(args[0]);
        int max;
        ArrayList<Integer> list;
        max = Integer.parseInt(args[1]);
        writeValues(fileNameOut,max);
        list = readValues(fileNameIn);
        System.out.println(list);
    }


}
