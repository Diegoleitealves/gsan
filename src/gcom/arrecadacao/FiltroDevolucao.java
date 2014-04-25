package gcom.arrecadacao;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca de um cliente endereco
 * 
 * @author Rafael Corr�a
 * @created 22 de Abril de 2005
 */

public class FiltroDevolucao extends Filtro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Construtor da classe FiltroClienteEndereco
	 */
	public FiltroDevolucao() {
	}

	/**
	 * Constructor for the FiltroDevolucao object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroDevolucao(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";
	
	/**
	 * Description of the Field
	 */
	public final static String IMOVEL_ID = "imovel.id";
	
	/**
	 * Description of the Field
	 */
	public final static String CLIENTE_ID = "cliente.id";
	
	/**
	 * Description of the Field
	 */
	public final static String AVISO_BANCARIO_ID = "avisoBancario.id";

	/**
	 * Description of the Field
	 */
	public final static String ANO_MES_REFERENCIA_ARRECADACAO = "anoMesReferenciaArrecadacao";
		
	/**
	 * Description of the Field
	 */
	public final static String DATA_DEVOLUCAO = "dataDevolucao";

	/**
	 * Description of the Field
	 */
	public final static String GUIA_DEVOLUCAO_CREDITO_TIPO_ID = "guiaDevolucao.creditoTipo.id";
	
	/**
	 * Description of the Field
	 */
	public final static String GUIA_DEVOLUCAO_DOCUMENTO_TIPO_ID = "guiaDevolucao.documentoTipo.id";
	
	/**
	 * Description of the Field
	 */
	public final static String DEVOLUCAO_SITUACAO_ATUAL_ID = "devolucaoSituacaoAtual.id";

	/**
	 * Description of the Field
	 */	
	public final static String DEVOLUCAO_GUIA_DEVOLUCAO_CONTA_CLIENTE_CONTAS_CLIENTE_RELACAO_TIPO_ID = "guiaDevolucao.conta.clienteContas.clienteRelacaoTipo.id";
	
	/**
	 * Description of the Field
	 */	
	public final static String DEVOLUCAO_GUIA_DEVOLUCAO_CONTA_CLIENTE_CONTAS_CLIENTE_ID = "guiaDevolucao.conta.clienteContas.cliente.id";

	/**
	 * Description of the Field
	 */	
	public final static String DEVOLUCAO_IMOVEL_CLIENTE_IMOVEIS_CLIENTE_RELACAO_TIPO_ID = "imovel.clienteImoveis.clienteRelacaoTipo.id";

	/**
	 * Description of the Field
	 */	
	public final static String DEVOLUCAO_IMOVEL_CLIENTE_IMOVEIS_CLIENTE_ID = "imovel.clienteImoveis.cliente.id";
	
	/**
	 * Description of the Field
	 */	
	public final static String DEVOLUCAO_GUIA_DEVOLUCAO_GUIA_PAGAMENTO_CLIENTE_GUIA_PAGAMENTO_CLIENTE_RELACAO_TIPO_ID = "guiaDevolucao.guiaPagamento.clientesGuiaPagamento.clienteRelacaoTipo.id";

	/**
	 * Description of the Field
	 */	
	public final static String DEVOLUCAO_GUIA_DEVOLUCAO_GUIA_PAGAMENTO_CLIENTE_GUIA_PAGAMENTO_CLIENTE_ID = "guiaDevolucao.guiaPagamento.clientesGuiaPagamento.cliente.id";
	
	/**
	 * Description of the Field
	 */	
	public final static String DEVOLUCAO_GUIA_DEVOLUCAO_GUIA_PAGAMENTO_CLIENTE_GUIA_PAGAMENTO_GUIA_PAGAMENTO_ID = "guiaDevolucao.guiaPagamento.clientesGuiaPagamento.guiaPagamento.id";

	/**
	 * Description of the Field
	 */
	public final static String AVISO_BANCARIO_ARRECADADOR = "avisoBancario.arrecadador";
	
	/**
	 * Description of the Field
	 */
	public final static String DEBITO_TIPO = "debitoTipo.id";

	/**
	 * Description of the Field
	 */
	public final static String DEVOLUCAO_SITUACAO_ATUAL_DESCRICAO = "devolucaoSituacaoAtual.descricaoDevolucaoSituacao";
	
	/**
	 * Description of the Field
	 */
	public final static String DEVOLUCAO_SITUACAO_ANTERIOR_DESCRICAO = "devolucaoSituacaoAnterior.descricaoDevolucaoSituacao";
	
	/**
	 * Description of the Field
	 */
	public final static String GUIA_DEVOLUCAO_ID = "guiaDevolucao.id";
	
	/**
	 * Description of the Field
	 */
	public final static String GUIA_DEVOLUCAO_CONTA_ID = "guiaDevolucao.conta.id";
	
	/**
	 * Description of the Field
	 */
	public final static String GUIA_DEVOLUCAO_GUIA_PAGAMENTO_ID = "guiaDevolucao.guiaPagamento.id";
	
	/**
	 * Description of the Field
	 */
	public final static String GUIA_DEVOLUCAO_DEBITO_A_COBRAR_ID = "guiaDevolucao.debitoACobrarGeral.id";
	
	/**
	 * Description of the Field
	 */
	public final static String LOCALIDADE_ID = "localidade.id";
	
	/**
	 * Description of the Field
	 */
	public final static String DEVOLUCAO_SITUACAO_ATUAL = "devolucaoSituacaoAtual";
	
	/**
	 * Description of the Field
	 */
	public final static String DEVOLUCAO_SITUACAO_ANTERIOR = "devolucaoSituacaoAnterior";
	
	/**
	 * Description of the Field
	 */
	public final static String GUIA_DEVOLUCAO = "guiaDevolucao";
	
	/**
	 * Description of the Field
	 */
	public final static String AVISO_BANCARIO = "avisoBancario";
	
	/**
	 * Description of the Field
	 */
	public final static String CLIENTE = "cliente";
	
	/**
	 * Description of the Field
	 */
	public final static String IMOVEL = "imovel";
	
	/**
	 * Description of the Field
	 */
	public final static String DEBITO_TIPO_ = "debitoTipo";
	
	/**
	 * Description of the Field
	 */
	public final static String LOCALIDADE = "localidade";
	
	public final static String CREDITO_A_REALIZAR_ID = "creditoARealizarGeral.id";
	
}
