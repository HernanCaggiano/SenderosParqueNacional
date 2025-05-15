package app;
import vista.VistaMapa;
import controlador.ControladorGrafo;
public class Aplicacion {
    public static void main(String[] args) {
        VistaMapa vista = new VistaMapa();
        ControladorGrafo ctrl = new ControladorGrafo(vista);
        ctrl.ejecutar();
    }
}