package Main;

import java.io.*;
import java.util.*;

public class Main {

    public static ArrayList<Ator> atorArrayList = new ArrayList<>();
    public static ArrayList<Filme> filmeArrayList = new ArrayList<>();
    public static ArrayList<Genero> generoArrayList = new ArrayList<>();
    public static ArrayList<Realizador> realizadorArrayList = new ArrayList<>();
    public static ArrayList<InputInvalido> inputsInvalidosArrayList = new ArrayList<>();
    public static Map<Integer, ArrayList<Realizador>> realizadoresPorFilmeMap = new HashMap<>();


    public static boolean parseFiles(File folder) {
        atorArrayList.clear();
        filmeArrayList.clear();
        generoArrayList.clear();
        realizadorArrayList.clear();
        inputsInvalidosArrayList.clear();
        realizadoresPorFilmeMap.clear();

        // Maps para lookup rápido
        Map<Integer, Filme> filmeMap = new HashMap<>();
        Map<Integer, Genero> generoMap = new HashMap<>();

        File ficheiroAtores = new File(folder, "actors.csv");
        File ficheiroRealizadores = new File(folder, "directors.csv");
        File ficheiroGenerosMovies = new File(folder, "genres_movies.csv");
        File ficheiroGeneros = new File(folder, "genres.csv");
        File ficheiroFilmes = new File(folder, "movies.csv");
        File ficheiroVotosFilmes = new File(folder, "movie_votes.csv");

        try {
            // --- FILMES ---
            if (ficheiroFilmes.exists()) {
                int linhasValidas = 0, linhasInvalidas = 0, primeiraLinhaInvalida = -1, nrLinha = 1;
                Scanner leitor = new Scanner(ficheiroFilmes);
                leitor.nextLine(); // ignora cabeçalho
                while (leitor.hasNextLine()) {
                    String linha = leitor.nextLine();
                    nrLinha++;
                    String[] dados = linha.split(",");
                    if (dados.length == 5) {
                        try {
                            int filmeId = Integer.parseInt(dados[0].trim());
                            String nomeFilme = dados[1].trim();
                            double duracao = Double.parseDouble(dados[2].trim());
                            int orcamento = dados[3].trim().isEmpty() ? 0 : (int) Double.parseDouble(dados[3].trim());
                            String dataLancamento = dados[4].trim().replace("/", "-");

                            // Só adiciona se ainda não existir
                            if (!filmeMap.containsKey(filmeId)) {
                                Filme filme = new Filme(filmeId, nomeFilme, duracao, orcamento, dataLancamento);
                                filmeArrayList.add(filme);
                                filmeMap.put(filmeId, filme);
                            }

                            linhasValidas++;
                        } catch (Exception e) {
                            linhasInvalidas++;
                            if (primeiraLinhaInvalida == -1) {
                                primeiraLinhaInvalida = nrLinha - 1;
                            }
                        }
                    } else {
                        linhasInvalidas++;
                        if (primeiraLinhaInvalida == -1) {
                            primeiraLinhaInvalida = nrLinha - 1;
                        }
                    }
                }
                inputsInvalidosArrayList.add(new InputInvalido(ficheiroFilmes.getName(), linhasValidas, linhasInvalidas, primeiraLinhaInvalida));
            }

            // --- ATORES ---
            if (ficheiroAtores.exists()) {
                int linhasValidas = 0, linhasInvalidas = 0, primeiraLinhaInvalida = -1, nrLinha = 1;
                Scanner leitor = new Scanner(ficheiroAtores);
                leitor.nextLine();
                while (leitor.hasNextLine()) {
                    String[] dados = leitor.nextLine().split(",");
                    if (dados.length == 4) {
                        try {
                            atorArrayList.add(new Ator(
                                    Integer.parseInt(dados[0].trim()),
                                    dados[1].trim(),
                                    dados[2].trim().charAt(0),
                                    Integer.parseInt(dados[3].trim())
                            ));
                            linhasValidas++;
                        } catch (Exception e) {
                            linhasInvalidas++;
                            if (primeiraLinhaInvalida == -1) {
                                primeiraLinhaInvalida = nrLinha;
                            }
                        }
                    } else {
                        linhasInvalidas++;
                        if (primeiraLinhaInvalida == -1) {
                            primeiraLinhaInvalida = nrLinha;
                        }
                    }
                    nrLinha++;
                }
                inputsInvalidosArrayList.add(new InputInvalido(ficheiroAtores.getName(), linhasValidas, linhasInvalidas, primeiraLinhaInvalida));
            }

            // --- REALIZADORES ---
            if (ficheiroRealizadores.exists()) {
                int linhasValidas = 0, linhasInvalidas = 0, primeiraLinhaInvalida = -1, nrLinha = 1;
                Scanner leitor = new Scanner(ficheiroRealizadores);
                leitor.nextLine();

                while (leitor.hasNextLine()) {
                    String[] dados = leitor.nextLine().split(",");
                    if (dados.length == 3) {
                        try {
                            int directorId = Integer.parseInt(dados[0].trim());
                            String directorName = dados[1].trim();
                            int movieId = Integer.parseInt(dados[2].trim());

                            Realizador realizador = new Realizador(directorId, directorName, movieId);
                            realizadorArrayList.add(realizador);

                            // Preencher o map realizadoresPorFilmeMap
                            Main.realizadoresPorFilmeMap.putIfAbsent(movieId, new ArrayList<>());
                            Main.realizadoresPorFilmeMap.get(movieId).add(realizador);

                            linhasValidas++;
                        } catch (Exception e) {
                            linhasInvalidas++;
                            if (primeiraLinhaInvalida == -1) {
                                primeiraLinhaInvalida = nrLinha;
                            }
                        }
                    } else {
                        linhasInvalidas++;
                        if (primeiraLinhaInvalida == -1) {
                            primeiraLinhaInvalida = nrLinha;
                        }
                    }
                    nrLinha++;
                }

                inputsInvalidosArrayList.add(new InputInvalido(ficheiroRealizadores.getName(), linhasValidas, linhasInvalidas, primeiraLinhaInvalida));

                for (Map.Entry<Integer, ArrayList<Realizador>> entry : realizadoresPorFilmeMap.entrySet()) {
                    int movieId = entry.getKey();
                    Filme filme = filmeMap.get(movieId);
                    if (filme != null) {
                        for (Realizador realizador : entry.getValue()) {
                            filme.adicionarRealizador(realizador.getDirectorId());
                        }
                    }
                }
            }

            // --- GENEROS ---
            if (ficheiroGeneros.exists()) {
                int linhasValidas = 0, linhasInvalidas = 0, primeiraLinhaInvalida = -1, nrLinha = 1;
                Scanner leitor = new Scanner(ficheiroGeneros);
                leitor.nextLine();
                while (leitor.hasNextLine()) {
                    String linha = leitor.nextLine();
                    nrLinha++;
                    String[] dados = linha.split(",");
                    if (dados.length == 2) {
                        try {
                            int generoId = Integer.parseInt(dados[0].trim());
                            String nomeGenero = dados[1].trim();
                            Genero genero = new Genero(generoId, nomeGenero);
                            generoArrayList.add(genero);
                            generoMap.put(generoId, genero);
                            linhasValidas++;
                        } catch (Exception e) {
                            linhasInvalidas++;
                            if (primeiraLinhaInvalida == -1) {
                                primeiraLinhaInvalida = nrLinha - 1;
                            }
                        }
                    } else {
                        linhasInvalidas++;
                        if (primeiraLinhaInvalida == -1) {
                            primeiraLinhaInvalida = nrLinha - 1;
                        }
                    }
                }
                inputsInvalidosArrayList.add(new InputInvalido(ficheiroGeneros.getName(), linhasValidas, linhasInvalidas, primeiraLinhaInvalida));
            }

            // --- GENEROS_FILMES ---
            if (ficheiroGenerosMovies.exists()) {
                int linhasValidas = 0, linhasInvalidas = 0, primeiraLinhaInvalida = -1, nrLinha = 1;
                Scanner leitor = new Scanner(ficheiroGenerosMovies);
                leitor.nextLine();
                while (leitor.hasNextLine()) {
                    String[] dados = leitor.nextLine().split(",");
                    if (dados.length == 2) {
                        try {
                            int genreId = Integer.parseInt(dados[0].trim());
                            int movieId = Integer.parseInt(dados[1].trim());
                            Filme filme = filmeMap.get(movieId);
                            if (filme != null) {
                                filme.adicionarGenero(genreId);
                            }
                            linhasValidas++;
                        } catch (Exception e) {
                            linhasInvalidas++;
                            if (primeiraLinhaInvalida == -1) {
                                primeiraLinhaInvalida = nrLinha;
                            }
                        }
                    } else {
                        linhasInvalidas++;
                        if (primeiraLinhaInvalida == -1) {
                            primeiraLinhaInvalida = nrLinha;
                        }
                    }
                    nrLinha++;
                }
                inputsInvalidosArrayList.add(new InputInvalido(ficheiroGenerosMovies.getName(), linhasValidas, linhasInvalidas, primeiraLinhaInvalida));
            }

            // --- MOVIE_VOTES ---
            if (ficheiroVotosFilmes.exists()) {
                int linhasValidas = 0, linhasInvalidas = 0, primeiraLinhaInvalida = -1, nrLinha = 1;
                Scanner leitor = new Scanner(ficheiroVotosFilmes);
                leitor.nextLine();

                while (leitor.hasNextLine()) {
                    String[] dados = leitor.nextLine().split(",");
                    if (dados.length == 3) {
                        try {
                            int movieId = Integer.parseInt(dados[0].trim());
                            double rating = Double.parseDouble(dados[1].trim());

                            Filme filme = filmeMap.get(movieId);
                            if (filme != null) {
                                filme.setRating(rating);
                            }

                            linhasValidas++;
                        } catch (Exception e) {
                            linhasInvalidas++;
                            if (primeiraLinhaInvalida == -1) {
                                primeiraLinhaInvalida = nrLinha;
                            }
                        }
                    } else {
                        linhasInvalidas++;
                        if (primeiraLinhaInvalida == -1) {
                            primeiraLinhaInvalida = nrLinha;
                        }
                    }
                    nrLinha++;
                }

                inputsInvalidosArrayList.add(new InputInvalido(ficheiroVotosFilmes.getName(), linhasValidas, linhasInvalidas, primeiraLinhaInvalida));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList getObjects(TipoEntidade tipo) {
        return switch (tipo) {
            case ATOR -> atorArrayList;
            case REALIZADOR -> realizadorArrayList;
            case GENERO_CINEMATOGRAFICO -> generoArrayList;
            case FILME -> filmeArrayList;
            case INPUT_INVALIDO -> inputsInvalidosArrayList;
        };
    }

    public static String[] normalizaComando(String command) {
        String[] parts = command.trim().split(" ");
        if (parts.length == 1) {
            return parts;
        }

        // Comandos que podem ter nomes completos (com espaços)
        switch (parts[0]) {
            // Nomes de pessoas/atores/realizadores podem ser compostos!
            case "COUNT_MOVIES_DIRECTOR":
                // nome pode ter espaços → tudo depois do primeiro espaço
                parts[1] = command.substring(command.indexOf(" ") + 1).trim();
                return new String[] {parts[0], parts[1]};

            case "TOP_GENRES_BY_YEAR":
                if (parts.length >= 3) {
                    return new String[] {parts[0], parts[1], parts[2]};
                }
                break;

            case "TOP_MOVIES_WITH_GENDER_BIAS":
                if (parts.length >= 3) {
                    return new String[] {parts[0], parts[1], parts[2]};
                } else {
                    return new String[] {parts[0], "", ""}; // comando mal formado
                }

            case "GET_ACTORS_BY_DIRECTOR":
                if (parts.length >= 3) {
                    String num = parts[1];
                    String nome = command.substring(command.indexOf(num) + num.length()).trim();
                    return new String[] {parts[0], num, nome};
                }
                break;
            case "GET_MOVIES_ACTOR_YEAR":
                if (parts.length >= 3) {
                    String ano = parts[1];
                    // O nome é tudo depois do ano
                    int posAno = command.indexOf(ano);
                    String nome = command.substring(posAno + ano.length()).trim();
                    return new String[] {parts[0], ano, nome};
                }
                break;

            case "GET_MOVIES_WITH_ACTOR_CONTAINING":
                parts[1] = command.substring(command.indexOf(" ") + 1).trim();
                return new String[] {parts[0], parts[1]};
            case "GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING":
                parts[1] = command.substring(command.indexOf(" ") + 1).trim();
                return new String[] {parts[0], parts[1]};
            case "DISTANCE_BETWEEN_ACTORS":
                parts[1] = command.substring(command.indexOf(" ") + 1).trim();
                return new String[] {parts[0], parts[1]};
            case "INSERT_ACTOR":
                parts[1] = command.substring(command.indexOf(" ") + 1).trim();
                return new String[] {parts[0], parts[1]};
            case "INSERT_DIRECTOR":
                parts[1] = command.substring(command.indexOf(" ") + 1).trim();
                return new String[] {parts[0], parts[1]};
            default:

                return parts;
        }
        return parts;
    }

    public static Result execute(String command) {
        String[] parts = normalizaComando(command);

        Result noCommandMatch = new Result(false, "Comando invalido", null);

        return switch (parts[0]) {
            case "COUNT_MOVIES_MONTH_YEAR" -> Queries.countMoviesMonthYear(parts[1], parts[2]);
            case "COUNT_MOVIES_DIRECTOR" -> {
                if (parts.length < 2) {
                    yield new Result(true, "COUNT_MOVIES_DIRECTOR", "0");
                }
                yield Queries.countMoviesDirector(parts[1]);
            }
            case "COUNT_ACTORS_IN_2_YEARS" -> Queries.countActorsIn2Years(parts[1], parts[2]);
            case "COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS" -> Queries.countMoviesBetweenYearsWithNActors(parts[1], parts[2], parts[3], parts[4]);
            case "GET_MOVIES_ACTOR_YEAR" -> Queries.getMoviesActorYear(parts[1], parts[2]);
            case "GET_MOVIES_WITH_ACTOR_CONTAINING" -> Queries.getMoviesWithActorContaining(parts[1]);
            case "GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING" -> Queries.getTop4YearsWithMoviesContaining(parts[1]);
            case "GET_ACTORS_BY_DIRECTOR" -> Queries.getActorsByDirector(parts[1], parts[2]);
            case "TOP_MONTH_MOVIE_COUNT" -> Queries.topMonthMovieCount(parts[1]);
            case "TOP_VOTED_ACTORS" -> Queries.topVotedActors(parts[1], parts[2]);
            case "TOP_MOVIES_WITH_MORE_GENDER" -> Queries.topMoviesWithMoreGender(parts[1], parts[2], parts[3]);
            case "TOP_MOVIES_WITH_GENDER_BIAS" -> Queries.topMoviesWithGenderBias(parts[1], parts[2]);
            case "TOP_6_DIRECTORS_WITHIN_FAMILY" -> Queries.top6DirectorsWithinFamily(parts[1], parts[2]);
            case "TOP_GENRES_BY_YEAR" -> Queries.topGenresByYear(parts[1], parts[2]);
            case "INSERT_ACTOR" -> Queries.insertActor(parts[1]);
            case "INSERT_DIRECTOR" -> Queries.insertDirector(parts[1]);
            case "DISTANCE_BETWEEN_ACTORS" -> Queries.distanceBetweenActors(parts[1]);
            case "HELP" -> Queries.help();
            case "QUIT" -> Queries.quit();
            default -> noCommandMatch;
        };
    }



    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to IMDB Clone");

        long start = System.currentTimeMillis();
        boolean parseOk = parseFiles(new File("test-files/TestMain"));
        if (!parseOk) {
            System.out.println("Error loading files");
            return;
        }
        long end = System.currentTimeMillis();

        System.out.println("Ficheiros carregados em " + (end - start) + " ms");

        Result result = execute("HELP");
        System.out.println(result.result);

        Scanner in = new Scanner(System.in);
        String line;
        do {
            System.out.print("> ");
            line = in.nextLine(); // Read input at the start of the loop

            if (line != null && !line.equals("QUIT")) {
                start = System.currentTimeMillis();
                result = execute(line);
                end = System.currentTimeMillis();

                if (!result.success) {
                    System.out.println("Erro: " + result.error);
                } else {
                    System.out.println(result.result);
                    System.out.println("(demorou " + (end - start) + " ms)");
                }
            }
        } while (line != null && !line.equals("QUIT"));
    }


}