import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class KlijentDigitron {

	static Socket soketZaKomunikaciju = null;
	static PrintStream izlazniTokKaServeru = null;
	static BufferedReader ulazniTokOdServera = null;
	static PrintStream izlazniTokKaServeruPodaci = null;
	static BufferedReader ulazniTokOdServeraPodaci = null;
	static Socket soketZaPodatke = null;
	static BufferedReader ulazKonzola = null;
	static boolean kraj = false;
	static boolean kraj1 = true;

	public static void main(String[] args) {
		try {
			int port = 1908;
			if (args.length > 0) {
				port = Integer.parseInt(args[0]);
			}

			soketZaKomunikaciju = new Socket("localhost", port);

			ulazKonzola = new BufferedReader(new InputStreamReader(System.in));
			izlazniTokKaServeru = new PrintStream(
					soketZaKomunikaciju.getOutputStream());

			ulazniTokOdServera = new BufferedReader(new InputStreamReader(
					soketZaKomunikaciju.getInputStream()));

			while (!kraj) {
				System.out.println(ulazniTokOdServera.readLine());
				izlazniTokKaServeru.println(ulazKonzola.readLine());
				String operacija = ulazniTokOdServera.readLine();
				if (operacija.startsWith("Dovidj")) {
					System.out.println(operacija);
					break;
				}
				if (operacija.startsWith("Sada unesite brojeve")) {
					System.out.println(operacija);
					int portPodaci = 15813;
					if (args.length > 0) {
						port = Integer.parseInt(args[0]);
					}

					soketZaPodatke = new Socket("localhost", portPodaci);
					ulazniTokOdServeraPodaci = new BufferedReader(
							new InputStreamReader(
									soketZaPodatke.getInputStream()));
					izlazniTokKaServeruPodaci = new PrintStream(
							soketZaPodatke.getOutputStream());

					while (kraj1) {
						izlazniTokKaServeruPodaci.println(ulazKonzola
								.readLine());
						String poruka = ulazniTokOdServeraPodaci.readLine();
						System.out.println(poruka);
						if (poruka.startsWith("Pogresan")) {
							continue;
						}
						
						break;
					}

					soketZaPodatke.close();
				}
			}
			soketZaKomunikaciju.close();

		} catch (UnknownHostException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}

	}

}
