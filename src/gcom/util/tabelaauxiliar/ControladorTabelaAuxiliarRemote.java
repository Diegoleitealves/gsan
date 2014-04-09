package gcom.util.tabelaauxiliar;

import gcom.util.filtro.Filtro;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * < <Descri��o da Interface>>
 * 
 * @author rodrigo
 */
public interface ControladorTabelaAuxiliarRemote extends javax.ejb.EJBObject {
	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param tabelaAuxiliar
	 *            Descri��o do par�metro
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public void atualizarTabelaAuxiliar(TabelaAuxiliarAbstrata tabelaAuxiliar)
			throws RemoteException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param filtro
	 *            Descri��o do par�metro
	 * @param nomePacoteObjeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public Collection pesquisarTeste(Filtro filtro, String nomePacoteObjeto)
			throws RemoteException;
}
