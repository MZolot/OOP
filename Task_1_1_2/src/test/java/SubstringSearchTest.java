import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

//import static org.junit.jupiter.api.Assertions.*;

class SubstringSearchTest {

    @Test
    public void testSimpleString() {
        SubstringSearch z = new SubstringSearch();
        String str = "bra";
        ArrayList<Integer> res1 = z.Search("input.txt", str);
        ArrayList<Integer> correct = new ArrayList<>();
        correct.add(1);
        correct.add(8);
        Assertions.assertEquals(correct, res1);
    }


}