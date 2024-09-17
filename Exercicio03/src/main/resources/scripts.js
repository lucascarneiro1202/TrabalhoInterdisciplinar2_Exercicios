var porta = 4567;

// ========================= Funcao para mostrar resultado ========================= 

document.body.addEventListener('load', (e) => {
  mostrarResposta("");
});

var divMostrarResposta = document.querySelector("#mostrarResposta");

function mostrarResposta (str)
{
  divMostrarResposta.innerHTML = `<p id=\"mensagem\">${str}</p>`;
}

function mostrarLista (arrObjLivros)
{
//Definir dados locais
  var tHead = document.querySelector("#mostrarLista #table-head");
  var tBody = document.querySelector("#mostrarLista #table-body");
//Esvaziar tabela
  tHead.innerHTML = "";
  tBody.innerHTML = "";
//Testar array de objetos de livros
  if (arrObjLivros[0] == undefined)
  {
    divMostrarResposta.innerHTML = `<p id=\"mensagem\">Não foi possível encontrar o livro especificado. Tente outro ID.</p>`;
  }
  else
  {
  //Mostrar resultado
    mostrarResposta("Livro lido com sucesso!");
  //Definir linha de cabecalho
    const cabecalho = document.createElement('tr');
    cabecalho.id = "tabelaTitulos";
  //Substituir dados de cabecalho
    cabecalho.innerHTML = `
      <th class="tableCell">ID</th>
      <th class="tableCell">Nome</th>
      <th class="tableCell">Autor</th>
      <th class="tableCell">Preço</th>
      <th class="tableCell">Sinopse</th>
      <th class="tableCell">Gênero</th>
      <th class="tableCell">Lançamento</th>
      <th class="tableCell">Páginas</th>
      <th class="tableCell">Editora</th>
    `;
  //Appendar linha de cabecalho
    tHead.appendChild(cabecalho);
  //Passar por cada elemento do array
    for (let i = 0; i < arrObjLivros.length; i++)
    {
    //Definir dados locais
      let objLivro = arrObjLivros[i];
      const row = document.createElement('tr');
      row.id = `table-row-${objLivro.id}`;
    //Adicionar elementos na tabela
      row.innerHTML += `
        <td class="tableCell" id="tableCell-id">${objLivro.id}</td>
        <td class="tableCel" id="tableCell-nome">${objLivro.nome}</td>
        <td class="tableCell" id="tableCell-autor">${objLivro.autor}</td>
        <td class="tableCell" id="tableCell-preco">${objLivro.preco}</td>
        <td class="tableCell" id="tableCell-sinopse">${objLivro.sinopse.substring(0, 30)}[...]</td>
        <td class="tableCell" id="tableCell-genero">${objLivro.genero}</td>
        <td class="tableCell" id="tableCell-lancamento">${objLivro.lancamento}</td>
        <td class="tableCell" id="tableCell-paginas">${objLivro.paginas}</td>
        <td class="tableCell" id="tableCell-editora">${objLivro.editora}</td>
      `;
    //Appendar elemento
      tBody.appendChild(row);
    }
  }
}

function atualizarTabela ()
{
//Definir dados locais
  let tabelaTitulos = document.querySelector('#tabelaTitulos');
//Testar se a tabela esta ativa
  if (tabelaTitulos != null)
  {
    objLivrosGetAll();
  }
}

// ========================= POST LIVRO unitario =========================

var formularioInserir = document.querySelector('#formInserir');

function objLivroInserir (form)
{
//Definir dados locais
  let objLivro = {
    nome: form.querySelector("#nome").value,
    autor: form.querySelector("#autor").value,
    preco: form.querySelector("#preco").value,
    sinopse: form.querySelector("#sinopse").value,
    genero: form.querySelector("#genero").value,
    lancamento: form.querySelector("#lancamento").value,
    paginas: form.querySelector("#paginas").value,
    editora: form.querySelector("#editora").value
  }
//Retornar
  return objLivro;
}

function inserirLivro (objLivro)
{
  fetch (`http://localhost:${porta}/livro/insert`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(objLivro)
  })
    .then(response => response.json()) 
    .then(data => mostrarResposta(`${data.message}`))
    .catch(err => console.log(err));      
}

