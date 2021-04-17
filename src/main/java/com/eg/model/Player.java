package com.eg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@ApiModel(description = "Player resource representation")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty("Identifier")
    private Long playerId;

    @Column(length = 100, name = "PLAYER_NAME")
    @NotNull
    @Size(min = 1, max = 100)
    @ApiModelProperty("Name of the player")
    private String playerName;

    @Column
    @NotNull
    @ApiModelProperty("Email of the player")
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
