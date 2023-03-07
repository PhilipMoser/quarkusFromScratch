package at.fhj.msd20.rest;

import at.fhj.msd20.service.FhjService;

import javax.inject.Inject;
import javax.ws.rs.*;

@Path("/fhj")
public class FhjRestApi {

    @Inject
    FhjService service;

    @GET
    public String sayHallo(){
        return service.sayHallo();
    };

    @GET
    @Path("{name}")
    public String sayHelloWithName(@PathParam("name") String name){
        return "hello " + name +" ! :)";
    }







}
