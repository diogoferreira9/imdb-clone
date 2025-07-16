package TestsJUnit;
import org.junit.jupiter.api.Test;
import Main.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestTopGenresByYear {
    @Test
    public void testTopGenresValidInput() {
        Result r = Queries.topGenresByYear("2", "2020");
        assertEquals("TOP_GENRES_BY_YEAR", r.error);
        assertTrue(r.success);
        assertEquals("", r.result.replace("\\r", ""));
    }

    @Test
    public void testTopGenresInvalidYear() {
        Result r = Queries.topGenresByYear("3", "20");
        assertFalse(r.success);
    }

    @Test
    public void testTopGenresInvalidLimit() {
        Result r = Queries.topGenresByYear("0", "2020");
        assertFalse(r.success);
    }

    @Test
    public void testTopGenresYearWithNoMovies() {
        Result r = Queries.topGenresByYear("5", "2019");
        assertEquals("", r.result);
    }

    @Test
    public void testTopGenresLimitExceedsAvailable() {
        Result r = Queries.topGenresByYear("10", "2020");
        assertTrue(r.success);
        assertEquals("", r.result.replace("\\r", ""));
    }
}
