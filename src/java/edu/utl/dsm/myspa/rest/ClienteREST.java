package edu.utl.dsm.myspa.rest;

import com.google.gson.Gson;
import edu.utl.dsm.myspa.controller.ControllerCliente;
import edu.utl.dsm.myspa.controller.ControllerEmpleado;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Usuario;
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
    public Response getAll(@QueryParam("e") String e, @QueryParam("t") String t) {
        String out = "";
        ControllerEmpleado cle = new ControllerEmpleado();
        ControllerCliente ce = new ControllerCliente();
        Gson gs = new Gson();
        try {
            if (cle.validateToken(t)) {
                List<Cliente> empleados = ce.getAll(Integer.parseInt(e));
                out = gs.toJson(empleados);
            }
        } catch (Exception ex) {
            out = String.format("\"error\": \"Hubo un error %s\"", ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("new") @DefaultValue("") String cliente, @FormParam("t") String t) {
        String out = "";
        ControllerCliente objCE = new ControllerCliente();
        ControllerEmpleado ce = new ControllerEmpleado();
        try {
            if (ce.validateToken(t)) {
                Gson objGS = new Gson();

                Cliente objC = objGS.fromJson(cliente, Cliente.class);
                System.out.println(cliente.toString());

                int idG = objCE.insert(objC);
                out = "{\"idGenerado\":" + idG + "}";
            }
        } catch (Exception ex) {
            out = String.format("{\"error\":\"Hubo un fallo en la inserción,"
                    + "vuelve a intentarlo, o llama al administrador del sistema %s\"}", ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@FormParam("new") @DefaultValue("") String cliente, @FormParam("t") String t) {
        String out = "";
        ControllerEmpleado ce = new ControllerEmpleado();
        ControllerCliente cc = new ControllerCliente();
        try {
            if (ce.validateToken(t) || cc.validateToken(t)) {
                Gson objGS = new Gson();
                Cliente objC = objGS.fromJson(cliente, Cliente.class);
                ControllerCliente objCE = new ControllerCliente();
                objCE.update(objC);
                out = "{\"result\":\"La modificación del empleado resultó exitosa\"}";
            }
        } catch (Exception ex) {
            out = String.format("{\"error\":\"Hubo un fallo en la modificación,"
                    + "vuelve a intentarlo, o llama al administrador del sistema %s\"}", ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @QueryParam("e") String status, @QueryParam("t") String t) {
        String out = "";
        Gson gson = new Gson();
        ControllerEmpleado cle = new ControllerEmpleado();
        try {
            if (cle.validateToken(t)) {
                ControllerCliente ce = new ControllerCliente();
                out = gson.toJson(ce.search(filter, Integer.valueOf(status)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            out = String.format("{\"error\": \"Hubo un error en la busqueda, vuelve a inentarlo o llama al administrador del sistema %s\"}", ex.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") String id, @QueryParam("t") String t) {
        String out = "";
        ControllerEmpleado ce = new ControllerEmpleado();
        try {
            if (ce.validateToken(t)) {
                ControllerCliente objCC = new ControllerCliente();
                objCC.delete(Integer.parseInt(id));
                out = "{\"result\":\"La eliminación resultó exitosa\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"error\":\"Hubo un fallo en la eliminación\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response in(@FormParam("usuario") @DefaultValue("0") String usuario, @FormParam("password") @DefaultValue("0") String password) {
        String out = "";
        try {
            ControllerCliente cc = new ControllerCliente();
            Cliente c = cc.login(usuario, password);
            Gson objGS = new Gson();
            out = objGS.toJson(c);
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
        ControllerEmpleado ce = new ControllerEmpleado();
        try {
            Usuario obju = new Usuario();
            obju.setId(Integer.parseInt(idu));
            ControllerCliente objCL = new ControllerCliente();
            objCL.deleteToken(obju);
            out = "{\"result\":\"Ok\"}";
        } catch (Exception ex) {
            ex.printStackTrace();
            out = "{\"error\":\"Se generó un error en el cierre de sesión\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
