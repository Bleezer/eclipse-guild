package com.eg.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long playerId;

    @Column(length = 100, name = "PLAYER_NAME")
    @NotNull
    @Size(min = 1, max = 100)
    private String playerName;

    @Column
    @NotNull
    private String email;

    // ======================================
    // =            Constructors            =
    // ======================================

    public Player(){
    }

    public Player(String playerName, String email) {
        this.playerName = playerName;
        this.email = email;
    }


    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
