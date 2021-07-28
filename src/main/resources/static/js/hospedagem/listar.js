$(()=> {

    const filtrarButton = $("#filtrar-button");

    filtrarButton.click(()=> {
        const dataInicio = $("#dataInicio").val();
        const dataFim = $("#dataInicio").val();
        const valorMinimo = $("#valorMinimo").val();
        const valorMaximo = $("#valorMaximo").val();

        window.location.replace(
            `/hospedagem/listar?filtrar&dataInicio=${dataInicio}&dataFinal=${dataFim}&valorMinimo=${valorMinimo}&valorMaximo=${valorMaximo}`
        )
    })
})