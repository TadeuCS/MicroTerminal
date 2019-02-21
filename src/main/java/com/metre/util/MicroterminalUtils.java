/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metre.util;

import com.metre.gertec.Gertec;
import com.metre.gradual.Wilbor;
import com.metre.model.Microterminal;
import static com.metre.util.MicroterminaEnum.MT_720;
import static com.metre.util.MicroterminaEnum.MT_740;
import static com.metre.util.MicroterminaEnum.WILBOR_16;
import static com.metre.util.MicroterminaEnum.WILBOR_44;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tadeu-pc
 */
public class MicroterminalUtils extends Thread {

    public String MeuIP;

    private Socket socket;
    private int linhas;
    private int colunas;
    private InputStream input;
    private OutputStream output;
    private FacadeMicroterminal microTerminalFactory;
    private Microterminal microterminal;

    private Integer conta = 0;
    private Integer atendente = 0;
    private List<PedidoItemPojo> itens;

    public MicroterminalUtils(Microterminal microTerminal, Socket socket) {
        this.socket = socket;
        this.microterminal = microTerminal;
        trataDisplayByModeloTerminal(microTerminal.getModelo());
    }

    @Override
    public void run() {
        //String MeuIP;
        MeuIP = "";
        int dados;
        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();
            MeuIP = socket.getRemoteSocketAddress().toString();
            System.out.println("Terminal conectado: " + MeuIP);
            dados = 0;
            do {
                do {
                    novoPedido();
                } while (dados != 13);
                dados = 0;
            } while (dados != 0);
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Terminal desconectado:" + MeuIP);
    }

    private String getConta() throws IOException {
        String retorno = microTerminalFactory.getInput("Num Conta: ", "9999", input, output);
        System.out.println("Ret Conta: " + retorno);
        if ((retorno == null || retorno.trim().isEmpty()) && microterminal.getValidaLancamentos()) {
            microTerminalFactory.sendMessage(output, "Informe o número da conta e pressione ENTER");
            retorno = getConta();
        }
        return retorno == null || retorno.trim().isEmpty() ? "0" : retorno.trim();
    }

    private String getAtendente() throws IOException {
        String retorno = microTerminalFactory.getInput("Cod. Atendente: ", "99", input, output);
        System.out.println("Ret Atendente: " + retorno);
        if (microterminal.getValidaLancamentos()) {
            if (retorno == null || retorno.trim().isEmpty()) {
                microTerminalFactory.sendMessage(output, "Informe o código do atendente e pressione ENTER");
                retorno = getAtendente();
            } else {
                String ret = microTerminalFactory.getInput(trataLinhasDisplay("Atendente: Tadeu CS\nConfirmar (1=Sim 0=Não):"), "9", input, output);
                if (ret.trim().equals("0")) {
                    retorno = getAtendente();
                }
            }
        }
        return retorno == null || retorno.trim().isEmpty() ? "0" : retorno.trim();
    }

    private List<PedidoItemPojo> getListPedidoItens() throws IOException {
        if (itens == null) {
            itens = new ArrayList<>();
        }
        while (lancaItem()) {

        }
        return itens;
    }

    private Boolean lancaItem() throws IOException {
        Boolean lancaProximo = Boolean.TRUE;
        String codProd = getProduto();
        System.out.println("CodProd: " + codProd + "|");
        if (codProd != null && codProd.trim().equals("0")) {
            String retorno = microTerminalFactory.getInput(trataLinhasDisplay("Deseja Cancelar o Pedido\nConfirmar (1=Sim 0=Não):"), "9", input, output);
            System.out.println("Ret canc: " + retorno);
            if (retorno.trim().equals("1")) {
                System.out.println("Pedido cancelado!");
                novoPedido();
            }
        } else {
            if (!codProd.trim().isEmpty()) {
                String qtde = getQuantidade();
                PedidoItemPojo item = new PedidoItemPojo(Integer.parseInt(codProd.trim()), new BigDecimal(qtde.trim().replace(",", ".")));
                System.out.println(item);
                itens.add(item);
            } else if (microterminal.getValidaLancamentos()) {
                String retorno = microTerminalFactory.getInput(trataLinhasDisplay("Deseja registrar o Pedido\nConfirmar (1=Sim 0=Não):"), "9", input, output);
                System.out.println("Ret regPedido: " + retorno);
                lancaProximo = retorno.trim().equals("0");
            } else {
                lancaProximo = Boolean.FALSE;
            }
        }
        return lancaProximo;
    }

