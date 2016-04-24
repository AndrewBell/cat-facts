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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class CatFactController {

    private static final Logger logger = getLogger(CatFactController.class);

    @Autowired
    CatFactRepository catFactRepository;

    @RequestMapping("/catfacts/random")
    public CatFact getRandomCatFact() {
        // Todo: Cache this
        long totalFacts = catFactRepository.count();
        logger.debug("Total facts found: {}",totalFacts);
        long random = randFactNo(totalFacts);
        logger.debug("Getting random fact no:  {}",random);
        return catFactRepository.findOne(random);
    }

    public static long randFactNo(long max) {
        return ThreadLocalRandom.current().nextInt(1, (int) (max + 1));
    }
}
