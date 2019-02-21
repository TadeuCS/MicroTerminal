/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.bematech;

/**
 *
 * @author Tadeu-pc
 */
public class BemaFIT {

    public static native int IniciaComunicacao();

    public static native int FechaComunicacao();

    public static native int Bematech_FIT_SelecionaProtocolo(int protocolo); //0: VT100-STX/ETX ou 1: VT100-ESC.

    public static native int LimpaDisplay(char terminal);

    public static native int EscreveDisplay(char terminal, String mensagem);

    public static native int PosicionaCursor(char terminal, char linha, char coluna);

    public static native byte LerBuffer(char terminal);

    public static native int EnviaSerial(char terminal, char dados, char serial);

    public static native int ApagaLinha(char terminal);

    public static native int DeslocaCursorCima(char terminal);//Este comando será executado somente se o protocolo for VT100-ESC.

    public static native int DeslocaCursorDireita(char terminal);//Este comando será executado somente se o protocolo for VT100-ESC.

    public static native int VersaoFirmware(char terminal); //irá retornar byte a byte no formato "<STX> + <dados> + <ETX>".

    public static native int ComandoGenerico(char terminal, char comando); //Este comando será executado somente se o protocolo for VT100-STX-ETX

    public static native int LeituraCodigoBarras(char terminal);

    public static native int EnviaComando(char terminal);

}
