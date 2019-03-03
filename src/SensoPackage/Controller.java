/*
 *
 * lonely code by JanFre
 * Dies ist ein Produkt der Langeweile.
 *
 */

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
    @FXML
    Arc Rot, Blau, Grün, Gelb;
    @FXML
    ComboBox Wieviele;
    private boolean init = true;
    private int anzahl = 1;
    private int zähler = 0;
    private ArrayList<Integer> Reihenfolge = new ArrayList<Integer>();
    private ArrayList<Arc> ArcListe = new ArrayList<>();
    private ArrayList<Integer> EingebeListe = new ArrayList<Integer>();

    private ArrayList<MediaPlayer> loseL = new ArrayList<>();
    private ArrayList<MediaPlayer> winL = new ArrayList<>();

    private MediaPlayer mp1 = new MediaPlayer(new Media((getClass().getClassLoader().getResource("Sounds/ding-sound-effect_2.mp3").toString())));

    private MediaPlayer lose1 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/lose.mp3").toString()));
    private MediaPlayer lose2 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/retard-alert.mp3").toString()));
    private MediaPlayer lose3 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/sad.mp3").toString()));

    private MediaPlayer win1 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/careless_whispers.mp3").toString()));
    private MediaPlayer win2 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/for-honor-incredibilis.mp3").toString()));
    private MediaPlayer win3 = new MediaPlayer(new Media(getClass().getClassLoader().getResource("Sounds/victoryff.swf.mp3").toString()));

    public void MainGame() {
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
            ObservableList<String> options = FXCollections.observableArrayList(
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" //TODO
            );
            Wieviele.setItems(options);
            init = false;


        }
    }

    public void Blink(Arc arc) {
        Timer t2 = new Timer();
        t2.schedule(new TimerTask() {
            @Override
            public void run() {
                arc.setOpacity(0.5);
                mp1.stop();
                mp1.play();
                //mp1.setAutoPlay(true);
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
        anzahl = Wieviele.getSelectionModel().getSelectedIndex();
        t3.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int x = randInt(0, 3);
                Reihenfolge.add(zähler, x);
                Blink(ArcListe.get(x));
                zähler++;
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
            for (int i = 0; i < Reihenfolge.size(); i++) {
                if (Reihenfolge.get(i).intValue() == EingebeListe.get(i).intValue()) {
                    //System.out.println("IF:_1 " + Reihenfolge.get(i).intValue()+" : "+EingebeListe.get(i).intValue() +" :__" +i); Funktions kontrolle
                    if (Reihenfolge.size() - 1 == i) {
                        int x = randInt(0, 2);
                        winL.get(x).stop();
                        winL.get(x).play();
                        System.out.println("You win"); //TODO
                        Reihenfolge.clear();
                        EingebeListe.clear();
                    }
                } else {
                    int x = randInt(0, 2);
                    loseL.get(x).stop();
                    loseL.get(x).play();
                    System.out.println("You Lose"); //TODO
                    Reihenfolge.clear();
                    EingebeListe.clear();
                }
            }
        } catch (Exception ex) {
            Reihenfolge.clear();
            EingebeListe.clear();
        }
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
