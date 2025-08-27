package com.example.URL.Shortner.repository;

import com.example.URL.Shortner.entity.UrlShortenerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLShortenerRepository extends JpaRepository<UrlShortenerDetails, Long> {

    UrlShortenerDetails findByOriginalUrl(String originalUrl) ;

    UrlShortenerDetails findByShortUrl(String shortUrl);
}
