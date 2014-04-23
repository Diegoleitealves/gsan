package gcom.relatorio.seguranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.ConsultaCdl;
import gcom.seguranca.FiltroConsultaCadastroCdl;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
 * @author Rodrigo Cabral
 * @version 1.0
 */

public class RelatorioResultadoPesquisaConsultaCadastroCdl extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioResultadoPesquisaConsultaCadastroCdl(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_CONSULTA_CADASTRO_CDL);
	}
	
	@Deprecated
	public RelatorioResultadoPesquisaConsultaCadastroCdl() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param situacao pagamento
	 *            Description of the Parameter
	 * @param SituacaoPagamentoParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */

	public Object executar() throws TarefaException {

		// ------------------------------------
		//		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroConsultaCadastroCdl filtroConsultaCadastroCdl = (FiltroConsultaCadastroCdl) getParametro("filtroConsultaCadastroCdl");
		String periodoAcessoInicial = (String) getParametro("periodoAcessoInicial");
		String periodoAcessoFinal = (String) getParametro("periodoAcessoFinal");
		
		
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioResultadoPesquisaConsultaCadastroCdlBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoConsultaCadastroCdl = fachada.pesquisar(filtroConsultaCadastroCdl,
				ConsultaCdl.class.getName());
		
		Integer total = 0;

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoConsultaCadastroCdl != null && !colecaoConsultaCadastroCdl.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator consultaCadastroCdlIterator = colecaoConsultaCadastroCdl.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (consultaCadastroCdlIterator.hasNext()) {

				ConsultaCdl consultaCdl = (ConsultaCdl) consultaCadastroCdlIterator.next();
			
				String numeroImovelCliente = "";
				if (consultaCdl.getNumeroImovelCliente() != null){
					numeroImovelCliente = consultaCdl.getNumeroImovelCliente().toString();
				}
				
				String cpfCliente = "";
				if (consultaCdl.getCpfCliente() != null){
					cpfCliente = Util.formatarCpf(consultaCdl.getCpfCliente());
				}
				
				String cnpjCliente = "";
				if (consultaCdl.getCnpjCliente() != null){
					cnpjCliente = Util.formatarCnpj(consultaCdl.getCnpjCliente());
				}
				
				String acaoOperador = "";
				if (consultaCdl.getCodigoAcaoOperador() != null){
					
					if (consultaCdl.getCodigoAcaoOperador() == 1){
						acaoOperador = "Aceita";
					} else if (consultaCdl.getCodigoAcaoOperador() == 2){
						acaoOperador = "Rejeitada";
					} else {
						acaoOperador = "Dados conferem";
					}
					
				}
				
				
				relatorioBean = new RelatorioResultadoPesquisaConsultaCadastroCdlBean(
						// Login operador
						consultaCdl.getLoginUsuario(),
						// Cod. Cliente GSAN
						consultaCdl.getCodigoCliente().getId().toString(),
						// CPF Cliente
						cpfCliente,
						// CNPJ Cliente
						cnpjCliente,
						// Nome Cliente/Razao Social
						consultaCdl.getNomeCliente(),
						// Op��o Operador
						acaoOperador,
						// Logradouro Cliente
						consultaCdl.getLogradouroCliente(),
						// N�mero 
						numeroImovelCliente,
						// Complemento
						consultaCdl.getComplementoEnderecoCliente(),
						// Bairro
						consultaCdl.getBairroCliente(),
						// Cidade
						consultaCdl.getCidadeCliente(),
						// UF
						consultaCdl.getUf(),
						// Telefone
						consultaCdl.getTelefoneComercialCliente()
						);
				
				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
				
				total++;
			}
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		
		if(periodoAcessoInicial != null && !periodoAcessoInicial.equals("")){
	
			parametros.put("periodoAcessoInicial", periodoAcessoInicial);
	
		}
		
		if(periodoAcessoFinal != null && !periodoAcessoFinal.equals("")){
			
			parametros.put("periodoAcessoFinal", periodoAcessoFinal);
	
		}
		
		if(total != null){
		
			parametros.put("total", total);
		
		}
		
		parametros.put("tipo", "R1090" );
		
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_CONSULTA_CADASTRO_CDL, parametros,
				ds, tipoFormatoRelatorio);
		
		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 1;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioResultadoPesquisaConsultaCadastroCdl", this);
	}

}
