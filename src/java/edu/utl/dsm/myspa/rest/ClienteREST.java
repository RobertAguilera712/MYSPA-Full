package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerCliente;
import edu.utl.dsm.myspa.model.Cliente;
import java.util.ArrayList;
import java.util.List;
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

@Path("cliente")
public class ClienteREST extends Application {

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("e") String e) {
        String out;

		ControllerCliente ce = new ControllerCliente();
		Gson gs = new Gson();
		try {
			List<Cliente> empleados = ce.getAll(Integer.parseInt(e));
			out = gs.toJson(empleados);
		} catch (Exception ex) {
			out = String.format("\"error\": \"Hubo un error %s\"", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("new") @DefaultValue("") String cliente) {
        String out = "";
		try {
			Gson objGS = new Gson();

			Cliente objC = objGS.fromJson(cliente, Cliente.class);
			System.out.println(cliente.toString());

			ControllerCliente objCE = new ControllerCliente();
			int idG = objCE.insert(objC);
			out = "{\"idGenerado\":" + idG + "}";
		} catch (Exception ex) {
			out = String.format("{\"error\":\"Hubo un fallo en la inserción,"
					+ "vuelve a intentarlo, o llama al administrador del sistema %s\"}", ex.toString());
		}
		return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("new") @DefaultValue("") String cliente) {
        String out = "";
		try {
			Gson objGS = new Gson();
			Cliente objC = objGS.fromJson(cliente, Cliente.class);
			ControllerCliente objCE = new ControllerCliente();
			objCE.update(objC);
			out = "{\"result\":\"La modificación del empleado resultó exitosa\"}";
		} catch (Exception ex) {
			out = String.format("{\"error\":\"Hubo un fallo en la modificación,"
					+ "vuelve a intentarlo, o llama al administrador del sistema %s\"}", ex.toString());
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

			ControllerCliente ce = new ControllerCliente();
			out = gson.toJson(ce.search(filter, Integer.valueOf(status)));

		} catch (Exception ex) {
			ex.printStackTrace();

			out = String.format("{\"error\": \"Hubo un error en la busqueda, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") String id) {
        String out = "";
        try {
            ControllerCliente objCC = new ControllerCliente();
            objCC.delete(Integer.parseInt(id));
            out = "{\"result\":\"La eliminación resultó exitosa\"}";
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Hubo un fallo en la eliminación\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}