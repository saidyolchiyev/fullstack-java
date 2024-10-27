package com.spring.example.controller;

import com.spring.example.WebApplication;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import static com.spring.example.WebApplication.*;

@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {

    @GetMapping("/{fileName}")
    public @ResponseBody
    byte[] getImage(@PathVariable("fileName") String fileName) {
        try {
            File file = new File(path + fileName);
            InputStream in = new FileInputStream(file);
            return IOUtils.toByteArray(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
