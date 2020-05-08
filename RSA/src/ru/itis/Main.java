package ru.itis;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

public class Main {

  public static void main(String[] args) throws IOException {
    String key = getRandomKey(50, false);
    String key16 = getRandomKey(50, true);
    System.out.println("Сгенерированные ключи:");
    System.out.println(key);
    System.out.println(key16);

    BufferedReader reader = new BufferedReader(new FileReader("text.txt"));
    String text = reader.readLine();
    Lab02 lab02 = new Lab02(text, key);
    RSA rsa = new RSA();

    rsa.rsaDecoder(rsa.rsaCoder(key, false), false);
    rsa.rsaDecoder(rsa.rsaCoder(key16, true), true);

  }

  private static String getRandomKey(int len, boolean flag) {
    Random random = new Random();
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < len; i++) {
      builder.append(random.nextInt(10));
    }
    if (flag) {
      return new BigInteger(builder.toString()).toString(16);
    }
    return builder.toString();
  }


}
