import { useState, useEffect } from 'react';
import axios from 'axios';

function AlunoForm({ alunoEditando, onSalvo, onCancelar }) {
  const [nome, setNome] = useState('');
  const [serie, setSerie] = useState('');
  const [dataNascimento, setDataNascimento] = useState('');
  const [erro, setErro] = useState('');

  // Sempre que "alunoEditando" mudar, preenche o formulário com os dados dele
  useEffect(() => {
    if (alunoEditando) {
      setNome(alunoEditando.nome);
      setSerie(alunoEditando.serie);
      setDataNascimento(alunoEditando.dataNascimento);
    } else {
      setNome('');
      setSerie('');
      setDataNascimento('');
    }
  }, [alunoEditando]);

  function handleSubmit(event) {
    event.preventDefault();

    const dados = { nome, serie, dataNascimento };

    if (alunoEditando) {
      // Modo edição: PUT
      axios.put(`http://localhost:8080/alunos/${alunoEditando.matricula}`, dados)
        .then(response => {
          onSalvo(response.data);
          setErro('');
        })
        .catch(error => {
          console.error('Erro ao atualizar aluno:', error);
          setErro('Não foi possível atualizar o aluno.');
        });
    } else {
      // Modo criação: POST
      axios.post('http://localhost:8080/alunos', dados)
        .then(response => {
          onSalvo(response.data);
          setNome('');
          setSerie('');
          setDataNascimento('');
          setErro('');
        })
        .catch(error => {
          console.error('Erro ao criar aluno:', error);
          setErro('Não foi possível cadastrar o aluno.');
        });
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <h2>{alunoEditando ? 'Editar Aluno' : 'Cadastrar Aluno'}</h2>

      {erro && <p style={{ color: 'red' }}>{erro}</p>}

      <div>
        <label>Nome: </label>
        <input type="text" value={nome} onChange={(e) => setNome(e.target.value)} required />
      </div>

      <div>
        <label>Série: </label>
        <input type="text" value={serie} onChange={(e) => setSerie(e.target.value)} required />
      </div>

      <div>
        <label>Data de Nascimento: </label>
        <input type="date" value={dataNascimento} onChange={(e) => setDataNascimento(e.target.value)} required />
      </div>

      <button type="submit">{alunoEditando ? 'Salvar alterações' : 'Cadastrar'}</button>
      {alunoEditando && (
        <button type="button" onClick={onCancelar} style={{ marginLeft: '8px' }}>
          Cancelar
        </button>
      )}

    </form>
  );
}

export default AlunoForm;