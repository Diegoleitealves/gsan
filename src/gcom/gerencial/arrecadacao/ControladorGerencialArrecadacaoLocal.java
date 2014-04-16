package gcom.gerencial.arrecadacao;

import gcom.util.ControladorException;

/**
 * 
 * 
 * @author Ivan S�rgio
 * @created 11/05/2007
 */
public interface ControladorGerencialArrecadacaoLocal
		extends
			javax.ejb.EJBLocalObject {
	
	
	/**
	 * M�todo que gera o resumo da Arrecadacao
	 *
	 * [UC0551] - Gerar Resumo da Arrecadacao Agua/Esgoto
	 * 			- Gerar Resumo da Arrecadacao Outros
	 * 			- Gerar Resumo da Arrecadacao Credito
	 *
	 * @author Ivan S�rgio
	 * @date 17/05/2007
	 */
	public void gerarResumoArrecadacao(
			int idLocalidade, 
			int anoMesReferenciaArrecadacao,
			int idFuncionalidadeIniciada) throws ControladorException;
	
	/**
	 * M�todo que gera o resumo da Arrecadacao Por Ano
	 *
	 * @author Fernando Fontelles Filho
	 * @date 02/06/2010 
	 *
	 */
	public void gerarResumoArrecadacaoPorAno(
			int idLocalidade, 
			int anoMesReferenciaArrecadacao,
			int idFuncionalidadeIniciada) throws ControladorException;
}
