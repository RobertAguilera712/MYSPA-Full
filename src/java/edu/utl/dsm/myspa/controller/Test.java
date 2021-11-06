package edu.utl.dsm.myspa.controller;

import edu.utl.dsm.myspa.model.Empleado;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

	public static void main(String[] args) {
		try {
			ControllerEmpleado ce = new ControllerEmpleado();
			ce.delete(5);
		} catch (Exception ex) {
			Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
