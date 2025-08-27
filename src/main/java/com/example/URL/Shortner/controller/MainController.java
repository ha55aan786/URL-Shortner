package com.example.URL.Shortner.controller;

import com.example.URL.Shortner.service.ShortnerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class MainController {

    @Autowired
    private ShortnerServices shortenerServices;


    @GetMapping("/generate")
    public ResponseEntity<String> generateURL (@RequestParam String url) throws Exception {
        /*
        * 1.generateURL
        get url in param or body
        pass the url to the service class
        */

        String result =  shortenerServices.returnURL(url);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/hit/{uuid}")
    public ResponseEntity<Void> hitTheLink(@PathVariable String uuid) throws Exception {
        String originalUrl = shortenerServices.getOriginalUrl(uuid);

        if (originalUrl == null) {
            return ResponseEntity.notFound().build(); // 404 if no mapping exists
        }

        // Redirect the browser
        return ResponseEntity.status(HttpStatus.FOUND)  // 302 redirect
                .location(URI.create(originalUrl))
                .build();
    }

}
