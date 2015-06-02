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
import java.rmi.server.*;

import br.uff.ic.grad.sd.server.IReaderWriter;

public class Cliente {
  // Metodo principal
  public static void main (String[] args) {
     Scanner in= new Scanner(System.in);
     int escolha;    
     boolean acabou = true;

    // Instala um gerenciador de seguranca para controlar a carga dinamica dos stubs
    //System.setSecurityManager(new RMISecurityManager());

    // Especifica o nome do servidor e do objeto para obter um stub para acessar o objeto servidor
    String url = "rmi://localhost/Servidor_de_arquivo";
   
    try {
      IReaderWriter i = (IReaderWriter) Naming.lookup(url);
      while (!acabou){
         System.out.print("Digite a opção desejada :\n1-Leitor\n2-Escritor\n3-Sair ");
         escolha=in.nextInt();
         switch(escolha){
		case 1:{
			System.out.print("Entre com a linha inicial e a quantidade de linhas:  ");
			int linha=in.nextInt();
			int qtdlinhas=in.nextInt();
			System.out.println("Resultado:\\n");
                        i.read(url, linha, qtdlinhas);
			break;
		}
		case 2:{
			System.out.print("Entre com os dados ");
			String dados=in.next();
			System.out.println("Resultado:\\n ");
                        i.write(url, dados);
			break;
		}
		case 3:{
			acabou = true;
                        break;
		}
   	 }
	System.out.println("");
        
      }
    }
    catch (Exception e) {
      System.out.println("ERRO_CLIENT! " + e);
    }
    System.exit(0);
  }
}
