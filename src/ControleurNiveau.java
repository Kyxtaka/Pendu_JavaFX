import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;


    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu) {
        this.modelePendu = modelePendu;
    }

    /**
     * gère le changement de niveau
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
        String nomDuRadiobouton = radiobouton.getText();
        if (nomDuRadiobouton.equals("Facile")){
            this.modelePendu = new MotMystere("/usr/share/dict/french",3,4,MotMystere.FACILE,10);
            this.modelePendu.setMotATrouver();
        } else if (nomDuRadiobouton.equals("Moyen")){
            this.modelePendu = new MotMystere("/usr/share/dict/french",5,7,MotMystere.MOYEN,10);
            this.modelePendu.setMotATrouver();
        } else if (nomDuRadiobouton.equals("Difficile")){
            this.modelePendu = new MotMystere("/usr/share/dict/french",8,10,MotMystere.DIFFICILE,10);
            this.modelePendu.setMotATrouver();
        } else if (nomDuRadiobouton.equals("Expert")){
            this.modelePendu = new MotMystere("/usr/share/dict/french",11,20,MotMystere.EXPERT,10);
            this.modelePendu.setMotATrouver();
            System.out.println(this.modelePendu.getMotATrouve());
        }
        System.out.println(nomDuRadiobouton);
    }
}