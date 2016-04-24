/**
 * Created by Andrew Bell 12/7/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.catfacts.controller;

import com.recursivechaos.catfacts.domain.CatFact;
import com.recursivechaos.catfacts.repository.CatFactRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.concurrent.ThreadLocalRandom;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class CatFactController {

    private static final Logger logger = getLogger(CatFactController.class);
    public static final String MODERATION_MESSAGE = "Your cat fact has been submitted and is waiting moderation. Please check back later using the value from the location header.";

    @Autowired
    CatFactRepository catFactRepository;

    @RequestMapping("/catfacts/random")
    public ResponseEntity<CatFact> getRandomCatFact() {
        // Todo: Cache this
        long totalFacts = catFactRepository.count();
        logger.debug("Total facts found: {}", totalFacts);
        long random = randFactNo(totalFacts);
        logger.debug("Getting random fact no:  {}", random);
        CatFact catFact = catFactRepository.findOne(random);
        HttpHeaders headers = createHeaders(catFact);
        return new ResponseEntity<>(catFact, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/catfacts", method = RequestMethod.POST)
    public ResponseEntity<String> createCatFact(@RequestBody CatFact catFact) {
        logger.debug("Received POST request: {}", catFact);
        catFact.setModerated(false);
        catFact = catFactRepository.save(catFact);
        logger.debug("Saved catfact: {}", catFact);
        HttpHeaders headers = createHeaders(catFact);
        return new ResponseEntity<>(MODERATION_MESSAGE, headers, HttpStatus.CREATED);
    }

    private HttpHeaders createHeaders(CatFact catFact) {
        HttpHeaders httpHeaders = new HttpHeaders();
        // TODO: Extract external URI, or figure out how to assume
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromUriString("http://localhost:8080/catfacts").path("/{id}").buildAndExpand(catFact.getId()).toUri());
        return httpHeaders;
    }

    public static long randFactNo(long max) {
        return ThreadLocalRandom.current().nextInt(1, (int) (max + 1));
    }
}
