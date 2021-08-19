
$(() => {

    const estadoSelect = $('#estado').change(function (){
        const siglaEstado = this.value;
        const nome = $('input[name=nome]').val().trim();
        const email = $('input[name=email]').val().trim();
        const telefone = $('input[name=telefone]').val().trim();
        const chavePix = $('input[name=chavePix]').val().trim();

        let url = `/profissional/cadastrar?uf=${siglaEstado}`;

        if (chavePix !== '') {
            url +=`&chavePix=${chavePix}`;
        }

        if (nome !== '') {
            url += `&nome=${nome}`;
        }

        if (email !== '') {
            url+=`&email=${email}`
        }

        if (telefone !== '') {
            url += `&telefone=${telefone}`;
        }

        console.log(url);
        window.location.href = url;

    })
})