package com.eg.rest;

import com.eg.model.Player;
import com.eg.repository.PlayerRepository;


import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/players")
public class PlayerEndpoint {

    @Inject
    private PlayerRepository playerRepository;

    @GET
    @Path("/{id: \\d+}")
    @Produces(APPLICATION_JSON)
    public Response getPlayer(@PathParam("id") Long id) {
        Player player = playerRepository.find(id);

        if (player == null) {
            Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(player).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response createPlayer(Player player, @Context UriInfo uriInfo) {
        player = playerRepository.create(player);
        URI createdURI = uriInfo.getAbsolutePathBuilder().path(player.getPlayerId().toString()).build();
        return Response.created(createdURI).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    public Response dropPlayer(@PathParam("id") @Min(1) Long id) {

        playerRepository.drop(id);
        return Response.noContent().build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getPlayers() {
        List<Player> players = playerRepository.findAll();
        if (players.size() == 0 ){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(players).build();
    }

    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    public Response countPlayers() {
        Long nbrOfPlayers = playerRepository.countAll();
        if (nbrOfPlayers == 0)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok(nbrOfPlayers).build();
    }




}
