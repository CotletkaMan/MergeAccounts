import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class UserMergerTest {
    private final Pair<String, Collection<String>> user1 =
            new Pair<>("user1", new HashSet<>(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")));

    private final Pair<String, Collection<String>> user2 =
            new Pair<>("user2", new HashSet<>(Arrays.asList("foo@gmail.com", "ups@pisem.net")));

    private final Pair<String, Collection<String>> user3 =
            new Pair<>("user3", new HashSet<>(Arrays.asList("xyz@pisem.net", "vasya@pupkin.com")));

    private final Pair<String, Collection<String>> user4 =
            new Pair<>("user4", new HashSet<>(Arrays.asList("ups@pisem.net", "aaa@bbb.ru")));

    private final Pair<String, Collection<String>> user5 =
            new Pair<>("user5", new HashSet<>(Arrays.asList("xyz@pisem.net")));

    @Test
    public void emptyTest () {
        List<Pair<String, Collection<String>>> result = UserMerger.mergeUsers(Collections.emptyList());

        Assertions.assertTrue(result.isEmpty(), "Make users from nothing!!");
    }

    @Test
    public void oneRowTest () {
        List<Pair<String, Collection<String>>> input = Collections.singletonList(user1);
        List<Pair<String, Collection<String>>> result = UserMerger.mergeUsers(input);

        Assertions.assertEquals(input, result, "algorithm breaks unique case !!");
    }

    @Test
    public void sameRowTest () {
        List<Pair<String, Collection<String>>> input = Arrays.asList(user1, user1);
        List<Pair<String, Collection<String>>> result = UserMerger.mergeUsers(input);

        Assertions.assertEquals(user1, result.get(0), "algorithm breaks unique case !!");
    }

    @Test
    public void twoSeparateRowTest () {
        List<Pair<String, Collection<String>>> input = Arrays.asList(
                new Pair<>("user1", Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")),
                new Pair<>("user3", Arrays.asList("xyz@pisem.net", "vasya@pupkin.com"))
        );
        List<Pair<String, Collection<String>>> result = UserMerger.mergeUsers(input);

        Assertions.assertEquals(input, result, "algorithm breaks unique case !!");
    }

    @Test
    public void mergeOneUserCase () {
        List<Pair<String, Collection<String>>> input = Arrays.asList(user1, user2);

        Pair<String, Collection<String>> expectedResult = new Pair<>("user1", new HashSet<>(Arrays.asList(
                "xxx@ya.ru", "foo@gmail.com", "lol@mail.ru", "ups@pisem.net"
        )));
        List<Pair<String, Collection<String>>> result = UserMerger.mergeUsers(input);

        Assertions.assertEquals(expectedResult, result.get(0), "Can't merge one user!!");
    }

    @Test
    public void generalCase () {
        List<Pair<String, Collection<String>>> input = Arrays.asList(user1, user2, user3, user4, user5);

        Pair<String, Collection<String>> erUser1 = new Pair<>("user1", new HashSet<>(Arrays.asList(
                "xxx@ya.ru", "foo@gmail.com", "lol@mail.ru", "ups@pisem.net", "aaa@bbb.ru"
        )));


        List<Pair<String, Collection<String>>> expectedResult = Arrays.asList(erUser1, user3);
        List<Pair<String, Collection<String>>> result = UserMerger.mergeUsers(input);

        Assertions.assertEquals(expectedResult, result, "Can't merge many users!!");
    }
}


/*
user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru
user2 -> foo@gmail.com, ups@pisem.net
user3 -> xyz@pisem.net, vasya@pupkin.com
user4 -> ups@pisem.net, aaa@bbb.ru
user5 -> xyz@pisem.net
 */

/*
user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru, ups@pisem.net, aaa@bbb.ru
user3 -> xyz@pisem.net, vasya@pupkin.com
 */