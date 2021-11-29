
package edu.utl.dsm.myspa.rest;


import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerSala;
import edu.utl.dsm.myspa.model.Sala;
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

@Path("room")
public class SalaRest extends Application {
    @Path("insert")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(@FormParam("new") @DefaultValue("") String s) {

		String out;
		Gson gson = new Gson();
		Sala sala = gson.fromJson(s, Sala.class);

		try {

			ControllerSala controllerSala = new ControllerSala();

			int id = controllerSala.insert(sala);

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
			ControllerSala controllerSala = new ControllerSala();
			controllerSala.delete(Integer.parseInt(id));

			out = "{\"result\": \"Eliminación exitosa\"}";

		} catch (Exception ex) {
			ex.printStackTrace();

			out = String.format("{\"error\": \"Hubo un error en la eliminación, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("getAll")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@QueryParam("e") String estatus) {
		String out = "";
		try {
			ControllerSala objCS = new ControllerSala();
			List<Sala> sala = new ArrayList<Sala>();
			sala = objCS.getAll(Integer.parseInt(estatus));
			Gson objGS = new Gson();
			out = objGS.toJson(sala);
		} catch (Exception ex) {
			ex.printStackTrace();
			out = String.format("{\"error\":\"Se produjo un error al cargar el catalogo, vuelva a intentarlo %s\"}", ex.toString());
		}
		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("update")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@FormParam("new") @DefaultValue("") String s) {
		String out;

		Gson gson = new Gson();
		Sala sala = gson.fromJson(s, Sala.class);

		try {

			ControllerSala cs = new ControllerSala();
			cs.update(sala);

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

			ControllerSala cs = new ControllerSala();
			out = gson.toJson(cs.search(filter, Integer.valueOf(status)));

		} catch (Exception ex) {
			ex.printStackTrace();

			out = String.format("{\"error\": \"Hubo un error en la busqueda, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

}


