package org.example.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    /**
     * The main method is the entry point of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Stop working...");
            try {
                Reciever.save();
            } catch (Exception e) {
                LOGGER.warn("Something wrong with File");
            }
        }));
        try {
            Server server = new Server(Integer.parseInt(args[0]), args[1]);
            server.start();
        } catch (Exception e){
            LOGGER.debug("Something wrong with server");
        }

    }
}