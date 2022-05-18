/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peluqueria.semaforos;

import java.util.Random;
import java.util.concurrent.Semaphore;
/**
 *
 * @author SaFteiNZz
 */
public class Peluqueria 
{
    
    public static int N = 2; //Numero de sillas en la peluqueria
    
    public static void main(String[] args) throws InterruptedException {
        //Variables
        int contador = 0; //Numero de clientes que se han pasado por la peluqueria en la sesion
        Semaphore SillasEspera = new Semaphore(N);
        Semaphore sillaCortar = new Semaphore(1); //Solo hay 1 peluquero

        //Crear 20 clientes
        for (int o = 0; o <= 20; o++)
        {
            Cliente c = new Cliente(contador, SillasEspera, sillaCortar);
            //Ejecutar accion del cliente
            c.start();

            contador++;

            //Tiempo para siguiente cliente se pase por la peluqueria (3-4.5s)
            Random r = new Random();
            int low = 3000;
            int high = 4500;
            int result = r.nextInt(high-low) + low;
            Thread.sleep(result);
        }
    }  
}
