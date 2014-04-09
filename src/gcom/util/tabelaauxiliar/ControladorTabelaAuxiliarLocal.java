package gcom.util.tabelaauxiliar;

import gcom.util.ControladorException;
import gcom.util.filtro.Filtro;

import java.util.Collection;

/**
 * < <Descri��o da Interface>>
 * 
 * @author rodrigo
 */
public interface ControladorTabelaAuxiliarLocal extends
		javax.ejb.EJBLocalObject {
	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param tabelaAuxiliar
	 *            Descri��o do par�metro
	 */
	public void atualizarTabelaAuxiliar(TabelaAuxiliarAbstrata tabelaAuxiliar)
			throws ControladorException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param filtro
	 *            Descri��o do par�metro
	 * @param nomePacoteObjeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public Collection pesquisarTeste(Filtro filtro, String nomePacoteObjeto)
			throws ControladorException;
}
