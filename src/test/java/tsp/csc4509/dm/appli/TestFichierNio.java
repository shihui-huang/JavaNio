package tsp.csc4509.dm.appli;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestFichierNio {

    @Test
    public void testWriteValue() throws Exception {
        FileOutputStream fout = new FileOutputStream("./tmp/test.bin");
        FichierNio.writeValues(fout, 10);
        fout.close();
        assertTrue("test de WriteValue: le fichier écrit identique au modèle", compareFile("./tmp/test.bin", "./data/entiers0-9.data"));

        fout = new FileOutputStream("./tmp/test.bin");
        FichierNio.writeValues(fout, 15);
        fout.close();
        assertTrue("test de WriteValue: le fichier écrit différent du modèle", !compareFile("./tmp/test.bin", "./data/entiers0-9.data"));

    }

    @Test
    public void testReadValue() throws Exception {
        FileInputStream fin = new FileInputStream("./data/entiers0-9.data");
        ArrayList<Integer> list = FichierNio.readValues(fin);
        fin.close();

        List list1 = Arrays.asList(0,1,2,3,4,5,6,7,8,9);
        ArrayList<Integer> listTest1 = new ArrayList<>(list1);
        assertEquals("test de ReadValues: le fichier lu identique au  modèle", list, listTest1);

        List list2 = Arrays.asList(10,11,12,13,14,15);
        ArrayList<Integer> listTest2 = new ArrayList<>(list2);
        assertNotEquals("test de ReadValues: le fichier lu diffÃ©rent au modèle", list,listTest2);
    }

    /**
     * Compare le contenu de deux fichiers et retourne true s'ils sont identiques.
     * @param fileName1
     * 					nom du premier fichier.
     * @param fileName2
     * 					nom du second fichier.
     * @return
     * 					true si les deux fichiers ont le même contenu, false sinon.
     * @throws Exception
     * 					toutes les exceptions possibles.
     */
    private boolean compareFile(String fileName1, String fileName2) throws Exception {
        FileInputStream fin1 = new FileInputStream(fileName1);
        FileInputStream fin2 = new FileInputStream(fileName2);
        FileChannel fcin1 = fin1.getChannel();
        FileChannel fcin2 = fin2.getChannel();
        ByteBuffer buffer1 = ByteBuffer.allocate(1024);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        int lu1, lu2;
        do {
            buffer1.clear();
            buffer2.clear();
            lu1 = fcin1.read(buffer1);
            lu2 = fcin2.read(buffer2);
            if (lu1 != lu2) {
                return false;
            }
            buffer1.flip();
            buffer2.flip();
            for (int ind=0; ind < lu1; ind++) {
                if (buffer1.get() != buffer2.get()) {
                    return false;
                }
            }

        } while (lu1 == 1024);

        fin1.close();
        fin2.close();
        return true;
    }
}
