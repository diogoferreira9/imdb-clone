public class Ator {
    int actorId;
    String actorName;
    char actorGender;
    int movieId;

    public Ator(int actorId, String actorName, char actorGender, int movieId) {
        this.actorId = actorId;
        this.actorName = actorName;
        this.actorGender = actorGender;
        this.movieId = movieId;
    }

    public int getActorId() { return actorId; }
    public String getActorName() { return actorName; }
    public char getActorGender() { return actorGender; }
    public int getMovieId() { return movieId; }

    @Override
    public String toString() {
        String generoCompleto = actorGender == 'M' ? "Masculino" : "Feminino";
        return actorId + " | " + actorName + " | " + generoCompleto + " | " + movieId;
    }
}