import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.net.Socket;

import java.util.Locale;


public class PodaciNit extends Thread {


	BufferedReader ulazniTokOdKlijenta = null;
	PrintStream izlazniTokKaKlijentu = null;
	Socket soketZaKomunikaciju = null;
	String operacija;
	boolean kraj = true;
	String regex = "[0-9 ]+";
	public PodaciNit(Socket soketPodaci,String operacija){
		this.soketZaKomunikaciju = soketPodaci;
		this.operacija = operacija;
	}
	
	
	public void run(){
		String brojevi;
		
	
		try{
		
		ulazniTokOdKlijenta = new BufferedReader(
										new InputStreamReader(
													soketZaKomunikaciju.getInputStream()));
		izlazniTokKaKlijentu = new PrintStream(
										soketZaKomunikaciju.getOutputStream());
		while(kraj){
			
		brojevi = ulazniTokOdKlijenta.readLine();
		
		if(!brojevi.matches(regex)){
			izlazniTokKaKlijentu.println("Pogresan unos. Unesite brojeve!");
			continue;
		}
		if(brojevi.indexOf("  ")!=-1){
			izlazniTokKaKlijentu.println("Pogresan unos.Dozvoljen je samo jedan razmak izmedju brojeva.Sada unesite brojeve");
			continue;
		}
		if(!velikiBrojevi(brojevi)){
			izlazniTokKaKlijentu.println("Pogresan unos.Najvise se moze uneti petocifren broj.Unesite opet");
			continue;
		}
		 String[] nizBrojeva = brojevi.split(" ");
		
		int rezultat = 0;
		int rezultat1 = 1;
		
		if(operacija.toLowerCase(Locale.getDefault()).indexOf("sabiranje")!=-1){
			
			for (int i = 0; i < nizBrojeva.length; i++) {
				rezultat = rezultat + Integer.parseInt(nizBrojeva[i]);
			}
				
			izlazniTokKaKlijentu.println("Rezultat= "+rezultat);
		}
		
		if(operacija.toLowerCase(Locale.getDefault()).indexOf("oduzimanje")!=-1){
		int	rezultatOduzimanje=Integer.parseInt(nizBrojeva[0]);
			for (int i = 1; i < nizBrojeva.length; i++) {
				rezultatOduzimanje = rezultatOduzimanje - Integer.parseInt(nizBrojeva[i]);
			}
			
			izlazniTokKaKlijentu.println("Rezultat= "+rezultatOduzimanje);
		}
		
		if(operacija.toLowerCase(Locale.getDefault()).indexOf("mnozenje")!=-1){
			
			for (int i = 0; i < nizBrojeva.length; i++) {
				rezultat1 = rezultat1 * Integer.parseInt(nizBrojeva[i]);
			}
			
			izlazniTokKaKlijentu.println("Rezultat= "+rezultat1);
		}
		
		if(operacija.toLowerCase(Locale.getDefault()).indexOf("deljenje")!=-1){
			
			rezultat1= Integer.parseInt(nizBrojeva[0]);
			
			for (int i = 1; i < nizBrojeva.length; i++) {
				if(Integer.parseInt(nizBrojeva[i])==0){
					izlazniTokKaKlijentu.println("Neko je spavao na casovima matematike. NE SME SE DEILITI NULOM!");
					break;
				}
				
				rezultat1=rezultat1 / Integer.parseInt(nizBrojeva[i]);
				izlazniTokKaKlijentu.println("Rezultat= "+rezultat1);
				
			}
			}
		break;
		}
		soketZaKomunikaciju.close();
		
		}
		catch(Exception e) {
			System.out.println(e);
		}
			
		
	}
public boolean velikiBrojevi(String brojevi){
	String[] nizBrojeva = brojevi.split(" ");
	for (int i = 0; i < nizBrojeva.length; i++) {
		if(nizBrojeva[i].length()>6){
			return false;
		}
	}
	return true;
}	
	 
}
