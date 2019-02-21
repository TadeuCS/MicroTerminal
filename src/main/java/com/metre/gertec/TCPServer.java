package com.metre.gertec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    static ServerSocket servidor = null;
    static Socket socket;
    DecimalToHex decimalParaHexa = new DecimalToHex();

    private static DataInputStream in;
    private static DataOutputStream out;

    public void iniciarComunicacao() {
        if (servidor == null) {
            try {
                System.out.println("Inicializando Servidor !");
                servidor = new ServerSocket(6550);
                System.out.println("Socket do Servidor Inicializado na porta 6550 !");
                socket = servidor.accept();
                System.out.println("Aguardando Resposta do Microterminal !");
                //Timeout que fica associado a leitura de respostas. Se resposta demorar mais que 5 segundos haverá exception.
                socket.setSoTimeout(5000);
                System.out.println("Resposta Recebida com Sucesso ! ");
                System.out.println("");
                //Define que a variavel in é responsável por receber respostas por meio do socket
                in = new DataInputStream(socket.getInputStream());
                //Define que a variavel out é responsável por enviar comandos por meio do socket
                out = new DataOutputStream(socket.getOutputStream());

                wGetIdentify();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    } //Fim de iniciarComunicacao

    public void wGetIdentify() throws IOException {

        /*Envio de comando wGetIdentify para o Servidor. O Comando wGetIdentify deve ser enviado antes de qualquer outro
		comando para o terminal, caso contrário nenhum comando ira funcionar. */
        byte[] header = new byte[4]; //Comando mais tamanho dos argumentos que é igual a 0.

        /*Como o tamanho é igual a 0 não são enviados argumentos. Caso sejam enviados os bytes destes argumentos podem
		 *interferir na resposta das chamadas de comandos futuros. */
        header[0] = 0x03;
        header[1] = 0;
        out.write(header, 0, 4);	//Envio do comando out.write estabelecendo como parÃ¢metro o comando, inÃ­cio e tamanho da escrita.
        out.flush(); //O comando flush efetiva as escritas do out.write caso as mesmas nÃ£o tenham sido enviadas ainda.

        System.out.println("wGetIdentify Enviado !");

        //ID da Resposta		
        byte[] byteID = new byte[2];
        in.read(byteID, 0, 2);
        //Converte os Bytes do IDconvertido para LittleEndian
        byte[] byteIDconvertido = getLittleEndian(byteID);
        //Converte os Bytes em LittleEndian do IDconvertido para Hexadecimal
        String bytesID = bytesToHex(byteIDconvertido);

        //Tamanho do Argumento			
        byte[] byteTamArg = new byte[2];
        in.read(byteTamArg, 0, 2);
        //Converte os Bytes do IDconvertido para LittleEndian
        byte[] byteTamArgConvertido = getLittleEndian(byteTamArg);
        //Converte os Bytes em LittleEndian para Hexadecimal
        String bytesArg = bytesToHex(byteTamArgConvertido);

        //Tratamento do Argumento
        byte[] byteArgumentos = new byte[2];
        in.read(byteArgumentos, 0, 2);
        //Converte os Argumento em Bytes para LittleEndian
        byte[] byteArgumentosConvertido = getLittleEndian(byteArgumentos);
        //Converte os Argumentos em LittleEndian para Hexadecimal
        String bytesArgumentos = bytesToHex(byteArgumentosConvertido);

        //Imprime as respostas do comando 0x03 (0x04)
        System.out.println("ID: " + bytesID);
        System.out.println("Tamanho: " + bytesArg);
        System.out.println("Argumentos: " + bytesArgumentos);
        System.out.println("");
        System.out.println("");

    } //Fim de wGetIdenfity

    public void getEnableSerial(int portaCOM) {
        try {

            //Envio do comando vGetEnableSerial
            byte[] header = new byte[5]; //Definição do header de tamanho 5 que corresponde ao comando mais o seu argumento mais tamanho dos argumentos que é igual a 0.
            header[0] = 0x3b; //Comando
            header[1] = 0; //Byte Separador, não é tamanho do argumento
            header[2] = 1; //Tamanho do Argumento
            header[3] = 0; //Byte Separador
            header[4] = (byte) portaCOM; //Porta a ser verificada (COM = COM-1)
            out.write(header, 0, 5);
            out.flush();
            System.out.println("Comando getEnableSerial enviado para o terminal !");
            System.out.println("Available: " + in.available()); //Bytes disponíveis para leitura.			
            //Recebimento dos valores de Resposta		 
            byte[] bytesIDResposta = new byte[2];
            //Leitura dos Bytes do ID do comando de resposta que deve ser 0x3c
            in.read(bytesIDResposta, 0, 2);
            //Conversão de valores para LittleEndian
            byte[] bytesIDRespostaLittleEndian = getLittleEndian(bytesIDResposta);
            //Conversão de valores para Hexadecimal
            String id = bytesToHex(bytesIDRespostaLittleEndian);
            System.out.println("ID Resposta: " + id);

            byte byteSeparador[] = new byte[1];
            in.read(byteSeparador, 0, 1);

            //Leitura de valor da porta COM informada
            byte bytesCOM[] = new byte[2];
            in.read(bytesCOM, 0, 2);
            byte bytesCOMLittleEndian[] = getLittleEndian(bytesCOM);
            String com = bytesToHex(bytesCOMLittleEndian);
            System.out.println("Porta COM: " + com);

            //Recebimento da resposta habilitado ou desabilitado
            byte[] bytesBoolean = new byte[4];
            in.read(bytesBoolean, 0, 4);
            byte[] bytesBooleanLittleEndian = getLittleEndian(bytesBoolean);
            String booleanHexa = bytesToHex(bytesBooleanLittleEndian);
            System.out.println("Bytes Boolean: " + booleanHexa);
            System.out.println("0001 = True / 0000 = False");
            System.out.println("Available: " + in.available()); //Verificação de que não existe nenhum byte pendente para leitura.
            System.out.println("");
            System.out.println("");

        } catch (IOException e) {
            e.printStackTrace();
        }

    } //Fim de getEnableSerial

    public void setEnableSerial(int portaCOM, int habilitar) throws Exception {

        byte byteHabilitar = 00000000; //Valor true padrão

        if (habilitar == 1) {
            byteHabilitar = 00000001; //Valor false identificado pelo Microterminal			
        }

        try {
            //Envio do comando vSetEnableSerial
            byte[] header = new byte[9]; //DefiniÃ§Ã£o do header de tamanho 5 que corresponde ao comando mais o seu argumento  mais tamanho dos argumentos que é igual a 0.
            header[0] = 0x39; //Comando vSetEnableSerial. Utiliza o argumento ARG_TYPE_BOOL
            header[1] = 0; //Byte Separador
            header[2] = 5; //Tamanho do argumento
            header[3] = 0; //Byte Separador
            header[4] = (byte) portaCOM; //Porta COM a ser Habilitada/Desativada (COM = COM-1)
            header[5] = byteHabilitar; //Comando True "00000000" / False "00000001"			
            out.write(header, 0, 9);
            out.flush();

            System.out.println("Comando setEnableSerial Enviado !!!");

            //Recebimento dos valores de Resposta		 
            byte[] bytesResposta = new byte[2];
            in.read(bytesResposta, 0, 2);
            //Conversão de valores para LittleEndian
            byte[] bytesRespostaLittleEndian = new byte[2];
            bytesRespostaLittleEndian = getLittleEndian(bytesResposta);
            //Conversão de valores para Hexadecimal
            String resposta = bytesToHex(bytesRespostaLittleEndian);
            System.out.println("Resposta: " + resposta);

            byte byteSeparador[] = new byte[2];
            in.read(byteSeparador, 0, 2);

            System.out.println("Available: " + in.available()); //VerificaÃ§Ã£o de que nÃ£o existe nenhum byte pendente para leitura.
            System.out.println("");
            System.out.println("");

        } catch (IOException e) {
            e.printStackTrace();
        }

    } //Fim de setEnableSerial

    public void setSetupSerial(int comPort, String baud, String bits, String parity, String stops, String handshaking) {

        String hexBaud = decimalParaHexa.decToHex(Integer.parseInt(baud));

        String subStringhexBaud02 = hexBaud.substring(0, 2);
        String subStringhexBaud24 = hexBaud.substring(2, 4);
        String subStringhexBaud46 = hexBaud.substring(4, 6);
        String subStringhexBaud68 = hexBaud.substring(6, 8);

        //Transformação de valores de Hexa para Decimal		
        int decimalBaud02 = hex2decimal(subStringhexBaud02);
        int decimalBaud24 = hex2decimal(subStringhexBaud24);
        int decimalBaud46 = hex2decimal(subStringhexBaud46);
        int decimalBaud68 = hex2decimal(subStringhexBaud68);

        /*Não é possível converter um valor em hexa para bytes pois o cast (byte) já faz essa conversão. O correto é 
		 *informar o valor da substring convertido para base decimal e deixar que o cast faça a conversão para hexadecimal.
		 *Exemplo: Um byte não pode receber o valor: Integer.parseInt(subStringhexBaud46) levando em conta que a subString
		 *seja um valor Hexa, como C2 E3 etc.
		 **/
        try {

            //Envio do comando vSetSetupSerial 0x41 responde 0x42 + bool indicando resultado
            byte[] comando = new byte[20];
            comando[0] = 0x41; //Bytes Comando
            comando[1] = 0;
            comando[2] = 0x0c; //Byte separador 0c
            comando[3] = 0;
            comando[4] = (byte) comPort; //Porta COM
            comando[5] = (byte) decimalBaud68;
            comando[6] = (byte) decimalBaud46;
            comando[7] = (byte) decimalBaud24;
            comando[8] = (byte) decimalBaud02;
            comando[9] = Byte.valueOf(bits); //Bits por Dado - Vá¡ria de 5 a 8
            comando[10] = 0;
            comando[11] = Byte.valueOf(parity); //Bits de Paridade - 0 Nenhuma / 1 Impar / 2 Par / 3 Mark / 4 Space
            comando[12] = 0;
            comando[13] = Byte.valueOf(stops); //Bits de Paradas
            comando[14] = 0;
            comando[15] = Byte.valueOf(handshaking); //Bits de Handshaking - 0 Sem Handshaking / 1 Com Handshaking
            comando[16] = 0x27;
            //O Restante é preenchido por "0"
            out.write(comando, 0, 20);
            out.flush();
            System.out.println("Comando setSetupSerial enviado !!!");

            //Recebimento dos valores de Resposta		 
            byte[] bytesRespostaComando = new byte[2];
            in.read(bytesRespostaComando, 0, 2);
            //ConversÃ£o de valores para LittleEndian
            byte[] bytesRespostaComandoLittleEndian = new byte[2];
            bytesRespostaComandoLittleEndian = getLittleEndian(bytesRespostaComando);
            //ConversÃ£o de valores para Hexadecimal
            String respostaComando = bytesToHex(bytesRespostaComandoLittleEndian);
            System.out.println("Resposta: " + respostaComando);

            //Bytes Separador
            byte[] bytesSeparador = new byte[2];
            in.read(bytesSeparador, 0, 2);

            //Resposta Verdadeiro ou Falso
            byte[] bytesStatus = new byte[4];
            in.read(bytesStatus, 0, 4);
            byte[] bytesRespostaLittleEndian = new byte[4];
            bytesRespostaLittleEndian = getLittleEndian(bytesStatus);
            //Conversão de valores para Hexadecimal
            String resposta = bytesToHex(bytesRespostaLittleEndian);
            System.out.println("Status: " + resposta);
            System.out.println("0001 = True / 0000 = False");

            byte[] bytesMicroterminal = new byte[4];
            in.read(bytesMicroterminal, 0, 4);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    } //Fim de setSetupSerial

    public void getSetupSerial(int comPort) {

        try {
            byte[] comando = new byte[5];
            comando[0] = 0x43; //Bytes vGetSetupSerial. Retorna 0x44h + ARG_COM_SETUP_SERIAL
            comando[1] = 0;
            comando[2] = 1;
            comando[3] = 0;
            comando[4] = (byte) comPort; //Porta COM
            out.write(comando, 0, 5);
            System.out.println("Comando vGetSetupSerial enviado ! ");
            System.out.println("OBS: Valores recebidos estÃ£o em Hexadecimal !");

            //Recebimento dos valores de Resposta		 
            byte[] bytesRespostaComando = new byte[2];
            in.read(bytesRespostaComando, 0, 2);
            //ConversÃ£o de valores para LittleEndian
            byte[] bytesRespostaComandoLittleEndian = new byte[2];
            bytesRespostaComandoLittleEndian = getLittleEndian(bytesRespostaComando);
            //ConversÃ£o de valores para Hexadecimal
            String respostaComando = bytesToHex(bytesRespostaComandoLittleEndian);
            System.out.println("Resposta: " + respostaComando);

            byte[] bytesSeparador = new byte[1];
            in.read(bytesSeparador, 0, 1); //0c

            byte bytesCOM[] = new byte[2];
            //Leitura de valor da porta COM informada
            in.read(bytesCOM, 0, 2);
            byte bytesCOMLittleEndian[] = getLittleEndian(bytesCOM);
            String com = bytesToHex(bytesCOMLittleEndian);
            System.out.println("Porta COM: " + com);

            //Leitura da velocidade
            byte[] bytesBaud = new byte[4];
            in.read(bytesBaud, 0, 4);
            byte[] bytesRespostaLittleEndian = new byte[4];
            bytesRespostaLittleEndian = getLittleEndian(bytesBaud);
            //ConversÃ£o de valores para Hexadecimal
            String respostaBaud = bytesToHex(bytesRespostaLittleEndian);
            System.out.println("Baud: " + respostaBaud);

            //Leitura dos Bits
            byte[] bytesBits = new byte[2];
            in.read(bytesBits, 0, 2);
            byte bytesBitsLittleEndian[] = getLittleEndian(bytesBits);
            String bits = bytesToHex(bytesBitsLittleEndian);
            System.out.println("Bits: " + bits + " (5 a 8)");

            //Leitura da Paridade
            byte[] bytesParity = new byte[2];
            in.read(bytesParity, 0, 2);
            byte bytesParityLittleEndian[] = getLittleEndian(bytesParity);
            String parity = bytesToHex(bytesParityLittleEndian);
            System.out.println("Parity: " + parity + " (0 a 4)");

            //Leitura dos Bits de Parada
            byte[] bytesStops = new byte[2];
            in.read(bytesStops, 0, 2);
            byte bytesStopsLittleEndian[] = getLittleEndian(bytesStops);
            String stops = bytesToHex(bytesStopsLittleEndian);
            System.out.println("Stops: " + stops + "(1 ou 2)");

            //Leitura do Handshaking
            byte[] bytesHandshaking = new byte[2];
            in.read(bytesHandshaking, 0, 2);
            byte bytesHandshakingLittleEndian[] = getLittleEndian(bytesHandshaking);
            String handshaking = bytesToHex(bytesHandshakingLittleEndian);
            System.out.println("Handshaking: " + handshaking + "(0 ou 1)");

        } catch (IOException e) {
            e.printStackTrace();
        }

    } //Fim de getSetupSerial

    public void sendBinSerial(int portaCOM) throws InterruptedException {

        try {
            byte[] comando = new byte[262];
            comando[0] = 0x3f;
            comando[1] = 0;
            comando[2] = 2;
            comando[3] = 1;
            comando[4] = (byte) portaCOM;
            comando[5] = 5; //Byte correspondente a solicitação de peso da balança.
            comando[260] = 0;
            comando[261] = 1;	//01		
            out.write(comando, 0, 262);
            out.flush();
        } catch (IOException e) {
            System.out.println("Erro ao enviar o comando de solicitaçãode peso ! Reincie o microterminal e tente novamente.");
            e.printStackTrace();
        }

        /* Ao mandar este comando com o byte de solicitação de peso da balança (5) o microterminal responde
		 * automaticamente com o comando 0x3d que corresponde ao getBinSerial com as informações de peso
		 * da balança em Hexadecimal. Para obter o peso final recebido o comandoGetBin utiliza um vetor de bytes
		 * sendo necessário apenas tratar os valores recebidos.
         */
        try {
            //Recebimento dos valores de Resposta		 
            byte[] bytesRespostaComando = new byte[2];
            in.read(bytesRespostaComando, 0, 2);
            //Conversão de valores para LittleEndian
            byte[] bytesRespostaComandoLittleEndian = new byte[2];
            bytesRespostaComandoLittleEndian = getLittleEndian(bytesRespostaComando);
            //Conversão de valores para Hexadecimal
            String respostaComando = bytesToHex(bytesRespostaComandoLittleEndian);
            System.out.println("Resposta: " + respostaComando);

            byte[] bytesMT = new byte[2];
            in.readFully(bytesMT, 0, 2); //Bytes 04 00			

            //Resposta comando Booleana
            byte[] bytesRespostaBooleana = new byte[4];
            in.read(bytesRespostaBooleana, 0, 4);
            byte[] bytesRespostaBooleanaLittleEndian = new byte[4];
            bytesRespostaBooleanaLittleEndian = getLittleEndian(bytesRespostaBooleana);
            String respostaBooleana = bytesToHex(bytesRespostaBooleanaLittleEndian);
            System.out.println("Resposta Booleana: " + respostaBooleana);

            /*Valores do comandoGetBin são recebidos de uma vez em um vetor de bytes pois ao ler proporcionalmente
			 *cada valor não são recebidos valores. Por os valores estarem sendo armazenados em um vetor de bytes
			 *os valores são convertidos para base decimal, sendo necessário então converter os valores do peso para
			 *ASCII.
             */
            System.out.println("Available: " + in.available());
            byte[] comandoGetBin = new byte[262];
            System.out.println("Iniciando leitura de valores...");
            in.read(comandoGetBin, 0, 262);

            int posByteInicializador = 5;
            int i = posByteInicializador + 1;
            while (comandoGetBin[i] != 3) {
                System.out.println("Bytes Peso[" + i + "] = " + comandoGetBin[i] + " - ASCII " + (char) comandoGetBin[i]);
                i++;
                if (i == comandoGetBin.length) //Se i for igual ao tamanho de comandoGetBin o loop sai de execução
                {
                    System.out.println("Erro na leitura dos dados ! Reinicie a balança sem nenhum peso e tente novamente.");
                    break;
                }

            }
            System.out.println("Tamanho lido: " + comandoGetBin[comandoGetBin.length - 1]);

        } catch (IOException e) {
            System.out.println("Erro ao ler os dados do comandoGetBin. Verifique a parametrização serial da balança e tente novamente !");
            Thread.sleep(2000);
            //e.printStackTrace();

        }

    } //Fim de sendBinSerial	

    //Funções auxiliares	
    public static int hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }

    private static byte[] getLittleEndian(byte[] dados) {
        byte[] ret = new byte[dados.length];
        int j = 0;
        for (int i = dados.length - 1; i >= 0; i--) {
            ret[j++] = dados[i];
        }
        return ret;
    } //Fim de getLittleEndian	

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    } //Fim de bytesToHex

    public TCPServer() {
        iniciarComunicacao();
    }

}
