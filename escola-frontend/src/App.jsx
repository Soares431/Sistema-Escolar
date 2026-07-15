import { useEffect, useState } from 'react';
import axios from 'axios';
import AlunoForm from './AlunoForm';

function App() {
  const [alunos, setAlunos] = useState([]);

  function carregarAlunos() {
    axios.get('http://localhost:8080/alunos')
      .then(response => setAlunos(response.data))
      .catch(error => console.error('Erro ao buscar alunos:', error));
  }

  useEffect(() => {
    carregarAlunos();
  }, []);

  function handleAlunoCriado(novoAluno) {
    setAlunos([...alunos, novoAluno]); // adiciona o novo aluno na lista, sem precisar recarregar tudo
  }

  return (
    <div>
      <h1>Alunos cadastrados</h1>
      <ul>
        {alunos.map(aluno => (
          <li key={aluno.matricula}>
            {aluno.nome} — {aluno.serie}
          </li>
        ))}
      </ul>

      <AlunoForm onAlunoCriado={handleAlunoCriado} />
    </div>
  );
}

export default App;