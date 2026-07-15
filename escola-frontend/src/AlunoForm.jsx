import { useState } from 'react';
import axios from 'axios';

function AlunoForm({ onAlunoCriado }) {
  const [nome, setNome] = useState('');
  const [serie, setSerie] = useState('');
  const [dataNascimento, setDataNascimento] = useState('');
  const [erro, setErro] = useState('');

  function handleSubmit(event) {
    event.preventDefault(); // evita a página recarregar sozinha, comportamento padrão do HTML

    axios.post('http://localhost:8080/alunos', {
      nome: nome,
      serie: serie,
      dataNascimento: dataNascimento
    })
      .then(response => {
        onAlunoCriado(response.data); // avisa o componente pai que deu certo
        // limpa o formulário
        setNome('');
        setSerie('');
        setDataNascimento('');
        setErro('');
      })
      .catch(error => {
        console.error('Erro ao criar aluno:', error);
        setErro('Não foi possível cadastrar o aluno. Verifique os dados.');
      });
  }

  return (
    <form onSubmit={handleSubmit}>
      <h2>Cadastrar Aluno</h2>

      {erro && <p style={{ color: 'red' }}>{erro}</p>}

      <div>
        <label>Nome: </label>
        <input
          type="text"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
        />
      </div>

      <div>
        <label>Série: </label>
        <input
          type="text"
          value={serie}
          onChange={(e) => setSerie(e.target.value)}
          required
        />
      </div>

      <div>
        <label>Data de Nascimento: </label>
        <input
          type="date"
          value={dataNascimento}
          onChange={(e) => setDataNascimento(e.target.value)}
          required
        />
      </div>

      <button type="submit">Cadastrar</button>
    </form>
  );
}

export default AlunoForm;