import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TitledPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */    
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button bJouer;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.niveaux = new ArrayList<>();
        this.niveaux.add("Facile");
        this.niveaux.add("Moyen");
        this.niveaux.add("Difficile");
        this.dessin = new ImageView(new Image("file:img/pendu0.png"));
        // Mot Cryptee
        Dictionnaire dico = new Dictionnaire("/usr/share/dict/french", 3, 10);
        String mot = dico.choisirMot();
        this.motCrypte = new Text(mot);
        // Progressbar
        this.pg = new ProgressBar(this.modelePendu.getNbEssais());
        // Clavier
        ControleurLettres controleLettre = new  ControleurLettres(modelePendu, this); 
        this.clavier = new Clavier("abcdefghijklmnopqrstuv-",controleLettre,10);
        // Le niveau
        this.leNiveau = new Text(this.modelePendu.getNiveau()+"");
        // Chronometre
        this.chrono = new Chronometre();
        // Panel central
        this.panelCentral = new BorderPane();
        // Bouton Parametre
        this.boutonParametres = new Button();
        this.boutonParametres.setGraphic(new ImageView(new Image("file:img/parametres.png")));
        //Bouton Maison
        RetourAccueil retourAccueil = new RetourAccueil(modelePendu, this);
        this.boutonMaison = new Button();
        this.boutonMaison.setGraphic(new ImageView(new Image("file:img/home.png")));
        this.boutonMaison.setOnAction(retourAccueil);
        // Bouton Jouer
        ControleurLancerPartie controleLancerPartie = new ControleurLancerPartie(modelePendu, this);
        this.bJouer = new Button();
        this.bJouer.setText("Lancer une partie");
        this.bJouer.setOnAction(controleLancerPartie);
        
        
        // A terminer d'implementer
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){
        // A implementer          
        Pane banniere = new Pane();
        return banniere;
    }

    /**
     * @return le panel du chronomètre
     */
    private TitledPane leChrono(){
        // A implementer
        TitledPane res = new TitledPane("Chronomètre",this.chrono);
        return res;
    }

    /**
     * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     *         de progression et le clavier
     */
    private Pane fenetreJeu(){
        // A implementer
        Pane res = new Pane();
        return res;
    }

    /**
     * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     */
    private Pane fenetreAccueil(){
        BorderPane res = new BorderPane();
        VBox homeContainer =  new VBox();
        Button lauchGame  = new Button("Lancer une Partie");

        VBox levelChooser =  new VBox();
        ToggleGroup buttonGroup =  new ToggleGroup();

        RadioButton facileButton =  new RadioButton("Facile") ;
        facileButton.setSelected(true);
        facileButton.setToggleGroup(buttonGroup);

        RadioButton mediumButton =  new RadioButton("Moyen") ;
        mediumButton.setToggleGroup(buttonGroup);

        RadioButton difficileButton =  new RadioButton("Dificile") ;
        difficileButton.setToggleGroup(buttonGroup);

        RadioButton expertButton =  new RadioButton("Master") ;
        expertButton.setToggleGroup(buttonGroup);

        levelChooser.getChildren().addAll(facileButton,mediumButton,difficileButton,expertButton);
        TitledPane levelContainer =  new TitledPane("Niveau de difficulté", levelChooser);
        
        homeContainer.getChildren().addAll(lauchGame, levelContainer);
        res.setTop(this.titre());
        res.setCenter(homeContainer);
        return res;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File("file:"+repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        this.fenetreAccueil();
        this.majAffichage();
    }
    
    public void modeJeu(){
        this.fenetreJeu();
        this.majAffichage();
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        // A implementer
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        // A implementer
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        return this.chrono;
    }

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Regle",ButtonType.OK);
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Vous avez gagné ! \n GG", ButtonType.OK);        
        return alert;
    }
    
    public Alert popUpMessagePerdu(){
        // A implementer    
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Vous avez perdu ! \n looser", ButtonType.OK);
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
