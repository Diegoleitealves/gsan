package gcom.micromedicao.leitura;

import gcom.util.ErroRepositorioException;

/**
 * Interface para o reposit�rio de funcion�rio
 * 
 * @author S�vio Luiz
 * @created 13 de Janeiro de 2006
 */

public interface IRepositorioLeitura {

	
	public Integer verificarExistenciaLeituraAnormalidade(Integer idLeituraAnormalidade) throws ErroRepositorioException;

}
