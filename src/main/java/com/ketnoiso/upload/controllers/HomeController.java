package com.ketnoiso.upload.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;

/**
 */
@Controller
public class HomeController {
    @Value("${upload.store.dir}")
    private String UPLOAD_STORE_PATH;
    /**
     *
     */
    @Value("${upload.tmp.dir}")
    private String UPLOAD_TMP_PATH;

    /**
     *
     * @return
     */
    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public String provideUploadInfo() {
        return "upload";
    }

    /**
     *
     * @param name
     * @param file
     * @param chunks
     * @param chunk
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(
                            @RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam(required=false, defaultValue="-1") int chunks,
                            @RequestParam(required=false, defaultValue="-1") int chunk) throws IOException {
        //no chunk
        if(chunks ==-1 && chunk == -1) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + file.getOriginalFilename() + "!";
            } catch (Exception e) {
                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
            }
        } else {
            Path source = Paths.get(UPLOAD_TMP_PATH,name);
            //create temp file
            //first call
            if(chunk==0 && chunks>=0) {
                Files.deleteIfExists(source);
                Files.createFile(source);
            } else if (chunks > 0 && chunk > 0) {
                Files.write(source, file.getBytes(), StandardOpenOption.APPEND);
                //last call
                if (chunk == chunks - 1)
                {
                    Files.write(source, file.getBytes(), StandardOpenOption.APPEND);
                    Path target = Paths.get(UPLOAD_STORE_PATH,name);
                    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                    return target.toString();
                }
            }
        }
        return "ok";
    }
}
