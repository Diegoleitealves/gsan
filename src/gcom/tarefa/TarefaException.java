package gcom.tarefa;


/**
 * Representa um problema no n�vel do Action
 * 
 * @author thiago
 * @date 24/01/2006
 */
public class TarefaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    private String parametroMensagem;

    /**
     * Construtor da classe TarefaException
     * 
     * @param mensagem
     *            Chave do erro que aconteceu no controlador(mensagem obtida num
     *            arquivo de properties)
     * @param excecaoCausa
     *            Exce��o que originou o problema
     */
    public TarefaException(String mensagem, Exception excecaoCausa) {
        super(mensagem, excecaoCausa);

    }

    /**
     * Construtor da classe TarefaException
     * 
     * @param mensagem
     *            Descri��o do par�metro
     */
    public TarefaException(String mensagem) {
        super(mensagem, null);
    }

    /**
     * Construtor da classe TarefaException
     * 
     * @param mensagem
     *            Descri��o do par�metro
     * @param excecaoCausa
     *            Descri��o do par�metro
     * @param parametroMensagem
     *            Descri��o do par�metro
     */
    public TarefaException(String mensagem, Exception excecaoCausa,
            String parametroMensagem) {
        super(mensagem, excecaoCausa);
        this.parametroMensagem = parametroMensagem;

    }

	public TarefaException(String mensagem, String parametroMensagem) {
		super(mensagem);
		this.parametroMensagem = parametroMensagem;

	}

    /**
     * Retorna o valor de parametroMensagem
     * 
     * @return O valor de parametroMensagem
     */
    public String getParametroMensagem() {
        return parametroMensagem;
    }

}
