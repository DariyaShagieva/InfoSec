package ru.itis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
  private BigInteger d;
  private BigInteger n;
  private BigInteger e;

  public String rsaCoder (String text, boolean flag) {
    BigInteger coderText;
    if (flag) {
       coderText = new BigInteger(text, 16);
    } else {
      coderText = new BigInteger(text);
    }
    BigInteger encrypted = coderText.modPow(e, n);
    try (FileWriter writer = new FileWriter("rsaCoder.txt")) {
      String messageEncrypt;
      if (flag) {
        messageEncrypt = encrypted.toString(16);
      } else
        messageEncrypt = encrypted.toString();
      writer.write(messageEncrypt);
      writer.flush();
      System.out.println(messageEncrypt);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
    if (flag) {
      return encrypted.toString(16);
    } else return encrypted.toString();
  }

  public   void rsaDecoder(String text, boolean flag) {

    BigInteger decoderText;
    if (flag) {
      decoderText = new BigInteger(text, 16);
    } else {
      decoderText = new BigInteger(text);
    }
    BigInteger decrypted = decoderText.modPow(d, n);
    if (flag) {
      System.out.println(decrypted.toString(16));
    } else {
      System.out.println(decrypted);
    }
  }


  RSA() throws IOException {
    SecureRandom secureRandom = new SecureRandom();
    // выбираем два простых числа q и p
    BigInteger q = BigInteger.probablePrime(128, secureRandom);
    BigInteger p = BigInteger.probablePrime(128, secureRandom);

    // вычисляем модуль
    BigInteger n = p.multiply(q);

    // функция Эйлера
    BigInteger eulerFun = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

    // берем е и проверяем на взаимопростые числа
    BigInteger e = BigInteger.TWO;
    while (eulerFun.gcd(e).intValue() > 1) {
      e = e.add(BigInteger.ONE);
    }

    //Вычисляется d (алгоритмом Евклида) таким образом, что (e·d – 1) делилось на φ(n).
    BigInteger d = e.modInverse(eulerFun);

    FileWriter fileWriterOpenKey = new FileWriter(new File("openKey.txt"));
    FileWriter fileWriterCloseKey = new FileWriter(new File("closeKey.txt"));

    fileWriterOpenKey.write(e + " " + n);
    fileWriterCloseKey.write(d + " " + n);

    fileWriterCloseKey.flush();
    fileWriterCloseKey.close();
    fileWriterOpenKey.flush();
    fileWriterOpenKey.close();
    this.d = d;
    this.e = e;
    this.n = n;
  }

}
