package SensoPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Arc;
import sun.security.krb5.internal.MethodData;

import java.util.*;

public class Controller {
    //FXML Imports
    @FXML
    Arc Rot, Blau, Grün, Gelb;
    @FXML
    ComboBox Wieviele;

    //Wiederholungs kontrolle
    private boolean init = true;
    private int anzahl = 1;
    private int zähler = 0;

    //Listen zur speicherung von Reihenfolgen oder der Arc bzw. Kreisviertel
    private ArrayList<Integer> Reihenfolge = new ArrayList<Integer>();
    private ArrayList<Arc> ArcListe = new ArrayList<>();
    private ArrayList<Integer> EingebeListe = new ArrayList<Integer>();

    //Speicher der verschiedenen Sounds wenn man Gewinnt bzw. Verliert
    private ArrayList<MediaPlayer> loseL = new ArrayList<>();
    private ArrayList<MediaPlayer> winL = new ArrayList<>();



    //Einbindung MediaPlayer mit Daten aus Resource files
    private MediaPlayer mp1 = new MediaPlayer(new Media((getClass().getClassLoader().getResource("Sounds/ding-sound-effect_2.mp3").toString())));
    private MediaPlayer lose1 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/lose.mp3").toString()));
    private MediaPlayer lose2 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/retard-alert.mp3").toString()));
    private MediaPlayer lose3 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/sad.mp3").toString()));

    private MediaPlayer win1 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/careless_whispers.mp3").toString()));
    private MediaPlayer win2 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/for-honor-incredibilis.mp3").toString()));
    private MediaPlayer win3 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/victoryff.swf.mp3").toString()));

    public void MainGame() {
        //Einmaliges einfügen in Listen
        if (init) {
            loseL.add(lose1);
            loseL.add(lose2);
            loseL.add(lose3);

            winL.add(win1);
            winL.add(win2);
            winL.add(win3);

            for (MediaPlayer m : loseL) {

            }
            for (MediaPlayer m : winL) {

            }

            mp1.setVolume(0.07);
            System.out.println(loseL.get(0).getVolume());

            //Erstellung von Keylisteners
            System.out.println("Init");
            ArcListe.add(Rot); //0
            ArcListe.add(Blau);//1
            ArcListe.add(Grün);//2
            ArcListe.add(Gelb);//3
            Timer t1 = new Timer();
            Rot.setOnMouseClicked(event -> {
                mp1.stop();
                mp1.play();
                Rot.setOpacity(0.5);
                EingebeListe.add(0);
                t1.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Rot.setOpacity(1);
                    }
                }, 800);
            });
            Blau.setOnMouseClicked(event -> {
                mp1.stop();
                mp1.play();
                Blau.setOpacity(0.5);
                EingebeListe.add(1);
                t1.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Blau.setOpacity(1);
                    }
                }, 800);
            });
            Grün.setOnMouseClicked(event -> {
                mp1.stop();
                mp1.play();
                Grün.setOpacity(0.5);
                EingebeListe.add(2);
                t1.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Grün.setOpacity(1);
                    }
                }, 800);
            });
            Gelb.setOnMouseClicked(event -> {
                mp1.stop();
                mp1.play();
                Gelb.setOpacity(0.5);
                EingebeListe.add(3);
                t1.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Gelb.setOpacity(1);
                    }
                }, 800);
            });
            //Erstellung des Inhalts der ComboBox mit Hilfe einer Observable list

            ObservableList<String> options = FXCollections.observableArrayList(
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" //TODO
            );
            Wieviele.setItems(options);
            init = false;


        }
    }
// Methode zum einmalingen aufblinken eines Arc
    public void Blink(Arc arc) {
        //Timer der 500ms wartet bis Blinken
        Timer t2 = new Timer();
        t2.schedule(new TimerTask() {
            @Override
            public void run() {
                //Ändern der Sichtbarkeit um einen Blink effect zu erstellen
                arc.setOpacity(0.5);
                //Sound beim aufblinken
                mp1.stop();
                mp1.play();
                //mp1.setAutoPlay(true);
                //Weiterer Timer der die Normale sichtbarkeit wieder herstellt
                t2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        arc.setOpacity(1);
                    }
                }, 500);
            }
        }, 500);
    }

    public void GameLogik() {
        Timer t3 = new Timer();
        //auswahl aus der Combobox (Wie oft nach einander Binken)
        anzahl = Wieviele.getSelectionModel().getSelectedIndex();
        t3.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //Randome reigenfolge
                int x = randInt(0, 3);
                //Speichern der Reihenfolge zur überprüfung
                Reihenfolge.add(zähler, x);
                //Binken
                Blink(ArcListe.get(x));
                zähler++;
                //Beenden des Timers und brechen der Schleife
                if (zähler > anzahl) {
                    t3.cancel();
                    zähler = 0;
                    EingebeListe.clear();
                }
            }
        }, 0, 1000);
    }

    public void Check() {
        System.out.println("Check");
        try {
            //Überprüft eingaben mit gespeicherten Daten    .size für größe der Liste
            for (int i = 0; i < Reihenfolge.size(); i++) {
                //vergleicht ob eingabe == dem gespeicherten
                if (Reihenfolge.get(i).intValue() == EingebeListe.get(i).intValue()) {
                    //System.out.println("IF:_1 " + Reihenfolge.get(i).intValue()+" : "+EingebeListe.get(i).intValue() +" :__" +i); Funktions kontrolle
                    //Wenn i die maximale größe des Array erreicht
                    if (Reihenfolge.size() - 1 == i) {
                        //Auswahl Sound
                        int x = randInt(0, 2);
                        //Abspielen vom Sound
                        winL.get(x).stop();
                        winL.get(x).play();
                        System.out.println("You win"); //TODO
                        //Liste für weitere Spiele leeren
                        Reihenfolge.clear();
                        EingebeListe.clear();
                    }
                } else {
                    //Auswahl Sound
                    int x = randInt(0, 2);
                    //Abspielen vom Sound
                    loseL.get(x).stop();
                    loseL.get(x).play();
                    System.out.println("You Lose"); //TODO
                    //Liste für weitere Spiele leeren
                    Reihenfolge.clear();
                    EingebeListe.clear();
                }
            }
            //Catch falls nicht gleich oft gedrückt wird wie ausgewählt wurde
        } catch (Exception ex) {
            Reihenfolge.clear();
            EingebeListe.clear();
        }
    }

    public static int randInt(int min, int max) {
        //Randome wert
        Random rand = new Random();
        //Auswahl eines Int zwischen min und max
        //Es wird ein randome ist genommen und der maximal wert abgezogen und der minimal darauf gerechnet
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
