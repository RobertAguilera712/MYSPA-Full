package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerTratamiento;
import edu.utl.dsm.myspa.model.Tratamiento;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("treatment")
public class TratamientoRest extends Application {
    
    //http://localhost:8080/MySpa/api/treatment/getAll?e=1
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("e") String estatus) {
        String out = "";
        try {
            ControllerTratamiento objCT = new ControllerTratamiento();
            List<Tratamiento> tratamientos = new ArrayList<Tratamiento>();
            tratamientos = objCT.getAll(Integer.parseInt(estatus));
            Gson objGS = new Gson();
            out = objGS.toJson(tratamientos);
        } catch (Exception ex) {
            ex.printStackTrace();
            out = String.format("{\"error\":\"Se produjo un error al cargar el catalogo, vuelva a intentarlo %s\"}", ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    //http://localhost:8080/MySpa/api/treatment/insert?new={id:0,nombre:'Coco milk 2',descripcion:'Un tratamiento genial',costo:120.00,estatus:1}
    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("new") String s) {

        String out;
        Gson gson = new Gson();
        Tratamiento tratamiento = gson.fromJson(s, Tratamiento.class);

        try {

            ControllerTratamiento ct = new ControllerTratamiento();

            int id = ct.insert(tratamiento);

            out = String.format("{\"idGenerado\": \"%d\"}", id);
        } catch (Exception ex) {
            ex.printStackTrace();

            out = String.format("{\"error\": \"Hubo un error en la inserción, vuelve a intentarlo o llama al administrador del sistema %s\"}", ex.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") String id) {
        String out;

        try {
            ControllerTratamiento ct = new ControllerTratamiento();
            ct.delete(Integer.parseInt(id));

            out = "{\"result\": \"Eliminación exitosa\"}";

        } catch (Exception ex) {
            ex.printStackTrace();

            out = String.format("{\"error\": \"Hubo un error en la eliminación, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("update")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("new") String s) {
        String out;

        Gson gson = new Gson();
        Tratamiento tratamiento = gson.fromJson(s, Tratamiento.class);

        try {

            ControllerTratamiento cs = new ControllerTratamiento();
            cs.update(tratamiento);

            out = "{\"result\": \"Modificación exitosa\"}";

        } catch (Exception ex) {
            ex.printStackTrace();

            out = String.format("{\"error\": \"Hubo un error en la modificación, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @QueryParam("e") String status) {
        String out;
        Gson gson = new Gson();

        try {

            ControllerTratamiento ct = new ControllerTratamiento();
            out = gson.toJson(ct.search(filter, Integer.valueOf(status)));

        } catch (Exception ex) {
            ex.printStackTrace();

            out = String.format("{\"error\": \"Hubo un error en la busqueda, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
}
