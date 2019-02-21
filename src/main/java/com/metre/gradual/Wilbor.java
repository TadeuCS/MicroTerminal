/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.gradual;

import com.metre.util.FacadeMicroterminal;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Tadeu-pc
 */
public class Wilbor extends FacadeMicroterminal {

    @Override
    protected void clear(OutputStream output) {
        int i;
        String s = (char) 27 + "[H" + (char) 27 + "[J";
        for (i = 0; i < s.length(); i++) {
            try {
                output.write(s.charAt(i));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void sendMessage(OutputStream output, String m) {
        clear(output);
        int i;
//        String s = (char) 27 + "[" + String.valueOf(0) + ";" + String.valueOf(0) + "H" + msg;
        String s = (char) 27 + "[ " + m;
        for (i = 0; i < s.length(); i++) {
            try {
                output.write(s.charAt(i));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void setPosition(OutputStream output, int line, int column) {
        int i;
        String s = (char) 27 + "[" + String.valueOf(line) + ";" + String.valueOf(column) + "H";
        for (i = 0; i < s.length(); i++) {
            try {
                output.write(s.charAt(i));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected String getInput(String label, String mask, InputStream input, OutputStream output) {
        clear(output);
        int i;
        for (i = 0; i < label.length(); i++) {
            try {
                output.write(label.charAt(i));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return getKey(input, output, mask);
    }

    @Override
    protected String getKey(InputStream input, OutputStream output, String mask) {
        int pospic = 0, posicao = 0, retorno = 0;
        String s = "";
        Boolean fim = false;
        int dado;
        do {
            dado = -1;
            while (dado == -1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                try {
                    dado = input.read();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
//            System.out.println("Dado: " + dado);
//            System.out.println("String: "+s);
            try {
                switch (dado) {
                    //enter
                    case 13:
                        fim = true;
                        s = s + (char) 13;
                        break;
                    //backspace
                    case 8:
                        System.out.println("String: " + s);
                        if (s.length() > 0) {
                            s = s.substring(0, s.length() - 1);
                            output.write(8);
                            output.write(32);
                            output.write(8);
                        }
                        System.out.println("String: " + s);
                        break;
                    //delete
                    case 27:
                        dado = 13;
                        s = "0";
                        fim = true;
//                        if (s.length() > 0) {
//                            while (s.length() > 0) {
//                                output.write(8);
//                                s = s.substring(s.length() - 1);
//                            }
//                        } else {
//                            s = "";
//                            s = s + (char) 27;
//                            fim = true;
//                        }
                        break;
                    default:
                        switch (mask.substring(s.length(), s.length() + 1)) {
                            case "9":
                                if ((dado >= 48) && (dado <= 57)) {
                                    output.write(dado);
                                    s = s + (char) dado;
                                }
                                break;
                            case "A":
                                if ((dado >= 65) && (dado <= 90)) {
                                    output.write(dado);
                                    s = s + (char) dado;
                                }
                                break;
                            case "a":
                                if ((dado >= 97) && (dado <= 122)) {
                                    output.write(dado);
                                    s = s + (char) dado;
                                }
                                break;
                            case "X":
                                output.write(dado);
                                s = s + (char) dado;
                                break;
                            case "|":
                                if (dado == 13) {
                                    output.write(dado);
                                    s = s + (char) 13;
                                }
                                break;

                            default:
                                output.write(38);
                                break;
                        }
                        if (s.length() == mask.length()) {
                            fim = true;
                            s = s + (char) 13;
                        }
                        break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } while (fim == false);
        return s;
    }

    @Override
    protected String setKey(int dado, OutputStream output, String mask) {
        try {
            output.write(dado);
        } catch (Exception e) {
        }
        return "" + (char) dado;
    }
}
