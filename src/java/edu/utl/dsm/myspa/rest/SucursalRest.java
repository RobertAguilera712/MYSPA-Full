package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerSucursal;
import edu.utl.dsm.myspa.model.Sucursal;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("branch")
public class SucursalRest extends Application {

	@Path("insert")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(@QueryParam("s") String s) {

		String out;
		Gson gson = new Gson();
		Sucursal sucursal = gson.fromJson(s, Sucursal.class);

		try {

			ControllerSucursal controllerSucursal = new ControllerSucursal();

			int id = controllerSucursal.insert(sucursal);

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
			ControllerSucursal controllerSucursal = new ControllerSucursal();
			controllerSucursal.delete(Integer.parseInt(id));

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
			ControllerSucursal objCS = new ControllerSucursal();
			List<Sucursal> sucursales = new ArrayList<Sucursal>();
			sucursales = objCS.getAll(Integer.parseInt(estatus));
			Gson objGS = new Gson();
			out = objGS.toJson(sucursales);
		} catch (Exception ex) {
			ex.printStackTrace();
			out = String.format("{\"error\":\"Se produjo un error al cargar el catalogo, vuelva a intentarlo %s\"}", ex.toString());
		}
		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("update")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@QueryParam("s") String s){
		String out;

		Gson gson = new Gson();
		Sucursal sucursal = gson.fromJson(s, Sucursal.class);

		try {

			ControllerSucursal cs = new ControllerSucursal();
			cs.update(sucursal);

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
	public Response search(@QueryParam("filter") String filter, @QueryParam("e") String status){
		String out;
		Gson gson = new Gson();

		try {

			ControllerSucursal cs = new ControllerSucursal();
			out = gson.toJson(cs.search(filter, Integer.valueOf(status)));

		} catch (Exception ex) {
			ex.printStackTrace();

			out = String.format("{\"error\": \"Hubo un error en la busqueda, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

	

	
	

}

