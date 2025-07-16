# 🎬 IMDB Clone

IMDB Clone is a Java-based command-line application that parses, indexes, and queries movie data from multiple CSV files. It simulates a lightweight IMDb-like system with functionality for analyzing actors, directors, genres, and films using custom commands.

## 📁 Project Structure

The project follows a modular design with clear separation between data parsing, object modeling, and query execution.

### Main Components

| Class         | Description |
|---------------|-------------|
| `Main`        | Entry point, file parser, command interpreter. |
| `Queries`     | Contains logic for all supported user commands. |
| `Filme`       | Represents a movie and its associated data. |
| `Ator`        | Represents an actor linked to a movie. |
| `Realizador`  | Represents a director and their movies. |
| `Genero`      | Represents a genre. |
| `InputInvalido` | Tracks invalid rows during file parsing. |
| `Result`      | Wraps command results with success/error state. |
| `TipoEntidade`| Enum for data categories (FILME, ATOR, etc.). |

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 11 or higher
- IDE or terminal access for running Java programs

### 📂 Data Files

Place your CSV files inside a folder named `test-files/TestMain`, with the following expected filenames:

- `movies.csv`
- `actors.csv`
- `directors.csv`
- `genres.csv`
- `genres_movies.csv`
- `movie_votes.csv`

Each file is parsed once at startup.

---

## 🧠 Supported Commands

Below are the commands you can run after startup:

COUNT_MOVIES_MONTH_YEAR <month> <year>
COUNT_MOVIES_DIRECTOR <min-votes> <full-name>
COUNT_ACTORS_IN_2_YEARS <year-1> <year-2>
COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS <start-year> <end-year> <min> <max>
GET_MOVIES_ACTOR_YEAR <year> <full-name>
GET_MOVIES_WITH_ACTOR_CONTAINING <name>
GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING <search-string>
GET_ACTORS_BY_DIRECTOR <min> <full-name>
TOP_MONTH_MOVIE_COUNT <year>
TOP_VOTED_ACTORS <num> <year>
TOP_MOVIES_WITH_MORE_GENDER <num> <year> <gender>
TOP_MOVIES_WITH_GENDER_BIAS <num> <year>
TOP_6_DIRECTORS_WITHIN_FAMILY <start-year> <end-year>
TOP_GENRES_BY_YEAR <num> <year>
INSERT_ACTOR <id>;<name>;<gender>;<movie-id>
INSERT_DIRECTOR <id>;<name>;<movie-id>
DISTANCE_BETWEEN_ACTORS <actor-1>,<actor-2>
HELP
QUIT

---

## 🛠 Features

- ✅ CSV parsing with error tracking
- ✅ Commands for counting, filtering, and comparing entities
- ✅ In-memory relational mapping (actors to movies, genres to films, etc.)
- ✅ Smart command parser supporting multi-word names
- ✅ Input validation and custom error messages
- ✅ Special formatting for movies with ID between 100 and 200


