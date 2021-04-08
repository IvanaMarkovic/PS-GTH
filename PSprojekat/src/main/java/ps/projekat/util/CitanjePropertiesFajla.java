package ps.projekat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CitanjePropertiesFajla {

    private Properties konfiguracija;
    private static final Logger logger = Logger.getLogger(CitanjePropertiesFajla.class.getName());

    public CitanjePropertiesFajla(String nazivPropertiesFajla) {
        String nazivPaketa = "." + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        String nazivFajla = nazivPaketa + File.separator + "propertiesFajlovi" + File.separator + nazivPropertiesFajla;
        ucitavanjeKonfiguracije(nazivFajla);
    }


    public Properties getKonfiguracija() {
        return konfiguracija;
    }

    public void ucitavanjeKonfiguracije(String nazivFajla) {
        InputStream inputStream = null;
        try {
            konfiguracija = new Properties();
            inputStream = new FileInputStream(nazivFajla);
            if (inputStream != null) {
                konfiguracija.load(inputStream);
            }
            inputStream.close();
        } catch (IOException e) {
            LoggerUtil.log(Level.WARNING, logger.getName(), e, LoggerUtil.getNazivPaketa() + File.separator + "error1.log");
        } catch (Exception e) {
            LoggerUtil.log(Level.WARNING, logger.getName(), e, LoggerUtil.getNazivPaketa() + File.separator + "error1.log");
        }
    }
}
