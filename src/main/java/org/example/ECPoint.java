package org.example;

import java.math.BigInteger;

public class ECPoint {
    private BigInteger x;
    private BigInteger y;
    private ECField field; //그래프에 정보 넣어줌
    public ECPoint(BigInteger x, BigInteger y, ECField field) {
        this.x = x;
        this.y = y;
        this.field = field;
    }
    public ECPoint(int x, int y, ECField field) {
        this.x = BigInteger.valueOf(x);
        this.y = BigInteger.valueOf(y);
        this.field = field;
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public ECField getField() {
        return field;
    }

    public void setField(ECField field) {
        this.field = field;
    }

    //지금 점 = 들어온 점 ?
    public boolean allPointSame(ECPoint r){
        return getX().equals(r.getX()) && getY().equals(r.getY());
    }

    //무한대 영역 재귀함수 탈출을 해주기 위해
    public ECPoint addOperation(ECPoint r){
        if (r.getY().equals(field.getP())){ //전달 받은 나를 전달하면 됨(순환)
            System.out.println("P + O = P \n");
            return this;
        }
        if (getY().equals(field.getP())){ // 새로 전달 받은 점을 넣어줌
            System.out.println("O + P = P \n");
            return r;
        }

        BigInteger S; //기울기를 구한다
        if (allPointSame(r)) {
            // 전달 받은 값이 나랑 같으면 P = Q
            if (this.getY().equals(BigInteger.ZERO)){ //0이 되는 경우 예외처리 -> y = 0이다 -> 계속 0임
                return new ECPoint(getX(), field.getP(), field); // x = 0, y = 17 -> 일부러 무한의 점으로 만듦
            }
            BigInteger modInverseResult = field.modInverse(BigInteger.valueOf(2).multiply(getY())); //2Yp
            S = BigInteger.valueOf(3).multiply(getX().pow(2)).add(field.getA()).multiply(modInverseResult).mod(field.getP());
        }else{ // 전달 받은 값이 나랑 같지 않을 때 P != Q
            if (this.getX().equals(r.getX())){ // x좌표는 같은데 y좌표가 다른 경우가 있을수도 있음
                return new ECPoint(getX(), field.getP(), field); //모듈러 값과 같은 값을 넣어줘서 이상한 애를 잡아냄
            }
            BigInteger modInverseResult = field.modInverse(r.getX().subtract(getX()));
            S = r.getY().subtract(getY()).multiply(modInverseResult).mod(field.getP());
        }
        BigInteger newX = S.pow(2).subtract(getX()).subtract(r.getX()).mod(field.getP()); //역수일 경우 mod 연산
        BigInteger newY = S.multiply(getX().subtract(newX)).subtract(getY()).mod(field.getP());

        return new ECPoint(newX, newY, field);
    }
/*
    public ECPoint test(int len){
        ECPoint newPoint = this;
        System.out.println("k: " + 1);
        System.out.println("x: " + newPoint.getX());
        System.out.println("y: " + newPoint.getY());
        System.out.println(newPoint);
        System.out.println();
        for(int k = 0; k < len - 1; k++){
            newPoint = addOperation(newPoint);

            if (newPoint.getY() == field.getP()) {
                continue;
            }
            System.out.println("k: " + (k + 2));
            System.out.println("x: " + newPoint.getX());
            System.out.println("y: " + newPoint.getY());
            System.out.println(newPoint);
            System.out.println();
        }
        return newPoint;
    }
 */

    @Override
    public String toString() {
        return "ECPoint{" +
                "x=" + x +
                ", y=" + y +
                ", field=" + field +
                '}';
    }

   public ECPoint multiplyOperation(int k) {
       ECPoint newPoint = this;
        for(int i = 0; i< k-1 ; i++){
            newPoint = addOperation(newPoint);
            if (newPoint.getY() == field.getP()) {
                continue;
            }
        }
       return newPoint;
   }

}

