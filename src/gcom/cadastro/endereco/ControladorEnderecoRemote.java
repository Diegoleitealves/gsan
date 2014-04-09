package gcom.cadastro.endereco;

import gcom.cadastro.geografico.Bairro;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Declara��o p�blica de servi�os do Session Bean de ControladorEndere�o
 * 
 * @author S�vio Luiz
 * @created 23 de Agosto de 2005
 */
public interface ControladorEnderecoRemote extends javax.ejb.EJBObject {

	/**
	 * inseri um logradouro na base
	 * 
	 * @param logradouro
	 *            Description of the Parameter
	 * @param bairro
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @exception RemoteException
	 *                Description of the Exception
	 */
	public Integer inserirLogradouro(Logradouro logradouro, Bairro bairro)
			throws RemoteException;

	/**
	 * inseri os ceps importados
	 * 
	 * @param cepsImportados
	 *            Description of the Parameter
	 */
	public void inserirCepImportados(Collection cepsImportados)
			throws RemoteException;

}
