import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMerger {
    static List<Pair<String, Collection<String>>> mergeUsers(List<Pair<String, Collection<String>>> source) {
        Map<String, Collection<String>> user2Mails = buildIndex(source);
        Map<String, Collection<String>> mail2Users = buildRevertIndex(source);

        List<Pair<String, Collection<String>>> result = new ArrayList<>();

        Collection<String> checkedUsers = new HashSet<>();
        for(Pair<String, Collection<String>> pair : source) {
            if (!checkedUsers.contains(pair.getLeft())) {
                Collection<String> mails = findLocalCycledGraph(user2Mails, mail2Users, pair.getLeft(), checkedUsers);
                result.add(new Pair<>(pair.getLeft(), mails));
            }
        }

        return result;
    }

    private static Map<String, Collection<String>> buildIndex(List<Pair<String, Collection<String>>> data) {
        return  data.stream().collect(Collectors.toMap(Pair::getLeft, Pair::getRight, (map1, map2) -> {return map2;}));
    }

    private static Map<String, Collection<String>> buildRevertIndex(List<Pair<String, Collection<String>>> data) {
        Map<String, Collection<String>> revertIndex = new HashMap<>();

        for(Pair<String, Collection<String>> inputRow : data) {
            for(String mail : inputRow.getRight()) {
                revertIndex.computeIfAbsent(mail, key -> new ArrayList<>()).add(inputRow.getLeft());
            }
        }

        return revertIndex;
    }


    private static Collection<String> findLocalCycledGraph(Map<String, Collection<String>> user2mails,
                                                           Map<String, Collection<String>> mail2users,
                                                           String rootUser,
                                                           Collection<String> checkedUsers) {
        checkedUsers.add(rootUser);
        Collection<String> mails = new HashSet<>();

        for (String mail : user2mails.get(rootUser)) {
            for (String user : mail2users.get(mail)) {
                if (!checkedUsers.contains(user))
                    mails.addAll(findLocalCycledGraph(user2mails, mail2users, user, checkedUsers));
                else
                    mails.add(mail);
            }
        }

        return mails;
    }
}
