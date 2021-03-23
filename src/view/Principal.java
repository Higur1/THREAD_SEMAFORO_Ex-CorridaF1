package view;
import java.util.concurrent.Semaphore;

import controller.Corrida;

public class Principal {

	public static void main(String[] args) {
		int perm = 5;
		int aux = 0;
		
		Semaphore semaforo = new Semaphore(perm);
		for(int escuderia = 1; escuderia < 8; escuderia++) {
			for(int carro = 0 ; carro < 2 ; carro++) {
				Corrida StartCorrida = new Corrida(escuderia, aux, semaforo);
				StartCorrida.start();
				aux++;
			}
		}
	}
}
