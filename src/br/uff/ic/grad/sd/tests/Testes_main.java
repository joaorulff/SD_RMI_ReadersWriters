package br.uff.ic.grad.sd.tests;

import java.io.File;
import java.rmi.RemoteException;

import br.uff.ic.grad.sd.server.ReaderWriterImpl;

public class Testes_main {
	
	public static void main(String[] args) throws RemoteException {
		
		ReaderWriterImpl serverImpl = new ReaderWriterImpl(1);
		
		for (int i = 0; i < 100; i++) {
			
			int aleatoryActions = (int)(Math.random()*2);
			
			if(aleatoryActions == 0){
				serverImpl.read("othersource/arquivo1.txt", 1, 2);
			}else{
				serverImpl.write("othersource/arquivo1.txt", "arquivo 1 linha inserida"+ i);
			}
			
			
			
			
			
		}
		
		
		
		
		
	}

}
