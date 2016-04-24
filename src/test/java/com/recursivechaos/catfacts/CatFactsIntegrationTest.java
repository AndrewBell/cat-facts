/**
 * Created by Andrew Bell 4/24/2016
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2016. See license.txt for details.
 */

package com.recursivechaos.catfacts;

import com.recursivechaos.catfacts.controller.CatFactController;
import com.recursivechaos.catfacts.domain.CatFact;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CatFactsIntegrationTest {

    private static final Logger logger = getLogger(CatFactsIntegrationTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetRandomFact() throws Exception {
        ResponseEntity<CatFact> response = this.restTemplate.getForEntity("/catfacts/random", CatFact.class);
        logger.info("Received response: {}", response);
        assertEquals("Did not return an OK response", HttpStatus.OK, response.getStatusCode());
        assertNotNull("Did not get a fact", response.getBody().getFact());
        assertNotNull("Did not get an id", response.getBody().getId());
    }

    @Test
    public void testPostNew() throws Exception {
        CatFact catFact = new CatFact("Cat's are certifiably evil");
        ResponseEntity<String> response = this.restTemplate.postForEntity("/catfacts", catFact, String.class);
        assertEquals("Did not return an OK response", HttpStatus.CREATED, response.getStatusCode());
        assertNotNull("Did not return location header", response.getHeaders().getLocation());
        assertEquals("Did not return moderation message", CatFactController.MODERATION_MESSAGE, response.getBody());
    }
}
