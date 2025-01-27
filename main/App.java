package main;

import java.util.*;
import java.time.*;

import redblack.RedBlackTree;
import splay.SplayTree;
import zip.ZipTree;
import zipzip.ZipZipTree;

public class App {

    private RedBlackTree rbTree;
    private SplayTree splayTree;
    private ZipTree zipTree;
    private ZipZipTree zipZipTree;
    private Random random;

    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final int SEARCH_SAMPLES = 1000;
    private static final int DELETE_SAMPLES = 1000;

    public App() {
        rbTree = new RedBlackTree();
        splayTree = new SplayTree();
        zipTree = new ZipTree();
        zipZipTree = new ZipZipTree();
        random = new Random();
    }

    public void runTests() {
        for (int size : SIZES) {
            System.out.println("Testing with " + size + " elements:");
            testInsert(size);
            testSearch(size);
            testDelete(size);
            System.out.println();
        }
    }

    private void testInsert(int size) {
        System.out.println();
        System.out.println("Insertion tests:\n");
        
        // Random order insertion
        List<Integer> randomList = generateRandomList(size);
        testInsertionPerformance("  Random Order", randomList);

        // Ascending order insertion
        List<Integer> ascendingList = generateAscendingList(size);
        testInsertionPerformance("  Ascending Order", ascendingList);

        // Descending order insertion
        List<Integer> descendingList = generateDescendingList(size);
        testInsertionPerformance("  Descending Order", descendingList);
    }

    private void testSearch(int size) {
        System.out.println("Search tests: \n");
        List<Integer> searchKeys = generateRandomList(SEARCH_SAMPLES);
        
        long rbTime = measureSearchTime(rbTree, searchKeys);
        long splayTime = measureSearchTime(splayTree, searchKeys);
        long zipTime = measureSearchTime(zipTree, searchKeys);
        long zipZipTime = measureSearchTime(zipZipTree, searchKeys);

        System.out.println("    Red-Black Tree: " + rbTime + "ns");
        System.out.println("    Splay Tree: " + splayTime + "ns");
        System.out.println("    Zip Tree: " + zipTime + "ns");
        System.out.println("    ZipZip Tree: " + zipZipTime + "ns");
        System.out.println();
    }

    private void testDelete(int size) {
        System.out.println("Deletion tests:\n");
        List<Integer> deleteKeys = generateRandomList(DELETE_SAMPLES);
        
        long rbTime = measureDeleteTime(rbTree, deleteKeys);
        long splayTime = measureDeleteTime(splayTree, deleteKeys);
        long zipTime = measureDeleteTime(zipTree, deleteKeys);
        long zipZipTime = measureDeleteTime(zipZipTree, deleteKeys);

        System.out.println("    Red-Black Tree: " + rbTime + "ns");
        System.out.println("    Splay Tree: " + splayTime + "ns");
        System.out.println("    Zip Tree: " + zipTime + "ns");
        System.out.println("    ZipZip Tree: " + zipZipTime + "ns");
        System.out.println();
    }

    private void testInsertionPerformance(String orderType, List<Integer> keys) {
        long rbTime = measureInsertionTime(rbTree, keys);
        long splayTime = measureInsertionTime(splayTree, keys);
        long zipTime = measureInsertionTime(zipTree, keys);
        long zipZipTime = measureInsertionTime(zipZipTree, keys);

        System.out.println(orderType + ":");
        System.out.println();
        System.out.println("        Red-Black Tree: " + rbTime + "ns");
        System.out.println("        Splay Tree: " + splayTime + "ns");
        System.out.println("        Zip Tree: " + zipTime + "ns");
        System.out.println("        ZipZip Tree: " + zipZipTime + "ns");
        System.out.println();
    }

    private long measureInsertionTime(RedBlackTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.insert(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private long measureInsertionTime(SplayTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.insert(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private long measureInsertionTime(ZipTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.insert(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private long measureInsertionTime(ZipZipTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.insert(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }


    private long measureSearchTime(RedBlackTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.search(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private long measureSearchTime(SplayTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.find(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private long measureSearchTime(ZipTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.search(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private long measureSearchTime(ZipZipTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.search(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private long measureDeleteTime(RedBlackTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.delete(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private long measureDeleteTime(SplayTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.delete(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private long measureDeleteTime(ZipTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.delete(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private long measureDeleteTime(ZipZipTree tree, List<Integer> keys) {
        Instant start = Instant.now();
        for (int key : keys) {
            tree.delete(key);
        }
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos();
    }

    private List<Integer> generateRandomList(int size) {
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(random.nextInt());
        }
        return list;
    }

    private List<Integer> generateAscendingList(int size) {
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    private List<Integer> generateDescendingList(int size) {
        List<Integer> list = new ArrayList<>(size);
        for (int i = size - 1; i >= 0; i--) {
            list.add(i);
        }
        return list;
    }

    public static void main(String[] args) {
        App app = new App();
        app.runTests();
    }
}