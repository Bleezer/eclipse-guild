package com.eg.rest;

import com.eg.model.Player;
import com.eg.repository.PlayerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


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
@Api("Player")
public class PlayerEndpoint {

    @Inject
    private PlayerRepository playerRepository;

    @GET
    @Path("/{id: \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Returns a given player", response = Player.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Player found"),
            @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
            @ApiResponse(code = 404, message = "Player not found")
    }
    )
    public Response getPlayer(@PathParam("id") @Min(1) Long id) {
        Player player = playerRepository.find(id);

        if (player == null) {
            Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(player).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @ApiOperation("Creates a player given a JSon Player representation")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The player is created"),
            @ApiResponse(code = 415, message = "Format is not JSon")
    })
    public Response createPlayer(Player player, @Context UriInfo uriInfo) {
        player = playerRepository.create(player);
        URI createdURI = uriInfo.getAbsolutePathBuilder().path(player.getPlayerId().toString()).build();
        return Response.created(createdURI).build();
    }

    @DELETE
    @Path("/{id : \\d+}")
    @ApiOperation("Deletes a player given an id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Player has been deleted"),
            @ApiResponse(code = 400, message = "Invalid input. Id cannot be lower than 1"),
            @ApiResponse(code = 500, message = "Player not found")
    })
    public Response dropPlayer(@PathParam("id") @Min(1) Long id) {

        playerRepository.drop(id);
        return Response.noContent().build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation(value = "Returns all the players", response = Player.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Player found"),
            @ApiResponse(code = 204, message = "No players found"),
    })
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
    @ApiOperation(value = "Returns the number of players", response = Long.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Number of players found"),
            @ApiResponse(code = 204, message = "No players found"),
    })
    public Response countPlayers() {
        Long nbrOfPlayers = playerRepository.countAll();
        if (nbrOfPlayers == 0)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok(nbrOfPlayers).build();
    }




}
