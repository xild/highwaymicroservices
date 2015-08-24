# MicroServices de Logística
O projeto está dividido em três partes que são descritas abaixo: 

#[Microservices-Support](https://github.com/xild/highwaymicroservices/tree/master/microservices-support)

Projeto que serve como um registrador dos serviços da aplicação como um todo. Os serviços independentes precisam se comunicar e isso é feito através da biblioteca do netflix Eureka. 

Mais de um serviço pode ser inicilizado que todo acesso que ocorrer via RMI terá um loadbalance feito.

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


#HOW-TO

Para rodar o projeto a maneira mais fácil é executar o arquivo bat "runServices.bat"

