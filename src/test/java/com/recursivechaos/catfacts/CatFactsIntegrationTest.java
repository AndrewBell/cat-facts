/**
 * Created by Andrew Bell 4/24/2016
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2016. See license.txt for details.
 */

package com.recursivechaos.catfacts;

import com.recursivechaos.catfacts.domain.CatFact;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
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
        CatFact response = this.restTemplate.getForObject("/catfacts/random", CatFact.class);
        logger.info("Received response: {}", response);
        assertThat(response.getFact()).isNotEmpty();
        assertThat(response.getId()).isNotNull();
    }
}
