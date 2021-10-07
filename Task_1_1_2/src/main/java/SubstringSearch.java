import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SubstringSearch {

    private ArrayList<Integer> zFunction(int subLen, String str) {
        int l = 0;
        int r = 0;
        int strLen = str.length();
        int[] z = new int[strLen];
        ArrayList<Integer> res = new ArrayList<>();
        z[0] = 0;
        for (int i = subLen + 1; i < strLen; i++) {
            z[i] = 0;
            if (i <= r) {
                z[i] = Math.min(z[i - l], r - i + 1);
            }
            while (i + z[i] < strLen && str.charAt(i + z[i]) == str.charAt(z[i])) {
                z[i]++;
            }
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
            if (z[i] == subLen) {
                res.add(i - subLen - 1);
            }
        }
        return res;
    }

    private ArrayList<Integer> zSearch(String subStr, String mainStr) {
        String strForZ;
        strForZ = String.join("0", subStr, mainStr);
        return zFunction(subStr.length(), strForZ);
    }

    /** Finds all entries of the substring in file
     * @param fileName name of the file to search within
     * @param subStr substring that is searched for
     * @return list of indexes of each substring entry
     */
    public ArrayList<Integer> Search(String fileName, String subStr) {
        int subLen = subStr.length();
        ArrayList<Integer>  resList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int bufferSize = 30;
            char[] charPartBig = new char[subLen * bufferSize];
            char[] charPart = new char[subLen - 1];
            String strPartBig;
            String strPart;
            reader.mark((subLen * bufferSize));
            if (reader.read(charPartBig) == -1) {
                strPartBig = String.valueOf(charPartBig);
                return zSearch(subStr, strPartBig);
            }
            String mainStr;
            ArrayList<Integer>  resPart;
            int i = 0;
            int x;
            reader.reset();
            reader.mark(subLen * bufferSize);
            while (reader.read(charPartBig) != -1) {
                strPartBig = String.valueOf(charPartBig);
                reader.mark(subLen);
                x = reader.read(charPart);
                strPart =  String.valueOf(charPart);
                mainStr = String.join("", strPartBig, strPart);
                resPart = zSearch(subStr, mainStr);
                for (int resElem : resPart) {
                    resList.add(resElem + (subLen * bufferSize * i));
                }
                if (x == -1) {
                    break;
                }
                i++;
                Arrays.fill(charPartBig, '\r');
                reader.reset();
                reader.mark(subLen * bufferSize);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return resList;
    }
}
