package gcom.gui.cadastro.cliente;

import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.FiltroFoneTipo;
import gcom.cadastro.cliente.FoneTipo;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Luis Octavio
 */
public class ExibirAtualizarClienteTelefoneAction extends GcomAction {
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Prepara o retorno da A��o
		ActionForward retorno = actionMapping
				.findForward("atualizarClienteTelefone");

		// Cria a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m o action form
		DynaValidatorForm clienteActionForm = (DynaValidatorForm) actionForm;

		// Obtendo o idMuncipio corrente
		String idMunicipio = ((String) clienteActionForm.get("idMunicipio"));

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		Collection colecaoClienteFone = (Collection) sessao
				.getAttribute("colecaoClienteFone");

		if (colecaoClienteFone == null) {
			colecaoClienteFone = new HashSet();
		}

		// Pega a refer�ncia do Gerenciador de P�ginas
		// GerenciadorPaginas gerenciadorPaginas = null;

		// Inicializa a cole��o de FoneTipo
		Collection foneTipos = null;
		ClienteFone clienteFone = null;
		Collection municipios = null;

		// Filtro de tipos de telefone
		FiltroFoneTipo filtroFoneTipo = new FiltroFoneTipo();

		filtroFoneTipo.adicionarParametro(new ParametroSimples(
				FiltroFoneTipo.INDICADOR_USO,
				ConstantesSistema.INDICADOR_USO_ATIVO));

		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

