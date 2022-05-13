package com.example.fxmlplayer;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.util.concurrent.Callable;

public class Controller {

    @FXML
    private Label Durations ;

    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;

    @FXML
    private ImageView play;

    @FXML
    private ImageView stop;

    @FXML
    private ImageView exit;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider progressBar;

    @FXML
    public void initialize() {
        String video = getClass().getResource("hiphop.mp4").toExternalForm();
        Media media = new Media(video);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        DurationLabel();

        String buttonPlay = getClass().getResource("/play.png").toExternalForm();
        Image image = new Image(buttonPlay);
        play.setImage(image);


        volumeSlider.setValue(mediaPlayer.getVolume()*100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue()/100);
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                progressBar.setValue(newValue.toSeconds());
            }
        });
        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
            }
        });

        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration total=media.getDuration();
                progressBar.setMax(total.toSeconds());
            }
        });
    }

    @FXML
    void playVideo(MouseEvent event) {
        if(mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING){
            mediaPlayer.play();
            String buttonPlay = getClass().getResource("/pause-button.png").toExternalForm();
            Image image1 = new Image(buttonPlay);
            play.setImage(image1);
        }else {
            mediaPlayer.pause();
            String buttonPlay1 = getClass().getResource("/play.png").toExternalForm();
            Image image1 = new Image(buttonPlay1);
            play.setImage(image1);
        }
    }

    @FXML
    void stopVideo(MouseEvent event) {
        mediaPlayer.stop();
    }

    @FXML
    void exitApplication(MouseEvent event) {
        Platform.exit();
    }



    public String getTime(Duration time){
        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes();
        int seconds = (int) time.toSeconds();

        if (seconds > 59) seconds = seconds % 60;
        if (minutes > 59) minutes = minutes % 60;
        if (hours > 59) hours = hours % 60;

        if(hours > 0 ) return String.format("%d:%02d%02d",
                hours, minutes, seconds);

        else return String.format("%02d:%02d",
                minutes,seconds);


    }
    public void  DurationLabel(){
        Durations.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(mediaPlayer.getCurrentTime()) ;
            }
        },mediaPlayer.currentTimeProperty()));
    }
}

