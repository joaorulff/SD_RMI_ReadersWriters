package br.uff.ic.grad.sd.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServInit {
	
	public static void main(String[] args) throws RemoteException {
		System.out.println("Registrando o objeto servidor...");
        // Registra o objeto servidor
        Registry reg = LocateRegistry.createRegistry(1099);
        
        reg.rebind("server", new Servidor());

        System.out.println("Servidor pronto para receber requisicoes de clientes!");
		
		
		
	}

}
