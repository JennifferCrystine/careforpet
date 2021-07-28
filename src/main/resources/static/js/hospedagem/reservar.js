$(()=>{

    const button = $("#reservar-button");
    button.click(()=> {
        $.get(`/hospedagem/reservar?id=${$('#id').val()}&dataInicio=${$('#dataInicio').val()}&dataFinal=${$('#dataFinal').val()}`)
            .then(data => {
                console.log(data);
                const chave = data['chavePix'];
                const valor = data['valor'];

                const div = $('#pagar-div');
                div.html(`<label>Chave Pix: ${chave}</label><br><label>Valor: ${valor}</label>`)
            })
    })

})

// Datepicker
$(function() {
    $('input[name="daterange"]').daterangepicker({
        opens: 'right',
        locale: {
            "format": "DD/MM/YYYY",
            "separator": " - ",
            "applyLabel": "Aplicar",
            "cancelLabel": "Cancelar",
            "fromLabel": "De",
            "toLabel": "Até",
            "customRangeLabel": "Custom",
            "daysOfWeek": [
                "Dom",
                "Seg",
                "Ter",
                "Qua",
                "Qui",
                "Sex",
                "Sáb"
            ],
            "monthNames": [
                "Janeiro",
                "Fevereiro",
                "Março",
                "Abril",
                "Maio",
                "Junho",
                "Julho",
                "Agosto",
                "Setembro",
                "Outubro",
                "Novembro",
                "Dezembro"
            ],
        }
    }, function(start, end, label) {
        console.log("Uma nova escolha de datas foi feita: " + start.format('YYYY-MM-DD') + ' até ' + end.format('YYYY-MM-DD'));
    });
});