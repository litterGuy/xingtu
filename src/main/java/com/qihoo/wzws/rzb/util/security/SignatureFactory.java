
package com.qihoo.wzws.rzb.util.security;


public class SignatureFactory {

    public static SignatureManager getSignature(String securityId) {

        if ("0001".equals(securityId)) {

            return new RSASignatureManager();

        }


        return null;

    }

}