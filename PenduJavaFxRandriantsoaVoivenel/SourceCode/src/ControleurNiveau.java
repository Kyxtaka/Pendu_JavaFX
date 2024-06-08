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
            this.modelePendu.setNiveau(MotMystere.FACILE);
            this.modelePendu.setMotATrouver();
            System.out.println(this.modelePendu.getMotATrouve());
        } else if (nomDuRadiobouton.equals("Moyen")){
            this.modelePendu.setNiveau(MotMystere.MOYEN);
            this.modelePendu.setMotATrouver();
            System.out.println(this.modelePendu.getMotATrouve());
        } else if (nomDuRadiobouton.equals("Difficile")){
            this.modelePendu.setNiveau(MotMystere.DIFFICILE);
            this.modelePendu.setMotATrouver();
            System.out.println(this.modelePendu.getMotATrouve());
        } else if (nomDuRadiobouton.equals("Expert")){
            this.modelePendu.setNiveau(MotMystere.EXPERT);
            this.modelePendu.setMotATrouver();
            System.out.println(this.modelePendu.getMotATrouve());
        }
        System.out.println(nomDuRadiobouton);
    }
}