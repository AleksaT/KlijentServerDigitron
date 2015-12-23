import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;


public class ServerNit extends Thread {

	
	BufferedReader ulazniTokOdKlijenta = null;
	PrintStream izlazniTokKaKlijentu = null;
	Socket soketZaKomunikaciju = null;
	ServerSocket soketZaPodatke = null;
	PodaciNit klijentPodaci = null;
	Socket soketZaKomunikacijuPodaci = null;
	
	public ServerNit(Socket soket,  ServerSocket soketPodaci){
		this.soketZaKomunikaciju = soket;
		this.soketZaPodatke = soketPodaci;
	}
	
	public void run(){
		
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
			
			soketZaKomunikacijuPodaci = soketZaPodatke.accept();
			klijentPodaci = new PodaciNit(soketZaKomunikacijuPodaci, operacija);
			klijentPodaci.start();
			
			
			}}
			soketZaKomunikaciju.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}
	


}

