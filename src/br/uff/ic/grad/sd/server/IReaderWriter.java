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

public interface IReaderWriter extends Remote {

  // Leitura
  public void read(String arquivo, int linhainicial, int qtdlinhas);
  
  public void write(String arquivo, String dados);
 
}

