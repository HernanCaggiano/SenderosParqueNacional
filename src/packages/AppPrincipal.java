package packages;

import javax.swing.SwingUtilities;

public class AppPrincipal {

	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaPrincipal vista = new VistaPrincipal();
            Grafo grafo = new Grafo();
            vista.setVisible(true);
            new ControladorRedSenderos(vista, grafo);
        });
    }
}