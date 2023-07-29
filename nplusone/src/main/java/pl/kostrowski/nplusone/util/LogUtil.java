package pl.kostrowski.nplusone.util;

import java.util.List;

public class LogUtil {

    public static String makeList(List<?> items, int intend, boolean addNewLine) {
        String intendation = "    ".repeat(intend);
        StringBuilder sb = new StringBuilder();
        if (addNewLine) {
            sb.append("\n");
        }
        items.forEach(s->sb.append(intendation).append(s).append("\n"));
        return sb.toString();
    }


}
