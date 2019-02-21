/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.gertec;

import com.metre.util.FacadeMicroterminal;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Tadeu-pc
 */
public class Gertec extends FacadeMicroterminal{

    @Override
    protected void clear(OutputStream output) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void sendMessage(OutputStream output, String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setPosition(OutputStream output, int line, int column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String getInput(String label, String mask, InputStream input, OutputStream output) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String getKey(InputStream input, OutputStream output, String mask) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String setKey(int tecla, OutputStream output, String mask) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
