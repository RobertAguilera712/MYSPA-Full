package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerEmpleado;
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

	@Path("getAll")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@QueryParam("e") String estatus, @QueryParam("t") String t) {
		ControllerEmpleado ce = new ControllerEmpleado();
		String out;
		try {
			if (ce.validateToken(t)) {
				ControllerSucursal objcs = new ControllerSucursal();
				List<Sucursal> sucursales = new ArrayList<Sucursal>();
				sucursales = objcs.getAll(Integer.parseInt(estatus));
				Gson objGS = new Gson();
				out = objGS.toJson(sucursales);
			} else {
				out = "{\"error\":\"Acceso denegado al API\"}";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			out = String.format("{\"error\":\"Se produjo un error al cargar el catalogo de Sucursales, vuelva a intentarl %s\"}", ex.toString());
		}
		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("insert")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(@QueryParam("new") String s, @QueryParam("t") String t) {

		String out;
		Gson gson = new Gson();
		Sucursal sucursal = gson.fromJson(s, Sucursal.class);
		ControllerEmpleado ce = new ControllerEmpleado();
		try {

			if (ce.validateToken(t)) {
				ControllerSucursal controllerSucursal = new ControllerSucursal();

				int id = controllerSucursal.insert(sucursal);

				out = String.format("{\"idGenerado\": \"%d\"}", id);
			} else {
				out = "{\"error\":\"Acceso denegado al API\"}";
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			out = String.format("{\"error\": \"Hubo un error en la inserci??n, vuelve a intentarlo o llama al administrador del sistema %s\"}", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("delete")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam("id") String id, @QueryParam("t") String t) {
		String out;
		ControllerEmpleado ce = new ControllerEmpleado();
		try {
			if (ce.validateToken(t)) {
				ControllerSucursal controllerSucursal = new ControllerSucursal();
				controllerSucursal.delete(Integer.parseInt(id));
				out = "{\"result\": \"Eliminaci??n exitosa\"}";
			} else {
				out = "{\"error\":\"Acceso denegado al API\"}";
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			out = String.format("{\"error\": \"Hubo un error en la eliminaci??n, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("update")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@QueryParam("new") String s, @QueryParam("t") String t) {
		String out;

		Gson gson = new Gson();
		Sucursal sucursal = gson.fromJson(s, Sucursal.class);
		ControllerEmpleado ce = new ControllerEmpleado();

		try {

			if (ce.validateToken(t)) {
				ControllerSucursal cs = new ControllerSucursal();
				cs.update(sucursal);

				out = "{\"result\": \"Modificaci??n exitosa\"}";
			} else {
				out = "{\"error\":\"Acceso denegado al API\"}";
			}

		} catch (Exception ex) {
			ex.printStackTrace();

			out = String.format("{\"error\": \"Hubo un error en la modificaci??n, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@QueryParam("filter") String filter, @QueryParam("e") String status, @QueryParam("t") String t) {
		String out;
		Gson gson = new Gson();
		ControllerEmpleado ce = new ControllerEmpleado();

		try {

			if (ce.validateToken(t)) {
				ControllerSucursal cs = new ControllerSucursal();
				out = gson.toJson(cs.search(filter, Integer.valueOf(status)));
			} else {
				out = "{\"error\":\"Acceso denegado al API\"}";
			}

		} catch (Exception ex) {
			ex.printStackTrace();

			out = String.format("{\"error\": \"Hubo un error en la busqueda, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

}
