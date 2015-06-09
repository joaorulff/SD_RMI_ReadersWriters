package br.uff.ic.grad.sd.tests;

import java.io.File;
import java.rmi.RemoteException;

import br.uff.ic.grad.sd.server.ReaderWriterImpl;
import client.Cliente;

public class Testes_main {
	
	public static void main(String[] args) throws RemoteException {
		Cliente c1 = new Cliente();
                Cliente c3 = new Cliente();
                Cliente c2 = new Cliente();                
		Thread tc1 = new Thread(c1);
                Thread tc2 = new Thread(c2);
                Thread tc3 = new Thread(c3);
                Cliente c4 = new Cliente();                
		Thread tc4 = new Thread(c1);
                Cliente c5 = new Cliente();                
		Thread tc5 = new Thread(c1);
                Cliente c6 = new Cliente();                
		Thread tc6 = new Thread(c1);
                Cliente c7 = new Cliente();                
		Thread tc7 = new Thread(c1);
                
		tc1.start();                
                tc2.start();
                tc3.start();
                tc4.start();
                tc5.start();
                tc6.start();
                tc7.start();
                
		
		
		
	}

}
