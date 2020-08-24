package com.sampaio.servico;

import com.sampaio.modelo.Configuracao;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.io.*;

public class ConfiguracaoServico {

    private static final Logger log = Logger.getLogger(ConfiguracaoServico.class);

    private static String NOME_ARQUIVO = "configuracao.xml";
    private static String ALIAS_CONFIGURACAO = "configuracao";
    private static Class<?>[] classesSecurity = new Class[]{Configuracao.class};


    public static Configuracao buscar() {
        log.info("Iniciou buscar()");
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(NOME_ARQUIVO);
        } catch (FileNotFoundException e) {
            log.info(NOME_ARQUIVO + " não encontrado!");
            log.info("Terminou buscar()");
            return null;
        }
        XStream xStream = new XStream();

        XStream.setupDefaultSecurity(xStream);
        xStream.allowTypes(classesSecurity);
        xStream.alias(ALIAS_CONFIGURACAO, Configuracao.class);

        Object fromXML = null;
        try {
            fromXML = xStream.fromXML(inputStream);
        } catch (Exception e) {

        }
        try {
            inputStream.close();
        } catch (Exception e) {
        }
        Configuracao configuracoes = (Configuracao) fromXML;
        log.info("Terminou buscar()");
        return configuracoes;
    }

    public static void salvar(Configuracao configuracao) {
        log.info("Iniciou salvar()");
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[]{"br.com.smartfull.**"});
        xStream.alias(ALIAS_CONFIGURACAO, Configuracao.class);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(NOME_ARQUIVO);
        } catch (FileNotFoundException e) {
        }
        xStream.toXML(configuracao, outputStream);
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        log.info("Terminou salvar()");
    }
}
