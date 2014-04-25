package gcom.cadastro.localidade;

import gcom.util.ControladorException;

import java.rmi.RemoteException;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public interface ControladorLocalidadeRemote extends javax.ejb.EJBObject {
	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param setorComercial
	 *            Descri��o do par�metro
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public void atualizarSetorComercial(SetorComercial setorComercial)
			throws RemoteException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param localidade
	 *            Descri��o do par�metro
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public void atualizarLocalidade(Localidade localidade)
			throws RemoteException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param quadra
	 *            Descri��o do par�metro
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public void atualizarQuadra(Quadra quadra) throws RemoteException;

	// public Collection pesquisarQuadraRelatorio(Quadra quadraParametros)
	// throws RemoteException;
	
	
	/**
	 * Inseri um objeto do tipo setor comercial no BD
	 * @param setorComercial
	 * @return ID gerado
	 * @throws ControladorException
	 */
	public Integer inserirSetorComercial(SetorComercial setorComercial) throws RemoteException;
	
//	/**
//	 * < <Descri��o do m�todo>>
//	 * 
//	 * @param gerencia Regional
//	 *            Descri��o do par�metro
//	 * @exception RemoteException
//	 *                Descri��o da exce��o
//	 */
//	public void atualizarGerenciaRegional(GerenciaRegional gerenciaRegional)
//			throws RemoteException;
//
}
