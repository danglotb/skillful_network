package fr.uca.cdr.skillful_network.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import fr.uca.cdr.skillful_network.entities.user.Perk;

public class AutoCompletion {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoCompletion.class);

    private static final long MAX_CANDIDATE_NUMBERS_WITHOUT_PREFIX = 5;
    private static final long MAX_CANDIDATE_NUMBERS = 5;

    public List<Perk> findCandidates(List<Perk> inputList, String pMatch) {
        final String match = pMatch != null ? pMatch.toLowerCase() : "";
        final List<Perk> candidates = inputList.stream()
                .filter(item -> match.isEmpty() || item.getName().toLowerCase().contains(match))
                .sorted((o1, o2) -> {
                    final int compare = - o1.getUserList().size() - o2.getUserList().size();
                    return compare == 0 ? o1.getName().compareTo(o2.getName()) : compare;
                })
                .limit(match.isEmpty() ? MAX_CANDIDATE_NUMBERS_WITHOUT_PREFIX : MAX_CANDIDATE_NUMBERS)
                .collect(Collectors.toList());
        LOGGER.info("Sorted candidate list: {}", candidates.toString());
        return candidates;
    }

}
