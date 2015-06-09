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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.applet.*;

public class Servidor extends UnicastRemoteObject implements IReaderWriter{
	
	
	
	int prioridade;
        int [] numeroLeitores;
        int [] numeroEscritores;
        int [] numeroEscritoresEsperando;
        Semaphores [] sets;
	
	public Servidor() throws RemoteException, MalformedURLException{
		super();
                numeroEscritores = new int [3];
                numeroLeitores = new int [3];
                numeroEscritoresEsperando = new int [3];
                for (int i = 0; i < 2; i++) {
                numeroEscritores[i]=0;
                numeroLeitores[i]=0;
                numeroEscritoresEsperando[i]=0;
            }
                sets = new Semaphores [3];
                sets[0] = new Semaphores();
                sets[1] = new Semaphores();
                sets[2] = new Semaphores();
                for (int j = 0; j < sets.length; j++) {
                sets[j].start();
                
            }
		System.out.println("Criando um objeto servidor...");
                // Cria um objeto servidor        
                System.out.println("Escolha prioridade: \n 1-Prioridade para Leitor \n 2- Priodade para escritor \n 3- Sem prioridade");
                Scanner in = new Scanner(System.in);
                prioridade = in.nextInt();
                
	}

        public void readFile(String arquivo, int linhainicial, int qtdlinhas) {
    	System.out.println("readfile");
        FileReader arq;
        try {
            arq = new FileReader(arquivo);
            BufferedReader buff = new BufferedReader(arq);
            for (int i = 0; i < linhainicial-1; i++) {
                buff.readLine();
            }
            for (int i = 0; i < qtdlinhas; i++) {
                System.out.println(buff.readLine());

            }
            buff.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void writeFile(String arquivo, String dados) {        
        try {
            FileWriter fstream = new FileWriter(arquivo, true); //true tells to append data.
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("" + dados + " ");
            out.close();
            System.out.println("Escreveu \n" + dados + "\n no arquivo:" + arquivo);
        } catch (IOException ex) {

        }
    }

    @Override
    public void read(int arqNum, String arquivo, int linhainicial, int qtdlinhas) {
        System.out.println("entrou no read");
        int posicao = arqNum-1;
        Semaphores semaforosUsando = sets[posicao];

        switch (prioridade) {
            case 1: {
                try {
                    // prioridade para leitor
                    semaforosUsando.mutex.acquire();
                    numeroLeitores[posicao]++;         //Tem mais um lendo agora...
                    if (numeroLeitores[posicao] == 1) {  //Se for o 1Âº, precisa bloquear para escrita
                        semaforosUsando.s.acquire();
                    }
                    semaforosUsando.mutex.release();
                    System.out.println("read");
                    readFile(arquivo, linhainicial, qtdlinhas);
                    semaforosUsando.mutex.acquire();
                    //Acabou de ler, vai informar que acabou diminuindo N
                    numeroLeitores[posicao]--;
                    if (numeroLeitores[posicao] == 0) { //Se nao tem mais ninguem lendo, abre para escrita
                        semaforosUsando.s.release();
                    }
                    semaforosUsando.mutex.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }

            case 2: {
                try {
                    // prioridade para escritor
                    semaforosUsando.mutex.acquire();
                    while (numeroLeitores[posicao] > 0 || numeroEscritoresEsperando[posicao] > 0) {
                        semaforosUsando.l.acquire();
                    }
                    numeroLeitores[posicao]++;
                    semaforosUsando.mutex.release();
                    readFile(arquivo, linhainicial, qtdlinhas);
                    semaforosUsando.mutex.acquire();
                    numeroLeitores[posicao]--;
                    if (numeroLeitores[posicao] == 0 && numeroEscritoresEsperando[posicao] > 0) {
                        semaforosUsando.s.release();
                    }
                    semaforosUsando.mutex.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
        }
        case3: {
            try {
                semaforosUsando.mutex.acquire();
                semaforosUsando.l.acquire();
            if (numeroLeitores[posicao] == 0) {
                semaforosUsando.s.acquire();
            }
            numeroLeitores[posicao]++;
            semaforosUsando.mutex.release();
            semaforosUsando.l.release();
            readFile(arquivo, linhainicial, qtdlinhas);
            System.out.println("leu");
            semaforosUsando.l.acquire();
            numeroLeitores[posicao]--;
            if (numeroLeitores[posicao] == 0) {
                semaforosUsando.s.release();
            }
            semaforosUsando.l.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void write(int arqNum, String arquivo, String dados) {
        System.out.println("entrou no write");
        int posicao = arqNum-1;
        Semaphores semaforosUsando = sets[posicao];
        
        switch (prioridade) {
            case 1: {
                try {
                    //Pega o semaforo pra escrever no arquivo
                    semaforosUsando.s.acquire();
                    writeFile(arquivo, dados);
                    //Libera ele 
                    semaforosUsando.s.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case 2: {
                try{
                semaforosUsando.mutex.acquire();
                numeroEscritoresEsperando[posicao]++;
                while (numeroLeitores[posicao] > 0 || numeroEscritores[posicao] > 0) {
                    semaforosUsando.s.acquire();
                }
                numeroEscritoresEsperando[posicao]--;
                numeroEscritores[posicao]++;
                semaforosUsando.mutex.release();
                /* Escrita */
                writeFile(arquivo, dados);
                semaforosUsando.mutex.acquire();
                numeroEscritores[posicao]--;
                if (numeroEscritoresEsperando[posicao] > 0) {
                    semaforosUsando.s.release();
                } else {
                    semaforosUsando.l.release();
                }
                semaforosUsando.mutex.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case 3:{
                 
            try {
            semaforosUsando.mutex.acquire();            
            semaforosUsando.s.acquire();
            semaforosUsando.mutex.release();
                writeFile(arquivo, dados);
            System.out.println("escreveu");
            semaforosUsando.s.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }

    }
        
        
        
        
        
        
        

//	@Override
//	public void read(String arquivo, int linhainicial, int qtdlinhas) {
//		rwi.read(arquivo, linhainicial, qtdlinhas);
//		
//	}
//
//
//	@Override
//	public void write(String arquivo, String dados) {
//		rwi.write(arquivo, dados);
//		
//	}

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
