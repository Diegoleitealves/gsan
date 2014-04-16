package gcom.gui.cadastro.cliente;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class InserirClientePessoaAction extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping
				.findForward("gerenciadorProcesso");

		// Pega o actionform da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		DynaValidatorForm form = (DynaValidatorForm) sessao
				.getAttribute("ClienteActionForm");

		Fachada fachada = Fachada.getInstancia();

		// Verifica se o usu�rio informou o CPF/RG - CNPJ
		String cpf = (String) form.get("cpf");
		String rg = (String) form.get("rg");
		String cnpj = (String) form.get("cnpj");

		// O usu�rio � pessoa f�sica
		if (cpf != null && !cpf.trim().equalsIgnoreCase("")) {

			boolean igual = true;
			Integer i = 0;

//			Integer tam = cpf.length();
//
//			while (i < tam - 1) {
//				if ((cpf.charAt(i)) == (cpf.charAt(i + 1))) {
//					i = i + 1;
//				} else {
//					igual = false;
//				}
//				i = i + 1;
//			}
            
            Integer tam = cpf.length() - 1;
            
            while ( i < tam ) {
                if ( (cpf.charAt(i)) != (cpf.charAt(i + 1)) ){
                    igual = false;
                    break;
                } else {
                    i++;
                }
            }

			if (igual) {
				throw new ActionServletException("atencao.cpf_invalido");
			}

			FiltroCliente filtroCliente = new FiltroCliente();

			filtroCliente.adicionarParametro(new ParametroSimples(
					FiltroCliente.CPF, cpf));

			Collection colecaoClienteComCpf = fachada.pesquisar(filtroCliente,
					Cliente.class.getName());

			if (!colecaoClienteComCpf.isEmpty()) {
				Cliente clienteComCpf = (Cliente) colecaoClienteComCpf
						.iterator().next();

				throw new ActionServletException(
						"atencao.cpf.cliente.ja_cadastrado", null, ""
								+ clienteComCpf.getId());
			}
		}

		if (rg != null && !rg.trim().equalsIgnoreCase("")) {
			FiltroCliente filtroCliente = new FiltroCliente();

			filtroCliente.adicionarParametro(new ParametroSimples(
					FiltroCliente.RG, rg));

			Integer idOrgaoExpedidor = new Integer(form.get("idOrgaoExpedidor")
					.toString());

			// filtroCliente.adicionarCaminhoParaCarregamentoEntidade("orgaoExpedidorRg");

			filtroCliente.adicionarParametro(new ParametroSimples(
					FiltroCliente.ORGAO_EXPEDIDOR_RG_ID, idOrgaoExpedidor
							.intValue()));

			Integer idUnidadeFederacao = new Integer(form.get(
					"idUnidadeFederacao").toString());
			filtroCliente
					.adicionarCaminhoParaCarregamentoEntidade("unidadeFederacao");
			filtroCliente.adicionarParametro(new ParametroSimples(
					FiltroCliente.UNIDADE_FEDERACAO_ID, idUnidadeFederacao
							.intValue()));

			Collection colecaoClienteComRg = fachada.pesquisar(filtroCliente,
					Cliente.class.getName());

			if (!colecaoClienteComRg.isEmpty()) {
				Cliente clienteComRg = (Cliente) colecaoClienteComRg.iterator()
						.next();

				throw new ActionServletException(
						"atencao.rg.cliente.ja_cadastrado", null, ""
								+ clienteComRg.getId());
			}
		}

		// O usu�rio � pessoa jur�dica
		if (cnpj != null && !cnpj.trim().equalsIgnoreCase("")) {
			FiltroCliente filtroCliente = new FiltroCliente();

			filtroCliente.adicionarParametro(new ParametroSimples(
					FiltroCliente.CNPJ, cnpj));

			Collection colecaoClienteComCnpj = fachada.pesquisar(filtroCliente,
					Cliente.class.getName());

			if (!colecaoClienteComCnpj.isEmpty()) {
				Cliente clienteComCnpj = (Cliente) colecaoClienteComCnpj
						.iterator().next();

				throw new ActionServletException(
						"atencao.cnpj.cliente.ja_cadastrado", null, ""
								+ clienteComCnpj.getId());
			}
		}

		// Pega o codigo do cliente(que vem de pessoa jur�dica)
		String codigoClienteResponsavel = (String) form
				.get("codigoClienteResponsavel");

		// Verificar se a data de emissao do rg � menor que a data atual do
		// sistema
		String dataEmissaoRg = (String) form.get("dataEmissao");

		if (dataEmissaoRg != null && !dataEmissaoRg.trim().equals("")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			try {
				if (!verificarDataMenorQueDataCorrente(dateFormat
						.parse(dataEmissaoRg))) {
					throw new ActionServletException(
							"atencao.data_menor_que_atual", null,
							"emiss�o do RG");
				}
			} catch (ParseException ex) {
				throw new ActionServletException("erro.sistema");
			}
		}

		// Verificar se a data de nascimento � menor que a data atual do sistema
		String dataNascimento = (String) form.get("dataNascimento");

		if (dataNascimento != null && !dataNascimento.trim().equals("")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			try {
				if (!verificarDataMenorQueDataCorrente(dateFormat
						.parse(dataNascimento))) {
					throw new ActionServletException(
							"atencao.data_menor_que_atual", null, "nascimento");
				}
			} catch (ParseException ex) {
				throw new ActionServletException("erro.sistema");
			}
		}

		// Verificar se a data de nascimento � maior que a data de emiss�o de rg
		if (dataNascimento != null && !dataNascimento.trim().equals("")
				&& dataEmissaoRg != null && !dataEmissaoRg.trim().equals("")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

			try {
				if (dateFormat.parse(dataNascimento).after(
						dateFormat.parse(dataEmissaoRg))) {
					throw new ActionServletException(
							"atencao.data_nascimento_maior_que_data_emissao");
				}
			} catch (ParseException ex) {
				throw new ActionServletException("erro.sistema");
			}
		}

		// Verifica o c�digo se ele estiver presente
		if (codigoClienteResponsavel != null
				&& !codigoClienteResponsavel.trim().equals("")) {
			FiltroCliente filtro = new FiltroCliente();

			filtro.adicionarParametro(new ParametroSimples(FiltroCliente.ID,
					codigoClienteResponsavel));
			filtro.adicionarParametro(new ParametroSimples(
					FiltroCliente.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));
			filtro.adicionarCaminhoParaCarregamentoEntidade("clienteTipo");

			// Pesquisa o c�digo para verificar a exist�ncia dele
			Collection clienteEncontrado = fachada.pesquisar(filtro,
					Cliente.class.getName());

			// Se o c�digo n�o existir ent�o o usu�rio passou um c�digo que n�o
			// foi pesquisado na p�gina
			if (clienteEncontrado.isEmpty()) {
				// Mostra o erro
				throw new ActionServletException(
						"atencao.cliente.responsavel.inexitente");
			}

			// O cliente foi encontrado
			Cliente encontrado = (Cliente) ((List) clienteEncontrado).get(0);

			if (encontrado.getClienteTipo().getIndicadorPessoaFisicaJuridica()
					.equals(ClienteTipo.INDICADOR_PESSOA_FISICA)) {
				// O usu�rio digitou uma pessoa f�sica
				// limpa a sele��o do usu�rio do form
				throw new ActionServletException(
						"atencao.responsavel.pessoa_juridica");
			}
		}

		return retorno;
	}
}
