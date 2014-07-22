package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by oper4 on 22.07.2014.
 */
public class Logs {

    public static void setLog(String message){
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;
        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("C:/MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
//
//            try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("MyLog.txt", true)))) {
//                out.println("the text");
//            }catch (IOException e) {
//                //exception handling left as an exercise for the reader
//            }
//            // the following statement is used to log any messages
//            //logger.("Server side log TomCatEE java");
            logger.info("------------------------------");
            logger.info(message);
            fh.close();

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
