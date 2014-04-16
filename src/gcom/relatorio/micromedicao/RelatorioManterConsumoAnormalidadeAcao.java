package gcom.relatorio.micromedicao;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.consumo.ConsumoAnormalidade;
import gcom.micromedicao.consumo.ConsumoAnormalidadeAcao;
import gcom.micromedicao.consumo.FiltroConsumoAnormalidade;
import gcom.micromedicao.consumo.FiltroConsumoAnormalidadeAcao;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidadeConsumo;
import gcom.micromedicao.leitura.LeituraAnormalidadeConsumo;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

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

public class RelatorioManterConsumoAnormalidadeAcao extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;
	public RelatorioManterConsumoAnormalidadeAcao(Usuario usuario) {
		super(usuario, ConstantesRelatorios.RELATORIO_MANTER_CONSUMO_ANORMALIDADE_ACAO);
	}
	
	@Deprecated
	public RelatorioManterConsumoAnormalidadeAcao() {
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

		FiltroConsumoAnormalidadeAcao filtroConsumoAnormalidadeAcao = (FiltroConsumoAnormalidadeAcao) 
							getParametro("filtroConsumoAnormalidadeAcao");
		ConsumoAnormalidadeAcao consumoAnormalidadeAcaoParametros = (ConsumoAnormalidadeAcao)
							getParametro("consumoAnormalidadeAcaoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		RelatorioManterConsumoAnormalidadeAcaoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoConsumoAnormalidadeAcao = fachada.pesquisar(filtroConsumoAnormalidadeAcao,
				ConsumoAnormalidadeAcao.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoConsumoAnormalidadeAcao != null && !colecaoConsumoAnormalidadeAcao.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator consumoAnormalidadeAcaoIterator = colecaoConsumoAnormalidadeAcao.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while (consumoAnormalidadeAcaoIterator.hasNext()) {

				ConsumoAnormalidadeAcao consumoAnormalidadeAcao = (ConsumoAnormalidadeAcao) consumoAnormalidadeAcaoIterator.next();				
				
				String consumoAnormalidade = "";
				if (consumoAnormalidadeAcao.getConsumoAnormalidade() != null){
					
					consumoAnormalidade = consumoAnormalidadeAcao.getConsumoAnormalidade().getDescricao().toString();
				}
				
				String categoria = "";
				if (consumoAnormalidadeAcao.getCategoria() != null){
					
					categoria = consumoAnormalidadeAcao.getCategoria().getDescricao().toString();
				}
				
				String imovelPerfil = "";
				if (consumoAnormalidadeAcao.getImovelPerfil() != null){
					
					imovelPerfil = consumoAnormalidadeAcao.getImovelPerfil().getDescricao().toString();
				}
				
				String leituraAnormalidadeConsumoMes1 = "";
				if (consumoAnormalidadeAcao.getLeituraAnormalidadeConsumoMes1() != null){
					
					leituraAnormalidadeConsumoMes1 = consumoAnormalidadeAcao.getLeituraAnormalidadeConsumoMes1().toString();
				}
				
				String leituraAnormalidadeConsumoMes2 = "";
				if (consumoAnormalidadeAcao.getLeituraAnormalidadeConsumoMes2() != null){
					
					leituraAnormalidadeConsumoMes2 = consumoAnormalidadeAcao.getLeituraAnormalidadeConsumoMes2().toString();
				}
				
				String leituraAnormalidadeConsumoMes3 = "";
				if (consumoAnormalidadeAcao.getLeituraAnormalidadeConsumoMes3() != null){
					
					leituraAnormalidadeConsumoMes3 = consumoAnormalidadeAcao.getLeituraAnormalidadeConsumoMes3().toString();
				}
				
				String servicoTipoMes1 = "";
				if (consumoAnormalidadeAcao.getServicoTipoMes1() != null){
					
					servicoTipoMes1 = consumoAnormalidadeAcao.getServicoTipoMes1().toString();
				}
				
				String servicoTipoMes2 = "";
				if (consumoAnormalidadeAcao.getServicoTipoMes2() != null){
					
					servicoTipoMes2 = consumoAnormalidadeAcao.getServicoTipoMes2().toString();
				}
				
				String servicoTipoMes3 = "";
				if (consumoAnormalidadeAcao.getServicoTipoMes3() != null){
					
					servicoTipoMes3 = consumoAnormalidadeAcao.getServicoTipoMes3().toString();
				}
				
				String solicitacaoTipoEspecificacaoMes1 = "";
				if (consumoAnormalidadeAcao.getSolicitacaoTipoEspecificacaoMes1() != null){
					
					solicitacaoTipoEspecificacaoMes1 = consumoAnormalidadeAcao.getSolicitacaoTipoEspecificacaoMes1().toString();
				}
				
				String solicitacaoTipoEspecificacaoMes2 = "";
				if (consumoAnormalidadeAcao.getSolicitacaoTipoEspecificacaoMes2() != null){
					
					solicitacaoTipoEspecificacaoMes2 = consumoAnormalidadeAcao.getSolicitacaoTipoEspecificacaoMes2().toString();
				}
				
				String solicitacaoTipoEspecificacaoMes3 = "";
				if (consumoAnormalidadeAcao.getSolicitacaoTipoEspecificacaoMes3() != null){
					
					solicitacaoTipoEspecificacaoMes3 = consumoAnormalidadeAcao.getSolicitacaoTipoEspecificacaoMes3().toString();
				}
				relatorioBean = new RelatorioManterConsumoAnormalidadeAcaoBean(
						consumoAnormalidade, 
						categoria, 
						imovelPerfil, 
						leituraAnormalidadeConsumoMes1, 
						leituraAnormalidadeConsumoMes2,
						leituraAnormalidadeConsumoMes3, 
						consumoAnormalidadeAcao.getNumerofatorConsumoMes1().toString(), 
						consumoAnormalidadeAcao.getNumerofatorConsumoMes2().toString(), 
						consumoAnormalidadeAcao.getNumerofatorConsumoMes3().toString(), 
						consumoAnormalidadeAcao.getIndicadorGeracaoCartaMes1().toString(), 
						consumoAnormalidadeAcao.getIndicadorGeracaoCartaMes2().toString(), 
						consumoAnormalidadeAcao.getIndicadorGeracaoCartaMes3().toString(), 
						servicoTipoMes1, 
						servicoTipoMes2, 
						servicoTipoMes3,
						solicitacaoTipoEspecificacaoMes1, 
						solicitacaoTipoEspecificacaoMes2, 
						solicitacaoTipoEspecificacaoMes3, 
						consumoAnormalidadeAcao.getDescricaoContaMensagemMes1(),
						consumoAnormalidadeAcao.getDescricaoContaMensagemMes2(), 
						consumoAnormalidadeAcao.getDescricaoContaMensagemMes3(), 
						consumoAnormalidadeAcao.getIndicadorUso().toString());
				
				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		
		if(consumoAnormalidadeAcaoParametros.getConsumoAnormalidade() != null 
				&& !consumoAnormalidadeAcaoParametros.getConsumoAnormalidade().equals("-1")){
			
			FiltroConsumoAnormalidade filtroConsumoAnormalidade = new FiltroConsumoAnormalidade();
			filtroConsumoAnormalidade.adicionarParametro(new ParametroSimples(FiltroConsumoAnormalidade.ID, consumoAnormalidadeAcaoParametros.getConsumoAnormalidade().getId()));
			
			Collection colecaoConsumoAnormalidade = fachada.pesquisar(filtroConsumoAnormalidade, ConsumoAnormalidade.class.getName());
			ConsumoAnormalidade consumoAnormalidade = (ConsumoAnormalidade) Util.retonarObjetoDeColecao(colecaoConsumoAnormalidade);
			
			parametros.put("consumoAnormalidade", consumoAnormalidade.getDescricaoAbreviada());
		}
		
		if (consumoAnormalidadeAcaoParametros.getCategoria() != null 
				&& !consumoAnormalidadeAcaoParametros.getCategoria().equals("-1")){
			
			FiltroCategoria filtroCategoria = new FiltroCategoria();
			filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, consumoAnormalidadeAcaoParametros.getCategoria().getId()));
			
			Collection colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());
			Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoCategoria);
			
			parametros.put("categoria",
					""+ categoria.getDescricaoAbreviada());
		}
		
		if (consumoAnormalidadeAcaoParametros.getImovelPerfil() != null 
				&& !consumoAnormalidadeAcaoParametros.getImovelPerfil().equals("-1")){
			
			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
			filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, consumoAnormalidadeAcaoParametros.getImovelPerfil().getId()));
			
			Collection colecaoImovelPerfil = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());
			ImovelPerfil imovelPerfil = (ImovelPerfil) Util.retonarObjetoDeColecao(colecaoImovelPerfil);
			
			parametros.put("imovelPerfil",
					""+ imovelPerfil.getDescricao());
		}
		
		if (consumoAnormalidadeAcaoParametros.getLeituraAnormalidadeConsumoMes1() != null 
				&& !consumoAnormalidadeAcaoParametros.getLeituraAnormalidadeConsumoMes1().equals("-1")){
			
			FiltroLeituraAnormalidadeConsumo filtroLeituraAnormalidadeConsumoMes1 = new FiltroLeituraAnormalidadeConsumo();
			filtroLeituraAnormalidadeConsumoMes1.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeConsumo.ID, 
					consumoAnormalidadeAcaoParametros.getLeituraAnormalidadeConsumoMes1().getId()));
			
			Collection colecaoLeituraAnormalidadeConsumoMes1 = fachada.pesquisar(filtroLeituraAnormalidadeConsumoMes1, 
					LeituraAnormalidadeConsumo.class.getName());
			LeituraAnormalidadeConsumo leituraAnormalidadeConsumoMes1 = (LeituraAnormalidadeConsumo) Util.retonarObjetoDeColecao(colecaoLeituraAnormalidadeConsumoMes1);
			
			parametros.put("leituraAnormalidadeConsumoMes1",
					""+ leituraAnormalidadeConsumoMes1.getDescricaoConsumoACobrar());
		}
		
		if (consumoAnormalidadeAcaoParametros.getLeituraAnormalidadeConsumoMes2() != null 
				&& !consumoAnormalidadeAcaoParametros.getLeituraAnormalidadeConsumoMes2().equals("-1")){
			
			FiltroLeituraAnormalidadeConsumo filtroLeituraAnormalidadeConsumoMes2 = new FiltroLeituraAnormalidadeConsumo();
			filtroLeituraAnormalidadeConsumoMes2.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeConsumo.ID, 
					consumoAnormalidadeAcaoParametros.getLeituraAnormalidadeConsumoMes2().getId()));
			
			Collection colecaoLeituraAnormalidadeConsumoMes2 = fachada.pesquisar(filtroLeituraAnormalidadeConsumoMes2, 
					LeituraAnormalidadeConsumo.class.getName());
			LeituraAnormalidadeConsumo leituraAnormalidadeConsumoMes2 = (LeituraAnormalidadeConsumo) Util.retonarObjetoDeColecao(colecaoLeituraAnormalidadeConsumoMes2);
			
			parametros.put("leituraAnormalidadeConsumoMes2",
					""+ leituraAnormalidadeConsumoMes2.getDescricaoConsumoACobrar());
		}
		
		if (consumoAnormalidadeAcaoParametros.getLeituraAnormalidadeConsumoMes3() != null 
				&& !consumoAnormalidadeAcaoParametros.getLeituraAnormalidadeConsumoMes3().equals("-1")){
			
			FiltroLeituraAnormalidadeConsumo filtroLeituraAnormalidadeConsumoMes3 = new FiltroLeituraAnormalidadeConsumo();
			filtroLeituraAnormalidadeConsumoMes3.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeConsumo.ID, 
					consumoAnormalidadeAcaoParametros.getLeituraAnormalidadeConsumoMes3().getId()));
			
			Collection colecaoLeituraAnormalidadeConsumoMes3 = fachada.pesquisar(filtroLeituraAnormalidadeConsumoMes3, 
					LeituraAnormalidadeConsumo.class.getName());
			LeituraAnormalidadeConsumo leituraAnormalidadeConsumoMes3 = (LeituraAnormalidadeConsumo) Util.retonarObjetoDeColecao(colecaoLeituraAnormalidadeConsumoMes3);
			
			parametros.put("leituraAnormalidadeConsumoMes3",
					""+ leituraAnormalidadeConsumoMes3.getDescricaoConsumoACobrar());
		}
		
		if (consumoAnormalidadeAcaoParametros.getNumerofatorConsumoMes1() != null 
				&& !consumoAnormalidadeAcaoParametros.getNumerofatorConsumoMes1().equals("")){
			
			parametros.put("numeroFatorConsumoMes1",
					""+ consumoAnormalidadeAcaoParametros.getNumerofatorConsumoMes1());
		}
		
		if (consumoAnormalidadeAcaoParametros.getNumerofatorConsumoMes2() != null 
				&& !consumoAnormalidadeAcaoParametros.getNumerofatorConsumoMes2().equals("")){
			
			parametros.put("numeroFatorConsumoMes2",
					""+ consumoAnormalidadeAcaoParametros.getNumerofatorConsumoMes2());
		}
		
		if (consumoAnormalidadeAcaoParametros.getNumerofatorConsumoMes3() != null 
				&& !consumoAnormalidadeAcaoParametros.getNumerofatorConsumoMes3().equals("")){
			
			parametros.put("numeroFatorConsumoMes3",
					""+ consumoAnormalidadeAcaoParametros.getNumerofatorConsumoMes3());
		}
		
		if (consumoAnormalidadeAcaoParametros.getIndicadorGeracaoCartaMes1() != null 
				&& !consumoAnormalidadeAcaoParametros.getIndicadorGeracaoCartaMes1().equals("")){
			
			parametros.put("indicadorGeracaoCartaMes1",
					""+ consumoAnormalidadeAcaoParametros.getIndicadorGeracaoCartaMes1());
		}
		
		if (consumoAnormalidadeAcaoParametros.getIndicadorGeracaoCartaMes2() != null 
				&& !consumoAnormalidadeAcaoParametros.getIndicadorGeracaoCartaMes2().equals("")){
			
			parametros.put("indicadorGeracaoCartaMes2",
					""+ consumoAnormalidadeAcaoParametros.getIndicadorGeracaoCartaMes2());
		}
		
		if (consumoAnormalidadeAcaoParametros.getIndicadorGeracaoCartaMes3() != null 
				&& !consumoAnormalidadeAcaoParametros.getIndicadorGeracaoCartaMes3().equals("")){
			
			parametros.put("indicadorGeracaoCartaMes3",
					""+ consumoAnormalidadeAcaoParametros.getIndicadorGeracaoCartaMes3());
		}
		
		if (consumoAnormalidadeAcaoParametros.getServicoTipoMes1() != null 
				&& !consumoAnormalidadeAcaoParametros.getServicoTipoMes1().equals("")){
			
			FiltroServicoTipo filtroServicoTipoMes1 = new FiltroServicoTipo();
			filtroServicoTipoMes1.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, 
					consumoAnormalidadeAcaoParametros.getServicoTipoMes1().getId()));
			
			Collection colecaoServicoTipoMes1 = fachada.pesquisar(filtroServicoTipoMes1, 
					ServicoTipo.class.getName());
			ServicoTipo servicoTipoMes1 = (ServicoTipo) 
					Util.retonarObjetoDeColecao(colecaoServicoTipoMes1);
			
			parametros.put("servicoTipoMes1",
					""+ servicoTipoMes1.getDescricaoAbreviada());
		}
		
		if (consumoAnormalidadeAcaoParametros.getServicoTipoMes2() != null 
				&& !consumoAnormalidadeAcaoParametros.getServicoTipoMes2().equals("")){
			
			FiltroServicoTipo filtroServicoTipoMes2 = new FiltroServicoTipo();
			filtroServicoTipoMes2.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, 
					consumoAnormalidadeAcaoParametros.getServicoTipoMes2().getId()));
			
			Collection colecaoServicoTipoMes2 = fachada.pesquisar(filtroServicoTipoMes2, 
					ServicoTipo.class.getName());
			ServicoTipo servicoTipoMes2 = (ServicoTipo) 
					Util.retonarObjetoDeColecao(colecaoServicoTipoMes2);
			
			parametros.put("servicoTipoMes2",
					""+ servicoTipoMes2.getDescricaoAbreviada());
		}
		
		if (consumoAnormalidadeAcaoParametros.getServicoTipoMes3() != null 
				&& !consumoAnormalidadeAcaoParametros.getServicoTipoMes3().equals("")){
			
			FiltroServicoTipo filtroServicoTipoMes3 = new FiltroServicoTipo();
			filtroServicoTipoMes3.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, 
					consumoAnormalidadeAcaoParametros.getServicoTipoMes3().getId()));
			
			Collection colecaoServicoTipoMes3 = fachada.pesquisar(filtroServicoTipoMes3, 
					ServicoTipo.class.getName());
			ServicoTipo servicoTipoMes3 = (ServicoTipo) 
					Util.retonarObjetoDeColecao(colecaoServicoTipoMes3);
			
			parametros.put("servicoTipoMes3",
					""+ servicoTipoMes3.getDescricaoAbreviada());
		}
		
		if (consumoAnormalidadeAcaoParametros.getSolicitacaoTipoEspecificacaoMes1() != null 
				&& !consumoAnormalidadeAcaoParametros.getSolicitacaoTipoEspecificacaoMes1().equals("-1")){
			
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacaoMes1 = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacaoMes1.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, 
					consumoAnormalidadeAcaoParametros.getSolicitacaoTipoEspecificacaoMes1().getId()));
			
			Collection colecaoSolicitacaoTipoEspecificacaoMes1 = fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoMes1, 
					SolicitacaoTipoEspecificacao.class.getName());
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacaoMes1 = (SolicitacaoTipoEspecificacao) 
					Util.retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacaoMes1);
			
			parametros.put("solicitacaoTipoEspecificacaoMes1",
					""+ solicitacaoTipoEspecificacaoMes1.getDescricao());
		}
		
		if (consumoAnormalidadeAcaoParametros.getSolicitacaoTipoEspecificacaoMes2() != null 
				&& !consumoAnormalidadeAcaoParametros.getSolicitacaoTipoEspecificacaoMes2().equals("-1")){
			
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacaoMes2 = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacaoMes2.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, 
					consumoAnormalidadeAcaoParametros.getSolicitacaoTipoEspecificacaoMes2().getId()));
			
			Collection colecaoSolicitacaoTipoEspecificacaoMes2 = fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoMes2, 
					SolicitacaoTipoEspecificacao.class.getName());
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacaoMes2 = (SolicitacaoTipoEspecificacao) 
					Util.retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacaoMes2);
			
			parametros.put("solicitacaoTipoEspecificacaoMes2",
					""+ solicitacaoTipoEspecificacaoMes2.getDescricao());
		}
		
		if (consumoAnormalidadeAcaoParametros.getSolicitacaoTipoEspecificacaoMes3() != null 
				&& !consumoAnormalidadeAcaoParametros.getSolicitacaoTipoEspecificacaoMes3().equals("-1")){
			
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacaoMes3 = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacaoMes3.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, 
					consumoAnormalidadeAcaoParametros.getSolicitacaoTipoEspecificacaoMes3().getId()));
			
			Collection colecaoSolicitacaoTipoEspecificacaoMes3 = fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoMes3, 
					SolicitacaoTipoEspecificacao.class.getName());
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacaoMes3 = (SolicitacaoTipoEspecificacao) 
					Util.retonarObjetoDeColecao(colecaoSolicitacaoTipoEspecificacaoMes3);
			
			parametros.put("solicitacaoTipoEspecificacaoMes3",
					""+ solicitacaoTipoEspecificacaoMes3.getDescricao());
		}
		
		if (consumoAnormalidadeAcaoParametros.getDescricaoContaMensagemMes1() != null 
				&& !consumoAnormalidadeAcaoParametros.getDescricaoContaMensagemMes1().equals("")){
			
			parametros.put("descricaoContaMensagemMes1",
					""+ consumoAnormalidadeAcaoParametros.getDescricaoContaMensagemMes1());
		}
		
		if (consumoAnormalidadeAcaoParametros.getDescricaoContaMensagemMes2() != null 
				&& !consumoAnormalidadeAcaoParametros.getDescricaoContaMensagemMes2().equals("")){
			
			parametros.put("descricaoContaMensagemMes2",
					""+ consumoAnormalidadeAcaoParametros.getDescricaoContaMensagemMes2());
		}
		
		if (consumoAnormalidadeAcaoParametros.getDescricaoContaMensagemMes3() != null 
				&& !consumoAnormalidadeAcaoParametros.getDescricaoContaMensagemMes3().equals("")){
			
			parametros.put("descricaoContaMensagemMes3",
					""+ consumoAnormalidadeAcaoParametros.getDescricaoContaMensagemMes3());
		}
		
		parametros.put("tipo", "R1058" );
		
		String indicadorUso = "";

		if (consumoAnormalidadeAcaoParametros.getIndicadorUso() != null
				&& !consumoAnormalidadeAcaoParametros.getIndicadorUso().equals("")) {
			if (consumoAnormalidadeAcaoParametros.getIndicadorUso().equals(new Short("1"))) {
				indicadorUso = "Ativo";
			} else if (consumoAnormalidadeAcaoParametros.getIndicadorUso().equals(
					new Short("2"))) {
				indicadorUso = "Inativo";
			}
			
		}
		
		parametros.put("indicadorUso", indicadorUso);
		
		// cria uma inst�ncia do dataSource do relat�rio

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(
				ConstantesRelatorios.RELATORIO_MANTER_CONSUMO_ANORMALIDADE_ACAO, parametros,
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
		AgendadorTarefas.agendarTarefa("RelatorioManterConsumoAnormalidadeAcao", this);
	}

}
