package gcom.micromedicao.hidrometro;

import gcom.util.ErroRepositorioException;
import gcom.util.filtro.Filtro;

import java.util.Collection;

/**
 * < <Descri��o da Interface>>
 * 
 * @author Administrador
 */
public interface IRepositorioHidrometro {

	public Collection pesquisarHidrometroPorHidrometroMovimentacao(Filtro filtro)
			throws ErroRepositorioException;
	
	/**
	 * [UC0000] - Efetuar Retirada de Hidr�metro
	 * 
	 * Pesquisa todos os campos do Hidrometro e seus relacionamentos obrigat�rios.
	 * @author Thiago Ten�rio
	 * @date 28/09/2006
	 * 
	 * @param idHidrometro
	 * @throws ErroRepositorioException
	 */
	
	public Object[] pesquisarHidrometroPeloId(Integer idHidrometro)
			throws ErroRepositorioException;

}
