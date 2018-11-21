Neste projeto foi desenvolvida uma aplicação monolítica, utilizando a plataforma de desenvolvimento JHipster. 
Para modelagem inicial foi utilizada a linguagem JDL que é suportada pelo JHipster. Com a linguagem JDL é possível fazer a descrição de todas as entidades e relacionamentos através de uma sintaxe simples e em um único arquivo. Além de permitir definir configurações que serão utilizadas pelo JHipster. 
Para o armazenamento dos dados foi utilizado o banco Postgres. E para o controle de versão e execução de script no banco de dados foi utilizado Liquibase. 
No lado do servidor, foi utilizada a linguagem Java através do Spring Boot. Para o gerenciamento de usuários e de papéis foram utilizadas as estruturas configuradas pelo JHipster. 
Já no lado do cliente, foi utilizado o framework Angular 5 além de utilizar bibliotecas como rxjs, font-awesome e ng-bootstrap.

Características definidas:

- Ao cadastrar novos usuários a senha inicial será o próprio login.

- Usuário com perfil de administrador somente poderá gerenciar o cadastro de usuários.

- Usuário com perfil de triador somente terá acesso a tela de Processos onde somente poderá gerenciar processos criados por ele.
  - Para incluir um processo, deverá fornecer uma descrição e selecionar os usuários com perfil de Finalizador que deverão dar um parecer sobre o processo.
  - Poderá visualizar os pareceres que forem cadastrados para os processos que venha a criar.
  - Ao remover um processo, o sistema irá remover os pareceres que estiverem associados ao processo.

- Usuário com perfil de finalizador somente terá acesso a tela de Parecer onde somente poderá gerenciar os pareceres criado por ele.
  - Para incluir um parecer, é necessário que ele esteja associado a algum processo como usuário que deverá dar um parecer.
  - Poderá adicionar mais de um parecer por processo.
