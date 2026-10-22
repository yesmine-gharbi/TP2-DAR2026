
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurObjet {

    public static void main(String[] args) {

        try {
            ServerSocket serveur = new ServerSocket(1234);

            System.out.println("Serveur en attente du client...");

            Socket socket = serveur.accept();

            ObjectInputStream entree = new ObjectInputStream(
                    socket.getInputStream());

            ObjectOutputStream sortie = new ObjectOutputStream(
                    socket.getOutputStream());

            Operation op = (Operation) entree.readObject();

            double a = op.getOperande1();
            char operateur = op.getOperateur();
            double b = op.getOperande2();

            String reponse;

            if (operateur == '+') {
                reponse = "Resultat = " + (a + b);
            } else if (operateur == '-') {
                reponse = "Resultat = " + (a - b);
            } else if (operateur == '*') {
                reponse = "Resultat = " + (a * b);
            } else if (operateur == '/') {
                if (b == 0) {
                    reponse = "Erreur : division par zero";
                } else {
                    reponse = "Resultat = " + (a / b);
                }
            } else {
                reponse = "Erreur : operateur invalide";
            }

            sortie.writeObject(reponse);
            sortie.flush();

            System.out.println("Operation recue : "
                    + a + " " + operateur + " " + b);
            System.out.println(reponse);

            entree.close();
            sortie.close();
            socket.close();
            serveur.close();

        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Classe de l'objet introuvable");
        }
    }
}
