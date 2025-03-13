package com.hms;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class AA {

//        public static void main(String[] args) {
//            String enPw = BCrypt.hashpw("testing", BCrypt.gensalt(5));
//            System.out.println(enPw);
//        }
public static void main(String[] args) {

    String imageUrl = "https://myhotelphotos-ashu.s3.eu-north-1.amazonaws.com/krishna_ji.jpg";
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    System.out.println(fileName);

}
}

