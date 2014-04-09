package gcom.cadastro.empresa;

import gcom.util.ErroRepositorioException;

import java.util.Collection;

/**
 * < <Descri��o da Interface>>
 * 
 * @author S�vio Luiz
 */
public interface IRepositorioEmpresa {

	/**
	 * Pesquisa as empresas que ser�o processadas no emitir contas
	 * 
	 * @author S�vio Luiz
	 * @date 09/01/2007
	 * 
	 */
	public Collection pesquisarIdsEmpresa()
			throws ErroRepositorioException;


}
