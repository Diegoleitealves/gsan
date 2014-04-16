package gcom.cadastro.geografico;

import gcom.util.ErroRepositorioException;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Declara��o p�blica de servi�os do Session Bean de ControladorCliente
 * 
 * @author S�vio Luiz
 * @created 25 de Abril de 2005
 */
public interface ControladorGeograficoRemote extends javax.ejb.EJBObject {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairro
	 *            Descri��o do par�metro
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public void atualizarBairro(Bairro bairro) throws RemoteException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param codigoSetorComercial
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public Collection pesquisarMunicipoPeloSetorComercial(
			String codigoSetorComercial, String idMunicipio)
			throws ErroRepositorioException, RemoteException;

}
