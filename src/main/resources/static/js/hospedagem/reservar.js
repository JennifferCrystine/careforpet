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