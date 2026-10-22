
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

public class client {

    public static void main(String[] args) {

        String adresseServeur = "localhost";
        int port = 5000;

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Connexion au serveur...");

            Socket socket = new Socket(adresseServeur, port);

            System.out.println("Connecté au serveur.");

            PrintWriter sortie = new PrintWriter(
                    socket.getOutputStream(), true
            );

            BufferedReader entree = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            System.out.print("Entrez une opération (exemple : 55 * 25) : ");

            String operation = scanner.nextLine();

            String regex = "^\\s*-?\\d+\\s*[+*/-]\\s*-?\\d+\\s*$";

            if (!Pattern.matches(regex, operation)) {
                System.out.println("Erreur : syntaxe incorrecte.");
                System.out.println("Exemple correct : 55 * 25");

                socket.close();
                scanner.close();
                return;
            }

            sortie.println(operation);

            System.out.println("Opération envoyée au serveur.");

            String resultat = entree.readLine();

            System.out.println("Résultat reçu : " + resultat);

            socket.close();

        } catch (Exception e) {
            System.out.println("Erreur client : " + e.getMessage());
        }

        scanner.close();
    }
}