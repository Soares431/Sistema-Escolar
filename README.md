# Sistema de Gerenciamento Escolar

Sistema Java empresarial para gerenciamento completo de instituições de ensino, implementando operações CRUD para alunos, professores, turmas e salas. Desenvolvido com arquitetura em camadas (DAO Pattern), JPA/Hibernate para persistência de dados em PostgreSQL/MySQL, e mapeamento de relacionamentos complexos (OneToOne, OneToMany, ManyToMany). Interface programática via classes Java com demonstração funcional através de método main.

## 📋 Índice

- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Funcionalidades](#funcionalidades)
- [Modelo de Dados](#modelo-de-dados)
- [Como Executar](#como-executar)
- [Estrutura de Classes](#estrutura-de-classes)
- [Configuração do Banco](#configuração-do-banco)
- [Melhorias Futuras](#melhorias-futuras)

## 🚀 Tecnologias

- **Java SE** (JDK 17+)
- **JPA (Jakarta Persistence API)** 3.0
- **Hibernate** 5.5.7.Final (ORM)
- **PostgreSQL** 42.5.0 (banco principal)
- **MySQL** 8.0.31 (alternativo)
- **Maven** (gerenciamento de dependências)
- **Apache Commons Collections** 3.2.2

## 🏗️ Arquitetura

### Padrão de Projeto

**Arquitetura em Camadas com DAO Pattern**
```
┌─────────────────────────────────────┐
│   Camada de Apresentação (Main)    │
├─────────────────────────────────────┤
│   Camada de Acesso a Dados (DAO)   │
├─────────────────────────────────────┤
│   Camada de Entidades (Entity)     │
├─────────────────────────────────────┤
│   JPA/Hibernate (ORM)               │
├─────────────────────────────────────┤
│   Banco de Dados (PostgreSQL)      │
└─────────────────────────────────────┘
```

### Estrutura do Projeto
```
src/main/java/
├── br.com.mildevs.dao/
│   ├── alunoDao.java          # CRUD de alunos
│   ├── professorDao.java      # CRUD de professores
│   └── turmaDao.java          # CRUD de turmas
│
├── br.com.mildevs.entity/
│   ├── aluno.java             # Entidade aluno
│   ├── professor.java         # Entidade professor
│   ├── turma.java             # Entidade turma
│   └── sala.java              # Entidade sala
│
└── br.com.mildevs.escola/
    └── programa.java          # Classe principal

META-INF/
└── persistence.xml            # Configuração JPA
```

## ✨ Funcionalidades

### 1. Gerenciamento de Alunos

**Operações:**
- ✅ Cadastrar aluno (INSERT)
- ✅ Consultar aluno por matrícula (SELECT)
- ✅ Listar todos os alunos (SELECT ALL)
- ✅ Remover aluno (DELETE)

**Atributos:**
- Matrícula (gerada automaticamente)
- Nome completo
- Série/Ano escolar
- Data de nascimento
- Turmas matriculadas (lista)

### 2. Gerenciamento de Professores

**Operações:**
- ✅ Cadastrar professor (INSERT)
- ✅ Consultar professor por código (SELECT)
- ✅ Listar todos os professores (SELECT ALL)
- ✅ Remover professor (DELETE)

**Atributos:**
- Código de funcionário (gerado automaticamente)
- Nome completo
- Telefone
- Nível de graduação (Mestrado/Doutorado)
- Salário
- Disciplina que leciona
- Turmas associadas (lista)

### 3. Gerenciamento de Turmas

**Operações:**
- ✅ Criar turma vinculada a uma sala (INSERT)
- ✅ Adicionar professor à turma (UPDATE)
- ✅ Matricular aluno na turma (UPDATE)
- ✅ Listar chamada de alunos (SELECT)
- ✅ Listar todas as turmas (SELECT ALL)
- ✅ Remover turma (DELETE)

**Regras de Negócio:**
- Turma deve ter uma sala obrigatória
- Professor deve ser adicionado antes de matricular alunos
- Relacionamento Many-to-Many entre alunos e turmas
- Relacionamento One-to-Many entre professor e turmas

### 4. Gerenciamento de Salas

**Atributos:**
- Número da sala (gerado automaticamente)
- Largura (em metros)
- Comprimento (em metros)
- Altura (em metros)
- Turma alocada (One-to-One)

**Características:**
- Relacionamento bidirecional com turma
- Exclusão em cascata (orphan removal)

## 📊 Modelo de Dados

### Relacionamentos JPA
```
┌─────────────┐
│   Aluno     │ ◄──────┐
└─────────────┘        │
       ▲               │ ManyToMany
       │               │
       │               │
       │        ┌──────┴──────┐
       │        │    Turma    │
       │        └──────┬──────┘
       │               │
       │               ▼ ManyToOne
       │        ┌─────────────┐
       │        │  Professor  │
       │        └─────────────┘
       │
       │ OneToOne
       │
┌──────┴──────┐
│    Sala     │
└─────────────┘
```

### Tabelas Geradas

**aluno**
```sql
matricula (PK, AUTO_INCREMENT)
nome (NOT NULL)
serie (NOT NULL)
data_nascimento (NOT NULL)
```

**professor**
```sql
cod_funcionario (PK, AUTO_INCREMENT)
nome (NOT NULL)
telefone (NOT NULL)
nivel_graduacao (NOT NULL, DEFAULT 'MESTRADO')
salario (NOT NULL)
disciplina (NOT NULL)
```

**turma**
```sql
cod_turma (PK, AUTO_INCREMENT)
cod_funcionario_fk (FK → professor)
```

**sala**
```sql
nro_sala (PK, AUTO_INCREMENT)
largura (NOT NULL)
comprimento (NOT NULL)
altura (NOT NULL)
turma_fk (FK → turma, UNIQUE)
```

**turmas_alunos** (tabela associativa)
```sql
aluno_fk (FK → aluno)
turma_fk (FK → turma)
PRIMARY KEY (aluno_fk, turma_fk)
```

## 🎯 Como Executar

### Pré-requisitos

1. **Java JDK 17+** instalado
2. **PostgreSQL** instalado e rodando
3. **Maven** configurado
4. **IDE** (Eclipse, IntelliJ, VSCode)

### Configuração do Banco

**1. Criar banco de dados:**
```sql
CREATE DATABASE mildevs;
```

**2. Criar schema:**
```sql
CREATE SCHEMA escola;
```

**3. Configurar credenciais em `persistence.xml`:**
```xml
<property name="jakarta.persistence.jdbc.url"
    value="jdbc:postgresql://127.0.0.1:5432/mildevs"/>
<property name="javax.persistence.jdbc.user" value="postgres" />
<property name="jakarta.persistence.jdbc.password" value="sua_senha" />
```

### Executar o Projeto

**Via IDE:**
```
1. Importar como projeto Maven
2. Atualizar dependências (mvn clean install)
3. Executar classe programa.java
```

**Via Maven:**
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="br.com.mildevs.escola.programa"
```

## 💻 Estrutura de Classes

### Entidades (Entity Layer)

#### **aluno.java**
```java
@Entity
public class aluno {
    @Id @GeneratedValue
    private int matricula;
    
    private String nome;
    private String serie;
    private LocalDate dataNascimento;
    
    @ManyToMany
    @JoinTable(name = "turmas_alunos")
    private List<turma> turmas;
}
```

#### **professor.java**
```java
@Entity
public class professor {
    @Id @GeneratedValue
    @Column(name = "cod_funcionario")
    private int codFuncionario;
    
    private String nome;
    private String telefone;
    private String nivelGraduacao;
    private double salario;
    private String disciplina;
    
    @OneToMany(mappedBy = "professor")
    private List<turma> turmas;
}
```

#### **turma.java**
```java
@Entity
public class turma {
    @Id @GeneratedValue
    @Column(name = "cod_turma")
    private int codTurma;
    
    @ManyToOne
    private professor professor;
    
    @ManyToMany(mappedBy = "turmas")
    private List<aluno> alunos;
    
    @OneToOne(mappedBy = "turma")
    private sala sala;
}
```

#### **sala.java**
```java
@Entity
public class sala {
    @Id @GeneratedValue
    @Column(name = "nro_sala")
    private int nroSala;
    
    private double largura;
    private double comprimento;
    private double altura;
    
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "turma_fk")
    private turma turma;
}
```

### DAOs (Data Access Layer)

#### Operações Comuns

**alunoDao:**
```java
- insirarAluno(aluno)          // Persiste novo aluno
- consultaAluno(matricula)     // Busca por ID
- mostraAlunos()               // Lista todos
- removeAluno(matricula)       // Remove por ID
```

**professorDao:**
```java
- insirarProfessor(professor)      // Persiste novo professor
- consultaProfessor(codFuncionario) // Busca por ID
- mostraProfessor()                 // Lista todos
- removeProfessor(codFuncionario)   // Remove por ID
```

**turmaDao:**
```java
- criaTurma(sala)                   // Cria turma com sala
- adicionarProfessor(professor, id) // Vincula professor
- adicionaraluno(aluno, id)         // Matricula aluno
- listaChamada(id)                  // Lista alunos da turma
- listaTurma()                      // Lista todas turmas
- removeTurma(id)                   // Remove turma
```

## 📈 Exemplo de Uso

### Fluxo Completo
```java
// 1. Cadastrar professor
professorDao professorDao = new professorDao();
professor prof = new professor();
prof.setNome("João Silva");
prof.setDisciplina("Lógica de Programação");
prof.setNivelGraduacao("DOUTORADO");
prof.setSalario(2000.0);
prof.setTelefone("11999999999");
professorDao.insirarProfessor(prof);

// 2. Criar sala
sala sala = new sala();
sala.setLargura(10);
sala.setComprimento(15);
sala.setAltura(3.5);

// 3. Criar turma
turmaDao turmaDao = new turmaDao();
turma turma = turmaDao.criaTurma(sala);
turmaDao.adicionarProfessor(prof, turma.getCodTurma());

// 4. Cadastrar e matricular alunos
alunoDao alunoDao = new alunoDao();
aluno aluno1 = new aluno();
aluno1.setNome("Lucas Santos");
aluno1.setSerie("1º ano");
aluno1.setDataNascimento(LocalDate.of(2010, 5, 15));
alunoDao.insirarAluno(aluno1);

turmaDao.adicionaraluno(aluno1, turma.getCodTurma());

// 5. Listar chamada
turmaDao.listaChamada(turma.getCodTurma());
```

## ⚙️ Configurações do Hibernate

### Estratégias de Fetch

- **EAGER:** Dados carregados imediatamente
- **LAZY:** Dados carregados sob demanda

### Cascade Types

- **CascadeType.ALL:** Todas operações em cascata
- **CascadeType.PERSIST:** Apenas inserção
- **CascadeType.MERGE:** Apenas atualização
- **CascadeType.REMOVE:** Apenas remoção

### Propriedades JPA
```xml
hibernate.show_sql=true           # Exibe SQL no console
hibernate.format_sql=true         # Formata SQL
hibernate.hbm2ddl.auto=update     # Atualiza schema automaticamente
hibernate.default_schema=escola   # Schema padrão
```

## 📝 Observações Técnicas

### Padrão de Nomenclatura

**⚠️ Importante:** O projeto usa nomenclatura não convencional em Java:
- Classes começam com minúscula (deveria ser PascalCase)
- Exemplo atual: `aluno.java` → Deveria ser: `Aluno.java`

### Relacionamentos Bidirecionais

O projeto implementa relacionamentos bidirecionais:
- `mappedBy` define o lado inverso
- Sincronização manual necessária ao adicionar/remover

### Transações JPA

Todas operações de escrita devem estar em transação:
```java
manager.getTransaction().begin();
// operações
manager.getTransaction().commit();
```

---

**Nota:** Sistema desenvolvido para fins acadêmicos demonstrando implementação de JPA/Hibernate com relacionamentos complexos e padrão DAO.
