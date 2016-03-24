package Comperators;

import MyGraph.ColorChoose;

import java.util.Comparator;

/**
 * Created by sanczo on 2016-03-18.
 */
public class LessConflictedComparator implements Comparator {
    @Override

    //Przyklad wykorzystania polimorfizmu ad hoc typu koercja
    public int compare(Object o1, Object o2) {

        ColorChoose first = (ColorChoose)o1;
        ColorChoose second = (ColorChoose)o2;

        int firstCompare = Integer.compare(first.getConflict(),second.getConflict());
        if (firstCompare == 0)
            return Integer.compare(first.getConflict(), second.getConflict());
        else
            return firstCompare;
    }
}
