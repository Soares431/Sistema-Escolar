import { useEffect, useState } from "react";
import axios from "axios";
import TurmaForm from "../components/TurmaForm";

function TurmasPages() {
    const [turmas, setTurmas] = useState([]);
    const [totalProfessores, setTotalProfessores] = useState([]);
    const [turmaEditando, setTurmaEditando] = useState(null);

    function carregarTurmas() {
        axios.get('http://localhost:8080/turmas')
            .then(response => setTurmas(response.data))
            .catch(error => console.error('Erro ao buscar turmas:', error));
    }

    function carregarProfessores() {
        axios.get('http://localhost:8080/professores')
            .then(response => setTotalProfessores(response.data))
            .catch(error => console.error('Erro ao buscar turmas:', error));
    }

    useEffect(() => {
        carregarTurmas();
        carregarProfessores();

    }, []);

    function handleSalvo(turmaSalvo) {
        if (turmaEditando) {
            // Atualiza o turma editado na lista, sem precisar buscar tudo de novo
            setTurmas(turmas.map(a => a.codTurma === turmaSalvo.codTurma ? turmaSalvo : a));
            setTurmaEditando(null);
        } else {
            setTurmas([...turmas, turmaSalvo]);
        }
    }

    function handleDeletar(codTurma) {
        if (!window.confirm('Tem certeza que deseja excluir essa turma?')) {
            return;
        }
        axios.delete(`http://localhost:8080/turmas/${codTurma}`)
            .then(() => {
                setTurmas(turmas.filter(a => a.codTurma !== codTurma));
            })
            .catch(error => {
                console.error('Erro ao deletar turma:', error);
                alert('Não foi possível excluir o turma.');
            });
    }

    return (
        <div>
            <h1>Turmas cadastrados</h1>
            <ul style={{ textAlign: 'left', listStylePosition: 'inside' }}>
                {turmas.map(turma => (
                    <li key={turma.codTurma}>
                        ID: {turma.codTurma} | {turma.nome} --  {turma.professor.nome}
                        <button onClick={() => setTurmaEditando(turma)} style={{ marginLeft: '8px' }}>
                            Editar
                        </button>
                        <button onClick={() => handleDeletar(turma.codTurma)} style={{ marginLeft: '4px' }}>
                            Excluir
                        </button>
                    </li>
                ))}
            </ul>

   

            <h2>Professores no sistema </h2>
            <ul style={{ textAlign: 'left', listStylePosition: 'inside' }}>
                {totalProfessores.map(prof => (
                    <li key={prof.codProfessor}>
                        <label>ID: {prof.codProfessor} | {prof.nome}</label>
                    </li>
                ))}
            </ul>

            <TurmaForm
                turmaEditando={turmaEditando}
                onSalvo={handleSalvo}
                onCancelar={() => setTurmaEditando(null)}
            />
        </div>
    );


};

export default TurmasPages;