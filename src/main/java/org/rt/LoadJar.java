package org.rt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author Ramin Taghizada
 */

public class LoadJar {

    private static Logger logger = LogManager.getLogger(LoadJar.class);
    protected Map<String, byte[] > jarContents;

    public LoadJar() {
        jarContents = new HashMap<String,byte[]>();
    }

    /*
     *  @return Map
     */
    public Map<String, byte[]> getJarContents() {
        return jarContents;
    }

    /*
     * @param jarFileName
     * @throws IOException
     */
    public void loadJar(String jarFileName) throws IOException {
        logger.info("loading jar: " + jarFileName);

        FileInputStream inputStream = new FileInputStream(jarFileName);
        loadJar(inputStream);
    }

    /*
     * @throws IOException
     */
    public void loadJar(InputStream inputStream) throws IOException {

        JarInputStream jarInputStream = new JarInputStream(new BufferedInputStream(inputStream));
        JarEntry jarEntry = null;
        try {
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {

                if (jarEntry.isDirectory()) continue;

                byte[] buffer = new byte[2048];
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                for (; ; ) {
                    int nBytes = jarInputStream.read(buffer);
                    if (nBytes <= 0) {
                        break;
                    }
                    outputStream.write(buffer, 0, nBytes);
                }

                byte[] bytes = outputStream.toByteArray();
                jarContents.put(jarEntry.getName(), bytes);

                logger.info("loaded Class  : " + jarEntry.getName());

                outputStream.close();
            }
        }
        catch (Exception e) {
            logger.warn(e.getMessage(),e);
        }
        finally {
            jarInputStream.close();
        }
    }
}