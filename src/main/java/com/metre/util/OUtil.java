/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.util;

/**
 *
 * @author Tadeu-PC
 */
public class OUtil {
    public static String onlyNumber(String retorno) {
        return retorno.replaceAll("[^0-9]", "");
    }
}
