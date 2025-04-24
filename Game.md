package AdventureGameJava.src.com.adventure;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.Stack;
import java.util.List;

/**
 * Classe principale pour le Jeu d'Aventure : Le Voyage d'Emma
 * Cette classe sert de point d'entrée et de contrôleur pour le jeu
 * 
 * 
 */
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Zone currentZone;
    private Player player;
    private transient GUI gui;
    private boolean gameOver;
    private boolean victorious;
    private Stack<Zone> previousZones;
    private int moveCount;
    private boolean randomizedPuzzles;
    
    /**
     * Constructeur pour la classe Game
     * Initialise le monde du jeu et démarre le jeu
     */
    public Game() {
        gui = new GUI();
        player = new Player();
        previousZones = new Stack<>();
        moveCount = 0;
        gameOver = false;
        victorious = false;
        randomizedPuzzles = true;
        initializeGame();
    }
    
    /**
     * Initialise le monde du jeu en créant des zones et en les connectant
     */
    private void initializeGame() {
        // Create zones
        Zone mainGreenhouse = new Zone("Serre Principale", 
                "L'entrée d'un vaste jardin botanique abandonné. La lumière du soleil traverse le plafond de verre,\n" +
                "illuminant diverses plantes. Cette serre semble être le centre névralgique du jardin.\n" +
                "Vous remarquez ancien jardinier(a qui vous pouvez parler) et un petit carnet incomplet sur une table à proximité. La sortie principale semble verrouillée.");
        
        Zone archiveCellar = new Zone("Cave des Archives",
                "Une pièce souterraine sombre avec des étagères remplies de dossiers et de registres poussiéreux.\n" +
                "L'air est moisi, et vous pouvez voir de vieux outils de jardinage accrochés aux murs.\n" +
                "Trois coffres mystérieux sont disposés dans différents coins de la pièce.");
        
        Zone tropicalGreenhouse = new Zone("Serre Tropicale",
                "L'air chaud et humide vous frappe en entrant. Une végétation dense vous entoure,\n" +
                "avec des fleurs colorées et plusieurs espèces de plantes inhabituelles.\n" +
                "Le chemin est bloqué par d'inquiétantes plantes carnivores. Vous aurez besoin d'un\n" +
                "pesticide naturel pour avancer. Vous entendez le son de l'eau qui goutte quelque part à proximité.");
        
        Zone alpineGreenhouse = new Zone("Serre Alpine",
                "Un environnement plus frais imitant les régions montagneuses. Des affleurements rocheux\n" +
                "sont couverts de petites plantes robustes. Une brise froide semble circuler\n" +
                "dans la pièce malgré le fait d'être à l'intérieur.\n" +
                "Vous remarquez une porte verrouillée à l'est avec un étrange mécanisme à code.");

        Zone botanicalLibrary = new Zone("Bibliothèque Botanique",
                "Des étagères remplies de textes botaniques bordent les murs. Un bureau de lecture est au centre,\n" +
                "avec un vieux livre relié en cuir ouvert dessus. Une faible lumière filtre à travers des fenêtres poussiéreuses.\n" +
                "Une cheminée ancienne se trouve contre le mur du fond. Quelque chose semble être caché près d'elle.");

        Zone desertGreenhouse = new Zone("Serre Désertique",
                "Aride et chaude, cette serre contient divers cactus et plantes du désert.\n" +
                "Le sable recouvre le sol, et la chaleur rayonne de lampes spécialement conçues.\n" +
                "Un arbre sec et tordu se dresse au centre. Près de lui, quelque chose semble scintiller faiblement.\n" +
                "Une sortie vers le nord semble bloquée par une énergie mystérieuse.");

        Zone labyrinth = new Zone("Labyrinthe Botanique - Entrée",
                "L'entrée d'un complexe dédale végétal. Des haies denses forment des murs naturels,\n" +
                "et des plantes grimpantes s'entrelacent au-dessus, créant un plafond verdoyant.\n" +
                "Une légère brume flotte à proximité du sol, ajoutant au mystère du lieu.\n" +
                "Vous êtes entré par le sud.");
        
        Zone labyrinthCenter = new Zone("Labyrinthe Botanique - Centre",
                "Vous arrivez dans une grande clairière circulaire au centre du labyrinthe. Trois portes\n" +
                "imposantes sont disposées aux points cardinaux Nord, Est et Ouest. Au centre, un piédestal\n" +
                "avec un mécanisme complexe semble attendre une activation. Une sortie mène au sud vers l'entrée.");

        Zone forgottenEchoesGate = new Zone("Porte des Échos Oubliés",
                "Une porte ancienne couverte de symboles évoquant le son et les échos. L'air vibre légèrement\n" +
                "autour de vous, et vous entendez des murmures lointains d'une autre époque.\n" +
                "Un parchemin repose sur un lutrin et une lettre gravée 'A' est posée à côté.");
        
        Zone lostRelicsGate = new Zone("Porte des Reliques Perdues",
                "Cette porte massive est ornée de représentations d'objets antiques et d'artefacts oubliés.\n" +
                "Des étagères poussiéreuses contiennent des fragments de poteries et d'instruments anciens.\n" +
                "Un médaillon scintille sur un piédestal vide au centre, à côté d'une lettre gravée 'B'.");
        
        Zone ancientLightGate = new Zone("Porte de la Lumière Ancienne",
                "Une magnifique porte dorée avec des motifs complexes qui semblent briller d'eux-mêmes.\n" +
                "L'air ici semble chargé d'une étrange énergie. Vous remarquez un cristal gravé sur un autel\n" +
                "et une lettre 'C' posée à proximité. Une autre grande porte scellée se trouve au nord.");
        
        Zone secretGarden = new Zone("Jardin Secret",
                "Un sanctuaire caché derrière la porte finale du Labyrinthe. Baigné dans une douce lumière éthérée,\n" +
                "ce jardin contient les plantes les plus rares du monde. En son centre, vous voyez une fleur lumineuse\n" +
                "comme aucune que vous n'avez jamais rencontrée - la légendaire Fleur de Lumière !\n" +
                "À côté de la fleur, un journal richement décoré repose sur un petit banc en pierre.");
        
        // Add these items before creating the puzzles that reward them
        Item page5 = new Item("page5", "Page 5 du carnet : Détaille les propriétés luminescentes de certaines mousses alpines.", 0);
        Item page6 = new Item("page6", "Page 6 du carnet : Note sur la nécessité d'un cristal pour amplifier la lumière.", 0);
        Item page7 = new Item("page7", "Page 7 du carnet : Contient une partie de l'énigme pour activer le cristal.", 0);
        Item letterA = new Item("letterA", "Une lettre mystérieuse avec le symbole 'A'. Elle semble faire partie d'une séquence.", 0);
        Item letterB = new Item("letterB", "Une lettre mystérieuse avec le symbole 'B'.", 0);
        Item letterC = new Item("letterC", "Une lettre mystérieuse avec le symbole 'C'.", 0);
        Item libraryKey = new Item("library_key", "Une vieille clé rouillée trouvée dans un coffre tropical. Elle semble faite pour une serrure intérieure.", 1);
        Item lightFlower = new Item("light_flower", "La légendaire Fleur de Lumière, émettant une douce lueur.", 1);
        Item journal = new Item("journal", "Le journal caché du grand-père, révélant ses secrets.", 1);
        
        // Create the puzzles for the story
        Puzzle carnivorousPlantsPuzzle = new Puzzle(
            "plants",
            "Des plantes carnivores bloquent le chemin vers l'est. Elles semblent sensibles à certains produits chimiques.",
            "pesticide",
            "Vous avez éliminé les plantes carnivores et libéré le passage vers l'est. Un coffre est révélé !",
            Puzzle.PuzzleType.BOTANICAL,
            null);

        Puzzle tropicalChestRiddle = new Puzzle(
            "tropical_chest_riddle",
            "Un coffre révélé derrière les plantes porte une énigme:\n" +
            "\"Mon créateur m'a rempli de secrets partiels,\n" +
            "Mes pages sont comme des fragments d'histoire.\n" +
            "Les mots manquants sont mes détails cruciaux,\n" +
            "Comptez les pages où l'encre a disparu,\n" +
            "Et le nombre qui vous guidera sera connu.\"",
            "3",
            "Le coffre est verrouillé par cette énigme. Le nombre de pages manquantes du carnet semble être la clé.",
            Puzzle.PuzzleType.RIDDLE,
            null);

        // Create the chest puzzles in Archive Cellar with correct constructor
        Puzzle chest1Puzzle = new Puzzle(
            "chest1",
            "Je commence par E, finis par E, mais ne contiens qu'une seule lettre. Que suis-je ?",
            "enveloppe",
            "Pensez à un objet postal commun.",
            Puzzle.PuzzleType.RIDDLE,
            page5);

        Puzzle chest2Puzzle = new Puzzle(
            "chest2",
            "Je commence par 2, puis 4, puis 8, 16... Quel est mon prochain nombre ?",
            "32",
            "Chaque nombre est le double du précédent.",
            Puzzle.PuzzleType.PATTERN,
            page6);

        Puzzle chest3Puzzle = new Puzzle(
            "chest3",
            "Si un train électrique se déplace du nord au sud, dans quelle direction va la fumée ?",
            "aucune",
            "Les trains électriques ne produisent pas de fumée.",
            Puzzle.PuzzleType.RIDDLE,
            page7);

        archiveCellar.addPuzzle(chest1Puzzle);
        archiveCellar.addPuzzle(chest2Puzzle);
        archiveCellar.addPuzzle(chest3Puzzle);

        // Create the number guessing puzzle for the Alpine Greenhouse
        Puzzle alpineCodePuzzle = new Puzzle(
            "door_code",
            "Pour ouvrir cette porte vers l'est, vous devez deviner un nombre entre 0 et 100.",
            "42",
            "Essayez entre 1 et 100.",
            Puzzle.PuzzleType.COMBINATION,
            null);

        alpineGreenhouse.addPuzzle(alpineCodePuzzle);

        // Create the crystal activation puzzle for the Desert Greenhouse
        Puzzle crystalPuzzle = new Puzzle(
            "crystal_riddle",
            "Dans un désert de silence et de lumière,\n" +
            "Je suis le gardien des chemins de pierre.\n" +
            "Comptez mes rayons, mon éclat secret,\n" +
            "Et le cristal s'éveillera, près de la ligne.\n" +
            "Six fois le nombre total de points sur un dé,\n" +
            "Plus trois fois le double d'un triangle sacré.\n" +
            "Trouvez ce nombre pour me libérer,\n" +
            "Et mon pouvoir pourra enfin briller.",
            "144",
            "Réfléchissez aux nombres: dé (21 points) et triangle (3 côtés). Le résultat active le cristal et ouvre peut-être un passage.",
            Puzzle.PuzzleType.COMBINATION,
            null);
        
        desertGreenhouse.addPuzzle(crystalPuzzle);

        // Add Labyrinth Center Mechanism Puzzle
        Puzzle labyrinthMechanismPuzzle = new Puzzle(
                "mechanism",
                "Le piédestal central a une fente qui semble attendre un nombre. Des gravures suggèrent que l'énergie du cristal du désert est nécessaire.",
                "144",
                "Quel nombre était associé à l'activation du cristal dans la Serre Désertique ?",
                Puzzle.PuzzleType.COMBINATION,
                null);

        labyrinthCenter.addPuzzle(labyrinthMechanismPuzzle);

        // Connect zones with exits
        mainGreenhouse.setExit("E", tropicalGreenhouse);
        mainGreenhouse.setSpecialExit("O", botanicalLibrary, true, "library_key", true);
        mainGreenhouse.setExit("S", archiveCellar);
        mainGreenhouse.setExit("N", alpineGreenhouse);
        
        tropicalGreenhouse.setExit("O", mainGreenhouse);
        tropicalGreenhouse.setSpecialExit("E", desertGreenhouse, true, null, false);
        
        archiveCellar.setExit("N", botanicalLibrary);
        
        alpineGreenhouse.setExit("S", mainGreenhouse);
        alpineGreenhouse.setSpecialExit("E", desertGreenhouse, true, "door_code", true);
        
        botanicalLibrary.setExit("E", mainGreenhouse);
        botanicalLibrary.setSpecialExit("S", archiveCellar, true, "1863", true);
        
        desertGreenhouse.setExit("O", alpineGreenhouse);
        desertGreenhouse.setExit("S", tropicalGreenhouse);
        desertGreenhouse.setSpecialExit("N", labyrinth, true, null, false);
        
        labyrinth.setExit("N", labyrinthCenter);
        
        labyrinthCenter.setSpecialExit("N", lostRelicsGate, true, null, false);
        labyrinthCenter.setSpecialExit("E", forgottenEchoesGate, true, null, false);
        labyrinthCenter.setSpecialExit("O", ancientLightGate, true, null, false);
        labyrinthCenter.setExit("S", labyrinth);
        labyrinthCenter.setSpecialExit("UP", secretGarden, true, "three_letters", false);
        
        lostRelicsGate.setExit("S", labyrinthCenter);
        forgottenEchoesGate.setExit("O", labyrinthCenter);
        ancientLightGate.setExit("E", labyrinthCenter);
        
        // Add items to zones
        Item notebook = new Item("notebook", "Le carnet de recherche incomplet de votre grand-père. Plusieurs pages semblent manquantes.", 1);
        mainGreenhouse.addItem(notebook);
        
        Item waterBottle = new Item("bottle", "Une bouteille d'eau pleine. Elle sera utile pour rester hydraté pendant votre exploration.", 2);
        mainGreenhouse.addItem(waterBottle);
        
        Item specialGlasses = new Item("glasses", "Des lunettes spéciales avec des verres teintés. Elles pourraient révéler des détails cachés.", 1);
        botanicalLibrary.addItem(specialGlasses);
        
        Item luminousCrystal = new Item("crystal", "Un cristal lumineux qui émet une douce lumière bleue. Il semble réagir à certaines plantes.", 2);
        desertGreenhouse.addItem(luminousCrystal);
        
        Item medallion = new Item("medallion", "Un médaillon doré révélant la deuxième partie du code. Il indique: 'Le second est le nombre de lettres'.", 1);
        lostRelicsGate.addItem(medallion);
        lostRelicsGate.addItem(letterB);
        
        Item scroll = new Item("scroll", "Un parchemin ancien contenant la première partie du code final. Il est écrit: 'Le premier est le nombre de portes'.", 1);
        forgottenEchoesGate.addItem(scroll);
        forgottenEchoesGate.addItem(letterA);
        
        Item engravedCrystal = new Item("engraved_crystal", "Un cristal gravé complétant le code final. Il est inscrit: 'Le dernier est le nombre de coffres'.", 1);
        ancientLightGate.addItem(engravedCrystal);
        ancientLightGate.addItem(letterC);
        
        Item lightFlowerItem = new Item("light_flower", "La légendaire Fleur de Lumière. Elle émet une douce lueur dorée et semble vibrer d'une énergie mystérieuse.", 1);
        secretGarden.addItem(lightFlowerItem);
        
        Item grandfatherJournal = new Item("journal", "Le journal de votre grand-père expliquant ses recherches sur la Fleur de Lumière et les raisons de sa disparition.", 2);
        secretGarden.addItem(grandfatherJournal);
        
        // Add containers to zones
        Container woodenChest = new Container("chest", "Un coffre en bois solide avec une serrure.", true, "key");
        woodenChest.addItem(new Item("magnifier", "Une loupe qui vous permet de voir les petits détails.", 1));
        botanicalLibrary.addContainer(woodenChest);
        
        Container glassDisplay = new Container("display", "Une vitrine en verre contenant des expositions botaniques.", false, null);
        botanicalLibrary.addContainer(glassDisplay);
        
        // Add the pesticide for the carnivorous plants
        Item pesticide = new Item("pesticide", "Un flacon de pesticide naturel. Particulièrement efficace contre les plantes carnivores.", 1);
        tropicalGreenhouse.addItem(pesticide);
        
        // Create containers for the chest puzzles
        Container chest1 = new Container("chest1", "Un coffre en bois ancien avec une énigme inscrite dessus.", false, null);
        chest1.addItem(page5);
        archiveCellar.addContainer(chest1);

        Container chest2 = new Container("chest2", "Un coffre en métal rouillé avec une énigme mathématique.", false, null);
        chest2.addItem(page6);
        archiveCellar.addContainer(chest2);

        Container chest3 = new Container("chest3", "Un coffre orné avec une énigme trompeuse.", false, null);
        chest3.addItem(page7);
        archiveCellar.addContainer(chest3);
        
        // Add puzzles to the Tropical Greenhouse
        tropicalGreenhouse.addPuzzle(carnivorousPlantsPuzzle);
        tropicalGreenhouse.addPuzzle(tropicalChestRiddle);

        // Create the hidden chest for the Tropical Greenhouse
        Container tropicalChest = new Container("tropical_chest", "Un coffre caché derrière où se trouvaient les plantes carnivores. Il est verrouillé par une énigme.", true, null);
        tropicalChest.addItem(libraryKey);
        tropicalChest.addItem(letterA);
        tropicalGreenhouse.addContainer(tropicalChest);
        
        // Set starting location
        currentZone = mainGreenhouse;

        // Add NPCs to guide the player
        NPC gardener = new NPC(
            "ancien_jardinier",
            "Un vieil homme avec une longue barbe blanche et des vêtements usés par le temps.",
            "Bienvenue dans le jardin botanique, jeune chercheuse. Je travaillais ici avec votre grand-père.\n" +
            "La Fleur de Lumière que vous cherchez est cachée dans un jardin secret au-delà du labyrinthe.\n" +
            "Pour y accéder, vous devrez résoudre de nombreuses énigmes et collecter trois lettres mystérieuses (A, B, C).\n" +
            "Commencez par explorer la Serre Tropicale à l'est (E), mais méfiez-vous des plantes carnivores !");
        gardener.addDialogue("yourself", "Je m'appelle Pierre. J'ai travaillé comme jardinier ici pendant plus de 30 ans, et j'étais l'assistant de votre grand-père.");
        gardener.addDialogue("grandfather", "Votre grand-père était un botaniste brillant. Il a disparu lors de ses recherches sur la Fleur de Lumière.");
        gardener.addDialogue("garden", "Ce jardin contient des plantes rares du monde entier. Il est divisé en plusieurs serres thématiques.");
        gardener.addDialogue("light flower", "La Fleur de Lumière est une plante légendaire aux propriétés curatives extraordinaires. Elle se trouve dans un jardin secret.");
        gardener.addDialogue("labyrinth", "Le labyrinthe est accessible depuis la Serre Désertique. C'est un passage complexe menant au jardin secret.");
        mainGreenhouse.addNPC(gardener);

        NPC botanist = new NPC(
            "botaniste_etranger",
            "Une femme d'âge moyen portant une blouse de laboratoire et des lunettes.",
            "Les plantes carnivores de cette serre bloquent le passage vers l'est (E).\n" +
            "Il vous faudra un pesticide naturel. Une fois les plantes parties, vous trouverez un coffre.\n" +
            "Résolvez son énigme pour obtenir une clé importante pour la Bibliothèque Botanique.");
        botanist.addDialogue("yourself", "Je suis Dr. Claire, botaniste spécialisée dans les plantes tropicales. J'étudie les espèces rares de cette serre.");
        botanist.addDialogue("plants", "Les plantes carnivores bloquant le passage sont une espèce hybride créée par votre grand-père. Elles sont sensibles au pesticide.");
        botanist.addDialogue("pesticide", "Le pesticide naturel que vous avez trouvé est une formule spéciale qui n'endommage que les plantes carnivores.");
        botanist.addDialogue("chest", "Le coffre derrière les plantes contient une clé importante et d'autres objets utiles. Son énigme concerne le carnet de votre grand-père.");
        botanist.addDialogue("library", "La Bibliothèque Botanique contient des connaissances accumulées par votre grand-père et d'autres chercheurs pendant des décennies.");
        tropicalGreenhouse.addNPC(botanist);

        NPC librarian = new NPC(
            "ancien_bibliothécaire",
            "Un homme âgé avec des lunettes épaisses et une tenue formelle défraîchie.",
            "Cette bibliothèque contient des connaissances accumulées sur des siècles.\n" +
            "Votre grand-père passait des heures à étudier ces livres.\n" +
            "Utilisez les lunettes spéciales près de la cheminée pour trouver le code (1863) de la porte sud (S) menant à la Cave des Archives.");
        librarian.addDialogue("yourself", "Je suis le gardien de cette bibliothèque depuis 40 ans. J'ai aidé votre grand-père à cataloguer ses découvertes.");
        librarian.addDialogue("books", "Ces livres contiennent des connaissances rares sur les plantes du monde entier, y compris certaines considérées comme mythiques.");
        librarian.addDialogue("glasses", "Les lunettes spéciales ont été créées par votre grand-père pour voir les inscriptions invisibles. Cherchez près de la cheminée.");
        librarian.addDialogue("code", "Le code 1863 est l'année de fondation de ce jardin botanique. Il ouvre la porte sud vers la Cave des Archives.");
        librarian.addDialogue("archives", "La Cave des Archives contient les journaux personnels et les notes de recherche de votre grand-père.");
        botanicalLibrary.addNPC(librarian);

        // Add guide for the labyrinth
        NPC ghostlyFigure = new NPC(
            "silhouette_fantomatique",
            "Une silhouette translucide qui semble être celle d'un botaniste d'une autre époque.",
            "Vous êtes entré dans le labyrinthe par le sud. Allez au nord (N) pour atteindre le centre.\n" +
            "Au centre, utilisez le nombre du cristal ('144') sur le mécanisme pour ouvrir les trois portes :\n" +
            "Nord (N) vers les Reliques Perdues (Médaillon, Lettre B).\n" +
            "Est (E) vers les Échos Oubliés (Parchemin, Lettre A).\n" +
            "Ouest (O) vers la Lumière Ancienne (Cristal Gravé, Lettre C).\n" +
            "Revenez au centre avec les trois lettres (A, B, C) pour ouvrir le passage final vers le haut (UP).");
        ghostlyFigure.addDialogue("yourself", "Je suis un écho du passé, un guide pour ceux qui cherchent la connaissance. Je suis lié à ce labyrinthe depuis des siècles.");
        ghostlyFigure.addDialogue("labyrinth", "Ce labyrinthe est un gardien de secrets, créé pour protéger le jardin secret et la Fleur de Lumière des personnes aux intentions impures.");
        ghostlyFigure.addDialogue("letters", "Les trois lettres mystérieuses (A, B, C) sont nécessaires pour ouvrir le passage final vers le Jardin Secret. Chacune est gardée par une porte.");
        ghostlyFigure.addDialogue("mechanism", "Le mécanisme au centre du labyrinthe s'active avec le nombre 144, le même qui a activé le cristal dans la Serre Désertique.");
        ghostlyFigure.addDialogue("garden", "Le Jardin Secret contient la légendaire Fleur de Lumière et le journal personnel de votre grand-père expliquant sa disparition.");
        labyrinth.addNPC(ghostlyFigure);
    }
    
    /**
     * La méthode principale de jeu qui exécute la boucle du jeu
     */
    public void play() {
        displayWelcome();
        
        // Main game loop
        while (!gameOver) {
            Command command = gui.getCommand();
            processCommand(command);
            
            // Check for victory condition
            checkVictoryCondition();
        }
        
        if (victorious) {
            gui.displayMessage("Félicitations ! Vous avez trouvé la Fleur de Lumière et découvert l'héritage de votre grand-père.");
            gui.displayMessage("Ses recherches aideront maintenant d'innombrables personnes, et sa mémoire vivra à travers votre travail.");
        } else {
            gui.displayMessage("Merci d'avoir joué. Au revoir !");
        }
        
        gui.close();
    }
    
    /**
     * Vérifie si les conditions de victoire sont remplies
     */
    private void checkVictoryCondition() {
        if (currentZone.getName().equals("Jardin Secret") && player.hasItem("light_flower") && player.hasItem("journal")) {
            victorious = true;
            gui.displayMessage("\n*********************************************");
            gui.displayMessage("FÉLICITATIONS ! Vous avez découvert la légendaire Fleur de Lumière !");
            gui.displayMessage("En lisant le journal de votre grand-père, vous comprenez enfin sa disparition");
            gui.displayMessage("et l'importance de ses recherches sur cette fleur extraordinaire.");
            gui.displayMessage("\nVotre grand-père écrit :");
            gui.displayMessage("\"Ma chère Emma, si tu lis ces lignes, c'est que tu as réussi à retrouver");
            gui.displayMessage("la Fleur de Lumière. Mes recherches ont révélé que cette fleur possède");
            gui.displayMessage("des propriétés curatives extraordinaires qui pourraient révolutionner la médecine.");
            gui.displayMessage("J'ai dû disparaître pour protéger ce secret d'individus mal intentionnés.");
            gui.displayMessage("Je te confie maintenant ce savoir et ce jardin. Fais-en bon usage pour le bien de l'humanité.\"");
            gui.displayMessage("\nGrâce à votre persévérance, le jardin abandonné redeviendra un lieu de recherche");
            gui.displayMessage("et de partage des connaissances, perpétuant ainsi l'héritage de votre grand-père.");
            gui.displayMessage("*********************************************");
        
        gameOver = true;
        }
    }
    
    /**
     * Affiche le message de bienvenue au début du jeu
     */
    private void displayWelcome() {
        gui.clearScreen();
        gui.displayMessage("Bienvenue dans Le Voyage d'Emma : Le Jardin du Botaniste !");
        gui.displayMessage("Dans cette aventure, vous jouez Emma, une jeune botaniste qui a");
        gui.displayMessage("hérité d'un jardin mystérieux de son grand-père.");
        gui.displayMessage("Votre mission est de trouver la légendaire Fleur de Lumière et de découvrir");
        gui.displayMessage("les secrets des recherches de votre grand-père.");
        gui.displayMessage("Tapez '?' pour obtenir de l'aide.\n");
        displayZoneInfo();
    }
    
    /**
     * Affiche des informations sur la zone actuelle
     */
    private void displayZoneInfo() {
        gui.displayMessage("\n" + currentZone.getName());
        gui.displayMessage(currentZone.getDescription());
        gui.displayMessage(currentZone.getExitString());
        
        if (!currentZone.getItemsString().isEmpty()) {
            gui.displayMessage("Vous voyez : " + currentZone.getItemsString());
        }
        
        if (!currentZone.getContainersString().isEmpty()) {
            gui.displayMessage("Vous remarquez : " + currentZone.getContainersString());
        }
    }
    
    /**
     * Traite la commande du joueur
     * 
     * @param command La commande à traiter
     */
    public void processCommand(Command command) {
        String commandWord = command.getCommandWord();
        
        switch (commandWord) {
            case "?":
                displayHelp();
                break;
            case "N":
            case "S":
            case "E":
            case "O":
            case "H":
            case "B":
            case "UP":
            case "DOWN":
                go(commandWord);
                break;
            case "LOOK":
                displayZoneInfo();
                break;
            case "TAKE":
                takeItem(command.getSecondWord());
                break;
            case "DROP":
                dropItem(command.getSecondWord());
                break;
            case "INVENTORY":
            case "I":
                displayInventory();
                break;
            case "EXAMINE":
                if (command.getSecondWord() == null) {
                    gui.displayMessage("Examiner quoi ?");
                } else if (currentZone.getContainer(command.getSecondWord()) != null) {
                    examineContainer(command.getSecondWord());
                } else if (currentZone.getNPC(command.getSecondWord()) != null) {
                    examineNPC(command.getSecondWord());
                } else if (currentZone.getPuzzle(command.getSecondWord()) != null) {
                    examinePuzzle(command.getSecondWord());
                } else {
                    gui.displayMessage("Il n'y a pas de " + command.getSecondWord() + " ici à examiner !");
                }
                break;
            case "OPEN":
                openContainer(command.getSecondWord());
                break;
            case "USE":
                useItem(command.getSecondWord());
                break;
            case "TALK":
                talkToNPC(command.getSecondWord());
                break;
            case "ASK":
                if (command.getSecondWord() == null) {
                    gui.displayMessage("Demander à qui ?");
                } else {
                    // Use the complete arguments string instead of just secondWord
                    String fullArgs = command.getCompleteArgs();
                    if (fullArgs == null) {
                        gui.displayMessage("Utilisez : ASK [personne] about [sujet]");
                        break;
                    }
                    
                    // Try different parsing approaches
                    String[] parts;
                    if (fullArgs.contains(" about ")) {
                        // Standard format: "ASK person about topic"
                        parts = fullArgs.split(" about ", 2);
                    } else {
                        // Alternate format: "ASK person topic" (without "about")
                        int firstSpace = fullArgs.indexOf(' ');
                        if (firstSpace > 0 && firstSpace < fullArgs.length() - 1) {
                            String npcName = fullArgs.substring(0, firstSpace).trim();
                            String topic = fullArgs.substring(firstSpace + 1).trim();
                            parts = new String[] {npcName, topic};
                        } else {
                            parts = new String[0];
                        }
                    }
                    
                    if (parts.length < 2) {
                        gui.displayMessage("Utilisez : ASK [personne] about [sujet]");
                        
                        // Afficher la liste des PNJ disponibles dans la zone
                        List<NPC> npcsInZone = currentZone.getNPCs();
                        if (!npcsInZone.isEmpty()) {
                            StringBuilder npcNames = new StringBuilder("Personnes présentes : ");
                            for (int i = 0; i < npcsInZone.size(); i++) {
                                npcNames.append(npcsInZone.get(i).getName());
                                if (i < npcsInZone.size() - 1) {
                                    npcNames.append(", ");
                                }
                            }
                            gui.displayMessage(npcNames.toString());
                            
                            // Si un seul nom a été fourni, suggérer des sujets pour ce PNJ
                            String potentialNpcName = command.getSecondWord();
                            NPC targetNPC = currentZone.getNPC(potentialNpcName);
                            if (targetNPC != null) {
                                List<String> topics = targetNPC.getTopics();
                                if (!topics.isEmpty()) {
                                    StringBuilder topicsStr = new StringBuilder("Sujets pour " + targetNPC.getName() + " : ");
                                    for (int i = 0; i < topics.size(); i++) {
                                        topicsStr.append(topics.get(i));
                                        if (i < topics.size() - 1) {
                                            topicsStr.append(", ");
                                        }
                                    }
                                    gui.displayMessage(topicsStr.toString());
                                }
                            }
                        }
                    } else {
                        askNPCAbout(parts[0], parts[1]);
                    }
                }
                break;
            case "SOLVE":
                String fullArgs = command.getCompleteArgs(); // Expects "puzzle_name with answer" or "puzzle_name answer"

                // PRINT THE FULL ARGS
                // gui.displayMessage("FULL ARGS: " + fullArgs);

                if (fullArgs == null || fullArgs.trim().isEmpty()) {
                    gui.displayMessage("Résoudre quoi et avec quelle réponse ?");
                    gui.displayMessage("Utilisez : SOLVE [énigme] with [réponse] ou SOLVE [énigme] [réponse]");
                    break; // Changed from return to break
                }

                fullArgs = fullArgs.trim();
                String puzzleName = null;
                String answer = null;
                String[] parts;

                // Prioritize splitting by " with "
                if (fullArgs.contains(" with ")) {
                    parts = fullArgs.split(" with ", 2);
                    if (parts.length == 2) {
                        puzzleName = parts[0].trim();
                        answer = parts[1].trim();
                    }
                } else {
                    // Fallback: Split by the first space for "SOLVE puzzle answer"
                    int firstSpace = fullArgs.indexOf(' ');
                    if (firstSpace > 0) {
                        puzzleName = fullArgs.substring(0, firstSpace).trim();
                        answer = fullArgs.substring(firstSpace + 1).trim();
                    }
                }

                // Check if parsing was successful
                if (puzzleName == null || puzzleName.isEmpty() || answer == null || answer.isEmpty()) {
                    gui.displayMessage("Format de commande invalide.");
                    gui.displayMessage("Utilisez : SOLVE [énigme] with [réponse] ou SOLVE [énigme] [réponse]");
                    // gui.displayMessage("DEBUG: Reçu args: '" + command.getSecondWord() + "'"); // Optional debug
                } else {
                    // gui.displayMessage("DEBUG: Tentative de résolution de '" + puzzleName + "' avec '" + answer + "'"); // Optional debug
                    solvePuzzle(puzzleName, answer);
                }
                break;
            case "SAVE":
                saveGame();
                break;
            case "LOAD":
                loadGame();
                break;
            case "Q":
                gameOver = true;
                break;
            default:
                gui.displayMessage("Je ne comprends pas cette commande.");
                break;
        }
    }
    
    /**
     * Affiche les informations d'aide
     */
    private void displayHelp() {
        gui.displayMessage("Vous êtes Emma, explorant le jardin botanique.");
        gui.displayMessage("Vos mots de commande sont :");
        gui.displayMessage("   N/S/E/O - Se déplacer dans cette direction (nord/sud/est/ouest)");
        gui.displayMessage("   H/B/UP/DOWN - Monter ou descendre (haut/bas/up/down)");
        gui.displayMessage("   R - Retourner à la pièce précédente");
        gui.displayMessage("   LOOK - Regarder autour de l'emplacement actuel");
        gui.displayMessage("   TAKE [objet] - Ramasser un objet");
        gui.displayMessage("   DROP [objet] - Déposer un objet");
        gui.displayMessage("   USE [objet] - Utiliser un objet dans votre inventaire");
        gui.displayMessage("   EXAMINE [conteneur] - Examiner le contenu d'un conteneur");
        gui.displayMessage("   OPEN [conteneur] - Ouvrir un conteneur verrouillé avec la bonne clé");
        gui.displayMessage("   INVENTORY (ou I) - Afficher votre inventaire");
        gui.displayMessage("   SAVE - Sauvegarder votre jeu");
        gui.displayMessage("   LOAD - Charger une partie sauvegardée");
        gui.displayMessage("   ? - Afficher cette aide");
        gui.displayMessage("   Q - Quitter le jeu");
    }
    
    /**
     * Déplace le joueur dans la direction spécifiée
     * 
     * @param direction La direction dans laquelle se déplacer
     */
    private void go(String direction) {
        if (direction == null) {
            gui.displayMessage("Aller où ?");
            return;
        }
        
        // Special case for Labyrinth Center -> Secret Garden with three letters
        if (currentZone.getName().equals("Labyrinthe Botanique - Centre") && 
            (direction.equals("UP") || direction.equals("H")) && 
            player.hasItem("letterA") && player.hasItem("letterB") && player.hasItem("letterC")) {
            
            Exit upExit = currentZone.getExit("UP");
            if (upExit != null) {
                upExit.setVisible(true);
                upExit.setLocked(false);
                gui.displayMessage("Vous placez les trois lettres mystérieuses dans les encoches du mécanisme central.");
                gui.displayMessage("Un passage s'ouvre vers le haut, révélant l'entrée du Jardin Secret !");
            }
        }
        
        Exit exit = currentZone.getExit(direction);
        if (exit == null) {
            // Special check for Library -> Archive code
            if (currentZone.getName().equals("Bibliothèque Botanique") && direction.equals("S")) {
                 String codeAttempt = gui.getInput("La porte sud est verrouillée par un code. Entrez le code :");
                 Exit southExit = currentZone.getExit("S"); // Re-fetch exit
                 if (southExit != null && southExit.getKeyName() != null && southExit.getKeyName().equals(codeAttempt)) {
                     southExit.setLocked(false);
                     gui.displayMessage("Le code est correct ! La porte vers la Cave des Archives s'ouvre.");
                     // Now try moving again
                     go(direction);
                     return;
                 } else {
                     gui.displayMessage("Code incorrect. La porte reste verrouillée.");
                     return;
                 }
            }
            gui.displayMessage("Il n'y a pas de sortie dans cette direction !");
            return;
        }
        
        if (!exit.isVisible()) {
            gui.displayMessage("Il n'y a pas de sortie visible dans cette direction !");
            return;
        }
        
        if (exit.isLocked()) {
            String keyNeeded = exit.getKeyName();
            if (keyNeeded != null) {
                if (keyNeeded.equals("three_letters")) { // Special case for final gate
                     if (player.hasItem("letterA") && player.hasItem("letterB") && player.hasItem("letterC")) {
                        exit.setLocked(false);
                        exit.setVisible(true); // Ensure it's visible
                        gui.displayMessage("Vous placez les trois lettres mystérieuses dans les encoches du mécanisme central.");
                        gui.displayMessage("Un passage s'ouvre vers le haut, révélant l'entrée du Jardin Secret !");
                     } else {
                         gui.displayMessage("Cette sortie nécessite les trois lettres mystérieuses (A, B, C) trouvées dans les portes du labyrinthe.");
                         return;
                     }
                } else if (player.hasItem(keyNeeded)) {
                    exit.setLocked(false);
                    gui.displayMessage("Vous utilisez " + keyNeeded + " pour déverrouiller la sortie.");
                } else if (keyNeeded.equals("door_code")) { // Alpine door code
                    gui.displayMessage("Cette porte nécessite de résoudre l'énigme 'door_code'. Utilisez SOLVE door_code with [nombre].");
                    return;
                } else if (keyNeeded.equals("1863") && currentZone.getName().equals("Bibliothèque Botanique")) { // Library code door
                     // Prompt handled above if exit was null initially, handle if exit exists but locked
                     String codeAttempt = gui.getInput("La porte sud est verrouillée par un code. Entrez le code :");
                     if (keyNeeded.equals(codeAttempt)) {
                         exit.setLocked(false);
                         gui.displayMessage("Le code est correct ! La porte vers la Cave des Archives s'ouvre.");
                     } else {
                         gui.displayMessage("Code incorrect. La porte reste verrouillée.");
                         return;
                     }
                } else {
                    gui.displayMessage("Cette sortie est verrouillée ! Vous avez besoin de quelque chose (" + keyNeeded + ").");
                    return;
                }
            } else {
                 // Handle implicitly locked doors like Desert->Labyrinth or Labyrinth Center gates
                 if (currentZone.getName().equals("Serre Désertique") && direction.equals("N")) {
                     gui.displayMessage("La sortie nord est bloquée par une énergie. Résolvez l'énigme 'crystal_riddle' pour la dissiper.");
                     return;
                 } else if (currentZone.getName().equals("Labyrinthe Botanique - Centre") && (direction.equals("N") || direction.equals("E") || direction.equals("O"))) {
                     gui.displayMessage("Cette porte est scellée. Résolvez l'énigme 'mechanism' au centre pour l'ouvrir.");
                     return;
                 } else if (currentZone.getName().equals("Serre Tropicale") && direction.equals("E")) {
                     gui.displayMessage("Le chemin vers l'est est bloqué par des plantes. Résolvez l'énigme 'plants'.");
                     return;
                 }
                gui.displayMessage("Cette sortie est verrouillée !");
                return;
            }
        }
        
        if (exit.isOneWay()) {
            gui.displayMessage("Vous prenez un passage qui semble n'avoir qu'un sens...");
        }
        
        previousZones.push(currentZone);
        currentZone = exit.getDestination();
        moveCount++;
        
        // Marquer la zone comme visitée
        player.visitZone(currentZone.getName());
        
        displayZoneInfo();
        
        // Vérifier si les conditions de victoire sont remplies après chaque déplacement
        checkVictoryCondition();
    }
    
    /**
     * Renvoie le joueur à la zone précédente
     */
    private void goBack() {
        if (previousZones.isEmpty()) {
            gui.displayMessage("Vous ne pouvez pas revenir plus loin !");
            return;
        }
        
        // Get the previous zone and go there
        Zone previousZone = previousZones.pop();
        
        // Check if the exit from current to previous is one-way
        boolean canReturn = true;
        for (String direction : "NSEOHB".split("")) {
            Exit exit = currentZone.getExit(direction);
            if (exit != null && exit.getDestination() == previousZone && exit.isOneWay()) {
                canReturn = false;
                break;
            }
        }
        
        if (!canReturn) {
            gui.displayMessage("Vous ne pouvez pas retourner par où vous êtes venu. C'est un passage à sens unique.");
            // Put the zone back on the stack since we couldn't return
            previousZones.push(previousZone);
            return;
        }
        
        currentZone = previousZone;
        moveCount++;
        
        displayZoneInfo();
    }
    
    /**
     * Prend un objet de la zone actuelle
     * 
     * @param itemName Le nom de l'objet à prendre
     */
    private void takeItem(String itemName) {
        if (itemName == null) {
            gui.displayMessage("Prendre quoi ?");
            return;
        }
        
        Item item = currentZone.removeItem(itemName);
        if (item == null) {
            gui.displayMessage("Il n'y a pas de " + itemName + " ici !");
        } else if (player.addItem(item)) {
            gui.displayMessage("Vous avez pris le " + itemName + ".");
        } else {
            currentZone.addItem(item); // Put it back if can't be taken
            gui.displayMessage("Vous ne pouvez pas porter plus d'objets !");
        }
    }
    
    /**
     * Dépose un objet dans la zone actuelle
     * 
     * @param itemName Le nom de l'objet à déposer
     */
    private void dropItem(String itemName) {
        if (itemName == null) {
            gui.displayMessage("Déposer quoi ?");
            return;
        }
        
        Item item = player.removeItem(itemName);
        if (item == null) {
            gui.displayMessage("Vous n'avez pas de " + itemName + " !");
        } else {
            currentZone.addItem(item);
            gui.displayMessage("Vous avez déposé le " + itemName + ".");
        }
    }
    
    /**
     * Utilise un objet de l'inventaire du joueur
     * 
     * @param itemName Le nom de l'objet à utiliser
     */
    private void useItem(String itemName) {
        if (itemName == null) {
            gui.displayMessage("Utiliser quel objet ?");
            return;
        }
        
        // Convert to lowercase for case-insensitive comparison
        String itemNameLower = itemName.toLowerCase();
        
        if (!player.hasItem(itemName)) {
            gui.displayMessage("Vous n'avez pas cet objet dans votre inventaire.");
            return;
        }
        
        // Actions spécifiques selon l'objet utilisé
        switch (itemNameLower) { // Use lowercase version for comparison
            case "glasses":
                useSpecialGlasses();
                break;
            case "pesticide":
                usePesticide();
                break;
            case "crystal":
                useCrystal();
                break;
            case "lettera":
            case "letterb":
            case "letterc":
                checkLetterCombination();
                break;
            case "page5":
            case "page6":
            case "page7":
                readNotebookPage(itemName); // Keep original case for readNotebookPage
                break;
            default:
                gui.displayMessage("Vous n'êtes pas sûr de comment utiliser cet objet ici.");
                break;
        }
    }
    
    /**
     * Utilise les lunettes spéciales pour révéler des détails cachés
     */
    private void useSpecialGlasses() {
        if (currentZone.getName().equals("Bibliothèque Botanique")) {
            gui.displayMessage("En mettant les lunettes spéciales, vous remarquez un code caché gravé près de la cheminée.");
            gui.displayMessage("Le code gravé indique: '1863' - la date de fondation du jardin botanique.");
            gui.displayMessage("Ce code ouvre la porte sud (S) vers la Cave des Archives.");
            player.addNote("Code Bibliothèque (Porte S vers Archives): 1863");
        } else if (currentZone.getName().equals("Serre Désertique")) {
            gui.displayMessage("À travers les lunettes spéciales, vous voyez le cristal briller d'une lumière intense.");
            gui.displayMessage("Des symboles mystérieux deviennent visibles sur sa surface, formant une énigme.");

            // Révéler l'énigme du cristal si elle n'a pas déjà été résolue
            Puzzle crystalPuzzle = currentZone.getPuzzle("crystal_riddle");
            if (crystalPuzzle != null && !crystalPuzzle.isSolved()) {
                gui.displayMessage("L'énigme 'crystal_riddle' est maintenant claire:");
                gui.displayMessage(crystalPuzzle.getDescription());
            } else if (crystalPuzzle != null && crystalPuzzle.isSolved()){
                 gui.displayMessage("Le cristal brille intensément ! L'énergie bloquant le passage nord (N) se dissipe.");
            }
        } else {
            gui.displayMessage("Vous regardez à travers les lunettes spéciales, mais ne voyez rien d'inhabituel ici.");
        }
    }
    
    /**
     * Utilise le pesticide pour éliminer les plantes carnivores
     */
    private void usePesticide() {
        if (currentZone.getName().equals("Serre Tropicale")) {
            Puzzle plantsPuzzle = currentZone.getPuzzle("plants");
            if (plantsPuzzle != null && !plantsPuzzle.isSolved()) {
                if (player.hasItem("pesticide")) {
                    plantsPuzzle.solve("pesticide"); // Mark puzzle as solved
                    player.removeItem("pesticide"); // Consume pesticide
                    gui.displayMessage("Vous pulvérisez le pesticide sur les plantes carnivores. Elles se rétractent rapidement,");
                    gui.displayMessage("libérant le passage vers l'est (E) et révélant un coffre ('tropical_chest') caché derrière les plantes.");
                    gui.displayMessage("Vous pouvez maintenant aller à l'est ou examiner/résoudre l'énigme du coffre.");

                    // Reveal the path East
                    Exit eastExit = currentZone.getExit("E");
                    if (eastExit != null) {
                        eastExit.setLocked(false); // Unlock the path
                        eastExit.setVisible(true); // Make it visible
                    }
                    // The chest itself is revealed, but still needs its riddle solved to open
                } else {
                     gui.displayMessage("Vous avez besoin de l'objet 'pesticide' pour faire ça.");
                }

            } else if (plantsPuzzle != null && plantsPuzzle.isSolved()){
                gui.displayMessage("Les plantes carnivores ont déjà été éliminées.");
            } else {
                 gui.displayMessage("Il n'y a pas de puzzle 'plants' ici.");
            }
        } else {
            gui.displayMessage("Il n'y a rien à traiter avec le pesticide ici.");
        }
    }
    
    /**
     * Utilise le cristal lumineux (après activation via énigme)
     */
    private void useCrystal() {
        if (player.hasItem("crystal")) {
            if (currentZone.getName().equals("Serre Désertique")) {
                Puzzle crystalRiddle = currentZone.getPuzzle("crystal_riddle");
                if (crystalRiddle != null && crystalRiddle.isSolved()) {
                    gui.displayMessage("Le cristal activé résonne avec l'énergie de la serre. Le passage nord semble maintenant accessible.");
                    // Unlocking is handled by the GO N logic / solvePuzzle result
                } else {
                    gui.displayMessage("Le cristal brille faiblement. Il doit être activé en résolvant l'énigme 'crystal_riddle'.");
                    gui.displayMessage("Utilisez les lunettes spéciales ici pour voir l'énigme.");
                }
            } else if (currentZone.getName().equals("Labyrinthe Botanique - Centre")) {
                 gui.displayMessage("Le cristal vibre en harmonie avec le mécanisme central.");
                 gui.displayMessage("Peut-être que le nombre utilisé pour activer le cristal ('144') est utile ici ?");
                 gui.displayMessage("Essayez : SOLVE mechanism with 144");
            } else if (currentZone.getName().equals("Porte de la Lumière Ancienne")) {
                gui.displayMessage("Le cristal réagit avec la porte dorée, mais ne l'ouvre pas.");
                gui.displayMessage("Le passage vers le Jardin Secret s'ouvre depuis le centre du Labyrinthe.");
            } else {
                gui.displayMessage("Le cristal brille doucement, mais ne semble pas avoir d'effet particulier ici.");
            }
        } else {
             gui.displayMessage("Vous n'avez pas de 'crystal'.");
        }
    }
    
    /**
     * Vérifie si le joueur a collecté les trois lettres et tente de les utiliser
     * (Action is now implicit when trying to go UP from Labyrinth Center)
     */
     private void checkLetterCombination() {
         // This specific check is removed as the logic is tied to the GO UP command from Lab Center now.
         gui.displayMessage("Les lettres semblent importantes pour le mécanisme central du Labyrinthe.");
         if (currentZone.getName().equals("Labyrinthe Botanique - Centre")) {
             if (player.hasItem("letterA") && player.hasItem("letterB") && player.hasItem("letterC")) {
                 gui.displayMessage("Vous avez les trois lettres ! Essayez d'aller 'UP' pour activer le passage final.");
             } else {
                 gui.displayMessage("Vous n'avez pas encore trouvé les trois lettres (A, B, C) dans les portes Nord, Est et Ouest.");
             }
         } else {
              gui.displayMessage("Vous devriez retourner au centre du Labyrinthe pour utiliser ces lettres.");
         }
     }
    
    /**
     * Lit une page du carnet de notes
     * 
     * @param pageName Le nom de la page à lire
     */
    private void readNotebookPage(String pageName) {
        switch (pageName) {
            case "page5":
                gui.displayMessage("Page 5 du carnet:");
                gui.displayMessage("'... La Fleur de Lumière émet une lumière qui semble réagir aux cristaux. Mes recherches");
                gui.displayMessage("suggèrent qu'elle possède des propriétés curatives extraordinaires. J'ai trouvé des indices");
                gui.displayMessage("indiquant que la fleur se trouve dans un jardin secret, accessible uniquement par la Porte");
                gui.displayMessage("de la Lumière Ancienne. Pour l'ouvrir, trois lettres mystérieuses doivent être réunies...'");
                player.addNote("Fleur de Lumière: Accessible par la Porte de la Lumière Ancienne avec 3 lettres");
                break;
            case "page6":
                gui.displayMessage("Page 6 du carnet:");
                gui.displayMessage("'... J'ai découvert un labyrinthe caché sous la Cave des Archives. Ce passage mène à une série");
                gui.displayMessage("de portes anciennes. La porte principale ne s'ouvrira que lorsque toutes les lettres seront");
                gui.displayMessage("réunies. La première lettre est cachée derrière une énigme dans la Serre Tropicale...'");
                player.addNote("Labyrinthe caché sous la Cave des Archives");
                
                // Révéler la sortie cachée vers le labyrinthe dans la Cave des Archives
                if (player.hasVisitedZone("Cave des Archives")) {
                    Zone archiveCellar = null;
                    for (String direction : currentZone.getExitDirections()) {
                    Exit exit = currentZone.getExit(direction);
                        if (exit.getDestination().getName().equals("Cave des Archives")) {
                            archiveCellar = exit.getDestination();
                        break;
                    }
                }
                    
                    if (archiveCellar != null) {
                        Exit westExit = archiveCellar.getExit("O");
                        if (westExit != null && !westExit.isVisible()) {
                            westExit.setVisible(true);
                            gui.displayMessage("Vous vous souvenez maintenant qu'il y a une entrée cachée vers le");
                            gui.displayMessage("labyrinthe dans la Cave des Archives, à l'ouest.");
                        }
                    }
                }
                break;
            case "page7":
                gui.displayMessage("Page 7 du carnet:");
                gui.displayMessage("'... Pour activer le cristal dans la Serre Désertique, il faut résoudre une énigme mathématique.");
                gui.displayMessage("La réponse est liée aux symboles géométriques: six fois le nombre total de points sur un dé,");
                gui.displayMessage("plus trois fois le double d'un triangle. Les lunettes spéciales révèleront les détails...'");
                player.addNote("Activation du cristal: 6 × (points sur un dé) + 3 × (2 × triangle)");
                break;
        }
    }
    
    /**
     * Examine un conteneur dans la zone actuelle
     * 
     * @param containerName Le nom du conteneur à examiner
     */
    private void examineContainer(String containerName) {
        if (containerName == null) {
            gui.displayMessage("Examiner quoi ?");
            return;
        }
        
        Container container = currentZone.getContainer(containerName);
        if (container == null) {
            gui.displayMessage("Il n'y a pas de " + containerName + " ici !");
            return;
        }
        
        if (container.isLocked()) {
            gui.displayMessage("Le " + containerName + " est verrouillé. Vous devez trouver un moyen de l'ouvrir.");
            return;
        }
        
        gui.displayMessage(container.getDescription());
        gui.displayMessage(container.getItemsString());
    }
    
    /**
     * Ouvre un conteneur dans la zone actuelle
     * 
     * @param containerName Le nom du conteneur à ouvrir
     */
    private void openContainer(String containerName) {
        if (containerName == null) {
            gui.displayMessage("Ouvrir quoi ?");
            return;
        }
        
        Container container = currentZone.getContainer(containerName);
        if (container == null) {
            gui.displayMessage("Il n'y a pas de " + containerName + " ici !");
            return;
        }
        
        if (!container.isLocked()) {
            gui.displayMessage("Le " + containerName + " est déjà ouvert.");
            return;
        }
        
        String keyName = container.getKeyName();
        if (keyName != null && player.hasItem(keyName)) {
            Item key = player.getItem(keyName);
            if (container.unlock(key)) {
                gui.displayMessage("Vous déverrouillez le " + containerName + " avec la " + keyName + ".");
                gui.displayMessage(container.getItemsString());
            } else {
                gui.displayMessage("La " + keyName + " ne correspond pas à la serrure.");
            }
        } else {
            gui.displayMessage("Vous n'avez pas la bonne clé pour ouvrir ce " + containerName + ".");
        }
    }
    
    /**
     * Affiche l'inventaire du joueur
     */
    private void displayInventory() {
        gui.displayMessage(player.getInventoryString());
    }
    
    /**
     * Sauvegarde l'état actuel du jeu
     */
    private void saveGame() {
        String saveName = gui.getInput("Entrez un nom pour votre fichier de sauvegarde :");
        if (saveName == null || saveName.trim().isEmpty()) {
            gui.displayMessage("Sauvegarde annulée.");
            return;
        }
        
        try {
            FileOutputStream fileOut = new FileOutputStream(saveName + ".sav");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            gui.displayMessage("Jeu sauvegardé sous '" + saveName + ".sav'");
        } catch (Exception e) {
            gui.displayMessage("Erreur lors de la sauvegarde du jeu : " + e.getMessage());
        }
    }
    
    /**
     * Charge un état de jeu sauvegardé
     */
    private void loadGame() {
        String loadName = gui.getInput("Entrez le nom de votre fichier de sauvegarde :");
        if (loadName == null || loadName.trim().isEmpty()) {
            gui.displayMessage("Chargement annulé.");
            return;
        }
        
        try {
            FileInputStream fileIn = new FileInputStream(loadName + ".sav");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Game loadedGame = (Game) in.readObject();
            in.close();
            fileIn.close();
            
            // Mise à jour de ce jeu avec l'état du jeu chargé
            this.currentZone = loadedGame.currentZone;
            this.player = loadedGame.player;
            this.previousZones = loadedGame.previousZones;
            this.moveCount = loadedGame.moveCount;
            
            gui.displayMessage("Jeu chargé depuis '" + loadName + ".sav'");
            displayZoneInfo();
        } catch (Exception e) {
            gui.displayMessage("Erreur lors du chargement du jeu : " + e.getMessage());
        }
    }
    
    /**
     * Convertit une abréviation de direction en son nom complet
     * 
     * @param direction L'abréviation (N, S, E, O, H, B)
     * @return Le nom complet (nord, sud, est, ouest, haut, bas)
     */
    private String getDirectionName(String direction) {
        switch (direction) {
            case "N": return "nord";
            case "S": return "sud";
            case "E": return "est";
            case "O": return "ouest";
            case "H": return "haut";
            case "B": return "bas";
            case "UP": return "haut";
            case "DOWN": return "bas";
            default: return direction;
        }
    }
    
    /**
     * Examine un PNJ dans la zone actuelle
     * 
     * @param npcName Le nom du PNJ à examiner
     */
    private void examineNPC(String npcName) {
        if (npcName == null) {
            gui.displayMessage("Examiner qui ?");
            return;
        }
        
        NPC npc = currentZone.getNPC(npcName);
        if (npc == null) {
            gui.displayMessage("Il n'y a pas de " + npcName + " ici !");
            return;
        }
        
        gui.displayMessage(npc.getDescription());
        if (npc.isHostile()) {
            gui.displayMessage(npcName + " semble hostile. Soyez prudent !");
        }
    }
    
    /**
     * Examine une énigme dans la zone actuelle
     * 
     * @param puzzleName Le nom de l'énigme à examiner
     */
    private void examinePuzzle(String puzzleName) {
        if (puzzleName == null) {
            gui.displayMessage("Examiner quelle énigme ?");
            return;
        }
        
        Puzzle puzzle = currentZone.getPuzzle(puzzleName);
        if (puzzle == null) {
            gui.displayMessage("Il n'y a pas de " + puzzleName + " ici !");
            return;
        }
        
        gui.displayMessage(puzzle.getDescription());
        if (puzzle.isSolved()) {
            gui.displayMessage("Vous avez déjà résolu cette énigme.");
        }
    }
    
    /**
     * Parle à un PNJ dans la zone actuelle
     * 
     * @param npcName Le nom du PNJ à qui parler
     */
    private void talkToNPC(String npcName) {
        if (npcName == null) {
            gui.displayMessage("Parler à qui ?");
            return;
        }
        
        NPC npc = currentZone.getNPC(npcName);
        if (npc == null) {
            gui.displayMessage("Il n'y a pas de " + npcName + " ici !");
            return;
        }
        
        // Affiche le dialogue initial si c'est la première conversation
        if (!npc.hasTalked()) {
            gui.displayMessage(npc.getName() + " dit : \"" + npc.getDialog() + "\"");
            npc.setHasTalked(true);  // Marque le PNJ comme ayant déjà parlé
        } else {
            // Conversation par défaut pour les interactions suivantes
            String response = npc.talk("yourself");
            gui.displayMessage(response);
        }
        
        // Liste des sujets disponibles
        List<String> topics = npc.getTopics();
        if (!topics.isEmpty()) {
            StringBuilder topicsStr = new StringBuilder("Vous pouvez demander à propos de : ");
            for (int i = 0; i < topics.size(); i++) {
                topicsStr.append(topics.get(i));
                if (i < topics.size() - 1) {
                    topicsStr.append(", ");
                }
            }
            gui.displayMessage(topicsStr.toString());
        }
        
        // Vérifier les cadeaux
        if (!npc.hasGivenGift()) {
            Item gift = npc.getGift();
            if (gift != null && player.addItem(gift)) {
                gui.displayMessage(npcName + " vous donne un " + gift.getName() + ".");
            }
        }
    }
    
    /**
     * Demande à un PNJ à propos d'un sujet spécifique
     * 
     * @param npcName Le nom du PNJ à qui demander
     * @param topic Le sujet à propos duquel demander
     */
    private void askNPCAbout(String npcName, String topic) {
        if (npcName == null) {
            gui.displayMessage("Demander à qui ?");
            return;
        }
        
        if (topic == null) {
            gui.displayMessage("Demander à propos de quoi ?");
            return;
        }
        
        NPC npc = currentZone.getNPC(npcName);
        if (npc == null) {
            gui.displayMessage("Il n'y a pas de " + npcName + " ici !");
            return;
        }
        
        String response = npc.talk(topic);
        gui.displayMessage(response);
    }
    
    /**
     * Tente de résoudre une énigme
     * 
     * @param puzzleName Le nom de l'énigme à résoudre
     * @param answer La réponse à essayer
     */
    private void solvePuzzle(String puzzleName, String answer) {
        if (puzzleName == null) {
            gui.displayMessage("Résoudre quoi ?");
            return;
        }
        
        if (answer == null) {
            gui.displayMessage("Résoudre avec quelle réponse ?");
            return;
        }

        puzzleName = puzzleName.toLowerCase(); // Normalize name
        answer = answer.toLowerCase(); // Normalize answer for string comparisons

        // D'abord vérifier les énigmes des PNJ (Removed for simplicity, handled via TALK/ASK)
        // ...

        // Ensuite vérifier les énigmes de la zone
        Puzzle puzzle = currentZone.getPuzzle(puzzleName);
        if (puzzle == null) {
            gui.displayMessage("Il n'y a pas d'énigme nommée '" + puzzleName + "' ici.");
            return;
        }

        if (puzzle.isSolved()) {
            gui.displayMessage("Cette énigme ('" + puzzleName + "') a déjà été résolue.");
            return;
        }

        // Traitement spécial pour l'énigme de code de la Serre Alpine
        if (puzzleName.equals("door_code") && currentZone.getName().equals("Serre Alpine")) {
            try {
                int guess = Integer.parseInt(answer);
                int targetNumber = Integer.parseInt(puzzle.getSolution()); // Assumes solution is stored as string

                if (guess == targetNumber) {
                    puzzle.solve(answer); // Mark solved
                    gui.displayMessage("Correct ! La porte s'ouvre, révélant un passage vers la Serre Désertique à l'est (E).");

                    // Déverrouiller la sortie est
                    Exit eastExit = currentZone.getExit("E");
                    if (eastExit != null) {
                        eastExit.setLocked(false);
                        eastExit.setVisible(true);
                    }
                } else if (guess < targetNumber) {
                    gui.displayMessage("Le nombre est trop petit. Essayez un nombre plus grand.");
                } else {
                    gui.displayMessage("Le nombre est trop grand. Essayez un nombre plus petit.");
                }
                return; // Handled Alpine code puzzle
            } catch (NumberFormatException e) {
                gui.displayMessage("Veuillez entrer un nombre valide pour l'énigme 'door_code'.");
                return;
            }
        }

        // Traitement standard pour les autres énigmes
        if (puzzle.solve(answer)) {
            gui.displayMessage("Correct ! Vous avez résolu l'énigme '" + puzzleName + "' !");

            // Post-solve actions based on puzzle name
            switch (puzzleName) {
                case "tropical_chest_riddle":
                    Container tropicalChest = currentZone.getContainer("tropical_chest");
                    if (tropicalChest != null && tropicalChest.isLocked()) {
                         tropicalChest.unlock(null); // Unlock chest (no key needed, riddle was the lock)
                         gui.displayMessage("Le coffre ('tropical_chest') s'ouvre !");
                         gui.displayMessage(tropicalChest.getItemsString()); // Show items inside
                         
                         // Add this code to make items available in the zone
                         for (Item item : tropicalChest.getItems()) {
                             currentZone.addItem(item);
                         }
                         
                         gui.displayMessage("Utilisez TAKE [objet] pour les prendre.");
                    }
                    break;

                case "crystal_riddle":
                    gui.displayMessage("Le cristal s'illumine intensément ! L'énergie bloquant le passage nord (N) se dissipe.");
                    Exit northExitDesert = currentZone.getExit("N");
                    if (northExitDesert != null) {
                        northExitDesert.setLocked(false);
                        northExitDesert.setVisible(true);
                    }
                    break;

                case "mechanism": // Labyrinth Center mechanism
                     gui.displayMessage("Le mécanisme central s'active avec un grondement ! Les portes Nord, Est et Ouest s'ouvrent.");
                     Exit northExitCenter = currentZone.getExit("N");
                     Exit eastExitCenter = currentZone.getExit("E");
                     Exit westExitCenter = currentZone.getExit("O");
                     if (northExitCenter != null) { northExitCenter.setLocked(false); northExitCenter.setVisible(true); }
                     if (eastExitCenter != null) { eastExitCenter.setLocked(false); eastExitCenter.setVisible(true); }
                     if (westExitCenter != null) { westExitCenter.setLocked(false); westExitCenter.setVisible(true); }
                     break;

                 case "plants": // Should be solved via USE pesticide, but handle here just in case
                      gui.displayMessage("Les plantes carnivores se rétractent.");
                      Exit eastExitTropical = currentZone.getExit("E");
                      if (eastExitTropical != null) { eastExitTropical.setLocked(false); eastExitTropical.setVisible(true); }
                      // Also reveal chest if not already visible? (Handled by USE PESTICIDE ideally)
                      break;

                 // Chest puzzles in Archives just give items as reward below
                 case "chest1":
                 case "chest2":
                 case "chest3":
                     break; // Item reward handled below

                default:
                    // No specific action besides giving reward if any
                    break;
            }

            // Donner la récompense standard s'il y en a une
            Item reward = puzzle.getReward();
            if (reward != null) {
                if (player.addItem(reward)) {
                    gui.displayMessage("Vous avez reçu " + reward.getName() + " ('" + reward.getDescription() + "') comme récompense !");
                    // Special action for pages: reveal hidden labyrinth exit hint
                    if (reward.getName().equals("page6")) {
                         player.addNote("Page 6 mentionne un labyrinthe caché accessible depuis la Serre Désertique.");
                    }
                } else {
                     // Drop reward in the room if inventory is full
                     currentZone.addItem(reward);
                     gui.displayMessage("Vous avez trouvé " + reward.getName() + ", mais votre inventaire est plein. L'objet a été laissé ici.");
                }
            }
        } else {
            gui.displayMessage("Ce n'est pas la bonne réponse pour '" + puzzleName + "'. Essayez encore.");
            if (puzzle.getHint() != null && !puzzle.getHint().isEmpty()) {
                gui.displayMessage("Indice : " + puzzle.getHint());
            }
        }
    }
    
    /**
     * La méthode principale pour démarrer le jeu
     * 
     * @param args Arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
} 
