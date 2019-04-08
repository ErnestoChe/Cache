package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        NativeCache<Integer> nc = new NativeCache<>(7, Integer.class);
        nc.put("qwe", 1);
        nc.put("asd", 2);
        nc.put("zxc", 3);
        nc.put("rty", 4);
        nc.put("fgh", 5);
        nc.put("vbn", 6);
        nc.put("uio", 7);
        nc.log();
        for (int i = 0; i < 3; i++) {
            nc.get("qwe");
            nc.get("asd");
            nc.get("zxc");
            nc.get("rty");
        }
        nc.get("uio");
        for (int i = 0; i < 5; i++) {
            nc.get("fgh");
            nc.get("vbn");
        }
        nc.get("fgh");
        System.out.println();
        nc.log();
        nc.put("uio", 7);
        nc.log();
        nc.put("qwerty", 2);
        nc.log();
        nc.put("topchik", 12);
        nc.log();
    }
}
