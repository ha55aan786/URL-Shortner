package com.example.URL.Shortner.controller;

import com.example.URL.Shortner.service.ShortnerServices;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class MainController {

    @Autowired
    private ShortnerServices shortenerServices;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);


    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/generate")
    public ResponseEntity<String> generateURL (@RequestParam String url) throws Exception {
        /*
        *1.generateURL
        get url in param or body
        pass the url to the service class
        */

        String result =  shortenerServices.returnURL(url);
        logger.info("Generated short URL: {}", url);
        return ResponseEntity.ok(result);

    }

        @CrossOrigin(origins = "http://localhost:5173")
        @GetMapping("/hit/{uuid}")
        public ResponseEntity<Void> hitTheLink(@PathVariable String uuid, HttpServletRequest httpServletRequest) throws Exception {
            String originalUrl = shortenerServices.getOriginalUrl(uuid);

            if (originalUrl == null) {
                logger.warn("Short URL not found: {}", httpServletRequest.getRequestURL() );
                return ResponseEntity.notFound().build(); // 404 if no mapping exists
            }

            logger.info("Redirecting short URL {} → {}", httpServletRequest.getRequestURL(), originalUrl);
            // Redirect the browser
            return ResponseEntity.status(HttpStatus.FOUND)  // 302 redirect
                    .location(URI.create(originalUrl))
                    .build();
        }

}
