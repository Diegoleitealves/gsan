package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.hidrometro.FiltroHidrometro;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.FiltroHidrometroMarca;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroClasseMetrologica;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
import gcom.micromedicao.hidrometro.HidrometroMarca;
import gcom.micromedicao.hidrometro.HidrometroRelojoaria;
import gcom.micromedicao.hidrometro.HidrometroTipo;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author compesa
 * @created 13 de Setembro de 2005
 */
public class AtualizarHidrometroAction extends GcomAction {
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

		AtualizarHidrometroActionForm atualizarHidrometroActionForm = (AtualizarHidrometroActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);
		
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		// ------------ REGISTRAR TRANSA��O ----------------
        RegistradorOperacao registradorOperacao = new RegistradorOperacao(
				Operacao.OPERACAO_HIDROMETRO_ATUALIZAR,
				new UsuarioAcaoUsuarioHelper(usuario,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
        
        Operacao operacao = new Operacao();
        operacao.setId(Operacao.OPERACAO_HIDROMETRO_ATUALIZAR);

        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
        operacaoEfetuada.setOperacao(operacao);
        //------------ REGISTRAR TRANSA��O ----------------

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Hidrometro hidrometro = (Hidrometro) sessao.getAttribute("hidrometro");
		
		// Obt�m a fachada
		Fachada fachada = Fachada.getInstancia();

		
		FiltroHidrometro filtroHidrometro = new FiltroHidrometro();

        filtroHidrometro.adicionarParametro(new ParametroSimples(
                FiltroHidrometro.ID, hidrometro.getId()));

        Collection hidrometros = fachada.pesquisar(filtroHidrometro,
                Hidrometro.class.getName());

        if (hidrometros == null || hidrometros.isEmpty()) {
        	throw new ActionServletException("atencao.atualizacao.timestamp");
        }

		// Cria o objeto classe metrol�gica e seta o id
		HidrometroClasseMetrologica hidrometroClasseMetrologica = new HidrometroClasseMetrologica();
		hidrometroClasseMetrologica.setId(new Integer(atualizarHidrometroActionForm
				.getIdHidrometroClasseMetrologica()));
		hidrometro.setHidrometroClasseMetrologica(hidrometroClasseMetrologica);

		// Cria o objeto hidr�metro marca e seta o id
		HidrometroMarca hidrometroMarca = new HidrometroMarca();
		hidrometroMarca.setId(new Integer(atualizarHidrometroActionForm
				.getIdHidrometroMarca()));
	
		/* * [FS0004]- Verificar Preenchimento dos campos
		 * Caso 3
		 */
		
		// Cria o objeto hidr�metro capacidade e seta o id
		HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();
		hidrometroCapacidade.setId(new Integer(atualizarHidrometroActionForm
				.getIdHidrometroCapacidade()));
		
		
		FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();
		
		filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(
				FiltroHidrometroCapacidade.ID,hidrometroCapacidade.getId().toString()));
			
		Collection colecaoHidrometroCapacidadeBase = fachada.pesquisar(
				filtroHidrometroCapacidade,HidrometroCapacidade.class.getName());
		
		HidrometroCapacidade hidrometroCapacidadeBase = (HidrometroCapacidade) 
				colecaoHidrometroCapacidadeBase.iterator().next(); 
		
		if(!hidrometroCapacidadeBase.getCodigoHidrometroCapacidade().
				equalsIgnoreCase(atualizarHidrometroActionForm.getNumeroHidrometro().substring(0,1))){
			throw new ActionServletException("atencao.capacidade_incompativel_numero_fixo");
		}
		
		hidrometro.setHidrometroCapacidade(hidrometroCapacidade);
		
		FiltroHidrometroMarca filtroHidrometroMarca = new FiltroHidrometroMarca();
		
		filtroHidrometroMarca.adicionarParametro(new ParametroSimples(
				FiltroHidrometroMarca.ID,hidrometroMarca.getId().toString()));
		
		
		Collection colecaoHidrometroMarcaBase = fachada.pesquisar(
				filtroHidrometroMarca,HidrometroMarca.class.getName());
		
		HidrometroMarca hidrometroMarcaBase = (HidrometroMarca) 
				colecaoHidrometroMarcaBase.iterator().next(); 
		
		if(!hidrometroMarcaBase.getCodigoHidrometroMarca().
				equalsIgnoreCase(atualizarHidrometroActionForm.getNumeroHidrometro().substring(3,4))){
			throw new ActionServletException("atencao.marca_incompativel_numero_fixo");
		}
		
		hidrometro.setHidrometroMarca(hidrometroMarca);

		

		// Cria o objeto hidr�metro di�metro e seta o id
		HidrometroDiametro hidrometroDiametro = new HidrometroDiametro();
		hidrometroDiametro.setId(new Integer(atualizarHidrometroActionForm
				.getIdHidrometroDiametro()));
		hidrometro.setHidrometroDiametro(hidrometroDiametro);

		// Cria o objeto hidr�metro tipo e seta o id
		HidrometroTipo hidrometroTipo = new HidrometroTipo();
		hidrometroTipo.setId(new Integer(atualizarHidrometroActionForm
				.getIdHidrometroTipo()));
		hidrometro.setHidrometroTipo(hidrometroTipo);
		
		// Cria o objeto hidr�metro relojoaria e seta o id
		if(atualizarHidrometroActionForm
				.getIdHidrometroRelojoaria() != null && Integer.parseInt(atualizarHidrometroActionForm
						.getIdHidrometroRelojoaria()) > ConstantesSistema.NUMERO_NAO_INFORMADO){
			
			HidrometroRelojoaria hidrometroRelojoaria = new HidrometroRelojoaria();
			hidrometroRelojoaria.setId(new Integer(atualizarHidrometroActionForm
					.getIdHidrometroRelojoaria()));
			hidrometro.setHidrometroRelojoaria(hidrometroRelojoaria);
			
		}else{
			hidrometro.setHidrometroRelojoaria(null);
		}
		

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		Date dataAquisicao = null;
		try {
			dataAquisicao = formatoData.parse(atualizarHidrometroActionForm
					.getDataAquisicao());
		} catch (ParseException ex) {
			// Erro no hibernate
			reportarErros(httpServletRequest, "erro.sistema", ex);
			// Atribui o mapeamento de retorno para a tela de erro
			retorno = actionMapping.findForward("telaErro");
		}
		
		
		/**
		 * [FS0008]-Montar ano de fabricacao
		 */
		/*String numeroHidrometro = atualizarHidrometroActionForm.getNumeroHidrometro();
		
		String anoFabricacaoForm = atualizarHidrometroActionForm.getAnoFabricacao();
		
		String aux1 = numeroHidrometro.substring(1,3);
		
		Integer aux2= new Integer(aux1);
		
		if (anoFabricacaoForm.equalsIgnoreCase("") || anoFabricacaoForm == null) {
			if (aux2 >= 85) {
				char[] anoFabricacaoChar = anoFabricacaoForm.toCharArray();
				anoFabricacaoChar[0] = '1';
				anoFabricacaoChar[1] = '9';
				anoFabricacaoForm = (new String(anoFabricacaoChar) + aux2);
			} else if (aux2 >= 00) {
				char[] anoFabricacaoChar = anoFabricacaoForm.toCharArray();
				anoFabricacaoChar[0] = '2';
				anoFabricacaoChar[1] = '0';
				anoFabricacaoForm = (new String(anoFabricacaoChar) + aux2);
			}

		}*/

		Integer anoFabricacao = new Integer(atualizarHidrometroActionForm.getAnoFabricacao());

		Date dataAquisicaoAnterior = null;
		try {
			dataAquisicaoAnterior = formatoData.parse("01/01/1985");
		} catch (ParseException ex) {
			// Erro no hibernate
			reportarErros(httpServletRequest, "erro.sistema", ex);
			// Atribui o mapeamento de retorno para a tela de erro
			retorno = actionMapping.findForward("telaErro");
		}
		Calendar dataAtual = new GregorianCalendar();
		int anoAtual = dataAtual.get(Calendar.YEAR);
		// caso a data de aquisi��o seja menor que a data atual
		if (dataAquisicao.after(new Date())) {
			throw new ActionServletException(
					"atencao.data.aquisicao.nao.superior.data.corrente");
		}

		Integer anoDataAquisicao = Util.getAno(dataAquisicao);
		// caso a data de aquisi��o seja menor que o ano fabrica��o
		if (anoDataAquisicao < anoFabricacao) {
			throw new ActionServletException(
					"atencao.ano.aquisicao.menor.ano.fabricacao");

		}
		// caso a data de aquisi��o seja menor que 01/01/1985
		if (dataAquisicao.before(dataAquisicaoAnterior)) {
			throw new ActionServletException(
					"atencao.data.aquisicao.nao.inferior.1985");

		}

		// caso o ano de fabrica��o seja maior que o atual
		if (anoFabricacao > anoAtual) {
			throw new ActionServletException(
					"atencao.ano.fabricacao.nao.superior.data.corrente");

		}
		// caso o ano de fabrica��o seja menor que 1985
		if (anoFabricacao < 1985) {
			throw new ActionServletException(
					"atencao.ano.fabricacao.nao.inferior.1985");
		}
		
		//Vazao Transicao
		BigDecimal vazaoTransicao = null;
		
		if (atualizarHidrometroActionForm.getVazaoTransicao() != null 
				&& !atualizarHidrometroActionForm.getVazaoTransicao().equals("") ) {
			vazaoTransicao = Util.formatarMoedaRealparaBigDecimal( atualizarHidrometroActionForm.getVazaoTransicao() ) ;
		}
		
		//Vazao Nominal
		BigDecimal vazaoNominal = null;
		
		if (atualizarHidrometroActionForm.getVazaoNominal() != null 
				&& !atualizarHidrometroActionForm.getVazaoNominal().equals("") ) {
			vazaoNominal = Util.formatarMoedaRealparaBigDecimal( atualizarHidrometroActionForm.getVazaoNominal() ) ;
		}
		
		//Vazao Minima
		BigDecimal vazaoMinima = null;
		
		if (atualizarHidrometroActionForm.getVazaoMinima() != null 
				&& !atualizarHidrometroActionForm.getVazaoMinima().equals("") ) {
			vazaoMinima = Util.formatarMoedaRealparaBigDecimal( atualizarHidrometroActionForm.getVazaoMinima().replace("," , "." ) ) ;
		}
		
		//Nota Fiscal
		Integer notaFiscal = null;
		
		if ( atualizarHidrometroActionForm.getNotaFiscal() != null
				&& !atualizarHidrometroActionForm.getNotaFiscal().equals("") ) {
			notaFiscal = new Integer (atualizarHidrometroActionForm.getNotaFiscal() ) ;
		}
		
		//Tempo de Garantia
		Short tempoGarantiaAnos = null;
		
		if ( atualizarHidrometroActionForm.getTempoGarantiaAnos() != null
				&& !atualizarHidrometroActionForm.getTempoGarantiaAnos().equals("") ) {
			tempoGarantiaAnos = new Short (atualizarHidrometroActionForm.getTempoGarantiaAnos() ) ;
		}

		hidrometro.setNumero(atualizarHidrometroActionForm.getNumeroHidrometro());
		hidrometro.setDataAquisicao(dataAquisicao);
		hidrometro.setAnoFabricacao(new Short(atualizarHidrometroActionForm
				.getAnoFabricacao()));
		hidrometro.setIndicadorMacromedidor(new Short(atualizarHidrometroActionForm
				.getIndicadorMacromedidor()));
		hidrometro.setNumeroDigitosLeitura(new Short(atualizarHidrometroActionForm
				.getIdNumeroDigitosLeitura()));
		hidrometro.setVazaoTransicao(vazaoTransicao);
		hidrometro.setVazaoNominal(vazaoNominal);
		hidrometro.setVazaoMinima(vazaoMinima);
		hidrometro.setNotaFiscal(notaFiscal);
		hidrometro.setTempoGarantiaAnos(tempoGarantiaAnos);
		
		//------------ REGISTRAR TRANSA��O ----------------
        hidrometro.setOperacaoEfetuada(operacaoEfetuada);
        hidrometro.adicionarUsuario(usuario, 
        		UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
        registradorOperacao.registrarOperacao(hidrometro);
        //------------ REGISTRAR TRANSA��O ----------------

		// Inseri hidr�metro
		fachada.atualizarHidrometro(hidrometro);

		// M�todo utilizado para montar a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Hidr�metro de n�mero "
				+ hidrometro.getNumero() + " atualizado com sucesso.",
				"Realizar outra Manuten��o de Hidr�metro",
				"exibirManterHidrometroAction.do?menu=sim");

		// Remove objetos da sess�o
		sessao.removeAttribute("HidrometroActionForm");
		sessao.removeAttribute("colecaoIntervalo");
		sessao.removeAttribute("colecaoHidrometroClasseMetrologica");
		sessao.removeAttribute("colecaoHidrometroMarca");
		sessao.removeAttribute("colecaoHidrometroDiametro");
		sessao.removeAttribute("colecaoHidrometroCapacidade");
		sessao.removeAttribute("colecaoHidrometroTipo");
		sessao.removeAttribute("fixo");
		sessao.removeAttribute("faixaInicial");
		sessao.removeAttribute("faixaFinal");
		sessao.removeAttribute("hidrometros");

		return retorno;
	}
}
