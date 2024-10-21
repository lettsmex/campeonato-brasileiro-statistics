# Projeto Técnicas de Programação - Análise de Dados do Campeonato Brasileiro de Futebol Masculino

O Projeto realiza a leitura e análise dos dados de jogos do Campeonato Brasileiro de Futebol Masculino entre os anos de 2003 a 2022.


## Sobre

Esse projeto foi desenvolvido por Letícia Santiago de Souza.


## Dados Solicitados

Conforme o enunciado do Projeto, é necessário exibir os seguintes dados:

* O time que mais venceu jogos no ano de 2008;
* O estado que teve menos jogos entre 2003 e 2022;
* O jogador que mais fez gols;
* O jogador que mais fez gols de pênaltis;
* O jogador que mais fez gols contra;
* O jogador que mais recebeu cartões amarelos;
* O jogador que mais recebeu cartões vermelhos;
* O placar da partida com mais gols.

Os arquivos CSV utilizados no projeto foram:

* [Dados do Campeonato Brasileiro](https://github.com/vconceicao/ada_brasileirao_dataset/tree/master)


    * campeonato-brasileiro-cartoes.csv
    * campeonato-brasileiro-estatisticas-full.csv
    * campeonato-brasileiro-full.csv
    * campeonato-brasileiro-gols.csv

## Pré-Requisitos para acessar o Projeto

* JDK 17 ou superior;
* Maven;
* IDE (IntelliJ IDEA, Visual Studio Code, etc.);
* Git instalado para clonar o repositório.

## Como Instalar

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/lettsmex/campeonato-brasileiro-statistics.git
   ```

2. **Importe o projeto:**

   Abra o projeto clonado em sua IDE de preferência.


## Como Executar

Após a instalação, compile e execute o projeto através da Main.

O projeto fará a leitura dos arquivos CSV localizados no diretório `src/main/resources/data/`.

## Arquivos CSV

Certifique-se de que os arquivos CSV estão localizados na pasta `src/main/resources/data/`.

## Estrutura do Projeto

* **src/main/java:** Código fonte do projeto.
* **src/main/resources/data:** Diretório para os arquivos CSV.
* **pom.xml:** Arquivo de configuração do Maven.