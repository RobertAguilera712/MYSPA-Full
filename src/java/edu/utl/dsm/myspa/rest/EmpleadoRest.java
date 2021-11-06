package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerEmpleado;
import edu.utl.dsm.myspa.model.Empleado;
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

@Path("employee")
public class EmpleadoRest extends Application {

	@Path("getAll")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@QueryParam("e") String e) {
		String out;

		ControllerEmpleado ce = new ControllerEmpleado();
		Gson gs = new Gson();
		try {
			List<Empleado> empleados = ce.getAll(Integer.parseInt(e));
			out = gs.toJson(empleados);
		} catch (Exception ex) {
			out = String.format("\"error\": \"Hubo un error %s\"", ex.toString());
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

			ControllerEmpleado ce = new ControllerEmpleado();
			out = gson.toJson(ce.search(filter, Integer.valueOf(status)));

		} catch (Exception ex) {
			ex.printStackTrace();

			out = String.format("{\"error\": \"Hubo un error en la busqueda, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("insert")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(@FormParam("new") @DefaultValue("") String empleado) {
		String out = "";
		try {
			Gson objGS = new Gson();

			Empleado objE = objGS.fromJson(empleado, Empleado.class);
			System.out.println(empleado.toString());

			ControllerEmpleado objCE = new ControllerEmpleado();
			int idG = objCE.insert(objE);
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
	public Response update(@FormParam("new") @DefaultValue("") String empleado) {
		String out = "";
		try {
			Gson objGS = new Gson();
			Empleado objE = objGS.fromJson(empleado, Empleado.class);
			ControllerEmpleado objCE = new ControllerEmpleado();
			objCE.update(objE);
			out = "{\"result\":\"La modificación del empleado resultó exitosa\"}";
		} catch (Exception ex) {
			out = String.format("{\"error\":\"Hubo un fallo en la modificación,"
					+ "vuelve a intentarlo, o llama al administrador del sistema %s\"}", ex.toString());
		}
		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("delete")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@QueryParam("id") String id) {
		String out;

		try {
			ControllerEmpleado ce = new ControllerEmpleado();
			ce.delete(Integer.parseInt(id));

			out = "{\"result\": \"Eliminación exitosa\"}";

		} catch (Exception ex) {
			ex.printStackTrace();

			out = String.format("{\"error\": \"Hubo un error en la eliminación, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

}
