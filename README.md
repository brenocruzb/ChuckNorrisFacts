# ChuckNorrisFacts

Aplicativo criado para consumir uma API pública usando arquitetura mvvm.

## Sobre a organização do projeto

Foram criados os seguintes pacotes para o projeto:

* base: Responsável por guardar as classes que servem de base para as demais. Por exemplo, componentes básicos em toda viewModel, fica na BaseViewModel, portando toda viewModel herda de BaseViewModel.
* data: Pacote que contém classes responsáveis pelo gerenciamento de dados da aplicação.
	- rest: responsável por obter os dados da API;
	- dao: responsável por gerenciar os dados locais da aplicação;
	- local: a principio minha ideia era usar SharedPreference para gerenciar os dados locais. Como a gestão ficou um pouco complexa, abandonei esta ideia. Deixei o pacote a título de curiosidade para ver como seria feito;
	- model: guarda as data class da aplicação.
* di: Toda a injeção de dependência da aplicação fica armazenada neste pacote.
* ui: Responsável por guardar as views da aplicação. Cada view possui sua viewModel, e neste projeto, seu adapter. Caso houvesse uso de fragmentes, eles ficariam armazenados neste pacote também. Cada tela possui seu próprio pacote. Nesta aplicação temos a mainActivity e a searchActivity.
* util: Pacote responsável por armazenar classes e arquivos genéricos que não dependem da aplicação. Neste caso temos um arquivo denominado CustomSetter que possui métodos genéricos de suporte a diversas partes da aplicação.

* App Class: Uma classe que herda de Application para que possa inicializar componentes globais na primeira execução do app. Neste caso, usamos esta classe para iniciar o koin (framework de injeção de dependência).

## Sobre o layout da aplicação

Componentes básicos foram criados para que a iteração com o usuário seja a mais simples possível. O uso de recyclerView para a tela principal e um framework para gerenciamento de TAGS foram alguns destes componentes.

## Gradle

No gradle da aplicação é possível identificar todos os frameworks usados no app.

Dentre eles, os principais são:

* Retrofit - Aamplamente usada para requisições REST em Android
* RxJava2 - Programação reativa
* Koin - Injeção de dependência
* Room - Framework de banco de dados responsável por abstrair boa parte do código criado quando se trabalhava diretamente com SQLite.
