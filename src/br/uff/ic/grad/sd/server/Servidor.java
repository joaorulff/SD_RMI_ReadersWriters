package br.uff.ic.grad.sd.server;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Livia
 */
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.Scanner;

import sun.applet.*;

public class Servidor extends UnicastRemoteObject implements IReaderWriter{
	
	private static ReaderWriterImpl rwi;
	private static int prioridade;
	
	
	public Servidor() throws RemoteException{
		super();
		System.out.println("Criando um objeto servidor...");
        // Cria um objeto servidor
        
        System.out.println("Escolha prioridade: \n 1-Prioridade para Leitor \n 2- Priodade para escritor \n 3- Sem prioridade");
        Scanner in = new Scanner(System.in);
        prioridade = in.nextInt();
        rwi = new ReaderWriterImpl(prioridade);
	}


	@Override
	public void read(String arquivo, int linhainicial, int qtdlinhas) {
		rwi.read(arquivo, linhainicial, qtdlinhas);
		
	}


	@Override
	public void write(String arquivo, String dados) {
		rwi.write(arquivo, dados);
		
	}

	/*
    public static void main(String[] args) {
        try {
            System.out.println("Criando um objeto servidor...");
            // Cria um objeto servidor
            
            System.out.println("Escolha prioridade: \n 1-Prioridade para Leitor \n 2- Priodade para escritor \n 3- Sem prioridade");
            Scanner in = new Scanner(System.in);
            prioridade = in.nextInt();
            rwi = new ReaderWriterImpl(prioridade);

            System.out.println("Registrando o objeto servidor...");
            // Registra o objeto servidor
            Registry reg = LocateRegistry.createRegistry(1099);
            
            reg.rebind("server", rwi);

            System.out.println("Servidor pronto para receber requisicoes de clientes!");
        } catch (Exception e) {
            System.out.println("ERRO_SERVER!" + e);
        }
    }
    */
}
