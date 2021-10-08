import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SubstringSearchTest {
    SubstringSearch z = new SubstringSearch();

    @ParameterizedTest
    @CsvSource({"input.txt,bra", "input.txt,Hello", "input2.txt,aaa", "input3.txt,me"})
    void testSubstringSearch(String fileName, String subString) throws IOException {
        ArrayList<Integer> resList = z.search(fileName, subString);
        ArrayList<Integer> correctList = z.search(fileName, subString);
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String strMain = reader.readLine();
        int k;
        k = strMain.indexOf(strMain);
        while (k != -1) {
            correctList.add(k);
            k = strMain.indexOf(subString, k + 1);
        }
        int[] correctArr = resList.stream().mapToInt(i -> i).toArray();
        int[] resArr = resList.stream().mapToInt(i -> i).toArray();
        Assertions.assertArrayEquals(correctArr, resArr);
    }

    @Test
    void testNotExistingFile() {
        Assertions.assertThrows(IOException.class, () -> z.search("input0.txt", "no"));
    }

    @Test
    void testEmptySubstring() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> z.search("input.txt", ""));
    }

    @Test
    void testEmptyFile() throws IOException {
        ArrayList<Integer> resList = z.search("input_empty.txt", "smth");
        int[] correctArr = resList.stream().mapToInt(i -> i).toArray();
        int[] resArr = {};
        Assertions.assertArrayEquals(correctArr, resArr);

    }

}