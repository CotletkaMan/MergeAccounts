import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args){
        try {
            List<Pair<String, Collection<String>>> inputData = uploadSourceData();
            printResult(UserMerger.mergeUsers(inputData));
        } catch (Exception e) {
            System.err.println("Something wrong " + e);

        }
    }

    private static List<Pair<String, Collection<String>>> uploadSourceData() throws Exception{
        List<Pair<String, Collection<String>>> inputData = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for(String inputLine = br.readLine(); inputLine != null && !inputLine.isEmpty(); inputLine = br.readLine()) {
            List<String> parsedLine = Arrays.stream(inputLine.split("\\s?(->|,)\\s?"))
                    .collect(Collectors.toCollection(LinkedList::new));
            String key = parsedLine.get(0);
            parsedLine.remove(0);

            inputData.add(new Pair<>(key, parsedLine));
        }

        br.close();

        return inputData;
    }



    private static void printResult(List<Pair<String, Collection<String>>> result) {
        result.forEach(pair ->
                System.out.println(
                        pair.getLeft() + " -> " + String.join(", ", pair.getRight())
                )
        );
    }
}
