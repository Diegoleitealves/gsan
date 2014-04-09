package gcom.micromedicao;

import gcom.cadastro.imovel.FiltroImovelSubCategoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.bean.ImovelCobrarDoacaoHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.FaturamentoGrupo;
import gcom.micromedicao.consumo.FiltroConsumoHistorico;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.relatorio.micromedicao.FiltrarRelatorioAnormalidadeLeituraPeriodoHelper;
import gcom.relatorio.micromedicao.RelatorioAnormalidadeLeituraPeriodoBean;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 * < <Descri��o da Interface>>
 * 
 * @author Administrador
 * @created 13 de Setembro de 2005
 */
public interface ControladorMicromedicaoRemote extends javax.ejb.EJBObject {
	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param faturamentoGrupo
	 *            Descri��o do par�metro
	 * @param sistemaParametro
	 *            Descri��o do par�metro
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public void consistirLeiturasCalcularConsumos(
			FaturamentoGrupo faturamentoGrupo, SistemaParametro sistemaParametro)
			throws RemoteException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param sistemaParametro
	 *            Descri��o do par�metro
	 * @param medicaoTipo
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception RemoteException
	 *                Descri��o da exce��o
	 */
	public int[] obterConsumoMedioHidrometro(Imovel imovel,
			SistemaParametro sistemaParametro, MedicaoTipo medicaoTipo)
			throws RemoteException;

	/**
	 * Description of the Method
	 * 
	 * @param hidrometro
	 *            Description of the Parameter
	 * @exception RemoteException
	 *                Description of the Exception
	 */
	public void atualizarHidrometro(Hidrometro hidrometro)
			throws RemoteException;

	/**
	 * Description of the Method
	 * 
	 * @param hidrometros
	 *            Description of the Parameter
	 * @param hidrometroAtualizado
	 *            Description of the Parameter
	 * @exception RemoteException
	 *                Description of the Exception
	 */
	public void atualizarConjuntoHidrometro(Collection hidrometros,
			Hidrometro hidrometroAtualizado) throws RemoteException;
	
	
	/*
	 * [UC0121] - Filtrar Exce��es de Leituras e Consumos
	 * Fl�vio Leonardo Cavalcanti Cordeiro
	 */
	public Collection filtrarExcecoesLeiturasConsumos(FiltroImovelSubCategoria filtroImovelSubCategoria, 
			FiltroConsumoHistorico filtroConsumoHistorico, 
			FiltroMedicaoHistorico filtroMedicaoHistorico, String qtdEconomias, String consumoMedioMinimo)
			throws RemoteException;
	
	/**
	 * Permite pesquisar im�vel doa��o baseando-se em rotas
	 * [UC0394] Gerar D�bitos a Cobrar de Doa��es
	 * @author  C�sar Ara�jo
	 * @date    05/08/2006
	 * @param   Collection<Rota> rotas - Cole��o de rotas
	 * @return  Collection<ImovelCobrarDoacaoHelper> - Cole��o de ImovelCobrarDoacaoHelper 
	 *          j� com as informa��es necess�rias para registro da cobran�a
	 * @throws  ErroRepositorioException
	 * @throws ControladorException 
	**/ 
	public Collection<ImovelCobrarDoacaoHelper> pesquisarImovelDoacaoPorRota(Collection<Rota> rotas) throws ControladorException;

	/**
	 *[UC0965] - Relatorio de Anormalidade de Leitura por Periodo
	 *
	 *@since 03/11/2009
	 *@author Marlon Patrick
	 */
	public Collection<RelatorioAnormalidadeLeituraPeriodoBean> pesquisarRelatorioAnormalidadeLeituraPeriodo(FiltrarRelatorioAnormalidadeLeituraPeriodoHelper filtro) throws ControladorException;

}
