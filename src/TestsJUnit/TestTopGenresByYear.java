package TestsJUnit;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTopGenresByYear {
    @Test
    public void testTopGenresValidInput() {
        Result r = Queries.topGenresByYear("2", "2020");
        assertEquals("TOP_GENRES_BY_YEAR", r.error);
        assertEquals(true, r.success);
        assertEquals("", r.result.replace("\\r", ""));
    }

    @Test
    public void testTopGenresInvalidYear() {
        Result r = Queries.topGenresByYear("3", "20");
        assertEquals(false, r.success);
    }

    @Test
    public void testTopGenresInvalidLimit() {
        Result r = Queries.topGenresByYear("0", "2020");
        assertEquals(false, r.success);
    }

    @Test
    public void testTopGenresYearWithNoMovies() {
        Result r = Queries.topGenresByYear("5", "2019");
        assertEquals("", r.result);
    }

    @Test
    public void testTopGenresLimitExceedsAvailable() {
        Result r = Queries.topGenresByYear("10", "2020");
        assertEquals(true, r.success);
        assertEquals("", r.result.replace("\\r", ""));
    }
}
