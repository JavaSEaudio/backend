package util;

import org.apache.log4j.Logger;

import java.io.*;

public class FileWrite {

    private final static Logger log =  Logger.getLogger("com.audiostorage.report");

    private FileWrite(){}

    public static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
        try {
            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
