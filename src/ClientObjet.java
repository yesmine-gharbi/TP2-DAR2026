
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientObjet {

    public static void main(String[] args) {

        Scanner clavier = new Scanner(System.in);

        System.out.print("Premier operande : ");
        double a = clavier.nextDouble();

        System.out.print("Operateur (+, -, *, /) : ");
        char operateur = clavier.next().charAt(0);

        System.out.print("Deuxieme operande : ");
        double b = clavier.nextDouble();

        try {
            Socket socket = new Socket("localhost", 1234);

            ObjectOutputStream sortie = new ObjectOutputStream(
                    socket.getOutputStream());

            sortie.flush();

            ObjectInputStream entree = new ObjectInputStream(
                    socket.getInputStream());

            Operation op = new Operation(a, operateur, b);

            sortie.writeObject(op);
            sortie.flush();

            String reponse = (String) entree.readObject();

            System.out.println("Reponse du serveur : " + reponse);

            entree.close();
            sortie.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Erreur client : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Classe de reponse introuvable");
        }

        clavier.close();
    }
}
