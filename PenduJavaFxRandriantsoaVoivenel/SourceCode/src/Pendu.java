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
import java.util.List;
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
    // private BorderPane panelCentral;
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
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button boutonInfo;
     /**
     * le bouton qui permet de (lancer ou relancer une partie)
     */ 
    private Scene scene;
    private Stage stage;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        //init du modele 
        this.modelePendu = new MotMystere("/usr/share/dict/french", 0, 10,0,10);
        // init des objet
        this.motCrypte = new Text("");
        this.pg = new ProgressBar(this.modelePendu.getNbEssais());
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("img");
        this.dessin = new ImageView(this.lesImages.get(0));
        this.leNiveau = new Text(this.modelePendu.getNiveau()+"");
        this.chrono = new Chronometre();

        // Init des nivreaux
        this.niveaux = new ArrayList<>();
        this.niveaux.add("Facile");
        this.niveaux.add("Moyen");
        this.niveaux.add("Difficile");
        this.niveaux.add("Expert");

        // Init des boutton
        // Bouton Parametre
        ImageView parametres = new ImageView(new Image("file:img/parametres.png", 20,20,true,false));
        this.boutonParametres = new Button("",parametres);  
        this.boutonParametres.setOnAction(null);
        //Bouton Maison
        RetourAccueil retourAccueil = new RetourAccueil(modelePendu, this);
        ImageView maison = new ImageView(new Image("file:img/home.png",20,20,true,false));
        this.boutonMaison = new Button("",maison);
        this.boutonMaison.setOnAction(retourAccueil);
        // Bouton Aide règle du jeu
        ImageView info = new ImageView(new Image("file:img/info.png", 20,20,true,false));
        this.boutonInfo = new Button("",info); 
        this.boutonInfo.setOnAction(new ControleurInfos(this));
        // Bouton Jouer
        ControleurLancerPartie controleLancerPartie = new ControleurLancerPartie(modelePendu, this);
        this.bJouer = new Button("Lancer une partie");
        this.bJouer.setOnAction(controleLancerPartie);
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
        BorderPane.setMargin(titre, new Insets(20));
        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(this.boutonMaison,this.boutonParametres,this.boutonInfo);
        banniere.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, Insets.EMPTY)));
        banniere.setLeft(titre);
        banniere.setRight(buttonContainer);
        HBox.setMargin(this.boutonMaison, new Insets(0,2.5,0,2.5));
        HBox.setMargin(this.boutonParametres, new Insets(0,2.5,0,2.5));
        HBox.setMargin(this.boutonInfo, new Insets(0,2.5,0,2.5));
        BorderPane.setMargin(buttonContainer,new Insets(20));
        return banniere;
    }

    /**
     * @return le panel du chronomètre
     */
    private TitledPane leChrono(){
        TitledPane res = new TitledPane("Chronomètre",this.chrono);
        VBox.setMargin(res, new Insets(20));
        this.chrono.start();
        return res;
    }

    /**
     * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     *         de progression et le clavier
     */
    private Pane fenetreJeu(){
        BorderPane res = new BorderPane();
        //top
        res.setTop(this.titre());

        // Center
        VBox centerContainer = new VBox();
        centerContainer.setAlignment(Pos.BASELINE_CENTER);
        this.motCrypte.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        
        // this.motCrypte.setTextAlignment(Pos.BASELINE_CENTER);
        centerContainer.getChildren().add(this.motCrypte);
        // mise en place de l'image
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
        this.leNiveau.setText("Difficulté "+this.niveaux.get(this.modelePendu.getNiveau()));
        this.leNiveau.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        Button resetWordButton = new Button("Nouveau mot");
        resetWordButton.setOnAction(new ControleurLancerPartie(modelePendu, this));
        rightContainer.getChildren().addAll(leNiveau,this.leChrono(),resetWordButton);
        VBox.setMargin(this.motCrypte, new Insets(20));
        VBox.setMargin(this.dessin, new Insets(10));
        VBox.setMargin(this.leNiveau, new Insets(20));
        VBox.setMargin(resetWordButton, new Insets(20));
        VBox.setMargin(this.pg, new Insets(10));
        BorderPane.setMargin(rightContainer, new Insets(25,0,0,0));
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
            VBox.setMargin(level, new Insets(2.5));
        }
        TitledPane levelContainer =  new TitledPane("Niveau de difficulté", levelChooser);
        homeContainer.getChildren().addAll(lauchGame, levelContainer);
        VBox.setMargin(lauchGame,new Insets(25,20,10,20));
        VBox.setMargin(levelContainer,new Insets(20));
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
        this.scene.setRoot(fenetre);
    }
    
    public void modeJeu(){
        Pane fenetre = this.fenetreJeu();
        this.scene.setRoot(fenetre);
    }
    
    public void modeParametres(){
        modeParametres();
    }
    public Clavier getClavier() {
        return this.clavier;
    }

    /** lance une partie */
    public void lancePartie(){
        // Init Clavier
        String touche = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ-");
        ControleurLettres controleLettre = new  ControleurLettres(modelePendu, this); 
        this.clavier = new Clavier(touche, controleLettre, 4);
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        this.leNiveau.setText(this.modelePendu.getNiveau()+"");
        this.dessin.setImage(this.lesImages.get(0));
        this.pg.setProgress(0.0);
        this.chrono.resetTime();
        this.chrono.start();
        modeJeu();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage(){
        int nbErreursRestantes = this.modelePendu.getNbErreursRestants();
        int nbErreurs = this.modelePendu.getNbErreursMax() - nbErreursRestantes; 
        double poucentage = 1.0/nbErreursRestantes;
        if (this.modelePendu.getNbLettresAtrouver() == this.modelePendu.getNbLettresRestantes()) {
            this.pg.setProgress(0.0);
        } else  {
            System.out.println(poucentage);
            this.pg.setProgress(poucentage);
        }
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
        System.out.println(nbErreurs);
        System.out.println(this.modelePendu.getNbErreursRestants());
        if (nbErreurs <= this.modelePendu.getNbErreursMax()) this.dessin.setImage(this.lesImages.get(nbErreurs));
        if (this.modelePendu.gagne() || this.modelePendu.perdu()) {
           this.chrono.stop();System.out.println("execute");
            if (this.modelePendu.gagne()) {
                this.pg.setProgress(1.0);
                this.popUpMessageGagne().showAndWait();
            }
            else this.popUpMessagePerdu().showAndWait();
            this.modelePendu.setMotATrouver();
            this.lancePartie();
        }
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono(){
        return this.chrono;
    }
    

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(
            Alert.AlertType.CONFIRMATION,
            "La partie est en cours!"+
            System.lineSeparator()+
            "Etes-vous sûr de l'interrompre ?",
            ButtonType.YES, ButtonType.NO
        );
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu(){
        Alert alert = new Alert(
            Alert.AlertType.INFORMATION,
            "1 - Essayer de deviner le mot Crypté"+
            System.lineSeparator()+
            "2 - Vous avez droit à 10 erreur au total"+
            System.lineSeparator()+
            "3 - Si vous avez trouvé le bon mot la partie est terminé et vous avez gagné"+
            System.lineSeparator()+
            "4 - Si le personnage dessiné est pendu, vous avez perdu et la partie est terminée"
            ,ButtonType.OK
        );
        alert.setTitle("Règles du jeu");
        return alert;
    }
    
    public Alert popUpMessageGagne(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
            "Vous avez gagné !"+System.lineSeparator()+
            "GG"+System.lineSeparator()
            +System.lineSeparator()
            +"Une nouvelle partie va débuter", ButtonType.OK
        );
        alert.setTitle("Victoire");        
        return alert;
    }
    
    public Alert popUpMessagePerdu() {  
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Vous avez perdu !"+System.lineSeparator()+
            "Looser"+System.lineSeparator()
            +System.lineSeparator()
            +"Une nouvelle partie va débuter", ButtonType.OK
        );
        alert.setTitle("Défaite");
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