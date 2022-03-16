package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerEmpleado;
import edu.utl.dsm.myspa.controller.ControllerSala;
import edu.utl.dsm.myspa.controller.ControllerServicio;
import edu.utl.dsm.myspa.model.Sala;
import edu.utl.dsm.myspa.model.Servicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("service")
public class ServicioRest {

    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("s") String s, @QueryParam("t") String t) {
        ControllerEmpleado ce = new ControllerEmpleado();
        String out = "";
        try {
            Gson gson = new Gson();
            Servicio servicio = gson.fromJson(s, Servicio.class);
            ControllerServicio cs = new ControllerServicio();
            int r = cs.insert(servicio);
            out = String.format("{\"result\": \"Servicio %d generado con exito\"}", r);
        } catch (Exception ex) {
            ex.printStackTrace();
            out = String.format("{\"exception\": \"Error al insertar servicio %s\"}", ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
