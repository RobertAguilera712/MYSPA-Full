/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerEmpleado;
import edu.utl.dsm.myspa.controller.ControllerReservacion;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Horario;
import edu.utl.dsm.myspa.model.Reservacion;
import edu.utl.dsm.myspa.model.Sala;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author kasparov
 */
@Path("reservation")
public class ReservacionRest extends Application {

	@Path("getHourAv")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHourAv(@QueryParam("fecha") String fecha,
			@QueryParam("sala") String sala,
			@QueryParam("t") String t) {
		ControllerEmpleado objCL = new ControllerEmpleado();
		String out = "";
		try {
			if (!t.equals("") && (objCL.validateToken(t))) {
				ControllerReservacion objCR = new ControllerReservacion();
				List<Horario> horarios = objCR.getHourAv(fecha, Integer.parseInt(sala));
				Gson objGS = new Gson();
				out = objGS.toJson(horarios);
			} else {
				out = "{\"error\":\"Acceso denegado al API\"}";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			out = "{\"error\":\"Se produjo un error al cargar las horas disponibles\"}";
		}
		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("insert")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(@QueryParam("estatus") String estatus,
			@QueryParam("cliente") String cliente,
			@QueryParam("fecha") String fecha,
			@QueryParam("sala") String sala,
			@QueryParam("horario") String horario,
			@QueryParam("t") String t) {
		ControllerEmpleado objCL = new ControllerEmpleado();
		String out = "";
		try {
			if (!t.equals("") && (objCL.validateToken(t))) {
				Cliente objC = new Cliente();
				objC.setId(Integer.parseInt(cliente));
				Sala objS = new Sala();
				objS.setId(Integer.parseInt(horario));
				Horario objH = new Horario();
				objH.setId(Integer.parseInt(horario));
				Reservacion objR = new Reservacion();
				objR.setCliente(objC);
				objR.setEstatus(Integer.parseInt(estatus));
				objR.setFecha(fecha);
				objR.setHorario(objH);
				objR.setSala(objS);
				ControllerReservacion objCR = new ControllerReservacion();
				objCR.insert(objR);
				out = "{\"result\":\"Reservación almacenada con éxito\"}";
			} else {
				out = "{\"error\":\"Acceso denegado al API\"}";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			out = String.format("{\"exception\":\"Se produjo un error al insertar la reservación, vuelva a intentarlo %s\"}", ex.toString());
		}
		return Response.status(Response.Status.OK).entity(out).build();
	}

	@Path("getAll")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@QueryParam("e") String estatus, @QueryParam("t") String t) {
		ControllerEmpleado ce = new ControllerEmpleado();
		String out;
		try {
			if (ce.validateToken(t)) {
				ControllerReservacion objcr = new ControllerReservacion();
				List<Reservacion> reservaciones = new ArrayList<Reservacion>();
				reservaciones = objcr.getAll(Integer.parseInt(estatus));
				Gson objGS = new Gson();
				out = objGS.toJson(reservaciones);
			} else {
				out = "{\"error\":\"Acceso denegado al API\"}";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			out = String.format("{\"error\":\"Se produjo un error al cargar el catalogo de Resevaciones, vuelva a intentarl %s\"}", ex.toString());
		}
		return Response.status(Response.Status.OK).entity(out).build();
	}
}