import { useEffect, useState } from 'react';
import axios from 'axios';
import AlunoForm from './components/AlunoForm';
import { Link, Route, Routes } from 'react-router-dom';
import AlunosPages from './pages/AlunosPage';
import ProfessoresPages from './pages/ProfessoresPage';

function App() {

  return (
    <div>
      <Link to="/alunos">Alunos</Link>
      <Link to="/professores">Professores</Link>
      {/* <Link to="/turmas">Turmas</Link>
      <Link to="/salas">Salas</Link> */}

      <Routes>
        <Route path='/alunos' element={<AlunosPages />}></Route>
        <Route path='/professores' element={<ProfessoresPages />}></Route>
      </Routes>

      
    </div>
  );
}

export default App; 