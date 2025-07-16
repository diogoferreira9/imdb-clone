# 🎬 IMDB

Welcome to the IMDB project! This application is a movie data analysis tool designed to process and analyze information about films, actors, directors, genres, and votes. It loads structured data from CSV files and provides a wide range of interactive commands for querying and manipulating this data. It's ideal for anyone interested in learning how to work with datasets, implement search features, or simulate an IMDb-like platform in Java.

---

## ✨ Features

- **CSV Data Integration**: Parses movie, actor, director, genre, and vote data from CSV files.
- **Interactive CLI Commands**: Execute intuitive text-based commands to explore and analyze data.
- **Smart Input Validation**: Detects and reports malformed entries in the input files.
- **Entity Linking**: Automatically links actors, directors, and genres to their respective movies.
- **Custom Outputs**: Each command returns clean, well-formatted results based on filters or calculations.
- **Extensible Design**: Modular code structure with support for adding new commands easily.

---

## 💡 Commands

Once the application is running, you can execute the following commands:

- `COUNT_MOVIES_MONTH_YEAR <month> <year>`  
- `COUNT_MOVIES_DIRECTOR <min-votes> <full-name>`  
- `COUNT_ACTORS_IN_2_YEARS <year-1> <year-2>`  
- `COUNT_MOVIES_BETWEEN_YEARS_WITH_N_ACTORS <start-year> <end-year> <min> <max>`  
- `GET_MOVIES_ACTOR_YEAR <year> <full-name>`  
- `GET_MOVIES_WITH_ACTOR_CONTAINING <name>`  
- `GET_TOP_4_YEARS_WITH_MOVIES_CONTAINING <search-string>`  
- `GET_ACTORS_BY_DIRECTOR <min> <full-name>`  
- `TOP_MONTH_MOVIE_COUNT <year>`  
- `TOP_VOTED_ACTORS <num> <year>`  
- `TOP_MOVIES_WITH_MORE_GENDER <num> <year> <gender>`  
- `TOP_MOVIES_WITH_GENDER_BIAS <num> <year>`  
- `TOP_6_DIRECTORS_WITHIN_FAMILY <start-year> <end-year>`  
- `TOP_GENRES_BY_YEAR <num> <year>`  
- `INSERT_ACTOR <id>;<name>;<gender>;<movie-id>`  
- `INSERT_DIRECTOR <id>;<name>;<movie-id>`  
- `DISTANCE_BETWEEN_ACTORS <actor-1>,<actor-2>`  
- `HELP` — Display all commands  
- `QUIT` — Exit the application  

🛑 Special rules:
- Cannot insert actors into movies with 3 or more existing actors (`Filme fechado`).
- Commands reject invalid years, names, or values and return friendly error messages.

---

## 🛠 Technical Details

- **Programming Language**: Java  
- **Project Type**: Console-based CLI application  
- **Data Structures Used**: `ArrayList`, `HashMap`, `HashSet`  
- **Data Files Expected**:
  - `movies.csv`
  - `actors.csv`
  - `directors.csv`
  - `genres.csv`
  - `genres_movies.csv`
  - `movie_votes.csv`
- **Parsing Strategy**: All files are parsed once at the start using `Scanner`, and errors are logged in `InputInvalido`.

---

## 📁 Example Usage

```text
> COUNT_MOVIES_DIRECTOR 200 Christopher Nolan
5

> GET_MOVIES_ACTOR_YEAR 2019 Scarlett Johansson
Avengers: Endgame
Marriage Story

> TOP_GENRES_BY_YEAR 3 2020
Action:15
Drama:12
Comedy:9

> INSERT_ACTOR 9999;John Smith;M;120
Filme fechado
