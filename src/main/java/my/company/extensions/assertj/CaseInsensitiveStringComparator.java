package my.company.extensions.assertj;


import java.util.Comparator;

public class CaseInsensitiveStringComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        return s1.toLowerCase().compareTo(s2.toLowerCase());
    }

    @Override
    public String toString() {
        return "CaseInsensitiveStringComparator";
    }
}
