$(()=> {

    const filtrarButton = $("#filtrar-button");

    filtrarButton.click(()=> {
        const rangeArray = $("#range").val().split(" - ");

        const dataInicio = rangeArray[0];
        const dataFim = rangeArray[1];

        const valorMinimo = $("#valorMinimo").val();
        const valorMaximo = $("#valorMaximo").val();

        const tipo = $("#tipo").val();

        window.location.replace(
            `/hospedagem/listar?filtrar&dataInicio=${dataInicio}&dataFinal=${dataFim}&valorMinimo=${valorMinimo}&valorMaximo=${valorMaximo}&tipo=${tipo}`
        )
    })
})