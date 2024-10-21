package service;

import util.DateValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BrasileiraoAnalyzer {
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String COLOR_RESET = "\u001B[0m";
    public void analyze() {
        ReaderImpl reader = new ReaderImpl();
        CardCounterImpl cardCounter = new CardCounterImpl();

        String cartoes = "campeonato-brasileiro-cartoes.csv";
        String goals = "campeonato-brasileiro-gols.csv";
        String estatisticasFull = "campeonato-brasileiro-estatisticas-full.csv";
        String matchResults = "campeonato-brasileiro-full.csv";
        List<String[]> dataCards = reader.readCsvFile(cartoes);
        List<String[]> dataGoals = reader.readCsvFile(goals);
        List<String[]> dataMatchResults = reader.readCsvFile(matchResults);
        List<String> topTeam2008 = getTeamsWithMostWinsIn2008(dataMatchResults);
        String cardColor1 = "Amarelo";
        String cardColor2 = "Vermelho";


        System.out.println(BLUE + "DADOS:" + COLOR_RESET);


        if (topTeam2008.size() > 1){
            System.out.println(GREEN + "\nTimes com mais vitórias em 2008 são: \n" + COLOR_RESET + String.join(", ", topTeam2008));
        }else{
            System.out.println(GREEN + "\nTime com mais vitórias em 2008 é: \n" + COLOR_RESET + String.join(", ", topTeam2008));
        }

        List <String> stateWithLeastMatches = getStateWithLeastMatches(dataMatchResults);
        System.out.println(GREEN + "\nEstado que teve menos jogos dentro do período de 2003 a 2022: \n" + COLOR_RESET +
                String.join(", ", stateWithLeastMatches));



        Map<String, Integer> contadorGols = totalGoals(dataGoals);
        printTopScorer(contadorGols, "\nJogador(es) com mais gols feitos nesse período:");
        analyzeGoalByType(dataGoals, "Penalty", "\nJogador(es) com mais gols de pênalti feitos nesse período:");
        analyzeGoalByType(dataGoals, "Gol Contra", "\nJogador(es) com mais gols contra feitos nesse período:");

        analyzeCards(cardCounter, dataCards, cardColor1);
        analyzeCards(cardCounter, dataCards, cardColor2);
        moreGoalsMatch(dataMatchResults);

    }

    private Map<String, Integer> totalGoals(List<String[]> dados) {
        Map<String, Integer> goalCounter = new HashMap<>();
        String player;

        for (int i = 1; i < dados.size(); i++) {
            String[] linha = dados.get(i);
            player = linha[3];
            if (goalCounter.containsKey(player)) {
                goalCounter.put(player, goalCounter.get(player) + 1);
            } else {
                goalCounter.put(player, 1);
            }
        }
        return goalCounter;
    }

    private Map<String, Integer> totalGoalsByType(List<String[]> dados, String goalType) {
        Map<String, Integer> typeCounter = new HashMap<>();
        String player;
        for (int i = 1; i < dados.size(); i++) {
            String[] linha = dados.get(i);
            player = linha[3];
            if (linha[5] != null && !linha[5].isBlank() && linha[5].equalsIgnoreCase(goalType)) {
                typeCounter.put(player, typeCounter.getOrDefault(player, 0) + 1);
            }
        }
        return typeCounter;
    }

    private void printTopScorer(Map<String, Integer> map, String message) {
        if (map.isEmpty()) {
            System.err.println("Nenhum dado encontrado.");
            return;
        }
        int maxScore = map.values().stream()
                .max(Integer::compare)
                .orElse(0);

        List<Map.Entry<String, Integer>> topScorers = map.entrySet().stream()
                .filter(entry -> entry.getValue() == maxScore)
                .toList();

        System.out.println(GREEN + message + COLOR_RESET);

        if (topScorers.size() == 1) {
            topScorers.stream().findFirst().ifPresent(entry ->
                    System.out.println(entry.getKey() + " com " + entry.getValue() + ".")
            );
        } else {
            topScorers.forEach(entry ->
                    System.out.println(entry.getKey() + " com " + entry.getValue() + ".")
            );
        }
    }


    private void analyzeCards(CardCounterImpl cardCounter, List<String[]> dataCards, String cardColor) {
        Map<String, Integer> cardCount = cardCounter.totalCards(dataCards, cardColor);
        printTopScorer(cardCount, GREEN + "\nJogador(es) com mais cartões " + cardColor.toLowerCase()+"s"+ " recebidos nesse período:" + COLOR_RESET);
        orderList(cardCount);
    }

    private void orderList(Map<String, Integer> map) {

        if (map.isEmpty()) {
            System.err.println("Está vazio.");
            return;
        }
        List<Map.Entry<String, Integer>> orderEntry = new ArrayList<>(map.entrySet());
        orderEntry.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

    }

    private void analyzeGoalByType(List<String[]> dataGoals, String goalType, String message) {
        Map<String, Integer> goalCountByType = totalGoalsByType(dataGoals, goalType);
        printTopScorer(goalCountByType, GREEN + message + COLOR_RESET);
        orderList(goalCountByType);
    }

    private void moreGoalsMatch(List <String[]> matchResults) {

        String matchWithMoreGoals = "";
        int maiorNumeroDeGols = 0;
        int golsMandante = 0;
        int golsVisitante = 0;

        for(int i = 1; i < matchResults.size(); i++) {
            String[] linha = matchResults.get(i);
            String partidaId = linha[0];
            int golsMandanteAtual = Integer.parseInt(linha[12]);
            int golsVisitanteAtual = Integer.parseInt(linha[13]);

            int totalGolsPartidaAtual = golsMandanteAtual + golsVisitanteAtual;

            if (totalGolsPartidaAtual > maiorNumeroDeGols) {
                maiorNumeroDeGols = totalGolsPartidaAtual;
                matchWithMoreGoals = partidaId;
                golsMandante = golsMandanteAtual;
                golsVisitante = golsVisitanteAtual;
            }
        }
            System.out.println(GREEN + "\nPlacar da partida com mais gols:\n" + COLOR_RESET + "Anfitrião "  + golsMandante + " x " + golsVisitante + " Visitante" + " (ID da partida: " + matchWithMoreGoals + ")");

    }

    private List<String> getTeamWithMostWins(List<String[]> dataMatchResults) {
        Map<String, Integer> teamWins = new HashMap<>();

        for (int i = 1; i < dataMatchResults.size(); i++) {
            String[] match = dataMatchResults.get(i);
            String winner = match[10];

            if (winner != null && !winner.isBlank() && !winner.equals("-")) {
                teamWins.put(winner, teamWins.getOrDefault(winner, 0) + 1);
            }
        }
        int maxWins = teamWins.values().stream().max(Integer::compare).orElse(0);

        return teamWins.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maxWins)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }

    private List<String> getTeamsWithMostWinsIn2008(List<String[]> dataMatchResults) {
        Map<String, Integer> teamWins = new HashMap<>();

        for (int i = 1; i < dataMatchResults.size(); i++) {
            String[] match = dataMatchResults.get(i);
            String winner = match[10].trim();
            String date = match[2].trim();

            if (winner != null && !winner.isBlank() && !winner.equals("-") && DateValidator.year2008(date)) {
                teamWins.put(winner, teamWins.getOrDefault(winner, 0) + 1);
            }
        }

        if (teamWins.isEmpty()) {
            return List.of("Nenhum time encontrado");
        }

        int maxWins = teamWins.values().stream().max(Integer::compare).orElse(0);

        return teamWins.entrySet().stream()
                .filter(entry -> entry.getValue() == maxWins)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }

    private List<String> getStateWithLeastMatches(List<String[]> matchResults) {
        Map<String, Integer> stateMatchCount = new HashMap<>();

        for (int i = 1; i < matchResults.size(); i++) {
            String[] match = matchResults.get(i);
            String homeState = match[14].trim();
            String awayState = match[15].trim();
            String matchDate = match[2].trim();

            if (DateValidator.Date2003to2022(matchDate, 2003, 2022)) {
                if (!homeState.isBlank() && !homeState.equals("-")) {
                    stateMatchCount.put(homeState, stateMatchCount.getOrDefault(homeState, 0) + 1);
                }
                if (!awayState.isBlank() && !awayState.equals("-")) {
                    stateMatchCount.put(awayState, stateMatchCount.getOrDefault(awayState, 0) + 1);
                }
            }
        }

        int minMatches = stateMatchCount.values()
                .stream()
                .min(Integer::compareTo)
                .orElse(0);

        return stateMatchCount.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == minMatches)
                .map(Map.Entry::getKey)
                .toList();
    }
}
