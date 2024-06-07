import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private Button boutonInfo;

    private Scene scene;
    private Stage stage;
    

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("img");
        this.niveaux = new ArrayList<>();
        this.niveaux.add("Facile");
        this.niveaux.add("Moyen");
        this.niveaux.add("Difficile");
        this.niveaux.add("Expert");
        this.dessin = new ImageView(new Image("file:img/pendu0.png"));

        // Mot Cryptee
        Dictionnaire dico = new Dictionnaire("/usr/share/dict/french", 3, 10);
        String mot = dico.choisirMot();
        this.motCrypte = new Text(this.modelePendu.getMotCrypte());

        // Progressbar
        this.pg = new ProgressBar(this.modelePendu.getNbEssais());

        // Clavier
        ControleurLettres controleLettre = new  ControleurLettres(modelePendu, this); 
        // this.clavier = new Clavier("abcdefghijklmnopqrstuv-",controleLettre,10);

        // Le niveau
        this.leNiveau = new Text(this.modelePendu.getNiveau()+"");

        // Chronometre
        this.chrono = new Chronometre();

        // Panel central
        this.panelCentral = new BorderPane();

        // Bouton Parametre
        ImageView parametres = new ImageView(new Image("file:img/parametres.png", 20,20,true,false));
        this.boutonParametres = new Button("",parametres);  

        //Bouton Maison
        RetourAccueil retourAccueil = new RetourAccueil(modelePendu, this);
        ImageView maison = new ImageView(new Image("file:img/home.png",20,20,true,false));
        this.boutonMaison = new Button("",maison);
        this.boutonMaison.setOnAction(retourAccueil);

        // Bouton Aide règle du jeu
        ImageView info = new ImageView(new Image("file:img/info.png", 20,20,true,false));
        this.boutonInfo = new Button("",info); 

        // Bouton Jouer
        ControleurLancerPartie controleLancerPartie = new ControleurLancerPartie(modelePendu, this);
        this.bJouer = new Button("Lancer une partie");
        this.bJouer.setOnAction(controleLancerPartie);

        // Progress Bar
        this.pg = new ProgressBar();
        this.pg.setProgress(0.0); //only pour test

        // Clavier
        String touche = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ-");
        this.clavier = new Clavier(touche, null, 4);

        // A terminer d'implementer
    }


    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        this.scene = new Scene(fenetre, 800, 1000);
        return this.scene;
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private BorderPane titre() {
        BorderPane banniere =  new BorderPane();  
        Label titre = new Label("Jeu du Pendu");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(this.boutonMaison,this.boutonParametres,this.boutonInfo);
        banniere.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, Insets.EMPTY)));
        banniere.setLeft(titre);
        banniere.setRight(buttonContainer);
        return banniere;
    }

    /**
     * @return le panel du chronomètre
     */
    private TitledPane leChrono(){
        // A implementer
        TitledPane res = new TitledPane("Chronomètre",this.chrono);
        this.chrono.start();
        return res;
    }

    /**
     * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     *         de progression et le clavier
     */
    private Pane fenetreJeu(){
        // A implementer avec les bonne fonctionnalité
        //motCrypte a implémenté
        //Niveau a implémenté
        //Chrono A implémenter
        BorderPane res = new BorderPane();

        //top
        res.setTop(this.titre());

        // Center
        VBox centerContainer = new VBox();
        centerContainer.setAlignment(Pos.BASELINE_CENTER);
        // this.motCrypte.setText("Ici Mot Mystere a Implementer");
        this.motCrypte.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        // this.motCrypte.setTextAlignment(Pos.BASELINE_CENTER);
        centerContainer.getChildren().add(this.motCrypte);
        // mise en place de l'image
        // int imageStatus = 1; //test status image (Permet de choisir une Image) ==> juste pour tester
        this.dessin.setImage(this.lesImages.get(0));
        this.dessin.setFitWidth(450); //largeur
        this.dessin.setFitHeight(650); //hauteur
        centerContainer.getChildren().add(this.dessin);
        //affichage Progress bar
        centerContainer.getChildren().add(this.pg);
        // Affichage Clavier
        centerContainer.getChildren().add(this.clavier);

        // Right
        VBox rightContainer = new VBox();
        this.leNiveau.setText("Niveau A implémenter");;
        this.leNiveau.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Button resetWordButton = new Button("Nouveau mot");
        resetWordButton.setOnAction(new ControleurLancerPartie(modelePendu, this));
        rightContainer.getChildren().addAll(leNiveau,this.leChrono(),resetWordButton);

        res.setRight(rightContainer);
        res.setCenter(centerContainer);
        return res;
    }

    /**
     * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     */
    private BorderPane fenetreAccueil(){
        BorderPane res = new BorderPane();
        VBox homeContainer =  new VBox();
        // Button lauchGame  = new Button("Lancer une Partie");
        Button lauchGame = this.bJouer;

        VBox levelChooser =  new VBox();
        ToggleGroup buttonGroup =  new ToggleGroup();
        ControleurNiveau controleurNiveau = new ControleurNiveau(this.modelePendu);
        for (String  niveau: this.niveaux) {
            RadioButton level = new RadioButton(niveau);
            level.setOnAction(controleurNiveau);
            if (niveau.equals("Facile")) level.setSelected(true);
            level.setToggleGroup(buttonGroup);
            levelChooser.getChildren().add(level);
        }
    
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
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }


    public void modeAccueil(){
        BorderPane fenetre = this.fenetreAccueil();
        this.majAffichage(fenetre);
    }
    
    public void modeJeu(){
        Pane fenetre = this.fenetreJeu();
        this.majAffichage(fenetre);
    }
    
    public void modeParametres(){
        modeParametres();
    }

    /** lance une partie */
    public void lancePartie(){
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        this.leNiveau.setText(this.modelePendu.getNiveau()+"");
        this.dessin.setImage(this.lesImages.get(0));
        modeJeu();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(Pane lePane){
        this.scene.setRoot(lePane);
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
        this.stage = stage;
        this.stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        this.stage.setScene(this.laScene());
        this.modeAccueil();
        // this.modeJeu(); //test only
        this.stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}