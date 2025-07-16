package Main;

import java.time.LocalDate;
import java.util.*;

public class Queries {

    //OBG
    public static Result countMoviesMonthYear(String month, String year) {
        // Validação do mês (1-12) e ano (4 dígitos)
        int mes, ano;
        try {
            mes = Integer.parseInt(month);
            ano = Integer.parseInt(year);
            if (mes < 1 || mes > 12 || year.length() != 4) {
                return new Result(false, "COUNT_MOVIES_MONTH_YEAR", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "COUNT_MOVIES_MONTH_YEAR", "Erro: Comando invalido");
        }

        int count = 0;
        for (Filme filme : Main.filmeArrayList) {
            String[] partesData = filme.movieReleaseDate.split("-");
            if (partesData.length == 3) {
                // partesData[1] = mês, partesData[2] = ano
                int filmeMes = Integer.parseInt(partesData[1]);
                int filmeAno = Integer.parseInt(partesData[2]);
                if (filmeMes == mes && filmeAno == ano) {
                    count++;
                }
            }
        }

        return new Result(true, "COUNT_MOVIES_MONTH_YEAR", String.valueOf(count));
    }

    //OBG
    public static Result countMoviesDirector(String directorName) {
        int count = 0;

        for (Realizador r : Main.realizadorArrayList) {
            if (r.getDirectorName().equalsIgnoreCase(directorName)) {
                count++;
            }
        }

        return new Result(true, "COUNT_MOVIES_DIRECTOR", String.valueOf(count));
    }

    //OBG
    public static Result countActorsIn2Years(String year1, String year2) {
        int ano1, ano2;

        try {
            ano1 = Integer.parseInt(year1);
            ano2 = Integer.parseInt(year2);
            if (year1.length() != 4 || year2.length() != 4) {
                return new Result(false, "COUNT_ACTORS_IN_2_YEARS", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "COUNT_ACTORS_IN_2_YEARS", "Erro: Comando invalido");
        }

        // Guardar movieIds desse(s) ano(s)
        HashSet<Integer> filmesAno1 = new HashSet<>();
        HashSet<Integer> filmesAno2 = new HashSet<>();

        for (Filme f : Main.filmeArrayList) {
            String[] partes = f.movieReleaseDate.split("-");
            if (partes.length == 3) {
                int filmeAno = Integer.parseInt(partes[2]);
                if (filmeAno == ano1) {
                    filmesAno1.add(f.movieId);
                }
                if (filmeAno == ano2) {
                    filmesAno2.add(f.movieId);
                }
            }
        }

        // Se os anos forem iguais, contar atores com >=2 filmes nesse ano
        if (ano1 == ano2) {
            HashMap<Integer, Integer> contagemPorAtor = new HashMap<>();

            for (Ator a : Main.atorArrayList) {
                if (filmesAno1.contains(a.getMovieId())) {
                    contagemPorAtor.put(a.getActorId(),
                            contagemPorAtor.getOrDefault(a.getActorId(), 0) + 1);
                }
            }

            int total = 0;
            for (int count : contagemPorAtor.values()) {
                if (count >= 2) {
                    total++;
                }
            }

            return new Result(true, "COUNT_ACTORS_IN_2_YEARS", String.valueOf(total));
        }

        // Se os anos forem diferentes, procurar interseção
        HashSet<Integer> atoresAno1 = new HashSet<>();
        HashSet<Integer> atoresAno2 = new HashSet<>();

        for (Ator a : Main.atorArrayList) {
            if (filmesAno1.contains(a.getMovieId())) {
                atoresAno1.add(a.getActorId());
            }
            if (filmesAno2.contains(a.getMovieId())) {
                atoresAno2.add(a.getActorId());
            }
        }

        atoresAno1.retainAll(atoresAno2);
        return new Result(true, "COUNT_ACTORS_IN_2_YEARS", String.valueOf(atoresAno1.size()));
    }

    public static Result countMoviesBetweenYearsWithNActors(String yearStart, String yearEnd, String min, String max) {
        int inicio, fim, minAtores, maxAtores;

        try {
            inicio = Integer.parseInt(yearStart);
            fim = Integer.parseInt(yearEnd);
            minAtores = Integer.parseInt(min);
            maxAtores = Integer.parseInt(max);

            if (inicio > fim || minAtores < 0 || maxAtores <= minAtores) {
                return new Result(false, "COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS", "Erro: Comando invalido");
        }

        Map<Integer, Set<String>> atoresPorFilme = new HashMap<>();
        for (Ator a : Main.atorArrayList) {
            String nomeNormalizado = a.getActorName().trim().toLowerCase();
            atoresPorFilme
                    .computeIfAbsent(a.getMovieId(), _ -> new HashSet<>())
                    .add(nomeNormalizado);
        }

        int contadorFilmesValidos = 0;

        for (Filme f : Main.filmeArrayList) {
            String[] partesData = f.movieReleaseDate.split("-");
            if (partesData.length != 3) {
                continue;
            }

            int ano;
            try {
                ano = Integer.parseInt(partesData[2]);
            } catch (Exception e) {
                continue;
            }

            if (ano <= inicio || ano >= fim) {
                continue;
            }

            Set<String> nomesAtores = atoresPorFilme.get(f.movieId);
            if (nomesAtores != null) {
                int countAtores = nomesAtores.size();
                if (countAtores > minAtores && countAtores < maxAtores) {
                    contadorFilmesValidos++;
                }
            }
        }

        return new Result(true, "COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS", String.valueOf(contadorFilmesValidos));
    }

    //OBG
    public static Result getMoviesActorYear(String year, String fullName) {
        try {
            if (year.length() != 4) {
                return new Result(false, "GET_MOVIES_ACTOR_YEAR", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "GET_MOVIES_ACTOR_YEAR", "Erro: Comando invalido");
        }

        ArrayList<Integer> movieIdsDoAtor = new ArrayList<>();

        for (Ator a : Main.atorArrayList) {
            if (a.getActorName().equalsIgnoreCase(fullName)) {
                movieIdsDoAtor.add(a.getMovieId());
            }
        }

        if (movieIdsDoAtor.isEmpty()) {
            return new Result(true, "GET_MOVIES_ACTOR_YEAR", "No results");
        }

        ArrayList<Filme> filmesDoAno = new ArrayList<>();
        for (Filme f : Main.filmeArrayList) {
            if (movieIdsDoAtor.contains(f.movieId)) {
                String[] partes = f.movieReleaseDate.split("-");
                if (partes.length == 3 && partes[2].equals(year)) {
                    filmesDoAno.add(f);
                }
            }
        }

        if (filmesDoAno.isEmpty()) {
            return new Result(true, "GET_MOVIES_ACTOR_YEAR", "No results");
        }

        // Ordenação dos filmes pela data (crescente)
        filmesDoAno.sort(Comparator.comparing(f -> {
            String[] partes = f.movieReleaseDate.split("-");
            //dd-MM-yyyy
            return LocalDate.of(
                    Integer.parseInt(partes[2]),  // ano
                    Integer.parseInt(partes[1]),  // mês
                    Integer.parseInt(partes[0])   // dia
            );
        }));

        StringBuilder sb = new StringBuilder();
        for (Filme f : filmesDoAno) {
            sb.append(f.movieName).append("\n");
        }

        sb.setLength(sb.length() - 1); // remover último \n
        return new Result(true, "GET_MOVIES_ACTOR_YEAR", sb.toString());
    }

    public static Result getMoviesWithActorContaining(String substr) {
        ArrayList<String> nomesFilmes = new ArrayList<>();


        for (Ator ator : Main.atorArrayList) {
            if (ator.getActorName().contains(substr)) {
                // Encontraste um ator com a substring!
                int movieId = ator.getMovieId();
                // Procura o filme
                for (Filme filme : Main.filmeArrayList) {
                    if (filme.movieId == movieId) {
                        if (!nomesFilmes.contains(filme.movieName)) {
                            nomesFilmes.add(filme.movieName);
                        }
                    }
                }
            }
        }

        if (nomesFilmes.isEmpty()) {
            return new Result(true, "GET_MOVIES_WITH_ACTOR_CONTAINING", "No results");
        }

        nomesFilmes.sort(String::compareToIgnoreCase);

        StringBuilder sb = new StringBuilder();
        for (String nome : nomesFilmes) {
            sb.append(nome).append("\n");
        }
        sb.setLength(sb.length() - 1);

        return new Result(true, "GET_MOVIES_WITH_ACTOR_CONTAINING", sb.toString());
    }


    public static Result getTop4YearsWithMoviesContaining(String searchString) {
        // Mapa: ano -> contagem de filmes com o título contendo a string (case-sensitive)
        HashMap<Integer, Integer> filmesPorAno = new HashMap<>();

        for (Filme f : Main.filmeArrayList) {

            if (f.movieName.contains(searchString)) {
                String[] partesData = f.movieReleaseDate.split("-");
                if (partesData.length == 3) {
                    try {
                        int ano = Integer.parseInt(partesData[2]);
                        filmesPorAno.put(ano, filmesPorAno.getOrDefault(ano, 0) + 1);
                    } catch (NumberFormatException e) {
                        // Ignorar datas com ano inválido ou mal formatado
                    }
                }
            }
        }

        if (filmesPorAno.isEmpty()) {
            return new Result(true, "GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING", "No results");
        }

        List<Map.Entry<Integer, Integer>> lista = new ArrayList<>(filmesPorAno.entrySet());

        lista.sort((a, b) -> {
            int comp = Integer.compare(b.getValue(), a.getValue()); // mais filmes primeiro
            if (comp == 0) {
                return Integer.compare(a.getKey(), b.getKey());
            }
            return comp;
        });

        StringBuilder sb = new StringBuilder();
        int limite = Math.min(4, lista.size());
        for (int i = 0; i < limite; i++) {
            Map.Entry<Integer, Integer> entry = lista.get(i);
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        sb.setLength(sb.length() - 1); // remove o último \n
        return new Result(true, "GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING", sb.toString());
    }

    //OBG
    public static Result getActorsByDirector(String num, String fullName) {
        int minimo;
        try {
            minimo = Integer.parseInt(num);
            if (minimo < 1) {
                return new Result(false, "GET_ACTORS_BY_DIRECTOR", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "GET_ACTORS_BY_DIRECTOR", "Erro: Comando invalido");
        }

        // Recolher todos os filmes do realizador
        HashSet<Integer> filmesDoRealizador = new HashSet<>();
        boolean realizadorExiste = false;

        for (Realizador r : Main.realizadorArrayList) {
            if (r.getDirectorName().equalsIgnoreCase(fullName)) {
                filmesDoRealizador.add(r.getMovieId());
                realizadorExiste = true;
            }
        }

        //participações dos atores nesses filmes
        HashMap<String, Integer> contagem = new HashMap<>();
        for (Ator a : Main.atorArrayList) {
            if (filmesDoRealizador.contains(a.getMovieId())) {
                String nome = a.getActorName();
                contagem.put(nome, contagem.getOrDefault(nome, 0) + 1);
            }
        }

        // filtrar os que participaram >= minimo vezes
        ArrayList<String> resultado = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            if (entry.getValue() >= minimo) {
                resultado.add(entry.getKey() + ":" + entry.getValue());
            }
        }

        if (!realizadorExiste || resultado.isEmpty()) {
            return new Result(true, "GET_ACTORS_BY_DIRECTOR", "No results");
        }

        Collections.sort(resultado);

        StringBuilder sb = new StringBuilder();
        for (String linha : resultado) {
            sb.append(linha).append("\n");
        }

        sb.setLength(sb.length() - 1); // remove último \n
        return new Result(true, "GET_ACTORS_BY_DIRECTOR", sb.toString());
    }

    //OBG
    public static Result topMonthMovieCount(String year) {
        int ano;
        try {
            ano = Integer.parseInt(year);
            if (year.length() != 4) {
                return new Result(false, "TOP_MONTH_MOVIE_COUNT", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "TOP_MONTH_MOVIE_COUNT", "Erro: Comando invalido");
        }

        // Mapear mês -> nº de filmes
        HashMap<Integer, Integer> filmesPorMes = new HashMap<>();

        for (Filme f : Main.filmeArrayList) {
            String[] partes = f.movieReleaseDate.split("-");
            if (partes.length != 3) {
                continue;
            }

            try {
                int mes = Integer.parseInt(partes[1]);
                int anoFilme = Integer.parseInt(partes[2]);

                if (anoFilme == ano) {
                    filmesPorMes.put(mes, filmesPorMes.getOrDefault(mes, 0) + 1);
                }
            } catch (Exception e) {
                // Ignorar datas mal formatadas
            }
        }

        if (filmesPorMes.isEmpty()) {
            return new Result(true, "TOP_MONTH_MOVIE_COUNT", "No results");
        }

        // Encontrar o mês com mais filmes (e mais recente em caso de empate)
        int melhorMes = -1;
        int maxFilmes = -1;

        for (Map.Entry<Integer, Integer> entry : filmesPorMes.entrySet()) {
            int mes = entry.getKey();
            int count = entry.getValue();

            if (count > maxFilmes || (count == maxFilmes && mes < melhorMes)) {
                melhorMes = mes;
                maxFilmes = count;
            }
        }

        return new Result(true, "TOP_MONTH_MOVIE_COUNT", melhorMes + ":" + maxFilmes);
    }


    public static Result topVotedActors(String num, String year) {
        int limite, ano;

        try {
            limite = Integer.parseInt(num);
            ano = Integer.parseInt(year);
            if (limite < 1 || year.length() != 4) {
                return new Result(false, "TOP_VOTED_ACTORS", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "TOP_VOTED_ACTORS", "Erro: Comando invalido");
        }

        // Obter os filmes lançados nesse ano
        HashSet<Integer> filmesDoAno = new HashSet<>();
        HashMap<Integer, Double> ratingsPorFilme = new HashMap<>();

        for (Filme f : Main.filmeArrayList) {
            String[] partes = f.movieReleaseDate.split("-");
            if (partes.length == 3) {
                try {
                    int anoFilme = Integer.parseInt(partes[2]);
                    if (anoFilme == ano) {
                        filmesDoAno.add(f.movieId);
                        ratingsPorFilme.put(f.movieId, f.rating); // assume-se que f.rating existe
                    }
                } catch (NumberFormatException e) {
                    //cenas escritas
                }
            }
        }

        // Map: ator -> lista de ratings dos filmes do ano
        HashMap<String, ArrayList<Double>> ratingPorAtor = new HashMap<>();

        for (Ator a : Main.atorArrayList) {
            if (filmesDoAno.contains(a.getMovieId())) {
                String nome = a.getActorName();
                double rating = ratingsPorFilme.getOrDefault(a.getMovieId(), 0.0);
                ratingPorAtor.putIfAbsent(nome, new ArrayList<>());
                ratingPorAtor.get(nome).add(rating);
            }
        }

        // Calcular média de rating por ator
        ArrayList<String> resultado = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Double>> entry : ratingPorAtor.entrySet()) {
            String nome = entry.getKey();
            ArrayList<Double> ratings = entry.getValue();

            double soma = 0.0;
            for (double r : ratings) {
                soma += r;
            }
            double media = ratings.isEmpty() ? 0.0 : soma / ratings.size();

            resultado.add(nome + ":" + String.format("%.1f", media));
        }

        // Ordenar por rating desc
        resultado.sort((a, b) -> {
            double r1 = Double.parseDouble(a.split(":")[1]);
            double r2 = Double.parseDouble(b.split(":")[1]);
            return Double.compare(r2, r1); // ordem decrescente
        });

        // Limitar aos N primeiros
        int max = Math.min(limite, resultado.size());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < max; i++) {
            sb.append(resultado.get(i)).append("\n");
        }

        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1); // remove último \n
        }

        return new Result(true, "TOP_VOTED_ACTORS", sb.toString());
    }

    //OBG
    public static Result topMoviesWithMoreGender(String num, String year, String gender) {
        int limite, ano;
        char genero;

        try {
            limite = Integer.parseInt(num);
            ano = Integer.parseInt(year);
            if (limite < 1 || year.length() != 4) {
                return new Result(false, "TOP_MOVIES_WITH_MORE_GENDER", "Erro: Comando invalido");
            }
            genero = gender.trim().toUpperCase().charAt(0);
            if (genero != 'M' && genero != 'F') {
                return new Result(false, "TOP_MOVIES_WITH_MORE_GENDER", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "TOP_MOVIES_WITH_MORE_GENDER", "Erro: Comando invalido");
        }

        // Map: movieId -> count de atores do género
        HashMap<Integer, Integer> contagemPorFilme = new HashMap<>();

        for (Filme f : Main.filmeArrayList) {
            String[] partes = f.movieReleaseDate.split("-");
            if (partes.length != 3) {
                continue;
            }

            try {
                int anoFilme = Integer.parseInt(partes[2]);
                if (anoFilme == ano) {
                    int count = 0;
                    for (Ator a : Main.atorArrayList) {
                        if (a.getMovieId() == f.movieId && a.getActorGender() == genero) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        contagemPorFilme.put(f.movieId, count);
                    }
                }
            } catch (NumberFormatException e) {
                //Cenas escritas
            }
        }

        // Criar lista com nome do filme e count
        ArrayList<String> resultado = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : contagemPorFilme.entrySet()) {
            int movieId = entry.getKey();
            int count = entry.getValue();
            String nomeFilme = "";
            for (Filme f : Main.filmeArrayList) {
                if (f.movieId == movieId) {
                    nomeFilme = f.movieName;
                    break;
                }
            }
            resultado.add(nomeFilme + ":" + count);
        }

        resultado.sort((a, b) -> {
            int countA = Integer.parseInt(a.substring(a.lastIndexOf(':') + 1).trim());
            int countB = Integer.parseInt(b.substring(b.lastIndexOf(':') + 1).trim());

            if (countA != countB) {
                return Integer.compare(countB, countA); // mais atores primeiro
            }

            String nomeA = a.substring(0, a.lastIndexOf(':')).trim();
            String nomeB = b.substring(0, b.lastIndexOf(':')).trim();

            return nomeA.compareToIgnoreCase(nomeB); // alfabética em caso de empate
        });

        // Construir output
        StringBuilder sb = new StringBuilder();
        int max = Math.min(limite, resultado.size());
        for (int i = 0; i < max; i++) {
            sb.append(resultado.get(i)).append("\n");
        }

        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1); // remover último \n
        }

        return new Result(true, "TOP_MOVIES_WITH_MORE_GENDER", sb.toString());
    }

    //OBG
    public static Result topMoviesWithGenderBias(String num, String year) {
        int limite, ano;
        try {
            limite = Integer.parseInt(num);
            ano = Integer.parseInt(year);
            if (limite < 1 || year.length() != 4) {
                return new Result(false, "TOP_MOVIES_WITH_GENDER_BIAS", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "TOP_MOVIES_WITH_GENDER_BIAS", "Erro: Comando invalido");
        }

        ArrayList<String> resultado = new ArrayList<>();

        for (Filme f : Main.filmeArrayList) {
            String[] partes = f.movieReleaseDate.split("-");
            if (partes.length != 3) {
                continue;
            }

            int anoFilme;
            try {
                anoFilme = Integer.parseInt(partes[2]);
            } catch (Exception e) {
                continue;
            }

            if (anoFilme != ano) {
                continue;
            }

            int masc = 0, fem = 0;
            for (Ator a : Main.atorArrayList) {
                if (a.getMovieId() == f.movieId) {
                    if (a.getActorGender() == 'M') {
                        masc++;
                    }
                    else if (a.getActorGender() == 'F') {
                        fem++;
                    }
                }
            }

            int total = masc + fem;
            if (total < 11) {
                continue;
            }

            int percentagem = (int) Math.round((Math.max(masc, fem) * 100.0) / total);
            char generoDominante = masc >= fem ? 'M' : 'F';

            resultado.add(f.movieName + ":" + generoDominante + ":" + percentagem);
        }

        if (resultado.isEmpty()) {
            return new Result(true, "TOP_MOVIES_WITH_GENDER_BIAS", "No results");
        }

        resultado.sort((a, b) -> {
            int percA = Integer.parseInt(a.substring(a.lastIndexOf(':') + 1));
            int percB = Integer.parseInt(b.substring(b.lastIndexOf(':') + 1));
            if (percA != percB) {
                return Integer.compare(percB, percA);
            }
            String nomeA = a.substring(0, a.indexOf(':')).trim();
            String nomeB = b.substring(0, b.indexOf(':')).trim();
            return nomeA.compareToIgnoreCase(nomeB);
        });

        StringBuilder sb = new StringBuilder();
        int max = Math.min(limite, resultado.size());
        for (int i = 0; i < max; i++) {
            sb.append(resultado.get(i)).append("\n");
        }

        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1);
        }

        return new Result(true, "TOP_MOVIES_WITH_GENDER_BIAS", sb.toString());
    }