function limparInsert (form)
{
//Definir dados locais
  let nome = form.querySelector("#nome");
  let autor = form.querySelector("#autor");
  let preco = form.querySelector("#preco");
  let sinopse = form.querySelector("#sinopse");
  let genero = form.querySelector("#genero");
  let lancamento = form.querySelector("#lancamento");
  let paginas = form.querySelector("#paginas");
  let editora = form.querySelector("#editora");
//Esvaziar campos
  nome.value = "";
  autor.value = "";
  preco.value = "";
  sinopse.value = "";
  genero.value = "";
  lancamento.value = "";
  paginas.value = "";
  editora.value = "";
}

formularioInserir.addEventListener('submit', (e) => 
{
//Prevenir o recarregamento da pagina
  e.preventDefault();
//Definir objeto com os valores inseridos
  let objLivro = objLivroInserir(e.target); 
//Testar se o objeto esta completamente preenchido
  let property;
  let test = true;
  for ( const p in objLivro )
  {
    if (objLivro[p].length == 0)
      test = false;
  }    
//Enviar requisicao para o back-end
  if (test)
    inserirLivro(objLivro);
  atualizarTabela();
//Limpar campos
  limparInsert(e.target);
})

// ========================= GET LIVRO em grupo =========================

var buttonLerTodos = document.querySelector("#mostrarTodos");

function objLivrosGetAll ()
{
//Fazer chamada ao back-end
  fetch(`http://localhost:${porta}/livro`, {
    method: "GET",
    headers: {
      'Content-Type': 'application/json',
      'Origin': `http://localhost:${porta}`
    }
  })
    .then((res) => res.json())
    .then((data) => mostrarLista(data.data))
}

buttonLerTodos.addEventListener ('click', (e) => objLivrosGetAll());


// ========================= GET LIVRO em grupo (filtrado) =========================

var formularioFiltrarLivros = document.querySelector("#filtrarLivro");

async function getLivrosOrderBy (id)
{
//Definir dados locais
  let arrObjLivro = [];
//Tentar fazer a chamada
  try
  {
    const res = await fetch (`http://localhost:${porta}/livro/${id}`, {
      method: "GET",
      headers: {
        'Content-Type': 'application/json',
        'Origin': `http://localhost:${porta}`
      }
    }); 
    const data = await res.json();
    arrObjLivro[0] = data.data;
    mostrarLista(arrObjLivro);
    return data;
  }
  catch (error) 
  {
    console.error('Erro ao buscar:', error);
    throw error;
  }
}

formularioFiltrarLivros.addEventListener('submit', (e) => 
{
//Definir dados locais
  let form = e.target;
  let id = form.querySelector("#idLivroFiltrar").value;
//Impedir o recarregamento da pagina
  e.preventDefault();
//Definir array de objetos resultantes
  getLivrosOrderBy(id); 
})

// ========================= PUT LIVRO unitario ========================= (PUT é substituído por GET/POST)

async function atualizarLivro (objLivro)
{ 
//Tentar fazer a chamada
  try
  {
    const res = await fetch (`http://localhost:${porta}/livro/update/${objLivro.id}`, {
      method: "PUT",
      headers: { 
        "Content-type": "application/json"
      },
      body: JSON.stringify(objLivro),
    }); 
    const data = await res.json();
    mostrarResposta(`${data.message}`);
  }
  catch (error) 
  {
    console.error('Erro ao buscar:', error);
    throw error;
  }
}

var formularioEditarLivro = document.querySelector("#editarLivro");

