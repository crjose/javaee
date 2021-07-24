/**
 * Confirmar a exclusão de um contato
 * @Author Cristovão Rocha
 * @param idcon
 */

function confirmar(idcon){
	let resposta = confirm("Confirma que deseja excluir este contato? ")
	if(resposta === true){
		//redirecionando para o servlet Controller
		window.location.href = "delete?idcon=" + idcon
		
	}
	
	
}