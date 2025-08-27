package com.example.URL.Shortner.service;

import com.example.URL.Shortner.entity.UrlShortenerDetails;
import com.example.URL.Shortner.repository.URLShortenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;


@Service
public class ShortnerServices {

    @Autowired
    private URLShortenerRepository urlShortenerRepository;

    @Value("${url.shortener.base.url}")
    private String baseURL;

    public String returnURL (String url) throws Exception {
    /*
    * service for generate controller:
    --------------------------------
        generate a uuid
        set a base url : https://localhost:8080/hit/{uuid}
        map that uuid with the url in db
        return the complete url
    */
        String generatedURL = null;
        try {
            UrlShortenerDetails existedUrl = urlShortenerRepository.findByOriginalUrl(url);
            if (Objects.isNull(existedUrl)) {
                UUID uuid =  UUID.randomUUID();
                String updatedUuid = uuid.toString().substring(0, 7);
                generatedURL = returnUuid(url, baseURL + updatedUuid);
            }else {
                return existedUrl.getShortUrl();
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return generatedURL;
    }

    public String getOriginalUrl(String shortUrl) throws Exception {
        shortUrl = baseURL + shortUrl;
        UrlShortenerDetails urlShortenerDetails = urlShortenerRepository.findByShortUrl(shortUrl);

        if (urlShortenerDetails == null) {
            return null;
        }
        return urlShortenerDetails.getOriginalUrl();
    }

    private String returnUuid(String url, String generatedUrl) {
        if (urlShortenerRepository.findByShortUrl(generatedUrl) == null) {
            UrlShortenerDetails urlDetails = new UrlShortenerDetails();
            urlDetails.setOriginalUrl(url);
            urlDetails.setShortUrl(generatedUrl);

            urlShortenerRepository.save(urlDetails);

        } else {
            returnUuid(url, generatedUrl);
        }
        return generatedUrl;
    }

}
