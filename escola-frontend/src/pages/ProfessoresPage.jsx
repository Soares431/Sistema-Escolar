import { useEffect, useState } from "react";
import axios from "axios";
import ProfessorForm from "../components/ProfessorForm"

function ProfessoresPages() {
    const [professores, setProfessores] = useState([]);
    const [professorEditando, setProfessorEditando] = useState(null);

    function carregarProfessores() {
        axios.get('http://localhost:8080/professores')
            .then(response => setProfessores(response.data))
            .catch(error => console.error('Erro ao buscar professores:', error));
    }

    useEffect(() => {
        carregarProfessores();
    }, []);

    function handleSalvo(professorSalvo) {
        if (professorEditando) {
            // Atualiza o professor editado na lista, sem precisar buscar tudo de novo
            setProfessores(professores.map(a => a.codProfessor === professorSalvo.codProfessor ? professorSalvo : a));
            setProfessorEditando(null);
        } else {
            setProfessores([...professores, professorSalvo]);
        }
    }

    function handleDeletar(codProfessor) {
        if (!window.confirm('Tem certeza que deseja excluir esse professor?')) {
            return;
        }
        axios.delete(`http://localhost:8080/professores/${codProfessor}`)
            .then(() => {
                setProfessores(professores.filter(a => a.codProfessor !== codProfessor));
            })
            .catch(error => {
                console.error('Erro ao deletar professor:', error);
                alert('Não foi possível excluir o professor.');
            });
    }

     return (
        <div>
            <h1>Professores Cadasstrados</h1>
            <ul>
                {professores.map(prof => (
                    <li key={prof.codProfessor}>
                        {prof.nome} — {prof.disciplina} 
                        <button onClick={() => setProfessorEditando(prof)} style={{ marginLeft: '8px' }}>
                            Editar
                        </button>
                        <button onClick={() => handleDeletar(prof.codProfessor)} style={{ marginLeft: '4px' }}>
                            Excluir
                        </button>
                    </li>
                ))}
            </ul>

            <ProfessorForm
                professorEditando={professorEditando}
                onSalvo={handleSalvo}
                onCancelar={() => setProfessorEditando(null)}
            />
        </div>
    ); 
}

export default ProfessoresPages;
