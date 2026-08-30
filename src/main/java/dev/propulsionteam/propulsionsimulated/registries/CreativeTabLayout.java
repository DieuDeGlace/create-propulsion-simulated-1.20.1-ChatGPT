package dev.propulsionteam.propulsionsimulated.registries;

import java.util.ArrayList;
import java.util.List;

final class CreativeTabLayout {
    private CreativeTabLayout() {
    }

    static <T> boolean padSections(List<T> items, List<Integer> sectionItemCounts, int itemsPerRow, T emptySlot) {
        if (sectionItemCounts.isEmpty() || itemsPerRow <= 0) {
            return false;
        }

        int expectedItemCount = sectionItemCounts.stream().mapToInt(Integer::intValue).sum();
        if (items.size() != expectedItemCount) {
            return false;
        }

        List<T> padded = new ArrayList<>();
        addPadding(padded, itemsPerRow, emptySlot);

        int itemIndex = 0;
        for (int sectionIndex = 0; sectionIndex < sectionItemCounts.size(); sectionIndex++) {
            int sectionItemCount = sectionItemCounts.get(sectionIndex);
            int nextItemIndex = itemIndex + sectionItemCount;
            padded.addAll(items.subList(itemIndex, nextItemIndex));
            itemIndex = nextItemIndex;

            if (sectionIndex < sectionItemCounts.size() - 1) {
                int slotsToFinishRow = (itemsPerRow - sectionItemCount % itemsPerRow) % itemsPerRow;
                addPadding(padded, slotsToFinishRow + itemsPerRow, emptySlot);
            }
        }

        items.clear();
        items.addAll(padded);
        return true;
    }

    private static <T> void addPadding(List<T> items, int count, T emptySlot) {
        for (int i = 0; i < count; i++) {
            items.add(emptySlot);
        }
    }
}
