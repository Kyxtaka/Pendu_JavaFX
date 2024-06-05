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
        // A implémenter
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
            new MotMystere("",1,2,MotMystere.FACILE,10);
        } else if (nomDuRadiobouton.equals("Moyen")){
            new MotMystere("",3,5,MotMystere.MOYEN,10);
        } else if (nomDuRadiobouton.equals("Difficile")){
            new MotMystere("",5,8,MotMystere.DIFFICILE,10);
        } else if (nomDuRadiobouton.equals("Expert")){
            new MotMystere("",9,15,MotMystere.EXPERT,10);
        }
        System.out.println(nomDuRadiobouton);
    }
}