		// S� vai mandar fazer a pesquisa do munic�pio se o usu�rio apertou o
		// enter e n�o o bot�o "adicionar"
		if (clienteActionForm.get("botaoClicado") == null
				|| clienteActionForm.get("botaoClicado").equals("")) {

			if (idMunicipio != null && !idMunicipio.trim().equalsIgnoreCase("")) {

				// Manda para a p�gina a informa��o do campo para que seja
				// focado no retorno da pesquisa
				httpServletRequest.setAttribute("nomeCampo", "idMunicipio");

				// Adiciona Parametro para pesquisar municipio
				filtroMunicipio.adicionarParametro(new ParametroSimples(
						FiltroMunicipio.ID, idMunicipio));
				filtroMunicipio.adicionarParametro(new ParametroSimples(
						FiltroMunicipio.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
				// Recebe a cole��o de munic�pio conforme o idMuncipio informado
				municipios = fachada.pesquisar(filtroMunicipio, Municipio.class
						.getName());

				// Verifica se o retorno da pesquisa trouxe resultados
				if (municipios != null && !municipios.isEmpty()) {

					// Prepara o iterator para percorrer a cole��o resultante da
					// pesquisa
					Iterator iteratorMunicipio = municipios.iterator();

					// Posiciona-se no primeiro registro
					Municipio municipio = (Municipio) iteratorMunicipio.next();

					// Passa pelo request o municipio encontrado na pesquisa
					clienteActionForm
							.set("idMunicipio", "" + municipio.getId());
					clienteActionForm.set("descricaoMunicipio", municipio
							.getNome());
					clienteActionForm.set("ddd", "" + municipio.getDdd());
				} else {
					httpServletRequest.setAttribute("municipioNaoEncontrado",
							"true");
					//clienteActionForm.set("idMunicipio", "");
					clienteActionForm.set("descricaoMunicipio",
							"Munic�pio Inexistente");
					//clienteActionForm.set("ddd", "");

				}
			}
		}

		// Realiza a pesquisa de tipois de telefone
		foneTipos = fachada.pesquisar(filtroFoneTipo, FoneTipo.class.getName());

		if (foneTipos == null || foneTipos.isEmpty()) {
			// Nenhum tipo de telefone cadastrado
			throw new ActionServletException("atencao.naocadastrado", null,
					"tipo de telefone");

		} else {
			// Envia os objetos no request
			sessao.setAttribute("foneTipos", foneTipos);
		}

		municipios = fachada.pesquisar(filtroMunicipio, Municipio.class
				.getName());

		// Envia para sessao a colecao de municipios
		sessao.setAttribute("municipios", municipios);

		if (clienteActionForm.get("botaoClicado") != null
				&& !clienteActionForm.get("botaoClicado").equals("")) {
			if ((clienteActionForm.get("botaoClicado").equals("ADICIONAR"))
					&& (clienteActionForm.get("ddd") != null && !((String) clienteActionForm
							.get("ddd")).trim().equalsIgnoreCase(""))
					&& (clienteActionForm.get("idTipoTelefone") != null && !((String) clienteActionForm
							.get("idTipoTelefone")).trim().equalsIgnoreCase(""))
					&& (clienteActionForm.get("telefone") != null && !((String) clienteActionForm
							.get("telefone")).trim().equalsIgnoreCase(""))) {

				// Verificar se o usu�rio digitou um DDD que existe no sistema
				filtroMunicipio.limparListaParametros();

				filtroMunicipio.adicionarParametro(new ParametroSimples(
						FiltroMunicipio.DDD, (String) clienteActionForm
								.get("ddd")));

				Collection municipiosComDDDValido = fachada.pesquisar(
						filtroMunicipio, Municipio.class.getName());

				if (municipiosComDDDValido.isEmpty()) {

					clienteActionForm.set("ddd", "");

					// O DDD n�o existe no sistema
					throw new ActionServletException(
							"atencao.telefone.ddd.nao_existente");
				}

				clienteFone = new ClienteFone();
				clienteFone.setDdd((String) clienteActionForm.get("ddd"));
				clienteFone.setTelefone((String) clienteActionForm
						.get("telefone"));

				if (clienteActionForm.get("ramal") != null
						&& !((String) clienteActionForm.get("ramal")).trim()
								.equalsIgnoreCase("")) {
					clienteFone.setRamal((String) clienteActionForm
							.get("ramal"));
				}
				
				if (clienteActionForm.get("contato") != null
						&& !((String) clienteActionForm.get("contato")).trim()
								.equalsIgnoreCase("")) {
					clienteFone.setContato((String) clienteActionForm
							.get("contato"));
				}

				FoneTipo foneTipo = new FoneTipo();

				foneTipo.setId(new Integer(clienteActionForm.get(
						"idTipoTelefone").toString()));
				foneTipo.setDescricao(clienteActionForm.get("textoSelecionado")
						.toString());

				clienteFone.setFoneTipo(foneTipo);
				clienteFone.setUltimaAlteracao(new Date());
				
				// Verifica se o telefone j� existe na cole��o
				if (!colecaoClienteFone.contains(clienteFone)) 
				{
					colecaoClienteFone.add(clienteFone);
				}
				else
				{
					httpServletRequest.setAttribute("telefoneJaExistente", true);
				}

				clienteActionForm.set("botaoAdicionar", null);
				clienteActionForm.set("botaoClicado", null);
				clienteActionForm.set("ddd", null);
				clienteActionForm.set("telefone", null);
				clienteActionForm.set("idTipoTelefone", null);
				clienteActionForm.set("idMunicipio", null);
				clienteActionForm.set("ramal", null);
				clienteActionForm.set("contato", null);
				clienteActionForm.set("descricaoMunicipio", null);

			}
		}

		sessao.setAttribute("colecaoClienteFone", colecaoClienteFone);

		// Limpa a indica��o que o bot�o adicionar foi clicado
		clienteActionForm.set("botaoClicado", "");

		// Se a cole��o de telefones tiver apenas um item, ent�o este item tem
		// que estar selecionado
		// como telefone principal
		Iterator iterator = colecaoClienteFone.iterator();

		if (colecaoClienteFone != null && colecaoClienteFone.size() == 1) {

			clienteFone = (ClienteFone) iterator.next();

			clienteActionForm.set("indicadorTelefonePadrao", new Long(
					obterTimestampIdObjeto(clienteFone)));

		}
		
		//**********************************************************************
		// CRC2103
		// Autor: Ivan Sergio
		// Data: 02/07/2009
		// Verifica se a tela deve ser exibida como um PopUp
		//**********************************************************************
		if (httpServletRequest.getParameter("POPUP") != null) {
			if (httpServletRequest.getParameter("POPUP").equals("true")) {
				sessao.setAttribute("POPUP", "true");
			}else {
				sessao.setAttribute("POPUP", "false");
			}
		}else if (sessao.getAttribute("POPUP") == null) {
			sessao.setAttribute("POPUP", "false");
		}
		//**********************************************************************

		return retorno;
	}
}
