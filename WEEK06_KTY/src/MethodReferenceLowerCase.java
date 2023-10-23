import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class MethodReferenceLowerCase {

    public static void main(String[] args) {
        Function<String, String> toLowerCase = (str) -> str.toLowerCase();
        
        List<String> listOfNames = Arrays.asList("Apple", "Banana", "Cherry");
        
        List<String> result = new ArrayList<>();

        for (String name : listOfNames) {
            result.add(toLowerCase.apply(name));
        }

        System.out.println("Original List: " + listOfNames);
        System.out.println("Lowercase List: " + result);
    }
}