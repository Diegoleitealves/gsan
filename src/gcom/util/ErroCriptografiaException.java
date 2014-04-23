package gcom.util;

/**
 * Exce��o lan�ada quando ocorre um erro de criptografia
 * 
 * @author Rodrigo Silveira
 */
public class ErroCriptografiaException extends Exception {
	private static final long serialVersionUID = 1L;
    private Exception excecaoInterna;

    private int codigoErro;

    /**
     * Construtor da classe ErroCriptografiaException
     * 
     * @param pMensagem
     *            Descri��o do par�metro
     * @param pExcecaoInterna
     *            Descri��o do par�metro
     * @param pCodigoErro
     *            Descri��o do par�metro
     */
    public ErroCriptografiaException(String pMensagem,
            Exception pExcecaoInterna, int pCodigoErro) {
        super(pMensagem);
        excecaoInterna = pExcecaoInterna;
        codigoErro = pCodigoErro;
    }

    /**
     * Construtor da classe ErroCriptografiaException
     * 
     * @param pMensagem
     *            Descri��o do par�metro
     */
    public ErroCriptografiaException(String pMensagem) {
        super(pMensagem);
    }

    /**
     * Retorna o valor de codigoErro
     * 
     * @return O valor de codigoErro
     */
    public int getCodigoErro() {
        return codigoErro;
    }

    /**
     * Retorna o valor de excecaoInterna
     * 
     * @return O valor de excecaoInterna
     */
    public Exception getExcecaoInterna() {
        return excecaoInterna;
    }

}
