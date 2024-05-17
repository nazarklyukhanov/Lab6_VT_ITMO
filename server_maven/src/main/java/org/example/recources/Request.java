package org.example.recources;

import java.io.Serializable;

public class Request implements Serializable {
    private String message;
    private MusicBand musicBand;

    public Request(String message, MusicBand musicBand) {
        this.message = message;
        this.musicBand = musicBand;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }
}
