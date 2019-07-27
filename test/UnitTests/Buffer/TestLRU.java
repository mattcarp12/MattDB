package UnitTests.Buffer;

import java.util.ArrayList;
import java.util.HashMap;

public class TestLRU {

    public static void testArrayList() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(2);
        list.add(4);
        list.add(6);
        list.add(1);
        System.out.println(list.remove(0));
        System.out.println(list.remove(0));
        System.out.println(list.remove(0));
        System.out.println(list.remove(0));
        System.out.println(list.remove(0));

    }

    public static void testHash() {
        HashMap<Integer, Integer> hash = new HashMap<>();
        hash.put(1, 1);
        hash.put(2, 2);
        hash.remove(1);
        hash.remove(0);
    }

    public static void main(String[] args) {
        testHash();
        testArrayList();
    }
}
