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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
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
        CatFact mockCatFact1 = new CatFact("A cat is a large rodent", true);
        CatFact mockCatFact2 = new CatFact("A cat is fat", false);
        List<CatFact> catFactList = new ArrayList<>();
        catFactList.add(mockCatFact1);
        catFactList.add(mockCatFact2);
        when(catFactRepository.findByModeratedTrue()).thenReturn(catFactList);

        ResponseEntity<CatFact> catFactResponse = catFactController.getRandomCatFact();
        logger.info("Test body received: " + catFactResponse);
        CatFact catFact = catFactResponse.getBody();

        verify(catFactRepository).findByModeratedTrue();
        assertEquals("Did not return 200 OK", HttpStatus.OK, catFactResponse.getStatusCode());
        assertNotNull("Did not get cat fact", catFact.getFact());
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
        assertEquals("Did not return 202 ACCEPTED", HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Did not attach moderation message", CatFactController.MODERATION_MESSAGE, response.getBody());
    }

    @Test
    public void testGetNotFound() throws Exception {
        when(catFactRepository.findOne(1L)).thenReturn(null);

        ResponseEntity<CatFact> response = catFactController.getCatFact(1L, new MockHttpServletRequest());

        verify(catFactRepository).findOne(1L);
        assertEquals("Did not return 404 NOT FOUND", HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUnauthorizedModerated() throws Exception {
        String fact = "Cats don't care";
        CatFact mockCatFact = new CatFact(fact, true);

        when(catFactRepository.findOne(1L)).thenReturn(mockCatFact);

        ResponseEntity<CatFact> response = catFactController.getCatFact(1L, new MockHttpServletRequest());

        verify(catFactRepository).findOne(1L);
        assertEquals("Did not return 200 OK", HttpStatus.OK, response.getStatusCode());
        assertEquals("Did not return correct cat fact", mockCatFact.getFact(), response.getBody().getFact());
    }

    @Test(expected = BadCredentialsException.class)
    public void testGetUnauthorizedUnmoderated() throws Exception {
        String fact = "Cats don't care";
        CatFact mockCatFact = new CatFact(fact, false);

        when(catFactRepository.findOne(1L)).thenReturn(mockCatFact);

        catFactController.getCatFact(1L, new MockHttpServletRequest());
    }

    // TODO: Need to mock principal, but unsure of how.
    @Test
    @Ignore
    public void testGetAuthorizedUnmoderated() throws Exception {
        String fact = "Cats don't care";
        CatFact mockCatFact = new CatFact(fact, false);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addUserRole("USER_ADMIN");

        when(catFactRepository.findOne(1L)).thenReturn(mockCatFact);

        ResponseEntity<CatFact> response = catFactController.getCatFact(1L, request);

        verify(catFactRepository).findOne(1L);
        assertEquals("Did not return 200 OK", HttpStatus.OK, response.getStatusCode());
        assertEquals("Did not return correct cat fact", mockCatFact.getFact(), response.getBody().getFact());
    }

}
