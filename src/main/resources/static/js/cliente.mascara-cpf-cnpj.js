var Brewer = Brewer || {};

Brewer.MascaraCpfCnpj = (function () {
	
	function MascaraCpfCnpj() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
		this.labelCpfCnpj = $('[for=cpfOuCnpj]');
		this.inputCpfCnpj = $('#cpfOuCnpj');
	}
	
	MascaraCpfCnpj.prototype.iniciar = function() {
		this.radioTipoPessoa.change(onTipoPessoaAlterado.bind(this));
	}
	
	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionada = $(evento.currentTarget);
		var documento = tipoPessoaSelecionada.data('documento');
		var mascara = tipoPessoaSelecionada.data('mascara');
		this.labelCpfCnpj.text(documento);
		this.inputCpfCnpj.val('');
		this.inputCpfCnpj.mask(mascara);
		this.inputCpfCnpj.removeAttr('disabled');
	}
	
	return MascaraCpfCnpj;
	
}()); 

$(function () {
	var mascaraCpfCnpj = new Brewer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
});