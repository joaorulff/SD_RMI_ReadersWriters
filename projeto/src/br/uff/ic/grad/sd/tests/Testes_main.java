package br.uff.ic.grad.sd.tests;

import java.io.File;
import java.rmi.RemoteException;

import br.uff.ic.grad.sd.server.ReaderWriterImpl;

public class Testes_main {
	
	public static void main(String[] args) throws RemoteException {
		
		ReaderWriterImpl serverImpl = new ReaderWriterImpl(2);
		serverImpl.read("othersource/arquivo2.txt", 2, 4);
		
		
		
		
	}

}
