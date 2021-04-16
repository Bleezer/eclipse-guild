package com.eg.repository;

import com.eg.model.Player;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.List;


@Transactional(SUPPORTS)
public class PlayerRepository {

    @PersistenceContext(unitName = "eclipseGuildPU")
    private EntityManager em;

    public Player find(@NotNull Long id){
        return em.find(Player.class, id);
    }

    @Transactional(REQUIRED)
    public Player create(@NotNull Player player){
        em.persist(player);
        return player;
    }

    @Transactional(REQUIRED)
    public void drop(@NotNull Long id){
        em.remove(em.getReference(Player.class, id));
    }

    public List<Player> findAll() {
        TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p ORDER BY p.playerName DESC", Player.class);
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM Player p", Long.class);
        return query.getSingleResult();
    }
}
