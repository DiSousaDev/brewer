$(function() {
	
	var modal = $('#modalCadastroRapidoEstilo')
	var botaoSalvar = modal.find('.js-modal-cadastro-estilo-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeEstilo = $('#nomeEstilo');
	
	modal.on('shown.bs.modal', onModalShow);
	
	function onModalShow() {
		inputNomeEstilo.focus();
		console.log('teste');
	}
	
});