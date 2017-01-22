package com.ecfront.dew.common;

import org.junit.Assert;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.Objects;

public class EncryptHelperTest {

    @Test
    public void symmetric() throws Exception {
        Assert.assertEquals(EncryptHelper.Symmetric.encrypt("gudaoxuri", "SHA-256"), "70C0CC2B7BF8A8EBCD7B59C49DDDA9A1E551122BA5D7AB3B7B02141D4CE4C626".toLowerCase());
        Assert.assertTrue(EncryptHelper.Symmetric.validate("gudaoxuri", EncryptHelper.Symmetric.encrypt("gudaoxuri", "SHA-256"), "SHA-256"));
        Assert.assertTrue(EncryptHelper.Symmetric.validate("password", EncryptHelper.Symmetric.encrypt("password", "bcrypt"), "bcrypt"));
    }

    @Test
    public void asymmetric() throws Exception {
        String privateStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALjt0CEssHfGENZxyASF6pNtGKYCGW43+LE3JhT8y8TE39vDK22GJZWJFXYfWwasavknIfepBIVrnuMidtcPqUY3bhrDZN+J6MtYaSPSEwRcS2PgF/065CEdSbLy6cvKA64GUiG188un1xIsGBVUdu3fdu41OQvt+90TZT0HclXJAgMBAAECgYEAjXFndVhHCPU3P637PGppBqW06pREeybYUkNKH1dTS4cBaYcXmke2S290OMq2xp3tm++wbUqbKKkt97AOkWNrJfq8Ecpdw9s3c7yQGWaPuwiX38Cgtq6r0utjT20YgR6etGpqafoBt93RZpEm0eEzFPUnS7qYc86HprL0RJ0/i7kCQQDaOmvO82cYIK1ESkA0GdDVQoz2A1V8HvEWOsccRGqlWuasLUccyBnx1G/LDZUxcPOraDyxI8sdl7VbweLR0H9LAkEA2O/rWXwnSYKqdpt+OhpUBHNnMs3IMvRzefJ1zObnIMyYR3KXtpQ/fL4gEquNwJgFIaPJVg5/3zHISEw3e8XOuwJAIDrGl07tZ+vTiyVoLAmwBP8KMH83jdhIBN9zbqJQGdG+Bam+Oer3ofac+CEuapni8uq3I/ZEVj+EomOVKyWe1wJAATztROd2ee7q9h5RDBfWXughsKKH//JxLkL59R9kNkW0oMPApeQWsKmNGU4tUuoLLXP31CvlAusPz4nPzz8DvQJBAJXpICPNJw84fONzS0raRqlFoZMMI0cqeGtPIiCHKaRHyzQv/FFu2KxUcCrod8PngaBFRselzrwZILmXHqrHc1M=";
        String publicStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC47dAhLLB3xhDWccgEheqTbRimAhluN/ixNyYU/MvExN/bwytthiWViRV2H1sGrGr5JyH3qQSFa57jInbXD6lGN24aw2TfiejLWGkj0hMEXEtj4Bf9OuQhHUmy8unLygOuBlIhtfPLp9cSLBgVVHbt33buNTkL7fvdE2U9B3JVyQIDAQAB";

        Map<String, String> keys = EncryptHelper.Asymmetric.generateKeys("RSA", 1024, "UTF-8");
        PublicKey publicKey = EncryptHelper.Asymmetric.getPublicKey(publicStr, "RSA");
        PrivateKey privateKey = EncryptHelper.Asymmetric.getPrivateKey(privateStr, "RSA");

        byte[] encryptByPub = EncryptHelper.Asymmetric.encrypt("好的了".getBytes("UTF-8"), publicKey, "RSA");
        String result = new String(EncryptHelper.Asymmetric.decrypt(encryptByPub, privateKey, "RSA"), "UTF-8");
        Assert.assertTrue(Objects.equals(result, "好的了"));

        byte[] encryptByPriv = EncryptHelper.Asymmetric.encrypt("好的了".getBytes("UTF-8"), privateKey, "RSA");
        byte[] decryptByPub = EncryptHelper.Asymmetric.decrypt(encryptByPriv, publicKey, "RSA");
        Assert.assertTrue(Objects.equals(new String(decryptByPub, "UTF-8"), "好的了"));
        Assert.assertTrue(EncryptHelper.Asymmetric.verify(publicKey, decryptByPub, EncryptHelper.Asymmetric.sign(privateKey, "好的了".getBytes("UTF-8"), "SHA1withRSA"), "SHA1withRSA"));
    }

}