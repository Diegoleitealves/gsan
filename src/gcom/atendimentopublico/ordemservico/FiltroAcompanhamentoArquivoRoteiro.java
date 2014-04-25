package gcom.atendimentopublico.ordemservico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Descri��o da classe
 * 
 * @author COMPESA
 */
public class FiltroAcompanhamentoArquivoRoteiro extends Filtro implements
		Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Construtor da classe FiltroHidrometroLocalArmazenagem
	 * 
	 * @param campoOrderBy
	 *            Descri��o do par�metro
	 */
	public FiltroAcompanhamentoArquivoRoteiro(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Construtor da classe FiltroHidrometroLocalArmazenagem
	 */
	public FiltroAcompanhamentoArquivoRoteiro() {
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";
	
	/**
	 * Description of the Field
	 */
	public final static String SITUACAO = "situacaoTransmissaoLeitura";


}
