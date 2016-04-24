/**
 * Created by Andrew Bell 12/29/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.catfacts;

import com.recursivechaos.catfacts.controller.CatFactController;
import com.recursivechaos.catfacts.domain.CatFact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CatFactControllerTest {

    private static final Logger logger = getLogger(CatFactControllerTest.class);

    @Autowired
    CatFactController catFactController;

    @Test
    public void testRequest() throws Exception {
        CatFact randomCatFact = catFactController.getRandomCatFact();
        logger.info("Test body received: " + randomCatFact);
        assertNotNull("Did not get cat fact", randomCatFact.getFact());
    }
}
