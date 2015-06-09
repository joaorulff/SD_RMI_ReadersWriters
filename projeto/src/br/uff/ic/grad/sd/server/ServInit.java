package br.uff.ic.grad.sd.server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServInit {
	
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		System.out.println("Registrando o objeto servidor...");
        // Registra o objeto servidor
          
          Registry reg = LocateRegistry.createRegistry(1099);
          Servidor s = new Servidor();
          reg.rebind("server", s);
          

        System.out.println("Servidor pronto para receber requisicoes de clientes!");
		
		
		
	}

}
