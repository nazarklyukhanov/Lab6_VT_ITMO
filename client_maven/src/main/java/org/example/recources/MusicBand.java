package org.example.recources;

import org.example.exceptions.WrongArgumentException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a music band.
 */
public class MusicBand implements Serializable {
    private static final Integer min = 1000000;
    private static final Integer max = 10000000;
    private static ArrayList<Integer> idList = new ArrayList<>();
    private int id;
    private String name; // Cannot be null or empty
    private Coordinates coordinates; // Cannot be null
    private LocalDateTime creationDate; // Cannot be null
    private Integer numberOfParticipants; // Cannot be null, must be greater than 0
    private Integer albumsCount; // Can be null, must be greater than 0 if not null
    private MusicGenre genre; // Cannot be null
    private Label label; // Cannot be null

    /**
     * Constructs a new MusicBand object.
     *
     * @param name                The name of the music band.
     * @param coordinates         The coordinates of the music band.
     * @param numberOfParticipants The number of participants in the music band.
     * @param albumsCount         The number of albums of the music band.
     * @param genre               The genre of the music band.
     * @param label               The label of the music band.
     */
    public MusicBand(String name, Coordinates coordinates,
                     Integer numberOfParticipants, Integer albumsCount,
                     MusicGenre genre, Label label) {
        int id = (int) Math.floor(Math.random() * (max - min + 1)) + min;
        while (idList.contains(id)) {
            id = (int) Math.floor(Math.random() * (max - min + 1)) + min;
        }
        idList.add(id);

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.numberOfParticipants = numberOfParticipants;
        this.albumsCount = albumsCount;
        this.genre = genre;
        this.label = label;
    }

    /**
     * Constructs a new MusicBand object with a specified ID.
     *
     * @param id                  The ID of the music band.
     * @param name                The name of the music band.
     * @param coordinates         The coordinates of the music band.
     * @param creationDate        The creation date of the music band.
     * @param numberOfParticipants The number of participants in the music band.
     * @param albumsCount         The number of albums of the music band.
     * @param genre               The genre of the music band.
     * @param label               The label of the music band.
     * @throws WrongArgumentException If the specified ID is not unique.
     */
    public MusicBand(int id, String name, Coordinates coordinates, LocalDateTime creationDate,
                     Integer numberOfParticipants, Integer albumsCount,
                     MusicGenre genre, Label label) throws WrongArgumentException {
        if (idList.contains(id)) {
            throw new WrongArgumentException("id");
        }
        idList.add(id);
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.albumsCount = albumsCount;
        this.genre = genre;
        this.label = label;
    }

    /**
     * Checks if the provided ID is unique.
     *
     * @param id The ID to check.
     * @return True if the ID is unique, otherwise false.
     */
    public static boolean isUniqueId(int id) {
        return !idList.contains(id);
    }

    /**
     * Removes the specified ID from the list of used IDs.
     *
     * @param id The ID to remove.
     */
    public static void removeId(int id) {
        idList.remove(Integer.valueOf(id));
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public Integer getAlbumsCount() {
        return albumsCount;
    }

    public void setAlbumsCount(Integer albumsCount) {
        this.albumsCount = albumsCount;
    }

    public MusicGenre getGenre() {
        return genre;
    }


    @Override
    public String toString() {
        return  "id=\"" + id + '\"' +
                " name=\"" + name + '\"' +
                " coordinates_x=\"" + coordinates.getX() + '\"' +
                " coordinates_y=\"" + coordinates.getY() + '\"' +
                " creationDate=\"" + creationDate + '\"' +
                " numberOfParticipants=\"" + numberOfParticipants + '\"' +
                " albumsCount=\"" + albumsCount + '\"' +
                " genre=\"" + genre + '\"' +
                " label_sales=\"" + label.getSales() + '\"';
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
