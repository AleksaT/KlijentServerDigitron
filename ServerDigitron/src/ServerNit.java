import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Locale;


public class ServerNit extends Thread {

	
	BufferedReader ulazniTokOdKlijenta = null;
	PrintStream izlazniTokKaKlijentu = null;
	Socket soketZaKomunikaciju = null;
	ServerNit[] klijenti;
	
	public ServerNit(Socket soket, ServerNit[] klijenti){
		this.soketZaKomunikaciju = soket;
		this.klijenti = klijenti;
	}
	
	public void run(){
		String brojevi;
		String operacija;
		try {
			
			ulazniTokOdKlijenta = new BufferedReader(
											new InputStreamReader(
													soketZaKomunikaciju.getInputStream()));
			izlazniTokKaKlijentu = new PrintStream(
											soketZaKomunikaciju.getOutputStream());
			
			while(true){

			izlazniTokKaKlijentu.println("Izaberite zeljenu racunsku operaciju : sabiranje,oduzimanje,mnozenje,deljenje ili quit za izlaz");
				operacija = ulazniTokOdKlijenta.readLine();
			
				if(operacija.startsWith("quit")){
					izlazniTokKaKlijentu.println("Dovidjenja!");
					break;
				}
				
				if ((operacija.toLowerCase(Locale.getDefault())).indexOf("deljenje")==-1 
						&& 
					(operacija.toLowerCase(Locale.getDefault())).indexOf("mnozenje")==-1
						&&
					(operacija.toLowerCase(Locale.getDefault())).indexOf("oduzimanje")==-1
						&&
					(operacija.toLowerCase(Locale.getDefault())).indexOf("sabiranje")==-1) {
					
					izlazniTokKaKlijentu.println("Uneta je pogresna komanda!");
					
				}else{
				
				
			izlazniTokKaKlijentu.println("Sada unesite brojeve");
				brojevi = ulazniTokOdKlijenta.readLine();
			
			if(brojevi.indexOf("  ")!=-1){
				izlazniTokKaKlijentu.println("Dozvoljen je samo jedan razmak izmedju brojeva"+"\n Sada unesite brojeve");
				brojevi = ulazniTokOdKlijenta.readLine();
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
			
			
			}}
			soketZaKomunikaciju.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		for (int i = 0; i < klijenti.length; i++) {
			if (klijenti[i]==this) {
				klijenti[i]=null;
			}
		}
	}
	


}

