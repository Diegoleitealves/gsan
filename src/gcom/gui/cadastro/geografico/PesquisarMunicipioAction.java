package gcom.gui.cadastro.geografico;

import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Realiza a pesquisa de responsavel superior de acordo com os par�metros
 * informados
 * 
 * @author S�vio Luiz
 * @created 20 de Julho de 2005
 */

public class PesquisarMunicipioAction extends GcomAction {
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
        ActionForward retorno = actionMapping.findForward("listaMunicipio");

        //Mudar isso quando tiver esquema de seguran�a
        HttpSession sessao = httpServletRequest.getSession(false);

        DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

        //Recupera os par�metros do form
        String nome = (String) pesquisarActionForm.get("nomeMunicipio");
        Integer idRegiaoDesenvolvimento = (Integer) pesquisarActionForm
                .get("idRegiaoDesenvolvimento");
        Integer idRegiao = (Integer) pesquisarActionForm.get("idRegiao");
        Integer idMicrorregiao = (Integer) pesquisarActionForm
                .get("idMicrorregiao");
        Integer idUnidadeFederacao = (Integer) pesquisarActionForm
                .get("idUnidadeFederacao");
        String tipoPesquisa = (String) pesquisarActionForm.get("tipoPesquisa");

        FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

        filtroMunicipio.setCampoOrderBy(FiltroCliente.NOME);

        boolean peloMenosUmParametroInformado = false;

        //Insere os par�metros informados no filtro

        if (nome != null && !nome.trim().equalsIgnoreCase("")) {
            peloMenosUmParametroInformado = true;
            if (tipoPesquisa != null
					&& tipoPesquisa
							.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA
									.toString())) {
				filtroMunicipio.adicionarParametro(new ComparacaoTextoCompleto(
						FiltroMunicipio.NOME, nome));
			} else {
				filtroMunicipio.adicionarParametro(new ComparacaoTexto(
						FiltroMunicipio.NOME, nome));
			}
        }

        if (idRegiaoDesenvolvimento != null
                && idRegiaoDesenvolvimento.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
            peloMenosUmParametroInformado = true;
            filtroMunicipio.adicionarParametro(new ParametroSimples(
                    FiltroMunicipio.REGIAO_DESENVOLVOMENTO_ID,
                    idRegiaoDesenvolvimento));
        }

        if (idRegiao != null
                && idRegiao.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
            peloMenosUmParametroInformado = true;
            filtroMunicipio.adicionarParametro(new ParametroSimples(
                    FiltroMunicipio.REGIAO_ID, idRegiao));
        }

        if (idMicrorregiao != null
                && idMicrorregiao.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
            peloMenosUmParametroInformado = true;
            filtroMunicipio.adicionarParametro(new ParametroSimples(
                    FiltroMunicipio.MICRORREGICAO_ID, idMicrorregiao));
        }

        if (idUnidadeFederacao != null
                && idUnidadeFederacao.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
            peloMenosUmParametroInformado = true;
            filtroMunicipio.adicionarParametro(new ParametroSimples(
                    FiltroMunicipio.UNIDADE_FEDERACAO_ID, idUnidadeFederacao));
        }

        if( sessao.getAttribute("indicadorUsoTodos") == null ){
	        filtroMunicipio.adicionarParametro(new ParametroSimples(
	                FiltroMunicipio.INDICADOR_USO,
	                ConstantesSistema.INDICADOR_USO_ATIVO));
        }    

        //Erro caso o usu�rio mandou filtrar sem nenhum par�metro
        if (!peloMenosUmParametroInformado) {
            throw new ActionServletException(
                    "atencao.filtro.nenhum_parametro_informado");
        }

        Collection municipios = null;

        //Obt�m a inst�ncia da Fachada
        Fachada fachada = Fachada.getInstancia();

        //adiciona as depend�ncias para serem mostradas na p�gina
        filtroMunicipio
                .adicionarCaminhoParaCarregamentoEntidade("microrregiao.regiao");

        //Faz a busca das empresas
        municipios = fachada.pesquisar(filtroMunicipio, Municipio.class
                .getName());

      	    // Aciona o controle de pagina��o para que sejam pesquisados apenas
			// os registros que aparecem na p�gina
			Map resultado = controlarPaginacao(httpServletRequest, retorno,
					filtroMunicipio, Municipio.class.getName());
			municipios = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");
			
			if(municipios.isEmpty()){
				//Nenhum logradouro cadastrado de acordo com os par�metros passados
	            throw new ActionServletException(
	                    "atencao.pesquisa.nenhumresultado", null, "munic�pio");
			}
            sessao.setAttribute("colecaoMunicipios", municipios);

        return retorno;
    }

}
