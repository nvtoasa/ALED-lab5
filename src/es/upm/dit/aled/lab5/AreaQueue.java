package es.upm.dit.aled.lab5;

import java.util.LinkedList;
import java.util.Queue;

import es.upm.dit.aled.lab5.gui.Position2D;

/**
 * Extension of Area that maintains a strict queue of the Patients waiting to
 * enter in it. After a Patient exits, the first one in the queue will be
 * allowed to enter.
 * 
 * @author rgarciacarmona
 */
public class AreaQueue extends Area {

	private Queue<Patient> cola;
	
	public AreaQueue(String name, int time, int capacity, Position2D position) {
		super(name, time, capacity, position);
		this.cola = new LinkedList<Patient>();
	}
	
	@Override
	public synchronized void enter(Patient patient) {
		try {
			this.waiting++;
			this.cola.offer(patient);
			while (this.cola.peek() != patient || this.numPatients >= this.capacity) {
				System.out.println("Patient " + patient.getNumber() + " waiting for " + this.name);
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt(); // Restore interrupted status
		}
		this.cola.remove(); // Dequeue from the front
		this.numPatients++;
		this.waiting--;
		System.out.println("Patient " + patient.getNumber() + " has entered " + this.name);
	}
	// SOLUCION
}