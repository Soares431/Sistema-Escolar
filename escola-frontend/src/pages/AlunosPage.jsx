import { useEffect, useState } from "react";
import axios from "axios";
import AlunoForm from "../components/AlunoForm";

function AlunosPages() {
    const [alunos, setAlunos] = useState([]);
    const [alunoEditando, setAlunoEditando] = useState(null);

    function carregarAlunos() {
        axios.get('http://localhost:8080/alunos')
            .then(response => setAlunos(response.data))
            .catch(error => console.error('Erro ao buscar alunos:', error));
    }

    useEffect(() => {
        carregarAlunos();
    }, []);

    function handleSalvo(alunoSalvo) {
        if (alunoEditando) {
            // Atualiza o aluno editado na lista, sem precisar buscar tudo de novo
            setAlunos(alunos.map(a => a.matricula === alunoSalvo.matricula ? alunoSalvo : a));
            setAlunoEditando(null);
        } else {
            setAlunos([...alunos, alunoSalvo]);
        }
    }

    function handleDeletar(matricula) {
        if (!window.confirm('Tem certeza que deseja excluir esse aluno?')) {
            return;
        }
        axios.delete(`http://localhost:8080/alunos/${matricula}`)
            .then(() => {
                setAlunos(alunos.filter(a => a.matricula !== matricula));
            })
            .catch(error => {
                console.error('Erro ao deletar aluno:', error);
                alert('Não foi possível excluir o aluno.');
            });
    }

    return (
        <div>
            <h1>Alunos cadastrados</h1>
            <ul>
                {alunos.map(aluno => (
                    <li key={aluno.matricula}>
                        {aluno.nome} — {aluno.serie}
                        <button onClick={() => setAlunoEditando(aluno)} style={{ marginLeft: '8px' }}>
                            Editar
                        </button>
                        <button onClick={() => handleDeletar(aluno.matricula)} style={{ marginLeft: '4px' }}>
                            Excluir
                        </button>
                    </li>
                ))}
            </ul>

            <AlunoForm
                alunoEditando={alunoEditando}
                onSalvo={handleSalvo}
                onCancelar={() => setAlunoEditando(null)}
            />
        </div>
    ); 


};

export default AlunosPages;