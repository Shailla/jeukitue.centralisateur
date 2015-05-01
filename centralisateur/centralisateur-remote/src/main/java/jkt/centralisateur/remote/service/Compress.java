package jkt.centralisateur.remote.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

public class Compress {

    private static byte[] fileContent1 = "Content 1".getBytes();
    private static byte[] fileContent2 = "Content 2".getBytes();
    
    public static void main(String argv[]) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            
            {    
                TarArchiveOutputStream tarOutput = new TarArchiveOutputStream(bos);
                
                {
                    TarArchiveEntry entry1 = new TarArchiveEntry("/coucou/conf1");
                    entry1.setSize(fileContent1.length);
                    tarOutput.putArchiveEntry(entry1);
                    tarOutput.write(fileContent1);
                    tarOutput.closeArchiveEntry();
                }
                
                {
                    TarArchiveEntry entry2 = new TarArchiveEntry("/coucou/conf2");
                    entry2.setSize(fileContent2.length);
                    tarOutput.putArchiveEntry(entry2);
                    tarOutput.write(fileContent2);
                    tarOutput.closeArchiveEntry();
                }
                
                tarOutput.close();
            }
            
            {
                OutputStream os = new FileOutputStream("c:/compress.tar.gz");
                GZIPOutputStream gzipOutput = new GZIPOutputStream(os); 
                gzipOutput.write(bos.toByteArray());
                gzipOutput.close();
            }
        }
        catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }

    }

}
