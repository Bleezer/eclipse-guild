package com.eg.rest;

import com.eg.model.Player;
import com.eg.repository.PlayerRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@RunAsClient
public class PlayerEndpointTest {

    private static String playerId;
    private Response response;

    @Deployment(testable = false)
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(Player.class)
                .addClass(PlayerRepository.class)
                .addClass(PlayerEndpoint.class)
                .addClass(JAXRSConfiguration.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }

    // ======================================
    // =            Test methods            =
    // ======================================

    @Test
    @InSequence(2)
    public void shouldGetNoPlayer(@ArquillianResteasyResource("api/players") WebTarget webTarget) {
        // Count all
        response = webTarget.path("count").request().get();
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
        // Find all
        response = webTarget.request(APPLICATION_JSON).get();
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    @InSequence(3)
    public void shouldCreateAPlayer(@ArquillianResteasyResource("api/players") WebTarget webTarget) {
        // Creates a player
        Player player = new Player("Masodik Misi", "eg@misi.com");
        response = webTarget.request(APPLICATION_JSON).post(Entity.entity(player, APPLICATION_JSON));
        assertEquals(CREATED.getStatusCode(), response.getStatus());
        // Checks the created player
        String location = response.getHeaderString("location");
        assertNotNull(location);
        playerId = location.substring(location.lastIndexOf("/") + 1);
    }

    @Test
    @InSequence(4)
    public void shouldFindTheCreatedBook(@ArquillianResteasyResource("api/players") WebTarget webTarget) {
        // Finds the player
        response = webTarget.path(playerId).request(APPLICATION_JSON).get();
        assertEquals(OK.getStatusCode(), response.getStatus());
        // Checks the found player
        Player playerFound = response.readEntity(Player.class);
        assertNotNull(playerFound.getPlayerId());
        assertTrue(playerFound.getPlayerName().startsWith("Masodik"));
        assertEquals("eg@misi.com", playerFound.getEmail());
    }


}
