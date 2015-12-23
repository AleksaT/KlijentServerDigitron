import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerDigitron {
	
	public static void main(String[] args) {
		
		int port = 1908;
		
		if(args.length>0){
			port = Integer.parseInt(args[0]);
		}
		
		int portPodaci = 15813;
		if(args.length>0){
			portPodaci = Integer.parseInt(args[0]);
		}
		Socket klijentSoket = null;
		try {
			
			ServerSocket serverSoket = new ServerSocket(port);
			ServerSocket serverSoketPodaci = new ServerSocket (portPodaci);
			while(true){
				
				klijentSoket = serverSoket.accept();
				
				ServerNit nit = new ServerNit(klijentSoket,serverSoketPodaci);
				nit.start();
				
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		
		
	}
}
