package com.metre.gertec;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TCPClient {

    public static void main(String[] args) {
        //Inicialização automática do Servidor
        TCPServer conexao = new TCPServer();

        //Funções de utilização do Menu
        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        try {
            System.out.println("Aplicativo Exemplo MT 7X0 - Gertec v1.0");
            System.out.println("");
            System.out.println("**************************");
            System.out.println("********** Menu **********");
            System.out.println("**************************");
            System.out.println("0 - Mostrar Menu");
            System.out.println("1 - setEnableSerial");
            System.out.println("2 - getEnableSerial");
            System.out.println("3 - setSetupSerial");
            System.out.println("4 - getSetupSerial");
            System.out.println("5 - sendBinSerial");
            System.out.println("100 - FECHAR APLICATIVO");
            System.out.println("**************************");
            System.out.println("");

            System.out.print("Opcao escolhida: ");
            String key = br.readLine();
            System.out.println("");
            while ((Integer.parseInt(key) != 100) && (Integer.parseInt(key) < 100)) {
                switch (Integer.parseInt(key)) {

                    case 1:
                        System.out.println("Informe a porta COM: ");
                        key = br.readLine();
                        int valor = Integer.parseInt(key);
                        System.out.println("Informe 0 para True ou 1 para False !");
                        key = br.readLine();
                        int valorBooleano = Integer.parseInt(key);
                        System.out.println("ValorBooleano: " + valorBooleano);
                        conexao.setEnableSerial(valor, valorBooleano);
                        key = "0";
                        break;
                    case 2:
                        System.out.println("Informe a porta COM: ");
                        key = br.readLine();
                        int valor2 = Integer.parseInt(key);
                        conexao.getEnableSerial(valor2);
                        key = "0";
                        break;
                    case 3:
                        System.out.println("Informe a porta COM: (0 a 3) ");
                        key = br.readLine();
                        int comPort = Integer.parseInt(key);
                        System.out.println("Informe a velocidade de transmissão (Baud): (1200, 2400, 4800, 9600, 19200, 38400, 57600, 115200)");
                        key = br.readLine();
                        String baud = key;
                        System.out.println("Informe os Bits: (5 a 8)");
                        key = br.readLine();
                        String bits = key;
                        System.out.println("Informe os Bits de Paridade: (0 a 4)");
                        key = br.readLine();
                        String parity = key;
                        System.out.println("Informe os Bits de Parada: (1 ou 2)");
                        key = br.readLine();
                        String stops = key;
                        System.out.println("Informe o Handshaking utilizado: (0 ou 1)");
                        key = br.readLine();
                        String handshaking = key;
                        conexao.setSetupSerial(comPort, baud, bits, parity, stops, handshaking);
                        key = "0";
                        break;
                    case 4:
                        System.out.println("Informe a porta COM: (0 a 3) ");
                        key = br.readLine();
                        int comPortGetSetupSerial = Integer.parseInt(key);
                        conexao.getSetupSerial(comPortGetSetupSerial);
                        key = "0";
                        break;
                    case 5:
                        System.out.println("Informe a porta COM: (0 a 3) ");
                        key = br.readLine();
                        int comPortSendBindSerial = Integer.parseInt(key);
                        conexao.sendBinSerial(comPortSendBindSerial);
                        key = "0";
                        break;
                    default:

                        System.out.println("Aplicativo Exemplo MT 7X0 - Gertec v1.0");
                        System.out.println("");
                        System.out.println("**************************");
                        System.out.println("********** Menu **********");
                        System.out.println("**************************");
                        System.out.println("0 - Mostrar Menu");
                        System.out.println("1 - setEnableSerial");
                        System.out.println("2 - getEnableSerial");
                        System.out.println("3 - setSetupSerial");
                        System.out.println("4 - getSetupSerial");
                        System.out.println("5 - sendBinSerial");
                        System.out.println("100 - FECHAR APLICATIVO");
                        System.out.println("**************************");
                        System.out.println("");

                        System.out.print("Opcao escolhida: ");
                        key = br.readLine();
                        System.out.println("");
                        break;

                } //Fim de Switch

            } //Fim de while

            System.out.println("Fechando Aplicativo");
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fim do Aplicativo com ERRO");
        }

    } //Fim de Main

} //Fim da classe TCPClient
