package com.ketnoiso.upload;

import java.io.File;
import java.io.IOException;

/**
 * Created by IT on 3/18/2015.
 */
public class App {
    public static void main(String[] args) throws IOException {
        File f = File.createTempFile("bac",".tmp");
        System.out.println(f.getAbsolutePath());
    }
}
