package gcom.util;

/**
 * Exce��o representa uma situa��o onde o registro que ser� deletado n�o existe mais no sistema.
 * 
 * @author rodrigo
 */
public class RemocaoRegistroNaoExistenteException extends
		ErroRepositorioException {
	private static final long serialVersionUID = 1L;
	private Throwable excecaoCausa;

	private long timestamp;

	public RemocaoRegistroNaoExistenteException() {
	}

	public RemocaoRegistroNaoExistenteException(String mensagem) {
		super(mensagem);
	}

	public RemocaoRegistroNaoExistenteException(Exception excecaoCausa) {
		this.excecaoCausa = excecaoCausa;

	}

	public RemocaoRegistroNaoExistenteException(Exception excecaoCausa,
			String mensagem) {
		super(mensagem);
		this.excecaoCausa = excecaoCausa;

	}

	public long getTimestamp() {
		return timestamp;
	}

	public Throwable getExcecaoCausa() {
		return this.excecaoCausa;
	}

}
