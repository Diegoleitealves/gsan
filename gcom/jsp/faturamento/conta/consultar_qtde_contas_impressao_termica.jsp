<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="gcom.util.ConstantesSistema"%>
<%@ page import="gcom.cadastro.sistemaparametro.SistemaParametro"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>
<%@ include file="/jsp/util/titulo.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet"
	href="<bean:message key="caminho.css"/>EstilosCompesa.css"
	type="text/css">
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/regras_validator.js"></script>
<html:javascript staticJavascript="false" formName="ConsultarQtdeContaImpressaoTermicaActionForm"
	dynamicJavascript="false" />
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>validacao/ManutencaoRegistro.js"></script>
<script language="JavaScript"
	src="<bean:message key="caminho.js"/>util.js"></script>

</head>

<body leftmargin="5" topmargin="5"
	onload="setarFoco('${requestScope.nomeCampo}');">
	<html:form action="/exibirConsultarQtdeContaImpressaoTermicaAction"
		method="post">

		<%@ include file="/jsp/util/cabecalho.jsp"%>
		<%@ include file="/jsp/util/menu.jsp"%>

		<table width="770" border="0" cellspacing="5" cellpadding="0">
			<tr>
				<td width="130" valign="top" class="leftcoltext">
					<div align="center">
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>

						<%@ include file="/jsp/util/informacoes_usuario.jsp"%>

						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>

						<%@ include file="/jsp/util/mensagens.jsp"%>

						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
						<p align="left">&nbsp;</p>
					</div>
				</td>

				<td width="630" valign="top" class="centercoltext">
					<table height="100%">
						<tr>
							<td></td>
						</tr>
					</table>

					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="11"><img border="0"
								src="<bean:message key="caminho.imagens"/>parahead_left.gif" />
							</td>
							<td class="parabg">Consultar Quantidade de Contas para
								Impressao T&eacute;rmica</td>
							<td width="11"><img border="0"
								src="<bean:message key="caminho.imagens"/>parahead_right.gif" />
							</td>
						</tr>
					</table>
					<p>&nbsp;</p>

					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td height="0">
								<table width="100%" bgcolor="#99CCFF">
									<!--header da tabela interna -->
									<tr bordercolor="#FFFFFF">
										<td width="8%" bgcolor="#90c7fc" align="center">
											<div align="center">
												<font color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>
														Ano/M�s </strong>
												</font>
											</div>
										</td>
										<td width="16%" bgcolor="#90c7fc" align="center">
											<div align="center">
												<font color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>
														Localidade </strong>
												</font>
											</div>
										</td>
										<td width="18%" bgcolor="#90c7fc" align="center">
											<div align="center">
												<font color="#000000" style="font-size: 9px"
													face="Verdana, Arial, Helvetica, sans-serif"> <strong>
														Qtde de Contas </strong>
												</font>
											</div>
										</td>
									</tr>
								</table>
								<p>&nbsp;</p>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><input type="button" name="Button"
								class="bottonRightCol" value="Limpar"
								onclick="window.location.href='<html:rewrite page="/exibirConsultarQtdeContaImpressaoTermicaAction.do?menu=sim"/>'">
							</td>
							<td><div align="right">
									<input type="button" name="Button" class="bottonLeftCol"
										value="Filtrar" onclick="javascript:chamarFiltrar();"
										url="ConsultarQtdeContaImpressaoTermicaAction.do" />
								</div></td>
						</tr>
						</td>
						</tr>

					</table> <%@ include file="/jsp/util/rodape.jsp"%>

	</html:form>
</body>
</html:html>