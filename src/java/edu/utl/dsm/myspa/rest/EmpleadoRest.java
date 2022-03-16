package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerEmpleado;
import edu.utl.dsm.myspa.model.Empleado;
import edu.utl.dsm.myspa.model.Usuario;
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
    public Response getAll(@QueryParam("e") String e, @QueryParam("t") String t) {
        String out;

        ControllerEmpleado ce = new ControllerEmpleado();
        Gson gs = new Gson();
        try {
//            if (ce.validateToken(t)) {
                List<Empleado> empleados = ce.getAll(Integer.parseInt(e));
                out = gs.toJson(empleados);
//            } else {
//                out = "{\"error\":\"Acceso denegado al API\"}";
//            }
        } catch (Exception ex) {
            out = String.format("\"error\": \"Hubo un error %s\"", ex.toString());
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
                out = gson.toJson(ce.search(filter, Integer.valueOf(status)));
            } else {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            out = String.format("{\"error\": \"Hubo un error en la busqueda, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("new") @DefaultValue("") String empleado, @QueryParam("t") String t) {
        String out = "";
        ControllerEmpleado objCE = new ControllerEmpleado();
        try {
//            if (objCE.validateToken(t)) {
                Gson objGS = new Gson();
                Empleado objE = objGS.fromJson(empleado, Empleado.class);
                System.out.println(empleado.toString());
                int idG = objCE.insert(objE);
                out = "{\"idGenerado\":" + idG + "}";
//            } else {
//                out = "{\"error\":\"Acceso denegado al API\"}";
//            }
        } catch (Exception ex) {
            out = String.format("{\"error\":\"Hubo un fallo en la inserción,"
                    + "vuelve a intentarlo, o llama al administrador del sistema %s\"}", ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("new") @DefaultValue("") String empleado, @QueryParam("t") String t) {
        String out = "";
        ControllerEmpleado objCE = new ControllerEmpleado();
        try {
            if (objCE.validateToken(t)) {
                Gson objGS = new Gson();
                Empleado objE = objGS.fromJson(empleado, Empleado.class);
                objCE.update(objE);
                out = "{\"result\":\"La modificación del empleado resultó exitosa\"}";
            } else {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception ex) {
            out = String.format("{\"error\":\"Hubo un fallo en la modificación,"
                    + "vuelve a intentarlo, o llama al administrador del sistema %s\"}", ex.toString());
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
                ce.delete(Integer.parseInt(id));

                out = "{\"result\": \"Eliminación exitosa\"}";
            } else {
                out = "{\"error\":\"Acceso denegado al API\"}";
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            out = String.format("{\"error\": \"Hubo un error en la eliminación, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response in(@FormParam("usuario") @DefaultValue("0") String usuario, @FormParam("password") @DefaultValue("0") String password) {
        String out = "";
        try {
            ControllerEmpleado ce = new ControllerEmpleado();
            Empleado e = ce.login(usuario, password);
            Gson objGS = new Gson();
            out = objGS.toJson(e);
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Hubo un fallo en el acceso, verifica la información. \"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("out")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response out(@FormParam("idu") @DefaultValue("0") String idu) {
        String out = "";
        try {
            Usuario obju = new Usuario();
            obju.setId(Integer.parseInt(idu));
            ControllerEmpleado objCL = new ControllerEmpleado();
            objCL.deleteToken(obju);
            out = "{\"result\":\"Ok\"}";
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Se generó un error en el cierre de sesión\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
