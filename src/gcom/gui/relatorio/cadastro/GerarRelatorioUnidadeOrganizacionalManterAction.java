package gcom.gui.relatorio.cadastro;

import gcom.atendimentopublico.registroatendimento.FiltroMeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.FiltroUnidadeTipo;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeTipo;
import gcom.fachada.Fachada;
import gcom.gui.cadastro.unidade.UnidadeOrganizacionalActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cadastro.RelatorioManterUnidadeOrganizacional;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
 * @author Arthur Carvalho
 * @version 1.0
 */

public class GerarRelatorioUnidadeOrganizacionalManterAction extends
		ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		UnidadeOrganizacionalActionForm unidadeOrganizacionalActionForm = (UnidadeOrganizacionalActionForm) actionForm;

		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = (FiltroUnidadeOrganizacional) sessao
				.getAttribute("filtroUnidadeOrganizacional");
		

		// Inicio da parte que vai mandar os parametros para o relat�rio

		UnidadeOrganizacional unidadeOrganizacionalParametros = new UnidadeOrganizacional();
		
		
		String id = null;
		
		if(unidadeOrganizacionalActionForm.getIdUnidade() != null 
			&& !unidadeOrganizacionalActionForm.getIdUnidade().trim().equals("")){
		
			id = unidadeOrganizacionalActionForm.getIdUnidade();
		
		}
		
		Short indicadorAberturaRa = 0;

		if (unidadeOrganizacionalActionForm.getUnidadeAbreRA() != null
				&& !unidadeOrganizacionalActionForm.getUnidadeAbreRA().equals("")) {

			indicadorAberturaRa = new Short(""
					+ unidadeOrganizacionalActionForm.getUnidadeAbreRA());
		}

		Short indicadorTramite = 0;
		
		if(unidadeOrganizacionalActionForm.getUnidadeTramitacao()!= null 
				&& !unidadeOrganizacionalActionForm.getUnidadeTramitacao().equals("")){
			
			indicadorTramite = new Short ("" + unidadeOrganizacionalActionForm.getUnidadeTramitacao());
		}
		
		Short indicadorEsgoto = 0;
		
		if(unidadeOrganizacionalActionForm.getUnidadeEsgoto() != null 
				&& !unidadeOrganizacionalActionForm.getUnidadeEsgoto().equals("")){
			
			indicadorEsgoto = new Short("" + unidadeOrganizacionalActionForm.getUnidadeEsgoto());
		}
		
		Short indicadorUso = 0;
		
		if(unidadeOrganizacionalActionForm.getIndicadorUso() != null 
				&& !unidadeOrganizacionalActionForm.getIndicadorUso().equals("")){
			
			indicadorUso = new Short("" + unidadeOrganizacionalActionForm.getIndicadorUso());
		}
		
		String sigla = null;
		
		if (unidadeOrganizacionalActionForm.getSigla() != null
				&& !unidadeOrganizacionalActionForm.getSigla().equals("")){
			
			sigla = unidadeOrganizacionalActionForm.getSigla();
		}
		
		
		// seta os parametros que ser�o mostrados no relat�rio
		unidadeOrganizacionalParametros.setId(id == null ? null : new Integer(id));
		
		UnidadeTipo unidadeTipo = new UnidadeTipo();
		
		//UnidadeTipo
		if (unidadeOrganizacionalActionForm.getIdTipoUnidade() != null && !unidadeOrganizacionalActionForm.getIdTipoUnidade().trim().equals("")) {
			FiltroUnidadeTipo filtroUnidadeTipo = new FiltroUnidadeTipo();
			filtroUnidadeTipo.adicionarParametro( new ParametroSimples( FiltroUnidadeTipo.ID, unidadeOrganizacionalActionForm.getIdTipoUnidade() ) );
			Collection colUniTip = Fachada.getInstancia().pesquisar( filtroUnidadeTipo, UnidadeTipo.class.getName() );
			
			UnidadeTipo unidadeTipoInformada = ( UnidadeTipo ) colUniTip.iterator().next();
			
			unidadeTipo = unidadeTipoInformada;
		}
		
		// N�vel
		
		String pesquisouNivel = "nao";
		if (unidadeOrganizacionalActionForm.getNivelHierarquico() != null 
				&& !unidadeOrganizacionalActionForm.getNivelHierarquico().trim().equals("")) {
			
			pesquisouNivel = "sim";
			
			unidadeTipo.setNivel(new Short(unidadeOrganizacionalActionForm.getNivelHierarquico()));
		}
		
		//Unidade Centralizadora
		if(unidadeOrganizacionalActionForm.getIdUnidadeCentralizadora() != null && !unidadeOrganizacionalActionForm.getIdUnidadeCentralizadora().trim().equals("")){
			FiltroUnidadeOrganizacional filtroUnidadeCentralizadora = new FiltroUnidadeOrganizacional();
			filtroUnidadeCentralizadora.adicionarParametro( new ParametroSimples(FiltroUnidadeOrganizacional.ID, unidadeOrganizacionalActionForm.getIdUnidadeCentralizadora()));
			Collection colUniCen = Fachada.getInstancia().pesquisar( filtroUnidadeCentralizadora, UnidadeOrganizacional.class.getName() );
		
			UnidadeOrganizacional unidadeCentralizadora =  (UnidadeOrganizacional ) colUniCen.iterator().next();
			
			unidadeOrganizacionalParametros.setUnidadeCentralizadora( unidadeCentralizadora );
		}
		
		//Unidade Repavimentadora
		if(unidadeOrganizacionalActionForm.getIdUnidadeRepavimentadora() != null && !unidadeOrganizacionalActionForm.getIdUnidadeRepavimentadora().trim().equals("")){
			FiltroUnidadeOrganizacional filtroUnidadeRepavimentadora = new FiltroUnidadeOrganizacional();
			filtroUnidadeRepavimentadora.adicionarParametro( new ParametroSimples(FiltroUnidadeOrganizacional.ID, unidadeOrganizacionalActionForm.getIdUnidadeRepavimentadora()));
			Collection colUniRep = Fachada.getInstancia().pesquisar( filtroUnidadeRepavimentadora, UnidadeOrganizacional.class.getName() );
		
			UnidadeOrganizacional unidadeRepavimentadora =  ( UnidadeOrganizacional ) colUniRep.iterator().next();
			
			unidadeOrganizacionalParametros.setUnidadeRepavimentadora( unidadeRepavimentadora );
		}
		
	
		
		//Empresa
		if (unidadeOrganizacionalActionForm.getIdEmpresa() != null && !unidadeOrganizacionalActionForm.getIdEmpresa().trim().equals("")) {
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.adicionarParametro( new ParametroSimples( FiltroEmpresa.ID, unidadeOrganizacionalActionForm.getIdEmpresa() ) );
			
			Collection colEmp = Fachada.getInstancia().pesquisar( filtroEmpresa, Empresa.class.getName() );
			
			Empresa empresa = ( Empresa ) colEmp.iterator().next();
			unidadeOrganizacionalParametros.setEmpresa( empresa );
		}
		
		//Meio da Solicita��o padr�o	
		if(unidadeOrganizacionalActionForm.getIdMeioSolicitacao() != null && !unidadeOrganizacionalActionForm.getIdMeioSolicitacao().trim().equals("")){
			FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao();
			filtroMeioSolicitacao.adicionarParametro( new ParametroSimples (FiltroMeioSolicitacao.ID, unidadeOrganizacionalActionForm.getIdMeioSolicitacao() ) );
		
			Collection colMeioSoli = Fachada.getInstancia().pesquisar( filtroMeioSolicitacao, MeioSolicitacao.class.getName());
			MeioSolicitacao meioSolicitacao = (MeioSolicitacao) colMeioSoli.iterator().next();
			unidadeOrganizacionalParametros.setMeioSolicitacao( meioSolicitacao );
		}
		
		//Localidade
		if(unidadeOrganizacionalActionForm.getIdLocalidade() != null && !unidadeOrganizacionalActionForm.getIdLocalidade().trim().equals("")){
			FiltroLocalidade filtroLocalidade= new FiltroLocalidade();
			filtroLocalidade.adicionarParametro( new ParametroSimples (FiltroLocalidade.ID, unidadeOrganizacionalActionForm.getIdLocalidade() ) );
		
			Collection colLoca = Fachada.getInstancia().pesquisar( filtroLocalidade, Localidade.class.getName());
			Localidade localidade = (Localidade) colLoca.iterator().next();
			unidadeOrganizacionalParametros.setLocalidade( localidade );
		}
			
		unidadeOrganizacionalParametros.setSigla(sigla);
		unidadeOrganizacionalParametros.setIndicadorEsgoto(indicadorEsgoto);
		unidadeOrganizacionalParametros.setIndicadorUso(indicadorUso);
		unidadeOrganizacionalParametros.setDescricao(unidadeOrganizacionalActionForm.getDescricao());
		unidadeOrganizacionalParametros.setIndicadorAberturaRa(indicadorAberturaRa);
		unidadeOrganizacionalParametros.setIndicadorTramite(indicadorTramite);
		unidadeOrganizacionalParametros.setUnidadeTipo(unidadeTipo);

		// Fim da parte que vai mandar os parametros para o relat�rio

		// cria uma inst�ncia da classe do relat�rio
		RelatorioManterUnidadeOrganizacional relatorioManterUnidadeOrganizacional= new RelatorioManterUnidadeOrganizacional(
				(Usuario)(httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));
		relatorioManterUnidadeOrganizacional.addParametro("filtroUnidadeOrganizacional",
				filtroUnidadeOrganizacional);
		relatorioManterUnidadeOrganizacional.addParametro("unidadeOrganizacionalParametros",
				unidadeOrganizacionalParametros);
		
		relatorioManterUnidadeOrganizacional.addParametro("pesquisouNivel", pesquisouNivel);

		// chama o met�do de gerar relat�rio passando o c�digo da analise
		// como par�metro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if (tipoRelatorio == null) {
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterUnidadeOrganizacional.addParametro("tipoFormatoRelatorio", Integer
				.parseInt(tipoRelatorio));
		try {
			retorno = processarExibicaoRelatorio(relatorioManterUnidadeOrganizacional,
					tipoRelatorio, httpServletRequest, httpServletResponse,
					actionMapping);

		} catch (SistemaException ex) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		} catch (RelatorioVazioException ex1) {
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "erro.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
