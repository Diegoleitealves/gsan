package gcom.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Esta classe � usada para guardar todos os valores que devem ser constantes
 * durante a execu��o do sistema. O carregamento das constantes � feita
 * automaticamente durante o classloader desta classe. Ap�s esta inicializa��o,
 * que � feita antes de qualquer acesso a classe, as constantes podem ser
 * consultadas de forma muito simples. Para o funcionamento desejado o usu�rio
 * deve p�r um arquivo chamado application.properties no mesmo diret�rio onde
 * localiza-se esta classe, este arquivo deve conter todos os nomes das
 * constantes do sistemas juntamente com os seus valores como pode ser visto
 * abaixo.
 * 
 * <pre>
 * 
 *  
 *  # Arquivo de Configura��es das constantes do sistema
 *  SERVER = www.compesa.com.br
 *  PORT = 8080
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *   @author   rodrigo
 * 
 * 
 */

public class ConstantesAplicacao {

    // Nome do Arquivo de propriedades
    private final static String NOME_ARQUIVO_PROPRIEDADES;

    // guarda as constantes contidas no application.properties juntamente com
    // seus valores
    private static Properties propriedades = null;

    // inicializa��o est�tica
    static {
        // Nome do Arquivo de propriedades
        NOME_ARQUIVO_PROPRIEDADES = "/application.properties";
        loadResources();
    }

    /**
     * Description of the Field
     */
    public static String CAMINHO_SISTEMA = get("caminho.sistema");

    /**
     * Description of the Field
     */
    public static String CAMINHO_IMAGENS = get("caminho.imagens");

    /**
     * Este m�todo retorna o valor da constante passada como par�metro.
     * 
     * @param key
     *            Nome da constante
     * @return Descri��o do retorno
     */

    public static String get(String key) {
        return propriedades.getProperty(key);
    }
    
    /**
     * Este m�todo retorna o valor da constante passada como par�metro.
     * 
     * @param key
     *            Nome da constante
     * @param paramtros lista de parametros a serem substuidos na string
     * @return Descri��o do retorno
     */

    public static String get(String key, String... parametros) {
        String retorno = propriedades.getProperty(key);
        
        int i = 0;
        
        for ( String parametro : parametros ){        	
        	retorno = retorno.replace( "{" + i + "}", parametro );        	
        	++i;
        }
        
        return retorno;        
    }
    

    /**
     * Carrega o arquivo de properties do sistema
     */
    private static void loadResources() {
        propriedades = new Properties();

        InputStream stream;

        try {

            ClassLoader classLoader = ClassLoader.getSystemClassLoader();

            stream = classLoader.getResourceAsStream(NOME_ARQUIVO_PROPRIEDADES);

            // if system class loader not found try the this class classLoader
            if (stream == null) {
                stream = gcom.util.ConstantesAplicacao.class.getClassLoader()
                        .getResourceAsStream(NOME_ARQUIVO_PROPRIEDADES);
            }

            if (stream == null) {
                stream = gcom.util.ConstantesAplicacao.class
                        .getResourceAsStream(NOME_ARQUIVO_PROPRIEDADES);
            }

            propriedades.load(stream);

        } catch (IOException e) {
            e.printStackTrace();
            System.err
                    .println("Nao foi possivel localizar o arquivo de propriedades. Certifique-se "
                            + "de que o arquivo "
                            + NOME_ARQUIVO_PROPRIEDADES
                            + " esteja na raiz do CLASSPATH");

        }

    }

    /**
     * < <Descri��o do m�todo>>
     * 
     * @param args
     *            Descri��o do par�metro
     * @exception Exception
     *                Descri��o da exce��o
     */
    public static void main(String args[]) throws Exception {

        System.out.println(ConstantesAplicacao.CAMINHO_SISTEMA);

    }

}