    public static Result top6DirectorsWithinFamily(String yearStart, String yearEnd) {
        int inicio, fim;
        try {
            inicio = Integer.parseInt(yearStart);
            fim = Integer.parseInt(yearEnd);
            if (inicio > fim) {
                return new Result(false, "TOP_6_DIRECTORS_WITHIN_FAMILY", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "TOP_6_DIRECTORS_WITHIN_FAMILY", "Erro: Comando invalido");
        }

        HashMap<String, Integer> contagem = new HashMap<>();

        for (Filme f : Main.filmeArrayList) {
            String[] partes = f.movieReleaseDate.split("-");
            if (partes.length != 3) {
                continue;
            }

            int ano;
            try {
                ano = Integer.parseInt(partes[2]);
            } catch (Exception e) {
                continue;
            }

            if (ano < inicio || ano > fim) {
                continue;
            }

            ArrayList<Realizador> realizadores = Main.realizadoresPorFilmeMap.get(f.movieId);
            if (realizadores == null || realizadores.size() < 2) {
                continue;
            }

            // agrupa os realizadores por ultimo nome
            HashMap<String, ArrayList<Realizador>> familiaPorApelido = new HashMap<>();
            for (Realizador r : realizadores) {
                String[] partesNome = r.getDirectorName().trim().split(" ");
                String ultimo = partesNome[partesNome.length - 1].toLowerCase();
                familiaPorApelido.putIfAbsent(ultimo, new ArrayList<>());
                familiaPorApelido.get(ultimo).add(r);
            }

            // conta famílias com 2 ou mais realizadores no mesmo filme
            for (ArrayList<Realizador> familia : familiaPorApelido.values()) {
                if (familia.size() >= 2) {
                    for (Realizador r : familia) {
                        String nome = r.getDirectorName();
                        contagem.put(nome, contagem.getOrDefault(nome, 0) + 1);
                    }
                }
            }
        }

        if (contagem.isEmpty()) {
            return new Result(true, "TOP_6_DIRECTORS_WITHIN_FAMILY", "No results");
        }

        ArrayList<Map.Entry<String, Integer>> lista = new ArrayList<>(contagem.entrySet());

        lista.sort((a, b) -> {
            int comp = Integer.compare(b.getValue(), a.getValue()); // por contagem desc
            if (comp == 0) {
                return a.getKey().compareToIgnoreCase(b.getKey()); // por nome asc
            }
            return comp;
        });

        StringBuilder sb = new StringBuilder();
        int limite = Math.min(6, lista.size());
        for (int i = 0; i < limite; i++) {
            Map.Entry<String, Integer> entry = lista.get(i);
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        sb.setLength(sb.length() - 1); // remove o último \n

        return new Result(true, "TOP_6_DIRECTORS_WITHIN_FAMILY", sb.toString());
    }

    public static Result topGenresByYear(String numStr, String yearStr) {
        int limite, ano;
        try {
            limite = Integer.parseInt(numStr);
            ano = Integer.parseInt(yearStr);
            if (yearStr.length() != 4 || limite < 1) {
                return new Result(false, "TOP_GENRES_BY_YEAR", "Erro: Comando invalido");
            }
        } catch (Exception e) {
            return new Result(false, "TOP_GENRES_BY_YEAR", "Erro: Comando invalido");
        }

        HashMap<Integer, Integer> contadorGeneros = new HashMap<>();

        for (Filme f : Main.filmeArrayList) {
            String[] partes = f.movieReleaseDate.split("-");
            if (partes.length != 3){
                continue;
            }

            int anoFilme;
            try {
                anoFilme = Integer.parseInt(partes[2]);
            } catch (Exception e) {
                continue;
            }

            if (anoFilme != ano) {
                continue;
            }

            for (int generoId : f.generosIds) {
                contadorGeneros.put(generoId, contadorGeneros.getOrDefault(generoId, 0) + 1);
            }
        }

        HashMap<Integer, String> nomesGeneros = new HashMap<>();
        for (Genero g : Main.generoArrayList) {
            nomesGeneros.put(g.getGenreId(), g.getGenreName());
        }

        ArrayList<String> resultado = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : contadorGeneros.entrySet()) {
            int generoId = entry.getKey();
            int count = entry.getValue();
            String nomeGenero = nomesGeneros.getOrDefault(generoId, "Unknown");
            resultado.add(nomeGenero + ":" + count);
        }

        // ordena por count desc
        resultado.sort((a, b) -> {
            int countA = Integer.parseInt(a.substring(a.lastIndexOf(":") + 1));
            int countB = Integer.parseInt(b.substring(b.lastIndexOf(":") + 1));
            if (countA != countB) {
                return Integer.compare(countB, countA); // maior primeiro
            }
            String nomeA = a.substring(0, a.indexOf(":")).toLowerCase();
            String nomeB = b.substring(0, b.indexOf(":")).toLowerCase();
            return nomeA.compareTo(nomeB); // alfabético se empate
        });

        // limita aomaximo solicitado
        StringBuilder sb = new StringBuilder();
        int max = Math.min(limite, resultado.size());
        for (int i = 0; i < max; i++) {
            sb.append(resultado.get(i)).append("\n");
        }

        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1); // remover último \n
        }

