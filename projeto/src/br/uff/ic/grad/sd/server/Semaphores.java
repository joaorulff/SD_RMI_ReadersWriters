package br.uff.ic.grad.sd.server;

import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Livia
 */
public class Semaphores {
    Semaphore mutex; //
    Semaphore s; //
    Semaphore l;
    
    public void start(){
        mutex = new Semaphore(1);
        s =  new Semaphore(1);
        l = new Semaphore(1);
    }
}
