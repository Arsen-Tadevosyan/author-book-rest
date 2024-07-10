package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.CountryInfo;
import com.example.authorbookrest.entity.Country;
import com.example.authorbookrest.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountriesEndpoint {

    private final String X_RAPID_API_KEY = "X-RapidAPI-Key";
    private final String X_RAPID_API_HOST = "X-RapidAPI-Host";
    private final String COUNTRIES_URL = "https://geography4.p.rapidapi.com/apis/geography/v1/country";

    @Value("${rapid.api.key}")
    private String rapidApiKey;

    @Value("${rapid.api.host}")
    private String rapidApiHost;

    private final RestTemplate restTemplate;
    private final CountryRepository countryRepository;


    @GetMapping
    public ResponseEntity<List<CountryInfo>> getAllCountries() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(X_RAPID_API_KEY, rapidApiKey);
        httpHeaders.add(X_RAPID_API_HOST, rapidApiHost);

        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);

        ResponseEntity<CountryInfo[]> exchange = restTemplate.exchange(COUNTRIES_URL, HttpMethod.GET, httpEntity, CountryInfo[].class);
        List<CountryInfo> result = List.of(exchange.getBody());
        for (CountryInfo countryInfo : result) {
            CountryInfo.Name name = countryInfo.getName();
            countryRepository.save(Country.builder()
                    .name(name.getCommon())
                    .build());
        }
        return ResponseEntity.ok(result);
    }


}