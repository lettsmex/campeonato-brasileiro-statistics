package service;

import model.CardCounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardCounterImpl implements CardCounter {

    @Override
    public Map<String, Integer> totalCards(List<String[]> dados, String cardColor) {
        return dados.stream()
                .skip(1)
                .filter(linha -> linha[3].equalsIgnoreCase(cardColor))
                .collect(Collectors.toMap(
                        linha -> linha[4],
                        linha -> 1,
                        Integer::sum
                ));
    }

}
