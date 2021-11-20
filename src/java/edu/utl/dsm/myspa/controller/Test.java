package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.model.Cliente;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.utl.dsm.myspa.model.Usuario;
import edu.utl.dsm.myspa.model.Persona;

public class Test {

    public static void main(String[] args) {
		probarInsert();
    }
    
    public static void probarGetAll() {
        try {
            ControllerCliente cc = new ControllerCliente();
            List<Cliente> clientes = cc.getAll(1);

            for (int i = 0; i < clientes.size(); i++) {
                System.out.println(clientes.get(i).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void probarInsert() {
        try {
            ControllerCliente cc = new ControllerCliente();
            Persona p = new Persona("Carlos", "Velazco", "Hernandez", "Calle falsa 1234", "4774009871",
			"AUAR011207AN1", "H");
            Usuario u = new Usuario("IvanHK", "12345", "Hacker", null);

            Cliente c = new Cliente();

			c.setEstatus(1);
			c.setCorreo("rovygamer@gmail.com");
			c.setPersona(p);
			c.setUsuario(u);

            int idG = cc.insert(c);
            System.out.println(idG);
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void probarDelete() {
        try {
            ControllerCliente cc = new ControllerCliente();
            cc.delete(16);
            System.out.println("Eliminación exitosa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void probarSearch() {
        try {
            ControllerCliente cc = new ControllerCliente();
            List<Cliente> clientes = cc.search("idCliente = 1", 1);
            System.out.println(clientes);

            for (int i = 0; i < clientes.size(); i++) {
                System.out.println(clientes.get(i).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void probarUpdate(){
        try {
            ControllerCliente cc = new ControllerCliente();
            Persona p = new Persona(141, "A", "A", "A", "D", "T", "C", "H");
            Usuario u = new Usuario(141, "N", "A", "1", null);

            Cliente c = new Cliente(118, 1, "A", "c", p, u);

            cc.update(c);
            System.out.println("Modificación exitosa");
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
