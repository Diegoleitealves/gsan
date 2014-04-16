package gcom.gui.cadastro.localidade;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.LocalidadeClasse;
import gcom.cadastro.localidade.LocalidadePorte;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem;
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
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirLocalidadeAction extends GcomAction {

    public ActionForward execute(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {

        //Seta o retorno
        ActionForward retorno = actionMapping.findForward("telaSucesso");

        //Obt�m a inst�ncia da fachada
        Fachada fachada = Fachada.getInstancia();

        //Obt�m a sess�o
        HttpSession sessao = httpServletRequest.getSession(false);

        InserirLocalidadeActionForm inserirLocalidadeActionForm = (InserirLocalidadeActionForm) actionForm;
        
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        
        //------------ REGISTRAR TRANSA��O ----------------
        RegistradorOperacao registradorOperacao = new RegistradorOperacao(
				Operacao.OPERACAO_LOCALIDADE_INSERIR,
				new UsuarioAcaoUsuarioHelper(usuario,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
        
        Operacao operacao = new Operacao();
        operacao.setId(Operacao.OPERACAO_LOCALIDADE_INSERIR);

        OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
        operacaoEfetuada.setOperacao(operacao);
        //------------ REGISTRAR TRANSA��O ----------------
        
        String localidadeID = inserirLocalidadeActionForm.getLocalidadeID();
        String localidadeNome = inserirLocalidadeActionForm.getLocalidadeNome();
        Collection colecaoEnderecos = (Collection) sessao.getAttribute("colecaoEnderecos");
        String telefone = inserirLocalidadeActionForm.getTelefone();
        String ramal = inserirLocalidadeActionForm.getRamal();
        String fax = inserirLocalidadeActionForm.getFax();
        String email = inserirLocalidadeActionForm.getEmail();
        String menorConsumo = inserirLocalidadeActionForm.getMenorConsumo();
        String icms = inserirLocalidadeActionForm.getIcms();
        String centroCusto = inserirLocalidadeActionForm.getCentroCusto();
        String centroCustoEsgoto = inserirLocalidadeActionForm.getCentroCustoEsgoto();
        
        String eloID = inserirLocalidadeActionForm.getEloID();
        //String gerenciaID = inserirLocalidadeActionForm.getGerenciaID();
        String idUnidadeNegocio = inserirLocalidadeActionForm.getIdUnidadeNegocio();
        String classeID = inserirLocalidadeActionForm.getClasseID();
        String porteID = inserirLocalidadeActionForm.getPorteID();
        String informatizada = inserirLocalidadeActionForm.getInformatizada();
        String gerenteLocalidade = inserirLocalidadeActionForm.getGerenteLocalidade();
        String hidrometroLocalArmazenagem = inserirLocalidadeActionForm.getHidrometroLocalArmazenagem();
        //Inidicador se a localidade � a sede da empresa
        String sede = inserirLocalidadeActionForm.getSede();
        String municipio = inserirLocalidadeActionForm.getMunicipio();
        Localidade localidadeInserir = new Localidade();
        Collection colecaoPesquisa = null;

        sessao.removeAttribute("tipoPesquisaRetorno");
        //O c�digo da localidade � obrigat�rio.
        if (localidadeID == null || localidadeID.equalsIgnoreCase("")) {
            throw new ActionServletException(
            		"atencao.required",null,"C�digo");
        }

        //O nome da localidade � obrigat�rio.
        if (localidadeNome == null || localidadeNome.equalsIgnoreCase("")) {
            throw new ActionServletException(
            		"atencao.required",null,"Nome");
        }
        
        if (colecaoEnderecos != null && !colecaoEnderecos.isEmpty()) {
        	localidadeInserir = (Localidade) Util
            .retonarObjetoDeColecao(colecaoEnderecos);
        } 

        localidadeInserir.setId(new Integer(localidadeID));
        localidadeInserir.setDescricao(localidadeNome);
        
        
        //O telefone � obrigat�rio caso o ramal tenha sido informado.
        if (ramal != null && !ramal.equalsIgnoreCase("")) {
            localidadeInserir.setRamalfone(ramal);
            if (telefone == null || telefone.equalsIgnoreCase("")) {
                throw new ActionServletException(
                        "atencao.telefone_localidade_nao_informado");
            }
            else if (telefone.length() < 7){
            	throw new ActionServletException(
                "atencao.telefone_ou_fax_localidade_menor_sete_digitos", null, "Telefone");
            }
        }

        //Telefone.
        if (telefone != null && !telefone.equalsIgnoreCase("")) {
        	if (telefone.length() < 7){
        		throw new ActionServletException(
                "atencao.telefone_ou_fax_localidade_menor_sete_digitos", null, "Telefone");
        	}
        	else{
        		localidadeInserir.setFone(telefone);
        	}
        }

        //Fax.
        if (fax != null && !fax.equalsIgnoreCase("")) {
        	if (fax.length() < 7){
        		throw new ActionServletException(
                "atencao.telefone_ou_fax_localidade_menor_sete_digitos", null, "Fax");
        	}
        	else{
        		localidadeInserir.setFax(fax);
        	}
        }

        //E-mail.
        if (email != null && !email.equalsIgnoreCase("")) {
            localidadeInserir.setEmail(email);
        }

        //Menor Consumo.
        if (menorConsumo != null && !menorConsumo.equalsIgnoreCase("")) {
            localidadeInserir.setConsumoGrandeUsuario(Integer
                    .parseInt(menorConsumo));
        }

        //ICMS
        if (icms != null && !icms.equalsIgnoreCase("")) {
            localidadeInserir.setCodigoICMS(Integer
                    .parseInt(icms));
        }
        
        //Centro Custo
        if (centroCusto != null && !centroCusto.equalsIgnoreCase("")) {
            localidadeInserir.setCodigoCentroCusto(centroCusto);
        }
        
        //Centro Custo de Esgoto
        if (centroCustoEsgoto != null && !centroCustoEsgoto.equalsIgnoreCase("")) {
            localidadeInserir.setCodigoCentroCustoEsgoto(centroCustoEsgoto);
        }
        
        
        
        //Ger�ncia Regional
        if (idUnidadeNegocio == null 
        		|| idUnidadeNegocio.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
        	//Informe Ger�ncia Regional
        	throw new ActionServletException("atencao.required",null,"Unidade de Neg�cio");
        }else{        	
        	UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
            unidadeNegocio.setId(new Integer(idUnidadeNegocio));
            localidadeInserir.setUnidadeNegocio(unidadeNegocio);	
        }
        

        //Elo.
        Localidade localidadeElo = new Localidade();
        if (eloID != null
                && !eloID.equalsIgnoreCase(String
                        .valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))) {

            FiltroLocalidade filtroLocalidadeElo = new FiltroLocalidade();

            filtroLocalidadeElo.adicionarParametro(new ParametroSimples(
                    FiltroLocalidade.ID_UNIDADE_NEGOCIO, localidadeInserir
                            .getUnidadeNegocio().getId()));

            filtroLocalidadeElo.adicionarParametro(new ParametroSimples(
                    FiltroLocalidade.ID, eloID));

            filtroLocalidadeElo.adicionarParametro(new ParametroSimples(
                    FiltroLocalidade.INDICADORUSO,
                    ConstantesSistema.INDICADOR_USO_ATIVO));

            //Retorna localidade - Elo
            colecaoPesquisa = fachada.pesquisar(filtroLocalidadeElo,
                    Localidade.class.getName());

            if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
                //O c�digo do Elo n�o existe na tabela Localidade
                throw new ActionServletException(
                        "atencao.pesquisa_elo_nao_inexistente");
            } else {
                localidadeElo = (Localidade) Util
                        .retonarObjetoDeColecao(colecaoPesquisa);
                if (localidadeElo.getId().intValue() != localidadeElo
                        .getLocalidade().getId().intValue()) {
                    //A localidade escolhida n�o � um Elo
                    throw new ActionServletException(
                            "atencao.localidade_nao_e_elo");
                } else {
                    localidadeInserir.setLocalidade(localidadeElo);
                }
            }
        }
        else{
        	localidadeInserir.setLocalidade(localidadeInserir);
        }

        //Classe
        if (classeID == null 
        		|| classeID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
        	//Informe Classe
        	throw new ActionServletException("atencao.required",null,"Classe");
        }else{  
        	 LocalidadeClasse classe = new LocalidadeClasse();
             classe.setId(new Integer(classeID));
             localidadeInserir.setLocalidadeClasse(classe);
        }
        
        //Porte
        if (porteID == null 
        		|| porteID.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
        	// Informe Porte
        	throw new ActionServletException("atencao.required",null,"Porte");
        }else{  
        	LocalidadePorte porte = new LocalidadePorte();
            porte.setId(new Integer(porteID));
            localidadeInserir.setLocalidadePorte(porte);
        }
        
        //Local Armazenagem Hidrometro
        if (hidrometroLocalArmazenagem != null 
        		&& hidrometroLocalArmazenagem.equals("")){
        	HidrometroLocalArmazenagem hidrometroLocalArmazenagemID = new HidrometroLocalArmazenagem();
        	hidrometroLocalArmazenagemID.setId(new Integer(hidrometroLocalArmazenagem));
            localidadeInserir.setHidrometroLocalArmazenagem(hidrometroLocalArmazenagemID);
        }
        
        //Seta o Gerencia Regional de acordo com a Unidade de Negocio
    	FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
    	filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID,idUnidadeNegocio));
    	filtroUnidadeNegocio.adicionarCaminhoParaCarregamentoEntidade(FiltroUnidadeNegocio.GERENCIA);
    	filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(
                FiltroUnidadeNegocio.INDICADOR_USO,
                ConstantesSistema.INDICADOR_USO_ATIVO));

        Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio,
                UnidadeNegocio.class.getName());

        UnidadeNegocio unidadeNegocio = (UnidadeNegocio) colecaoUnidadeNegocio.iterator().next(); 
        
        if(unidadeNegocio.getGerenciaRegional() != null){
        	GerenciaRegional gerenciaRegional = new GerenciaRegional();
        	gerenciaRegional.setId(unidadeNegocio.getGerenciaRegional().getId());
        	
			if (gerenteLocalidade != null && !gerenteLocalidade.equals("")){
			
				Integer clienteFuncionario = fachada.verificarClienteSelecionadoFuncionario(new Integer(gerenteLocalidade));
				
				if(clienteFuncionario == null){
					throw new ActionServletException("atencao.cliente_selecionado_nao_e_funcionario");
				}
			}
        	
        	localidadeInserir.setGerenciaRegional(gerenciaRegional);
        }
        
        //Informatizada
        if (informatizada == null 
        		|| informatizada.equals("")){

        	// Informatizada
        	throw new ActionServletException("atencao.required",null,"Informatizada");
        }else{  
            localidadeInserir.setIndicadorLocalidadeInformatizada(new Short(informatizada));
        }

        if(gerenteLocalidade != null && !gerenteLocalidade.equals("")){
        	Cliente cliente = new Cliente();
        	cliente.setId(new Integer(gerenteLocalidade));
        	
        	localidadeInserir.setCliente(cliente);
        }
        
        //Sede
        if (sede == null 
        		|| sede.equals("")){

      
        	throw new ActionServletException("atencao.required",null,"Sede");
        
        }else{ 
        	
        	if ( inserirLocalidadeActionForm.getSede() != null && 
            		inserirLocalidadeActionForm.getSede().equals("1")){
            	
            	FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
            	
//            	filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, 
//            			inserirLocalidadeActionForm.getLocalidadeID()));
            	
            	boolean jaExisteSede = false;
            	
            	Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade,Localidade.class.getName());
            	
            	if (colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
            		
            		Iterator colecaoLocalidadeIterator = colecaoLocalidade.iterator();
                	
                	Localidade localidade = null;
                	
                	String localidadeSede = "";
                	
                	while ( colecaoLocalidadeIterator.hasNext() && jaExisteSede == false ){
                		
                		localidade = (Localidade) colecaoLocalidadeIterator.next();
                		
                		if ( localidade.getIndicadorLocalidadeSede() == 1){
                			
                			localidadeSede = ""+localidade.getId();
                			
                			jaExisteSede = true;
                			
                		}
                		
                	}
                	
                	if (jaExisteSede){
                		
                		throw new ActionServletException(
                                "atencao.ja_existe_localidade_sede", null, localidadeSede);
                		
                	}else{
                		
                		localidadeInserir.setIndicadorLocalidadeSede(new Short(sede));
                		
                	}
            		
            	}
            	
            }else{
            	
            	localidadeInserir.setIndicadorLocalidadeSede(new Short(sede));
            	
            }
        	
        }
        
        //Munic�pio Principal
        if(municipio != null && !municipio.equals("")){
        	FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
        	filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, municipio));
        	

            Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio,
                    Municipio.class.getName());
            
            if(colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){
            	Municipio objMunicipio = (Municipio) colecaoMunicipio.iterator().next(); 
            	localidadeInserir.setMunicipio(objMunicipio);
            }else{
            	throw new ActionServletException("atencao.municipio.inexistente");
            }
        }
        
        //Indicador de Uso
        localidadeInserir
                .setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

        //Ultima altera��o
        localidadeInserir.setUltimaAlteracao(new Date());

        FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

        filtroLocalidade.adicionarParametro(new ParametroSimples(
                FiltroLocalidade.ID, localidadeInserir.getId()));

        //Verificar exist�ncia da Localidade
        colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class
                .getName());

        if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {
            //Localidade j� existe
            throw new ActionServletException(
                    "atencao.pesquisa_localidade_ja_cadastrada", null, localidadeID);
        } else {
        	Integer idLocalidade = null;
            
            //------------ REGISTRAR TRANSA��O ----------------
        	localidadeInserir.setOperacaoEfetuada(operacaoEfetuada);
        	localidadeInserir.adicionarUsuario(usuario, 
            		UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
            registradorOperacao.registrarOperacao(localidadeInserir);
            //------------ REGISTRAR TRANSA��O ----------------  
            idLocalidade = fachada.inserirLocalidadeRetorno(localidadeInserir);

            montarPaginaSucesso(httpServletRequest, "Localidade de c�digo  "
                    + localidadeInserir.getId().intValue()
                    + " inserida com sucesso.", "Inserir outra Localidade",
                    "exibirInserirLocalidadeAction.do?menu=sim",
                    "exibirAtualizarLocalidadeAction.do?idRegistroInseridoAtualizar="
					+ idLocalidade,
					"Atualizar Localidade Inserida");

        }

        sessao.removeAttribute("colecaoEnderecos");

        //devolve o mapeamento de retorno
        return retorno;
    }

}
