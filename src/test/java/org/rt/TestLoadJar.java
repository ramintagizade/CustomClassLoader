package org.rt;

import org.junit.Test;

import java.io.IOException;


/**
 * @author Ramin Taghizada
 */

public class TestLoadJar {

    @Test
    public void testLoadJar() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //LoadJar loadJar = new LoadJar();
        //loadJar.loadJar(System.getProperty("user.dir")+"/lib/testJar-1.0.0.jar");

        //LoadResources loadResources = new LoadResources();
        //loadResources.loadResource(System.getProperty("user.dir")+"/lib");

        CustomClassLoader customClassLoader = new CustomClassLoader();
        customClassLoader.load(System.getProperty("user.dir")+"/lib");

        Class clazz = customClassLoader.loadClass("org.rt.Main.class");
        System.out.println(clazz);
    }
}
