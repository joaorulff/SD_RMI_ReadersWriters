package client;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Livia
 */
import java.util.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

import br.uff.ic.grad.sd.server.IReaderWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Runnable {
  // Metodo principal
	
  public static void main (String[] args) throws RemoteException{
     Scanner in= new Scanner(System.in);
     int escolha;    
     boolean acabou = false;
     String path;

     Registry reg = LocateRegistry.getRegistry("127.0.0.1",1099);
     IReaderWriter irw;
	try {
		irw = (IReaderWriter) reg.lookup("server");
		System.out.println("passou aqui");     
    
      while (!acabou){
         System.out.print("Digite a opção desejada :\n1-Leitor\n2-Escritor\n3-Sair ");
         escolha=in.nextInt();
         switch(escolha){
		case 1:{
			System.out.print("Entre com o aqruivo desejado, a linha inicial e a quantidade de linhas:  ");
			int arqNum = in.nextInt();
			path = "othesource/arquivo"+arqNum+".txt";
			int linha=in.nextInt();
			int qtdlinhas=in.nextInt();
			System.out.println("Resultado:\\n");
                        irw.read(arqNum, path, linha, qtdlinhas);
			break;
		}
		case 2:{
			System.out.println("Entre com o arquivo desejado");
			int arqNum = in.nextInt();
			path="C:\\Users\\Livia\\Documents\\NetBeansProjects\\SD_RMI_ReadersWriters\\othersource\\arquivo"+arqNum+".txt";
                        System.out.println(path);
			System.out.print("Entre com os dados ");
			String dados=in.next();
			System.out.println("Resultado:\\n ");
                        irw.write(arqNum, path, dados);
			break;
		}
		case 3:{
			acabou = true;
                        break;
		}
   	 }
  }
      } catch (NotBoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      
      
}

    @Override
    public void run() {
      try {
          Registry reg = LocateRegistry.getRegistry("127.0.0.1",1099);
          IReaderWriter irw;
	  irw = (IReaderWriter) reg.lookup("server");
          System.out.println("passou aqui thread"); 
          long id = Thread.currentThread().getId();
          String x = ""+id;
          if(id%2==0){
              irw.write(1,"1", x);
          }
          else{
              irw.read(1,"1", 1, 2);
          }
          
          
      } catch (RemoteException ex) {
          Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
      } catch (NotBoundException ex) {
          Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
      }
        
    }
}