        return new Result(true, "TOP_GENRES_BY_YEAR", sb.toString());
    }
    //OBG
    public static Result insertActor(String args) {
        String[] partes = args.split(";");
        if (partes.length != 4) {
            return new Result(false, "INSERT_ACTOR", "Erro");
        }

        try {
            int id = Integer.parseInt(partes[0].trim());
            String nome = partes[1].trim();
            char genero = partes[2].trim().toUpperCase().charAt(0);
            int movieId = Integer.parseInt(partes[3].trim());

            // Validar género
            if (genero != 'M' && genero != 'F') {
                return new Result(false, "INSERT_ACTOR", "Erro");
            }

            // Validar se o ID já existe
            for (Ator a : Main.atorArrayList) {
                if (a.getActorId() == id) {
                    return new Result(false, "INSERT_ACTOR", "Erro");
                }
            }

            // Validar se o filme existe
            boolean filmeExiste = false;
            for (Filme f : Main.filmeArrayList) {
                if (f.movieId == movieId) {
                    filmeExiste = true;
                    break;
                }
            }

            if (!filmeExiste) {
                return new Result(false, "INSERT_ACTOR", "Erro");
            }

            // Inserir ator
            Ator novo = new Ator(id, nome, genero, movieId);
            Main.atorArrayList.add(novo);
            return new Result(true, "INSERT_ACTOR", "OK");

        } catch (Exception e) {
            return new Result(false, "INSERT_ACTOR", "Erro");
        }
    }
    //OBG
    public static Result insertDirector(String args) {
        String[] partes = args.split(";");
        if (partes.length != 3) {
            return new Result(false, "INSERT_DIRECTOR", "Erro");
        }

        try {
            int id = Integer.parseInt(partes[0].trim());
            String nome = partes[1].trim();
            int movieId = Integer.parseInt(partes[2].trim());

            // Verifica se o ID já existe
            for (Realizador r : Main.realizadorArrayList) {
                if (r.getDirectorId() == id) {
                    return new Result(false, "INSERT_DIRECTOR", "Erro");
                }
            }

            // Verifica se o filme existe
            boolean filmeExiste = false;
            for (Filme f : Main.filmeArrayList) {
                if (f.movieId == movieId) {
                    filmeExiste = true;
                    break;
                }
            }

            if (!filmeExiste) {
                return new Result(false, "INSERT_DIRECTOR", "Erro");
            }

            // Inserir o realizador
            Realizador novo = new Realizador(id, nome, movieId);
            Main.realizadorArrayList.add(novo);

            Main.realizadoresPorFilmeMap.putIfAbsent(movieId, new ArrayList<>());
            Main.realizadoresPorFilmeMap.get(movieId).add(novo);

            for (Filme f : Main.filmeArrayList) {
                if (f.movieId == movieId) {
                    f.adicionarRealizador(id);
                    break;
                }
            }

            return new Result(true, "INSERT_DIRECTOR", "OK");

        } catch (Exception e) {
            return new Result(false, "INSERT_DIRECTOR", "Erro");
        }
    }

    public static Result distanceBetweenActors(String args) {
        String[] nomes = args.split(",");
        if (nomes.length != 2) {
            return new Result(false, "DISTANCE_BETWEEN_ACTORS", "Erro: Comando invalido");
        }

        String nome1 = nomes[0].trim().toLowerCase();
        String nome2 = nomes[1].trim().toLowerCase();

        Set<Integer> idsNome1 = new HashSet<>();
        Set<Integer> idsNome2 = new HashSet<>();
        Map<Integer, Set<Integer>> atorParaFilmes = new HashMap<>();
        Map<Integer, Set<Integer>> filmeParaAtores = new HashMap<>();

        for (Ator a : Main.atorArrayList) {
            int actorId = a.getActorId();
            int movieId = a.getMovieId();
            String actorName = a.getActorName().trim().toLowerCase();

            atorParaFilmes.computeIfAbsent(actorId, _ -> new HashSet<>()).add(movieId);
            filmeParaAtores.computeIfAbsent(movieId, _ -> new HashSet<>()).add(actorId);

            if (actorName.equals(nome1)) {
                idsNome1.add(actorId);
            }
            if (actorName.equals(nome2)) {
                idsNome2.add(actorId);
            }
        }

        if (idsNome1.isEmpty() || idsNome2.isEmpty()) {
            return new Result(true, "DISTANCE_BETWEEN_ACTORS", "No results");
        }

        // Passo 1: verificar se atores têm filme em comum (distância 0)
        for (int id1 : idsNome1) {
            for (int filme : atorParaFilmes.getOrDefault(id1, Set.of())) {
                Set<Integer> atoresNoFilme = filmeParaAtores.get(filme);
                for (int id2 : idsNome2) {
                    if (atoresNoFilme.contains(id2)) {
                        return new Result(true, "DISTANCE_BETWEEN_ACTORS", "0");
                    }
                }
            }
        }

        // Passo 2: verificar distância 1 (ator intermediário comum)
        Set<Integer> atoresLigadosANome1 = new HashSet<>();
        for (int id1 : idsNome1) {
            for (int filme : atorParaFilmes.getOrDefault(id1, Set.of())) {
                atoresLigadosANome1.addAll(filmeParaAtores.getOrDefault(filme, Set.of()));
            }
        }

        for (int intermediario : atoresLigadosANome1) {
            Set<Integer> filmesDoIntermediario = atorParaFilmes.get(intermediario);
            for (int filme : filmesDoIntermediario) {
                Set<Integer> atoresDoFilme = filmeParaAtores.get(filme);
                for (int id2 : idsNome2) {
                    if (atoresDoFilme.contains(id2)) {
                        return new Result(true, "DISTANCE_BETWEEN_ACTORS", "1");
                    }
                }
            }
        }

        return new Result(true, "DISTANCE_BETWEEN_ACTORS", "No results");
    }


    public static Result help() {
        String comandos = """
        Comandos disponíveis:
        COUNT_MOVIES_MONTH_YEAR <month> <year>
        COUNT_MOVIES_DIRECTOR <full-name>
        COUNT_ACTORS_IN_2_YEARS <year-1> <year-2>
        COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS <year-start> <year-end> <min> <max>
        GET_MOVIES_ACTOR_YEAR <year> <full-name>
        GET_MOVIES_WITH_ACTOR_CONTAINING <name>
        GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING <search-string>
        GET_ACTORS_BY_DIRECTOR <num> <full-name>
        TOP_MONTH_MOVIE_COUNT <year>
        TOP_VOTED_ACTORS <num> <year>
        TOP_MOVIES_WITH_MORE_GENDER <num> <year> <gender>
        TOP_MOVIES_WITH_GENDER_BIAS <num> <year>
        TOP_6_DIRECTORS_WITHIN_FAMILY <year-start> <year-end>
        TOP_GENRES_BY_YEAR <num> <year>
        INSERT_ACTOR <id>;<name>;<gender>;<movie-id>
        INSERT_DIRECTOR <id>;<name>;<movie-id>
        DISTANCE_BETWEEN_ACTORS <actor-1>,<actor-2>
        HELP
        QUIT
        """;
        return new Result(true, null, comandos);
    }

    public static Result quit() {
        return new Result(true, null, "Programa terminado.");
    }
}
