public class Genero {
    int genreId;
    String genreName;

    public Genero(int genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public int getGenreId() { return genreId; }
    public String getGenreName() { return genreName; }

    @Override
    public String toString() {
        return genreId + " | " + genreName;
    }
}