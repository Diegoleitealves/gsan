package gcom.gui.arrecadacao.banco;

import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.banco.FiltroAgencia;
import gcom.arrecadacao.banco.FiltroBanco;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirAgenciaBancariaAction extends GcomAction {

	/**
	 * Este caso de uso permite inserir uma Ag�ncia Banc�ria
	 * 
	 * [UC0515] Inserir Ag�ncia Banc�ria
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @author Thiago Ten�rio
	 */

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirAgenciaBancariaActionForm inserirAgenciaBancariaActionForm = (InserirAgenciaBancariaActionForm) actionForm;

		// ------------ REGISTRAR TRANSA��O ----------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_AGENCIA_BANCARIA_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSA��O ----------------

		String codigo = inserirAgenciaBancariaActionForm.getCodigo();
		String nome = inserirAgenciaBancariaActionForm.getNome();
		String bancoID = inserirAgenciaBancariaActionForm.getBancoID();
		Collection colecaoEnderecos = (Collection) sessao
				.getAttribute("colecaoEnderecos");
		String telefone = inserirAgenciaBancariaActionForm.getTelefone();
		String ramal = inserirAgenciaBancariaActionForm.getRamal();
		String fax = inserirAgenciaBancariaActionForm.getFax();
		String email = inserirAgenciaBancariaActionForm.getEmail();

		Agencia agenciaInserir = new Agencia();
		Collection colecaoPesquisa = null;

		sessao.removeAttribute("tipoPesquisaRetorno");
		
		if (colecaoEnderecos != null && !colecaoEnderecos.isEmpty()) {
        	Agencia agenciaEndereco = (Agencia) Util
            .retonarObjetoDeColecao(colecaoEnderecos);
        	
        	agenciaInserir = agenciaEndereco;
        	
        }
//		else {
//        	throw new ActionServletException(
//					"atencao.campo_selecionado.obrigatorio", null, "Endere�o");
//        }

		if (Util.validarNumeroMaiorQueZERO(inserirAgenciaBancariaActionForm
				.getBancoID())) {
			// Constr�i o filtro para pesquisa do servi�o tipo refer�ncia
			FiltroBanco filtroBanco = new FiltroBanco();
			filtroBanco.adicionarParametro(new ParametroSimples(
					FiltroBanco.ID, inserirAgenciaBancariaActionForm
							.getBancoID()));
			
			Collection colecaoBancos = (Collection) fachada.pesquisar(
					filtroBanco, Banco.class.getName());

			// setando
			agenciaInserir.setBanco((Banco) colecaoBancos.iterator().next());
		}

        agenciaInserir.setNomeAgencia(nome);
		
		// O c�digo da Ag�ncia Bancaria � obrigat�rio.
		if (codigo == null || codigo.equalsIgnoreCase("")) {
			throw new ActionServletException("atencao.required", null, "C�digo da Ag�ncia Banc�ria");
		}

		// O nome da Ag�ncia Bancaria � obrigat�rio.
		if (nome == null || nome.equalsIgnoreCase("")) {
			throw new ActionServletException("atencao.required", null, "Nome da Ag�ncia Banc�ria");
		}

		agenciaInserir.setCodigoAgencia(codigo);
		agenciaInserir.setNomeAgencia(nome);

		// O telefone � obrigat�rio caso o ramal tenha sido informado.
		if (ramal != null && !ramal.equalsIgnoreCase("")) {
			agenciaInserir.setNumeroRamal(ramal);
			if (telefone == null || telefone.equalsIgnoreCase("")) {
				throw new ActionServletException(
						"atencao.telefone_agencia_bancaria_nao_informado");
			} else if (telefone.length() < 7) {
				throw new ActionServletException(
						"atencao.telefone_ou_fax_agencia_bancaria_menor_sete_digitos",
						null, "Telefone");
			}
		}

		// Telefone.
		if (telefone != null && !telefone.equalsIgnoreCase("")) {
			if (telefone.length() < 7) {
				throw new ActionServletException(
						"atencao.telefone_ou_fax_agencia_bancaria_menor_sete_digitos",
						null, "Telefone");
			} else {
				agenciaInserir.setNumeroTelefone(telefone);
			}
		}

		// Fax.
		if (fax != null && !fax.equalsIgnoreCase("")) {
			if (fax.length() < 7) {
				throw new ActionServletException(
						"atencao.telefone_ou_fax_agencia_bancaria_menor_sete_digitos",
						null, "Fax");
			} else {
				agenciaInserir.setNumeroFax(fax);
			}
		}

		// E-mail.
		if (email != null && !email.equalsIgnoreCase("")) {
			agenciaInserir.setEmail(email);
		}

		// Banco.
		Banco banco = new Banco();
		if (bancoID != null
				&& !bancoID.equalsIgnoreCase(String
						.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))) {

			FiltroBanco filtroBanco = new FiltroBanco();

			filtroBanco.adicionarParametro(new ParametroSimples(FiltroBanco.ID,
					bancoID));

			filtroBanco.adicionarParametro(new ParametroSimples(
					FiltroBanco.INDICADOR_USO,
					ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna localidade - Elo
			colecaoPesquisa = fachada.pesquisar(filtroBanco, Banco.class
					.getName());

			if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
				// O c�digo do Elo n�o existe na tabela Localidade
				throw new ActionServletException(
						"atencao.pesquisa_elo_nao_inexistente");
			} else {
				banco = (Banco) Util.retonarObjetoDeColecao(colecaoPesquisa);
				if (banco.getId().intValue() != banco.getId().intValue()) {
					// A localidade escolhida n�o � um Elo
					throw new ActionServletException(
							"atencao.localidade_nao_e_elo");
				} else {
					agenciaInserir.setBanco(banco);
				}
			}
		}

		// Ultima altera��o
		agenciaInserir.setUltimaAlteracao(new Date());

	      FiltroAgencia filtroAgencia = new FiltroAgencia();

	        filtroAgencia.adicionarParametro(new ParametroSimples(
	        		FiltroAgencia.CODIGO_AGENCIA, agenciaInserir.getCodigoAgencia()));
	        filtroAgencia.adicionarParametro(new ParametroSimples(
	        		FiltroAgencia.BANCO_ID, bancoID));

		// Verificar exist�ncia da Ag�ncia Banc�ria
		colecaoPesquisa = fachada.pesquisar(filtroAgencia, Agencia.class
				.getName());

		if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {
			// Gerencia Regional j� existe
			throw new ActionServletException(
					"atencao.pesquisa_agencia_ja_cadastrada", null, codigo);
		} else {
			Integer idAgencia = null;

			idAgencia = fachada.inserirAgenciaBancaria(agenciaInserir);
			
			montarPaginaSucesso(httpServletRequest,
					"Ag�ncia Bancaria de c�digo  "
							+ agenciaInserir.getCodigoAgencia()
							+ " inserida com sucesso.",
					"Inserir outra Ag�ncia Bancaria",
					"exibirInserirAgenciaBancariaAction.do?menu=sim",
					"exibirAtualizarAgenciaBancariaAction.do?inserir=sim&idRegistroAtualizacao="
							+ idAgencia, "Atualizar Agencia Bancaria Inserida");

		}

		sessao.removeAttribute("colecaoEnderecos");

		// devolve o mapeamento de retorno
		return retorno;
	}

}
