/**
 * Created by Andrew Bell 12/29/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.catfacts;

import com.recursivechaos.catfacts.controller.CatFactController;
import com.recursivechaos.catfacts.domain.CatFact;
import com.recursivechaos.catfacts.repository.CatFactRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CatFactControllerTest {

    private static final Logger logger = getLogger(CatFactControllerTest.class);

    @InjectMocks
    CatFactController catFactController;

    @Mock
    CatFactRepository catFactRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRandom() throws Exception {
        CatFact mockCatFact = new CatFact("A cat is a large rodent", true);
        when(catFactRepository.count()).thenReturn(1L);
        when(catFactRepository.findOne(1L)).thenReturn(mockCatFact);

        ResponseEntity<CatFact> catFactResponse = catFactController.getRandomCatFact();
        logger.info("Test body received: " + catFactResponse);
        CatFact catFact = catFactResponse.getBody();

        verify(catFactRepository).findOne(1L);
        verify(catFactRepository).count();
        assertEquals(HttpStatus.OK, catFactResponse.getStatusCode());
        assertEquals("Did not get correct cat fact", mockCatFact.getFact(), catFact.getFact());
        assertEquals("Did not get location header", "/catfacts/" + catFact.getId(), catFactResponse.getHeaders().getLocation().getPath());
    }

    @Test
    public void testPostNew() throws Exception {
        String fact = "Cats literally have sandpaper on their tongues";
        CatFact newCatFact = new CatFact(fact);
        CatFact mockCatFact = new CatFact(fact, false);
        mockCatFact.setId(1L);
        when(catFactRepository.save(any(CatFact.class))).thenReturn(mockCatFact);

        ResponseEntity<String> response = catFactController.createCatFact(newCatFact);

        verify(catFactRepository).save(any(CatFact.class));
        assertEquals("Did not create correct location header", "/catfacts/1", response.getHeaders().getLocation().getPath());
        assertEquals("Did not return created status", HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Did not attach moderation message", CatFactController.MODERATION_MESSAGE, response.getBody());
    }
}
