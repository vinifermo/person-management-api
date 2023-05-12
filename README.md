# PERSON-MANAGEMENT-API

O objetivo deste documento é identificar seus conhecimentos quanto às tecnologias utilizadas no cotidiano de desenvolvimento da equipe de Back-end.

Esta análise propõe avaliar os seguintes temas: 
-	Qualidade de código
-	Java, Spring boot
-	API REST
-	Testes

## **Qualidade de código**

#### 1.	Durante a implementação de uma nova funcionalidade de software solicitada, quais critérios você avalia e implementa para garantia de qualidade de software?
Testes automatizados: Testes automatizados devem ser implementados para garantir que a nova funcionalidade esteja funcionando corretamente e que as mudanças não afetaram outras áreas do software. Esses testes devem ser executados regularmente e integrados com a integração contínua (CI).

Revisão de código: A revisão de código por outros membros da equipe pode ajudar a identificar problemas e erros antes que a funcionalidade seja implantada. Isso inclui revisão de estilo de código, revisão de lógica e garantir que o código está aderindo às melhores práticas e padrões.

Análise estática de código: Ferramentas de análise estática de código podem ser usadas para identificar possíveis problemas no código, como vulnerabilidades de segurança, falta de conformidade com padrões de codificação e problemas de desempenho.

Testes de aceitação do usuário: Os testes de aceitação do usuário podem ser usados para garantir que a nova funcionalidade atenda aos requisitos do usuário. Os usuários podem ser envolvidos na definição de casos de teste e no teste da funcionalidade para garantir que atenda às suas necessidades.

Monitoramento de logs: Logs e métricas devem ser monitorados para garantir que a nova funcionalidade esteja funcionando corretamente em produção. Isso inclui monitorar erros, desempenho e uso.

#### 2.	Em qual etapa da implementação você considera a qualidade de software?
Durante a implementação, a equipe de desenvolvimento deve se concentrar em implementar os requisitos do software de acordo com as especificações, utilizando boas práticas de codificação e garantindo que o código esteja limpo, claro e fácil de entender e manter. 
Além disso, a equipe de desenvolvimento deve criar testes automatizados e realizar revisões de código para identificar problemas e erros antes que a funcionalidade seja implantada.

## Desafio Java

Usando Spring boot, crie uma API simples para gerenciar Pessoas. Esta API deve permitir:  
-	Criar uma pessoa
-	Editar uma pessoa
-	Consultar uma pessoa
-	Listar pessoas
-	Criar endereço para pessoa
-	Listar endereços da pessoa
-	Poder informar qual endereço é o principal da pessoa  

Uma Pessoa deve ter os seguintes campos:  
-	Nome
-	Data de nascimento
-	Endereço:
-	Logradouro
-	CEP
-	Número
-	Cidade

Requisitos  
-	Todas as respostas da API devem ser JSON  
- Banco de dados H2
