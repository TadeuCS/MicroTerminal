/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.bematech;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tadeu-PC
 */
public class SocketBemaFIT {

    private static int protocolo;
    private static char terminal;
    private int iRetorno = 0;
    private byte tmp;

    public SocketBemaFIT(Integer numProtocolo, char numTerminal, String dirDll) {
        this.protocolo = numProtocolo;
        this.terminal = numTerminal;
        String dllBemaSB32 = "C:/Chef/Bin/BemaSB32.dll";
        if (dirDll != null) {
            dllBemaSB32 = dirDll;
        }
        System.load(dllBemaSB32);
    }

    public void iniciaComunicacao() {
        // Inicia a comunicação com o FIT Básico
        iRetorno = BemaFIT.IniciaComunicacao();
        if (iRetorno == 0) {
            JOptionPane.showMessageDialog(null, "Erro ao iniciar comunicação com o leitor!");
        } else {
            JOptionPane.showMessageDialog(null, "Conexão iniciada com sucesso!");
        }
    }

    public void fechaComunicacao() {
        // finaliza a comunicação com o FIT Básico
        iRetorno = BemaFIT.FechaComunicacao();
        if (iRetorno == 0) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar comunicação com o leitor!");
        } else {
            JOptionPane.showMessageDialog(null, "Conexão Finalizada com sucesso!");
        }
    }

    public void selecionaProtocolo() {
        // Inicia a comunicação com o FIT Básico
        System.out.println(protocolo);
        iRetorno = BemaFIT.Bematech_FIT_SelecionaProtocolo(protocolo);
        if (iRetorno == 0) {
            JOptionPane.showMessageDialog(null, "Erro ao selecionar o protocolo de comunicação!");
        }
    }

    public void ativaLeitura() {
        // Esta função será executada somente se o protocolo for VT100-ESC.
        // Solicita ao microterminal que envie a sua versão de firmware.
        iRetorno = BemaFIT.LeituraCodigoBarras(terminal);
        if (iRetorno == 0) {
            JOptionPane.showMessageDialog(null, "Erro ao selecionar o protocolo de comunicação!");
        }
    }

    public void versaoFirmware() {
        // Verifica a versão do firmware
        iRetorno = BemaFIT.VersaoFirmware(terminal);
        if (iRetorno == 0) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar a versão do Firmware do terminal!");
        }
    }

    public String lerBuffer() {
        // Repete até que o leitor envie o ENTER
        String buffer = "";
        try {
            while (tmp != (char) 3) {
                tmp = BemaFIT.LerBuffer(terminal);
                buffer = buffer + (char) tmp;
                Thread.sleep(100);
            }
            if (iRetorno == 0) {
                System.out.println("Erro ao ao ler o buffer!");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SocketBemaFIT.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Fim da leitura dos comandos!");
        return buffer;
    }

    public void escreveDisplay(String msg) {
        // Verifica a versão do firmware
        limparDisplay();
        iRetorno = BemaFIT.EscreveDisplay(terminal, msg);
        if (iRetorno == 0) {
            JOptionPane.showMessageDialog(null, "Erro ao escrever no display!");
        }
    }

    public void limparDisplay() {
        // Verifica a versão do firmware
        iRetorno = BemaFIT.LimpaDisplay(terminal);
        if (iRetorno == 0) {
            JOptionPane.showMessageDialog(null, "Erro ao limpar o display!");
        }
    }
    public void lerCodigoBarras() {
        // Verifica a versão do firmware
        iRetorno = BemaFIT.LeituraCodigoBarras(terminal);
        if (iRetorno == 0) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o código de barras!");
        }
    }

}
