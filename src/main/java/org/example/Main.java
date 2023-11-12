package org.example;

public class Main {
    public static void main(String[] args) {
        ECField field = new ECField(2, 2, 17);
        ECPoint G = new ECPoint(5, 1, field);

        // Public Key 생성
        ECPoint xA = G.multiplyOperation(3);
        ECPoint xB = G.multiplyOperation(7);
        System.out.println(xA);
        System.out.println(xB);

        // Key Exchange
        ECPoint keyA = xB.multiplyOperation(3);
        ECPoint keyB = xA.multiplyOperation(7);
        System.out.println(keyA);
        System.out.println(keyB);


       // G.test(19 * 3);
    }

}