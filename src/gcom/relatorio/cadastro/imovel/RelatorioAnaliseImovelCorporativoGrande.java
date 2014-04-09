package gcom.relatorio.cadastro.imovel;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.bean.GerarRelatorioAnaliseImovelCorporativoGrandeHelper;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
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
 * Este caso de uso gera relat�rio de an�lise do im�vel corporativo ou grande
 * 
 * @author Ana Maria
 * @date 06/01/09
 * 
 */
public class RelatorioAnaliseImovelCorporativoGrande extends TarefaRelatorio {
	private static final long serialVersionUID = 1L;

	public RelatorioAnaliseImovelCorporativoGrande(Usuario usuario) {
		super(usuario,
				ConstantesRelatorios.RELATORIO_ANALISE_IMOVEL_CORPORATIVO_GRANDE);
	}

	@Deprecated
	public RelatorioAnaliseImovelCorporativoGrande() {
		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException {

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Integer idGerenciaRegional = (Integer) getParametro("idGerenciaRegional");
		Integer idUnidadeNegocio = (Integer) getParametro("idUnidadeNegocio");
		Integer idLocalidadeInicial = (Integer) getParametro("idLocalidadeInicial");
		Integer idLocalidadeFinal = (Integer) getParametro("idLocalidadeFinal");
		Integer idSetorComercialInicial = (Integer) getParametro("idSetorComercialInicial");
		Integer idSetorComercialFinal = (Integer) getParametro("idSetorComercialFinal");
		Integer idImovel = (Integer) getParametro("idImovelPerfil");
		Integer selecionar = (Integer) getParametro("selecionar");
		Integer referencia = (Integer) getParametro("referencia");
		Integer idImovelPerfil = (Integer)getParametro("idImovelPerfil");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");	
		

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioAnaliseImovelCorporativoGrandeBean relatorioBean = null;

		Collection colecaoRelatorioAnaliseImovelCorporativoGrandeHelper = fachada.pesquisarDadosRelatorioAnaliseImovelCorporativoGrande(
				idGerenciaRegional,idUnidadeNegocio, idLocalidadeInicial, idLocalidadeFinal, 
				idSetorComercialInicial, idSetorComercialFinal, referencia, idImovel, selecionar);
		

		// se a cole��o de par�metros da analise n�o for vazia
		if (colecaoRelatorioAnaliseImovelCorporativoGrandeHelper != null
				&& !colecaoRelatorioAnaliseImovelCorporativoGrandeHelper.isEmpty()) {

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoRelatorioAnaliseImovelCorporativoGrandeHelperIterator = colecaoRelatorioAnaliseImovelCorporativoGrandeHelper
					.iterator();
			
			// la�o para criar a cole��o de par�metros da analise
			while (colecaoRelatorioAnaliseImovelCorporativoGrandeHelperIterator.hasNext()) {

				GerarRelatorioAnaliseImovelCorporativoGrandeHelper helper = (GerarRelatorioAnaliseImovelCorporativoGrandeHelper) colecaoRelatorioAnaliseImovelCorporativoGrandeHelperIterator
						.next();

				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio
				
				// Ger�ncia Regional
				String gerenciaRegional = "";

				if (helper.getIdGerenciaRegional() != null) {
					gerenciaRegional = helper.getIdGerenciaRegional()
							+ " - " + helper.getNomeGerenciaRegional();
				}
				
				// Unidade de Neg�cio
				String unidadeNegocio = "";

				if (helper.getIdUnidadeNegocio() != null) {
					unidadeNegocio = helper.getIdUnidadeNegocio()
							+ " - "+ helper.getNomeUnidadeNegocio();
				}
				
				
				// Localidade
				String localidade = "";

				if (helper.getIdLocalidade() != null) {
					localidade = helper
							.getIdLocalidade()
							+ " - "
							+ helper
									.getNomeLocalidade();
				}
				
				// Setor Comercial
				String setorComercial = "";
				String idSetorComercial = "";

				if (helper.getIdSetorComercial() != null) {
					setorComercial = helper.getCodigoSetorComercial().toString();
					idSetorComercial = helper.getIdSetorComercial().toString();			
				}

				// Im�vel, Endere�o e Categoria
				String matriculaImovel = "";
				String endereco = "";
				String inscricao = "";
				
				if (helper.getIdImovel() != null) {
					matriculaImovel = helper.getIdImovel().toString();
					Imovel imovel = new Imovel();
					imovel.setId(helper.getIdImovel());
					endereco = fachada.pesquisarEndereco(helper.getIdImovel());
					inscricao = fachada.pesquisarInscricaoImovel(helper.getIdImovel());
				}
				
				// Capacidade do Hidr�metro
				String capacidadeHidrometro = "";
				
				if (helper.getCapacidadeHidrometro() != null) {
					capacidadeHidrometro = helper.getCapacidadeHidrometro();
				}			
				
				String consumoMedio = "";
				
				if (helper.getConsumoMedio() != null) {
					consumoMedio = helper.getConsumoMedio().toString();
				}
				
				String consumoFaturado = "";
				
				if (helper.getConsumoFaturado() != null) {
					consumoFaturado = helper.getConsumoFaturado().toString();
				}
				
				String tipoLigacao = "";
				
				if(helper.getIdTipoLigacao() != null){
					if(helper.getIdTipoLigacao().equals(1)){
						tipoLigacao = "�GUA";
					}else{
						tipoLigacao = "ESGOTO";
					}
				}

				relatorioBean = new RelatorioAnaliseImovelCorporativoGrandeBean(

						// Unidade de Neg�cio
						unidadeNegocio,
						
						// Ger�ncia Regional
						gerenciaRegional,

						// Localidade
						localidade,
						
						// Id do Setor Comercial
						idSetorComercial,
						
						// Setor Comercial
						setorComercial,
						
						//Inscri��o
						inscricao,
						
						// Im�vel
						matriculaImovel,
										
						// Endere�o
						endereco,
						
						// Capacidade do Hidr�metro
						capacidadeHidrometro,
						
						//Consumo M�dio
						consumoMedio,
						
						//Consumo Faturado
						consumoFaturado,
						
						//Tipo de liga��o
						tipoLigacao						
				);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);

			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada
				.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("mesAno", Util.formatarAnoMesParaMesAno(referencia));		
		
		if(idUnidadeNegocio!=null){
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, idUnidadeNegocio));		
			Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());		
			UnidadeNegocio unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoUnidadeNegocio);
			parametros.put("unidadeNegocio", unidadeNegocio.getNomeAbreviado() + "-" + unidadeNegocio.getNome());
		}else{
			parametros.put("unidadeNegocio", "");
		}
		
		if(idLocalidadeInicial!=null){
			FiltroLocalidade filtroLocalidadeInicial = new FiltroLocalidade();
			filtroLocalidadeInicial.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeInicial));		
			Collection colecaoLocalidadeInicial = fachada.pesquisar(filtroLocalidadeInicial, Localidade.class.getName());		
			Localidade localidadeInicial = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidadeInicial);
			parametros.put("localidadeInicial",  localidadeInicial.getId() + "-" + localidadeInicial.getDescricao()) ;
			
		}else{
			parametros.put("localidadeInicial", "") ;
		}	
		
		if(idLocalidadeInicial!=null){
			FiltroLocalidade filtroLocalidadeFinal = new FiltroLocalidade();
			filtroLocalidadeFinal.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeInicial));		
			Collection colecaoLocalidadeFinal = fachada.pesquisar(filtroLocalidadeFinal, Localidade.class.getName());		
			Localidade localidadeFinal = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidadeFinal);		
			parametros.put("localidadeFinal", localidadeFinal.getId() + "-" + localidadeFinal.getDescricao()) ;		
				
		}else{
			parametros.put("localidadeFinal", "") ;					
		}
		
		if(idGerenciaRegional!=null){
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, idGerenciaRegional));		
			Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());		
			GerenciaRegional gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoGerenciaRegional);
			parametros.put("gerenciaRegional", gerenciaRegional.getNomeAbreviado() + "-" + gerenciaRegional.getNome());		
		}else{
			parametros.put("gerenciaRegional", "");		
		}
		
		
		if(idSetorComercialInicial!= null){
			FiltroSetorComercial filtroSetorComercialInicial = new FiltroSetorComercial();
			filtroSetorComercialInicial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, idSetorComercialInicial));		
			Collection colecaoSetorComercialInicial = fachada.pesquisar(filtroSetorComercialInicial, SetorComercial.class.getName());		
			SetorComercial setorComercialInicial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercialInicial);
			parametros.put("setorComercialInicial",  setorComercialInicial.getId() + "-" + setorComercialInicial.getDescricao()) ;				
		}else{
			parametros.put("setorComercialInicial", "") ;				
		}
		
		if(idSetorComercialInicial!=null){
			FiltroSetorComercial filtroSetorComercialFinal = new FiltroSetorComercial();
			filtroSetorComercialFinal.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, idSetorComercialInicial));		
			Collection colecaoSetorComercialFinal = fachada.pesquisar(filtroSetorComercialFinal, SetorComercial.class.getName());		
			SetorComercial setorComercialFinal = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercialFinal);		
			parametros.put("setorComercialFinal",  setorComercialFinal.getId() + "-" + setorComercialFinal.getDescricao()) ;				
		}else{
			parametros.put("setorComercialFinal",  "") ;		
		}		
		
		if(idImovelPerfil!=null){
			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
			filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, idImovelPerfil));		
			Collection colecaoImovelPerfil = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());		
			ImovelPerfil imovelPerfil = (ImovelPerfil) Util.retonarObjetoDeColecao(colecaoImovelPerfil);		
			parametros.put("imovelPerfil","AN�LISE DO IM�VEIS " +imovelPerfil.getDescricao().toUpperCase()+"S") ;		
		}else{
			parametros.put("imovelPerfil", "") ;	
		}
		
		parametros.put("tipoFormatoRelatorio", "R0887");		
		
		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(
				ConstantesRelatorios.RELATORIO_ANALISE_IMOVEL_CORPORATIVO_GRANDE,
				parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try {
			persistirRelatorioConcluido(retorno,
					Relatorio.ANALISE_IMOVEL_CORPORATIVO_GRANDE,
					idFuncionalidadeIniciada);
		} catch (ControladorException e) {
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio() {
		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch() {
		AgendadorTarefas.agendarTarefa("RelatorioAnaliseImovelCorporativoGrande",
				this);
	}
}
