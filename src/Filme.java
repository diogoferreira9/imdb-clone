import java.util.ArrayList;
import java.util.Collections;

public class Filme {
    int movieId;
    String movieName;
    double movieDuration;
    int movieBudget;
    String movieReleaseDate;
    double rating = 0.0;

    // Para as associações
    ArrayList<Integer> generosIds = new ArrayList<>();
    ArrayList<Integer> realizadoresIds = new ArrayList<>();

    public Filme(int movieId, String movieName, double movieDuration, int movieBudget, String movieReleaseDate) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieDuration = movieDuration;
        this.movieBudget = movieBudget;
        this.movieReleaseDate = movieReleaseDate;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void adicionarGenero(int generoId) {
        if (!generosIds.contains(generoId)) {
            generosIds.add(generoId);
        }
    }

    public void adicionarRealizador(int realizadorId) {
        if (!realizadoresIds.contains(realizadorId)) {
            realizadoresIds.add(realizadorId);
        }
    }

    @Override
    public String toString() {
        // Formatar data para AAAA-MM-DD (já deve vir assim, mas fica seguro)
        String dataFormatada = movieReleaseDate.replace("/", "-");
        String[] partes = dataFormatada.split("-");
        if (partes.length == 3 && partes[0].length() != 4) {
            dataFormatada = partes[2] + "-" + partes[1] + "-" + partes[0];
        }

        // Contar atores masculinos e femininos
        int masc = 0;
        int fem = 0;
        for (Ator ator : pt.ulusofona.aed.deisimdb.Main.atorArrayList) {
            if (ator.getMovieId() == this.movieId) {
                if (ator.getActorGender() == 'M'){
                    masc++;
                }
                else if (ator.getActorGender() == 'F') {
                    fem++;
                }
            }
        }

        // Casos para movieId
        if (movieId >= 1000) {
            // Só números
            return movieId + " | " + movieName + " | " + dataFormatada + " | " +
                    generosIds.size() + " | " +
                    realizadoresIds.size() + " | " +
                    masc + " | " + fem;
        } else {
            // Listas ordenadas de nomes de géneros e realizadores
            ArrayList<String> nomesGeneros = new ArrayList<>();
            for (int generoId : generosIds) {
                for (Genero genero : pt.ulusofona.aed.deisimdb.Main.generoArrayList) {
                    if (genero.getGenreId() == generoId) {
                        nomesGeneros.add(genero.getGenreName());
                        break;
                    }
                }
            }
            Collections.sort(nomesGeneros);

            ArrayList<String> nomesRealizadores = new ArrayList<>();
            for (int realizadorId : realizadoresIds) {
                for (Realizador realizador : pt.ulusofona.aed.deisimdb.Main.realizadorArrayList) {
                    if (realizador.getDirectorId() == realizadorId && realizador.getMovieId() == this.movieId) {
                        nomesRealizadores.add(realizador.getDirectorName());
                        break;
                    }
                }
            }
            Collections.sort(nomesRealizadores);

            // Monta string final
            return movieId + " | " + movieName + " | " + dataFormatada + " | " +
                    String.join(",", nomesGeneros) + " | " +
                    String.join(",", nomesRealizadores) + " | " +
                    masc + " | " + fem;
        }
    }
}
