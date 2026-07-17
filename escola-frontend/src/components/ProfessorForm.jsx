import { useEffect, useState } from 'react';
import axios from 'axios';

function ProfessorForm({ professorEditando, onSalvo, onCancelar }) {
    const [nome, setNome] = useState('');
    const [telefone, setTelefone] = useState('');
    const [nivelGraduacao, setNivelGraduacao] = useState('')
    const [salario, setSalario] = useState('');
    const [disciplina, setDisciplina] = useState('');
    const [erro, setErro] = useState(''); 

    useEffect(() => {
        if (professorEditando) {
            setNome(professorEditando.nome);
            setTelefone(professorEditando.telefone);
            setNivelGraduacao(professorEditando.nivelGraduacao);
            setSalario(professorEditando.salario)
            setDisciplina(professorEditando.disciplina)
        } else {
            setNome('');
            setTelefone('');
            setNivelGraduacao('');
            setSalario('');
            setDisciplina('');
        }
    }, [professorEditando]);

    function handleSubmit(event) {
        event.preventDefault();

        const dados = { nome, telefone, nivelGraduacao, salario, disciplina};

        if (professorEditando) {
            // Modo edição: PUT
            axios.put(`http://localhost:8080/professores/${professorEditando.codProfessor}`, dados)
                .then(response => {
                    onSalvo(response.data);
                    setErro('');
                })
                .catch(error => {
                    console.error('Erro ao atualizar professsor:', error);
                    setErro('Não foi possível atualizar o professor.');
                });
        } else {
            // Modo criação: POST
            axios.post('http://localhost:8080/professores', dados)
                .then(response => {
                    onSalvo(response.data);
                    setNome('');
                    setTelefone('');
                    setNivelGraduacao('');
                    setSalario('');
                    setDisciplina('');
                    setErro('');
                })
                .catch(error => {
                    console.error('Erro ao criar professor:', error);
                    setErro('Não foi possível cadastrar o professor.');
                });
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h2>{professorEditando ? 'Editar Professor' : 'Cadastrar Professor'}</h2>

            {erro && <p style={{ color: 'red' }}>{erro}</p>}

            <div>
                <label>Nome: </label>
                <input type="text" value={nome} onChange={(e) => setNome(e.target.value)} required />
            </div>

            <div>
                <label>Telefone para Contato: </label>
                <input type="text" value={telefone} onChange={(e) => setTelefone(e.target.value)} required />
            </div>

            <div>
                <label>Nivel de Graduação: </label>
                <input type="text" value={nivelGraduacao} onChange={(e) => setNivelGraduacao(e.target.value)} required />
            </div>

            <div>
                <label>Salario: </label>
                <input type="number" value={salario} onChange={(e) => setSalario(e.target.value)} required />
            </div>
            <div>
                <label>Disciplina: </label>
                <input type="text" value={disciplina} onChange={(e) => setDisciplina(e.target.value)} required />
            </div>

            <button type="submit">{professorEditando ? 'Salvar alterações' : 'Cadastrar'}</button>
            {professorEditando && (
                <button type="button" onClick={onCancelar} style={{ marginLeft: '8px' }}>
                    Cancelar
                </button>
            )}

        </form>
    )
}

export default ProfessorForm;