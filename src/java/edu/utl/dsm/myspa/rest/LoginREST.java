
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.model.Empleado;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("log")   
public class LoginREST extends Application{

@Path("in")
@POST
@Produces(MediaType.APPLICATION_JSON)
public Response in(@FormParam("usuario") @DefaultValue("0") String usuario, @FormParam("password") @DefaultValue("0") String password)
{
    String out="";
    try{
        ControllerLogin cL = new ControllerLogin();
        Empleado e = cL.login(usuario, password);
        Gson objGS = new Gson();
        out= objGS.toJson(e);
    }catch (Exception ex){
        ex.printStackTrace();
        out="{\"error\":\"Hubo un fallo en el acceso, verifica la información. \"}";
    }
    return Response.status(Response.Status.OK).entity(out).build();
}}