function inputsEditarLivro (codigo, objLivro)
{
//Definir dados locais
  let row = document.querySelector(`#table-row-${codigo}`);
  let cellNome = row.querySelector(`#tableCell-nome`);
  let cellAutor = row.querySelector(`#tableCell-autor`);
  let cellPreco = row.querySelector(`#tableCell-preco`);
  let cellSinopse = row.querySelector(`#tableCell-sinopse`);
  let cellGenero = row.querySelector(`#tableCell-genero`);
  let cellLancamento = row.querySelector(`#tableCell-lancamento`);
  let cellPaginas = row.querySelector(`#tableCell-paginas`);
  let cellEditora = row.querySelector(`#tableCell-editora`);
//Substituir as células por inputs
  cellNome.innerHTML = `<input type="text" class="inputInserir" value="${objLivro.nome}">`;
  cellAutor.innerHTML = `<input type="text" class="inputInserir" value="${objLivro.autor}">`;
  cellPreco.innerHTML = `<input type="number" class="inputInserir" value="${objLivro.preco}">`;
  cellSinopse.innerHTML = `<input type="text" class="inputInserir" value="${objLivro.sinopse}">`;
  cellGenero.innerHTML = `<input type="text" class="inputInserir" value="${objLivro.genero}">`;
  cellLancamento.innerHTML = `<input type="number" class="inputInserir" value="${objLivro.lancamento}">`;
  cellPaginas.innerHTML = `<input type="number" class="inputInserir" value="${objLivro.paginas}">`;
  cellEditora.innerHTML = `<input type="text" class="inputInserir" value="${objLivro.editora}">`;
//Adicionar botoes de confirmacao-cancelamento
  let botoes = document.createElement('div');
  botoes.innerHTML = `
    <div id="botoesEdicao">
      <button id="botaoConfirmarEdicao">Confirmar</button>
      <button id="botaoCancelarEdicao">Cancelar</button>
    </div>
  `;
  row.appendChild(botoes);
}

function inputsEditarPreencherLivro (codigo, objLivro)
{
//Definir dados locais
  let row = document.querySelector(`#table-row-${codigo}`);
  let confirmar = row.querySelector('#botaoConfirmarEdicao');
  let cancelar = row.querySelector('#botaoCancelarEdicao');
//Definir eventos
  confirmar.addEventListener('click', async () => 
  {
  //Preencher objeto com novos valores
    objLivro.nome = row.querySelector('#tableCell-nome .inputInserir').value;
    objLivro.autor = row.querySelector('#tableCell-autor .inputInserir').value;
    objLivro.preco = parseFloat( row.querySelector('#tableCell-preco .inputInserir').value );
    objLivro.sinopse = row.querySelector('#tableCell-sinopse .inputInserir').value;
    objLivro.genero = row.querySelector('#tableCell-genero .inputInserir').value;
    objLivro.lancamento = parseInt( row.querySelector('#tableCell-lancamento .inputInserir').value );
    objLivro.paginas = parseInt( row.querySelector('#tableCell-paginas .inputInserir').value );
    objLivro.editora = row.querySelector('#tableCell-editora .inputInserir').value;
  //Inserir novo objeto
    await atualizarLivro(objLivro);
    await getLivrosOrderBy(objLivro.id);
  })
  cancelar.addEventListener('click', () => {
    location.reload();
  })
}

formularioEditarLivro.addEventListener ('submit', async (e) => 
{
//Impedir o recarregamento da página
  e.preventDefault();
//Definir dados locais
  let id = e.target.querySelector("#idLivroEditar").value;
//Mostrar o livro individualmente
  let objLivro = await getLivrosOrderBy(id);
//Substituir os valores para edição
  inputsEditarLivro(id, objLivro.data);
  inputsEditarPreencherLivro(id, objLivro.data);
})

// ========================= DELETE LIVRO unitario ========================= 

var formularioExcluirLivro = document.querySelector("#excluirLivro");

function deleteLivro (id)
{
  fetch(`http://localhost:${porta}/livro/delete/${id}`, {
    method: "DELETE",
    origin: `http://localhost:${porta}`
  })
  .then((res) => res.json())
  .then((data) => mostrarResposta(`${data.message}`));
}

formularioExcluirLivro.addEventListener ('submit', (e) => 
{
//Impedir o recarregamento da página
  e.preventDefault();
//Definir dados locais
  let id = e.target.querySelector("#idLivroExcluir").value;
//Funcao para excluir livro
  deleteLivro(id);
//Esvaziar input
  let idLivroExcluir = document.querySelector('#idLivroExcluir');
  idLivroExcluir.value = "";
  atualizarTabela();
})
