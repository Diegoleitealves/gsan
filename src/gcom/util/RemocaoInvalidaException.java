package gcom.util;

/**
 * Exce��o representa uma situa��o onde o registro que ser� deletado possui
 * depend�ncias que n�o podem ser removidas.
 * 
 * @author rodrigo
 */
public class RemocaoInvalidaException extends ErroRepositorioException {
	private static final long serialVersionUID = 1L;
    private Throwable excecaoCausa;

    private long timestamp;

    /**
     * Construtor da classe RemocaoInvalidaException
     */
    public RemocaoInvalidaException() {
    }

    /**
     * Construtor da classe RemocaoInvalidaException
     * 
     * @param mensagem
     *            Mensagem da exce��o
     */
    public RemocaoInvalidaException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor da classe RemocaoInvalidaException
     * 
     * @param excecaoCausa
     *            Exce��o que originou este erro
     */
    public RemocaoInvalidaException(Exception excecaoCausa) {
        this.excecaoCausa = excecaoCausa;

    }

    /**
     * Construtor da classe RemocaoInvalidaException
     * 
     * @param excecaoCausa
     *            Exce��o que originou este erro
     * @param mensagem
     *            Mensagem da exce��o
     */
    public RemocaoInvalidaException(Exception excecaoCausa, String mensagem) {
        super(mensagem);
        this.excecaoCausa = excecaoCausa;

    }

    /**
     * Retorna o timestamp do erro
     * 
     * @return O timestamp do erro
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
