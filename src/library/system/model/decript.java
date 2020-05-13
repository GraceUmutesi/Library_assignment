/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.model;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class decript {
    public String econde(String password){
        Base64.Encoder en = Base64.getEncoder();
        return en.encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }
    public String decode(String password){
        Base64.Decoder dec = Base64.getDecoder();
        return new String(dec.decode(password));
    }
}
