package gcom.gui.cadastro.localidade;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
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

public class InserirSetorComercialAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        PesquisarAtualizarSetorComercialActionForm form = (PesquisarAtualizarSetorComercialActionForm) actionForm;

        HttpSession sessao = httpServletRequest.getSession(false);
        
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

        //------------ REGISTRAR TRANSA��O ----------------
        RegistradorOperacao registradorOperacao = new RegistradorOperacao(
				Operacao.OPERACAO_SETOR_COMERCIAL_INSERIR,
				new UsuarioAcaoUsuarioHelper(usuario,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
        
        Operacao operacao = new Operacao();
        operacao.setId(Operacao.OPERACAO_SETOR_COMERCIAL_INSERIR);

        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
        operacaoEfetuada.setOperacao(operacao);
        //------------ REGISTRAR TRANSA��O ----------------

        String localidadeID = form.getLocalidadeID();
        String setorComercialCD = form.getSetorComercialCD();
        String setorComercialNome = form.getSetorComercialNome();
        String municipioID = form.getMunicipioID();
        String indicadorSetorAlternativo = "";
        
        //Indicador Setor Alternativo
        if ( form.getIndicadorSetorAlternativo() != null && !form.getIndicadorSetorAlternativo().equals("") ) {
    		indicadorSetorAlternativo = form.getIndicadorSetorAlternativo();	
    	}

        if (Util.verificarNaoVazio(localidadeID)) {

        	Collection colecaoPesquisa = null;

            FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

            filtroLocalidade.adicionarParametro(
            	new ParametroSimples(
            		FiltroLocalidade.ID, 
                	localidadeID));

            filtroLocalidade.adicionarParametro(
            	new ParametroSimples(
            		FiltroLocalidade.INDICADORUSO,
            		ConstantesSistema.INDICADOR_USO_ATIVO));

            //Retorna localidade
            colecaoPesquisa = 
            	this.getFachada().pesquisar(filtroLocalidade,
                    Localidade.class.getName());

            if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
                //Localidade inexistente
                throw new ActionServletException("atencao.pesquisa.localidade_inexistente");
            } else {
                Localidade localidade = 
                	(Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

                if (setorComercialCD == null || setorComercialCD.equalsIgnoreCase("")) {
                    //C�digo do setor comercial n�o informado.
                	throw new ActionServletException("atencao.codigo_setor_comercial_nao_informado");
                } else if (setorComercialNome == null
                        || setorComercialNome.equalsIgnoreCase("")) {
                    //Nome do setor comercial n�o informado.
                    throw new ActionServletException("atencao.nome_setor_comercial_nao_informado");
                } else if (municipioID == null || municipioID.equalsIgnoreCase("")) {
                     //C�digo do munic�pio n�o informado.
                    throw new ActionServletException("atencao.municipio_nao_informado");
                } else {
                	
                	FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

                    filtroMunicipio.adicionarParametro(
                    	 new ParametroSimples(
                    		FiltroMunicipio.ID, 
                    		municipioID));

                    filtroMunicipio.adicionarParametro(
                    	new ParametroSimples(
                			FiltroMunicipio.INDICADOR_USO,
                			ConstantesSistema.INDICADOR_USO_ATIVO));

                    //Retorna municipio
                    colecaoPesquisa = this.getFachada().pesquisar(
                    		filtroMunicipio, 
                    		Municipio.class.getName());

                    if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
                    	throw new ActionServletException("atencao.pesquisa.municipio_inexistente");
                    } else {
                    	Municipio municipio = 
                    		(Municipio) Util.retonarObjetoDeColecao(colecaoPesquisa);
                          
                    	//Cria o objeto setorComercial que ser�
                    	// inserido na base
                    	SetorComercial setorComercial = new SetorComercial();
                    	
                    	setorComercial.setCodigo(Integer.parseInt(setorComercialCD));
                    	setorComercial.setDescricao(setorComercialNome);
                    	setorComercial.setLocalidade(localidade);
                    	setorComercial.setMunicipio(municipio);
                    	setorComercial.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
                    	setorComercial.setUltimaAlteracao(new Date());
                    	setorComercial.setIndicadorSetorAlternativo(new Short (indicadorSetorAlternativo) );
                          
                        //------------ REGISTRAR TRANSA��O ----------------
                        setorComercial.setOperacaoEfetuada(operacaoEfetuada);
                        setorComercial.adicionarUsuario(usuario, 
                      			UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
                      	registradorOperacao.registrarOperacao(setorComercial);
                        //------------ REGISTRAR TRANSA��O ----------------  

                      	Collection colecaoFontes = (Collection)
                      		this.getSessao(httpServletRequest).getAttribute("colecaoFonteCaptacao");
                      	
                      	Integer codigoSetorComercialInserido = 
                      		(Integer) this.getFachada().inserirSetorComercial(setorComercial,colecaoFontes);
                                
                        montarPaginaSucesso(httpServletRequest,
                        		"Setor Comercial de c�digo " + setorComercial.getCodigo()
                                	+ " da localidade "  + localidade.getId() 
                                	+ " - "  + localidade.getDescricao().toUpperCase() 
                                	+ " inserido com sucesso.",
                                	"Inserir outro Setor Comercial",
                                	"exibirInserirSetorComercialAction.do?menu=sim",
                                	"exibirAtualizarSetorComercialAction.do?menu=sim&setorComercialID=" + 
                                	codigoSetorComercialInserido, "Atualizar Setor Comercial Inserido");
                     }
                }
            }
        } else {
            // Campo localidadeID n�o informado.
            throw new ActionServletException("atencao.localidade_nao_informada");
        }

        //devolve o mapeamento de retorno
        return retorno;
    }

}
