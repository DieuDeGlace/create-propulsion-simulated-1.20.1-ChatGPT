package dev.propulsionteam.propulsionsimulated.registries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CreativeTabLayoutTest {
    @Test
    void padsSectionRowsWithoutPuttingPaddingInTheSourceContents() {
        List<Integer> items = new ArrayList<>();
        for (int item = 1; item <= 11; item++) {
            items.add(item);
        }

        assertTrue(CreativeTabLayout.padSections(items, List.of(2, 9), 9, 0));
        assertEquals(36, items.size());
        assertEquals(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2), items.subList(0, 11));
        assertEquals(List.of(3, 4, 5, 6, 7, 8, 9, 10, 11), items.subList(27, 36));
    }

    @Test
    void repeatedPaddingIsIdempotent() {
        List<Integer> items = new ArrayList<>(List.of(1, 2, 3));
        assertTrue(CreativeTabLayout.padSections(items, List.of(3), 9, 0));
        List<Integer> firstResult = List.copyOf(items);

        assertFalse(CreativeTabLayout.padSections(items, List.of(3), 9, 0));
        assertEquals(firstResult, items);
    }

    @Test
    void leavesUnexpectedMenuContentsUntouched() {
        List<Integer> items = new ArrayList<>(List.of(1, 2));

        assertFalse(CreativeTabLayout.padSections(items, List.of(3), 9, 0));
        assertEquals(List.of(1, 2), items);
    }
}
