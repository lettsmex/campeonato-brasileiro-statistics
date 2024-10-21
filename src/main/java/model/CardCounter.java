package model;

import java.util.List;
import java.util.Map;

public interface CardCounter {
    Map<String, Integer> totalCards (List<String[]> dados, String cardColor);
}
