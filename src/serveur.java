import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class serveur {

    public static void main(String[] args) {

        int port = 5000;

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Serveur démarré...");
            System.out.println("En attente d'un client sur le port " + port + "...");

            Socket socket = serverSocket.accept();

            System.out.println("Client connecté.");

            BufferedReader entree = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter sortie = new PrintWriter(
                    socket.getOutputStream(), true
            );

            String operation = entree.readLine();

            System.out.println("Opération reçue : " + operation);

            String resultat = calculer(operation);

            sortie.println(resultat);

            System.out.println("Résultat envoyé : " + resultat);

            socket.close();
            serverSocket.close();

        } catch (Exception e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }

    public static String calculer(String operation) {

        Pattern pattern = Pattern.compile(
                "^\\s*(-?\\d+)\\s*([+*/-])\\s*(-?\\d+)\\s*$"
        );

        Matcher matcher = pattern.matcher(operation);

        if (!matcher.matches()) {
            return "Erreur : opération invalide";
        }

        int operande1 = Integer.parseInt(matcher.group(1));
        String operateur = matcher.group(2);
        int operande2 = Integer.parseInt(matcher.group(3));

        switch (operateur) {

            case "+":
                return String.valueOf(operande1 + operande2);

            case "-":
                return String.valueOf(operande1 - operande2);

            case "*":
                return String.valueOf(operande1 * operande2);

            case "/":
                if (operande2 == 0) {
                    return "Erreur : division par zéro";
                }

                return String.valueOf(
                        (double) operande1 / operande2
                );

            default:
                return "Erreur : opérateur inconnu";
        }
    }
}