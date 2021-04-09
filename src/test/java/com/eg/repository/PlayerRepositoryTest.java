package com.eg.repository;

import com.eg.model.Player;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;


@RunWith(Arquillian.class)
public class PlayerRepositoryTest {

    private static Long playerId;

    @Inject
    private PlayerRepository playerRepository;

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(PlayerRepository.class)
                .addClass(Player.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }

    // ======================================
    // =            Test methods            =
    // ======================================

    @Test
    @InSequence(1)
    public void shouldBeDeployed() {
        assertNotNull(playerRepository);
    }

    @Test
    @InSequence(2)
    public void shouldGetNoPlayer() {
        // Count all
        assertEquals(Long.valueOf(0), playerRepository.countAll());
        // Find all
        assertEquals(0, playerRepository.findAll().size());
    }

    @Test(expected = Exception.class)
    @InSequence(3)
    public void shouldFailCreatingANullPlayer() {
        playerRepository.create(null);
    }

    @Test
    @InSequence(4)
    public void shouldCreateAPlayer() {
        Player player1 = new Player("Balazs", "bb@email.com");
        player1 = playerRepository.create(player1);
        assertEquals(Long.valueOf(1), playerRepository.countAll());

        playerId = player1.getPlayerId();
        assertNotNull(playerId);
    }
}
