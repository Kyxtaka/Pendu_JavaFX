import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
// import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle ;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'un faire un héritié d'un TilePane
 */
public class Clavier extends TilePane{
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     * @param touches une chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(String touches, EventHandler<ActionEvent> actionTouches,int tailleLigne) {
        this.setPadding(new Insets(10));
        this.setPrefColumns(tailleLigne);
        this.setVgap(5);
        this.setHgap(5);
        this.clavier = new ArrayList<>();
        for (int i = 0;i<touches.length();i++){
            Button bouton = new Button(String.valueOf(touches.charAt(i)));
            bouton.setOnAction(actionTouches);
            bouton.setShape(new Circle(1.5));
            bouton.setMaxSize(3,3);
            this.clavier.add(bouton);
            this.getChildren().add(bouton);
        }
    }

    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * @param touchesDesactivees une chaine de caractères contenant la liste des touches désactivées
     */
    public void desactiveTouches(Set<String> touchesDesactivees){
        for (Button bouton : this.clavier){
            for (String str : touchesDesactivees){
                if (bouton.getText().contains(str)){
                    bouton.setOnAction(null);
                    bouton.setDisable(true);
                }
            }
        }
    }
}