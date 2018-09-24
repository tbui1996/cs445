package cs445.a4;

/**
 * This abstract data type represents the backend for a streaming radio service.
 * It stores the songs, stations, and users in the system, as well as the
 * like/dislike ratings that users assign to songs.
 */
public interface StreamingRadio {

    /**
     * The abstract methods below are declared as void methods with no
     * parameters. You need to expand each declaration to specify a return type
     * and parameters, as necessary. You also need to include a detailed comment
     * for each abstract method describing its effect, its return value, any
     * corner cases that the client may need to consider, any exceptions the
     * method may throw (including a description of the circumstances under
     * which this will happen), and so on. You should include enough details
     * that a client could use this data structure without ever being surprised
     * or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a new song to the system. If the song is already in the system
	 * then the method will throw an IllegalArgumentException. If the 
	 * song is null, a NullPointerException will be thrown. If song
	 * is added successfully, no exceptions are thrown and nothing is returned.
	 * The song will then appear in the system if added successfully.
     *@param Song theSong being added to the system
     *@throws NullPointerException if theSong is invalid
     *@throws IllegalArgumentException if the song is already in the system
     */
    public void addSong(Song theSong) throw IllegalArgumentException, NullPointerException;

    /**
     * Removes an existing song from the system.
     *If the song that is to be removed doest not exist in the system, the method will
     *return an IllegalArgumentException
     *@param theSong is the song that is to be removed from the system
     *@throws NullPointerException is theSong is null
     *@throw IllegalArgumentException if the video doesn't exist in the system
     */
    public void removeSong(Song theSong);

    /**
     * Adds an existing song to the playlist for an existing radio station.
     *If the song does not exist, the method will throw a NullPointerException.
     *If no exceptions are thrown, theSong should be added to the end of the playlist for theStation. 
     *
     *@param song to be added to the playlist of the station
     *@throws NullPointerException if the song is invalid
     *@param theStation station which the song is being added to
     */
    public void addToStation(Station thestation,Song theSong) throws NullPointerException;

    /**
     * Removes a song from the playlist for a radio station.
     *If the song or playlist is null, the the method will throw a NullPointerException.
     *If the playlist does not exist, the method will throw an IllegalArgumentException.
     *If the song does not exist in the playlist, then the method will throw an IllegalArgumentException.
     *@param song the song to be removed
     *@param station the station the song is being removed by
     *@throws IllegalArgumentException if the Station does not exist
     *@throw IllegalArgumentException if the song does not exist in the Station
     */
    public void removeFromStation(Song theSong);

    /**
     * Sets a user's rating for a song, as either "like" or "dislike".
     *If the User has already rated the song, the method will throw an IllegalArgumentException
     *@throws IllegalArgumentException if theUser or theSong does not exist in the system
     */
    public void rateSong(User theUser, Song theSong,boolean rate);

    /**
     * Clears a user's rating on a song. If this user has rated this song and
     * the rating has not already been cleared, then the rating is cleared and
     * the state will appear as if the rating was never made. If there is no
     * such rating on record (either because this user has not rated this song,
     * or because the rating has already been cleared), then this method will
     * throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theSong song from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a
     * rating on record for the song
     * @throws NullPointerException if either the user or the song is null
     */
    public void clearRating(User theUser, Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Predicts whether a user will like or dislike a song (that they have not
     * yet rated).
     *If theSong or theUser is null the method will throw a NullPointerException
     *If theUser or theSong does not exist in the system, the method will throw an IllegalArgumentException
     *@param theSong the song that the rating is being predicted on
     *@param theUser the user that the rating is being predicted for
     *@throws NullPointerException if theUser or theSong is null
     *@throws IllegalArgumentException if theUser or theSong does not exist in the system
     *@throws IllegalArgumentException if theSong already has a rating
     */
    public boolean predictRating(Song theSong, User theUser) throws IllegalArgumentException, NullPointerException;

    /**
     * Suggests a song for a user that they are predicted to like.
     *If theUser is null, the method will throw a NullPointerException
     *If theUser does not have any previous rating on any songs, the method will return an IllegalArgumentException.
     @param theUser user that the song is being suggested to
     @return the song that is being suggested to theUser
     @throws NullPointerException if theUser is null or does not exist
     @throws IllegalArgumentException if theUser does not exist in the system
     @throws IllegalArgumentException if theUser has no previous ratings
     */
    public Song suggestSong(User theUser) throws NullPointerException;

}

