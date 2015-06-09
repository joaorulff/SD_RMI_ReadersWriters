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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReaderWriterImpl /*extends UnicastRemoteObject*/ {

//    int prioridade;
//    int numeroLeitores;
//    int numeroEscritores;
//    int numeroEscritoresEsperando;
//
//    public ReaderWriterImpl(int tipoPrioridade) throws RemoteException {
//        prioridade = tipoPrioridade;
//        numeroEscritores = 0;
//        numeroLeitores = 0;
//        numeroEscritoresEsperando=0;
//        Semaphores.start();
//    }
//
//    public void readFile(String arquivo, int linhainicial, int qtdlinhas) {
//    	System.out.println("readfile");
//        FileReader arq;
//        try {
//            arq = new FileReader(arquivo);
//            BufferedReader buff = new BufferedReader(arq);
//            for (int i = 0; i < linhainicial-1; i++) {
//                buff.readLine();
//            }
//            for (int i = 0; i < qtdlinhas; i++) {
//                System.out.println(buff.readLine());
//
//            }
//            buff.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    public void writeFile(String arquivo, String dados) {
//        try {
//            FileWriter fstream = new FileWriter(arquivo, true); //true tells to append data.
//            BufferedWriter out = new BufferedWriter(fstream);
//            out.write("" + dados + " ");
//            out.close();
//            System.out.println("Escreveu \n" + dados + "\n no arquivo:" + arquivo);
//        } catch (IOException ex) {
//
//        }
//    }
//
//    @Override
//    public void read(String arquivo, int linhainicial, int qtdlinhas) {
//
//        switch (prioridade) {
//            case 1: {
//                try {
//                    // prioridade para leitor
//                    Semaphores.mutex.acquire();
//                    numeroLeitores++;         //Tem mais um lendo agora...
//                    if (numeroLeitores == 1) {  //Se for o 1Âº, precisa bloquear para escrita
//                        Semaphores.s.acquire();
//                    }
//                    Semaphores.mutex.release();
//                    System.out.println("read");
//                    readFile(arquivo, linhainicial, qtdlinhas);
//                    Semaphores.mutex.acquire();
//                    //Acabou de ler, vai informar que acabou diminuindo N
//                    numeroLeitores--;
//                    if (numeroLeitores == 0) { //Se nao tem mais ninguem lendo, abre para escrita
//                        Semaphores.s.release();
//                    }
//                    Semaphores.mutex.release();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                break;
//            }
//
//            case 2: {
//                try {
//                    // prioridade para escritor
//                    Semaphores.mutex.acquire();
//                    while (numeroLeitores > 0 || numeroEscritoresEsperando > 0) {
//                        Semaphores.l.acquire();
//                    }
//                    numeroLeitores++;
//                    Semaphores.mutex.release();
//                    readFile(arquivo, linhainicial, qtdlinhas);
//                    Semaphores.mutex.acquire();
//                    numeroLeitores--;
//                    if (numeroLeitores == 0 && numeroEscritoresEsperando > 0) {
//                        Semaphores.s.release();
//                    }
//                    Semaphores.mutex.release();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
//            
//                }
//                break;
//            }        
//            case 3: {
//            try {
//                Semaphores.mutex.acquire();
//                Semaphores.l.acquire();
//            if (numeroLeitores == 0) {
//                Semaphores.s.acquire();
//            }
//            numeroLeitores++;
//            Semaphores.mutex.release();
//            Semaphores.l.release();
//            readFile(arquivo, linhainicial, qtdlinhas);
//            System.out.println("leu");
//            Semaphores.l.acquire();
//            numeroLeitores--;
//            if (numeroLeitores == 0) {
//                Semaphores.s.release();
//            }
//            Semaphores.l.release();
//            
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//           break; 
//        }
//      }
//    }
//
//    @Override
//    public void write(String arquivo, String dados) {
//        System.out.println("entrou");
//        switch (prioridade) {
//            case 1: {
//                try {
//                    //Pega o semaforo pra escrever no arquivo
//                    Semaphores.s.acquire();
//                    writeFile(arquivo, dados);
//                    //Libera ele 
//                    Semaphores.s.release();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                break;
//            }
//            case 2: {
//                try{
//                Semaphores.mutex.acquire();
//                numeroEscritoresEsperando++;
//                while (numeroLeitores > 0 || numeroEscritores > 0) {
//                    Semaphores.s.acquire();
//                }
//                numeroEscritoresEsperando--;
//                numeroEscritores++;
//                Semaphores.mutex.release();
//                /* Escrita */
//                writeFile(arquivo, dados);
//                Semaphores.mutex.acquire();
//                numeroEscritores--;
//                if (numeroEscritoresEsperando > 0) {
//                    Semaphores.s.release();
//                } else {
//                    Semaphores.l.release();
//                }
//                Semaphores.mutex.release();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                break;
//            }
//            case 3:{
//                 
//            try {
//            Semaphores.mutex.acquire();            
//            Semaphores.s.acquire();//Semáforo que checa acesso(é o db?) Você não diz nada!!!
//            Semaphores.mutex.release();
//                writeFile(arquivo, dados);
//            System.out.println("escreveu");
//            Semaphores.s.release();
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ReaderWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            }
//        }
//
//            
//    }

}
