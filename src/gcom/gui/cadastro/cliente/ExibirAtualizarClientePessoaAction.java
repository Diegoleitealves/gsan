package gcom.gui.cadastro.cliente;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.cadastro.cliente.FiltroOrgaoExpedidorRg;
import gcom.cadastro.cliente.FiltroPessoaSexo;
import gcom.cadastro.cliente.FiltroProfissao;
import gcom.cadastro.cliente.FiltroRamoAtividade;
import gcom.cadastro.cliente.OrgaoExpedidorRg;
import gcom.cadastro.cliente.PessoaSexo;
import gcom.cadastro.cliente.Profissao;
import gcom.cadastro.cliente.RamoAtividade;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Description of the Class
 * 
 * @author Rodrigo
 */
public class ExibirAtualizarClientePessoaAction extends GcomAction {

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

		DynaValidatorForm clienteActionForm = (DynaValidatorForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Verifica se o usu�rio escolheu algum tipo de pessoa para que seja
		// mostrada a p�gina de
		// pessoa f�sica ou de jur�dica, se nada estiver especificado a p�gina
		// selecionada ser� a de
		// pessoa f�sica
		
		Short tipoPessoa = (Short)  clienteActionForm.get("tipoPessoa");
		String tipoPessoaForm =  tipoPessoa.toString() ; 
		
		FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();

		filtroClienteTipo.adicionarParametro(new ParametroSimples(
				FiltroClienteTipo.ID, tipoPessoaForm));
		tipoPessoa = ((ClienteTipo) fachada.pesquisar(filtroClienteTipo,
				ClienteTipo.class.getName()).iterator().next())
				.getIndicadorPessoaFisicaJuridica();

		ActionForward retorno = actionMapping
				.findForward("atualizarClientePessoaFisica");
		
        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);
        
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		
        // -------------------------------------------------------------------------------------------
		// Alterado por :  Davi Menezes - data : 10/08/2011 
		// Analista :  Rafael Pinto.
    	// [UC0009] - [FS0014] - Verificar obrigatoriedade
    	// -------------------------------------------------------------------------------------------
		
		// Se o usu�rio n�o tem permiss�o especial.
		boolean temPermissaoParaAlterarClienteSemCpf = 
			fachada.verificarPermissaoEspecial(PermissaoEspecial.MANTER_CLIENTE_SEM_CPF_CNPJ, usuarioLogado);		

		SistemaParametro sistemaParametro = this.getSistemaParametro();
		
		if(sistemaParametro.getIndicadorDocumentoObrigatorioManterCliente() == ConstantesSistema.NAO.shortValue()){
			temPermissaoParaAlterarClienteSemCpf = true;
		}

		httpServletRequest.setAttribute("temPermissaoParaAlterarClienteSemCpf", temPermissaoParaAlterarClienteSemCpf);
		// -------------------------------------------------------------------------------------------

