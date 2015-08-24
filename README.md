# MicroServices de Logística
#Arquitetura

A ideia arquitetural é de compor os sistemas através de [microservices] (http://martinfowler.com/articles/microservices.html)

A motivação de utilizar essa arquitetura é de que fica fácil caso surja a necessidade de escalar a aplicação. Para isso só seria necessário criar uma classe que encapsulasse a leitura de qualquer um dos serviços via RMI. 

O projeto #[HighwayNetwork](https://github.com/xild/highwaymicroservices/tree/master/highwaynetwork) faz o cadastro da malha viária em uma base H2-SQL e logo em seguida efetua uma chamada utilizando RMI para o serviço [buscaRota]((https://github.com/xild/highwaymicroservices/tree/master/highwaypath)) (Para cadastrar a malha viária no Neo4j) . Isso significa que se o jar com o tomcat fosse executado em 10 portas diferentes o próprio #[Microservices-Support](https://github.com/xild/highwaymicroservices/tree/master/microservices-support) faria o LoadBalance da aplicação.

Exemplo: 
         
         `java -jar microservices.support-0.0.1-SNAPSHOT.jar`
         
         `java -jar highwaynetwork-0.0.1-SNAPSHOT.jar 2224`
         
         `java -jar highwaynetwork-0.0.1-SNAPSHOT.jar 2225`
         
         `java -jar highwaynetwork-0.0.1-SNAPSHOT.jar 2226`
         
         `java -jar highwaynetwork-0.0.1-SNAPSHOT.jar 2226`
Todo mundo que acessar a url do RMI http://highwaynetwork-services/  teria disponível todas essas instâncias.


O projeto está dividido em três partes que são 

#[Microservices-Support](https://github.com/xild/highwaymicroservices/tree/master/microservices-support)


A principal utilização desse projeto é que quando temos vários processos trabalhando em um conjunto precisamos achar um ou outro.
Esse pequeno projeto serve como o "Descobridor" de todos os serviços startados e os serviços conversam entre si via RMI.

Ele faz uso da biblioteca Spring Cloud e principalmente do recurso disponibilizado pela Netflix 
chamado "Eureka". 

#[HighwayNetwork](https://github.com/xild/highwaymicroservices/tree/master/highwaynetwork)


Esse projeto é um microservice para o cadastro da malha viária. O workflow desse projeto funciona da seguinte forma:  
-  Cadastro da malha viária em base de dados SQL.
-  Chamada via RestTemplate do serviço [HighwayPath](https://github.com/xild/highwaymicroservices/tree/master/highwaypath) para cadastro dessa malha no Neo4j

Para o cadastro da malha viária é esperado uma Requisição POST como a descrita abaixo: 

- Salvar Malha Viaria
   - curl -H "Content-Type: application/json" -X POST -d `json-abaixo` http://localhost:2222/v1/malhaviaria
  ` '

          {
            "nome": "PG",
            "vertices": [
            {
  
              "origem":"A",
              "destino":"B",
              "distancia":10
  
          },
          {
              "origem":"B",
              "destino":"C",
              "distancia":20
          }
           ]
          } 
          
#[HighwayPath](https://github.com/xild/highwaymicroservices/tree/master/highwaypath)

Esse é o microservices que cadastra na base de dados em grafo o "mapa" e busca o melhor caminho. 

Após a execução do passo acima, para obter o melhor caminho na malha viária, basta fazer uma requisição na url abaixo:
 
  - Buscar melhor rota
   -  `curl -H "Content-Type: application/json" -X GET localhost:2223/v1/buscaRota?{origem}{destino}{mapa}{valorLitro}{autonomia}`

O retorno será assim: 

         {
           "locais": [
             "A",
             "C",
             "D",
             "N*"
           ],
           "distancia": distanciaTotal,
           "custo": custo da viagem
         }


#HOW-TO

Por padrão o highwayNetwork será startado na porta 2222 - [configuração](https://github.com/xild/highwaymicroservices/blob/master/highwaynetwork/src/main/resources/highway-services.yaml)

Highwaypath na porta 2223 - [configuração](https://github.com/xild/highwaymicroservices/blob/master/highwaypath/src/main/resources/highwaypath-services.yaml)

E o serviço de registro na porta 111 - [configuração](https://github.com/xild/highwaymicroservices/blob/master/microservices-support/src/main/resources/registration-server.yml)

Para rodar o projeto executar os comandos abaixo em ordem:

- Microservices Support 
 - cd microservices-support
  - mvn clean package
  - cd /target
  - java -jar microservices.support-0.0.1-SNAPSHOT.jar`

- HighwayNetwork
 - cd highwaynetwork
 - mvn clean package
 - cd /target
 - java -jar highwaynetwork-0.0.1-SNAPSHOT.jar`

 
- HighwayPath
 - cd highwaypath
 - mvn clean package
 - cd /target
 - java -jar highwaypath-0.0.1-SNAPSHOT.jar`


