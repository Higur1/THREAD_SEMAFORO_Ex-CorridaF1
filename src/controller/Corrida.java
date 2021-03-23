package controller;

import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;

public class Corrida extends Thread{
	
	public int carro;
	public int escuderia;
	private static int count = 0;
	private static double [] GuardaTempos = new double [14];
	private static int [] GuardaCarros = new int [14];
	private static int [] GuardaEsc = new int[14];
	private Semaphore semaforo;
	
	DecimalFormat df = new DecimalFormat("##.##");
	
	public Corrida(int escuderia, int carro, Semaphore semaforo) {
		
		this.escuderia  = escuderia;
		this.carro = carro;
		this.semaforo = semaforo;
		
	}
	@Override
	public void run() {
		Pista();
		if (count == 14) {
			GridDeLargada();
		}
	}
	private void Pista() {
//-------------- início da seção crítica --------------------
		try {
			semaforo.acquire();
			int volta = 0;
			double melhorTempo = 1000;
			while(volta != 3){
				
				//Inicia a volta
				int minuto = (int)((Math.random()*3)+1);
				double segundo = ((Math.random()*0.59));
				double timeTotal = minuto + segundo;
				//Mostra o tempo da volta
				System.out.println("O carro #" + (carro+1)+ " terminou a volta em " + convertTime(timeTotal) +" m's");
				
				//Passa pra próxima volta
				volta++;
				
				//Verificar melhor tempo
				if(timeTotal < melhorTempo) {
					melhorTempo = timeTotal;
				}
			}
			GuardaTempos[count] = melhorTempo;
			GuardaCarros[count] = carro;
			GuardaEsc[count] = escuderia;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			count++;
			semaforo.release();
		}
//---------------------Fim da seção crítica-------------------
	}

	private void GridDeLargada() {
		System.out.println("*-------------------------------------------------*");
		System.out.println("                  Grid de largada                  ");
		System.out.println("*-------------------------------------------------*");	
		double aux;
		int aux2, aux3;
		boolean controle;
		 
		for(int i = 0; i < GuardaTempos.length; ++i){
			controle = true;
			for(int j = 0; j < (GuardaTempos.length - 1); ++j){
				if(GuardaTempos[j] > GuardaTempos[j + 1]){
					aux = GuardaTempos[j];
					aux2 = GuardaCarros[j];
					aux3 = GuardaEsc[j];
					GuardaTempos[j] = GuardaTempos[j + 1];
					GuardaCarros[j] = GuardaCarros[j+1];
					GuardaEsc[j] = GuardaEsc [j+1];
					GuardaTempos[j+1] = aux;
					GuardaCarros[j+1] = aux2;
					GuardaEsc[j+1] = aux3;
					controle = false;
				}
			}
			if(controle){
				break;
			}
		}
		for (int c = 0; c < 14; c++) {
			System.out.println("-------------------------------------------------");
			System.out.println((c+1)+"º (Escuderia #"+GuardaEsc[c]+") (Carro #"+ (GuardaCarros[c]+1)+") (tempo = " +convertTime(GuardaTempos[c])+" m's)");
		}
	}
	private String convertTime(double time) {
		String aux = df.format(time);
		aux = aux.replace(",", ":");
		return aux;
	}

}
		
