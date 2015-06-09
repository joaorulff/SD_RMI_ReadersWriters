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

public class Cliente {
  // Metodo principal
	
	
  public static void main (String[] args) throws RemoteException{
     Scanner in= new Scanner(System.in);
     int escolha;    
     boolean acabou = false;
     String path;

     Registry reg = LocateRegistry.getRegistry("127.0.0.1",1099);
    // Especifica o nome do servidor e do objeto para obter um stub para acessar o objeto servidor
/*
     String [] vetor = reg.list();
     System.out.println("Bindes");
     for (int i = 0; i < vetor.length; i++) {
		System.out.println(vetor[i]);
	}
*/
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
                        irw.read(path, linha, qtdlinhas);
			break;
		}
		case 2:{
			System.out.println("Entre com o arquivo desejado");
			int arqNum = in.nextInt();
			path="C:\\Users\\Livia\\Documents\\NetBeansProjects\\SD_RMI_ReadersWriters\\othersource\\arquivo1.txt";
                        System.out.println(path);
			System.out.print("Entre com os dados ");
			String dados=in.next();
			System.out.println("Resultado:\\n ");
                        irw.write(path, dados);
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
}
