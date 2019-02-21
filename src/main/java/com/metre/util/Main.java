package com.metre.util;

import com.metre.model.Microterminal;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        int port = 1001; //Integer.parseInt(args[0]);
        ArrayList vetorDeTerminais = new ArrayList();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Aguardando conexão na porta: " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                for (int i = 0; i < vetorDeTerminais.size(); i++) {
                    MicroterminalUtils itemDoVetor = (MicroterminalUtils) vetorDeTerminais.get(i);
                    if (socket.getRemoteSocketAddress().toString().equals(itemDoVetor.MeuIP)) {
                        itemDoVetor = null;
                        vetorDeTerminais.remove(i);
                    }
                }
                Microterminal micro = new Microterminal("localhost", "R", "1001", "TCP", Boolean.FALSE, Boolean.TRUE, MicroterminaEnum.WILBOR_44);
                MicroterminalUtils thread = new MicroterminalUtils(micro, socket);
                vetorDeTerminais.add(thread);
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
