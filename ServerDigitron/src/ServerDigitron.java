import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerDigitron {
	static ServerNit klijenti[] = new ServerNit[10];
	
	public static void main(String[] args) {
		
		int port = 2555;
		
		if(args.length>0){
			port = Integer.parseInt(args[0]);
		}
		
		Socket klijentSoket = null;
		try {
			
			ServerSocket serverSoket = new ServerSocket(port);
			
			while(true){
				
				klijentSoket = serverSoket.accept();
				for (int i = 0; i < klijenti.length; i++) {
					if (klijenti[i]==null) {
						klijenti[i]= new ServerNit(klijentSoket,klijenti);
						klijenti[i].start();
						break;
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
