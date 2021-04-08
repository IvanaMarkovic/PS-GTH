package ps.projekat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtil {

    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());
    private static String nazivPaketa;

    static {
        nazivPaketa = "." + File.separator + "src" +  File.separator + "main" +  File.separator + "resources" + File.separator + "logFajlovi";
        File logFajlovi = new File(nazivPaketa);
        if(!logFajlovi.exists()) {
            logFajlovi.mkdir();
        }
    }

    public static String getNazivPaketa() {
        return nazivPaketa;
    }

    public static void log(Level nivo, String nazivLoggera, Exception e, String nazivFajla) {
        Handler handler =  null;
        try {
             handler = new FileHandler(nazivFajla);
            Logger logger = Logger.getLogger(nazivLoggera);
            logger.addHandler(handler);
            logger.log(nivo, e.getMessage());
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
        }
    }
}
