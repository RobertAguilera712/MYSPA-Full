package edu.utl.dsm.myspa.rest;

import edu.utl.dsm.myspa.controller.ControllerProducto;
import edu.utl.dsm.myspa.model.Producto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("product")
public class ProductoRest {

	private final static ControllerProducto CP = new ControllerProducto();

    @Path("insert")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@QueryParam("new") String p) {
        String out;
        Producto producto = Utils.GSON.fromJson(p, Producto.class);

        try {
            int id = CP.insert(producto);
            out = String.format(Utils.JSON, "idGenerado", String.valueOf(id));
        } catch (Exception e) {
            out = String.format(Utils.ERROR, "la inserción", e.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("id") String id) {
        String out;

        try {
            CP.delete(Integer.parseInt(id));

            out = String.format(Utils.JSON, "result", "Eliminación exitosa") ;
        } catch (Exception e) {
            out = String.format(Utils.ERROR,  "la eliminación", e.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("e") String e) {
        String out;

        try {
            out = Utils.GSON.toJson(CP.getAll(Integer.parseInt(e)));
        } catch (Exception ex) {
            out = String.format(Utils.ERROR, "", ex.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("update")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("new") String p){
        String out;
		try{
			CP.update(Utils.GSON.fromJson(p, Producto.class));
            out = String.format(Utils.JSON, "result", "Modificación exitosa");
		}catch(Exception e){
			out = String.format(Utils.ERROR, "la modificación",  e.toString());
		}
		
		System.out.println(out);

		return Response.status(Response.Status.OK).entity(out).build();
    }

	@Path("search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@QueryParam("filter") String filter, @QueryParam("e") String e){
		String out;		
		try{
			out = Utils.GSON.toJson(CP.search(filter, Integer.parseInt(e)));
		}catch(Exception ex){
			out = String.format(Utils.ERROR, "la busqueda", ex.toString());
		}

		return Response.status(Response.Status.OK).entity(out).build();
	}

}