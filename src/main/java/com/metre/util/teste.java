/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.util;

import java.util.regex.Pattern;

/**
 *
 * @author Tadeu-PC
 */
public class teste {

    public static void main(String[] args) {
        String texto = "t22ade2u1231$#´´";
        System.out.println(texto.replaceAll("[^0-9]", ""));
    }
}