    private String getProduto() throws IOException {
        String retorno = microTerminalFactory.getInput("Cod. Produto: ", "9999999999999", input, output);
        retorno = OUtil.onlyNumber(retorno);
        System.out.println("Ret Prod: " + retorno + "|");
        if (microterminal.getValidaLancamentos()) {
            if (retorno.trim().isEmpty() && itens.isEmpty()) {
                microTerminalFactory.sendMessage(output, "Informe o código do Produto e pressione ENTER");
                retorno = getProduto();
            } else if (!retorno.trim().isEmpty()) {
                String ret = microTerminalFactory.getInput(trataLinhasDisplay("Produto: " + retorno + " - pão de queijo\nConfirmar (1=Sim 0=Não):"), "9", input, output);
                if (ret.trim().equals("0")) {
                    retorno = getProduto();
                }
            } else if (retorno.trim().isEmpty() && !itens.isEmpty()) {
                retorno = null;
            }
        }
        return retorno;
    }

    private String getQuantidade() throws IOException {
        String retorno = microTerminalFactory.getInput("Quantidade: ", "999999", input, output);
        if (microterminal.getValidaLancamentos()) {
            if (retorno == null || retorno.trim().isEmpty()) {
                microTerminalFactory.sendMessage(output, "Informe a quantidade do Produto e pressione ENTER");
                retorno = getQuantidade();
            }
        }
        return retorno;
    }

    private void registraPedido() throws IOException {
        System.out.println("Num Conta: " + conta);
        System.out.println("Atendente: " + atendente);
        System.out.println("=========================");
        System.out.println(" COD                QTDE");
        for (PedidoItemPojo i : itens) {
            System.out.println(" " + i.getCodProduto() + "               " + i.getQuantidade());
        }
        System.out.println("-------------------------");
//        microterminal.setKey(13, output, "9");
//        microterminal.getKey(input, output, "|");
        novoPedido();
    }

    private void novoPedido() throws IOException {
        conta = 0;
        atendente = 0;
        itens = new ArrayList<>();
        System.out.println("Novo Pedido");
        informaConta();
    }

    private String trataLinhasDisplay(String texto) {
        StringBuilder retorno = new StringBuilder();
        int line = 0;
        for (String string : texto.split("\n")) {
            if (string.length() < colunas && line == 0) {
                for (int i = 0; string.length() < colunas; i++) {
                    string = string + " ";
                }
            }
            retorno.append(string);
            line++;
        }
        return retorno.toString();
    }

    private void trataDisplayByModeloTerminal(MicroterminaEnum modelo) {
        switch (modelo) {
            case WILBOR_16:
                colunas = 16;
                microTerminalFactory = new Wilbor();
                break;
            case WILBOR_44:
                colunas = 40;
                microTerminalFactory = new Wilbor();
                break;
            case MT_720:
                colunas = 20;
                microTerminalFactory = new Gertec();
                break;
            case MT_740:
                colunas = 20;
                microTerminalFactory = new Gertec();
                break;
            default:
                System.out.println("O modelo de terminal " + modelo.name() + " não está Homologado!!!");
                break;
        }
    }

    private void informaConta() throws IOException {
        conta = 0;
        while (conta == 0) {
            conta = Integer.parseInt(getConta());
        }
        informaAtendente();
    }

    private void informaAtendente() throws IOException {
        atendente = 0;
        atendente = Integer.parseInt(getAtendente());
        if (atendente == 0) {
            informaConta();
        } else {
            informaProdutos();
        }
    }

    private void informaProdutos() throws IOException {
        itens = getListPedidoItens();
        registraPedido();
    }

}
