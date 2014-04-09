package gcom.util;

/**
 * Representa uma falha quando o usu�rio tenta inserir um registro
 * 
 * @author Rodrigo
 */
public class InsercaoInvalidaException extends ErroRepositorioException {
	private static final long serialVersionUID = 1L;
    private Throwable excecaoCausa;

    private long timestamp;

    /**
     * Construtor da classe InsercaoInvalidaException
     */
    public InsercaoInvalidaException() {
    }

    /**
     * Construtor da classe InsercaoInvalidaException
     * 
     * @param mensagem
     *            Mensagem da exce��o
     */
    public InsercaoInvalidaException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor da classe InsercaoInvalidaException
     * 
     * @param excecaoCausa
     *            Exce��o que originou este erro
     */
    public InsercaoInvalidaException(Exception excecaoCausa) {
        this.excecaoCausa = excecaoCausa;

    }

    /**
     * Construtor da classe InsercaoInvalidaException
     * 
     * @param excecaoCausa
     *            Exce��o que originou este erro
     * @param mensagem
     *            Mensagem da exce��o
     */
    public InsercaoInvalidaException(Exception excecaoCausa, String mensagem) {
        super(mensagem);
        this.excecaoCausa = excecaoCausa;

    }

    /**
     * Retorna o valor de timestamp
     * 
     * @return O valor de timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Retorna o valor de excecaoCausa
     * 
     * @return O valor de excecaoCausa
     */
    public Throwable getExcecaoCausa() {
        return this.excecaoCausa;
    }

}
