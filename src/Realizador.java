public class Realizador {
    int directorId;
    String directorName;
    int movieId;

    public Realizador(int directorId, String directorName, int movieId) {
        this.directorId = directorId;
        this.directorName = directorName;
        this.movieId = movieId;
    }

    public int getDirectorId() { return directorId; }
    public String getDirectorName() { return directorName; }
    public int getMovieId() { return movieId; }

    @Override
    public String toString() {
        return directorId + " | " + directorName + " | " + movieId;
    }
}