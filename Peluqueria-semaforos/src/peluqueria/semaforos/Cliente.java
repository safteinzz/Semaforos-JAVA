/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peluqueria.semaforos;

import java.util.concurrent.Semaphore;
/**
 *
 * @author SaFteiNZz
 */
public class Cliente extends Thread 
{
    int id;
    Semaphore SillasEspera;
    Semaphore sillaCortar;

    public Cliente(int id, Semaphore SillasEspera, Semaphore sillaCortar) {
        this.id = id;
        this.SillasEspera = SillasEspera;
        this.sillaCortar = sillaCortar;
    }

    @Override
    public void run() {
        // Entra el cliente
        System.out.println("CLIENTE " + id + ": Hola, tienes hueco?");		
        //Comprueba si hay sillas libres
        comprobarSillasEspera(SillasEspera, sillaCortar);		
        // Cliente se va
        System.out.println("CLIENTE " + id + ": Hasta luego");
        System.out.println("--------------------------------");
    }

    private void comprobarSillasEspera(Semaphore semaforoSillasEspera, Semaphore semaforoSillaCortar) {
        try {   
            //Comprobar si el peluquero se ha dormido
            if(semaforoSillasEspera.availablePermits() == Peluqueria.N && semaforoSillaCortar.availablePermits() > 0) {
                System.out.println("CLIENTE " + id + ": Eh tu! Despierta!");                        
            } 
            //Mirar si hay sillas libres
            if(semaforoSillasEspera.availablePermits() > 0 ) {
                // Coger una silla
                semaforoSillasEspera.acquire();
                System.out.println("PELUQUERO: Si tengo hueco CLIENTE " + id);
                //Cortar el pelo
                comprobarSillaCortar(semaforoSillaCortar);
                //Liberar espacio en el semaforo
                semaforoSillasEspera.release();    
            //No hay sillas
            } else 
            {
                System.out.println("PELUQUERO: No tengo hueco, vuelve luego CLIENTE " + id );
            }
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
    }

    private void comprobarSillaCortar(Semaphore semaforoSillaCortar) {
        try {
            //Coger el permiso de cortar el pelo
            semaforoSillaCortar.acquire();
            System.out.println("- CLIENTE " + id + " se está cortando el pelo");
            //Cortar el pelo (5 segundos)
            Thread.sleep(5000);
            System.out.println("- CLIENTE " + id + " se ha cortado el pelo");
            //Librar espacio en el semaforo de cortar el pelo
            semaforoSillaCortar.release();
        } catch (InterruptedException e) {			
            e.printStackTrace();
        }
    }	
}