		if (tipoPessoa != null
				&& tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)) {

			// Limpar todo o conte�do da p�gina de pessoa f�sica
			clienteActionForm.set("cpf", "");
			clienteActionForm.set("rg", "");
			clienteActionForm.set("dataEmissao", "");
			clienteActionForm.set("idOrgaoExpedidor", new Integer(
					ConstantesSistema.NUMERO_NAO_INFORMADO));
			clienteActionForm.set("idUnidadeFederacao", new Integer(
					ConstantesSistema.NUMERO_NAO_INFORMADO));
			clienteActionForm.set("dataNascimento", "");
			clienteActionForm.set("idProfissao", new Integer(
					ConstantesSistema.NUMERO_NAO_INFORMADO));
			clienteActionForm.set("idPessoaSexo", new Integer(
					ConstantesSistema.NUMERO_NAO_INFORMADO));
			clienteActionForm.set("nomeMae", "");

			// Prepara a p�gina para Pessoa Jur�dica
			retorno = actionMapping
					.findForward("atualizarClientePessoaJuridica");

			// -------Parte que trata do c�digo quando o usu�rio tecla enter
			String codigoDigitadoEnter = (String) clienteActionForm
					.get("codigoClienteResponsavel");

			// Verifica se o c�digo foi digitado
			if (codigoDigitadoEnter != null
					&& !codigoDigitadoEnter.trim().equals("")) {

				// Manda para a p�gina a informa��o do campo para que seja
				// focado no retorno da pesquisa
				httpServletRequest.setAttribute("nomeCampo",
						"codigoClienteResponsavel");

				FiltroCliente filtroCliente = new FiltroCliente();

				filtroCliente.adicionarParametro(new ParametroSimples(
						FiltroCliente.ID, codigoDigitadoEnter));
				filtroCliente.adicionarParametro(new ParametroSimples(
						FiltroCliente.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

				filtroCliente
						.adicionarCaminhoParaCarregamentoEntidade("clienteTipo");

				Collection clienteEncontrado = fachada.pesquisar(filtroCliente,
						Cliente.class.getName());

				if (clienteEncontrado != null && !clienteEncontrado.isEmpty()) {
					// O cliente foi encontrado
					Cliente encontrado = (Cliente) ((List) clienteEncontrado)
							.get(0);

					if (encontrado.getClienteTipo()
							.getIndicadorPessoaFisicaJuridica().equals(
									ClienteTipo.INDICADOR_PESSOA_JURIDICA)) {
						// Verifica se o usu�rio digitou uma pessoa jur�dica
						clienteActionForm.set("codigoClienteResponsavel", ""
								+ encontrado.getId());
						clienteActionForm.set("nomeClienteResponsavel",
								encontrado.getNome());
						//.setAttribute(
							//	"codigoClienteNaoEncontrado", "true");
						sessao.setAttribute(
							"codigoClienteNaoEncontrado", "true");
						
					} else {
						// O usu�rio digitou uma pessoa f�sica
						clienteActionForm.set("codigoClienteResponsavel", "");
						throw new ActionServletException(
								"atencao.responsavel.pessoa_juridica");
					}

				} else {
					clienteActionForm.set("codigoClienteResponsavel", "");
					//httpServletRequest.setAttribute(
						//	"codigoClienteNaoEncontrado", "exception");
					sessao.setAttribute(
							"codigoClienteNaoEncontrado", "exception");					
					clienteActionForm.set("nomeClienteResponsavel",
							"Cliente inexistente");

				}
			}

			// -------Fim da Parte que trata do c�digo quando o usu�rio tecla enter

			FiltroRamoAtividade filtroRamoAtividade = new FiltroRamoAtividade(
					FiltroRamoAtividade.DESCRICAO);

			filtroRamoAtividade.adicionarParametro(new ParametroSimples(
					FiltroRamoAtividade.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection ramoAtividades = fachada.pesquisar(filtroRamoAtividade,
					RamoAtividade.class.getName());

			httpServletRequest.setAttribute("ramoAtividades", ramoAtividades);

		} else {

			// Limpa os dados da p�gina de pessoa
			clienteActionForm.set("cnpj", "");
			clienteActionForm.set("idRamoAtividade", new Integer(
					ConstantesSistema.NUMERO_NAO_INFORMADO));
			clienteActionForm.set("codigoClienteResponsavel", "");
			clienteActionForm.set("nomeClienteResponsavel", "");

			// Prepara a p�gina para Pessoa F�sica
			FiltroOrgaoExpedidorRg filtroOrgaoExpedidor = new FiltroOrgaoExpedidorRg(
					FiltroOrgaoExpedidorRg.DESCRICAO_ABREVIADA);
			FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao(
					FiltroUnidadeFederacao.SIGLA);
			FiltroProfissao filtroProfissao = new FiltroProfissao(
					FiltroProfissao.DESCRICAO);
			FiltroPessoaSexo filtroPessoaSexo = new FiltroPessoaSexo(
					FiltroPessoaSexo.DESCRICAO);

			// S� vai mostrar os registros ativos
			filtroOrgaoExpedidor.adicionarParametro(new ParametroSimples(
					FiltroOrgaoExpedidorRg.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroProfissao.adicionarParametro(new ParametroSimples(
					FiltroProfissao.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroPessoaSexo.adicionarParametro(new ParametroSimples(
					FiltroPessoaSexo.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			// Faz a pesquisa das cole��es
			Collection orgaosExpedidores = fachada.pesquisar(
					filtroOrgaoExpedidor, OrgaoExpedidorRg.class.getName());

			Collection unidadesFederacao = fachada.pesquisar(
					filtroUnidadeFederacao, UnidadeFederacao.class.getName());

			Collection profissoes = fachada.pesquisar(filtroProfissao,
					Profissao.class.getName());

			Collection pessoasSexos = fachada.pesquisar(filtroPessoaSexo,
					PessoaSexo.class.getName());

			// A cole��o de pessoasSexos � obrigat�ria na p�gina
			if (!(pessoasSexos != null && !pessoasSexos.isEmpty())) {

				throw new ActionServletException("erro.naocadastrado", null,
						"sexo");

			}

			// Seta no request as cole��es
			httpServletRequest.setAttribute("orgaosExpedidores",
					orgaosExpedidores);
			httpServletRequest.setAttribute("unidadesFederacao",
					unidadesFederacao);
			httpServletRequest.setAttribute("profissoes", profissoes);
			httpServletRequest.setAttribute("pessoasSexos", pessoasSexos);

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
