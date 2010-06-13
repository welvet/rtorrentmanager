package rtorrent.utils;

import dialog.Dialog;
import dialog.Input;

import java.util.Collections;
import java.util.Comparator;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 0:00:04
 */
public class DialogUtils {
    private class InputComparator implements Comparator<Input> {
        public int compare(Input o1, Input o2) {
            if (o1.getPosition() > o2.getPosition())
                return 1;
            if (o1.getPosition() < o2.getPosition())
                return -1;
            return 0;
        }
    }

    public void sort(Dialog dialog) {       
        Collections.sort(dialog.getInputs(), new InputComparator());
    }
}
