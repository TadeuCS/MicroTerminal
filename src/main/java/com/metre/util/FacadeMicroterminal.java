/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.util;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Tadeu-pc
 */
public abstract class FacadeMicroterminal {

    protected abstract void clear(OutputStream output);

    protected abstract void sendMessage(OutputStream output, String msg);

    protected abstract void setPosition(OutputStream output, int line, int column);

    protected abstract String getInput(String label, String mask, InputStream input, OutputStream output);
    
    protected abstract String getKey(InputStream input, OutputStream output, String mask);
    
    protected abstract String setKey(int tecla, OutputStream output, String mask);
}
